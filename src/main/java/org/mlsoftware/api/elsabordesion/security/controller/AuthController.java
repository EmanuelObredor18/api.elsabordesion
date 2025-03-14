package org.mlsoftware.api.elsabordesion.security.controller;

import org.mlsoftware.api.elsabordesion.security.data.dto.input.UserInputDTO;
import org.mlsoftware.api.elsabordesion.security.service.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sabor-de-sion/api/auth")
public class AuthController {

    @Autowired
    private UserDetailsServiceImpl service;

    @PostMapping("/register")
    public String register(@RequestBody UserInputDTO userDTO) {
        return service.save(userDTO);
    }

}
