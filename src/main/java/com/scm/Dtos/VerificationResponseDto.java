package com.scm.Dtos;

import com.scm.Helper.MessageContent;
import com.scm.Helper.Status;
import lombok.Data;

@Data
public class VerificationResponseDto {
    private Status status;
    private MessageContent messageContent;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public MessageContent getMessageContent() {
        return messageContent;
    }

    public void setMessageContent(MessageContent messageContent) {
        this.messageContent = messageContent;
    }
}
