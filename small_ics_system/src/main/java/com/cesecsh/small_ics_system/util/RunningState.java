package com.cesecsh.small_ics_system.util;

import lombok.Getter;

/**
 * 运行状态
 */
@Getter
public enum RunningState {
    CLOSE("0", "关"),
    OPEN("1", "开");

    private final String key;
    private final String value;

    RunningState(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (RunningState delFlag : RunningState.values()) {
            if (key.equals(delFlag.getKey())) {
                return delFlag.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (RunningState delFlag : RunningState.values()) {
            if (value.equals(delFlag.getValue())) {
                return delFlag.getKey();
            }
        }
        return null;
    }
}
