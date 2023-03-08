package com.clone.OneC.generate_code;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
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
public class GenerateController {
    private final String nameController;
    private final String nameProject;
    private final String pathForCreateDirectory;
    private final String pathForCreateJavaClass;
    private List<MethodSpec> methodSpecs = new ArrayList<>();


    public GenerateController(String nameController, String nameProject, String pathBeforeProject, List<MethodSpec> methodSpecs) {
        this.nameController = nameController;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;
        this.pathForCreateJavaClass = pathBeforeProject + nameProject + "/src/main/java/";
        this.methodSpecs = methodSpecs;
    }

    public GenerateController(String nameController, String nameProject, String pathBeforeProject) {
        this.nameController = nameController;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;
        this.pathForCreateJavaClass = pathBeforeProject + nameProject + "/src/main/java/";
    }

    public void setMethodSpecs(MethodSpec methodSpec) {
        this.methodSpecs.add(methodSpec);
    }

    public void setMethodSpecs(List<MethodSpec> methodSpecs) {
        this.methodSpecs.addAll(methodSpecs);
    }

    private void createSomeController() throws IOException {
        // создание пути для создание папки
        Path path = Paths.get(this.pathForCreateDirectory);

        if (!Files.exists(path)) {
            Files.createDirectory(path);
        }

        AnnotationSpec requestMapping = AnnotationSpec.builder(RequestMapping.class).
                addMember("value", "\"/" + this.nameController + "\"").build();

        TypeSpec someController = TypeSpec.classBuilder(this.nameController).addModifiers(Modifier.PUBLIC).
                addAnnotation(RestController.class).
                addAnnotation(requestMapping).
                addMethods(this.methodSpecs).
                build();

        JavaFile javaFile = JavaFile.builder("com.example." + this.nameProject + ".controllers", someController).build();

        javaFile.writeTo(System.out);
        // путь до контроллера
        path = Paths.get("./src/main/resources/projects/" + this.nameProject + "/src/main/java/");

        javaFile.writeTo(path);
    }


    public void build() throws IOException {
        createSomeController();
    }
}
