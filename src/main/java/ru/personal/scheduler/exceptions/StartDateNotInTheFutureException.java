package ru.personal.scheduler.exceptions;

public class StartDateNotInTheFutureException extends RuntimeException implements BusinessException {
    public StartDateNotInTheFutureException() {
        super("The starting time of a new event must be in the future.");
    }
}
