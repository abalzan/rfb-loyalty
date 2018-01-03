package br.com.andrei.repository;

import static org.junit.Assert.*;

import java.time.LocalDate;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.andrei.RfbloyaltyApp;
import br.com.andrei.bootstrap.Bootstrap;
import br.com.andrei.domain.RfbEvent;
import br.com.andrei.domain.RfbLocation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfbloyaltyApp.class)
public class RfbEventRepositoryTest extends AbstractRepositoryTest {

	@Before
	public void setUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap(rfbLocationRepository, rfbEventRepository, rfbEventAttendanceRepository, userRepository, passwordEncoder,authorityRepository);
		
	}

	@Test
	public void findByRfbLocationAndEventDate() throws Exception {
		
		RfbLocation location = rfbLocationRepository.findByLocationName("St Pete - Ale and the Witch");
		
		assertNotNull(location);
		
		RfbEvent event = rfbEventRepository.findByRfbLocationAndEventDate(location, LocalDate.now());
		
		assertNotNull(event);
	}

}
