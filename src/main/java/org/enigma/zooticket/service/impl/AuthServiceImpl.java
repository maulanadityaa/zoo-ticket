package org.enigma.zooticket.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.enigma.zooticket.constant.ERole;
import org.enigma.zooticket.constant.EStatus;
import org.enigma.zooticket.model.entity.Admin;
import org.enigma.zooticket.model.entity.AppUser;
import org.enigma.zooticket.model.entity.Customer;
import org.enigma.zooticket.model.entity.Role;
import org.enigma.zooticket.model.entity.User;
import org.enigma.zooticket.model.request.AuthRequest;
import org.enigma.zooticket.model.request.CustomerRequest;
import org.enigma.zooticket.model.response.LoginResponse;
import org.enigma.zooticket.model.response.RegisterResponse;
import org.enigma.zooticket.repository.AdminRepository;
import org.enigma.zooticket.repository.UserRepository;
import org.enigma.zooticket.security.JwtUtil;
import org.enigma.zooticket.service.AuthService;
import org.enigma.zooticket.service.CustomerService;
import org.enigma.zooticket.service.RoleService;
import org.enigma.zooticket.util.Helper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CustomerService customerService;
    private final RoleService roleService;
    private final AdminRepository adminRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse register(AuthRequest authRequest) {
        try {
            Role role = roleService.getOrSave(authRequest.getRole());

            User user = User.builder()
                    .username(authRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userRepository.saveAndFlush(user);


            if (role.getName() == ERole.ROLE_CUSTOMER) {
//                Customer customer = Customer.builder()
////                        .fullName(authRequest.getFullName())
////                        .email(authRequest.getEmail())
////                        .mobilePhone(authRequest.getMobilePhone())
////                        .dob(Helper.stringToDate(authRequest.getDateOfBirth()))
//                        .user(user)
//                        .build();
                CustomerRequest customerRequest = CustomerRequest.builder()
                        .fullName(authRequest.getFullName())
                        .phone(authRequest.getMobilePhone())
                        .email(authRequest.getEmail())
                        .dateOfBirth(authRequest.getDateOfBirth())
                        .user(user)
                        .build();
                customerService.create(customerRequest);
            } else if (role.getName() == ERole.ROLE_ADMIN) {
                Admin admin = Admin.builder()
                        .name(authRequest.getFullName())
                        .mobilePhone(authRequest.getMobilePhone())
                        .user(user)
                        .status(EStatus.ACTIVE)
                        .build();
                adminRepository.save(admin);
            }

            return RegisterResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Transactional(rollbackOn = Exception.class)
    @Override
    public RegisterResponse registerAdmin(AuthRequest authRequest) {
        try {
            Role role = roleService.getOrSave(ERole.ROLE_ADMIN);

            User user = User.builder()
                    .username(authRequest.getUsername().toLowerCase())
                    .password(passwordEncoder.encode(authRequest.getPassword()))
                    .role(role)
                    .build();
            userRepository.saveAndFlush(user);

            Admin admin = Admin.builder()
                    .name(authRequest.getFullName())
                    .mobilePhone(authRequest.getMobilePhone())
                    .user(user)
                    .status(EStatus.ACTIVE)
                    .build();
            adminRepository.save(admin);

            return RegisterResponse.builder()
                    .username(user.getUsername())
                    .role(user.getRole().getName().toString())
                    .build();
        } catch (DataIntegrityViolationException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public LoginResponse login(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getUsername().toLowerCase(),
                authRequest.getPassword()
        ));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        AppUser appUser = (AppUser) authentication.getPrincipal();
        String token;
        token = jwtUtil.generateToken(appUser);

        return LoginResponse.builder()
                .token(token)
                .role(appUser.getRole().name())
                .build();
    }
}
