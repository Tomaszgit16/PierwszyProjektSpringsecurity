package pl.bykowski.springsecuritysimplefactorauth.model;

import javax.persistence.*;

// Token który wysyłamy swoim użytkownikom jest to model Entity będzie zapisywany do bazy danych
@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value; // wartość dla Tokenu

    //Entity to encja powiązane z bazą danych, dlaczego robic  ztokna entity bo bazy danych mają praktyki postacie normalne (rozróżnienie) jest to chyba trzecia praktyka, dla programowania sa praktywki Solid, KISS
    @OneToOne // związek onetoone 1 token przyporządkowany jest jednemu użytkownkowi tworzymy to tylko po stronie klasy token, użytkownik nie musi nic wiedizeć na temat tokena, wiązanie jednokierunkowe gdzie token wie o userze ale user nie wie nic o tokenie daltego nei tworzymy onetoone w przypadku klasy AppUsera jest na stronie bykowski.pl
    private AppUser appUser; //użytkownik z  jakim jest Token powiązany - indywidualny token

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
