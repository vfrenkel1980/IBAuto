package frameworkInfra.utils;

import com.aventstack.extentreports.Status;

import javax.mail.*;
import javax.mail.internet.MimeBodyPart;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * Static class that is used in order to check, read, download and delete mail.
 * Current implementation is in Ecommerce site
 */
public class MailService {

    public static boolean checkMailBySubject(String host, String user, String password, String subject) {
        Store store = connect(host, user, password);
        boolean isPresent = false;
        Folder emailFolder = null;
        try {
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            isPresent = checkMailBySubject(subject,emailFolder);
            emailFolder.close(true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeConnection(store);
        return isPresent;
    }

    public static Message getMailBySubject(String host, String user, String password, String subject) {
        Store store = connect(host, user, password);
        Folder emailFolder = null;
        Message msg = null;
        try {
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            msg = getMailBySubject(subject, emailFolder);
            emailFolder.close(true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeConnection(store);
        return msg;
    }

    public static void deleteMail(String host, String user, String password) {
        Store store = connect(host, user, password);
        Folder emailFolder = null;
        try {
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_WRITE);
            deleteMail(emailFolder);
            emailFolder.close(true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        closeConnection(store);

    }

    public static boolean saveMessageAttachments(String host, String user, String password, String subject, String downloadPath) {
        Store store = connect(host, user, password);
        boolean res = false;
        Message msg = null;
        Folder emailFolder = null;
        try {
            emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            msg = getMailBySubject(subject, emailFolder);
            res = saveAttachments(msg, downloadPath);
            emailFolder.close(true);
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        closeConnection(store);
        return res;
    }


    public static Store connect(String host, String user, String password) {
        Store store = null;
        try {
            Properties properties = new Properties();
            properties.put("mail.store.protocol", "imaps");
            properties.put("mail.imaps.host", "imap.gmail.com");
            properties.put("mail.imaps.port", "993");

            Session session = Session.getDefaultInstance(properties);
            store = session.getStore("imaps");
            store.connect(host, user, password);
        } catch (MessagingException e) {
            test.log(Status.WARNING, "Email connection failed with error: " + e.getMessage());
        }
        return store;
    }

    public static void closeConnection(Store store){
        try {
            store.close();
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkMailBySubject(String subject,Folder emailFolder) {
        try {
            boolean isPresent = false;
            int timeout = 0;
            Message[] messages;
            do {
                SystemActions.sleep(5);
                timeout++;
                messages = emailFolder.getMessages();
            } while (messages.length == 0 && timeout < 90);

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
            return isPresent;
        } catch (Exception e) {
            test.log(Status.WARNING, "Failed to retrieve Email message with error: " + e.getMessage());
            return false;
        }
    }

    private static Message getMailBySubject(String subject, Folder emailFolder) {
        Message[] messages;
        Message res = null;
        try {
            messages = emailFolder.getMessages();
            for (Message message : messages) {
                if (message.getSubject().equals(subject)) {
                    res = message;
                }
            }
        } catch (MessagingException e) {
            test.log(Status.WARNING, "Failed to retrieve Email message with error: " + e.getMessage());
        }
        return res;
    }

    public static void deleteMail(Folder emailFolder) {
        try {
            Message[] messages = emailFolder.getMessages();
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                System.out.println("deleting " + i);
                message.setFlag(Flags.Flag.DELETED, true);
            }
            test.log(Status.INFO, "Email messages deleted successfully");
        } catch (MessagingException e) {
            test.log(Status.WARNING, "Failed to delete Email message with error: " + e.getMessage());
        }
    }

    private static boolean saveAttachments(Message msg, String downloadPath) {
        boolean res = false;
        try {
            if (msg.getContent() instanceof Multipart) {
                Multipart multipart = (Multipart) msg.getContent();
                for (int i = 0; i < multipart.getCount(); i++) {
                    Part part = multipart.getBodyPart(i);
                    String disposition = part.getDisposition();

                    if ((disposition != null) &&
                            ((disposition.equalsIgnoreCase(Part.ATTACHMENT) ||
                                    (disposition.equalsIgnoreCase(Part.INLINE))))) {
                        MimeBodyPart mimeBodyPart = (MimeBodyPart) part;
                        String fileName = mimeBodyPart.getFileName();

                        File fileToSave = new File(fileName);
                        mimeBodyPart.saveFile(downloadPath + fileToSave);
                        res = true;
                    }
                }
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (MessagingException e1) {
            e1.printStackTrace();
        }
        return res;
    }

}
