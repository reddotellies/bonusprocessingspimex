package ru.spimex.shoppingbonus.bonus.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spimex.shoppingbonus.bonus.model.TransactionStatusType;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Уникальный идентификатор транзакции

    @Enumerated(EnumType.STRING)
    private TransactionStatusType status; // Статус транзакции: PENDING, SUCCESS, FAILED, REFUNDED

    @ManyToOne
    @JoinColumn(name = "bank_account_id")
    private BankAccount bankAccount; // Ссылка на аккаунт клиента

    private BigDecimal amount; // Сумма транзакции

    private BigDecimal commission; // Комиссия (если есть)

    private BigDecimal bonusAmount; // Сумма бонусов (если начислены)

    private Long timestamp; // Время транзакции в миллисекундах

}
