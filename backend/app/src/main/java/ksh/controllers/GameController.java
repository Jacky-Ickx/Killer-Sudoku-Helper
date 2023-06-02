package ksh.controllers;

import java.text.MessageFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import ksh.entities.StartingGridEntity;
import ksh.repositories.StartingGridRepository;
import ksh.services.KillerSudokuService;
import ksh.model.KillerSudoku;
import ksh.model.StartingGrid;

/**
 * REST Controller for routes starting with /games
 */
@RestController
public class GameController {
	/** repository for storing starting grids */
	private final StartingGridRepository repository;

	/** killer sudoku service singleton */
	@Autowired
	private KillerSudokuService service;

	/**
	 * constructor used to inject repository dependency
	 * 
	 * @param repository repository to inject
	 */
	GameController(final StartingGridRepository repository) {
		this.repository = repository;
	}

	/**
	 * GET endpoint for querying current state of a KillerSudoku game by corresponding id
	 * 
	 * @param id id of game
	 * @return current state, if id exists, otherwise 404
	 */
	@GetMapping(path = "/games/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public KillerSudoku getGameById(@PathVariable final String id) {
		final KillerSudoku sudoku = this.service.getGameById(id);
		if (sudoku == null) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "given id does not exist");
		return sudoku;
	}

	/**
	 * GET endpoint for querying starting grid of a game by corresponding id
	 * 
	 * @param id id of game
	 * @return starting grid of game, if id exists, otherwise 404
	 */
	@GetMapping(path = "/games/{id}/startingGrid", produces = MediaType.APPLICATION_JSON_VALUE)
	public StartingGrid getStartingGridById(@PathVariable final String id) {
		final StartingGridEntity entity = this.repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "given id does not exist"));
		try {
			return StartingGrid.fromEntity(entity);
		}
		catch (final JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MessageFormat.format("error while serializing KillerSudoku object: {0}", e));
		}
	}

	/**
	 * POST endpoint for registering a starting grid and starting a corresponding KillerSudoku game
	 * 
	 * @param sudoku starting grid to register
	 * @return generated uuid for game / starting grid
	 */
	@PostMapping(path = "/games", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
	public String createGame(@RequestBody final StartingGrid sudoku) {
		try {
			final StartingGridEntity entity = sudoku.toEntity();
			final String id = this.repository.save(entity).getId();
			this.service.createGame(id, sudoku);
			return id;
		}
		catch (final JsonProcessingException e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, MessageFormat.format("error while serializing KillerSudoku object: {0}", e));
		}
		catch (final IllegalArgumentException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, MessageFormat.format("grid is not valid: {0}", e.getMessage()));
		}
	}
}
