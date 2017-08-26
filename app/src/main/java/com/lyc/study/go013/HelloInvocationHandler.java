package com.lyc.study.go013;

import com.lyc.common.Mlog;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lyc on 17/5/23.
 */

public class HelloInvocationHandler implements InvocationHandler {
//    IHello hello = new Hello();
    InnerProxy innerProxy;

    public HelloInvocationHandler(InnerProxy innerProxy) {
        this.innerProxy = innerProxy;
    }

    /*
                       * @param proxy : 当前代理类的一个实例； 若在invoke()方法中调用proxy的非final方法，将造成无限循环调用.
                       */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        // 前置的业务逻辑操作
        Mlog.e("---开始");

        // 调用被代理类的方法，传入参数args，得到返回
//        Object object = method.invoke(hello, args);

        // 后置的业务逻辑操作
        Mlog.e("---结束");
        if (method.getName().equalsIgnoreCase("hello")){
            return "call IHello.hello";
        }else if (method.getName().equalsIgnoreCase("hello2")){
            return 1;
        }else {
            return null;
        }

    }




}
