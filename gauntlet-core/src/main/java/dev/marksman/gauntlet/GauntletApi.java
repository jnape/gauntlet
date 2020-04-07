package dev.marksman.gauntlet;

import com.jnape.palatable.lambda.adt.hlist.Tuple2;
import com.jnape.palatable.lambda.adt.hlist.Tuple3;
import dev.marksman.kraftwerk.GeneratorParameters;

import java.time.Duration;
import java.util.concurrent.Executor;

public interface GauntletApi {

    <A> GeneratorTestApi<A> all(Arbitrary<A> generator);

    <A, B> GeneratorTestApi<Tuple2<A, B>> all(Arbitrary<A> generatorA,
                                              Arbitrary<B> generatorB);

    <A, B, C> GeneratorTestApi<Tuple3<A, B, C>> all(Arbitrary<A> generatorA,
                                                    Arbitrary<B> generatorB,
                                                    Arbitrary<C> generatorC);

    Executor getExecutor();

    GeneratorTestRunner getGeneratorTestRunner();

    Reporter getReporter();

    GeneratorParameters getGeneratorParameters();

    Duration getDefaultTimeout();

    int getDefaultSampleCount();

    GauntletApi withExecutor(Executor executor);

    GauntletApi withGeneratorTestRunner(GeneratorTestRunner testRunner);

    GauntletApi withReporter(Reporter reporter);

    GauntletApi withDefaultSampleCount(int sampleCount);

    GauntletApi withGeneratorParameters(GeneratorParameters generatorParameters);

    GauntletApi withDefaultTimeout(Duration timeout);
}
