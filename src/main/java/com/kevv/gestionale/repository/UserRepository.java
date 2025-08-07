package com.kevv.gestionale.repository;

import com.kevv.gestionale.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    // Carica tutti gli utenti con i ruoli (fetch join per evitare LazyInitializationException)
    @Query("SELECT DISTINCT u FROM User u LEFT JOIN FETCH u.userRoles")
    List<User> findAllWithRoles();

    // Carica utente singolo con ruoli
    @Query("SELECT u FROM User u LEFT JOIN FETCH u.userRoles WHERE u.userid = :userid")
    Optional<User> findByIdWithRoles(@Param("userid") String userid);

    // Se vuoi un metodo che ritorna solo gli userid come stringhe
    @Query("SELECT u.userid FROM User u")
    List<String> findAllUserIds();



}
