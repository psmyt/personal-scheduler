package ru.personal.scheduler.exceptions;

public class ScheduledItemsOverlapException extends RuntimeException implements BusinessException{
    public ScheduledItemsOverlapException() {
        super("Scheduled events should not overlap");
    }
}
