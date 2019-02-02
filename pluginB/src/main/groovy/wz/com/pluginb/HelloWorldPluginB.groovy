package wz.com.pluginb

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.artifacts.Configuration
import org.gradle.api.artifacts.result.DependencyResult
import org.gradle.api.artifacts.result.ResolvedDependencyResult

public class HelloWorldPluginB implements Plugin<Project> {


    final static def EXT_NAME = "DependenceChecker"
    final static def PLUGIN_NAME = "RDCTask"

    @Override
    void apply(Project target) {
        //cooker-plugin
        //比如这里加一个简单的task
        target.task('cooker-test-task') << {
            println "hello lyc, this is cooker test task!"
        }

        // 创建一个Ext数据
        target.extensions.create(EXT_NAME, DependenceChecker)
        println("=============apply===============")
        DependenceChecker ext = target.extensions.DependenceChecker

        // 向Project中追加一个Task
        target.task(PLUGIN_NAME) << {

            Map depMap = new HashMap()
            String checkMode = "${ext.variant}runtimeclasspath"
            println target.configurations
            target.configurations.each { Configuration conf ->

                //  考虑多个Flavor的情况
                if (conf.name.toLowerCase().contains(checkMode)) {
                    // 获取所有依赖信息
                    conf.incoming.resolutionResult.root.dependencies.each { dr ->
                        resolveDependencies(depMap, dr)
                    }
                    printDepInfo(depMap)
                    reportDepInfo(depMap, ext.abortBuild)
                }
            }
        }
        target.tasks.findByName('preBuild').dependsOn(target.tasks.findByName(PLUGIN_NAME))
    }

    static def printDepInfo(Map depMap) {
        depMap.each { k, v ->
            println(k + " => " + v.size())
        }
    }

    static def reportDepInfo(Map depMap, boolean abortBuild) {

        depMap.each { k, v ->
            if (v.size() > 1) {
                if (abortBuild) {
                    throw new RuntimeException("${k} has duplicate dependences, please resolve it...\n\n${v}")
                }
            }
        }
    }


    static def resolveDependencies(Map<String, Set<String>> map, DependencyResult dr) {

        def depName = dr.requested.displayName
        if (dr != null && !depName.contains("project")) {
            String[] depSplit = depName.split(":")
            if (depSplit.length > 2) {
                def packageName = depSplit[0] + depSplit[1]
                def list = map.get(packageName)
                if (list == null) {
                    list = new HashSet<String>()
                    map.put(packageName, list)
                }
                list.add(depName)
            }
        }
        if (dr instanceof ResolvedDependencyResult) {
            dr.selected.dependencies.each { subDr ->
                resolveDependencies(map, subDr)
            }
        }
    }
}
