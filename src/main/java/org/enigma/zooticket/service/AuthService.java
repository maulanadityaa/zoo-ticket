package org.enigma.zooticket.service;

import org.enigma.zooticket.model.request.AuthRequest;
import org.enigma.zooticket.model.response.LoginResponse;
import org.enigma.zooticket.model.response.RegisterResponse;

public interface AuthService {
    RegisterResponse register(AuthRequest authRequest);

    RegisterResponse registerAdmin(AuthRequest authRequest);

    LoginResponse login(AuthRequest authRequest);
}
