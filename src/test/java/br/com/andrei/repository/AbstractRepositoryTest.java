package br.com.andrei.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public abstract class AbstractRepositoryTest {

	@Autowired
	RfbLocationRepository rfbLocationRepository;
	
	@Autowired
	RfbEventRepository rfbEventRepository;
	
	@Autowired
	RfbEventAttendanceRepository rfbEventAttendanceRepository;
	
	@Autowired
	UserRepository userRepository;
	
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AuthorityRepository authorityRepository;
}
