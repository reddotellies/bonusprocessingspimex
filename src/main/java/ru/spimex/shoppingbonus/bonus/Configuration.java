package ru.spimex.shoppingbonus.bonus;


import org.springframework.context.annotation.Bean;
import ru.spimex.shoppingbonus.bonus.model.SecurityContext;

@org.springframework.context.annotation.Configuration
public class Configuration {

    @Bean
    SecurityContext getSecurityContext() {
        return new SecurityContext();
    }
}
