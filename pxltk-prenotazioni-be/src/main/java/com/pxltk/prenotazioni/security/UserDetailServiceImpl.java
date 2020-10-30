package com.pxltk.prenotazioni.security;

import static java.util.Collections.emptyList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.pxltk.prenotazioni.entity.Utente;
import com.pxltk.prenotazioni.repository.UtenteRepository;

@Service
public class UserDetailServiceImpl implements UserDetailsService {

	private UtenteRepository applicationUserRepository;

    public UserDetailServiceImpl(UtenteRepository applicationUserRepository) {
        this.applicationUserRepository = applicationUserRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Utente applicationUser = applicationUserRepository.findByCredenziali_Email(email);
        if (applicationUser == null) {
            throw new UsernameNotFoundException(email);
        }
        return new User(applicationUser.getCredenziali().getEmail(), applicationUser.getCredenziali().getPassword(), emptyList());
    }

}
