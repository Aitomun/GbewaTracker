package com.eromosele.gbewatracker.service;

import com.eromosele.gbewatracker.dto.EmailDetails;

public interface EmailService {
    void sendEmail(EmailDetails emailDetails);
}
