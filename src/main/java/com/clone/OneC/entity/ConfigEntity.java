package com.clone.OneC.entity;

import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeName;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ConfigEntity extends BaseEntity {
    private String nameEntity;
    private String nameProject;
    private String pathBeforeProject;
    private String packageName;
    private Set<String> fieldsName;
    private List<String> types;
    private String relationshipName;
    private String relationshipType;
}
