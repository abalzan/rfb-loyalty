package br.com.andrei.security;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.andrei.domain.User;
import br.com.andrei.repository.UserRepository;

/**
 * Authenticate a user from the database.
 */
@Component("userDetailsService")
public class DomainUserDetailsService implements UserDetailsService {

    private final Logger log = LoggerFactory.getLogger(DomainUserDetailsService.class);

    private final UserRepository userRepository;
    
    private HttpServletRequest httpServletRequest;
    
    private LoginAttemptService loginAttemptService;

    public DomainUserDetailsService(UserRepository userRepository, HttpServletRequest httpServletRequest,
			LoginAttemptService loginAttemptService) {
		this.userRepository = userRepository;
		this.httpServletRequest = httpServletRequest;
		this.loginAttemptService = loginAttemptService;
	}

	@Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("Authenticating {}", login);
        String ipAddress = getClientIP();
        
        if (loginAttemptService.isBlocked(ipAddress)) {
        	throw new LockedException("Access Blocked");
        }
        String lowercaseLogin = login.toLowerCase(Locale.ENGLISH);
        Optional<User> userFromDatabase = userRepository.findOneWithAuthoritiesByLogin(lowercaseLogin);
        return userFromDatabase.map(user -> {
            if (!user.getActivated()) {
                throw new UserNotActivatedException("User " + lowercaseLogin + " was not activated");
            }
            List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                .collect(Collectors.toList());
            return new org.springframework.security.core.userdetails.User(lowercaseLogin,
                user.getPassword(),
                grantedAuthorities);
        }).orElseThrow(() -> new UsernameNotFoundException("User " + lowercaseLogin + " was not found in the " +
        "database"));
    }

	private String getClientIP() {
		
		String xHeader = httpServletRequest.getHeader("X-Forwarded-For");
		if (xHeader == null) {
			return httpServletRequest.getRemoteAddr();
		}
		return xHeader.split(",")[0];
	}
}
