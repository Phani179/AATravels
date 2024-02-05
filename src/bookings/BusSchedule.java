package bookings;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import CustomExceptions.PSNullException;
import registration_and_login.DbCredintials;

public class BusSchedule {
	public static List<String> datesAvailable = new ArrayList<>();

	public static void getDatesbyBusno(String bus_number) throws SQLException, PSNullException  {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		try 
		{
			connection = DbCredintials.getDBConnection();
			preparedStatement = connection.prepareStatement("SELECT available_date FROM journey_details WHERE bus_no = ?");
			preparedStatement.setString(1, bus_number);
			ResultSet result = preparedStatement.executeQuery();
			if(result == null)
			{
				// Result Set is null.
			}
			else {
				while(result.next())
				{
					datesAvailable.add(result.getString(1));
				}
			}
			result.close();
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
	            connection.close();
		}
		
	
	}

}
