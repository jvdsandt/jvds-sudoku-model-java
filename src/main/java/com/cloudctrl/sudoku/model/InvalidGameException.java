package com.cloudctrl.sudoku.model;

public class InvalidGameException extends RuntimeException {

	public InvalidGameException(String message) {
		super(message);
	}
}
