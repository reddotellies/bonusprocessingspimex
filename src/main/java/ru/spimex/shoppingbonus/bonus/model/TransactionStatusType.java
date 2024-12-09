package ru.spimex.shoppingbonus.bonus.model;

//Типы статусов для статусной модели

public enum TransactionStatusType {

    ONLINE_PURCHASE,       // Обычная онлайн-покупка
    SHOP_PURCHASE,         // Покупка в магазине
    REFUND_ONLINE_PURCHASE, // Онлайн-покупка менее 20 рублей с возвратом
    LARGE_PURCHASE         // Покупка более 300 рублей

}
