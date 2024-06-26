package com.eromosele.gbewatracker.service.impls;

import com.eromosele.gbewatracker.entity.ConfirmationToken;
import com.eromosele.gbewatracker.entity.UserEntity;
import com.eromosele.gbewatracker.repository.ConfirmationTokenRepository;
import com.eromosele.gbewatracker.repository.UserRepository;
import com.eromosele.gbewatracker.service.TokenValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TokenValidationServiceImpl implements TokenValidationService {
    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final UserRepository userModelRepository;


    @Override
    public String validateToken(String token) {

        Optional<ConfirmationToken> confirmationTokenOptional = confirmationTokenRepository.findByToken(token);
        if (confirmationTokenOptional.isEmpty()) {
            return "Invalid token";
        }

        ConfirmationToken confirmationToken = confirmationTokenOptional.get();

        if (confirmationToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return "Token has expired";
        }

        UserEntity user = confirmationToken.getUser();
        user.setEnabled(true);
        userModelRepository.save(user);

        confirmationTokenRepository.delete(confirmationToken); //delete the token after successful verification

        return "Email confirmed successfully";

    }
}
