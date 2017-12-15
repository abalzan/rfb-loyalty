package br.com.andrei.repository;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractRepositoryTest {

	@Autowired
	RfbLocationRepository rfbLocationRepository;
	
	@Autowired
	RfbEventRepository rfbEventRepository;
	
	@Autowired
	RfbEventAttendanceRepository rfbEventAttendanceRepository;
	
	@Autowired
	RfbUserRepository rfbUserRepository;
}
