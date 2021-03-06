package dev.marksman.gauntlet;

import dev.marksman.kraftwerk.constraints.Constraint;
import dev.marksman.kraftwerk.constraints.DoubleRange;
import dev.marksman.kraftwerk.constraints.FloatRange;
import dev.marksman.kraftwerk.constraints.IntRange;
import dev.marksman.kraftwerk.constraints.LongRange;
import dev.marksman.kraftwerk.constraints.ShortRange;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static dev.marksman.gauntlet.Arbitraries.bigDecimals;
import static dev.marksman.gauntlet.Arbitraries.bigIntegers;
import static dev.marksman.gauntlet.Arbitraries.bytes;
import static dev.marksman.gauntlet.Arbitraries.doubleFractionals;
import static dev.marksman.gauntlet.Arbitraries.doubles;
import static dev.marksman.gauntlet.Arbitraries.durations;
import static dev.marksman.gauntlet.Arbitraries.floatFractionals;
import static dev.marksman.gauntlet.Arbitraries.floats;
import static dev.marksman.gauntlet.Arbitraries.intNaturals;
import static dev.marksman.gauntlet.Arbitraries.ints;
import static dev.marksman.gauntlet.Arbitraries.localDateTimes;
import static dev.marksman.gauntlet.Arbitraries.localDates;
import static dev.marksman.gauntlet.Arbitraries.localTimes;
import static dev.marksman.gauntlet.Arbitraries.longNaturals;
import static dev.marksman.gauntlet.Arbitraries.longs;
import static dev.marksman.gauntlet.Arbitraries.shortNaturals;
import static dev.marksman.gauntlet.Arbitraries.shorts;
import static dev.marksman.gauntlet.TestParametersSource.generateParametersForTests;
import static dev.marksman.kraftwerk.Generators.generateBigDecimalRange;
import static dev.marksman.kraftwerk.Generators.generateBigIntegerRange;
import static dev.marksman.kraftwerk.Generators.generateByteRange;
import static dev.marksman.kraftwerk.Generators.generateDoubleRange;
import static dev.marksman.kraftwerk.Generators.generateDurationRange;
import static dev.marksman.kraftwerk.Generators.generateFloatRange;
import static dev.marksman.kraftwerk.Generators.generateIntRange;
import static dev.marksman.kraftwerk.Generators.generateLocalDateRange;
import static dev.marksman.kraftwerk.Generators.generateLocalDateTimeRange;
import static dev.marksman.kraftwerk.Generators.generateLocalTimeRange;
import static dev.marksman.kraftwerk.Generators.generateLongRange;
import static dev.marksman.kraftwerk.Generators.generateShortRange;

final class ArbitrariesTest extends GauntletApiBase {

    @Nested
    @DisplayName("ints")
    class Ints {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateIntRange()),
                    range -> all(ints(range)).satisfy(inRange(range)));
        }

        @Test
        void allNaturalsInRange() {
            assertThat(all(intNaturals()).satisfy(inRange(IntRange.inclusive(0, Integer.MAX_VALUE))));
        }
    }

    @Nested
    @DisplayName("longs")
    class Longs {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateLongRange()),
                    range -> all(longs(range)).satisfy(inRange(range)));
        }

        @Test
        void allNaturalsInRange() {
            assertThat(all(longNaturals()).satisfy(inRange(LongRange.inclusive(0, Long.MAX_VALUE))));
        }
    }


    @Nested
    @DisplayName("shorts")
    class Shorts {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateShortRange()),
                    range -> all(shorts(range)).satisfy(inRange(range)));
        }

        @Test
        void allNaturalsInRange() {
            assertThat(all(shortNaturals()).satisfy(inRange(ShortRange.inclusive((short) 0, Short.MAX_VALUE))));
        }
    }

    @Nested
    @DisplayName("bytes")
    class Bytes {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateByteRange()),
                    range -> all(bytes(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("doubles")
    class Doubles {

        // counterexamples:
        // java.lang.AssertionError: For test created with parameter: DoubleRange{4.9E-324 .. 4.9E-324}
        //  (run 9 of 10 in parameterized test group)
        //Counterexample found after 0 successes using seed -462449230673441572
        //Counterexample: 0.0

        @Test
        @Disabled
        void allInRange() {
            assertForEach(generateParametersForTests(generateDoubleRange()),
                    range -> all(doubles(range)).satisfy(inRange(range)));
        }

        @Test
        void fractionalsInRange() {
            assertThat(all(doubleFractionals()).satisfy(inRange(DoubleRange.exclusive(1f))));
        }
    }

    @Nested
    @DisplayName("floats")
    class Floats {

        //java.lang.AssertionError: For test created with parameter: FloatRange{3.4028235E38 .. 3.4028235E38}
        //(run 2 of 10 in parameterized test group)
        //Counterexample found after 0 successes using seed 2406810347792536945
        //Counterexample: 3.4028233E38

        //java.lang.AssertionError: For test created with parameter: FloatRange{0.0 .. 1.4E-45}
        //(run 7 of 10 in parameterized test group)
        //Counterexample found after 0 successes using seed -1233798184509104853
        //Counterexample: 2.8E-45

        @Test
        @Disabled
        void allInRange() {
            assertForEach(generateParametersForTests(generateFloatRange()),
                    range -> all(floats(range)).satisfy(inRange(range)));
        }

        @Test
        void fractionalsInRange() {
            assertThat(all(floatFractionals()).satisfy(inRange(FloatRange.exclusive(1f))));
        }
    }

    @Nested
    @DisplayName("bigIntegers")
    class BigIntegers {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateBigIntegerRange()),
                    range -> all(bigIntegers(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("bigDecimals")
    class BigDecimals {
        // TODO: fix kraftwerk BigDecimal generator
        @Test
        @Disabled
        void allInRange() {
            assertForEach(generateParametersForTests(generateBigDecimalRange()),
                    range -> all(bigDecimals(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("localDates")
    class LocalDates {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateLocalDateRange()),
                    range -> all(localDates(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("localTimes")
    class LocalTimes {

        // counterexample:
        //java.lang.AssertionError: For test created with parameter: LocalTimeRange{08:38:03.194917203 .. 08:54:00.475576586}
        //  (run 1 of 10 in parameterized test group)
        //Counterexample found after 34 successes using seed -1326148028601250931
        //Counterexample: 08:54:00.475576586

        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateLocalTimeRange()),
                    range -> all(localTimes(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("localDateTimes")
    class LocalDateTimes {

        //java.lang.AssertionError: For test created with parameter: LocalDateTimeRange{1899-03-23T18:14:39.679507037 .. 2113-03-03T06:30:12.936335354}
        //(run 3 of 10 in parameterized test group)
        //Counterexample found after 2 successes using seed 1717198910286683303
        //Counterexample: 2113-03-03T17:12:17.559301675

        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateLocalDateTimeRange()),
                    range -> all(localDateTimes(range)).satisfy(inRange(range)));
        }
    }

    @Nested
    @DisplayName("durations")
    class Durations {
        @Test
        void allInRange() {
            assertForEach(generateParametersForTests(generateDurationRange()),
                    range -> all(durations(range)).satisfy(inRange(range)));
        }
    }

    private <A> Prop<A> inRange(Constraint<A> range) {
        return Prop.predicate("in range", range::includes);
    }
}