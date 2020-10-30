package com.pxltk.prenotazioni.repository;

import com.pxltk.prenotazioni.entity.Credenziali;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.Date;

@Repository
public interface CredenzialiRepository extends CrudRepository<Credenziali, Integer> {
    @Query(value = "UPDATE credenziali SET email = ?2, password = ?3, edited_at = ?4 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Credenziali aggiornaCredenziali(Integer id, String email, String password, Timestamp edited_at);

    Credenziali findByEmail(String email);
}
