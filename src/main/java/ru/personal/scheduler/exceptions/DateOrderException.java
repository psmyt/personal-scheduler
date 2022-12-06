package ru.personal.scheduler.exceptions;

public class DateOrderException extends RuntimeException implements BusinessException {
    public DateOrderException() {
        super("The start date must be earlier than the end date.");
    }
}
