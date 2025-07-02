package Main;

public class Main 
{

    public static void main(String[] args) 
    {


        System.out.println("CineRoxo - Aplicação iniciada");
        // Iniciar a tela de login
        View.LoginScreen loginScreen = new View.LoginScreen();
        loginScreen.setVisible(true);
    }    
}
