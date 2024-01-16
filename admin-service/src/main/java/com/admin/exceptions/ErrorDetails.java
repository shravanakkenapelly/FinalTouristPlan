package com.admin.exceptions;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ErrorDetails {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private String ldt;
    private String message;
    private String details;

    public ErrorDetails(LocalDateTime ldt, String message, String details) {
        this.ldt = ldt.format(formatter);
        this.message = message;
        this.details = details;
    }

    public String getLdt() {
        return ldt;
    }

    public void setLdt(LocalDateTime ldt) {
        this.ldt = ldt.format(formatter);
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    @Override
    public String toString() {
        return "ErrorDetails [ldt=" + ldt + ", message=" + message + ", details=" + details + "]";
    }
}
