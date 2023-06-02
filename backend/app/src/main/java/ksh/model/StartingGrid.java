package ksh.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ksh.entities.StartingGridEntity;

/**
 * Model for starting grid used for communication between client and server
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class StartingGrid {
	/** given values of starting grid */
	@JsonProperty("grid")
	private int[][] grid;
	/** cage layout of KillerSudoku */
	@JsonProperty("cages")
	private Cage[] cages;

	/**
	 * default full constructor
	 * 
	 * @param grid  given values of starting grid
	 * @param cages cage layout of KillerSudoku
	 */
	@JsonCreator
	public StartingGrid(@JsonProperty("grid") final int[][] grid, @JsonProperty("cages") final Cage[] cages) {
		this.grid = grid;
		this.cages = cages;
	}

	/**
	 * maps this object to corresponding entity for persistance in repository
	 * 
	 * @return corresponding entitiy
	 * @throws JsonProcessingException when object can't be serialized to JSON string
	 */
	public StartingGridEntity toEntity() throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();

		return new StartingGridEntity(objectMapper.writeValueAsString(this));
	}

	/**
	 * creates StartingGrid from corresponding entity
	 * 
	 * @param entity entity to create StartingGrid from
	 * @return starting grid
	 * @throws JsonProcessingException when JSON string of entity can't be deserialized
	 */
	public static StartingGrid fromEntity(final StartingGridEntity entity) throws JsonProcessingException {
		final ObjectMapper objectMapper = new ObjectMapper();

		String serialized = entity.getKillerSudoku();
		// why the fuck, do i have to do this?
		// desirialization is done with the same framework, that did the serialization...
		// why do i have to do the cleanup manually? THIS IS BULLSHIT
		serialized = serialized.replace("\"{", "{").replace("}\"", "}").replace("\\", "");

		return objectMapper.readValue(serialized, StartingGrid.class);
	}

	/**
	 * default getter method for grid
	 * 
	 * @return values of starting grid
	 */
	@JsonProperty("grid")
	public int[][] getGrid() {
		return this.grid;
	}

	/**
	 * default setter method for grid
	 * 
	 * @param grid values of starting grid
	 */
	@JsonProperty("grid")
	public void setGrid(final int[][] grid) {
		this.grid = grid;
	}

	/**
	 * default getter method for cages
	 * 
	 * @return cage layout of killer sudoku
	 */
	@JsonProperty("cages")
	public Cage[] getCages() {
		return this.cages;
	}

	/**
	 * default setter method for cages
	 * 
	 * @param cages cage layout of killer sudoku
	 */
	@JsonProperty("cages")
	public void setCages(final Cage[] cages) {
		this.cages = cages;
	}
}