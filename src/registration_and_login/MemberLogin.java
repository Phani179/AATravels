package registration_and_login;

import mainpage.AccountStatus;
import mainpage.LogoLoad;
import mainpage.MenuOptions;

import java.io.IOException;
import java.sql.*;
import java.util.InputMismatchException;

import CustomExceptions.PSNullException;
import Login.AdminOptions;
import Login.UserOptions;

public class MemberLogin
{
    private MemberLogin()
    {}
    static String password;
    static boolean status = true;                    // Entered successfully
    public static String userMail;
    public static String userPassword;
    public static String adminMail;
    static String adminPassword;
    public static String mail ;
    static String admin = "admin";
    static boolean fromLoginCase = false;
    public static void loginCase() throws SQLException, PSNullException {
    	fromLoginCase = true;
        do{
            System.out.print("Enter your option \t");
            System.out.print(" 1. Admin login \t");
            System.out.println(" 2. User login \t");
            MenuOptions.option = MenuOptions.input.next();
            switch(MenuOptions.option)
            {
                case "1" :
                    MemberLogin.memberVerification(admin);
                    System.out.println("Successfully logged in");                
                    break;
                case "2" :
                    MemberLogin.memberVerification("user");                       //  Login.loginValidation(mailId,obj.usersData());
                    break;
                case "0" :
                    System.out.println("Exited From the Registration page");
                    MemberLogin.status = false;                                   // Exited
                    break;
                default:
                    System.out.println("Enter correct option");
            }
        }while(MemberLogin.status);
    }
    public static void loginCredentials(String email_id,String member) throws SQLException, PSNullException{
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = DbCredintials.getDBConnection();
            preparedStatement = connection.prepareStatement("SELECT password FROM "+member+"_details WHERE email_id = ?");
            preparedStatement.setString(1,email_id);
            ResultSet result = preparedStatement.executeQuery();
            if(result.next())
            {
                if(member.equals(admin))
                {
                    adminMail = mail;
                    adminPassword = result.getString(1);
                }
                else
                {
                    userMail = mail;
                    userPassword = result.getString(1);
                }
            }
            else
            {
                System.out.println("Invalid mail");
                System.out.print("1. If you are not registered, Register as a new user \t\t");
                System.out.print("2. If you are already registered, Enter correct mail \t\t");
                System.out.println("Enter your option");
                MenuOptions.option = MenuOptions.input.next();
                switch(MenuOptions.option)
                {
                	case "1" : UserCreation.registrationCase();
                        break;
                    case "2" : 
                        memberVerification(member);
                        break;
                    default : System.out.println("Enter correct option");
                }                
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
    public static boolean memberVerification(String member) throws SQLException, PSNullException
    {
        System.out.println("Enter mailid");
        mail = MailValidation.getMail();
        loginCredentials(mail,member);
        if(UserCreation.failCount == 5)
        {
            System.out.println("Account was blocked for entering 5 wrong passwords");
            AccountStatus.updateTheAccountStatusasLock();
            status = false;
            return false;
        }
        else if(AccountStatus.presentStatus(mail) == false)
        {
            System.out.println("Account was locked");
            status = false;
            return false;
        }
        else
        {
            if(member.equals(admin) && adminMail.equals(mail))
            {
                if(passwordVerification(member))
                {
                    System.out.println("Successfully Loggedin as a admin");
                    AdminOptions.adminLogin();
                    status = false;
                    return true;
                }
                else
                {
                    passwordVerification(member);
                }
            }
            else if(member.equals("user") && userMail.equals(mail))
            {
                if(passwordVerification(member))
                {
                	System.out.println("Successfully Loggedin as a user");
                	if(fromLoginCase)
                	{
                		UserOptions.userLogin();
                	}
                    status = false;
                    return true;
                }
                else
                {
                    passwordVerification(member);
                }
            }
            else
            {
                System.out.println("Mail not existed, Register as a new user");
            }
            return false;
        }

    }

    public static boolean passwordVerification(String member) throws SQLException, PSNullException{
        System.out.println("Enter Password");
        password = MenuOptions.input.next();
        if(member.equals(admin) && adminPassword.equals(PasswordHash.getEncryptedPassword(password)) || member.equals("user") && userPassword.equals(PasswordHash.getEncryptedPassword(password)))
        {
            return true;
        }
        else
        {
            if(UserCreation.failCount == 5 && member.equals("user"))
            {
                System.out.println("Account Locked");
                AccountStatus.updateTheAccountStatusasLock();
                return false;
            }
            else {
                UserCreation.failCount++;
                System.out.println("Incorrect password");
					toLastPage(member);
            }
        }
        return true;
    }

    static boolean exitPage = true;
    public static void toLastPage(String member) throws SQLException, PSNullException {

        do
        {
            System.out.println("1. Re-enter password");
            System.out.println("2. Register as a new user");
            System.out.println("3. Forgot Password");
            System.out.println("Enter 0 to exit from the login and to explore other features");
            System.out.println("Enter option");
            int option = MenuOptions.input.nextInt();
            switch(option)
            {
                case 1:
                    if(passwordVerification(member))
                    {
                        exitPage = false;
                        status = false;
                    }
                    break;
                case 2:
                    UserCreation.registrationCase();
                    break;
                case 3:
                    NewPasswordCreation.createNewPassword(member);
                    break;
                case 0:
                    System.out.println("Exited From the Registration page");
                    exitPage = false;                                             // Exited
                    break;
                default:
                    System.out.println("Enter correct option");
            }
        }while(MemberLogin.exitPage);
    }
}
