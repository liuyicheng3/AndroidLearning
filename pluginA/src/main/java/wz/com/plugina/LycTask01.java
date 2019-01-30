package wz.com.plugina;
import org.gradle.api.Action;
import org.gradle.api.DefaultTask;
import org.gradle.api.Task;
import org.gradle.api.tasks.TaskAction;

import java.lang.reflect.Field;
import java.util.Set;

public class LycTask01 extends DefaultTask {
    public LycTask01() {
        super();
    }

    /**
     * 在doFitst 和doLast中间执行
     */
    @TaskAction
    void doAction (){
        listversion();
        System.out.print("LycTask01 doAction");

    }

    private void listversion(){
        ReleaseInfoExtension custom_ext = (ReleaseInfoExtension) getProject().getExtensions().getByName
                ("ccjReleaseInfo");
        Field[] all = ReleaseInfoExtension.class.getDeclaredFields();
        for (Field field :all){
            try {
                String key = field.getName();
                Object value = field.get(custom_ext);
                System.out.println(key+" => "+value);

            } catch (IllegalAccessException e) {
                System.out.println("Exception"+ e);
            }
        }

    }


}
