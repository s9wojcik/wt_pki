package pl.assecods.pkitest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import pl.assecods.pkitest.entity.User;
import pl.assecods.pkitest.repository.UserRepository;

@Component
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
	
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
        Optional<User> userFromDatabase = userRepository.findOneByLogin(username);
    	
        return userFromDatabase.map(user -> {
            List<GrantedAuthority> grantedAuthorityList = new ArrayList<GrantedAuthority>();
            grantedAuthorityList.add(new GrantedAuthority() {  
            	public String getAuthority() {
            		return "USER"; 
            	}
            });
            
            System.out.println("Zalogowano: " + user.toString());
        	
            return new org.springframework.security.core.userdetails.User(
            		user.getLogin() , user.getPassword(), grantedAuthorityList);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + username + " was not found in the database"));
    }    
}
