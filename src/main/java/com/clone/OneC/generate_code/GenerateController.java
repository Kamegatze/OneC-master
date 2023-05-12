package com.clone.OneC.generate_code;

import com.squareup.javapoet.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class GenerateController extends GenerateClass {
    private List<MethodSpec> methodSpecs = new ArrayList<>();


    public GenerateController(String nameClass, String nameProject, String pathBeforeProject, List<MethodSpec> methodSpecs) {
        super(nameClass, nameProject, pathBeforeProject);
        this.methodSpecs = methodSpecs;
    }

    public GenerateController(String nameClass, String nameProject, String pathBeforeProject) {
        super(nameClass, nameProject, pathBeforeProject);
    }

    public void setMethodSpecs(MethodSpec methodSpec) {
        this.methodSpecs.add(methodSpec);
    }

    public void setMethodSpecs(List<MethodSpec> methodSpecs) {
        this.methodSpecs.addAll(methodSpecs);
    }

    @Override
    protected void toCreate() throws IOException {
        // создание пути для создание папки
        Path path = Paths.get(this.pathForCreateDirectory);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        AnnotationSpec requestMapping = AnnotationSpec.builder(RequestMapping.class).
                addMember("value", "\"/" + this.nameClass + "\"").build();

        TypeSpec someController = TypeSpec.classBuilder(this.nameClass).addModifiers(Modifier.PUBLIC).
                addAnnotation(RestController.class).
                addAnnotation(requestMapping).
                addMethods(this.methodSpecs).
                build();

        JavaFile javaFile = JavaFile.builder("com.example." + this.nameProject + ".controllers", someController)
                .indent("    ")
                .build();

        //javaFile.writeTo(System.out);
        // путь до контроллера
        path = Paths.get("./src/main/resources/projects/" + this.nameProject + "/src/main/java/");

        javaFile.writeTo(path);
    }

}
