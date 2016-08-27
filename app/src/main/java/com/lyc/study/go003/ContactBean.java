package com.lyc.study.go003;

import java.util.ArrayList;

/**
 * Created by lyc on 2016/2/22.
 */
public class ContactBean {

    public int contactId; //id  
    public String desplayName;//姓名
    public ArrayList<String>  phoneNumbers=new ArrayList<String>();// 电话号码
    public String sortKey; // 排序用的  
    public Long photoId; // 图片id  
    public String lookUpKey;
    //排序用的权重
    public double weight=0;

    @Override
    public String toString() {
        return "weight"+weight+"->"+desplayName + phoneNumbers;
    }
}
