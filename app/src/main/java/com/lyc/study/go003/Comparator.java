package com.lyc.study.go003;

/**
 * Created by lyc on 2016/2/22.
 */
public class Comparator implements java.util.Comparator<ContactBean> {

    /**
     * 如果o1小于o2,返回一个负数;如果o1大于o2，返回一个正数;如果他们相等，则返回0;
     */
    @Override
    public int compare(ContactBean o1, ContactBean o2) {
       return (int)(o2.weight-o1.weight);
    }
}
