package ru.spimex.shoppingbonus.bonus;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import ru.spimex.shoppingbonus.bonus.model.PaymentType;

import java.math.BigDecimal;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


/**
 * Тесты в данном классе предназначены для запуска массово(через класс),
 * контекст  сохраняется, тесты выстроены в заданном в ТЗ порядке, с уменьшением
 * общего баланса
 *
 *  Изначальная сумма в (А) = 5000 рублей.
 *         1. Online/100
 *         2. Shop/120
 *         3. Online/301
 *         4. Online/17
 *         5. Shop/1000
 *         6. Online/21
 *         7. Shop/570
 *         8. Online/700
 */

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentControllerOneByOneTest {

    @Autowired
    private MockMvc mockMvc;


    @BeforeEach
    public void setup() {
        // Если нужно, можно подготовить данные в базе данных H2
    }

    @Test
    @Order(1)
    public void testMakePaymentOnline100() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("100.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType.name(), amount)
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
    @Order(2)
    public void testMakePaymentShop120() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("120.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(120.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4780.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(29.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(12.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("SHOP_PURCHASE"));
    }

    @Test
    @Order(3)
    public void testMakePaymentOnline301() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("301.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(301.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4479.00))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(119.30))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(90.30))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    @Order(4)
    public void testMakePaymentOnline17() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("17.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(17.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(4477.3))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(119.30))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(1.7))
                .andExpect(jsonPath("$.paymentTransaction.status").value("REFUND_ONLINE_PURCHASE"));
    }

    @Test
    @Order(5)
    public void testMakePaymentShop1000() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("1000.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(1000.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(3477.30))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(419.30))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(300.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    @Order(6)
    public void testMakePaymentOnline21() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("21.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(21.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(3456.3))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(422.87))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(3.57))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("ONLINE_PURCHASE"));
    }

    @Test
    @Order(7)
    public void testMakePaymentShop570() throws Exception {

        // Параметры для запроса
        PaymentType paymentType = PaymentType.SHOP;
        BigDecimal amount = new BigDecimal("570.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(570.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(2886.3))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(593.87))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(171.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }

    @Test
    @Order(8)
    public void testMakePaymentOnline700() throws Exception {


        // Параметры для запроса
        PaymentType paymentType = PaymentType.ONLINE;
        BigDecimal amount = new BigDecimal("700.00");

        // Выполнение запроса с параметрами в пути
        mockMvc.perform(get("/api/payment/{shopType}/{amount}", paymentType, amount)
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.paymentTransaction.amount").value(700.00))
                .andExpect(jsonPath("$.paymentTransaction.balance").value(2186.3))
                .andExpect(jsonPath("$.paymentTransaction.bonusBalance").value(803.87))
                .andExpect(jsonPath("$.paymentTransaction.bonusAmount").value(210.00))
                .andExpect(jsonPath("$.paymentTransaction.commission").value(0.00))
                .andExpect(jsonPath("$.paymentTransaction.status").value("LARGE_PURCHASE"));
    }



}
