package ru.spimex.shoppingbonus.bonus.dto.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.spimex.shoppingbonus.bonus.model.PaymentTransaction;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BonusBalanceResponseDTO {

   private BigDecimal bonusBalance;


}
