package com.epam.spring.hometask.iservice;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.epam.spring.hometask.beans.User;

/**
 * @author Yuriy_Tkach
 */
public interface UserService extends AbstractDomainObjectService<User> {

    /**
     * Finding user by email
     *
     * @param email
     *            Email of the user
     * @return found user or <code>null</code>
     */
    public @Nullable User getUserByEmail(@Nonnull String email);

    public boolean isRegistered(@Nonnull User user);
}
