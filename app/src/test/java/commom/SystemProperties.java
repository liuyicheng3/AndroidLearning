package commom;

/**
 * 为了测试 SystemProperties 的 shadow而添加
 * 测试中可以指定
 *
 Build.VERSION.RELEASE
 Build.VERSION.SDK_INT
 Build.DEVICE
 Build.FINGERPRINT
 Build.BRAND
 Build.BOARD
 Build.MODEL

 等, 参见 test.java/cn.etouch.ecalendar.demo.EnvironmentTest
 */
public class SystemProperties {

    public static final int PROP_NAME_MAX = 31;
    public static final int PROP_VALUE_MAX = 255;
    private static final String TAG = "Shuame SystemProperties";


    public static String get(String key) {
        return null;
    }

    public static String get(String key, String def) {
        return null;
    }

}
