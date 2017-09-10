package com.lyc.study.go019.jayway;

/**
 * Created by lyc on 17/9/10.
 */

public class Example2 {
    public String methodToTest() {
        return methodToMock("input");
    }

    private String methodToMock(String input) {
        return "REAL VALUE = " + input;
    }
}
