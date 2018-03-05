package com.epam.spring.hometask.beans.discountstratigies;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;

public class BirthdayDiscountStrategy extends DiscountStrategyImpl
{
    public BirthdayDiscountStrategy(byte discount)
    {
        super(discount);
    }

    @Override
    public byte getDiscount(@Nullable User user, @Nonnull Event event, @Nonnull LocalDateTime airDateTime, long numberOfTickets)
    {
        if (!getUserService().isRegistered(user))
        {
            return 0;
        }
        else
        {
            LocalDateTime from = airDateTime.minusDays(5);
            LocalDateTime to = airDateTime.plusDays(5);

            return (user.getBirthDate() != null &&
                    user.getBirthDate().after(java.sql.Timestamp.valueOf(from)) &&
                    user.getBirthDate().before(java.sql.Timestamp.valueOf(to)))
                    ? getDiscount() : 0;
        }
    }
}
