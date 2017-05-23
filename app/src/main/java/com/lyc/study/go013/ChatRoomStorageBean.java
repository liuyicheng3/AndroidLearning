package com.lyc.study.go013;


import java.io.Serializable;
import java.util.Map;

/**
 * Created by lyc on 17/5/22.
 * 聊天室消息的存储类
 */

public class ChatRoomStorageBean implements Serializable {
    public String uuid;
    public String sessionId;
    public String fromNick;

    public String content="content";

    public long time;
    public String fromAccount;

    public Map<String, Object> remoteExtension;
    public Map<String, Object> localExtension;

    public String attach;

//    public  void



}
