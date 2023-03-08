package com.clone.OneC.controller;

import com.clone.OneC.entity.ConfigControllers;
import com.clone.OneC.entity.ConfigMethod;
import com.clone.OneC.service.CreateControllerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/add_controllers")
public class AddControllersInProjectController {

    private CreateControllerService createControllerService;



    @PostMapping("/create_controller")
    public ResponseEntity createController(@RequestBody ConfigControllers configControllers) {
        this.createControllerService = new CreateControllerService(configControllers);

        return ResponseEntity.ok("ok");
    }

    @PostMapping("/create_method")
    public ResponseEntity createMethod(@RequestBody ConfigMethod configMethod) throws Exception {
        this.createControllerService.addMethod(configMethod);
        return ResponseEntity.ok("ok");
    }

    @PostMapping("/build")
    public ResponseEntity buildController() throws IOException {
        this.createControllerService.buildController();
        this.createControllerService = null;
        return ResponseEntity.ok("ok");
    }
}
