package com.clone.OneC.generate_code;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;

import javax.lang.model.element.Modifier;
import jakarta.persistence.Entity;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GenerateEntity {

    private final String nameEntity;

    private final String nameProject;

    private final  String pathForCreateDirectory;

    private final String pathForCreateJavaClass;

    private final List<MethodSpec> methodSpecs = new ArrayList<>();

    private final  List<AnnotationSpec> annotationSpecs = new ArrayList<>();

    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject, List<MethodSpec> methodSpecs, List<AnnotationSpec> annotationSpecs) {
        this.nameEntity = nameEntity;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;
        this.pathForCreateJavaClass = pathBeforeProject + nameProject + "/src/main/java/";
        this.setMethodSpecs(methodSpecs);
        this.setAnnotationSpecs(annotationSpecs);
    }

    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject) {
        this.nameEntity = nameEntity;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;
        this.pathForCreateJavaClass = pathBeforeProject + nameProject + "/src/main/java/";
    }

    public void setMethodSpec (MethodSpec methodSpec) {
        this.methodSpecs.add(methodSpec);
    }

    public void setMethodSpecs (List<MethodSpec> methodSpecs) {
        this.methodSpecs.addAll(methodSpecs);
    }

    public void setAnnotationSpecs(List<AnnotationSpec> annotationSpecs) {
        this.annotationSpecs.addAll(annotationSpecs);
    }

    public void setAnnotationSpec(AnnotationSpec annotationSpec) {
        this.annotationSpecs.add(annotationSpec);
    }

    private void createEntity() throws IOException {
        // создание пути для создание папки
        Path path = Paths.get(this.pathForCreateDirectory);

        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }

        TypeSpec someEntity = TypeSpec.classBuilder(this.nameEntity)
                        .addModifiers(Modifier.PUBLIC)
                        .addAnnotation(Entity.class)
                        .build();

        JavaFile javaFile = JavaFile.builder("com.example." + this.nameProject + ".entity", someEntity).build();

        javaFile.writeTo(System.out);
        // путь до контроллера
        path = Paths.get("./src/main/resources/projects/" + this.nameProject + "/src/main/java/");

        javaFile.writeTo(path);
    }

    public void build() throws IOException {
        createEntity();
    }
}
