package ksh.controllers;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import ksh.model.ActionMessage;
import ksh.model.ErrorMessage;
import ksh.services.KillerSudokuService;

@Controller
public class SessionController {
	Logger logger = LoggerFactory.getLogger(SessionController.class);

	@Autowired
	private KillerSudokuService service;

	@Autowired
	private SimpMessagingTemplate template;

	public boolean logAndCheckID(final String id, final String messageType, final Object message) {
		this.logger.info(MessageFormat.format("\033[32m{0}/{1}\033[0m: {2}", id, messageType, message));
		if (this.service.getGameById(id) == null) {
			this.template.convertAndSend(MessageFormat.format("/session/broker/{0}/error", id), new ErrorMessage(messageType, MessageFormat.format("no session with id {0} found", id)));
			return false;
		}
		return true;
	}

	@MessageMapping("/{id}/hello")
	@SendTo("/session/broker/{id}")
	public String hello(@DestinationVariable final String id, final String message) {
		if (!this.logAndCheckID(id, "hello", message)) return null;
		return MessageFormat.format("Hello there, you have joined the session with id {0}", id);
	}

	@MessageMapping("/{id}/action")
	@SendTo("/session/broker/{id}/actions")
	public ActionMessage handleAction(@DestinationVariable final String id, final ActionMessage message) {
		if (!this.logAndCheckID(id, "action", message)) return null;
		return message;
	}
}
