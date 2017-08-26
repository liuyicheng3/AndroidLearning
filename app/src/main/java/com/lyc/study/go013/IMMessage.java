package com.lyc.study.go013;

import java.io.Serializable;
import java.util.Map;

/**
 * Created by lyc on 17/5/23.
 */


public interface IMMessage extends IMMessageSuper  {
    String getUuid();

    boolean isTheSame(IMMessage var1);

    String getSessionId();


    String getFromNick();



    void setContent(String var1);

    String getContent();

    long getTime();

    void setFromAccount(String var1);

    String getFromAccount();



    Map<String, Object> getRemoteExtension();

    void setRemoteExtension(Map<String, Object> var1);

    Map<String, Object> getLocalExtension();

    void setLocalExtension(Map<String, Object> var1);

    String getPushContent();

    void setPushContent(String var1);

    Map<String, Object> getPushPayload();

    void setPushPayload(Map<String, Object> var1);


    boolean isRemoteRead();

    int getFromClientType();

}