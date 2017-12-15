package br.com.andrei.repository;

import static org.junit.Assert.assertEquals;

import java.time.DayOfWeek;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.andrei.RfbloyaltyApp;
import br.com.andrei.bootstrap.Bootstrap;
import br.com.andrei.domain.RfbLocation;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfbloyaltyApp.class)
public class RfbLocationRepositoryTest extends AbstractRepositoryTest {

	@Before
	public void setUp() throws Exception {
		Bootstrap bootstrap = new Bootstrap(rfbLocationRepository, rfbEventRepository, rfbEventAttendanceRepository, rfbUserRepository);
	}

	@Test
	public void findAllByRunDayOfWeek() {
	List<RfbLocation> mondayLocation = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.MONDAY.getValue());
	List<RfbLocation> tuesdayLocation = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.TUESDAY.getValue());
	List<RfbLocation> WednesdayLocation = rfbLocationRepository.findAllByRunDayOfWeek(DayOfWeek.WEDNESDAY.getValue());
	
	assertEquals(2, mondayLocation.size());
	assertEquals(2, tuesdayLocation.size());
	assertEquals(1, WednesdayLocation.size());
	}
	

}
