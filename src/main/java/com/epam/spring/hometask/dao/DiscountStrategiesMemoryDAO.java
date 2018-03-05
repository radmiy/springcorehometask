package com.epam.spring.hometask.dao;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.idao.DiscountStrategy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class DiscountStrategiesMemoryDAO implements DiscountStrategy
{
    private List<DiscountStrategy> discountStrategies = new ArrayList<>();

    public List<DiscountStrategy> getDiscountStrategies()
    {
        return discountStrategies;
    }

    public void setDiscountStrategies(List<DiscountStrategy> discountStrategies)
    {
        this.discountStrategies = discountStrategies;
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime,
                            long numberOfTickets)
    {
        byte discount = 0;
        for (DiscountStrategy strategy : discountStrategies)
        {
            byte strategyDiscount =
                    strategy.getDiscount(user, event, airDateTime, numberOfTickets);
            if (strategyDiscount > discount)
            {
                discount = strategyDiscount;
            }
        }
        return discount;
    }
}
