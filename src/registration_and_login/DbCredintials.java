package registration_and_login;

import mainpage.MenuOptions;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class DbCredintials
{
        private DbCredintials()
        {       }
        static final String DBURL = "jdbc:mysql://localhost:3306/aatravels";
        static final String DBUSERNAME = "root";
        public static final String DBPASSWORD = "Phani@7989";

    	public static Connection getDBConnection() throws SQLException {
    		return DriverManager.getConnection(DBURL,DBUSERNAME,DBPASSWORD);     // Make DBPASSWORD = MenuOptions.input.next() for clearing the sonarlint error.
    	}
}
