package com.yeonieum.scheduledTeskserver.global.enums;

public enum ServiceStatusCode {
    PENDING("PENDING"),
    APPROVE("APPROVE"),
    IN_PROGRESS("IN_PROGRESS"),
    ENDED_EVENT("ENDED_EVENT"),
    CANCELED("CANCELED");

    private final String code;

    ServiceStatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ServiceStatusCode fromCode(String code) {
        switch (code) {
            case "PENDING":
                return PENDING;
            case "APPROVE":
                return APPROVE;
            case "IN_PROGRESS":
                return IN_PROGRESS;
            case "ENDED_EVENT":
                return ENDED_EVENT;
            case "CANCELED":
                return CANCELED;
            default:
                throw new IllegalArgumentException("Invalid status code: " + code);
        }
    }
}