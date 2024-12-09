package ru.spimex.shoppingbonus.bonus.model.states;

import ru.spimex.shoppingbonus.bonus.model.PaymentTransaction;
import ru.spimex.shoppingbonus.bonus.model.PaymentState;
import ru.spimex.shoppingbonus.bonus.model.TransactionStatusType;

import java.math.BigDecimal;

public class LargePurchaseState implements PaymentState {

    LargePurchaseState(){}


    @Override
    public PaymentTransaction processPayment(PaymentTransaction account, BigDecimal amount) {

        PaymentTransaction result = new PaymentTransaction();
        // Списываем сумму покупки
        result.setBalance(account.getBalance().subtract(amount));

        result.setAmount(amount);

        result.setCommission(new BigDecimal(0));

        // Начисляем 30% бонусов
        BigDecimal bonus = amount.multiply(BigDecimal.valueOf(0.30));
        result.setBonusBalance(account.getBonusBalance().add(bonus));

        result.setBonusAmount(bonus);

        //устанавливаем статус
        result.setStatus(TransactionStatusType.LARGE_PURCHASE);

        return result;
    }
}
