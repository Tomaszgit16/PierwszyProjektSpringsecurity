package pl.bykowski.springsecuritysimplefactorauth.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.bykowski.springsecuritysimplefactorauth.repo.AppUserRepo;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
// warstwa ktora posredniczy pomiedzy repo a konfiguracja springa

    ///wczytywanie z repo użytkownika
    private AppUserRepo appUserRepo; // tworzymy konstrukotr i wstrzykujemy appUserRepo autowire w nowszych wersjach springa nie jest konieczne stowosowanie jesli caly projekt z autowired to m=powinien być jesli nowy projekt mozemy z niego zrezygnowac

    public UserDetailsServiceImpl(AppUserRepo appUserRepo) {
        this.appUserRepo = appUserRepo;
    }

    //trzba pobrać użytkownika na pdosawie jego name
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException { // load by username na podstawie danych użytkownika pobrać jego szczegołowe dane

        //todo throw if not exist orelsethrow zapewnic obslugo co ma sie stac gdy nieznajdize tego elementu optionale w appuserRepo
        return appUserRepo.findByUsername(s).get(); // wrzucamy typ appuser weic w ramach tego interfejsu typem zwracanym jest user details appuser jest typem userdetails
    }
}
