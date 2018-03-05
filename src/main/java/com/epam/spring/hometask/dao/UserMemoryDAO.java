package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.idao.UserDAO;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserMemoryDAO implements UserDAO {
    private static Map<Long, User> userMap = new HashMap<>();

    public UserMemoryDAO(Map<Long, User> userMap)
    {
        this.userMap = userMap;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        for (User user : userMap.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    @Override
    public User save(@Nonnull User object) {
        return userMap.put(object.getId(), object);
    }

    @Override
    public void remove(@Nonnull User object) {
        userMap.remove(object.getId());
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userMap.get(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userMap.values();
    }

    @Override
    public boolean isRegistered(@Nonnull User user)
    {
        for(User currUser : userMap.values())
        {
            if(user.equals(currUser))
            {
                return true;
            }
        }
        return false;
    }
}
