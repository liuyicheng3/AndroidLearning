package com.powermock;

import com.lyc.study.go019.PowerMockTarget;
import com.lyc.study.go019.StaticUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.File;
import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyNew;
import static org.powermock.api.mockito.PowerMockito.verifyPrivate;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.api.mockito.PowerMockito.whenNew;

/**
 * PowerMock 使用 demo
 *
 * 如果只是使用PowerMock  就需要把Robolectric 那一套都去掉，还要去掉 @Rule
 *
 *
 * 如果只有一个 PrepareForTest 就这么写 @PrepareForTest(StaticUtils.class)
 *
 * 其实这个注解也可以写到方法前，但是会导致单独运行每一个测试都失败（包括不涉及到需要PrepareForTest的），错误如下：
 * java.lang.Exception: No tests found matching Method
 *
 *
 * 一个讲PowerMock 很好的实例：
 * https://www.ibm.com/developerworks/cn/java/j-lo-powermock/index.html
 * https://blog.jayway.com/2009/10/28/untestable-code-with-mockito-and-powermock/
 *
 * Created by lyc
 * on 16/7/20.
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest({StaticUtils.class,PowerMockTarget.class})
public class PowerMockUtilTest {
//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
    }



    @Test
    public void testCallArgumentInstance() {
        File file = mock(File.class);
        PowerMockTarget underTest = new PowerMockTarget();

        when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callArgumentInstance(file));
    }


    @Test
    public void testPartialMock() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        doReturn("partial mocked test4! 会直接跳过方法体").when(StaticUtils.class, "test4",any());
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    public void testPartialMock2() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        when(StaticUtils.class, "test4", any()).thenReturn("partial mocked test4!  会先执行方法体 然后返回mock的结果");
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    public void testCancelMock() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        doReturn("partial mocked test4! 会直接跳过方法体").when(StaticUtils.class, "test4",any());
        doCallRealMethod().when(StaticUtils.class, "test4",any());
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    public void testCallInternalInstance() throws Exception {
        File file = mock(File.class);
        PowerMockTarget underTest = new PowerMockTarget();
        whenNew(File.class).withArguments("bbb").thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callInternalInstance("bbb"));
    }

    @Test
    public void createDirectoryStructureWhenPathDoesntExist() throws Exception {
        final String directoryPath = "mocked path";

        File directoryMock = mock(File.class);

        // This is how you tell PowerMockito to mock construction of a new File.
        whenNew(File.class).withArguments(directoryPath).thenReturn(directoryMock);

        // Standard expectations
        when(directoryMock.exists()).thenReturn(false);
        when(directoryMock.mkdirs()).thenReturn(true);

        assertTrue(new PowerMockTarget().create(directoryPath));

        // Optionally verify that a new File was "created".
        verifyNew(File.class).withArguments(directoryPath);
    }

    @Test
    public void demoPrivateMethodMocking() throws Exception {
        final String expected = "TEST VALUE";
        final String nameOfMethodToMock = "methodToMock";
        final String input = "input";

        PowerMockTarget underTest = spy(new PowerMockTarget());

       /*
        * Setup the expectation to the private method using the method name
        */
        when(underTest, nameOfMethodToMock, input).thenReturn(expected);

        assertEquals(expected, underTest.methodToTest());

        // Optionally verify that the private method was actually called
        verifyPrivate(underTest).invoke(nameOfMethodToMock, input);
    }
}