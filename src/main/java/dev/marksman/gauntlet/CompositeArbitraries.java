package dev.marksman.gauntlet;

import com.jnape.palatable.lambda.adt.Maybe;
import com.jnape.palatable.lambda.adt.choice.Choice2;
import com.jnape.palatable.lambda.adt.hlist.HList;
import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.adt.hlist.Tuple3;
import com.jnape.palatable.lambda.adt.hlist.Tuple4;
import com.jnape.palatable.lambda.adt.hlist.Tuple5;
import com.jnape.palatable.lambda.functions.Fn2;
import com.jnape.palatable.lambda.functions.Fn3;
import com.jnape.palatable.lambda.functions.Fn4;
import com.jnape.palatable.lambda.functions.Fn5;
import dev.marksman.gauntlet.shrink.ShrinkStrategy;
import dev.marksman.gauntlet.shrink.builtins.ShrinkStrategies;

import static com.jnape.palatable.lambda.adt.Maybe.just;
import static com.jnape.palatable.lambda.adt.Maybe.nothing;
import static dev.marksman.gauntlet.Arbitrary.arbitrary;

final class CompositeArbitraries {
    static <A, B> Arbitrary<Tuple2<A, B>> combine(Arbitrary<A> a,
                                                  Arbitrary<B> b) {

        Fn2<A, B, Tuple2<A, B>> toFn = HList::tuple;

        return arbitrary(parameters -> new CompositeSupplyStrategy2<>(a.supplyStrategy(parameters),
                        b.supplyStrategy(parameters),
                        toFn),
                combineShrinkStrategies(a.getShrinkStrategy(), b.getShrinkStrategy(), toFn),
                PrettyPrinting.<A, B>productPrettyPrinter(a.getPrettyPrinter(), b.getPrettyPrinter()));
    }

    static <A, B, C> Arbitrary<Tuple3<A, B, C>> combine(Arbitrary<A> a,
                                                        Arbitrary<B> b,
                                                        Arbitrary<C> c) {

        Fn3<A, B, C, Tuple3<A, B, C>> toFn = HList::tuple;

        return arbitrary(parameters -> new CompositeSupplyStrategy3<>(a.supplyStrategy(parameters),
                        b.supplyStrategy(parameters),
                        c.supplyStrategy(parameters),
                        toFn),
                combineShrinkStrategies(a.getShrinkStrategy(), b.getShrinkStrategy(), c.getShrinkStrategy(), toFn),
                PrettyPrinting.<A, B, C>productPrettyPrinter(a.getPrettyPrinter(), b.getPrettyPrinter(), c.getPrettyPrinter()));
    }

    static <A, B, C, D> Arbitrary<Tuple4<A, B, C, D>> combine(Arbitrary<A> a,
                                                              Arbitrary<B> b,
                                                              Arbitrary<C> c,
                                                              Arbitrary<D> d) {

        Fn4<A, B, C, D, Tuple4<A, B, C, D>> toFn = HList::tuple;

        return arbitrary(parameters -> new CompositeSupplyStrategy4<>(a.supplyStrategy(parameters),
                        b.supplyStrategy(parameters),
                        c.supplyStrategy(parameters),
                        d.supplyStrategy(parameters),
                        toFn),
                combineShrinkStrategies(a.getShrinkStrategy(), b.getShrinkStrategy(), c.getShrinkStrategy(), d.getShrinkStrategy(), toFn),
                PrettyPrinting.<A, B, C, D>productPrettyPrinter(a.getPrettyPrinter(), b.getPrettyPrinter(),
                        c.getPrettyPrinter(), d.getPrettyPrinter()));
    }

    static <A, B, C, D, E> Arbitrary<Tuple5<A, B, C, D, E>> combine(Arbitrary<A> a,
                                                                    Arbitrary<B> b,
                                                                    Arbitrary<C> c,
                                                                    Arbitrary<D> d,
                                                                    Arbitrary<E> e) {

        Fn5<A, B, C, D, E, Tuple5<A, B, C, D, E>> toFn = HList::tuple;

        return arbitrary(parameters -> new CompositeSupplyStrategy5<>(a.supplyStrategy(parameters),
                        b.supplyStrategy(parameters),
                        c.supplyStrategy(parameters),
                        d.supplyStrategy(parameters),
                        e.supplyStrategy(parameters),
                        toFn),
                combineShrinkStrategies(a.getShrinkStrategy(), b.getShrinkStrategy(), c.getShrinkStrategy(),
                        d.getShrinkStrategy(), e.getShrinkStrategy(), toFn),
                PrettyPrinting.<A, B, C, D, E>productPrettyPrinter(a.getPrettyPrinter(), b.getPrettyPrinter(),
                        c.getPrettyPrinter(), d.getPrettyPrinter(), e.getPrettyPrinter()));
    }

