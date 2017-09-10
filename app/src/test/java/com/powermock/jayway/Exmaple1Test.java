package com.powermock.jayway;

import com.lyc.study.go019.StaticUtils;
import com.lyc.study.go019.jayway.Exmaple1;
import com.lyc.study.go019.jayway.IdGenerator;
import com.thoughtworks.xstream.core.ReferenceByIdMarshaller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.mockStatic;
import static org.powermock.api.mockito.PowerMockito.spy;
import static org.powermock.api.mockito.PowerMockito.verifyStatic;
import static org.powermock.api.mockito.PowerMockito.when;
import static org.powermock.reflect.internal.WhiteboxImpl.invokeMethod;

/**
 * Created by lyc on 17/9/10.
 * 参考 https://github.com/powermock/powermock-examples-maven
 *
 */
@RunWith(PowerMockRunner.class)
@PrepareForTest(IdGenerator.class)
public class Exmaple1Test {
    @Test
    public void demoStaticMethodMocking() throws Exception {
        mockStatic(IdGenerator.class);

       /*
        * Setup the expectation using the standard Mockito syntax,
        * generateNewId() will now return 2 everytime it's invoked
        * in this test.
        */
        when(IdGenerator.class,"generateNewId").thenReturn(2L);

        /**
         * 对于普通的方法可以直接调用
         *
         * 对于private的方法  可以使用 invokeMethod  间接调用
         *
         */

//        new Exmaple1().methodToTest();


        invokeMethod(new Exmaple1(), "privateMethodToTest");


        // Optionally verify that the static method was actually called
        verifyStatic();
        IdGenerator.generateNewId();
    }
}
