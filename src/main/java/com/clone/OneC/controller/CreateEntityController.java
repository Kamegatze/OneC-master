package com.clone.OneC.controller;

import com.clone.OneC.dto.ConfigDTO;
import com.clone.OneC.dto.ConfigEntityDTO;
import com.clone.OneC.entity.ConfigEntity;
import com.clone.OneC.generate_code.GenerateEntity;
import com.clone.OneC.service.CreateControllerService;
import com.clone.OneC.service.CreateEntityService;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/create_entity")
public class CreateEntityController {
    @Autowired
    private CreateEntityService createEntityService;
    @Autowired
    private CreateControllerService createControllerService;
    @PostMapping()
    public void createEntity(@RequestBody ConfigEntityDTO configEntity) throws IOException {
        GenerateEntity entity = createEntityService.createAndAddEntities(configEntity);
        ClassName className = ClassName.get(entity.packageToJavaFile(), entity.getNameClass());
        createControllerService.setTypeValues(entity.getNameClass(), className);
    }

    @GetMapping("/create_one_to_one")
    public void createEntityOneToOne(@RequestParam("nameOne") String nameOne, @RequestParam("nameTwo") String nameTwo) throws IOException {
        createEntityService.createToOneToOne(nameOne, nameTwo);
    }

    @GetMapping("/create_many_to_many")
    public void createEntityManyToMany(@RequestParam("nameOne") String nameOne, @RequestParam("nameTwo") String nameTwo) throws IOException {
        createEntityService.createToManyToMany(nameOne, nameTwo);
    }

    @GetMapping("/create_one_to_many")
    public void createEntityOneToMany(@RequestParam("nameOne") String nameOne, @RequestParam("nameTwo") String nameTwo) throws IOException {
        createEntityService.createToOneToMany(nameOne, nameTwo);
    }

    @GetMapping("/create_many_to_one")
    public void createEntityManyToOne(@RequestParam("nameOne") String nameOne, @RequestParam("nameTwo") String nameTwo) throws IOException {
        createEntityService.createToOneToMany(nameTwo, nameOne);
    }
}
