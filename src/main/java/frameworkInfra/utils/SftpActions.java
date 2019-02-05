
package frameworkInfra.utils;

import com.aventstack.extentreports.Status;
import com.jcraft.jsch.*;
import com.jcraft.jsch.ChannelSftp;

import java.util.Vector;

import static frameworkInfra.Listeners.SuiteListener.test;

/**
 * @code  Example: download last modifyied file from dir from server
 *          String remoteFilePath = "/var/www/incredl3/public_html/ibusagelogs/";
 *         String user = "web_admin";
 *         String host = "35.205.56.131";
 *         String ppk = "C:\\Users\\Aleksandra\\ssh\\known_hosts\\google_compute_engine.ppk";
 *         String filename = SftpActions.lastModifyiedFile(user, host, ppk, remoteFilePath);
 *         SftpActions.downloadFile(user,host,ppk,"C:\\Users\\Admin\\Desktop\\todayLog.txt",remoteFilePath+filename);
 */
public class SftpActions {

    public static boolean downloadFile(String user, String host, String ppk, String localfilename, String remoteFileName) {
        Session session = null;
        Channel channel = null;
        boolean res = false;
        try {
            session = sessionConnect(user, host, ppk);
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;

            System.out.println("Starting File download");
            channelSftp.get(remoteFileName, localfilename);
            res = true;

        } catch (JSchException | SftpException exception) {
            exception.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return res;
    }

    public static boolean uploadFile(String user, String host, String ppk, String localfilename, String remoteFileName) {

        Session session = null;
        Channel channel = null;
        boolean res = false;
        try {
            session = sessionConnect(user, host, ppk);
            channel = session.openChannel("sftp");
            channel.connect();
            ChannelSftp channelSftp = (ChannelSftp) channel;

            System.out.println("Starting File upload");
            channelSftp.put(localfilename, remoteFileName);
            res = true;

        } catch (JSchException | SftpException exception) {
            exception.printStackTrace();
        } finally {
            if (channel != null) {
                channel.disconnect();
            }
            if (session != null) {
                session.disconnect();
            }
        }
        return res;
    }

    public static Session sessionConnect(String user, String host, String ppk) throws JSchException {
        JSch jsch = new JSch();
        jsch.addIdentity(ppk);
        Session session = jsch.getSession(user, host);
        session.setTimeout(20 * 1000);
        session.setConfig("StrictHostKeyChecking", "no");
        session.connect();

        return session;
    }

    public static String oldestFile(String user, String host, String ppk, String remotePath) {
        Session session = null;
        ChannelSftp channelSftp = null;
        Channel channel = null;
        Vector list = null;
        int currentOldestTime;
        int nextTime = 2140000000; //Made very big for future-proofing
        ChannelSftp.LsEntry lsEntry = null;
        SftpATTRS attrs = null;
        String nextName = null;
        String oldestFile = "";
        boolean fileFound = false;
        try {
            session = sessionConnect(user, host, ppk);
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remotePath);
            list = channelSftp.ls("*");
            if (list.isEmpty()) {
                fileFound = false;
            }
            else {
                lsEntry = (ChannelSftp.LsEntry) list.firstElement();
                oldestFile = lsEntry.getFilename();
                attrs = lsEntry.getAttrs();
                currentOldestTime = attrs.getMTime();
                for (Object sftpFile : list) {
                    lsEntry = (ChannelSftp.LsEntry) sftpFile;
                    nextName = lsEntry.getFilename();
                    attrs = lsEntry.getAttrs();
                    nextTime = attrs.getMTime();
                    if (nextTime < currentOldestTime) {
                        oldestFile = nextName;
                        currentOldestTime = nextTime;
                    }
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

        return oldestFile;
    }

    public static String lastModifiedFile(String user, String host, String ppk, String remotePath) {
        Session session = null;
        ChannelSftp channelSftp = null;
        Channel channel = null;
        Vector list = null;
        int currentOldestTime;
        int nextTime = 2140000000; //Made very big for future-proofing
        ChannelSftp.LsEntry lsEntry = null;
        SftpATTRS attrs = null;
        String nextName = null;
        String lastModifiedFile = "";
        boolean fileFound = false;
        try {
            session = sessionConnect(user, host, ppk);
            channel = session.openChannel("sftp");
            channel.connect();
            channelSftp = (ChannelSftp) channel;
            channelSftp.cd(remotePath);
            list = channelSftp.ls("*");
            if (list.isEmpty()) {
                if (test != null)
                    test.log(Status.WARNING, "Remote directory is empty");
                return lastModifiedFile;
            }
            else {
                lsEntry = (ChannelSftp.LsEntry) list.firstElement();
                lastModifiedFile = lsEntry.getFilename();
                attrs = lsEntry.getAttrs();
                currentOldestTime = attrs.getMTime();
                for (Object sftpFile : list) {
                    lsEntry = (ChannelSftp.LsEntry) sftpFile;
                    nextName = lsEntry.getFilename();
                    attrs = lsEntry.getAttrs();
                    nextTime = attrs.getMTime();
                    if (nextTime > currentOldestTime) {
                        lastModifiedFile = nextName;
                        currentOldestTime = nextTime;
                    }
                }
            }
        } catch (SftpException e) {
            e.printStackTrace();

        } catch (
                Exception ex) {
            ex.printStackTrace();
        }

        return lastModifiedFile;
    }
}


