package registration_and_login;

public class PasswordHash
{
    private PasswordHash()
    {}
    static StringBuilder hashGeneratedPassword = new StringBuilder();
    public static String getEncryptedPassword(String password)
    {
        hashGeneratedPassword.setLength(0);
        for(int i = 0; i < password.length(); i++)
        {
            if(password.charAt(i) <= 'z' && password.charAt(i) >= 'a' || password.charAt(i) <= 'Z' && password.charAt(i) >= 'A')
            {
                hashGeneratedPassword.append((char)(password.charAt(i)+10));
            }
            else if(password.charAt(i) >= 48 && password.charAt(i) >= 57)
            {
                int x = password.charAt(i) + 10;
                hashGeneratedPassword.append((char)x);
            }
            else
            {
                hashGeneratedPassword.append((char)(password.charAt(i)+10));
            }
        }
        return hashGeneratedPassword.toString();
    }
    public static String getDecryptedPassword(String password) {
        hashGeneratedPassword.setLength(0);
        for (int i = 0; i < password.length(); i++) {
            if (password.charAt(i) <= 'z' && password.charAt(i) >= 'a' || password.charAt(i) <= 'Z' && password.charAt(i) >= 'A') {
                hashGeneratedPassword.append((char)(password.charAt(i) - 10));
            }
            else if (password.charAt(i) >= 48 && password.charAt(i) <= 57) {
                int x = password.charAt(i) - 10;
                hashGeneratedPassword.append((char)x);
            }
            else
            {
                hashGeneratedPassword.append((char)(password.charAt(i)-10));
            }
        }
        return hashGeneratedPassword.toString();
    }
}
