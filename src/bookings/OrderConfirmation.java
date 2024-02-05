package bookings;

import mainpage.MenuOptions;
import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;

import java.sql.*;
import java.time.LocalDate;

import CustomExceptions.PSNullException;

public class OrderConfirmation {
    public static String confirmation;
    public static String from;
    public static String to;

    public static void bookingTicket() throws SQLException, PSNullException {
        int bill = 0;
        System.out.println("\t\t Ticket Counter");
        busNumberVerifier(bill);       
    }

	public static void busNumberVerifier(int bill) throws SQLException, PSNullException {
		System.out.println("Enter Bus Number");
        String bus_number = MenuOptions.input.next();
        BusSchedule.getDatesbyBusno(bus_number);
        if(PlanJourney.busNumbers.contains(bus_number))
        {
        	availableDateVerifier(bill,bus_number);
        }
        else 
        {
			System.out.println("\tInvalid Bus Number");
			busNumberVerifier(bill);
		}
	}

	public static void availableDateVerifier(int bill,String bus_number) throws SQLException, PSNullException {
		Connection connection = null;
        PreparedStatement preparedStatement = null;
		System.out.println("Enter Date(YYYY-MM-DD)");
		String date = MenuOptions.input.next();
		if(BusSchedule.datesAvailable == null)
		{
			System.out.println("No more schedhules available");
			MenuOptions.mainPage();
		}
		else 
		{
			if(BusSchedule.datesAvailable.contains(date))
	    	{
	    		billing(bill, connection, preparedStatement, bus_number, date);
	    	}
	    	else 
	    	{
				System.out.println("\tBuses are not available on that day");
			}
		}
	}

	public static void billing(int bill, Connection connection, PreparedStatement preparedStatement, String bus_number,
			String date) throws SQLException, PSNullException {
		System.out.println("To confirm booking enter Yes(y) otherwise No(n)");
        confirmation = MenuOptions.input.next();
        if(confirmation.equalsIgnoreCase("y") || confirmation.equalsIgnoreCase("yes"))
        {
            try {
                connection = DbCredintials.getDBConnection();
                preparedStatement = connection.prepareStatement("SELECT from_place,to_place,price FROM journey_details WHERE bus_no = ? AND available_date = ?");
                preparedStatement.setString(1,bus_number);
                preparedStatement.setString(2, date);
                ResultSet result = preparedStatement.executeQuery();
                    while(result.next())
                    {
                        from = result.getString(1);
                        to = result.getString(2);
                        bill = result.getInt(3);
                    }
                    LocalDate todayDate = LocalDate.parse(date);
                    String dayOfWeek = String.valueOf(todayDate.getDayOfWeek());
                    if(dayOfWeek.equals("SATURDAY") || dayOfWeek.equals("SUNDAY"))
                    {
                        System.out.println("\t You are travelling in weekends, So you pay 20rupees more than other days cost");
                        System.out.println("\t Booking is Confirmed");
                        System.out.println("\t Bill of " + (bill+20) + " was completed");          // Weekends
                        System.out.println("\t Thank you, Your Ticket was booked");
                    }
                    else
                    {
                        System.out.println("\t Booking is Confirmed");
                        System.out.println("\t Bill of " + bill + " was completed");          // Other Days
                        System.out.println("\t Thank you, Your Ticket was booked");
                    }
                preparedStatement = connection.prepareStatement("INSERT INTO user_journey_details VALUES(?,?,?,?)");
                preparedStatement.setString(1,MemberLogin.mail);
                preparedStatement.setString(2,bus_number);
                preparedStatement.setString(3,date);
                preparedStatement.setInt(4,bill);
                preparedStatement.executeUpdate();
                MenuOptions.mainPage();
            }
            finally
            {
            	if(preparedStatement == null)
            	{
            		throw new PSNullException("Check your DB Credentials and DB Syntax");
            	}
            	else 
            	{
    				preparedStatement.close();
    			}
                connection.close();}
        }
        else
        {
            System.out.println("Booking cancelled");
            PlanJourney.toursAvailable();
        }
	}
}