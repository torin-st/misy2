package com.slyadz.misy2.usersservice.api.event;

public class UserVerificationSuccessEvent {
    private final UserVerificationSuccessEventData userVerificationSuccessEventData;

    public UserVerificationSuccessEvent(UserVerificationSuccessEventData userVerificationSuccessEventData) {
        this.userVerificationSuccessEventData = userVerificationSuccessEventData;
    }

    public UserVerificationSuccessEventData getUserVerificationSuccessEventData() {
        return userVerificationSuccessEventData;
    }
        
}
