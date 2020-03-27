package pl.bykowski.springsecuritysimplefactorauth;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bykowski.springsecuritysimplefactorauth.model.AppUser;
import pl.bykowski.springsecuritysimplefactorauth.repo.AppUserRepo;

@Configuration
public class Start {
    private AppUserRepo appUserRepo;
// Dane na start bez zapisywania danych w bazie
    public Start(AppUserRepo appUserRepo, PasswordEncoder passwordEncoder) {

        AppUser appUserJanusz = new AppUser();
        appUserJanusz.setUsername("Janusz");
        appUserJanusz.setPassword(passwordEncoder.encode("Janusz123"));
        appUserJanusz.setRole("ROLE_ADMIN");
        appUserJanusz.setEnabled(true); // domyślnie dajemy im dostep w konfiguracji bazy danych

        AppUser appUserBogdan = new AppUser();
        appUserBogdan.setUsername("Bogdan");
        appUserBogdan.setPassword(passwordEncoder.encode("Bogdan123"));
        appUserBogdan.setRole("ROLE_USER"); // rola user
        appUserBogdan.setEnabled(true); // domyślnie dajemy im dostep w konfiguracji bazy danych

        appUserRepo.save(appUserJanusz);
        appUserRepo.save(appUserBogdan);
    }


}
