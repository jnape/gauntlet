package dev.marksman.gauntlet;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.functions.Fn0;
import com.jnape.palatable.lambda.functions.Fn1;
import dev.marksman.enhancediterables.ImmutableFiniteIterable;
import dev.marksman.gauntlet.shrink.Shrink;
import dev.marksman.kraftwerk.Parameters;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static dev.marksman.enhancediterables.ImmutableFiniteIterable.emptyImmutableFiniteIterable;
import static dev.marksman.gauntlet.FilteredArbitrary.filteredArbitrary;
import static dev.marksman.gauntlet.util.FilterChain.filterChain;

final class ConcreteArbitrary<A> implements Arbitrary<A> {
    private final Fn1<Parameters, ValueSupplier<A>> generator;
    private final ImmutableFiniteIterable<Fn1<Parameters, Parameters>> parameterTransforms;
    private final Maybe<Shrink<A>> shrink;
    private final Fn1<A, String> prettyPrinter;
    private final int maxDiscards;
    private final Fn0<String> labelSupplier;

    private ConcreteArbitrary(Fn1<Parameters, ValueSupplier<A>> generator,
                              ImmutableFiniteIterable<Fn1<Parameters, Parameters>> parameterTransforms,
                              Maybe<Shrink<A>> shrink,
                              Fn1<A, String> prettyPrinter,
                              int maxDiscards,
                              Fn0<String> labelSupplier) {
        this.generator = generator;
        this.parameterTransforms = parameterTransforms;
        this.shrink = shrink;
        this.prettyPrinter = prettyPrinter;
        this.maxDiscards = maxDiscards;
        this.labelSupplier = labelSupplier;
    }

    @Override
    public ValueSupplier<A> prepare(Parameters parameters) {
        Parameters transformedParameters = parameterTransforms.foldLeft((acc, f) -> f.apply(acc), parameters);
        return generator.apply(transformedParameters);
    }

    @Override
    public Maybe<Shrink<A>> getShrink() {
        return shrink;
    }

    @Override
    public Fn1<A, String> getPrettyPrinter() {
        return prettyPrinter;
    }

    @Override
    public Arbitrary<A> withShrink(Shrink<A> shrink) {
        return new ConcreteArbitrary<>(generator, parameterTransforms, just(shrink), prettyPrinter, maxDiscards, labelSupplier);
    }

    @Override
    public Arbitrary<A> withNoShrink() {
        return shrink.match(__ -> this,
                __ -> new ConcreteArbitrary<>(generator, parameterTransforms, nothing(), prettyPrinter, maxDiscards, labelSupplier));
    }

    @Override
    public Arbitrary<A> suchThat(Fn1<A, Boolean> predicate) {
        return filteredArbitrary(this, filterChain(predicate), maxDiscards, labelSupplier);
    }

    @Override
    public Arbitrary<A> withMaxDiscards(int maxDiscards) {
        if (maxDiscards < 0) {
            maxDiscards = 0;
        }
        if (maxDiscards != this.maxDiscards) {
            return new ConcreteArbitrary<>(generator, parameterTransforms, shrink, prettyPrinter, maxDiscards, labelSupplier);
        } else {
            return this;
        }
    }

    @Override
    public Arbitrary<A> withPrettyPrinter(Fn1<A, String> prettyPrinter) {
        return new ConcreteArbitrary<>(generator, parameterTransforms, shrink, prettyPrinter, maxDiscards, labelSupplier);
    }

    @Override
    public <B> Arbitrary<B> convert(Fn1<A, B> ab, Fn1<B, A> ba) {
        return new ConcreteArbitrary<>(generator.fmap(vs -> vs.fmap(ab)),
                parameterTransforms, shrink.fmap(s -> s.convert(ab, ba)),
                prettyPrinter.contraMap(ba),
                maxDiscards, labelSupplier);

    }

    @Override
    public Arbitrary<A> modifyGeneratorParameters(Fn1<Parameters, Parameters> modifyFn) {
        return new ConcreteArbitrary<>(generator, parameterTransforms.append(modifyFn), shrink, prettyPrinter, maxDiscards, labelSupplier);
    }

    static <A> ConcreteArbitrary<A> concreteArbitrary(Fn1<Parameters, ValueSupplier<A>> generator,
                                                      Maybe<Shrink<A>> shrink,
                                                      Fn1<A, String> prettyPrinter,
                                                      Fn0<String> labelSupplier) {
        return new ConcreteArbitrary<>(generator, emptyImmutableFiniteIterable(), shrink, prettyPrinter, Gauntlet.DEFAULT_MAX_DISCARDS,
                labelSupplier);
    }

}
