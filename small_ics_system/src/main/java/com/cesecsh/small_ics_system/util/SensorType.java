package com.cesecsh.small_ics_system.util;

import lombok.Getter;

/**
 * 传感器类型
 */
@Getter
public enum SensorType {
    ENERGY_ACQUISITION("1", "能耗采集");

    private final String key;
    private final String value;

    SensorType(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public static String getValueByKey(String key) {
        for (SensorType delFlag : SensorType.values()) {
            if (key.equals(delFlag.getKey())) {
                return delFlag.getValue();
            }
        }
        return null;
    }

    public static String getKeyByValue(String value) {
        for (SensorType delFlag : SensorType.values()) {
            if (value.equals(delFlag.getValue())) {
                return delFlag.getKey();
            }
        }
        return null;
    }
}
