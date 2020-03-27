package pl.bykowski.springsecuritysimplefactorauth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import  javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Collection;
import java.util.Collections;

@Entity // Bedzie zapisaywan do bazy danych ta encja
public class AppUser implements UserDetails { // userdetail usługę z poziomu spring security dostawrcza interface
    // userdetail ktory umożliwa na pobranie danych username i password czy nie wygasło czy nie zablokowane
    // trzeba implemmentowac dane poprzez generate - > implements wszystkie metody narzucae przez interfejs

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;
    private String username;
    private String password;
    private String role; // role użytkownika
    private boolean isEnabled; // do tego żeby pokazywał czy użytkownik jest czy nie jest odblokowany, w momencie rejestracji ta wartość jest ustawiona defaultowo na false

    public void setEnabled(boolean enabled) {
        isEnabled = enabled;
    }




    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority(role)); // rola dla uzytkownika opakowane do kolekji jendolementowej
        //zamieniam role na role z 1 listą
    }

    @Override
    public String getPassword() {
        return password; // podstawiamy pod wygenerowane null password
    }

    @Override
    public String getUsername() {
        return username; // podstawiamy pod wygenerowane null username
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // przestawiamy reszte wartosci na true konto nie wygasle nie zablokowane credentiale itd
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return isEnabled; // zwracamy isEnaled boolean, wcześniej było odrazu true dawało to dostep odrazu, działać będzie z tokenem
    }


    // alt+insert -> do toString Teraz ten użytkonik będzie wyświetlony
    @Override
    public String toString() {
        return "AppUser{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }


}
