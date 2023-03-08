package com.clone.OneC.generate_code;

import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.lang.model.element.Modifier;
import java.lang.reflect.Type;
import java.util.List;
@Component
public class GenerateMethod {
    private MethodSpec getMethod(String nameMethod, Modifier modifier, Type typeReturn, String codeOfMethod, String pathRequest, List<ParameterSpec> parameterSpecs) {
        AnnotationSpec getMapping = AnnotationSpec.builder(GetMapping.class).
                addMember("value", "\"" + pathRequest + "\"").
                build();

        MethodSpec methodGet = MethodSpec.methodBuilder(nameMethod).
                returns(typeReturn).
                addModifiers(modifier).
                addAnnotation(getMapping).
                addCode(codeOfMethod).
                addParameters(parameterSpecs).
                build();

        return methodGet;
    }
    private MethodSpec postMethod(String nameMethod, Modifier modifier, String codeOfMethod, String pathRequest, List<ParameterSpec> parameterSpecs) {
        AnnotationSpec postMapping = AnnotationSpec.builder(PostMapping.class).
                addMember("value", "\"" + pathRequest + "\"").
                build();

        MethodSpec methodPost = MethodSpec.methodBuilder(nameMethod).
                returns(void.class).
                addModifiers(modifier).
                addAnnotation(postMapping).
                addCode(codeOfMethod).
                addParameters(parameterSpecs).
                build();

        return methodPost;
    }

    public MethodSpec build(String nameMethod, Modifier modifier,
                            String codeOfMethod, String pathRequest,
                            List<ParameterSpec> parameterSpecs, Type typeReturn,
                            String typeMethod) throws Exception {
        if(typeMethod.equals("GET")) {
            return getMethod(nameMethod, modifier, typeReturn, codeOfMethod, pathRequest, parameterSpecs);
        }
        else if(typeMethod.equals("POST")) {
            return postMethod(nameMethod, modifier, codeOfMethod, pathRequest, parameterSpecs);
        }
        throw new Exception("Incorrect parameter entered");
    }
}
