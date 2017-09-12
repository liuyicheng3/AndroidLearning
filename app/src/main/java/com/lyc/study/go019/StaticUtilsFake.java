package com.lyc.study.go019;

/**
 * Created by ring
 * on 16/7/20.
 */
public class StaticUtilsFake {


    private static String instance ="Fake origin";

    public static String fakeTest5() {
        System.out.println("StaticUtilsFake.fakeTest5");

        return "StaticUtilsFake.test5";
    }
}
