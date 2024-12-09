package ru.spimex.shoppingbonus.bonus.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.spimex.shoppingbonus.bonus.entity.BankAccount;

import java.util.List;
import java.util.Optional;

@Repository
public interface BankAccountRepository extends JpaRepository<BankAccount,Long> {

    Optional<BankAccount> findById(Long id);


}
