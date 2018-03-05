package com.epam.spring.hometask.service;

import com.epam.spring.hometask.beans.*;
import com.epam.spring.hometask.iservice.AuditoriumService;
import com.epam.spring.hometask.iservice.BookingService;
import com.epam.spring.hometask.iservice.DiscountService;
import com.epam.spring.hometask.iservice.UserService;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class BookingServiceImpl implements BookingService {
    private DiscountService discountService;
    private UserService userService;
    private AuditoriumService auditoriumService;

    public DiscountService getDiscountService()
    {
        return discountService;
    }

    public void setDiscountService(DiscountService discountService)
    {
        this.discountService = discountService;
    }

    public UserService getUserService()
    {
        return userService;
    }

    public void setUserService(UserService userService)
    {
        this.userService = userService;
    }

    public AuditoriumService getAuditoriumService()
    {
        return auditoriumService;
    }

    public void setAuditoriumService(AuditoriumService auditoriumService)
    {
        this.auditoriumService = auditoriumService;
    }

    @Override
    public double getTicketsPrice(@Nonnull Event event, @Nonnull LocalDateTime dateTime,
                                  @Nullable User user, @Nonnull Set<Long> seats) {
        double price = event.getBasePrice() *
                (event.getRating() == EventRating.HIGH ? 1.2 :
                        event.getRating() == EventRating.MID ? 1.1 : 1);
        double allPrice = 0;
        Auditorium auditorium = event.getAuditoriums().get(dateTime);
        for(Long seat : seats)
        {
            if(auditorium.getVipSeats().contains(seat))
            {
                allPrice += 2 * price;
            }
            else
            {
                allPrice += price;
            }
        }

        return  allPrice -
                allPrice * discountService.getDiscount(user, event, dateTime, seats.size()) / 100;
    }

    @Override
    public void bookTickets(@Nonnull Set<Ticket> tickets) {
        for(Ticket ticket : tickets)
        {
            Event event = ticket.getEvent();
            if(!event.getTickets().containsKey(ticket.getSeat())){
                event.getTickets().put(ticket.getSeat(), ticket);
                if(userService.isRegistered(ticket.getUser())){
                    ticket.getUser().getTickets().add(ticket);
                }
            } else {
                System.out.println("Ticket " + ticket + " already booked");
            }
        }
    }

    @Nonnull
    @Override
    public Set<Ticket> getPurchasedTicketsForEvent(@Nonnull Event event, @Nonnull LocalDateTime dateTime) {
        return new HashSet<>(event.getTickets().values());
    }
}
