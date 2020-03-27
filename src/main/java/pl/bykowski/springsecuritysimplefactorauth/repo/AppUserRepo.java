package pl.bykowski.springsecuritysimplefactorauth.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bykowski.springsecuritysimplefactorauth.model.AppUser;

import java.util.Optional;

@Repository
public interface AppUserRepo extends JpaRepository<AppUser, Long> { // JPA repo przyjmuje encje oraz userid

    // elastyczna formula ktora umowżliwa na pobanie uzytkownika na podstawie jego name
    Optional<AppUser> findByUsername(String username);


}
