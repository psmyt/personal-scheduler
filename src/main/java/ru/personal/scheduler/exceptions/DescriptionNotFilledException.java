package ru.personal.scheduler.exceptions;

public class DescriptionNotFilledException extends RuntimeException implements BusinessException {
    public DescriptionNotFilledException() {
        super("The field \"Description\" is mandatory");
    }
}
