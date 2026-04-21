package com.carlog.backend.error;

public class NoPendingInvitationException extends RuntimeException {
    public NoPendingInvitationException(String message) {
        super(message);
    }
}
