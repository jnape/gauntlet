package dev.marksman.gauntlet;

import com.jnape.palatable.lambda.functions.Fn1;
import com.jnape.palatable.lambda.functor.Contravariant;
import dev.marksman.collectionviews.NonEmptyVector;
import dev.marksman.gauntlet.prop.Facade;

import static dev.marksman.gauntlet.prop.Facade.biconditional;
import static dev.marksman.gauntlet.prop.Facade.conjunction;
import static dev.marksman.gauntlet.prop.Facade.disjunction;
import static dev.marksman.gauntlet.prop.Facade.exclusiveDisjunction;
import static dev.marksman.gauntlet.prop.Facade.implication;
import static dev.marksman.gauntlet.prop.Facade.mapped;
import static dev.marksman.gauntlet.prop.Facade.negation;

public interface Prop<A> extends Contravariant<A, Prop<?>>, Named {
    static <A> Prop<A> predicate(Fn1<? super A, Boolean> predicate) {
        return Facade.predicate(predicate);
    }

    static <A> Prop<A> predicate(String name, Fn1<? super A, Boolean> predicate) {
        return Facade.predicate(name, predicate);
    }

    static <A> Prop<A> prop(Fn1<? super A, SimpleResult> evaluator) {
        return Facade.prop(evaluator);
    }

    static <A> Prop<A> prop(String name, Fn1<? super A, SimpleResult> evaluator) {
        return Facade.prop(name, evaluator);
    }

    static <A> Prop<A> alwaysPass() {
        return Facade.alwaysPass();
    }

    static <A> Prop<A> alwaysFail() {
        return Facade.alwaysFail();
    }

    static <A> Prop<A> alwaysFail(String failureReason) {
        return Facade.alwaysFail(failureReason);
    }

    static <A> Prop<A> dynamic(Fn1<A, Prop<A>> selector) {
        return Facade.dynamic(selector);
    }

    static <A> Prop<A> named(String name, Prop<A> prop) {
        return Facade.named(name, prop);
    }

    @SafeVarargs
    static <A> Prop<A> allOf(Prop<A> first, Prop<A>... more) {
        return Facade.conjunction(NonEmptyVector.of(first, more));
    }

    @SafeVarargs
    static <A> Prop<A> anyOf(Prop<A> first, Prop<A>... more) {
        return Facade.disjunction(NonEmptyVector.of(first, more));
    }

    @SafeVarargs
    static <A> Prop<A> notAllOf(Prop<A> first, Prop<A>... more) {
        return Facade.conjunction(NonEmptyVector.of(first, more)).not();
    }

    @SafeVarargs
    static <A> Prop<A> noneOf(Prop<A> first, Prop<A>... more) {
        return Facade.disjunction(NonEmptyVector.of(first, more)).not();
    }

    EvalResult evaluate(A data);

    String getName();

    @Override
    default <B> Prop<B> contraMap(Fn1<? super B, ? extends A> fn) {
        return mapped(fn, this);
    }

    default Prop<A> and(Prop<A> other) {
        return conjunction(this, other);
    }

    default Prop<A> or(Prop<A> other) {
        return disjunction(this, other);
    }

    default Prop<A> xor(Prop<A> other) {
        return exclusiveDisjunction(this, other);
    }

    default Prop<A> not() {
        return negation(this);
    }

    default Prop<A> implies(Prop<A> other) {
        return implication(this, other);
    }

    default Prop<A> iff(Prop<A> other) {
        return biconditional(this, other);
    }

    default Prop<A> rename(String name) {
        return named(name, this);
    }

    default Prop<A> safe() {
        return Facade.safe(this);
    }
}
