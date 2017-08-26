package com.lyc.study.go013;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * Created by lyc on 17/5/22.
 */
// TODO: 17/5/23    动态代理的方法 先舍弃  先用静态代理
public class ChatRoomMessageInvocationHandler implements InvocationHandler {
    ChatRoomStorageBean storage;

    public ChatRoomMessageInvocationHandler(ChatRoomStorageBean bean){
        this.storage = bean;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName= method.getName();
        if (methodName.equals("getUuid")){
            return storage.uuid;
        }else if (methodName.equals("getSessionId")){
            return storage.sessionId;
        }else if (methodName.equals("getFromNick")){
            return storage.fromNick;
        }else if (methodName.equals("getContent")) {
            return storage.content;
        }else if (methodName.equals("getTime")){
            return storage.time;
        }else if (methodName.equals("getFromAccount")){
            return storage.fromAccount;
        }else if (methodName.equals("getRemoteExtension")){
            return storage.remoteExtension;
        }else if (methodName.equals("getLocalExtension")){
            return storage.localExtension;
        }else if (methodName.equals("getSuperInfo")){
            return storage.superInfo;
        }else {
            //Method threw 'java.lang.ClassCastException' exception. Cannot evaluate $Proxy13.toString()
            //这个地方偶尔会报这个错误，  估计和tostring 有关   不能反悔 null和new Object  而要返回一个确定的String 就不会出现这个错误
            return new Object();
        }

    }
}
