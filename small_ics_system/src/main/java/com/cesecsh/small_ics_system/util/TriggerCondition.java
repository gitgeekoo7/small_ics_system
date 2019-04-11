package com.cesecsh.small_ics_system.util;

import lombok.Getter;

/**
 * 触发条件
 */
@Getter
public enum TriggerCondition {
    LOW_LEVEL("0", "低电平"),
    HIGH_LEVEL("1", "高电平");

    private final String key;
    private final String value;

    TriggerCondition(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (TriggerCondition delFlag : TriggerCondition.values()) {
            if (key.equals(delFlag.getKey())) {
                return delFlag.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (TriggerCondition delFlag : TriggerCondition.values()) {
            if (value.equals(delFlag.getValue())) {
                return delFlag.getKey();
            }
        }
        return null;
    }
}
