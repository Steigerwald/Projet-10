package com.bibliotheque.service;

import com.bibliotheque.entity.Role;
import com.bibliotheque.entity.User;
import com.bibliotheque.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;

@Service
@Transactional
public class CustomUserDetailsService implements UserDetailsService {

    Logger logger = (Logger) LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Autowired
    private UserRepository userRepository;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User user = userRepository.findByMailUser(userName)
                .orElseThrow(() -> new UsernameNotFoundException("Email " + userName + " not found"));

        logger.info(" on est passe par la avant getAuthorities de loadUserByUsername");

        return new org.springframework.security.core.userdetails.User(user.getMailUser(), user.getMotDePasse(),
                getAuthorities(user));
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(User user) {
        ArrayList<Role> listRole = new ArrayList<Role>();
        listRole.clear();
        listRole.add(user.getRole());
        String[] userRole = listRole.stream().map((role) -> role.getNomRole()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRole);
        return authorities;
    }
}
