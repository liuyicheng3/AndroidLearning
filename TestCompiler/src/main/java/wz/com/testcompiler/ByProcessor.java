package wz.com.testcompiler;

import com.google.auto.service.AutoService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.Elements;
import javax.lang.model.util.Types;

import wz.com.annotationlib.MyClass;

import static javax.lang.model.element.Modifier.FINAL;
import static javax.lang.model.element.Modifier.PUBLIC;
import static javax.lang.model.element.Modifier.STATIC;

@AutoService(Processor.class)
@SupportedSourceVersion(SourceVersion.RELEASE_7)
@SupportedAnnotationTypes({"wz.com.annotationlib.MyClass.DIActivity"})
public class ByProcessor extends AbstractProcessor {

    private Elements elementUtils;

    private Types typeUtils;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        elementUtils = processingEnvironment.getElementUtils();
        typeUtils = processingEnvironment.getTypeUtils();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        if (set != null) {
            Set<? extends Element> elementsAnnotatedWith = roundEnvironment.getElementsAnnotatedWith(MyClass.DIActivity.class);
            if (elementsAnnotatedWith != null) {//获取设置DIActivity 注解的节点
                //判断注解的节点是否为Activity
                TypeElement typeElement = elementUtils.getTypeElement("android.app.Activity");
                for (Element element : elementsAnnotatedWith) {

                    TypeMirror typeMirror = element.asType();//获取注解节点的类的信息
                    MyClass.DIActivity annotation = element.getAnnotation(MyClass.DIActivity.class);//获取注解的信息


                    if (typeUtils.isSubtype(typeMirror, typeElement.asType())) {//注解在Activity的类上面

                        TypeElement classElement = (TypeElement) element;//获取节点的具体类型

                        //创建参数  Map<String,Class<? extends IRouteGroup>>> routes
                        ParameterSpec altlas = ParameterSpec
                                .builder(ClassName.get(typeMirror), "activity")//参数名
                                .build();

                        //创建方法
                        MethodSpec.Builder method = MethodSpec.methodBuilder
                                ("findById")
//                                .addAnnotation(Override.class)
                                .addModifiers(PUBLIC, STATIC)
                                .returns(TypeName.VOID)
                                .addParameter(altlas);

                        //创建函数体
//获取TypeElement的所有成员变量和成员方法
                        List<? extends Element> allMembers = elementUtils.getAllMembers(classElement);//??

                        //遍历成员变量
                        for (Element member : allMembers) {
                            //找到被BYView注解的成员变量
                            MyClass.BYView byView = member.getAnnotation(MyClass.BYView.class);
                            if (byView == null) {
                                continue;
                            }
                            //构建函数体
                            method.addStatement(String.format("activity.%s = (%s) activity.findViewById(%s)",
                                    member.getSimpleName(),//注解节点变量的名称
                                    ClassName.get(member.asType()).toString(),//注解节点变量的类型
                                    byView.value()));//注解的值
                        }

                        //创建类
                        TypeSpec typeSpec = TypeSpec.classBuilder("ManagerFindBy" + element.getSimpleName())
                                .addModifiers(PUBLIC, FINAL)//作用域
                                .addMethod(method.build())//添加方法
                                .build();
                        //创建Javaclass 文件

                        JavaFile javaFile = JavaFile.builder("com.prim.find.by", typeSpec).build();
                        try {
                            javaFile.writeTo(processingEnv.getFiler());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    } else {
                        throw new IllegalArgumentException("@DIActivity must of Activity");
                    }
                }

            }
            return true;
        }
        return false;
    }
}
