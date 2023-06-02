package ksh.controllers;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;

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
import ksh.model.Position;
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
	public ActionMessage handleAction(@DestinationVariable final String id, final ActionMessage action) {
		if (!this.logAndCheckID(id, "action", action)) return null;

		final var sudoku = this.service.getGameById(id);

		final var validList = new ArrayList<Position>(Arrays.asList(action.getCells()));
		for (final var position : sudoku.getStartingGridPositions()) {
			validList.remove(position);
		}
		action.setCells(validList.toArray(Position[]::new));

		switch (action.getActionType()) {
		case "addValue":
			sudoku.addValue(action.getCells(), action.getValue());
			break;

		case "removeValue":
			sudoku.removeValue(action.getCells(), action.getValue());
			break;

		case "setPencilMarks":
			sudoku.setPencilMarks(action.getCells());
			break;

		case "removePencilMarks":
			sudoku.removePencilMarks(action.getCells());
			break;

		case "deleteValues":
			sudoku.deleteValues(action.getCells());
			break;
		default:
			break;
		}
		return action;
	}
}
