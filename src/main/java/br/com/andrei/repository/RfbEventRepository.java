package br.com.andrei.repository;

import br.com.andrei.domain.RfbEvent;
import br.com.andrei.domain.RfbLocation;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RfbEvent entity.
 */
@Repository
public interface RfbEventRepository extends JpaRepository<RfbEvent, Long> {

	RfbEvent findByRfbLocationAndEventDate(RfbLocation location, LocalDate date);
}
