package com.clone.OneC.generate_code;

import com.squareup.javapoet.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.lang.model.element.Modifier;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
@Component
public class GenerateEntity {

    private final String nameEntity;

    private final String nameProject;

    private final  String pathForCreateDirectory;

    private final String pathForCreateJavaClass;

    private final List<MethodSpec> methodSpecs = new ArrayList<>();

    private final  List<AnnotationSpec> annotationSpecs = new ArrayList<>();

    @Getter
    @Setter
    private FieldSpec relationship;

    private final Map<TypeName, String> fields = new LinkedHashMap<>();


    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject, List<MethodSpec> methodSpecs, List<AnnotationSpec> annotationSpecs,
                          HashMap<TypeName, String> fields) {
        this.nameEntity = nameEntity;
        this.nameProject = nameProject;
        this.pathForCreateDirectory = pathBeforeProject + nameProject + "/src/main/java/com/example/" + nameProject;
        this.pathForCreateJavaClass = pathBeforeProject + nameProject + "/src/main/java/";
        this.setMethodSpecs(methodSpecs);
        this.setAnnotationSpecs(annotationSpecs);
        this.setFields(fields);
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

    public void setFields(HashMap<TypeName, String> fields) {
        this.fields.putAll(fields);
    }

    public void setField(TypeName type, String fields) {
        this.fields.put(type, fields);
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

        Set<TypeName> keys = this.fields.keySet();

        List<FieldSpec> fieldSpecs = new ArrayList<>();

        //генерация полей

        for (TypeName type : keys) {
            FieldSpec fieldSpec = FieldSpec.builder(type, this.fields.get(type), Modifier.PRIVATE)
                    .addAnnotation(AnnotationSpec.builder(Column.class)
                            .addMember("name", "$S", this.fields.get(type))
                            .build()
                    )
                    .addAnnotation(Setter.class)
                    .build();
            fieldSpecs.add(fieldSpec);
        }
        //генерация класса entity
        TypeSpec someEntity = generateClass(this.nameEntity, fieldSpecs, this.relationship);

        //создание java файла
        JavaFile javaFile = JavaFile.builder("com.example." + this.nameProject + ".entity", someEntity)
                .indent("    ")
                .build();

        // путь до контроллера
        path = Paths.get("./src/main/resources/projects/" + this.nameProject + "/src/main/java/");

        javaFile.writeTo(path);
    }


    private TypeSpec generateClass(String nameEntity, List<FieldSpec> fieldSpecs, FieldSpec relationship) {
        if (relationship != null) {
            return TypeSpec.classBuilder(nameEntity)
                    .addMethod(
                            MethodSpec.constructorBuilder()
                                    .addModifiers(Modifier.PUBLIC)
                                    .build()
                    )
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(Entity.class)
                    .addAnnotation(
                            AnnotationSpec.builder(Table.class)
                                    .addMember("name", "$S", nameEntity)
                                    .build()
                    )
                    .addAnnotation(Getter.class)
                    .addFields(fieldSpecs)
                    .addField(relationship)
                    .build();
        }
        return TypeSpec.classBuilder(nameEntity)
                .addMethod(
                        MethodSpec.constructorBuilder()
                                .addModifiers(Modifier.PUBLIC)
                                .build()
                )
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(Entity.class)
                .addAnnotation(
                        AnnotationSpec.builder(Table.class)
                                .addMember("name", "$S", nameEntity)
                                .build()
                )
                .addAnnotation(Getter.class)
                .addFields(fieldSpecs)
                .build();
    }
    public void build() throws IOException {
        createEntity();
    }
}
