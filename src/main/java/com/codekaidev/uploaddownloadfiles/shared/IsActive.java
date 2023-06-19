package com.codekaidev.uploaddownloadfiles.shared;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;

@AllArgsConstructor

public enum IsActive {
    YES("S"),
    NO("N");

    private final String status;

    @JsonValue
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status
     * @return
     */
    public static IsActive fromStatus(String status) {
        for (IsActive value : IsActive.values()) {
            if (value.getStatus().equals(status)) {
                return value;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }

}
