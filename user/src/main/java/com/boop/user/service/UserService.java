package com.boop.user.service;

import org.springframework.stereotype.Service;

@Service
public class UserService {
    public String whoami() {
        return "anonymous";
    }
}
