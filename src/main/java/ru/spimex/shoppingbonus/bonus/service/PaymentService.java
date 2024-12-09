package ru.spimex.shoppingbonus.bonus.service;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import ru.spimex.shoppingbonus.bonus.dto.responses.AccountBalanceResponseDTO;
import ru.spimex.shoppingbonus.bonus.dto.responses.BonusBalanceResponseDTO;
import ru.spimex.shoppingbonus.bonus.dto.responses.PaymentResponseDTO;
import ru.spimex.shoppingbonus.bonus.entity.Transaction;
import ru.spimex.shoppingbonus.bonus.exceptions.AccountNotFoundException;
import ru.spimex.shoppingbonus.bonus.exceptions.InsufficientBalanceException;
import ru.spimex.shoppingbonus.bonus.model.PaymentTransaction;
import ru.spimex.shoppingbonus.bonus.entity.BankAccount;
import ru.spimex.shoppingbonus.bonus.model.PaymentContext;
import ru.spimex.shoppingbonus.bonus.model.PaymentState;
import ru.spimex.shoppingbonus.bonus.model.PaymentType;
import ru.spimex.shoppingbonus.bonus.model.states.PaymentStateFactory;
import ru.spimex.shoppingbonus.bonus.repository.BankAccountRepository;
import ru.spimex.shoppingbonus.bonus.repository.TransactionRepository;

import java.math.BigDecimal;

@Service
public class PaymentService {

    private BankAccountRepository bankAccountRepository;
    private TransactionRepository transactionRepository;

    public PaymentService(BankAccountRepository bankAccountRepository, TransactionRepository transactionRepository) {
        this.bankAccountRepository = bankAccountRepository;
        this.transactionRepository = transactionRepository;
    }

    public BonusBalanceResponseDTO getBonusBalance(Long accountId) throws AccountNotFoundException {
        // Получаем аккаунт клиента
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException());
        BonusBalanceResponseDTO bonusBalanceResponseDTO = new BonusBalanceResponseDTO();
        bonusBalanceResponseDTO.setBonusBalance(account.getBonusBalance());
        return bonusBalanceResponseDTO;
    }

    public AccountBalanceResponseDTO getAccountBalance(Long accountId) throws AccountNotFoundException {
        // Получаем аккаунт клиента
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException());
        AccountBalanceResponseDTO accountBalanceResponseDTO = new AccountBalanceResponseDTO();
        accountBalanceResponseDTO.setAccountBalance(account.getBalance());
        return accountBalanceResponseDTO;
    }

    @Transactional(isolation = Isolation.SERIALIZABLE, rollbackFor = InsufficientBalanceException.class)
    public PaymentResponseDTO processPayment(Long accountId, PaymentType purchaseType, BigDecimal amount) throws InsufficientBalanceException, AccountNotFoundException {

        // Получаем аккаунт клиента
        BankAccount account = bankAccountRepository.findById(accountId).orElseThrow(() -> new AccountNotFoundException());

        //заполняем частично модель транзакции для последующей обработки шаблоном state
        PaymentTransaction paymentTransaction = new PaymentTransaction();
        paymentTransaction.setBalance(account.getBalance());
        paymentTransaction.setBonusBalance(account.getBonusBalance());

        // Определяем состояние в зависимости от входных данных через фабрику
        // Код спроектирован так, что состояние возможно создать только через фабрику,
        // что должно натолкнуть разработчика проверить логику по которой выбирается состояние, если он захочет добавить новое состояние
        PaymentState state = PaymentStateFactory.getState(purchaseType,amount);

        // Инициализация контекста
        PaymentContext context = new PaymentContext(state);

        // Обработка платежа с использованием текущего состояния
        PaymentTransaction result = context.processPayment(paymentTransaction, amount);

        //делаем проверки результата
        checkRules(result);

        // Сохранение обновленных данных
        account.setBalance(result.getBalance());
        account.setBonusBalance(result.getBonusBalance());
        account.setLastUpdated(System.currentTimeMillis());

        // Сохраняем изменения в bankAccount
        bankAccountRepository.save(account);

        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setCommission(result.getCommission());
        transaction.setBonusAmount(result.getBonusAmount());
        transaction.setStatus(result.getStatus());
        transaction.setBankAccount(account);
        transaction.setTimestamp(System.currentTimeMillis());

        //сохраняем транзакцию
        transactionRepository.save(transaction);

        return new PaymentResponseDTO(result);
    }

    private void checkRules(PaymentTransaction res) throws InsufficientBalanceException {
        if (res.getBalance().compareTo(BigDecimal.ZERO) < 0){
            throw new InsufficientBalanceException();
        }
    }
}
