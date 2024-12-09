package ru.spimex.shoppingbonus.bonus.model.states;

import ru.spimex.shoppingbonus.bonus.model.PaymentState;
import ru.spimex.shoppingbonus.bonus.model.PaymentType;

import java.math.BigDecimal;

public class PaymentStateFactory{
    public static PaymentState getState(PaymentType purchaseType, BigDecimal amount) {
        if (amount.compareTo(BigDecimal.valueOf(20)) < 0 && purchaseType.equals(PaymentType.ONLINE)) {
            return new RefundOnlinePurchaseState();
        } else if (amount.compareTo(BigDecimal.valueOf(300)) > 0) {
            return new LargePurchaseState();
        } else if (purchaseType.equals(PaymentType.SHOP)) {
            return new ShopPurchaseState();
        } else if (purchaseType.equals(PaymentType.ONLINE)) {
            return new OnlinePurchaseState();
        } else {
            throw new IllegalArgumentException("Invalid purchase type");
        }
    }
}
