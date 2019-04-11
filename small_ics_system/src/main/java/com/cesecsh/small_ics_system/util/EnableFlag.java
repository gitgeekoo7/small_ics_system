package com.cesecsh.small_ics_system.util;

import lombok.Getter;

/**
 * 启用标志
 */
@Getter
public enum EnableFlag {
    DISABLE("0", "禁用"),
    ENABLE("1", "启用");

    private final String key;
    private final String value;

    EnableFlag(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (EnableFlag delFlag : EnableFlag.values()) {
            if (key.equals(delFlag.getKey())) {
                return delFlag.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (EnableFlag delFlag : EnableFlag.values()) {
            if (value.equals(delFlag.getValue())) {
                return delFlag.getKey();
            }
        }
        return null;
    }
}
