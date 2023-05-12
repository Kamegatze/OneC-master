package com.clone.OneC.generate_code;

import com.squareup.javapoet.*;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
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

public class GenerateEntity extends GenerateClass {

    @Getter
    @Setter
    private FieldSpec relationship;

    private final Map<TypeName, String> fields = new LinkedHashMap<>();


    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject,
                          Map<TypeName, String> fields) {
        super(nameEntity, nameProject, pathBeforeProject);
        this.setFields(fields);
    }

    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject) {
        super(nameEntity, nameProject, pathBeforeProject);
    }




    public void setFields(Map<TypeName, String> fields) {
        this.fields.putAll(fields);
    }

    public void setField(TypeName type, String fields) {
        this.fields.put(type, fields);
    }

    @Override
    protected void toCreate() throws IOException {

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
        TypeSpec someEntity = generateClass(this.nameClass, fieldSpecs, this.relationship);

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
}
