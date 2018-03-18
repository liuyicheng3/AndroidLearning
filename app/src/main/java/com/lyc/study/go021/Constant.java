package com.lyc.study.go021;

import android.support.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by lyc on 18/3/18.
 */

public interface Constant {

    interface ReadyStatus {
        /**
         * 作者准备状态
         */
        String OK = "OK";
        String NO = "NO";
        @StringDef({OK, NO})
        @Retention(RetentionPolicy.SOURCE)
        @interface ReadyType {
        }
    }

}
