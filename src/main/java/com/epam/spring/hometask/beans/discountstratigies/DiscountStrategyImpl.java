package com.epam.spring.hometask.beans.discountstratigies;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.idao.DiscountStrategy;
import com.epam.spring.hometask.iservice.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public abstract class DiscountStrategyImpl implements DiscountStrategy
{
    private UserService userService;
    private byte discount;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public byte getDiscount()
    {
        return discount;
    }

    public void setDiscount(byte discount)
    {
        this.discount = discount;
    }

    public DiscountStrategyImpl(byte discount)
    {
        this.discount = discount;
    }

    @Override
    public abstract byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets);
}
