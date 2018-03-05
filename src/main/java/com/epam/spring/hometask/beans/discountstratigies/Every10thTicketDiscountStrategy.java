package com.epam.spring.hometask.beans.discountstratigies;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class Every10thTicketDiscountStrategy extends DiscountStrategyImpl
{
    public Every10thTicketDiscountStrategy(byte discount)
    {
        super(discount);
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event,
                            @Nonnull LocalDateTime airDateTime, long numberOfTickets)
    {
        return ((user.getTickets().size() + numberOfTickets) % 10 == 0) ? getDiscount() : 0;
    }
}
