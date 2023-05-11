package com.clone.OneC.generate_code;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;

import java.util.ArrayList;
import java.util.List;

public abstract class GenerateClass {
    private String nameClass;

    private String nameProject;

    private  String pathForCreateDirectory;

    private  String pathForCreateJavaClass;

    private List<MethodSpec> methodSpecs = new ArrayList<>();

    private List<AnnotationSpec> annotationSpecs = new ArrayList<>();


}
