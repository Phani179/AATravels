package Garbage;

import mainpage.AccountStatus;
import mainpage.MenuOptions;
import registration_and_login.DbCredintials;
import registration_and_login.PasswordHash;

import java.sql.*;
import java.util.*;

import CustomExceptions.PSNullException;

public class MemberLoginCredentials
{
    static String password;
    public static boolean status = true;                    // Entered succesfully
    static HashMap<String,String> user = new HashMap<>();
    static HashMap<String,String> admin = new HashMap<>();
    public static HashMap<String,String> LoginCredentials(String email_id,String member) throws SQLException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbCredintials.getDBConnection();
                 preparedStatement = connection.prepareStatement("SELECT email_id,password FROM ?_details WHERE email_id = ?");
                preparedStatement.setString(1,member);
            preparedStatement.setString(2,email_id);
            ResultSet result = preparedStatement.executeQuery();
                if(member.equals("admin"))
                {
                    while (result.next()) {
                        admin.put(result.getString("email_id"), result.getString("password"));
                    }
                    return admin;
                }
                else
                {
                    while (result.next()) {
                        user.put(result.getString("email_id"), result.getString("password"));
                    }
                    return user;
                }
        } catch (Exception e) {
            System.out.println(e);
        } finally {
            preparedStatement.close();
            connection.close();
        }
        return null;
    }
    static public boolean memberVerification(String mail, String member) throws SQLException, PSNullException
    {
        LoginCredentials(mail,member);
        ArrayList<String> mails = new ArrayList<>();
        if(member.equals("admin"))
        {
            mails.addAll(admin.keySet());
        }
        else
        {
            mails.addAll(user.keySet());
        }
        if(mails.contains(mail))
        {
            System.out.println("Mail Existed");
            ArrayList<String> passwords = new ArrayList<>();
            if(member.equals("admin"))
            {
                passwords.addAll(admin.values());
            }
            else
            {
                passwords.addAll(user.values());
            }
            if(passwordVerification(passwords))
            {
                if(AccountStatus.presentStatus(mail) == false)
                {
                    System.out.println("Sorry, Your account was blocked");
                }
                else
                {
                    System.out.println("Loggedin Successfully");        //  Login Successful --> Password validated
                }
                status = false;                                     // Entry completed.(After completion acces denied)
            }
            else
            {
                passwordVerification(passwords);
                System.out.println("Incorrect Password");
            }
        }
        else
        {
            System.out.println("Please Enter mail again, If your not already registered please got to new user section and create account");
            String email = MenuOptions.input.next();
            memberVerification(email,member);
        }
        return false;
    }

    public static boolean passwordVerification(ArrayList<String> passwords)
    {
        System.out.println("Enter Password");
        password = MenuOptions.input.next();
        if(passwords.contains(PasswordHash.getEncryptedPassword(password)))
        {
            return true;
        }
        else
        {
            System.out.println("Please check and enter credentials again");
        }
        return false;
    }
}
