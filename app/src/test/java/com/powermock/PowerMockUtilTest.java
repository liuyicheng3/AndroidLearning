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

import static org.mockito.Matchers.any;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * PowerMock 使用 demo
 *
 * 如果只是使用PowerMock  就需要把Robolectric 那一套都去掉，还要去掉 @Rule
 *
 *
 *
 * Created by lyc
 * on 16/7/20.
 */
@RunWith(PowerMockRunner.class)
public class PowerMockUtilTest {
//    @Rule
//    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
    }



    @Test
    public void testCallArgumentInstance() {
        File file = PowerMockito.mock(File.class);
        PowerMockTarget underTest = new PowerMockTarget();

        when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callArgumentInstance(file));
    }


    @Test
    @PrepareForTest(StaticUtils.class)
    public void testPartialMock() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        doReturn("partial mocked test4! 会直接跳过方法体").when(StaticUtils.class, "test4",any());
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    @PrepareForTest(StaticUtils.class)
    public void testPartialMock2() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        when(StaticUtils.class, "test4", any()).thenReturn("partial mocked test4!  会先执行方法体 然后返回mock的结果");
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    @PrepareForTest(StaticUtils.class)
    public void testCancelMock() throws Exception {
        mockStatic(StaticUtils.class);
        spy(StaticUtils.class);
        doReturn("partial mocked test4! 会直接跳过方法体").when(StaticUtils.class, "test4",any());
        doCallRealMethod().when(StaticUtils.class, "test4",any());
        System.out.print(StaticUtils.test4(new IOException()));
    }

    @Test
    @PrepareForTest(PowerMockTarget.class)
    public void testCallInternalInstance() throws Exception {
        File file = PowerMockito.mock(File.class);
        PowerMockTarget underTest = new PowerMockTarget();
        PowerMockito.whenNew(File.class).withArguments("bbb").thenReturn(file);
        PowerMockito.when(file.exists()).thenReturn(true);
        Assert.assertTrue(underTest.callInternalInstance("bbb"));
    }
}