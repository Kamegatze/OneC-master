package com.clone.OneC.entity;

import com.squareup.javapoet.ParameterSpec;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigMethod extends BaseEntity{
    private String nameMethod;
    private String pathForClassName;
    private String codeOfMethod;
    private String pathRequest;
    private Set<String> keyParameterSpec;
    private List<String> pathPackage;
    private String typeMethod;
    private List<String> typeAnnotation;
}
