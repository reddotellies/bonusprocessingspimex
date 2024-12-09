package ru.spimex.shoppingbonus.bonus.model;


import java.math.BigDecimal;

public interface PaymentState {

    PaymentTransaction processPayment(PaymentTransaction account, BigDecimal amount);
}
