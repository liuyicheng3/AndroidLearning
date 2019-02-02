package wz.com.plugina;

import org.gradle.api.DefaultTask;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.Dependency;
import org.gradle.api.artifacts.DependencySet;
import org.gradle.api.artifacts.result.DependencyResult;
import org.gradle.api.artifacts.result.ResolutionResult;
import org.gradle.api.plugins.ExtensionContainer;

import java.util.Map;
import java.util.Set;

public class HelloWorldPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {

        ConfigurationContainer c = project.getConfigurations();
        System.out.println("1~~~~~~");
        System.out.print(c);
        System.out.println("2~~~~~~");
        Set<Project> pSet = project.getAllprojects();
        for(Project p : pSet){
            System.out.print(p);
        }
        System.out.println("3~~~~~~");
        Set<Project> spSet = project.getSubprojects();
        for(Project p : spSet){
            System.out.print(p);
        }
        Map<Project, Set<Task>> ts = project.getAllTasks(false);
        System.out.println("4~~~~~~");
        Set<Project> kset = ts.keySet();
        for(Project p : kset){
            System.out.println(p);
            System.out.println(ts.get(p));
        }
        project.getExtensions().create("ccjReleaseInfo",ReleaseInfoExtension.class);       //创建扩展属性
        System.out.print("自定义Plugin：HelloWorldPlugin");
        project.getTasks().create("lycTask01", LycTask01.class).dependsOn("clean").setGroup("custom");
        project.getTasks().create("lycTask02", LycTask02.class).dependsOn("clean").setGroup
                ("custom");
    }
}