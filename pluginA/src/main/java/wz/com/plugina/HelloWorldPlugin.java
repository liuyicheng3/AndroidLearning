package wz.com.plugina;

import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class HelloWorldPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.getExtensions().create("ccjReleaseInfo",ReleaseInfoExtension.class);       //创建扩展属性
        System.out.print("自定义Plugin：HelloWorldPlugin");
        project.getTasks().create("lycTask01", LycTask01.class).dependsOn("clean").setGroup("custom");
    }
}