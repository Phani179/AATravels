package Login;

import java.sql.SQLException;

import CustomExceptions.PSNullException;
import bookings.PlanJourney;
import bookings.ReschedhuleJourney;
import mainpage.AccountStatus;
import mainpage.MenuOptions;
import registration_and_login.RegistrationDB;

public class AdminOptions 
{
	public static void adminLogin() throws SQLException, PSNullException 
	{
		MenuOptions.fromAdminLogin = true;
		
		do 
		{
			   System.out.print("\n Enter your option \t");
	           System.out.print(" 1. Lock Account \t");        // 5 incorrect passwords causes account lock.
	           System.out.print(" 2. Users Details \t");
	           System.out.println(" 3. Bookings ");
	           System.out.println("5. Log Out");
	           MenuOptions.option = MenuOptions.input.next();
	           switch(MenuOptions.option) {
               case "1" : 
               	AccountStatus.statusLock();           // true --> account active    false --> account blocked.
               	break;	
               case "2" :
               	RegistrationDB.users();
               	break;
               case "3" : 
            	   ReschedhuleJourney.previousBookings();
            	   break;
               case "4" :
               		MenuOptions.fromAdminLogin = false;
					MenuOptions.mainPage();
               	break;
               default : System.out.println("Enter correct option");
           }
		} while(true);
	}
}
