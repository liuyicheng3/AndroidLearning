package com.lyc.study.go019;

import java.io.File;

/**
 * Created by lyc on 17/9/10.
 */

public class PowerMockTarget {
    public boolean callArgumentInstance(File file) {
        return file.exists();
    }

    public boolean callInternalInstance(String path) {
        File file = new File(path);
        return file.exists();
    }
}
