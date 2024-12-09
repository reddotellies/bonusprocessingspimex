package ru.spimex.shoppingbonus.bonus.model.states;

import ru.spimex.shoppingbonus.bonus.model.PaymentTransaction;
import ru.spimex.shoppingbonus.bonus.model.PaymentState;
import ru.spimex.shoppingbonus.bonus.model.TransactionStatusType;

import java.math.BigDecimal;

public class RefundOnlinePurchaseState implements PaymentState {

    RefundOnlinePurchaseState(){}

    @Override
    public PaymentTransaction processPayment(PaymentTransaction account, BigDecimal amount) {

        PaymentTransaction result = new PaymentTransaction();

        BigDecimal commission = amount.multiply(BigDecimal.valueOf(0.10));

        result.setCommission(commission);
        //Сумма покупки
        result.setAmount(amount);

        // Списываем сумму комиссии
        result.setBalance(account.getBalance().subtract(commission)); // Возврат за вычетом комиссии

        //Баланс бонусов не меняется
        result.setBonusBalance(account.getBonusBalance());

        //Бонус не начисляется
        result.setBonusAmount(new BigDecimal(0));

        //устанавливаем статус
        result.setStatus(TransactionStatusType.REFUND_ONLINE_PURCHASE);

        return result;
    }
}
