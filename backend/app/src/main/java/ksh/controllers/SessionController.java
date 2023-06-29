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
import ksh.solver.Solver;

/**
 * WebSocket (Stomp) Controller for game sessions
 */
@Controller
public class SessionController {
	/** Logger of the Spring application */
	Logger logger = LoggerFactory.getLogger(SessionController.class);

	/** killer sudoku service singleton */
	@Autowired
	private KillerSudokuService service;

	/** simp messaging template singleton */
	@Autowired
	private SimpMessagingTemplate template;

	/**
	 * method for logging recieved messages and checking, whether id exists
	 * 
	 * sends error message, if id does not exist
	 * 
	 * @param id          id of KillerSudoku game (session)
	 * @param messageType type of message (subroute name)
	 * @param message     message content
	 * @return wether id exists
	 */
	public boolean logAndCheckID(final String id, final String messageType, final Object message) {
		this.logger.info(MessageFormat.format("\033[32m{0}/{1}\033[0m: {2}", id, messageType, message));
		if (this.service.getGameById(id) == null) {
			this.template.convertAndSend(MessageFormat.format("/session/broker/{0}/error", id), new ErrorMessage(messageType, MessageFormat.format("no session with id {0} found", id)));
			return false;
		}
		return true;
	}

	/**
	 * message handler for hello messages
	 * 
	 * @param id      id of KillerSudoku game (session)
	 * @param message message content
	 * @return hello reply to /session/broker/{id}
	 */
	@MessageMapping("/{id}/hello")
	@SendTo("/session/broker/{id}")
	public String hello(@DestinationVariable final String id, final String message) {
		if (!this.logAndCheckID(id, "hello", message)) return null;
		return "a new client connected";
	}

	/**
	 * message handler "requests" of client
	 * 
	 * @param id          id of KillerSudoku game (session)
	 * @param requestType type of information requested by client
	 * @return requested information to various destinations
	 */
	@MessageMapping("/{id}/request")
	public void handleRequest(@DestinationVariable final String id, final String requestType) {
		if (!this.logAndCheckID(id, "request", requestType)) return;

		switch (requestType) {
		case "completion status":
			final var sudoku = this.service.getGameById(id);
			if (sudoku.isSolved()) this.template.convertAndSend(MessageFormat.format("/session/broker/{0}", id), "Killer Sudoku has been solved");
			else this.template.convertAndSend(MessageFormat.format("/session/broker/{0}/error", id), new ErrorMessage("completion status request", "Killer Sudoku is not solved"));
			break;

		case "hint":
			final var hint = Solver.getNextStep(this.service.getGameById(id));
			if (hint != null) this.template.convertAndSend(MessageFormat.format("/session/broker/{0}/hint", id), hint);
			else this.template.convertAndSend(MessageFormat.format("/session/broker/{0}/error", id), new ErrorMessage("hint request", "could not generate hint"));
			break;

		default:
			break;
		}
	}

	/**
	 * message handler for synchronizing game actions between clients and server
	 * 
	 * @param id     id of KillerSudoku game (session)
	 * @param action message content: action performed by one client to be synchronized with server and all clients
	 * @return processed action message to /session/broker/{id}/actions
	 */
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
