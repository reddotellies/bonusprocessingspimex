package ru.spimex.shoppingbonus.bonus.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentContext {

        private PaymentState currentState;  // текущее состояние

        private PaymentContext(){}

        public PaymentContext(PaymentState currentState) {
                this.currentState = currentState;
        }

        public PaymentTransaction processPayment(PaymentTransaction paymentTransaction, BigDecimal amount) {
            return currentState.processPayment(paymentTransaction,amount);
        }

}
