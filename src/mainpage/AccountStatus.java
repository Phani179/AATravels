package mainpage;

import registration_and_login.DbCredintials;
import registration_and_login.MemberLogin;
import registration_and_login.PasswordHash;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import CustomExceptions.PSNullException;

public class AccountStatus
{
    private AccountStatus()
    {}
    public static String status = "account_status";
    static Connection connection = null;
    static PreparedStatement preparedStatement = null;
    static String mail ;
    static String password;
    public static void statusLock() throws SQLException, PSNullException
    { 
        if(MenuOptions.fromUserLogin || MemberLogin.memberVerification("user"))
        {
            updateTheAccountStatusasLock();
        }
        else
        {
            System.out.println("Your account is already locked");
        }
    }

    public static void updateTheAccountStatusasLock() throws SQLException, PSNullException {
        try
        {
            connection = DbCredintials.getDBConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE user_details SET "+status+" = false WHERE email_id = ?");
            preparedStatement.setString(1,MemberLogin.userMail);
            preparedStatement.executeUpdate();
            System.out.println("Account Locked Successfully");
        }
        catch (SQLException e)
        {
            System.out.println(e);
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

	public static void statusUnlock() throws SQLException, PSNullException 
	{
		System.out.println("Enter MailId");
		mail = MenuOptions.input.next();
		if (presentStatus(mail) == false) 
		{
			System.out.println("Enter password");
			password = MenuOptions.input.next();
			MemberLogin.loginCredentials(mail, "user");
			if (mail.equals(MemberLogin.userMail)
					&& PasswordHash.getEncryptedPassword(password).equals(MemberLogin.userPassword)) {
				updateTheAccountStatusasUnlock();
			} 
			else 
			{
				System.out.println("Invalid Credentials, Enter again");
				statusUnlock();
			}
		} 
		else 
		{
			System.out.println("Your account is already in Active State");
		}
	}
    public static void updateTheAccountStatusasUnlock() throws SQLException, PSNullException {
        try
        {
            connection = DbCredintials.getDBConnection();
            preparedStatement =
                    connection.prepareStatement("UPDATE user_details SET "+status+" = true WHERE email_id = ?");
            preparedStatement.setString(1,mail);
            preparedStatement.executeUpdate();
            System.out.println("Account Unlocked Successfully");
        }
        finally
        {
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

	public static boolean presentStatus(String email) throws  PSNullException, SQLException {
        ResultSet result = null;
        try{
            connection = DbCredintials.getDBConnection();
            preparedStatement = connection.prepareStatement("SELECT "+status+" FROM user_details WHERE email_id = ?");
            preparedStatement.setString(1,email);
            result = preparedStatement.executeQuery();
            result.next();
            return result.getBoolean(1);
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
}
