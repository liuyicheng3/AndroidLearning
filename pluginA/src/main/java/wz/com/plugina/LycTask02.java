package wz.com.plugina;
import org.gradle.api.DefaultTask;
import org.gradle.api.artifacts.Configuration;
import org.gradle.api.artifacts.ConfigurationContainer;
import org.gradle.api.artifacts.result.DependencyResult;
import org.gradle.api.plugins.ExtensionContainer;
import org.gradle.api.tasks.TaskAction;

import java.lang.reflect.Field;
import java.util.Set;

public class LycTask02 extends DefaultTask {
    public LycTask02() {
        super();
    }

    public String targetBuild = "releaseruntimeclasspath";

    /**
     * 在doFitst 和doLast中间执行
     */
    @TaskAction
    void doAction (){
        ConfigurationContainer c = getProject().getConfigurations();
        Set<String> names = c.getNames();
        for (String item : names ){
            if (item.toLowerCase().contains(targetBuild)){
                System.out.println(item);
                Configuration conf = c.getByName(item);
                try {
                    Set<? extends DependencyResult> dSet= conf.getIncoming().getResolutionResult()
                            .getRoot().getDependencies();

                    for (DependencyResult i : dSet){
                        String depName  = i.getRequested().getDisplayName();
                        System.out.println(depName);
                    }
                }catch (Exception e){
                    System.out.print(e.getCause());
                }
            }
//            dSet.add()
        }

    }



}
