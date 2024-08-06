package com.yeonieum.scheduledTeskserver.global.enums;

public enum ActiveStatus {
    PENDING("PENDING"),
    ACTIVE("ACTIVE"),
    COMPLETED("COMPLETED"),
    INACTIVE("F");

    private final String code;

    ActiveStatus(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ActiveStatus fromCode(String code) {
        switch (code) {
            case "PENDING":
                return PENDING;
            case "ACTIVE":
                return ACTIVE;
            case "COMPLETED":
                return COMPLETED;
            case "INACTIVE":
                return INACTIVE;
            default:
                throw new IllegalArgumentException("Invalid status code: " + code);
        }
    }
}
