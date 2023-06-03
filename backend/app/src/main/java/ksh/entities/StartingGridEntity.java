package ksh.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Class defining the starting grid entity to be received by the API and stored in repository
 */
@Entity
public class StartingGridEntity {
	/**
	 * auto-generated (at time of inserting into repository) UUID of starting grid (will also be used for KillerSudok game)
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private String id;

	/** json string contaning starting grid */
	@Column(columnDefinition = "json")
	private String killerSudoku;

	/**
	 * default empty constructor
	 */
	protected StartingGridEntity() {
	}

	/**
	 * default full constructor
	 * 
	 * @param killerSudoku json string containing staritng grid
	 */
	public StartingGridEntity(final String killerSudoku) {
		this.killerSudoku = killerSudoku;
	}

	/**
	 * default getter for id
	 * 
	 * @return uuid of starting grid
	 */
	public String getId() {
		return this.id;
	}

	/**
	 * default setter for id
	 * 
	 * @param id uuid to set
	 */
	public void setId(final String id) {
		this.id = id;
	}

	/**
	 * default getter for killerSudoku
	 * 
	 * @return json string of starting grid
	 */
	public String getKillerSudoku() {
		return this.killerSudoku;
	}

	/**
	 * default setter for killerSudoku
	 * 
	 * @param killerSudoku json string of starting grid to set
	 */
	public void setKillerSudoku(final String killerSudoku) {
		this.killerSudoku = killerSudoku;
	}

}
