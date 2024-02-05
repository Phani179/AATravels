package registration_and_login;

import mainpage.LogoLoad;
import mainpage.MenuOptions;
import CustomExceptions.PSNullException;

import java.io.IOException;
import java.sql.SQLException;

public class UserCreation
{
    private UserCreation()
    {}
	private static String firstName;
    private static String lastName;
    private static String mobileNumber;
    private static String gender;
    private static String mail;
    private static String password;
    static byte failCount = 0;
    private static boolean accountStatus = true;
	public static boolean isAccountStatus() {
		return accountStatus;
	}

    public static void registrationCase() throws SQLException{
        do {
            System.out.print("Enter your option \t");
            System.out.print(" 1. Admin registration \t");
            System.out.println(" 2. User registration \t");
            MenuOptions.option = MenuOptions.input.next();
            switch(MenuOptions.option)
            {
                case "1" :
                    UserCreation.memberRegistration("admin");
                    break;
                case "2":
                    UserCreation.memberRegistration("user");
                    break;
                case "0":
                    System.out.println("Exited From the Registration page");
                    RegistrationDB.status = false;                      // Exited
                    break;
                default:
                    System.out.println("Enter correct option");
            }
        }while(RegistrationDB.status);
    }
    public static void memberData()
    {
        System.out.println("Enter first Name");
        MenuOptions.input.nextLine();
        firstName = MenuOptions.input.nextLine();
        System.out.println("Enter last Name");
        lastName = MenuOptions.input.next();
        System.out.println("Enter mobile number");
        mobileNumber = MenuOptions.input.next();
        System.out.println("Enter gender (Male/Female) ");
        gender = MenuOptions.input.next();
        System.out.println("Enter mail-id ");
        mail = MailValidation.getMail();
        System.out.println("Enter password ");
        password = MenuOptions.input.next();
    }

	public static void memberRegistration(String member) throws SQLException{
		memberData();
		try {
			RegistrationDB.memberEntry(firstName, lastName, mobileNumber, gender, mail, password, member);
		} catch (PSNullException e) {
			System.out.println(e);
		}
	}	
}
