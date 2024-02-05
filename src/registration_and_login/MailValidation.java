package registration_and_login;

import mainpage.MenuOptions;

public class MailValidation
{
	private MailValidation()
	{}
	public static boolean mailValidate(String mail)
	{
		if(mail.indexOf('@') == -1 || mail.indexOf('.') == -1)
		{
			return false;
		}
		if(mail.indexOf('.') == mail.indexOf('@')-1 || mail.indexOf('.')-1 == mail.indexOf('@'))
		{
			return false;
		}
		
		int count = 0;
		char[] elements = mail.toCharArray();
		
		for(char temp : elements)
		{
			if(temp == '@')
			{
				count++;
				if(count > 1)
				{
					return false;
				}
			}
		}
		
		return true;
	}
	
	public static String getMail()
	{
		 String mail = MenuOptions.input.next();
		 if(mailValidate(mail))
		 {
			 return mail;
		 }
		 else
		 {
			 System.out.println("Please check mail once again, and enter");
			 mail = getMail();
		 }
		 return mail;
	}
}
