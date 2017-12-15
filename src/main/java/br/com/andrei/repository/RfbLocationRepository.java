package br.com.andrei.repository;

import br.com.andrei.domain.RfbLocation;
import org.springframework.stereotype.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the RfbLocation entity.
 */
@Repository
public interface RfbLocationRepository extends JpaRepository<RfbLocation, Long> {

	List<RfbLocation> findAllByRunDayOfWeek(Integer dayOfWeek);
	RfbLocation findByLocationName(String name);
	
}
