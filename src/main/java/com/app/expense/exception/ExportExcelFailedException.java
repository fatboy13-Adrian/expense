package com.app.expense.exception;

public class ExportExcelFailedException extends RuntimeException {
	public ExportExcelFailedException (String message, Throwable cause) {
        super(message);
    }
}