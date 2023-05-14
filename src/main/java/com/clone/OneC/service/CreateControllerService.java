package com.clone.OneC.service;

import com.clone.OneC.dto.ConfigMethodDTO;
import com.clone.OneC.entity.ConfigControllers;
import com.clone.OneC.generate_code.GenerateController;
import com.clone.OneC.generate_code.gnerate_method_controller.CreateMethod;
import com.clone.OneC.generate_code.gnerate_method_controller.FactoryCreateMethod;
import com.clone.OneC.generate_code.gnerate_method_controller.TypeMethod;
import com.squareup.javapoet.*;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.*;

@Service
public class CreateControllerService {

    private GenerateController generateController;


    private Map<String, TypeName> typeValues = new LinkedHashMap<>(Map.of("String", TypeName.get(String.class),
            "PathVariable", TypeName.get(PathVariable.class), "RequestParam", TypeName.get(RequestParam.class),
            "RequestBody", TypeName.get(RequestBody.class), "Integer", TypeName.INT, "Long", TypeName.LONG,
            "void", TypeName.VOID));


    public void setTypeValues(String name, TypeName type) {
        typeValues.put(name, type);
    }
    public void setGenerateControllerAndSetGenerateMethod(ConfigControllers configControllers) {
        this.generateController = new GenerateController(configControllers.getNameController(),
                configControllers.getNameProject(),
                "./src/main/resources/projects/", configControllers.getPackageName());
    }

    public GenerateController createController() {
        return generateController;
    }

    public void addMethod(ConfigMethodDTO configMethod) throws Exception {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();

        if (configMethod.getArgs() != null) {
            Set<String> argsName = configMethod.getArgs().keySet();

            for (String name: argsName) {
                parameterSpecs.add(
                        ParameterSpec.builder(typeValues.get(configMethod.getArgs().get(name)), name)
                                .addAnnotation((ClassName) typeValues.get(configMethod.getTypeAnnotation().get(name)))
                                .build()
                );
            }

        }
        TypeMethod typeMethod = TypeMethod.valueOf(configMethod.getTypeMethod());


        CreateMethod method = new FactoryCreateMethod().createTo(typeMethod);

        generateController.setMethodSpecs(method.createTo(configMethod.getNameMethod(),
                typeValues.get(configMethod.getReturnValue()), configMethod.getPathRequest(),
                parameterSpecs.toArray(new ParameterSpec[0])));
    }

    public void buildController() throws IOException {
        this.generateController.build();
    }
}
