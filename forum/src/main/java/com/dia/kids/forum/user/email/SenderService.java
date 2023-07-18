package com.dia.kids.forum.user.email;

import com.dia.kids.forum.exception.ForumException;

public interface SenderService {

    void sendEmail(EmailMessage emailMessage) throws ForumException;

}
