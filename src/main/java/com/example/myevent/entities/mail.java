package com.example.myevent.entities;
/*import javafx.scene.control.Alert;
import java.util.Properties;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Transport;
import javax.mail.Authenticator;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.PasswordAuthentication;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class mail {
    public static void send(String to, String sub, String msg, final String user, final String pass) {
        Properties props = new Properties();

        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("zeinebchekir742@gmail.com", "wesa pvwm qfus fkzd");
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(user));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(sub);
            message.setText(msg);

            Transport.send(message);

            // Afficher une alerte JavaFX pour indiquer que l'email a été envoyé avec succès
            Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
            successAlert.setContentText("Email envoyé avec succès !");
            successAlert.setHeaderText(null);
            successAlert.show();

        } catch (MessagingException e) {
            // En cas d'erreur, afficher une alerte avec un message approprié
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setContentText("Une erreur est survenue lors de l'envoi de l'email : " + e.getMessage());
            errorAlert.setHeaderText(null);
            errorAlert.show();
            e.printStackTrace();
        }
    }
}*/
