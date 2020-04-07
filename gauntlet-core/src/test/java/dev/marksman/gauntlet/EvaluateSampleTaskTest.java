package dev.marksman.gauntlet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static dev.marksman.gauntlet.EvaluateSampleTask.testSampleTask;
import static dev.marksman.gauntlet.Prop.prop;
import static dev.marksman.gauntlet.TestTaskResult.success;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;


@SuppressWarnings("unchecked")
@ExtendWith(MockitoExtension.class)
class EvaluateSampleTaskTest {

    private static final Prop<Integer> isOdd = prop(n -> n % 2 == 1);

    @Mock
    Context context;
    @Mock
    ResultReceiver receiver;

    @Test
    void successfulCase() {
        when(receiver.shouldRun(0)).thenReturn(true);
        EvaluateSampleTask<Integer> task = testSampleTask(context, receiver, isOdd, 0, 1);

        task.run();

        verify(receiver, times(1)).reportResult(eq(0), eq(success()));
        verifyNoMoreInteractions(receiver);
    }

    @Test
    void failureCase() {
        when(receiver.shouldRun(0)).thenReturn(true);
        EvaluateSampleTask<Integer> task = testSampleTask(context, receiver, isOdd, 0, 2);

        task.run();

        verify(receiver, times(1)).reportResult(eq(0), argThat(TestTaskResult::isFailure));
        verifyNoMoreInteractions(receiver);
    }

    @Test
    void errorCase() {
        when(receiver.shouldRun(0)).thenReturn(true);
        EvaluateSampleTask<Integer> task = testSampleTask(context, receiver, prop(n -> {
            throw new Exception("error!");
        }), 0, 2);

        task.run();

        verify(receiver, times(1)).reportResult(eq(0), argThat(TestTaskResult::isError));
        verifyNoMoreInteractions(receiver);
    }

    @Test
    void doesNotRunTestIfNotNeeded() {
        Prop<Integer> prop = (Prop<Integer>) mock(Prop.class);

        when(receiver.shouldRun(0)).thenReturn(false);
        EvaluateSampleTask<Integer> task = testSampleTask(context, receiver, prop, 0, 2);

        task.run();

        verifyNoInteractions(prop);
        verifyNoMoreInteractions(receiver);
    }
}