package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import javax.mail.*;
import java.util.Properties;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Static class that is used in order to check and delete mail.
 * Current implementation is in Ecommerce site
 *
 */
public class MailService {

    public static boolean checkMailBySubject(String host, String user, String password, String subject)
    {
        try {
            boolean isPresent = false;
            int timeout = 0;
            Message[] messages;
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.gmail.com");
            properties.put("mail.imaps.port", "993");

            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            do {
                SystemActions.sleep(5);
                timeout ++;
                messages = emailFolder.getMessages();
            } while (messages.length == 0 && timeout < 60);

            for (Message message : messages) {
                if (message.getSubject().equals(subject))
                    isPresent = true;
            }
            /*Message message = messages[0];
            subject = message.getSubject();*/

                /*System.out.println("Email Number " + (i + 1));
                System.out.println("Subject: " + message.getSubject());
                System.out.println("From: " + message.getFrom()[0]);
                System.out.println("Text: " + message.getContent().toString());*/

            emailFolder.close(false);
            store.close();

            return isPresent;


        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to retrieve Email message with error: " + e.getMessage());
            return false;
        }
    }

    public static void deleteMail(String host, String user, String password)
    {
        try
        {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.gmail.com");
            properties.put("mail.imaps.port", "993");

            Session session = Session.getDefaultInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, user, password);

            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);

            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("deleting " + i);
                message.setFlag(Flags.Flag.DELETED, true);
            }
            test.log(Status.INFO, "Email messages deleted successfully");
            emailFolder.close(true);
            store.close();
        } catch (MessagingException e) {
            test.log(Status.WARNING, "Failed to delete Email message with error: " + e.getMessage());
        }
    }



}
