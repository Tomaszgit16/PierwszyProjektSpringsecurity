package pl.bykowski.springsecuritysimplefactorauth.controller;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.bykowski.springsecuritysimplefactorauth.model.Token;
import pl.bykowski.springsecuritysimplefactorauth.repo.AppUserRepo;
import pl.bykowski.springsecuritysimplefactorauth.repo.TokenRepo;
import pl.bykowski.springsecuritysimplefactorauth.service.UserService;
import pl.bykowski.springsecuritysimplefactorauth.model.AppUser;

import java.security.Principal;
import java.util.Collection;

@Controller
public class UserController {

    private UserService userService; // tworzymy service do przesyłania danych do bazy danych aby była odseparowana logika
    private AppUserRepo appUserRepo; // do zapisania swojego nowego użytkownika po potwierdzeniu tokena jeszcze raz w bazie danych
    private TokenRepo tokenRepo; // wstrzykuję repozytorium gdzie mogę sprawdzić że wartość value tokena jest prawidłowa z token repo będziemy wyciagać info czy rzeczywiście taki token się znajduje

    public UserController(UserService userService, AppUserRepo appUserRepo, TokenRepo tokenRepo) {
        this.userService = userService;
        this.appUserRepo = appUserRepo;
        this.tokenRepo = tokenRepo;
    }


    //for REST value returned
    // @GetMapping("/hello") Gdybyśmy chcieli wrzucić resta to musi to być response body
    // @ResponseBody
    //public String hello(){
    //    return "hello";
    // }

    @GetMapping("/hello")
    public String hello(Principal principal, Model model) { // do modelu przekazujemy name
        model.addAttribute("name", principal.getName()); // będzie mozliwe przekazanie do mojego pliku html i przywitać użytkownika jego name
        Collection<? extends GrantedAuthority> authorities = SecurityContextHolder.getContext().getAuthentication().getAuthorities(); // kolekcja lista elementów
        Object details = SecurityContextHolder.getContext().getAuthentication().getDetails(); // sprawdzamy co daje IP, numer sesji itd
        model.addAttribute("authorities", authorities);
        model.addAttribute("details", details);
        return "hello"; // te inforacje na temat imienia principal.getname przekażemy do hello
    }

    @GetMapping("/sign-up")
    public String signup(Model model) { // trzeba zrobic tez bez informacji strone -  tworzymy model do ktorego przekaze pustego uzytkownika
        model.addAttribute("user", new AppUser()); // kto wejdzie formatke dostanie login i password jak wypelni dane to te dane wejda w miesjce AppUsera
        return "sign-up"; // domyslnie jest pusty AppUser - uzupełniamy go na sing-up /register w input i w momencie przycisku input submit
    }

    @PostMapping("/register")
    // po przekazaniu informacji metodą post i zmień adres strony na zmapowaną nazwę /register na stronę return "sign-up"
    public String register(AppUser appUser) { // tutaj przejdzie z informacja od użytkownika
        //System.out.println(appUser); //sprawdzamy czy przekazuje dane w konsoli jeszcze przed userService przesyłanie do bazy danych
        userService.addUser(appUser); //odseparowana logika przesyłania danych do bazy danych
        return "sign-up"; // sing-up.html?? nie dziala linkowanie? zmieniono na sign-up z sing-up

    }

    // metoda która będzie nam pozwalała na sprawdzenie czy ten token jest prawidłowy
    @GetMapping("/token") // towrzymy endpoint o nazwie token
    public String signup(@RequestParam String value) { // jest to value przekazywane w linku do kliknięcia
        Token byValue = tokenRepo.findByValue(value); // z token repo będziemy wyciagać info czy rzeczywiście taki token się znajduje, pobiieramy takie value
        AppUser appUser = byValue.getAppUser(); // wówczas mogę pobrać użytkownika do którego przypisany jest Token
        appUser.setEnabled(true); //swojemu użytkownikowi ustawiamy setEnabled na true i tego użytkownika spowrotem trzeba zapisać do bazy danych
        appUserRepo.save(appUser); // w Momencie jak się użytkownik się rejestruje to jest zapisywany do bazy danych ale jest zablokowany isEnabled na false,
        // wówczas ja wysyłam mu token jeśli zostanie stworzony token to zostanie wysłany link url z wykorzystaniem maila. Jak użytkownik kliknie na link to
        // przeniesie go na endpoint /token sprawdza czy taki token znajduje sie w bazie danych jeśli się znajduje to pobiera użytkownika który jest przypisany do tego tokenu
        // i nast ustawia flage enable na true i zapisuje do bazy danych i przenosi go do hello
        return "hello"; // i przenosi go do hello - endpoint jest pusty wiec danych nie pokazuje ?? odrazu się nieloguje

    }
}