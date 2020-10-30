package com.pxltk.prenotazioni.repository;

import com.pxltk.prenotazioni.entity.Utente;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;


/**
 * @author Gregorio Graziano
 * @email gregoriograziano@gmail.com
 */

@Repository
public interface UtenteRepository extends CrudRepository<Utente, Integer>{

    @Query(value = "SELECT * FROM utente U JOIN credenziali C ON U.id = C.id where U.id = ?1", nativeQuery = true)
    Utente getUtenteECredenziali(Integer id);

    @Query(value = "UPDATE utente SET nome = ?2, cognome = ?3, data_nascita = ?4 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Utente aggiornaUtente(Integer id, String nome, String cognome, Date data);
    
    Utente findByCredenziali_Id(int id);
    
    Utente findByCredenziali_Email(String email);
    
}
