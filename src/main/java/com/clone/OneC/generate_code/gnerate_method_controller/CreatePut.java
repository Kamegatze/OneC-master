package com.clone.OneC.generate_code.gnerate_method_controller;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import org.springframework.web.bind.annotation.PutMapping;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

public class CreatePut implements CreateMethod{
    @Override
    public MethodSpec createTo(String methodName, TypeName typeReturn, String pathRequest, ParameterSpec[] parameterSpecs) {
        if(parameterSpecs.length == 0)
            return MethodSpec.methodBuilder(methodName)
                    .returns(typeReturn)
                    .addModifiers(Modifier.PUBLIC)
                    .addAnnotation(
                            AnnotationSpec.builder(PutMapping.class)
                                    .addMember("value", "\"" + pathRequest + "\"")
                                    .build()
                    )
                    .build();

        List<ParameterSpec> parameterSpecList = Arrays.asList(parameterSpecs);

        return MethodSpec.methodBuilder(methodName)
                .returns(typeReturn)
                .addModifiers(Modifier.PUBLIC)
                .addAnnotation(
                        AnnotationSpec.builder(PutMapping.class)
                                .addMember("value", "\"" + pathRequest + "\"")
                                .build()
                )
                .addParameters(parameterSpecList)
                .build();
    }
}
