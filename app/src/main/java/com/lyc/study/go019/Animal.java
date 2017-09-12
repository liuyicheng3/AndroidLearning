package com.lyc.study.go019;

/**
 * Created by lyc on 17/9/12.
 */

public class Animal {
    private String name="origin  animal";

    public Animal(String name) {
        this.name= name;
    }

    public Animal() {
    }

    @Override
    public String toString() {
        return "Animal:"+name;
    }
}
