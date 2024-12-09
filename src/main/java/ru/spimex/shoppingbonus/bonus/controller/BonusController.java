package ru.spimex.shoppingbonus.bonus.controller;



import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.spimex.shoppingbonus.bonus.dto.error.ErrorResponse;
import ru.spimex.shoppingbonus.bonus.dto.responses.AccountBalanceResponseDTO;
import ru.spimex.shoppingbonus.bonus.dto.responses.BonusBalanceResponseDTO;
import ru.spimex.shoppingbonus.bonus.dto.responses.PaymentResponseDTO;
import ru.spimex.shoppingbonus.bonus.exceptions.AccountNotFoundException;
import ru.spimex.shoppingbonus.bonus.exceptions.InsufficientBalanceException;
import ru.spimex.shoppingbonus.bonus.model.PaymentType;
import ru.spimex.shoppingbonus.bonus.model.SecurityContext;
import ru.spimex.shoppingbonus.bonus.service.PaymentService;

import java.math.BigDecimal;

@RestController
@RequestMapping("/api")
public class BonusController {

        private PaymentService paymentService;
        private SecurityContext securityContext;

        BonusController(PaymentService paymentService,SecurityContext securityContext){
                this.paymentService = paymentService;
                this.securityContext = securityContext;
        }


        /**
         * Метод для обработки оплаты покупки.
         * @param paymentType - тип оплаты
         * @param amount - сумма покупки
         * @return
         * @throws InsufficientBalanceException
         * @throws AccountNotFoundException
         */

        @Operation(
                summary = "Произвести оплату",
                description = "Метод проводит оплату и начисляет бонусы",
                parameters = {
                        @Parameter(
                                description = "Тип оплаты (SHOP или ONLINE)",
                                name = "shopType",
                                required = true,
                                schema = @Schema(implementation = PaymentType.class, type = "string", allowableValues = {"SHOP", "ONLINE"})
                        ),
                        @Parameter(
                                description = "Сумма для оплаты",
                                name = "amount",
                                required = true,
                                schema = @Schema(type = "string", format = "decimal", example = "100.00")
                        )
                },
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Платеж успешно обработан",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = PaymentResponseDTO.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "400",
                                description = "Некорректные данные запроса"
                        ),
                        @ApiResponse(
                                responseCode = "402",
                                description = "Недостаточно средств для проведения оплаты",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponse.class)  // Ошибка, которая будет возвращена
                                )
                        )
                }
        )
        @GetMapping("/payment/{shopType}/{amount}")
        public ResponseEntity<PaymentResponseDTO> makePayment(
                @PathVariable("shopType") PaymentType paymentType,
                @PathVariable("amount") BigDecimal amount) throws InsufficientBalanceException, AccountNotFoundException {
                PaymentResponseDTO responseDTO = paymentService.processPayment(securityContext.getAccountId(), paymentType, amount);
                return  ResponseEntity.ok(responseDTO);
        }



        /**
         * Метод для получения бонусного баланса на счете клиента.
         * @return
         * @throws AccountNotFoundException
         */

        @Operation(
                summary = "Получить бонусный баланс",
                description = "Метод для получения бонусного баланса на счете клиента.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Баланс успешно возвращен",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = BonusBalanceResponseDTO.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Счет клиента не найден",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponse.class)
                                )
                        )
                }
        )
        @GetMapping("/bankAccountOfEMoney")
        public ResponseEntity<BonusBalanceResponseDTO> getBonusBalance() throws AccountNotFoundException {
                BonusBalanceResponseDTO bonusBalance = paymentService.getBonusBalance(securityContext.getAccountId());
                return  ResponseEntity.ok(bonusBalance);
        }

        /**
         * Метод для получения текущего баланса в банке.
         * @return
         * @throws AccountNotFoundException
         */

        @Operation(
                summary = "Получить текущий баланс",
                description = "Метод для получения текущего баланса в банке.",
                responses = {
                        @ApiResponse(
                                responseCode = "200",
                                description = "Баланс успешно возвращен",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = AccountBalanceResponseDTO.class)
                                )
                        ),
                        @ApiResponse(
                                responseCode = "404",
                                description = "Счет клиента не найден",
                                content = @Content(
                                        mediaType = "application/json",
                                        schema = @Schema(implementation = ErrorResponse.class)
                                )
                        )
                }
        )
        @GetMapping("/money")
        public ResponseEntity<AccountBalanceResponseDTO> getBalance() throws AccountNotFoundException {
                AccountBalanceResponseDTO accountBalance = paymentService.getAccountBalance(securityContext.getAccountId());
                return  ResponseEntity.ok(accountBalance);
        }



        //TODO можно вынести в отдельный класс, но тогда swagger может конфликтовать из-за controlleradvice

        @ExceptionHandler(InsufficientBalanceException.class)
        public ResponseEntity<ErrorResponse> handleInsufficientBalanceException(InsufficientBalanceException ex) {
                ErrorResponse insufficientBalance = new ErrorResponse(
                        "Недостаточно средств для совершения покупки.",
                        "INSUFFICIENT_BALANCE"
                );
                return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED)
                        .contentType(MediaType.APPLICATION_JSON) // Указываем тип контента
                        .body(insufficientBalance);
        }

        @ExceptionHandler(AccountNotFoundException.class)
        public ResponseEntity<ErrorResponse> handleAccountNotFoundException(AccountNotFoundException ex) {
                ErrorResponse accnotfound = new ErrorResponse(
                        "Aккаунт не найден",
                        "ACCOUNT_NOT_FOUND"
                );
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .contentType(MediaType.APPLICATION_JSON) // Указываем тип контента
                        .body(accnotfound);
        }


}
