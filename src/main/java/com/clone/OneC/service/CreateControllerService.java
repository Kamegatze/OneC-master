package com.clone.OneC.service;

import com.clone.OneC.entity.ConfigControllers;
import com.clone.OneC.entity.ConfigMethod;
import com.clone.OneC.generate_code.GenerateController;
import com.clone.OneC.generate_code.GenerateMethod;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import org.springframework.stereotype.Service;

import javax.lang.model.element.Modifier;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class CreateControllerService {

    private GenerateController generateController;

    private GenerateMethod generateMethod;

    public CreateControllerService(ConfigControllers configControllers) {
        this.generateController = new GenerateController(configControllers.getNameController(),
                configControllers.getNameProject(),
                "./src/main/resources/projects/");
        this.generateMethod = new GenerateMethod();
    }

    public GenerateController createController() {
        return generateController;
    }

    public void addMethod(ConfigMethod configMethod) throws Exception {
        List<ParameterSpec> parameterSpecs = new ArrayList<>();
        if (configMethod.getKeyParameterSpec() != null && configMethod.getPathPackage() != null) {
            //получение набора ключей, которые являются уникальными заначениями аргументов
            Set<String> nameArg = configMethod.getKeyParameterSpec();
            List<String> pathPackage = configMethod.getPathPackage();
            List<String> typeAnnotation = configMethod.getTypeAnnotation();

            int count = 0;

            for (String key: nameArg) {
                //создание параметров метода
                if(typeAnnotation.get(count).equals("")){
                    parameterSpecs.add(ParameterSpec.
                            builder(Class.forName(pathPackage.get(count)),
                            key).
                            build());
                }
                else {
                    parameterSpecs.add(ParameterSpec.
                            builder(Class.forName(pathPackage.get(count)),
                            key).
                            addAnnotation(Class.forName(typeAnnotation.get(count))).
                            build());
                }
                count++;
            }
        }

        MethodSpec someMethod;

        if (configMethod.getPathForClassName().equals("")) {
            someMethod = this.generateMethod.build(configMethod.getNameMethod(),
                    Modifier.PUBLIC, configMethod.getCodeOfMethod(), configMethod.getPathRequest(),
                    parameterSpecs, null ,configMethod.getTypeMethod());

            this.generateController.setMethodSpecs(someMethod);

            return;
        }

        someMethod = this.generateMethod.build(configMethod.getNameMethod(),
                Modifier.PUBLIC, configMethod.getCodeOfMethod(), configMethod.getPathRequest(),
                parameterSpecs, Class.forName(configMethod.getPathForClassName()) ,configMethod.getTypeMethod());

        this.generateController.setMethodSpecs(someMethod);
    }

    public void buildController() throws IOException {
        this.generateController.build();
    }
}
