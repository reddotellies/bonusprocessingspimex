package ru.spimex.shoppingbonus.bonus.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum PaymentType {
    SHOP, ONLINE;

    // Десериализуем строку из JSON в enum
    @JsonCreator
    public static PaymentType fromString(String type) {
        if (type == null) {
            throw new IllegalArgumentException("Payment type cannot be null");
        }
        switch (type.toUpperCase()) {
            case "SHOP":
                return SHOP;
            case "ONLINE":
                return ONLINE;
            default:
                throw new IllegalArgumentException("Unknown payment type: " + type);
        }
    }

    // Для сериализации enum в строку
    @JsonValue
    public String toJson() {
        return this.name();
    }
}