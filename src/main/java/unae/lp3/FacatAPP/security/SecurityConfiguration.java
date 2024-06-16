package unae.lp3.FacatAPP.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

    @Autowired
    private UserDetailsService usuarioServicio;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioServicio).passwordEncoder(passwordEncoder());
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                                .requestMatchers("/", "/registro", "/css/**").permitAll()  // Asegúrate de que la ruta de registro sea correcta
                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                .requestMatchers("/secretario/**").hasRole("SECRETARIO")
                                .requestMatchers("/vistas/**").hasRole("VISTAS")
                                .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                                .loginPage("/login")
                                .defaultSuccessUrl("/", true)  // Redirige al inicio después de un login exitoso
                                .permitAll()
                )
                .logout((logout) -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login?logout")
                                .permitAll()
                )
                .csrf(csrf -> csrf.disable());  // Puedes habilitar esto si estás manejando CSRF tokens adecuadamente
        return http.build();
    }
}
