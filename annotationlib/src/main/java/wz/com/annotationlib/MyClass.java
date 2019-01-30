package wz.com.annotationlib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class MyClass {
    @Target(ElementType.TYPE) // 注解作用在类上
    @Retention(RetentionPolicy.CLASS)
    public @interface Test {
        String path();
    }

    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.CLASS)
    public @interface DIActivity {

    }

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    public @interface BYView {
        int value() default 0;
    }
}
