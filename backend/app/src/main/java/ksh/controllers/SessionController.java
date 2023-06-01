package ksh.controllers;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import ksh.services.KillerSudokuService;

@Controller
public class SessionController {
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @Autowired
    private KillerSudokuService service;

    @MessageMapping("/{id}/hello")
    @SendTo("/session/broker/{id}")
    public String hello(@DestinationVariable final String id, final String message) {
        this.logger.info(MessageFormat.format("\033[32m{0}\033[0m: {1}", id, message));
        if (this.service.getGameById(id) == null) return MessageFormat.format("no session with id {0} found", id);
        return MessageFormat.format("Hello there, you have joined the session with id {0}", id);
    }
}
