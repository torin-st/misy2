package com.slyadz.misy2.usersservice.api.event;

public class UserVerificationFailedEvent {
    private final UserVerificationFailedEventData userVerificationFailedEventData;

    public UserVerificationFailedEvent(UserVerificationFailedEventData userVerificationFailedEventData) {
        this.userVerificationFailedEventData = userVerificationFailedEventData;
    }

    public UserVerificationFailedEventData getUserVerificationFailedEventData() {
        return userVerificationFailedEventData;
    }
    
}
