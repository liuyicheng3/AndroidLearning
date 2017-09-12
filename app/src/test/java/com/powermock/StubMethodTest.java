package com.powermock;

import com.lyc.study.go019.exmaple.suppressmethod.SuppressMethod;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.exceptions.MethodNotFoundException;
import org.powermock.reflect.exceptions.TooManyMethodsFoundException;

import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.support.membermodification.MemberMatcher.method;
import static org.powermock.api.support.membermodification.MemberModifier.stub;

@RunWith(PowerMockRunner.class)
@PrepareForTest(SuppressMethod.class)
public class StubMethodTest {

    @Test
    public void whenStubbingInstanceMethodTheMethodReturnsTheStubbedValue() throws Exception {
        String expectedValue = "Hello";
        stub(method(SuppressMethod.class, "getObject")).toReturn(expectedValue);

        SuppressMethod tested = new SuppressMethod();

        assertEquals(expectedValue, tested.getObject());
        assertEquals(expectedValue, tested.getObject());
    }

    @Test
    public void whenMockInstanceMethodTheMethodReturnsTheStubbedValue() throws Exception {
        String expectedValue = "Hello";

        SuppressMethod tested = mock(SuppressMethod.class);
        spy(tested);
        when(tested,"getObject").thenReturn(expectedValue);

        assertEquals(expectedValue, tested.getObject());
        assertEquals(expectedValue, tested.getObject());
    }

    @Test
    public void whenStubbingStaticMethodTheMethodReturnsTheStubbedValue() throws Exception {
        String expectedValue = "Hello";
        stub(method(SuppressMethod.class, "getObjectStatic")).toReturn(expectedValue);

        assertEquals(expectedValue, SuppressMethod.getObjectStatic());
        assertEquals(expectedValue, SuppressMethod.getObjectStatic());
    }

    @Test
    public void whenStubbingInstanceMethodWithPrimiteValueTheMethodReturnsTheStubbedValue() throws Exception {
        float expectedValue = 4;
        stub(method(SuppressMethod.class, "getFloat")).toReturn(expectedValue);

        SuppressMethod tested = new SuppressMethod();

        assertEquals(expectedValue, tested.getFloat(), 0.0f);
        assertEquals(expectedValue, tested.getFloat(), 0.0f);
    }

    @Test(expected = TooManyMethodsFoundException.class)
    public void whenSeveralMethodsFoundThenTooManyMethodsFoundExceptionIsThrown() throws Exception {
        stub(method(SuppressMethod.class, "sameName"));
    }

    @Test(expected = MethodNotFoundException.class)
    public void whenNoMethodsFoundThenMethodNotFoundExceptionIsThrown() throws Exception {
        stub(method(SuppressMethod.class, "notFound"));
    }

    @Test
    public void whenStubbingInstanceMethodByPassingTheMethodTheMethodReturnsTheStubbedValue() throws Exception {
        String expected = "Hello";
        stub(method(SuppressMethod.class, "getObject")).toReturn(expected);

        SuppressMethod tested = new SuppressMethod();

        assertEquals(expected, tested.getObject());
        assertEquals(expected, tested.getObject());
    }

    @Test
    public void whenStubbingStaticMethodByPassingTheMethodTheMethodReturnsTheStubbedValue() throws Exception {
        String expected = "Hello";
        stub(method(SuppressMethod.class, "getObjectStatic")).toReturn(expected);

        assertEquals(expected, SuppressMethod.getObjectStatic());
        assertEquals(expected, SuppressMethod.getObjectStatic());
    }

    @Test(expected = ClassCastException.class)
    public void whenStubbingInstanceMethodWithWrongReturnTypeThenClasscastExceptionIsThrown() throws Exception {
        String illegalReturnType = "Hello";
        stub(method(SuppressMethod.class, "getFloat")).toReturn(illegalReturnType);
        SuppressMethod tested = new SuppressMethod();
        tested.getFloat();
    }

    @Test
    public void whenStubbingInstanceMethodToThrowExceptionTheMethodThrowsTheStubbedException() throws Exception {
        Exception expected = new Exception("message");
        stub(method(SuppressMethod.class, "getObject")).toThrow(expected);

        SuppressMethod tested = new SuppressMethod();

        try {
            tested.getObject();
            fail();
        } catch (Exception e) {
            assertEquals("message", e.getMessage());
        }
    }

    @Test
    public void whenStubbingStaticMethodToThrowExceptionTheMethodThrowsTheStubbedException() throws Exception {
        Exception expected = new Exception("message");
        stub(method(SuppressMethod.class, "getObjectStatic")).toThrow(expected);
        try {
            SuppressMethod.getObjectStatic();
            fail();
        } catch (Exception e) {
            assertEquals("message", e.getMessage());
        }
    }

}