    static <A, B> Arbitrary<Choice2<A, B>> arbitraryChoice2(Arbitrary<A> a,
                                                            Arbitrary<B> b) {
        return null;
    }


    private static <A, B> Maybe<ShrinkStrategy<Tuple2<A, B>>> combineShrinkStrategies(Maybe<ShrinkStrategy<A>> shrinkA,
                                                                                      Maybe<ShrinkStrategy<B>> shrinkB,
                                                                                      Fn2<A, B, Tuple2<A, B>> toFn) {
        if (isNothing(shrinkA) && isNothing(shrinkB)) {
            return nothing();
        } else {
            return just(ShrinkStrategies.shrinkProduct(shrinkA.orElse(ShrinkStrategy.none()),
                    shrinkB.orElse(ShrinkStrategy.none()),
                    toFn, t -> t));
        }
    }

    private static <A, B, C> Maybe<ShrinkStrategy<Tuple3<A, B, C>>> combineShrinkStrategies(Maybe<ShrinkStrategy<A>> shrinkA,
                                                                                            Maybe<ShrinkStrategy<B>> shrinkB,
                                                                                            Maybe<ShrinkStrategy<C>> shrinkC,
                                                                                            Fn3<A, B, C, Tuple3<A, B, C>> toFn) {
        if (isNothing(shrinkA) && isNothing(shrinkB) && isNothing(shrinkC)) {
            return nothing();
        } else {
            return just(ShrinkStrategies.shrinkProduct(shrinkA.orElse(ShrinkStrategy.none()),
                    shrinkB.orElse(ShrinkStrategy.none()),
                    shrinkC.orElse(ShrinkStrategy.none()),
                    toFn, t -> t));
        }
    }

    private static <A, B, C, D> Maybe<ShrinkStrategy<Tuple4<A, B, C, D>>> combineShrinkStrategies(Maybe<ShrinkStrategy<A>> shrinkA,
                                                                                                  Maybe<ShrinkStrategy<B>> shrinkB,
                                                                                                  Maybe<ShrinkStrategy<C>> shrinkC,
                                                                                                  Maybe<ShrinkStrategy<D>> shrinkD,
                                                                                                  Fn4<A, B, C, D, Tuple4<A, B, C, D>> toFn) {
        if (isNothing(shrinkA) && isNothing(shrinkB) && isNothing(shrinkC) && isNothing(shrinkD)) {
            return nothing();
        } else {
            return just(ShrinkStrategies.shrinkProduct(shrinkA.orElse(ShrinkStrategy.none()),
                    shrinkB.orElse(ShrinkStrategy.none()),
                    shrinkC.orElse(ShrinkStrategy.none()),
                    shrinkD.orElse(ShrinkStrategy.none()),
                    toFn, t -> t));
        }
    }

    private static <A, B, C, D, E> Maybe<ShrinkStrategy<Tuple5<A, B, C, D, E>>> combineShrinkStrategies(Maybe<ShrinkStrategy<A>> shrinkA,
                                                                                                        Maybe<ShrinkStrategy<B>> shrinkB,
                                                                                                        Maybe<ShrinkStrategy<C>> shrinkC,
                                                                                                        Maybe<ShrinkStrategy<D>> shrinkD,
                                                                                                        Maybe<ShrinkStrategy<E>> shrinkE,
                                                                                                        Fn5<A, B, C, D, E, Tuple5<A, B, C, D, E>> toFn) {
        if (isNothing(shrinkA) && isNothing(shrinkB) && isNothing(shrinkC) && isNothing(shrinkD) && isNothing(shrinkE)) {
            return nothing();
        } else {
            return just(ShrinkStrategies.shrinkProduct(shrinkA.orElse(ShrinkStrategy.none()),
                    shrinkB.orElse(ShrinkStrategy.none()),
                    shrinkC.orElse(ShrinkStrategy.none()),
                    shrinkD.orElse(ShrinkStrategy.none()),
                    shrinkE.orElse(ShrinkStrategy.none()),
                    toFn, t -> t));
        }
    }

    private static <A> boolean isNothing(Maybe<A> ma) {
        return ma.match(__ -> true, __ -> false);
    }
}
