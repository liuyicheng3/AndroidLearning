package com.lyc.study.go019.jayway;

import java.io.File;

/**
 * Created by lyc on 17/9/10.
 */


public class Exmaple3 {
    public boolean create(String directoryPath) {
        File directory = new File(directoryPath);

        if (directory.exists()) {
            throw new IllegalArgumentException("" + directoryPath + " already exists.");
        }

        return directory.mkdirs();
    }
}
