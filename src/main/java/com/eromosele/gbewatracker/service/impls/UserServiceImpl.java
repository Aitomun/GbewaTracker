package com.eromosele.gbewatracker.service.impls;

import com.eromosele.gbewatracker.config.JwtService;
import com.eromosele.gbewatracker.dto.*;
import com.eromosele.gbewatracker.entity.ConfirmationToken;
import com.eromosele.gbewatracker.entity.UserEntity;
import com.eromosele.gbewatracker.enums.Role;
import com.eromosele.gbewatracker.exception.EmailAlreadyExistException;
import com.eromosele.gbewatracker.repository.ConfirmationTokenRepository;
import com.eromosele.gbewatracker.repository.UserRepository;
import com.eromosele.gbewatracker.service.EmailService;
import com.eromosele.gbewatracker.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Override
    public void registerUser(UserRegistrationRequest registrationRequest) {
    if(!registrationRequest.getPassword().equals(registrationRequest.getConfirmPassword())){
        throw new IllegalArgumentException("Passwords do not match");
    }
    Optional<UserEntity> existingUser = userRepository.findByEmail(registrationRequest.getEmail());

    if(existingUser.isPresent()){
        throw new EmailAlreadyExistException("Email already exists. Login to your account");
    }
        UserEntity user = UserEntity.builder()
                .firstName(registrationRequest.getFirstName())
                .lastName(registrationRequest.getLastName())
                .email(registrationRequest.getEmail())
                .phoneNumber(registrationRequest.getPhoneNumber())
                .password(passwordEncoder.encode(registrationRequest.getPassword()))
                .role(Role.USER)
                .build();

        UserEntity savedUser = userRepository.save(user);

        ConfirmationToken confirmationToken = new ConfirmationToken(savedUser);
        confirmationTokenRepository.save(confirmationToken);

        String confirmationUrl = "http://localhost:8080/api/auth/confirm?token=" + confirmationToken.getToken();
        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(savedUser.getEmail())
                .subject("ACCOUNT CREATION")
                .messageBody("CONGRATULATIONS!!! Your User Account Has Been Successfully Created.\n"
                + "Your Account Details: \n" + "Account FullName: " + savedUser.getFirstName() + " \n"
                + "Confirm your email " +
                        "Please click the link to confirm your registration: "+ confirmationUrl)
                .build();
        emailService.sendEmail(emailDetails);

    }

    @Override
    public LoginResponse loginUser(LoginRequestDto loginRequestDto){
           authenticationManager.authenticate(
                   new UsernamePasswordAuthenticationToken(
                           loginRequestDto.getEmail(),
                           loginRequestDto.getPassword()
                   )
           );
           UserEntity user = userRepository.findByEmail(loginRequestDto.getEmail()).orElseThrow();
           var jwtToken = jwtService.generateToken(user);
           return LoginResponse.builder()
                   .responseCode("002")
                   .responseMessage("Login Successfully")
                   .loginInfo(LoginInfo.builder()
                           .email(user.getEmail())
                           .token(jwtToken)
                           .build()
                   )
                   .build();
    }

}
