package br.com.andrei.service.impl;

import br.com.andrei.service.RfbEventService;
import br.com.andrei.domain.RfbEvent;
import br.com.andrei.domain.RfbLocation;
import br.com.andrei.repository.RfbEventRepository;
import br.com.andrei.repository.RfbLocationRepository;
import br.com.andrei.service.dto.RfbEventDTO;
import br.com.andrei.service.mapper.RfbEventMapper;

import java.time.LocalDate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing RfbEvent.
 */
@Service
@Transactional
public class RfbEventServiceImpl implements RfbEventService{

    private final Logger log = LoggerFactory.getLogger(RfbEventServiceImpl.class);

    private final RfbEventRepository rfbEventRepository;
    private final RfbLocationRepository locationRepository;

    private final RfbEventMapper rfbEventMapper;

    public RfbEventServiceImpl(RfbEventRepository rfbEventRepository, RfbLocationRepository locationRepository,
			RfbEventMapper rfbEventMapper) {
		this.rfbEventRepository = rfbEventRepository;
		this.locationRepository = locationRepository;
		this.rfbEventMapper = rfbEventMapper;
	}

	/**
     * Save a rfbEvent.
     *
     * @param rfbEventDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RfbEventDTO save(RfbEventDTO rfbEventDTO) {
        log.debug("Request to save RfbEvent : {}", rfbEventDTO);
        RfbEvent rfbEvent = rfbEventMapper.toEntity(rfbEventDTO);
        rfbEvent = rfbEventRepository.save(rfbEvent);
        return rfbEventMapper.toDto(rfbEvent);
    }

    /**
     * Get all the rfbEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfbEventDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RfbEvents");
        return rfbEventRepository.findAll(pageable)
            .map(rfbEventMapper::toDto);
    }

    /**
     * Get one rfbEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public RfbEventDTO findOne(Long id) {
        log.debug("Request to get RfbEvent : {}", id);
        RfbEvent rfbEvent = rfbEventRepository.findOne(id);
        return rfbEventMapper.toDto(rfbEvent);
    }
    
    /**
     * Get one rfbEvent by id.
     *
     * @param locationID the id of the entity
     * @return the entity
     */
   
    @Override
    public RfbEventDTO findByTodayAndLocation(Long locationID) {
        RfbLocation location = locationRepository.findOne(locationID);
        RfbEvent rfbEvent = rfbEventRepository.findByEventDateEqualsAndRfbLocationEquals(LocalDate.now(),location);
        return rfbEventMapper.toDto(rfbEvent);
    }

    /**
     * Delete the rfbEvent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfbEvent : {}", id);
        rfbEventRepository.delete(id);
    }
}
