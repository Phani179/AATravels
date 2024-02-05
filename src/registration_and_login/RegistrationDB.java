package registration_and_login;

import mainpage.MenuOptions;

import java.io.IOException;
import java.sql.*;

import CustomExceptions.PSNullException;


public class RegistrationDB 
{
    public static boolean status = true;              // Going on

    static void memberEntry(String firstName, String lastName, String mobileNumber, String gender, String mail, String password, String member) throws PSNullException, SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = DriverManager.getConnection(DbCredintials.DBURL,DbCredintials.DBUSERNAME, DbCredintials.DBPASSWORD);
            if(member.equals("admin"))
            {
                String query = "INSERT INTO admin_details VALUES ('"+firstName+"','"+lastName+"',"+mobileNumber+",'"+gender+"','"+mail+"','"+PasswordHash.getEncryptedPassword(password)+"');";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate();
                System.out.println("Successfully Admin Registered");
                status = false;             // completed
            }
            else
            {
                System.out.println("Enter your favourite number");
                String fav_number = MenuOptions.input.next();
                String query = "INSERT INTO user_details VALUES ('"+firstName+"','"+lastName+"',"+mobileNumber+",'"+gender+"','"+mail+"','"+PasswordHash.getEncryptedPassword(password)+"',"+UserCreation.failCount+","+UserCreation.isAccountStatus()+","+fav_number+")";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.executeUpdate(query);
                System.out.println("Successfully User Registered");
                status = false;             // completed
                try {
					MenuOptions.mainPage();
				} catch (SQLException e) {
					System.out.println(e);
				}
                
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
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

    public static void users() throws SQLException, PSNullException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            connection = DriverManager.getConnection(DbCredintials.DBURL,DbCredintials.DBUSERNAME, DbCredintials.DBPASSWORD);
            String query = "SELECT first_name, last_name, mobile_number, gender, email_id, account_status FROM user_details";
            preparedStatement = connection.prepareStatement(query);
            ResultSet result = preparedStatement.executeQuery(query);
            ResultSetMetaData metaData = result.getMetaData();
            int columnCount = metaData.getColumnCount();
            final String FORMAT = "%-13s";
            for(int i = 1; i <= columnCount; i++)
            {
            	System.out.printf(FORMAT,metaData.getColumnName(i));
            }
            System.out.println();
            while(result.next())
            {
                System.out.println(result.getString("first_name")+"\t\t"+result.getString("last_name")+"\t\t"+result.getString("mobile_number")
                        +"\t\t"+result.getString("gender")+"\t\t"+result.getString("email_id")+"\t\t"+
                        result.getString("account_status"));
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
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
}
