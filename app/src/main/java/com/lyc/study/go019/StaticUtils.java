package com.lyc.study.go019;

/**
 * Created by ring
 * on 16/7/20.
 */
public class StaticUtils {

    public static String test1() {
        System.out.println("called");

        return "StaticUtils.test1";
    }

    public static String test2(String s) {
        System.out.println("called");

        return s;
    }
    public static String test2(int s) {
        System.out.println("called");

        return s+"";
    }

    public static String test3() {
        System.out.println("called");

        return "StaticUtils.test3";
    }

    public static String test4(Exception e) {
        System.out.println("called");

        return "StaticUtils.test4";
    }
}
