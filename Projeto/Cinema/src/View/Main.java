package View;
import javax.swing.*;

public class Main 
{
    public static void main(String[] args) 
    {
        LoginScreen loginScreen = new LoginScreen();
        loginScreen.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginScreen.setVisible(true);
    }
}