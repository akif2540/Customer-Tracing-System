package view;

import business.UserController;
import core.Helper;
import dao.UserDao;
import entity.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginUI extends JFrame {
    private JPanel container;
    private JPanel pnl_top;
    private JLabel lbl_title;
    private JPanel pnl_bottom;
    private JTextField fld_mail;
    private JButton btn_login;
    private JLabel lbl_mail;
    private JLabel lbl_password;
    private JPasswordField fld_password;
    private UserController userController;


    public LoginUI() {
        this.userController = new UserController();

        this.add(container);
        this.setTitle("Müşteri Yönetim Sistemi");
        this.setSize(400, 400);

        // UI Başlangıç noktası

        int x = ((Toolkit.getDefaultToolkit().getScreenSize().width - this.getSize().width) / 2);
        int y = ((Toolkit.getDefaultToolkit().getScreenSize().height - this.getSize().height) / 2);
        this.setLocation(x, y);
        this.setVisible(true);


        this.btn_login.addActionListener(e -> {
            JTextField[] checkList = {this.fld_mail, this.fld_password};


            if (Helper.isFieldList(checkList)) {
                Helper.showMsg("fill");
            } else if (!Helper.isValidEmail(this.fld_mail.getText())) {
                Helper.showMsg("Geçerli Bir E-posta Adersi Giriniz!!");

            } else {
                User user = this.userController.findByLogin(this.fld_mail.getText(), this.fld_password.getText());
                if (user == null) {
                    Helper.showMsg("Girdiğiniz Bilgilere Göre Kullanıcı Bulunamadı:");
                } else {
                    Helper.showMsg("done");
                    this.dispose(); // ekranı kapat
                    DashboardUI dashboardUI = new DashboardUI(user); // dashborda giriş

                }
            }
        });
    }
}
