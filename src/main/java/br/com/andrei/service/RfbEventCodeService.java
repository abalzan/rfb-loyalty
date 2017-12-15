package br.com.andrei.service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.andrei.domain.RfbEvent;
import br.com.andrei.domain.RfbLocation;
import br.com.andrei.repository.RfbEventRepository;
import br.com.andrei.repository.RfbLocationRepository;

@Service
public class RfbEventCodeService {
	private final Logger log = LoggerFactory.getLogger(MailService.class);
	
	private RfbLocationRepository locationRepository;
	private RfbEventRepository eventRepository;
	
	public RfbEventCodeService(RfbLocationRepository locationRepository, RfbEventRepository eventRepository) {
		this.locationRepository = locationRepository;
		this.eventRepository = eventRepository;
	}
	
//	@Scheduled(cron="0 * * * * ?") //run once per minute
//	@Scheduled(cron="* * * * * ?") //run once per second
	@Scheduled(cron="0 0 * * * ?") //run once per hour
	public void generateRunEventCode() {

		log.debug("Generating Events");
		
		List<RfbLocation> locations = locationRepository.findAllByRunDayOfWeek(LocalDate.now().getDayOfWeek().getValue());
		
		locations.forEach(location -> {
			log.debug("Location id: " + location.getId());
			
			RfbEvent existEvent = eventRepository.findByRfbLocationAndEventDate(location, LocalDate.now());
			
			if (existEvent == null) {
				log.debug("No event Found Adding new event!");
				RfbEvent newEvent = new RfbEvent();
				newEvent.setRfbLocation(location);
				newEvent.setEventDate(LocalDate.now());
				newEvent.setEventCode(RandomStringUtils.random(10).toUpperCase());
				
				eventRepository.save(newEvent);
				log.debug("event added "+newEvent.toString());
			} else {
				log.debug("Event exists for the day");
			}
		});
		
	}
	
}
