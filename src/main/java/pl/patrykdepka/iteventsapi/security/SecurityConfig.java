package pl.patrykdepka.iteventsapi.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final ObjectMapper objectMapper;

    public SecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, AuthenticationManager authenticationManager) throws Exception {
        http.csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers("/api/auth/**")
                .authenticated()
                .antMatchers("/api/v1/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .addFilterBefore(authenticationFilter(authenticationManager, objectMapper), BasicAuthenticationFilter.class);

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public ItEventsAuthenticationFilter authenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
        return new ItEventsAuthenticationFilter(authenticationManager, objectMapper);
    }

//    @Bean
//    public ItEventsAuthenticationFilter2 authenticationFilter(AuthenticationManager authenticationManager, ObjectMapper objectMapper) {
//        ItEventsAuthenticationFilter2 authenticationFilter = new ItEventsAuthenticationFilter2(objectMapper);
//        authenticationFilter.setAuthenticationManager(authenticationManager);
//        return authenticationFilter;
//    }
}
