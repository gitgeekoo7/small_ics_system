package com.cesecsh.small_ics_system.util;

import lombok.Getter;

@Getter
public enum WorkingState {
    ON_LINE("0", "在线"),
    OFF_LINE("1", "离线");

    private final String key;
    private final String value;

    WorkingState(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (WorkingState workingState : WorkingState.values()) {
            if (key.equals(workingState.getKey())) {
                return workingState.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (WorkingState workingState : WorkingState.values()) {
            if (value.equals(workingState.getValue())) {
                return workingState.getKey();
            }
        }
        return null;
    }
}
