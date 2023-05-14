package com.clone.OneC.service;

import com.clone.OneC.dto.ConfigEntityDTO;
import com.clone.OneC.entity.ConfigEntity;
import com.clone.OneC.generate_code.GenerateEntity;
import com.squareup.javapoet.*;
import jakarta.persistence.*;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.sql.Date;
import java.sql.Time;
import java.util.*;

@Service
public class CreateEntityService {

    private final Map<String, TypeName> types = Map.of("String", TypeName.get(String.class),
            "Integer", TypeName.INT, "Date", TypeName.get(Date.class), "Time", TypeName.get(Time.class),
            "Long", TypeName.LONG, "Byte", TypeName.BYTE);

    private final Map<String, GenerateEntity> entities = new LinkedHashMap<>();

    public GenerateEntity createAndAddEntities(ConfigEntityDTO configEntity) throws IOException {

        Set<String> keys = configEntity.getFields().keySet();
        Map <String, TypeName> fields = new LinkedHashMap<>();


        for (String name:keys) {

            String type = configEntity.getFields().get(name);

            TypeName trueType = this.types.get(type);

            fields.put(name, trueType);

        }

        GenerateEntity entity = new GenerateEntity(configEntity.getNameEntity(),
                configEntity.getNameProject(), "./src/main/resources/projects/", configEntity.getPackageName(),
                fields);

        entity.build();

        entities.put(configEntity.getNameEntity(), entity);

        return entity;
    }

    public void createToOneToOne(String nameOne, String nameTwo) throws IOException{
        //поиск объектов в map
        GenerateEntity entityOne = entities.get(nameOne);
        GenerateEntity entityTwo = entities.get(nameTwo);


        ClassName clazzTwo = ClassName.get(entityTwo.packageToJavaFile(), entityTwo.getNameClass());

        FieldSpec oneToEntityOne = FieldSpec.builder(clazzTwo, entityTwo.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(OneToOne.class)
                .addAnnotation(
                        AnnotationSpec.builder(JoinColumn.class)
                                .addMember("name", "$S", entityTwo.getNameClass().toLowerCase() + "_id")
                                .addMember("referencedColumnName", "$S", "id")
                                .build()
                )
                .build();


        entityOne.setRelationship(oneToEntityOne);

        ClassName clazzOne = ClassName.get(entityOne.packageToJavaFile(), entityOne.getNameClass());

        FieldSpec twoToEntityOne = FieldSpec.builder(clazzOne, entityOne.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(
                        AnnotationSpec.builder(OneToOne.class)
                                .addMember("mappedBy", "$S", entityTwo.getNameClass().toLowerCase())
                                .build()
                )
                .build();

        entityTwo.setRelationship(twoToEntityOne);

        entityOne.build();

        entityTwo.build();
    }
    public void createToManyToMany(String nameOne, String nameTwo) throws IOException {

        //поиск объектов в map
        GenerateEntity entityOne = this.entities.get(nameOne);
        GenerateEntity entityTwo = this.entities.get(nameTwo);

        //эквивалентин вызову Object.class
        ClassName clazzTwo = ClassName.get(entityTwo.packageToJavaFile(), entityTwo.getNameClass());

        // создания связи для первой сущности
        FieldSpec oneFieldToMany = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), clazzTwo),
                        entityTwo.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(ManyToMany.class)
                .addAnnotation(
                        AnnotationSpec.builder(JoinTable.class)
                                .addMember("name", "$S", entityOne.getNameClass().toLowerCase() + "_" + entityTwo.getNameClass().toLowerCase())
                                .addMember(
                                        "joinColumns",
                                        CodeBlock.builder()
                                                .add("@$T(name = \"" + entityOne.getNameClass().toLowerCase() + "_id\")", JoinColumn.class)
                                                .build()
                                )
                                .addMember(
                                        "inverseJoinColumns",
                                        CodeBlock.builder()
                                                .add("@$T(name = \"" + entityTwo.getNameClass().toLowerCase() + "_id\")", JoinColumn.class)
                                                .build()
                                )
                                .build()
                )
                .build();

        ClassName clazzOne = ClassName.get(entityOne.packageToJavaFile(), entityOne.getNameClass());


        //создание связи для второй сущности
        FieldSpec twoFieldToMany = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), clazzOne),
                entityOne.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(
                        AnnotationSpec.builder(ManyToMany.class)
                                .addMember("mappedBy", "$S", entityOne.getNameClass().toLowerCase())
                                .build()
                )
                .build();
        //добавление связи
        entityOne.setRelationship(oneFieldToMany);
        entityTwo.setRelationship(twoFieldToMany);
        //перезапись файла
        entityOne.build();
        entityTwo.build();
    }

    public void createToOneToMany(String nameOne, String nameTwo) throws IOException {
        //поиск генераторов
        GenerateEntity entityOne = this.entities.get(nameOne);
        GenerateEntity entityTwo = this.entities.get(nameTwo);

        ClassName clazzTwo = ClassName.get(entityTwo.packageToJavaFile(), entityTwo.getNameClass());
        //создание связи one-to-many в виде листа
        FieldSpec oneFieldToMany = FieldSpec.builder(ParameterizedTypeName.get(ClassName.get(List.class), clazzTwo),
                entityTwo.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(
                        AnnotationSpec.builder(OneToMany.class)
                                .addMember("mappedBy", "$S", entityTwo.getNameClass().toLowerCase())
                                .build()
                )
                .build();

        ClassName clazzOne = ClassName.get(entityOne.packageToJavaFile(), entityOne.getNameClass());


        //создания связи many-to-one
        FieldSpec twoFieldToOne = FieldSpec.builder(clazzOne,
                entityOne.getNameClass().toLowerCase(), Modifier.PRIVATE)
                .addAnnotation(ManyToOne.class)
                .addAnnotation(
                        AnnotationSpec.builder(JoinColumn.class)
                                .addMember("name", "$S", entityOne.getNameClass().toLowerCase() + "_id")
                                .addMember("nullable", "$S", "false")
                                .build()
                )
                .build();

        entityOne.setRelationship(oneFieldToMany);
        entityTwo.setRelationship(twoFieldToOne);

        entityOne.build();
        entityTwo.build();
    }


}
