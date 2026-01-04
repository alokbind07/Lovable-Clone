package com.alokbind.projects.lovable_clone.service.impl;

import com.alokbind.projects.lovable_clone.dto.auth.UserProfileResponse;
import com.alokbind.projects.lovable_clone.service.UsageService;
import com.alokbind.projects.lovable_clone.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Override
    public UserProfileResponse getProfile(Long userId) {
        return null;
    }
}
