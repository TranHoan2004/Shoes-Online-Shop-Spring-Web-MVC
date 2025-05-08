package com.HE180030.email;

import com.HE180030.common.Constants;

public interface MailService extends Constants.MailProperties {
    void sendEmail(String to, String subject, String body);
}
