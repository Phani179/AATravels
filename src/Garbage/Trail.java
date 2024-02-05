package Garbage;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import com.mysql.cj.xdevapi.Statement;

import registration_and_login.DbCredintials;

public class Trail
{
        static StringBuffer hashGeneratedPassword = new StringBuffer();
        public static String getEncryptedPassword(String password)
        {
            for(int i = 0; i < password.length(); i++)
            {
                if(password.charAt(i) <= 'z' && password.charAt(i) >= 'a' || password.charAt(i) <= 'Z' && password.charAt(i) >= 'A')
                {
                    hashGeneratedPassword.append((char)(password.charAt(i)+10));
                }
                else if(password.charAt(i) <= 9 && password.charAt(i) >= 0)
                {
                    int x = password.charAt(i) + 10;
                    hashGeneratedPassword.append((char)x);
                }
                else
                {
                    hashGeneratedPassword.append((char)(password.charAt(i)+10));
                }
            }
            String hashPassword = hashGeneratedPassword.toString();
            return hashPassword;
        }
        public static String getDecryptedPassword(String password) {
            hashGeneratedPassword.setLength(0);
            for (int i = 0; i < password.length(); i++) {
                if (password.charAt(i) <= 'z' && password.charAt(i) >= 'a' || password.charAt(i) <= 'Z' && password.charAt(i) >= 'A') {
                    hashGeneratedPassword.append((char)(password.charAt(i) - 10));
                }
                else if (password.charAt(i) <= 9 && password.charAt(i) >= 0) {
                    int x = password.charAt(i) - 10;
                    hashGeneratedPassword.append((char)x);
                }
                else
                {
                    hashGeneratedPassword.append((char)(password.charAt(i)-10));
                }
            }
            String hashPassword = hashGeneratedPassword.toString();
            return hashPassword;
        }

    public static void main(String[] args) throws SQLException {
        Scanner scan = new Scanner(System.in);
       String x = scan.next();
//        System.out.println(getEncryptedPassword(x));
        String y = scan.next();
//        System.out.println(getDecryptedPassword(y));
        Connection connection = DbCredintials.getDBConnection();
        PreparedStatement preparedStatement  = connection.prepareStatement("SELECT password FROM "+x+"_details WHERE email_id = ?");
        try {
			preparedStatement.setString(1,y);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        ResultSet result = preparedStatement.executeQuery();
        while(result.next())
        {
        	System.out.println(result.getString(1));
        }
    }
}
