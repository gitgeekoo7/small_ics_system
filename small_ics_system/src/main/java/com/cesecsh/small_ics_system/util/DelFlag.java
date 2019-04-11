package com.cesecsh.small_ics_system.util;

import lombok.Getter;

/**
 * 删除标志
 */
@Getter
public enum DelFlag {
    UN_DELETED("0", "未删除"),
    DELETED("1", "已删除");

    private final String key;
    private final String value;

    DelFlag(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (DelFlag delFlag : DelFlag.values()) {
            if (key.equals(delFlag.getKey())) {
                return delFlag.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (DelFlag delFlag : DelFlag.values()) {
            if (value.equals(delFlag.getValue())) {
                return delFlag.getKey();
            }
        }
        return null;
    }
}
