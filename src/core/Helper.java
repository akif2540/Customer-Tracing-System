package core;

import javax.swing.*;

public class Helper {


    public static void setTheme() {
        for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
            if (info.getName().equals("Nimbus")) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (ClassNotFoundException | UnsupportedLookAndFeelException | IllegalAccessException |
                         InstantiationException e) {
                    throw new RuntimeException(e);
                }
                break;
            }
        }
    }

    public static boolean isFieldEmpty(JTextField f) {
        return f.getText().trim().isEmpty();
    }

    public static boolean isFieldList(JTextField[] fields) {
        for (JTextField fi : fields) {
            if (isFieldEmpty(fi)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidEmail(String mail) {

        if (!mail.contains("@")) return false;

        String[] parts = mail.split("@");
        if (parts.length != 2) return false;

        if (parts[0].trim().isEmpty() || parts[1].trim().isEmpty()) return false;

        if (!parts[1].contains(".")) return false;

        return true;
    }

    public static void optionPanelDialogTR() {
        UIManager.put("OptionPane.okButtonText", "Tamam");
        UIManager.put("OptionPane.yesButtonText", "Evet");
        UIManager.put("OptionPane.noButtonText", "Hayır");

    }

    public static void showMsg(String message) {
        String msg;
        String title;

        optionPanelDialogTR();

        switch (message) {
            case "fill":
                msg = "Lütfen Tüm alanları Doldurunuz!";
                title = "HATA!";
                break;
            case "done":
                msg = "İşlem Başarılı";
                title = "Sonuç";
                break;
            case "error":
                msg = "Bir Hata Oluştu";
                title = "HATA!";

            default:
                msg = message;
                title = "mesaj";
        }

        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }

    public static boolean confirm(String str) {
        optionPanelDialogTR();
        String msg;

        if (str.equals("sure")) {
            msg = "Bu işlemi Gerçekleştirmek İstediğinize Emin Misiniz?";
        } else {
            msg = str;
        }
        return JOptionPane.showConfirmDialog(null, msg, "Emin Misin ?", JOptionPane.YES_NO_OPTION) == 0;
    }
}

