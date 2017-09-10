package com.lyc.study.go019.jayway;

/**
 * Created by lyc on 17/9/10.
 */

public class Exmaple1 {
    public void methodToTest() {
        System.out.print("launch public Method");
        final long id = IdGenerator.generateNewId();
    }

    private void privateMethodToTest() {
        System.out.print("launch private Method");
        final long id = IdGenerator.generateNewId();
    }
}
