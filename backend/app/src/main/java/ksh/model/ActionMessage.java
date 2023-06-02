package ksh.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ActionMessage {
	private String actionType;
	private Position[] cells;
	private Integer value;

	public ActionMessage() {
	}

	@JsonCreator
	public ActionMessage(@JsonProperty("actionType") final String actionType, @JsonProperty("cells") final Position[] cells, @JsonProperty("value") final Integer value) {
		this.actionType = actionType;
		this.cells = cells;
		this.value = value;
	}

	public String getActionType() {
		return this.actionType;
	}

	public void setActionType(final String actionType) {
		this.actionType = actionType;
	}

	public Position[] getCells() {
		return this.cells;
	}

	public void setCells(final Position[] cells) {
		this.cells = cells;
	}

	public Integer getValue() {
		return this.value;
	}

	public void setValue(final Integer value) {
		this.value = value;
	}
}
