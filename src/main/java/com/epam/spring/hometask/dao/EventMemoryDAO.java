package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.idao.EventDAO;
import com.epam.spring.hometask.iservice.EventService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class EventMemoryDAO implements EventDAO {
    private static Map<Long, Event> eventMap = new HashMap<>();

    public EventMemoryDAO(Map<Long, Event> eventMap)
    {
        this.eventMap = eventMap;
    }

    @Nullable
    @Override
    public Event getByName(@Nonnull String name) {
        for (Event event : eventMap.values()) {
            if (event.getName().equals(name)) {
                return event;
            }
        }
        return null;
    }

    @Nonnull
    @Override
    public Set<Event> getForDateRange(@Nonnull LocalDate from, @Nonnull LocalDate to) {
        Set<Event> eventsRange = new HashSet<>();
        for (Event event : eventMap.values()) {
            if(event.airsOnDates(from,to))
            {
                eventsRange.add(event);
            }
        }
        return eventsRange;
    }

    @Nonnull
    @Override
    public Set<Event> getNextEvents(@Nonnull LocalDateTime to) {
        Set<Event> eventsRange = new HashSet<>();
        for (Event event : eventMap.values()) {
            if(event.airsOnDates(LocalDate.now(), to.toLocalDate()))
            {
                eventsRange.add(event);
            }
        }
        return eventsRange;
    }

    @Override
    public Event save(@Nonnull Event object) {
        return eventMap.put(object.getId(), object);
    }

    @Override
    public void remove(@Nonnull Event object) {
        eventMap.remove(object.getId());
    }

    @Override
    public Event getById(@Nonnull Long id) {
        return eventMap.get(id);
    }

    @Nonnull
    @Override
    public Collection<Event> getAll() {
        return eventMap.values();
    }
}
