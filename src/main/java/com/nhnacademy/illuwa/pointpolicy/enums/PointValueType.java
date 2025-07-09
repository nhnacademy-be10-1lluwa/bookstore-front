package com.nhnacademy.illuwa.pointpolicy.enums;

import com.fasterxml.jackson.annotation.JsonCreator;

public enum PointValueType {
    RATE, AMOUNT;

    @JsonCreator
    public static PointValueType fromStringIgnoreCase(String value) {
        for (PointValueType type : PointValueType.values()) {
            if (type.name().equalsIgnoreCase(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("No enum constant for value: " + value);
    }

    public static String getKoreanLabel(PointValueType type) {
        return switch (type) {
            case AMOUNT -> "금액";
            case RATE -> "비율";
            default -> type.name();
        };
    }

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }

}
