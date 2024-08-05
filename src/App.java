import business.UserController;
import core.Database;
import core.Helper;
import entity.User;
import view.DashboardUI;
import view.LoginUI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class App {
    public static void main(String[] args) {
        LoginUI loginUI = new LoginUI();
        Helper.setTheme();

//        UserController userController = new UserController();
//        User user = userController.findByLogin("akif@gmail.com", "12345");
//        DashboardUI dashboardUI = new DashboardUI(user);


    }
}
