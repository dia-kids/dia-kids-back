package com.dia.kids.forum.user.activation;

import com.dia.kids.forum.user.User;

public interface ActivationSenderService {

    void sendActivationCode(User user);

}
