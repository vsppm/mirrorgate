package com.bbva.arq.devops.ae.mirrorgate.service;

import com.bbva.arq.devops.ae.mirrorgate.model.BaseModel;
import com.bbva.arq.devops.ae.mirrorgate.model.Event;
import com.bbva.arq.devops.ae.mirrorgate.model.EventType;
import com.bbva.arq.devops.ae.mirrorgate.repository.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class EventServiceImpl implements EventService{

    private static final Logger LOGGER = LoggerFactory.getLogger(EventServiceImpl.class);

    private final EventRepository eventRepository;


    @Autowired
    public EventServiceImpl(EventRepository eventRepository){

        this.eventRepository = eventRepository;
    }

    @Override
    public void saveEvent(BaseModel baseObject, EventType type){
        LOGGER.info("Saving event with Id :{}", baseObject.getId());

        eventRepository.save(new Event()
            .setEventType(type)
            .setEventTypeCollectionId(baseObject.getId())
            .setTimestamp(System.currentTimeMillis())
        );
    }

    @Override
    public void saveEvents(Iterable<? extends BaseModel> reviews, EventType type) {
        LOGGER.info("Saving list of events");

        List<Event> eventList = StreamSupport.stream(reviews.spliterator(), false)
            .map(review -> new Event()
                .setEventType(type)
                .setEventTypeCollectionId(review.getId())
                .setTimestamp(System.currentTimeMillis())
            ).collect(Collectors.toList());

        eventRepository.saveAll(eventList);
    }

    @Override
    public Event getLastEvent(){
        return eventRepository.findFirstByOrderByTimestampDesc();
    }

    @Override
    public List<Event> getEventsSinceTimestamp(Long timestamp){
        return eventRepository.findByTimestampGreaterThanOrderByTimestampAsc(timestamp);
    }
}
