package pl.bykowski.springsecuritysimplefactorauth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.bykowski.springsecuritysimplefactorauth.model.AppUser;
import pl.bykowski.springsecuritysimplefactorauth.model.Token;
import pl.bykowski.springsecuritysimplefactorauth.repo.AppUserRepo;
import pl.bykowski.springsecuritysimplefactorauth.repo.TokenRepo;

import javax.mail.MessagingException;
import java.util.UUID;

// będziemy tworzyć metody pozwalające na zapisywanie nowego użytkownika
@Service
public class UserService {
    // będziemy tworzyć metody pozwalające na zapisywanie nowego użytkownika

    // zapisywanie do repo z czystym sumieniem;

    private TokenRepo tokenRepo; // Teraz token bedziemy zapisywac do bazy danych wstrzykując token

    private MailService mailService; // wstrzykujemy mailService do wysyłania tokena

    // wstrzyknac password encodera
    private AppUserRepo appUserRepo; // trzeba wstrzyknąć repozytorium
    private PasswordEncoder passwordEncoder; // trzeba wstrzyknąć password Encoder

    public UserService(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder, TokenRepo tokenRepo, MailService mailService) {
        this.appUserRepo = appUserRepo;
        this.passwordEncoder = passwordEncoder;
        this.tokenRepo = tokenRepo;
        this.mailService = mailService;
    }

    public void addUser(AppUser appUser) {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword())); // użytkownik podaje haslo w postaci jawnej. Moim zadaniem zapisanie do bazy danych już z z haslem zaszyfrowanym
        appUser.setRole("ROLE_USER"); // USTAWIAMY DOMYŚLNIE ROLE USER ZEBY MIAL JAKAS ROLE NA STARCIE
        appUserRepo.save(appUser);// Zapisuje do bazy danych mojego użytkownika save appUser
        sendToken(appUser); // Metoda umożliwiająca na wysłanie Tokena mailem do użytkownika - appUser info do usera

    }

    private void sendToken(AppUser appUser) {
        String tokenValue = UUID.randomUUID().toString(); // Biorę wartość randomowego tokena UUID.randomUUID
        // Teraz token bedziemy zapisywac do bazy danych wstrzykując token
        Token token = new Token(); // utworze token który zapisze do bazy danych
        token.setValue(tokenValue); // token ma teraz info na temat wartości
        token.setAppUser(appUser); // token musi mieć też informacje na temat usera musimy tego usera appUser przekazać do którego ten token będzie przepisany
        tokenRepo.save(token); // token zapisany do bazy dancyh
        // ten sam token wyśle użytkownikowi jak potwierdzi że wpisał odpowiedni token powinien dostać info że konto jest odblokowane i może z niego korzystać

        // możemy url tez zmienić gdybyśmy mieli na heroku by podać na jakim porcie i endpoincie nasza aplikacja leży i umieścić to w applicationproperties
        // albo zdefiniować to poprzez pobranie to z server port zminnych środowiskowych aby nie za każdym razem było to ustawione na local hoscie
        String url = "http://localhost:8080/token?value=" + tokenValue;  // będziemy to wszystko wysyłać do użytkownka w postaci linku tokenu i adresu tego tokenu, towrzymy takie zapytania sprawdające czy token jest prawidłowy wysyłajac za pomocą klasy MailService

        // w momencie rejestrcji wysyłany jest token, token jest generowany, zapisywany do bazy danych i przekierowuuje na na taki konkretnie endpoint, ścieżkę aplikacji
        try {
            mailService.sendMail(appUser.getUsername(), "Potwierdzaj to!", url, false); // trzeba było by pobrać od użytkownika maila, nie założyliśmy go, username i mail to tosamo w założeniu
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }

}
