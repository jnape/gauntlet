package dev.marksman.gauntlet.examples;

import dev.marksman.collectionviews.Vector;
import dev.marksman.gauntlet.Arbitraries;
import dev.marksman.gauntlet.Arbitrary;
import dev.marksman.gauntlet.GauntletApiBase;
import org.junit.jupiter.api.Test;

import static dev.marksman.gauntlet.Prop.predicate;

public final class VectorTests extends GauntletApiBase {
    private static final Arbitrary<Vector<Integer>> vectors = Arbitraries.ints().vector();

    @Test
    void reverseTwiceIsOriginal() {
        assertThat(all(vectors)
                .satisfy(predicate("reverse twice is original",
                        xs -> xs.reverse().reverse().equals(xs))));
    }
}
