package com.eromosele.gbewatracker.service;

import com.eromosele.gbewatracker.dto.*;
import com.eromosele.gbewatracker.entity.UserEntity;

public interface UserService {
   void registerUser(UserRegistrationRequest userRegistrationRequest);
    LoginResponse loginUser(LoginRequestDto loginRequestDto);

}
