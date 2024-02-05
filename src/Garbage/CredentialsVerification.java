package Garbage;

import registration_and_login.DbCredintials;
import mainpage.MenuOptions;
import registration_and_login.PasswordHash;

import java.sql.*;
import java.util.InputMismatchException;

public class CredentialsVerification
{
    public static void verification()
    {
        String mail = null;
        String password = null;

        try
        {
            System.out.println("Enter mail");
            mail = MenuOptions.input.next();
            System.out.println("Enter password");
            password = MenuOptions.input.next();
        }
        catch(InputMismatchException e)
        {
            System.out.println(e);
        }
        Connection connection = null;
        Statement statement = null;
        try
        {
            connection = DbCredintials.getDBConnection();
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT count(*) FROM admin_details WHERE email_id = ? AND password = ?;");
            preparedStatement.setString(1,mail);
            preparedStatement.setString(2, PasswordHash.getEncryptedPassword(password));
            ResultSet result = preparedStatement.executeQuery();
            result.next();
            System.out.println(result.getInt(1));
            int count = result.getInt(1);
            System.out.println(count);
            if(count >= 1)
            {
                System.out.println("Verified user");
            }
            else
            {
                System.out.println("Not verified user");
            }
        }
        catch(SQLException e)
        {
            System.out.println(e);
        }
    }

    public static void main(String[] args) {
        verification();
    }
}
