package com.pxltk.prenotazioni.repository;

import com.pxltk.prenotazioni.entity.Prenotazione;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Repository
public interface PrenotazioneRepository extends CrudRepository<Prenotazione, Integer> {
    @Query(value = "SELECT * FROM prenotazione where data = ?1", nativeQuery = true)
    LinkedList<Prenotazione> getPrenotazioneByData(Date data);

    @Query(value = "UPDATE prenotazione SET data = ?2 WHERE id = ?1 RETURNING *", nativeQuery = true)
    Prenotazione modificaPrenotazione(Integer id, Date data);
    
    @Query(value = "SELECT p FROM prenotazione p, utente u, credenziali c WHERE c.email = ?1, u.credenziali_id = c.id, p.utente_id = u.id", nativeQuery = true)
    LinkedList<Prenotazione> getPrenotazionesByEmailUtente(String email);

	List<Prenotazione> findAllByUtente_id(int id);
}
