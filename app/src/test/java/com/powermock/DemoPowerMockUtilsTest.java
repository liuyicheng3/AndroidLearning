package com.powermock;

import com.lyc.study.BuildConfig;
import com.lyc.study.go019.StaticUtils;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.rule.PowerMockRule;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;


import commom.TestApplicationManager;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.doCallRealMethod;
import static org.powermock.api.mockito.PowerMockito.doReturn;
import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * PowerMock 使用 demo
 *
 *
 * Created by lyc
 * on 16/7/20.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21,manifest="app/src/main/AndroidManifest.xml", application = TestApplicationManager.class)
@PowerMockIgnore({"org.mockito.*", "org.robolectric.*", "android.*"})
@PrepareForTest(StaticUtils.class)
public class DemoPowerMockUtilsTest {
    @Rule
    public PowerMockRule rule = new PowerMockRule();

    @Before
    public void setup() {
        mockStatic(StaticUtils.class);
    }

    @Test
    public void testPartialmock() throws Exception {
        spy(StaticUtils.class);
        doReturn("partial mocked test1!").when(StaticUtils.class, "test1");

        /**mock 特定参数的调用*/
        doReturn("partial mocked test2!").when(StaticUtils.class, "test2", "sssss");
//        doReturn("partial mocked test2!").when(StaticUtils.class,"test2","ss");

        /**mock 任意参数的调用, 会覆盖统一条件的 mock*/
        doReturn("partial mocked test2! modiify").when(StaticUtils.class, "test2", Mockito.any(String.class));
        doReturn("partial mocked test2! modiify").when(StaticUtils.class, "test2", Mockito.any(Integer.class));

        System.out.println(StaticUtils.test1());
        System.out.println(StaticUtils.test2("sssss"));
        System.out.println(StaticUtils.test2("ss"));
        System.out.println(StaticUtils.test2("aa"));
        System.out.println(StaticUtils.test2(123));
        System.out.println(StaticUtils.test3());

        /**取消 mock, 调用真正的方法*/
        doCallRealMethod().when(StaticUtils.class, "test1");
        System.out.println(StaticUtils.test1());


        System.out.println("\n\n\n==================");
        /**相比上面的会确实取调用方法, 运行方法内的代码, 只是 mock 了返回值.*/
        when(StaticUtils.class, "test3").thenReturn("mock return after");
        System.out.println(StaticUtils.test3());


        /**verify 方法调用的验证*/
        // verifyStatic函数的参数是一个校验模型
        // times(1)表示执行了1次， 但是此时还不知道对哪个函数的执行次数校验1次
        // 必须在后面调用 要校验的 函数， 执行后， Powermock就知道要校验谁了，
        // Powermock此时会执行真正的校验逻辑， 看test2函数并且参数为"ss" 是否真的执行了1次
        verifyStatic(times(1));
        StaticUtils.test2("ss");

        verifyStatic(times(2));
        StaticUtils.test1();

    }

    @Test
    public void testMock() {
        List mockedList = Mockito.mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

    }

    @Test
    public void testMock2(){
        //mock creation
        List mockedList = Mockito.mock(List.class);

        //using mock object
        mockedList.add("one");
        mockedList.clear();

        //verification
        verify(mockedList).add("one");
        verify(mockedList).clear();

        when(mockedList.get(anyInt())).thenReturn("element");
        doReturn("eeee").when(mockedList).get(anyInt());
    }
}