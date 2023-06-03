package ksh.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Model for the message format used to communicate / synchronize actions between server and clients
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionMessage {
	/** type of action to be performed */
	private String actionType;
	/** coordinates cells affected by action */
	private Position[] cells;
	/** value for action if needed */
	private Integer value;

	/**
	 * default empty constructor
	 */
	public ActionMessage() {
	}

	/**
	 * default full constructor
	 * 
	 * @param actionType type of action to be performed
	 * @param cells      coordinates of cells affected by action
	 * @param value      value for action if needed
	 */
	@JsonCreator
	public ActionMessage(@JsonProperty("actionType") final String actionType, @JsonProperty("cells") final Position[] cells, @JsonProperty("value") final Integer value) {
		this.actionType = actionType;
		this.cells = cells;
		this.value = value;
	}

	/**
	 * default getter method for actionType
	 * 
	 * @return type of action
	 */
	public String getActionType() {
		return this.actionType;
	}

	/**
	 * default setter method for actionType
	 * 
	 * @param actionType type of action
	 */
	public void setActionType(final String actionType) {
		this.actionType = actionType;
	}

	/**
	 * default getter method for cells
	 * 
	 * @return coordinates of affected cells
	 */
	public Position[] getCells() {
		return this.cells;
	}

	/**
	 * default setter method for cells
	 * 
	 * @param cells coordinates of affected cells
	 */
	public void setCells(final Position[] cells) {
		this.cells = cells;
	}

	/**
	 * default getter method for value
	 * 
	 * @return value of action
	 */
	public Integer getValue() {
		return this.value;
	}

	/**
	 * default setter method for value
	 * 
	 * @param value value of action
	 */
	public void setValue(final Integer value) {
		this.value = value;
	}
}
