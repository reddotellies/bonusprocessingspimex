package ru.spimex.shoppingbonus.bonus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.spimex.shoppingbonus.bonus.model.PaymentType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Тесты в данном классе предназначены для запуска по одному,
 * контекст не сохраняется и при каждом запуске баланс равен 5000
 * Данные тесты отключены для удобства сборки
 */


@SpringBootTest
@AutoConfigureMockMvc
@Disabled("Все тесты в этом классе отключены временно")
public class PaymentControllerSingleCallTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void setup() {
        // Если нужно, можно подготовить данные в базе данных H2
    }

    /*
    Изначальная сумма в (А) = 5000 рублей.
        1. Online/100
        2. Shop/120
        3. Online/301
        4. Online/17
        5. Shop/1000
        6. Online/21
        7. Shop/570
        8. Online/700

     */

    @Test
    public void testMakePaymentOnline100() throws Exception {
        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("100.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(100.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4900.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(17.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(17.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("ONLINE_PURCHASE"));
    }

    @Test
    public void testMakePaymentShop120() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("120.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(120.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4880.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(12.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(12.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("SHOP_PURCHASE"));
    }

    @Test
    public void testMakePaymentOnline301() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("301.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(301.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4699.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(90.30))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(90.30))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    public void testMakePaymentOnline17() throws Exception {
        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("17.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(17.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4998.3))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(1.7))
                .andExpect(jsonPath("$.paymentTransaction.status").value("REFUND_ONLINE_PURCHASE"));
    }

    @Test
    public void testMakePaymentShop1000() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("1000.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(1000.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4000.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(300.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(300.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    public void testMakePaymentOnline21() throws Exception {
        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("21.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(21.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4979.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(3.57))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(3.57))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("ONLINE_PURCHASE"));
    }

    @Test
    public void testMakePaymentShop570() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("570.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(570.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4430.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(171.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(171.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    public void testMakePaymentOnline700() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("700.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(700.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4300.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(210.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(210.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }



}
