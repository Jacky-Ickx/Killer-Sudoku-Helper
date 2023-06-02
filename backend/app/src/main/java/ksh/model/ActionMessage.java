package ksh.model;

public record ActionMessage(String actionType, Position[] cells, Integer value) {
}
