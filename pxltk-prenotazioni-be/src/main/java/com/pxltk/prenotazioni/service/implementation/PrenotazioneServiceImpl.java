package com.pxltk.prenotazioni.service.implementation;

import com.pxltk.prenotazioni.command.PrenotazioneCommand;
import com.pxltk.prenotazioni.command.search.SearchPrenotazioneCommand;
import com.pxltk.prenotazioni.dto.DataValidaDTO;
import com.pxltk.prenotazioni.dto.PrenotazioneDTO;
import com.pxltk.prenotazioni.entity.Prenotazione;
import com.pxltk.prenotazioni.entity.Utente;
import com.pxltk.prenotazioni.query.QueryPrenotazione;
import com.pxltk.prenotazioni.repository.PrenotazioneRepository;
import com.pxltk.prenotazioni.repository.UtenteRepository;
import com.pxltk.prenotazioni.service.iterface.IPrenotazioneService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;

@Service("PrenotaioneService")
public class PrenotazioneServiceImpl implements IPrenotazioneService {

    @Autowired
    private PrenotazioneRepository prenotazioneRepository;

    @Autowired
    private UtenteRepository utenteRepository;
    
    @Autowired
	private QueryPrenotazione queryPrenotazione;
    
    @Override
    public LinkedList<PrenotazioneDTO> leggiPrenotazioniByData(String data) throws ParseException {
        Date dateParsed=new SimpleDateFormat("yyyy-MM-dd").parse(data);
        SearchPrenotazioneCommand command = new SearchPrenotazioneCommand();
        command.setData(dateParsed);
        List<Prenotazione> prenotazioni = queryPrenotazione.getBookingListBy(command);
        
        //LinkedList<Prenotazione> prenotazioni = prenotazioneRepository.getPrenotazioneByData(dateParsed);
        LinkedList<PrenotazioneDTO> prenotazioniDTO = new LinkedList<>();
        for (Prenotazione prenotazione:prenotazioni) {
            prenotazioniDTO.add(new PrenotazioneDTO(prenotazione.getData().getTime(), prenotazione.getId()));
        }
        return prenotazioniDTO;
    }

    @Override
    public LinkedList<PrenotazioneDTO> leggiTuttePrenotazioni() {
        Iterable<Prenotazione> prenotazioni = prenotazioneRepository.findAll();
        LinkedList<PrenotazioneDTO> prenotazioniDTO = new LinkedList<PrenotazioneDTO>(); 
        for(Prenotazione prenotazione: prenotazioni) {
        	prenotazioniDTO.add(new PrenotazioneDTO(prenotazione.getData().getTime(), prenotazione.getId()));
        	System.out.println(prenotazione.getData() + " " + prenotazione.getId());
        }
        return prenotazioniDTO;
    }

    @Override
    public PrenotazioneDTO creaPrenotazione(PrenotazioneCommand prenotazioneCommand) {
        Prenotazione prenotazione = new Prenotazione();
        Date date = new Date(prenotazioneCommand.getData());
        prenotazione.setData(date);
        Utente utente = utenteRepository.findByCredenziali_Email(prenotazioneCommand.getEmail());
        prenotazione.setUtente(utente);
        prenotazioneRepository.save(prenotazione);
        return new PrenotazioneDTO(prenotazione.getData().getTime(), prenotazione.getId());
    }

    @Override
    public PrenotazioneDTO modificaPrenotazione(int id, long data) throws ParseException{
        Date dateParsed=new Date(data);
        Prenotazione prenotazione = prenotazioneRepository.modificaPrenotazione(id, dateParsed);
        return new PrenotazioneDTO(prenotazione.getData().getTime(), prenotazione.getId());
    }

    @Override
    public LinkedList<PrenotazioneDTO> leggiPrenotazioniByEmail(String email) {
        SearchPrenotazioneCommand command = new SearchPrenotazioneCommand();
        command.setEmail(email);
        Utente utente = utenteRepository.findByCredenziali_Email(email);
        List<Prenotazione> prenotazioni = prenotazioneRepository.findAllByUtente_id(utente.getId());
        LinkedList<PrenotazioneDTO> prenotazioniDTO = new LinkedList<PrenotazioneDTO>();
        for (Prenotazione prenotazione : prenotazioni) {
        	prenotazioniDTO.add(new PrenotazioneDTO(prenotazione.getData().getTime(),prenotazione.getId()));
		} 
        return prenotazioniDTO;
    }

    @Override
    public PrenotazioneDTO leggiPrenotazioneByIdPrenotazione(int id) {
        if (prenotazioneRepository.existsById(id)) {
            Optional<Prenotazione> prenotazione = prenotazioneRepository.findById(id);
            return new PrenotazioneDTO(prenotazione.get().getData().getTime(), prenotazione.get().getId());
        }
        return new PrenotazioneDTO();
    }
    	
	@Override
	public PrenotazioneDTO eliminaPrenotazione(int id) {
		if (prenotazioneRepository.existsById(id)) {
			Optional<Prenotazione> prenotazione = prenotazioneRepository.findById(id);
			prenotazioneRepository.deleteById(id);
			return new PrenotazioneDTO(prenotazione.get().getData().getTime(), prenotazione.get().getId());
		}
		return new PrenotazioneDTO();
	}

	@Override
	public LinkedList<DataValidaDTO> getDateValide() throws ParseException {
		final long MILLIS_IN_A_DAY = 1000 * 60 * 60 * 24;
		long millis=System.currentTimeMillis()+MILLIS_IN_A_DAY;
		LinkedList<DataValidaDTO> dateValide = new LinkedList<>();
		
		for (int i=0; i<30; i++) {
			
	        DataValidaDTO dvDTO = new DataValidaDTO();
			dvDTO.setData(millis);
			dvDTO.setValida(true);
			dateValide.add(dvDTO);
			millis += MILLIS_IN_A_DAY;

		}
		LinkedList<PrenotazioneDTO> allBooking = leggiTuttePrenotazioni();
		for (PrenotazioneDTO prenotazioneDTO : allBooking) {
			for (DataValidaDTO dateValideDTO : dateValide) {
				Date vD = new Date(dateValideDTO.getData());
				Date pdtoD = new Date(prenotazioneDTO.getData());
				if (vD.getDate()==pdtoD.getDate() && vD.getMonth()==pdtoD.getMonth() && vD.getYear()==pdtoD.getYear()) {
					dateValideDTO.setValida(false);
				}
			}
		}
		
		return dateValide;
	}


}
