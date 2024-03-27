package org.enigma.zooticket.controller;

import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.AppPath;
import org.enigma.zooticket.model.request.AuthRequest;
import org.enigma.zooticket.model.response.CommonResponse;
import org.enigma.zooticket.model.response.LoginResponse;
import org.enigma.zooticket.model.response.RegisterResponse;
import org.enigma.zooticket.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(AppPath.AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping(AppPath.SIGNUP)
    public ResponseEntity<?> register(@RequestBody AuthRequest authRequest) {
        RegisterResponse registerResponse = authService.register(authRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(CommonResponse.<RegisterResponse>builder()
                        .statusCode(HttpStatus.CREATED.value())
                        .message("User signed up successfully")
                        .data(registerResponse)
                        .build()
                );
    }

    @PostMapping(AppPath.SIGNIN)
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        LoginResponse loginResponse = authService.login(authRequest);

        return ResponseEntity.status(HttpStatus.OK)
                .body(CommonResponse.<LoginResponse>builder()
                        .statusCode(HttpStatus.OK.value())
                        .message("Login successful")
                        .data(loginResponse)
                        .build()
                );
    }
}
