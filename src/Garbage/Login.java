
// Without DB for Login.


//package Garbage;
//
//import LoginService.MenuOptions;
//import Garbage.User;
//
//import java.util.HashSet;
//import java.util.ArrayList;
//import java.util.HashMap;
//
//public class Login
//{
//	public static void loginValidation(String MailId,HashMap<String, User> userDetails)
//	{
//		HashSet<String> mails = new HashSet<>();
//		mails.addAll(userDetails.keySet());
//		ArrayList<User> passwords = new ArrayList<>();
//		passwords.addAll(userDetails.values());
//		if(mails.contains(MailId))
//		{
//			System.out.println("Enter Password");
//        	String password = MenuOptions.input.next();
//			for(int i = 0; i < passwords.size(); i++)
//			{
//				User user = passwords.get(i);
//				if(password.equals(user.getPassword()))
//				{
//					System.out.println("Successfully Loggedin");
//				}
//			}
//		}
//		else
//		{
//			System.out.println("Mail not Registered");
//		}
//	}
//}
