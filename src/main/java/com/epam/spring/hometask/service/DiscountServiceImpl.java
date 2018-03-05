package com.epam.spring.hometask.service;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.idao.DiscountStrategy;
import com.epam.spring.hometask.iservice.DiscountService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class DiscountServiceImpl implements DiscountService
{
    private DiscountStrategy strategy;

    public DiscountStrategy getStrategy()
    {
        return strategy;
    }

    public void setStrategies(DiscountStrategy strategy)
    {
        this.strategy = strategy;
    }

    public DiscountServiceImpl(DiscountStrategy strategy)
    {
        this.strategy = strategy;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets)
    {
        return strategy.getDiscount(user, event, airDateTime, numberOfTickets);
    }


}
