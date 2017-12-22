package br.com.andrei.bootstrap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import br.com.andrei.domain.RfbEvent;
import br.com.andrei.domain.RfbEventAttendance;
import br.com.andrei.domain.RfbLocation;
import br.com.andrei.domain.User;
import br.com.andrei.repository.RfbEventAttendanceRepository;
import br.com.andrei.repository.RfbEventRepository;
import br.com.andrei.repository.RfbLocationRepository;
import br.com.andrei.repository.UserRepository;

@Component
public class Bootstrap implements CommandLineRunner {

	private final RfbLocationRepository rfbLocationRepository;
	private final RfbEventRepository rfbEventRepository;
	private final RfbEventAttendanceRepository rfbEventAttendanceRepository;
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public Bootstrap(RfbLocationRepository rfbLocationRepository, RfbEventRepository rfbEventRepository,
	                 RfbEventAttendanceRepository rfbEventAttendanceRepository, UserRepository userRepository,
	                 PasswordEncoder passwordEncoder) {
	        this.rfbLocationRepository = rfbLocationRepository;
	        this.rfbEventRepository = rfbEventRepository;
	        this.rfbEventAttendanceRepository = rfbEventAttendanceRepository;
	        this.userRepository = userRepository;
	        this.passwordEncoder = passwordEncoder;
	    }

	@Transactional
	@Override
	public void run(String... strings) throws Exception {

		// init RFB Locations
		if (rfbLocationRepository.count() == 0) {
			// only load data if no data loaded
			initData();
		}
	}

	private void initData() {
		User user = new User();
		user.setFirstName("Johnny");
		user.setPassword(passwordEncoder.encode("admin"));
		user.setActivated(true);
		user.setLogin("johnny");
		userRepository.save(user);
		

		// load data
		RfbLocation aleAndWitch = getRfbLocation("St Pete - Ale and the Witch", DayOfWeek.MONDAY.getValue());

		user.setHomeLocation(aleAndWitch);
		userRepository.save(user);

		RfbEvent aleEvent = getRfbEvent(aleAndWitch);

		getRfbEventAttendance(user, aleEvent);

		RfbLocation ratc = getRfbLocation("St Pete - Right Around The Corner", DayOfWeek.TUESDAY.getValue());

		RfbEvent ratcEvent = getRfbEvent(ratc);

		getRfbEventAttendance(user, ratcEvent);

		RfbLocation stPeteBrew = getRfbLocation("St Pete - St Pete Brewing", DayOfWeek.WEDNESDAY.getValue());

		RfbEvent stPeteBrewEvent = getRfbEvent(stPeteBrew);

		getRfbEventAttendance(user, stPeteBrewEvent);

		RfbLocation yardOfAle = getRfbLocation("St Pete - Yard of Ale", DayOfWeek.THURSDAY.getValue());

		RfbEvent yardOfAleEvent = getRfbEvent(yardOfAle);

		getRfbEventAttendance(user, yardOfAleEvent);

	}

	private void getRfbEventAttendance(User user, RfbEvent rfbEvent) {
		RfbEventAttendance rfbAttendance = new RfbEventAttendance();
		rfbAttendance.setRfbEvent(rfbEvent);
		rfbAttendance.setUser(user);
		rfbAttendance.setAttendanceDate(LocalDate.now());

		System.out.println(rfbAttendance.toString());

		rfbEventAttendanceRepository.save(rfbAttendance);
		rfbEventRepository.save(rfbEvent);
	}

	private RfbEvent getRfbEvent(RfbLocation rfbLocation) {
		RfbEvent rfbEvent = new RfbEvent();
		rfbEvent.setEventCode(UUID.randomUUID().toString());
		rfbEvent.setEventDate(LocalDate.now()); // will not be on assigned day...
		rfbLocation.addRfbEvent(rfbEvent);
		rfbLocationRepository.save(rfbLocation);
		rfbEventRepository.save(rfbEvent);
		return rfbEvent;
	}

	private RfbLocation getRfbLocation(String locationName, int value) {
		RfbLocation rfbLocation = new RfbLocation();
		rfbLocation.setLocationName(locationName);
		rfbLocation.setRunDayOfWeek(value);
		rfbLocationRepository.save(rfbLocation);
		return rfbLocation;
	}
}
