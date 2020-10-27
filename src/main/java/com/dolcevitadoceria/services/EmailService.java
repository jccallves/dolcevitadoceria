package com.dolcevitadoceria.services;

import org.springframework.mail.SimpleMailMessage;

import com.dolcevitadoceria.domain.Pedido;


public interface EmailService {

	void sendOrderConfirmationEmail(Pedido obj);

	void sendEmail(SimpleMailMessage msg);
}