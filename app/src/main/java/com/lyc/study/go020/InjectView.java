package com.lyc.study.go020;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lyc on 18/3/13.
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface InjectView {
    /**控件的id
     *  @InjectView(id = R.id.btn_dialog)
     * */
    int id() default -1;


    /**
     * 也可以默认就是value
     *  注解就不需要写Id了 @InjectView(R.id.btn_dialog)
     */
//    int value() default -1;

}