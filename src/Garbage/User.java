
// User Creation for cache memory.

//package Garbage;
//
//import LoginService.MailValidation;
//import LoginService.MenuOptions;
//
//import java.util.*;
//
//public class User
//{
//    static User userObj;
//	static HashMap<String,User> userDetails = new HashMap<>();
//	private String firstName;
//    private String lastName;
//    private long mobileNumber;
//    private String gender;
//    private String mail;
//    private String password;
//
//
//    public User( String firstName, String lastName, long mobileNumber, String gender, String password)
//    {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.mobileNumber = mobileNumber;
//        this.gender = gender;
//        this.password = password;
//    }
//
//    public User() {
//	}
//
//	public static void userRegistration()
//    {
//
//        System.out.println("Enter first Name");
//        MenuOptions.input.nextLine();
//        String firstName = MenuOptions.input.nextLine();
//        System.out.println("Enter last Name");
//        String lastName = MenuOptions.input.next();
//        System.out.println("Enter mobile number");
//        long mobileNumber = MenuOptions.input.nextLong();
//        System.out.println("Enter gender (Male/Female) ");
//        String gender = MenuOptions.input.next();
//        System.out.println("Enter mail-id ");
//        String mail = MailValidation.getMail();
//        System.out.println("Enter password ");
//        String password = MenuOptions.input.next();
//        int failedCount = 0;
//        boolean accountStatus = true; // Active
//		User user = new User(firstName, lastName, mobileNumber, gender, password);
//		userDetails.put(mail,user);
//		System.out.println(userDetails);
//    }
//
//
//	@Override
//	public String toString() {
//		return "User [firstName=" + firstName + ", lastName=" + lastName + ", mobileNumber=" + mobileNumber
//				+ ", gender=" + gender + ", password=" + password + "]";
//	}
//
//	public HashMap<String,User> usersData()
//    {
//		return userDetails;
//    }
//
//	public String getFirstName() {
//		return firstName;
//	}
//
//	public String getLastName() {
//		return lastName;
//	}
//
//	public long getMobileNumber() {
//		return mobileNumber;
//	}
//
//	public String getGender() {
//		return gender;
//	}
//
//	public String getMail() {
//		return mail;
//	}
//
//    public String getPassword() {
//        return password;
//    }
//
//}
