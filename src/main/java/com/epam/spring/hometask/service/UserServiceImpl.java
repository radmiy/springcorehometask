package com.epam.spring.hometask.service;

import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.idao.UserDAO;
import com.epam.spring.hometask.iservice.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;

public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl() {

    }

    public UserDAO getUserDAO() {
        return userDAO;
    }

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Nullable
    @Override
    public User getUserByEmail(@Nonnull String email) {
        return userDAO.getUserByEmail(email);
    }

    @Override
    public User save(@Nonnull User object) {
        return userDAO.save(object);
    }

    @Override
    public void remove(@Nonnull User object) {
        userDAO.remove(object);
    }

    @Override
    public User getById(@Nonnull Long id) {
        return userDAO.getById(id);
    }

    @Nonnull
    @Override
    public Collection<User> getAll() {
        return userDAO.getAll();
    }

    public boolean isRegistered(@Nonnull User user){
        return userDAO.isRegistered(user);
    }
}
