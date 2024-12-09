package ru.spimex.shoppingbonus.bonus.service;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import ru.spimex.shoppingbonus.bonus.entity.BankAccount;
import ru.spimex.shoppingbonus.bonus.repository.BankAccountRepository;

import java.math.BigDecimal;

@Service
public class DatabaseInitializer {


    private BankAccountRepository bankAccountRepository;

    DatabaseInitializer(BankAccountRepository bankAccountRepository) {
       this.bankAccountRepository = bankAccountRepository;
    }

    @PostConstruct
    public void initData() {
        if (bankAccountRepository.count() == 0) {
            BankAccount account = new BankAccount();
            account.setBalance(new BigDecimal("5000.00"));
            account.setBonusBalance(BigDecimal.ZERO);
            account.setLastUpdated(System.currentTimeMillis());
            bankAccountRepository.save(account);
        }
    }
}