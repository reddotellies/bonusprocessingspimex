package ru.spimex.shoppingbonus.bonus.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class PaymentTransaction {

    @Schema(description = "Сумма платежа", example = "100.00")
    private BigDecimal amount;

    @Schema(description = "Баланс счета", example = "1000.00")
    private BigDecimal balance;

    @Schema(description = "Бонусный баланс счета", example = "150.00")
    private BigDecimal bonusBalance;

    @Schema(description = "Сумма бонуса, которая была начислена", example = "50.00")
    private BigDecimal bonusAmount;

    @Schema(description = "Комиссия за платеж", example = "5.00")
    private BigDecimal commission;

    @Schema(description = "Статус транзакции", example = "ONLINE_PURCHASE")
    private TransactionStatusType status;

}
