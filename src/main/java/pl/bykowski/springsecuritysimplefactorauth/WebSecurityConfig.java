package pl.bykowski.springsecuritysimplefactorauth;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bykowski.springsecuritysimplefactorauth.service.UserDetailsServiceImpl;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private UserDetailsServiceImpl userDetailsService; // wstrzykujemy poprzez konstruktor

    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService); // te implementacje
        // podkładamy do () userDetailsService po konstruktorze wersecurity config
        // mamy możliwość tworzenia nowych użytkowników kttorzy będa zapisywali do bazy danych
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable(); // wylaczamy zabezpieczenie spring security broni przed przypadkowym wysłaniem formularza
        http.headers().disable(); // wylaczamy zabezpiczenia blokowania naglowków
        http.authorizeRequests()
                .antMatchers("/hello").authenticated() // dostep wszyscy odrazu jak sie zalogują przejdzie do endpointu hello, a endpoint hello będzie już wiedzial jak ten użytkownik ma na imie poprzez priincipal w usercontroller
                //.antMatchers("/for-admin").hasAuthority("ROLE_ADMIN") // (usunieto strony for-admin wiec bez tego), w dokumentacji wymaga przedorstka role_ mozna też has role bez role
                //.antMatchers("/for-user").hasAuthority("ROLE_USER")
                // strony takie jak sign-up i register są domyślnie odbezpieczone
                .and()
                .formLogin().defaultSuccessUrl("/hello");   // moze wbić użytkownik hasrole . default success url umożliwi na przejście
        // odrazu na endpoint hello

    }
}
