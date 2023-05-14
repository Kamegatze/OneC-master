package com.clone.OneC.generate_code.gnerate_method_controller;

import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import java.lang.reflect.Type;

public interface CreateMethod {
    public MethodSpec createTo(String methodName, TypeName typeReturn, String pathRequest, ParameterSpec[] parameterSpecs);
}
