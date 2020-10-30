package com.pxltk.prenotazioni.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pxltk.prenotazioni.command.search.SearchPrenotazioneCommand;
import com.pxltk.prenotazioni.entity.Prenotazione;
import com.pxltk.prenotazioni.entity.Utente;

@Service("QueryPrenotazione")
@Scope("singleton")
public class QueryPrenotazione {
	
	@Autowired
	private EntityManager em;
	
	public List<Prenotazione> getBookingListBy(SearchPrenotazioneCommand command) {
		String SQL = "SELECT p FROM Prenotazione p";
		if(command.getEmail()!=null)
			SQL = SQL + ", Utente u, Credenziali c";
		
		List<String> sqlWhere = new ArrayList<>();
		String sqlOrder = "";
		
		if(command.getId()!=null)
			sqlWhere.add(" p.id = " + command.getId());
		if(command.getData()!=null)
			sqlWhere.add(" p.data = " + command.getData());
		if(command.getEmail()!=null)
			sqlWhere.add(" c.email = :email");
		
		sqlOrder += " ORDER BY p." + (command.getOrderByDate() !=null ? command.getOrderByDate() : "data"); 
		sqlOrder += " " + (command.getOrderByType() != null ? command.getOrderByType() : "ASC");
		
		SQL = SQL + (sqlWhere.size() > 0 ? " WHERE" + String.join(" AND ", sqlWhere) : "") + sqlOrder;
		
		TypedQuery<Prenotazione> q = em.createQuery(SQL, Prenotazione.class);
		
		if (command.getEmail()!=null)
			q.setParameter("email", command.getEmail());
		System.out.println(command.getEmail());
		System.out.println(SQL);
		if(command.getSkip()!=null && command.getTake()!=null) {
			int offset = command.getOffset();
			int take = command.getTake();
			q.setFirstResult(offset).setMaxResults(take);
		}
		System.out.println(q.getResultList().size());
		return  q.getResultList();
	}
	
}
