📄 Payment Service
Описание проекта
Payment Service —  микросервис для обработки платежей и начисления бонусов. Проект демонстрирует работу с платежами, обработку различных состояний, а также использование модульных тестов

🛠️ Технологический стек
Java 17
Spring Boot
Spring MVC
H2 Database (in-memory)
Swagger (OpenAPI)
JUnit 5

🚀 Инструкция по запуску
Клонирование репозитория:

bash
Копировать код
git clone https://github.com/yourusername/payment-service.git
cd payment-service
Запуск приложения:

bash
Копировать код
mvn spring-boot:run
Доступ к Swagger-документации:
После запуска приложения, Swagger UI будет доступен по адресу:
http://localhost:8080/swagger-ui/index.html

📚 Функциональность сервиса
Основные методы
Обработка платежа и начисление бонусов:
GET /api/payment/{paymentType}/{amount}
Метод позволяет выполнить оплату с указанным типом и суммой.

Получение бонусного баланса:
GET /bankAccountOfEMoney
Возвращает текущий бонусный баланс клиента.

Получение баланса счета:
GET /money
Возвращает текущий баланс счета клиента.

🧪 Тестирование
В проекте реализованы модульные тесты для проверки функциональности контроллера PaymentController:

PaymentControllerOneByOneTest — тестирует методы контроллера по отдельности.
PaymentControllerSingleCallTest — выполняет комплексное тестирование всех методов одним вызовом, сохраняя контекст счета аккаунта в базе, последовательно работая с ним.
Запуск тестов выполняется командой:

bash
Копировать код
mvn test
🗄️ База данных
Для хранения данных используется H2 Database (in-memory). При запуске приложения база данных создается автоматически.

Консоль H2 доступна по адресу:
http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:testdb
Пользователь: sa
Пароль: password
🧩 Шаблон проектирования: State
В проекте применяется шаблон проектирования State для управления состоянием платежей. Этот подход позволяет динамически изменять поведение объекта в зависимости от его текущего состояния.

💼 Дополнительная информация
Swagger-документация обеспечивает наглядное описание API и позволяет тестировать методы через веб-интерфейс.
Реализованы проверки на некорректные запросы и недостаточный баланс.
