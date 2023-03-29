package com.slyadz.misy2.userssevice.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.slyadz.misy2.usersservice.api.event.UserVerificationFailedEventData;
import com.slyadz.misy2.usersservice.api.event.UserVerificationSuccessEventData;
import com.slyadz.misy2.userssevice.kafka.KafkaSenderService;
import com.slyadz.misy2.userssevice.model.User;
import com.slyadz.misy2.userssevice.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final KafkaSenderService kafkaSenderService;

    public UserService(UserRepository userRepository, KafkaSenderService kafkaSenderService) {
        this.userRepository = userRepository;
        this.kafkaSenderService = kafkaSenderService;
    }

    public void validateOrderByUserId(Long userId, String orderId) {
        User user = userRepository
                .findById(userId)
                .orElseThrow(() -> new NoSuchElementException("Can't find User with id=" + userId));
        if (user.getName().equals("Jake"))
            kafkaSenderService.sendMessage(new UserVerificationFailedEventData(orderId));
        else
            kafkaSenderService.sendMessage(new UserVerificationSuccessEventData(orderId));
    }

}