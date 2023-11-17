package com.A3.Trabalho.Security;

import com.A3.Trabalho.Model.User;
import com.A3.Trabalho.Repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /*
    * Método para pegar o username do banco de dados e enviar para o CustomUserDetails
    * para ser recuperado o username, a senha e a permissão.
    * */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Usuário não encontrado");
        }
        return new CustomUserDetails(user);
    }
}
