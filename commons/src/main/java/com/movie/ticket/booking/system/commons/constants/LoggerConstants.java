package com.movie.ticket.booking.system.commons.constants;

public enum LoggerConstants {

    ENTERED_CONTROLLER_MESSAGE("Entered {} controller of {} class with value {}"),
    EXITING_CONTROLLER_MESSAGE("Exiting {} controller of {}"),
    ENTERED_SERVICE_MESSAGE("Entered {} service of {} class with value {}"),
    EXITING_SERVICE_MESSAGE("Exiting {} service of {}"),

    ENTERED_CONTROLLER_HANDLER_MESSAGE("Entered {} handler of {} class with value {}");


    private final String value;

    LoggerConstants(String value){
        this.value=value;
    }

    public String getValue(){
        return value;
    }
}
