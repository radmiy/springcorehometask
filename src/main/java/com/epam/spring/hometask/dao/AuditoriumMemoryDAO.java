package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.beans.Auditorium;
import com.epam.spring.hometask.idao.AuditoriumDAO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

public class AuditoriumMemoryDAO implements AuditoriumDAO {
    private static Set<Auditorium> auditoriumMap = new HashSet<>();

    public AuditoriumMemoryDAO() {
    }

    public AuditoriumMemoryDAO(Set<Auditorium> auditoriumMap) {
        this.auditoriumMap = auditoriumMap;
    }

    @Nonnull
    @Override
    public Set<Auditorium> getAll() {
        return auditoriumMap;
    }

    @Nullable
    @Override
    public Auditorium getByName(@Nonnull String name) {
        for (Auditorium auditorium : auditoriumMap) {
            if (auditorium.getName().equals(name)) {
                return auditorium;
            }
        }
        return null;
    }
}
