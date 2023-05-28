package ksh.controllers;

import java.text.MessageFormat;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class SessionController {
    Logger logger = LoggerFactory.getLogger(SessionController.class);

    @MessageMapping("/{id}/hello")
    @SendTo("/session/broker/{id}")
    public String hello(@DestinationVariable final String id, final String message) {
        this.logger.info(message);
        return MessageFormat.format("Hello there, you have joined the session with id {0}", id);
    }
}
