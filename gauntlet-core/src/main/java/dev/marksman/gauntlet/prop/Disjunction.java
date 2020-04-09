package dev.marksman.gauntlet.prop;

import dev.marksman.enhancediterables.ImmutableNonEmptyFiniteIterable;
import dev.marksman.gauntlet.EvalResult;
import dev.marksman.gauntlet.Prop;

import static dev.marksman.gauntlet.EvalResult.evalResult;
import static dev.marksman.gauntlet.Failure.failure;


final class Disjunction<A> implements Prop<A> {
    private final ImmutableNonEmptyFiniteIterable<Prop<A>> operands;
    private final String name;

    Disjunction(ImmutableNonEmptyFiniteIterable<Prop<A>> operands) {
        this.operands = operands;
        this.name = String.join(" ∨ ",
                operands.fmap(Prop::getName));
    }

    @Override
    public Prop<A> or(Prop<A> other) {
        return new Disjunction<>((other instanceof Disjunction<?>)
                ? operands.concat(((Disjunction<A>) other).operands)
                : operands.append(other));
    }

    @Override
    public EvalResult test(A data) {
        EvalResult result = evalResult(failure(this, "All disjuncts failed."));
        for (Prop<A> prop : operands) {
            EvalResult test = prop.test(data);
            if (test.isSuccess()) {
                return test;
            } else {
                result = combine(result, test);
            }
        }
        return result;
    }

    private EvalResult combine(EvalResult acc, EvalResult item) {
        // success + _ -> success
        // failure + success -> success
        // failure + failure -> failure

        return acc
                .match(EvalResult::evalResult,
                        f1 -> item.match(EvalResult::evalResult,
                                f2 -> evalResult(f1.addCause(f2))));
    }

    @Override
    public String getName() {
        return name;
    }
}
