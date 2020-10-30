package com.pxltk.prenotazioni.query;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.pxltk.prenotazioni.command.search.SearchUtenteCommand;
import com.pxltk.prenotazioni.entity.Utente;

@Service("QueryUtente")
@Scope("singleton")
public class QueryUtente {
	
	@Autowired
	private EntityManager em;
	
	public List<Utente> getUserListBy(SearchUtenteCommand command) {
		String SQL = "SELECT c FROM Utente c";
		if (command.getIdPrenotazione()!=null || command.getDataPrenotazione()!=null)
			SQL = SQL + ", Prenotazione p";
		
		List<String> sqlWhere = new ArrayList<>();
		String sqlOrder = "";
		
		if(command.getNome()!=null)
			sqlWhere.add(" c.nome = "+ command.getNome());
		if(command.getEmail()!=null)
			sqlWhere.add(" c.email = :email");
		if(command.getIdRuolo()!=null)
			sqlWhere.add(" c.idRuolo = "+ command.getIdRuolo());
		if(command.getCognome()!=null)
			sqlWhere.add(" c.cognome = "+ command.getCognome());
		if(command.getDataNascita()!=null)
			sqlWhere.add(" c.data_nascita = "+ command.getDataNascita());
		if(command.getIdPrenotazione()!=null)
			sqlWhere.add(" p.id ="+ command.getIdPrenotazione());
		if(command.getDataPrenotazione()!=null)
			sqlWhere.add(" p.data ="+ command.getDataPrenotazione());
		
		sqlOrder += " ORDER BY c." + (command.getOrderByName() !=null ? command.getOrderByName() : "nome");
		sqlOrder += " " + (command.getOrderByType() != null ? command.getOrderByType() : "ASC");
		
		SQL = SQL + (sqlWhere.size() > 0 ? " WHERE " + String.join(" AND ", sqlWhere) : "") + sqlOrder;
		
		TypedQuery<Utente> q = em.createQuery(SQL, Utente.class);
		if (command.getEmail()!=null)
			q.setParameter("email", command.getEmail());
		if(command.getSkip()!=null && command.getTake()!=null) {
			int offset = command.getOffset();
			int take = command.getTake();
			q.setFirstResult(offset).setMaxResults(take);
		}
		return  q.getResultList();
	}
}
