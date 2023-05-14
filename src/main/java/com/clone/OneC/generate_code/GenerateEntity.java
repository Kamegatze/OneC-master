package com.clone.OneC.generate_code;

import com.squareup.javapoet.*;
import jakarta.persistence.*;
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

    private JavaFile javaFile;

    private final Map<String, TypeName> fields = new LinkedHashMap<>(Map.of("id", TypeName.INT));


    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject, String packageName,
                          Map<String, TypeName> fields) {
        super(nameEntity, nameProject, pathBeforeProject, packageName);
        this.setFields(fields);
    }

    public GenerateEntity(String nameEntity, String nameProject,
                          String pathBeforeProject, String packageName) {
        super(nameEntity, nameProject, pathBeforeProject, packageName);
    }




    public void setFields(Map<String, TypeName> fields) {
        this.fields.putAll(fields);
    }

    public void setField(TypeName type, String fields) {
        this.fields.put(fields, type);
    }

    @Override
    protected void toCreate() throws IOException {

        // создание пути для создание папки
        Path path = Paths.get(this.pathForCreateDirectory);

        if(!Files.exists(path)) {
            Files.createDirectory(path);
        }

        Set<String> keys = this.fields.keySet();

        List<FieldSpec> fieldSpecs = new ArrayList<>();

        //генерация полей
        for (String name : keys) {

            if (name.equals("id")) {
                FieldSpec fieldSpec = FieldSpec.builder(this.fields.get(name), name, Modifier.PRIVATE)
                        .addAnnotation(Id.class)
                        .addAnnotation(Setter.class)
                        .addAnnotation(
                                AnnotationSpec.builder(Column.class)
                                    .addMember("name", "$S", name)
                                    .build()
                        )
                        .addAnnotation(
                                AnnotationSpec.builder(GeneratedValue.class)
                                        .addMember(
                                                "strategy",
                                                CodeBlock.builder()
                                                        .add("GenerationType.IDENTITY")
                                                        .build()
                                        )
                                        .build())
                        .build();
                fieldSpecs.add(fieldSpec);
            }
            else {
                FieldSpec fieldSpec = FieldSpec.builder(this.fields.get(name), name, Modifier.PRIVATE)
                        .addAnnotation(AnnotationSpec.builder(Column.class)
                                .addMember("name", "$S", name)
                                .build()
                        )
                        .addAnnotation(Setter.class)
                        .build();
                fieldSpecs.add(fieldSpec);
            }
        }

        //генерация класса entity
        TypeSpec someEntity = generateClass(this.nameClass, fieldSpecs, this.relationship);

        //создание java файла
        this.javaFile = JavaFile.builder(this.packageName + "." + this.nameProject + ".entity", someEntity)
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

    public String packageToJavaFile() {
        return this.javaFile.packageName;
    }
}
