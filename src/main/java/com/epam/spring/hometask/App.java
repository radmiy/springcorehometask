package com.epam.spring.hometask;

import com.epam.spring.hometask.beans.Event;
import com.epam.spring.hometask.beans.Ticket;
import com.epam.spring.hometask.beans.User;
import com.epam.spring.hometask.iservice.AuditoriumService;
import com.epam.spring.hometask.iservice.BookingService;
import com.epam.spring.hometask.iservice.EventService;
import com.epam.spring.hometask.iservice.UserService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App
{
    private static AuditoriumService auditoriumService;
    private static EventService eventService;
    private static UserService userService;
    private static BookingService bookingService;

    private static Event selectEvent;
    private static User user;
    private static DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args)
    {
        ConfigurableApplicationContext context =
                new ClassPathXmlApplicationContext("spring.xml");
        auditoriumService = (AuditoriumService) context.getBean("auditoriumService");
        eventService = (EventService) context.getBean("eventService");
        userService = (UserService) context.getBean("userService");
        bookingService = (BookingService) context.getBean("bookingService");
        int numElem = 0;
        while (numElem != 3)
        {
            System.out.println("");
            System.out.println("1. Event service");
            System.out.println("2. User service");
            System.out.println("3. Exit");
            System.out.print("Choose number:");


            try
            {
                numElem = Integer.parseInt(scanner.nextLine());
            }
            catch (NumberFormatException err)
            {
                System.out.println("Error input string");
            }

            switch (numElem)
            {
                case 1:
                    getEventService();
                    break;
                case 2:
                    getUser();
                    break;
            }
        }

        User user = userService.getById(1L);
        Event event = eventService.getById(1L);
        List<LocalDateTime> dateTimes = new ArrayList<>(event.getAirDates());
        Ticket ticket = new Ticket(user, event, dateTimes.get(0), 5L);
        System.out.println();

        Set<Long> seats = new TreeSet<>();
        for (long indSeat = 1; indSeat < 11; indSeat++, seats.add(indSeat)) ;
        System.out.println(bookingService.getTicketsPrice(eventService.getById(2L),
                (LocalDateTime) eventService.getById(2L).getAirDates().toArray()[0],
                userService.getById(1L),
                seats));

    }

    private static void getEventService()
    {
        int numElem = 0;
        while (numElem != 4)
        {
            System.out.println("");
            System.out.println("1. View all Event");
            System.out.println("2. Show Events from now in range");
            System.out.println("3. Choose Event by name");
            System.out.println("4. Exit from menu");
            System.out.print("Choose number:");


            try
            {
                numElem = Integer.parseInt(scanner.nextLine());
            }
            catch (InputMismatchException | NumberFormatException err)
            {
                System.out.println("Error input string");
            }
            switch (numElem)
            {
                case 1:
                    for (Event event : eventService.getAll())
                    {
                        System.out.println(event);
                    }
                    break;
                case 2:
                    String date = scanner.nextLine();
                    LocalDateTime localDate = getDate(date);
                    if (localDate == null)
                    {
                        System.out.println("Error input string");
                        break;
                    }
                    Set<Event> events = eventService.getNextEvents(localDate);
                    for (Event event : events)
                    {
                        System.out.println(event);
                    }
                    break;
                case 3:
                    System.out.print("Please input name: ");
                    String nameEvent = scanner.nextLine();
                    selectEvent = eventService.getByName(nameEvent);
                    System.out.println("Selected: " + selectEvent);
                    if (user == null)
                    {
                        System.out.println("You are not login. Use default user for booking.");
                        user = userService.getUserByEmail("Unregistred");
                    }
                    getTicket();
                    break;
            }
        }
    }

    private static void getTicket()
    {
        int numElem = 0;
        Set<Ticket> tickets = new HashSet<>();
        LocalDateTime localDate = null;
        while (numElem != 3)
        {
            System.out.println("");
            System.out.println("1. Add ticket");
            System.out.println("2. Book tickets");
            System.out.println("3. Exit from menu");
            System.out.print("Choose number:");
            try
            {
                numElem = Integer.parseInt(scanner.nextLine());
            }
            catch (InputMismatchException | NumberFormatException err)
            {
                System.out.println("Error input string");
            }
            switch (numElem)
            {
                case 1:
                    if (selectEvent == null)
                    {
                        System.out.println("Please select event");
                        numElem = 3;
                        break;
                    }
                    System.out.println(selectEvent);
                    System.out.print("Please input date in format \"yyyy-MM-dd HH:mm\": ");
                    try
                    {
                        String date = scanner.nextLine();
                        localDate = getDate(date);
                        if (localDate == null)
                        {
                            System.out.println("Error input string");
                            break;
                        }
                        if (selectEvent.airsOnDateTime(localDate))
                        {
                            System.out.println(selectEvent.getAuditoriums().get(localDate));
                            System.out.print("Please input seat: ");
                            int numSeat = -1;
                            try
                            {
                                numSeat = Integer.parseInt(scanner.nextLine());
                            }
                            catch (InputMismatchException | NumberFormatException err)
                            {
                                System.out.println("Error input string");
                                break;
                            }
                            Ticket ticket = new Ticket(user, selectEvent, localDate, numSeat);
                            Set<Long> seats = new HashSet<>();
                            if (seats.contains(ticket.getSeat()))
                            {
                                System.out.println("You have selected that seat.");
                                break;
                            }
                            seats.add(ticket.getSeat());
                            double price = bookingService.getTicketsPrice(selectEvent, localDate, user, seats);
                            ticket.setPrice(price);
                            tickets.add(ticket);
                            System.out.println("Add ticket: " + ticket);
                        }
                    }
                    catch (DateTimeParseException err)
                    {
                        System.out.println("Error input string");
                        break;
                    }
                    break;
                case 2:
                    bookingService.bookTickets(tickets);
                    if(localDate != null)
                    {
                        Set<Long> seats = new HashSet<>();
                        for (Ticket ticket : tickets)
                        {
                            seats.add(ticket.getSeat());
                        }
                        System.out.println("Full price of " + seats.size() + " tickets = " + bookingService.getTicketsPrice(selectEvent, localDate, user, seats));
                        System.out.println("For date " + localDate.format(dateTimeFormatter) + " purchased " + bookingService.getPurchasedTicketsForEvent(selectEvent, localDate).size() + " tickets");
                    }
                    break;
            }
        }
    }

    private static void getUser()
    {
        int numElem = 0;
        while (numElem != 5)
        {
            System.out.println("");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("3. Log off");
            System.out.println("4. Delete user");
            System.out.println("5. Exit from menu");
            System.out.print("Choose number:");
            try
            {
                numElem = Integer.parseInt(scanner.nextLine());
            }
            catch (InputMismatchException | NumberFormatException err)
            {
                System.out.println("Error input string");
            }
            switch (numElem)
            {
                case 1:
                    try
                    {
                        System.out.print("Intput user ID: ");
                        user = userService.getById(Long.parseLong(scanner.nextLine()));
                        if (user == null)
                        {
                            System.out.println("That user is not in system.");
                            break;
                        }
                    }
                    catch (NumberFormatException err)
                    {
                        System.out.println("That user is not in system.");
                        break;
                    }
                    System.out.println("You are loggin as " + user);
                    break;
                case 2:
                    System.out.print("Inpute first name: ");
                    String firstName = scanner.nextLine();
                    System.out.print("Inpute last name: ");
                    String lastName = scanner.nextLine();
                    System.out.print("Inpute email: ");
                    String email = scanner.nextLine();
                    System.out.print("Input date in format \"yyyy-MM-dd\": ");
                    String dateStr = scanner.nextLine();
                    try
                    {
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = format.parse(dateStr);

                        User currUser = new User(firstName, lastName, email, date);
                        if (userService.isRegistered(currUser))
                        {
                            System.out.println("This user already in system.");
                            break;
                        }
                        userService.save(currUser);
                        user = userService.getById(currUser.getId());
                        System.out.println("User: " + user + " registered in system.");
                    }
                    catch (ParseException err)
                    {
                        System.out.println("Incorrect user info.");
                        break;
                    }
                    break;
                case 3:
                    if (user != null)
                    {
                        System.out.println("Log off " + user);
                        user = null;
                    }
                    break;
                case 4:
                    System.out.print("Please intput ID: ");
                    long id = Long.parseLong(scanner.nextLine());
                    User remUser = userService.getById(id);
                    userService.remove(remUser);
                    System.out.println("User: " + user + " removed from system.");
                    user = null;
                    break;
            }
        }
    }

    private static LocalDateTime getDate(String date)
    {
        try
        {
            return LocalDateTime.parse(date, dateTimeFormatter);
        }
        catch (DateTimeParseException err)
        {
            return null;
        }
    }
}
