package ksh.model;

/**
 * Model for message format for communicating errors via WebSocket
 */
public record ErrorMessage(String source, String message) {
}
