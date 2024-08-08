package com.multipleDB.createmultipleDB.controller;

import com.multipleDB.createmultipleDB.dto.UsersDTO;
import com.multipleDB.createmultipleDB.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user")
@CrossOrigin(origins = "*",allowedHeaders = "*")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping
    public String registerCompany(@RequestBody UsersDTO usersDTO) {
        userService.save(usersDTO);
        return "success";
    }
}
