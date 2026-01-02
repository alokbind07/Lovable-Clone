package com.alokbind.projects.lovable_clone.service;

import com.alokbind.projects.lovable_clone.dto.auth.AuthResponse;
import com.alokbind.projects.lovable_clone.dto.auth.LoginRequest;
import com.alokbind.projects.lovable_clone.dto.auth.SignupRequest;

public interface AuthService {

    AuthResponse signup(SignupRequest request);

    AuthResponse login(LoginRequest request);
}
