package pl.bykowski.springsecuritysimplefactorauth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.springsecuritysimplefactorauth.model.AppUser;
import pl.bykowski.springsecuritysimplefactorauth.model.Token;

import java.util.Optional;

@Repository
public interface TokenRepo extends JpaRepository<Token, Long> { // JPA repo przyjmuje encje (entity)
// Użytkownik w momencie gdy dokona rejestracji będzie zablokowany w AppUserze trzeba zrobić flagę isEnabled aktulanie jest na true dostęp odrazu

    Token findByValue(String value);

// trzeba jeszcze zrobić zabezpieczenie przed ręcznym wpisywaniu tokenów, najlepiej poprzez optionale jak zaczęte w pliku AppUserRepo, a obsługujemy to w UserDeatailImpl
// materiał "jak wykorzystywać optionale" youtube bykowski 23 minuty jak robić taką obsługę 16 minuta? 
}
