package com.powermock.jayway;

import com.lyc.study.go019.jayway.Example2;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static junit.framework.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by lyc on 17/9/10.
 */

@PrepareForTest(Example2.class)
@RunWith(PowerMockRunner.class)
public class Example2Test {
    @Test
    public void demoPrivateMethodMocking() throws Exception {
        final String expected = "TEST VALUE";
        final String nameOfMethodToMock = "methodToMock";
        final String input = "input";

        Example2 underTest = spy(new Example2());
 
	/*
     * Setup the expectation to the private method using the method name
	 */
        when(underTest, nameOfMethodToMock, input).thenReturn(expected);

        assertEquals(expected, underTest.methodToTest());

        // Optionally verify that the private method was actually called
        verifyPrivate(underTest).invoke(nameOfMethodToMock, input);
    }
}