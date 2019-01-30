package wz.com.plugina;

public class ReleaseInfoExtension {

    String versionName ="alpha";
    String versionCode="0.1";
    String glideVersion="4.0.0";
    String versionInfo="default";
    String fileName="default_fileName";


    @Override
    public String toString() {
        return "ReleaseInfoExtension{" +
                "versionName='" + versionName + '\'' +
                ", versionCode='" + versionCode + '\'' +
                ", versionInfo='" + versionInfo + '\'' +
                ", fileName='" + fileName + '\'' +
                '}';
    }
}
