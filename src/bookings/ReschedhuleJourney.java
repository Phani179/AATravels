package bookings;

import mainpage.MenuOptions;
import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;

import java.sql.*;

import CustomExceptions.PSNullException;
import Login.UserOptions;

public class ReschedhuleJourney
{
    Connection connection = null;
    PreparedStatement statement = null;
    static ReschedhuleJourney obj = new ReschedhuleJourney();
    public static void rescheduleCase() throws SQLException, PSNullException {
        if(MenuOptions.fromUserLogin || MemberLogin.memberVerification("user"))
        {
            rescheduleJourney();
        }
        else
        {
            System.out.println("You are not a valid user");
        }
    }
    public static void rescheduleJourney() throws SQLException, PSNullException {
        if(previousBookings())
        {
        	System.out.println("\t\t Tours Available");
            PlanJourney.toursAvailable();
            System.out.println("If you want to re-schedule your journey, Enter previously scheduled bus_no");
            int prv_bus_no = MenuOptions.input.nextInt();
            System.out.print("Enter present schedule bus_no");
            int pre_bus_no = MenuOptions.input.nextInt();
            System.out.println("Enter New schedule Date from the above available dates");
            String new_date = MenuOptions.input.next();
            bookingUpdate(new_date,prv_bus_no,pre_bus_no);
            System.out.print("Updated successfully");
        }
        else
        {
            System.out.println("You are not book any tickets till now");
            MenuOptions.mainPage();
        }
    }

    public static boolean previousBookings() throws SQLException, PSNullException 
    {
    	try {
    		obj.connection = DbCredintials.getDBConnection();
    		if(MenuOptions.fromAdminLogin)
    		{
    			 String query = "SELECT from_place,to_place,price,journey_details.bus_no,date_of_travel FROM journey_details JOIN user_journey_details ON journey_details.bus_no = user_journey_details.bus_no AND journey_details.available_date = user_journey_details.date_of_travel";
    	         obj.statement = obj.connection.prepareStatement(query);
    		}
    		else {
				 String query = "SELECT from_place,to_place,price,journey_details.bus_no,date_of_travel FROM journey_details JOIN user_journey_details ON journey_details.bus_no = user_journey_details.bus_no AND journey_details.available_date = user_journey_details.date_of_travel WHERE email_id = ?";
            obj.statement = obj.connection.prepareStatement(query);
            obj.statement.setString(1, MemberLogin.userMail);
			}
            ResultSet result = obj.statement.executeQuery();
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            if(columnCount == 0)
            {
                System.out.println("You didn't have any bookings");
                return false;
            }
            else
            {
            	System.out.println("\t\t Your prevoius bookings");
                final String FORMAT = "%-13s";
                for(int i = 1; i <= columnCount; i++)
                {
                    System.out.printf(FORMAT,metaData.getColumnName(i));
                }
                System.out.println();
                while(result.next())
                {
                    for(int i = 1; i <= columnCount; i++)
                    {
                        System.out.printf(FORMAT,result.getString(i));
                    }
                    System.out.println();
                }
            }
            return true;
		}
        finally {
        	if( obj.statement == null)
        	{
        		throw new PSNullException("Check your DB Credentials and DB Syntax");
        	}
        	else 
        	{
        		 obj.statement.close();
			}
        	obj.connection.close();
		}
    }

    public static void bookingUpdate(String new_date,int prv_bus_no,int pre_bus_no) throws SQLException, PSNullException
    {
    	try {
    		 obj.connection = DbCredintials.getDBConnection();
    	        String query = "UPDATE user_journey_details SET date_of_travel = ?, bus_no = ? WHERE email_id = ? AND bus_no = ?";
    	        obj.statement = obj.connection.prepareStatement(query);
    	        obj.statement.setString(1,new_date);
    	        obj.statement.setInt(2,pre_bus_no);
    	        obj.statement.setString(3,MemberLogin.mail);
    	        obj.statement.setInt(4,prv_bus_no);
    	        obj.statement.executeUpdate();
		} 
       finally
       {
	       	if( obj.statement == null)
	       	{
	       		throw new PSNullException("Check your DB Credentials and DB Syntax");
	       	}
	       	else 
	       	{
	       		 obj.statement.close();
				}
	       	obj.connection.close(); 
	       }
    }
}
