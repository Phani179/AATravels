package registration_and_login;

import mainpage.MenuOptions;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CustomExceptions.PSNullException;

public class NewPasswordCreation
{
    private NewPasswordCreation()
    {}
    public static void createNewPassword(String member) throws SQLException, PSNullException
    {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try
        {
            System.out.println("Enter mail");
            String mail = MenuOptions.input.next();
            System.out.println("Enter mobile_number");
            String mobile_number = MenuOptions.input.next();
            System.out.println("Enter your Favourite Number");
            String fav_number = MenuOptions.input.next();
            connection = DbCredintials.getDBConnection();
            preparedStatement = connection.prepareStatement("SELECT count(*) FROM "+member+"_details WHERE email_id = ? AND mobile_number = ? AND favourite_number = ?");
            preparedStatement.setString(1,mail);
            preparedStatement.setString(2, mobile_number);
            preparedStatement.setString(3,fav_number);
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            if(result.getInt(1) == 1)
            {
                System.out.println("Enter your new password");
                String newPassword = MenuOptions.input.next();
                if(newWithOldPasswordCompare(mail,newPassword,member))
                {
                    System.out.println("Same as old password");
                }
                else
                {
                    preparedStatement = connection.prepareStatement("UPDATE "+member+"_details SET password = ? WHERE email_id = ?");
                    preparedStatement.setString(1,PasswordHash.getEncryptedPassword(newPassword));
                    preparedStatement.setString(2,mail);
                    preparedStatement.executeUpdate();
                    System.out.println("Password successfully changed");
                    MemberLogin.exitPage = false;
                }
                MenuOptions.mainPage();
            }
            else
            {
                System.out.println("Invalid Credentials");
            }
        }
        finally {
	        if(preparedStatement == null)
	    	{
	    		throw new PSNullException("Check DB Credentials and SQL Syntax");
	    	}
	    	else {
				preparedStatement.close();
			}
	        connection.close();
		}
    }

    public static boolean newWithOldPasswordCompare(String mail,String newPassword,String member) throws SQLException, PSNullException {
        MemberLogin.loginCredentials(mail,member);
        if(PasswordHash.getEncryptedPassword(newPassword).equals(MemberLogin.userPassword))
        {
            return true;
        }
        return false;
    }
}
