package cloudInfra.IncrediCloud.webServer;

import com.aventstack.extentreports.Status;
import frameworkInfra.Listeners.SuiteListener;
import org.testng.annotations.Listeners;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import static frameworkInfra.Listeners.SuiteListener.test;

@Listeners(SuiteListener.class)
public class WebServer implements Runnable {

    private ServerSocket server;
    private int port;
    public String secret;

    public WebServer(int port){
        this.port = port;
        this.secret = "";
    }

    public String startWebServer(){
        test.log(Status.INFO, "Web Server STARTED");
        try {
            server = new ServerSocket(port);
            return listenToPort();

        } catch (IOException e) {
            test.log(Status.ERROR, "Failed to start Web Server with error \n" + e.getMessage());
            return null;
        }
    }

    public void closeWebServer(){
        try {
            server.close();
        } catch (IOException e) {
            test.log(Status.ERROR, " Failed to close WebServer with error: " + e.getMessage());
        }
    }

    private String listenToPort(){
        test.log(Status.INFO, "Listening for connection on port " + port + " ....");
        while (true) {
            try (Socket socket = server.accept()) {
                secret = extractSecretFromURI(socket);
                break;
            } catch (IOException e) {
                test.log(Status.ERROR, "Failed while listening to port with error \n" + e.getMessage());
            }
            finally {
                return secret;
            }
        }
    }

    private String extractSecretFromURI(Socket socket){
        String path = "";
        try {
            DataInputStream dis = new DataInputStream(socket.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(dis));
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println("HTTP/1.0 200 OK");
            out.println("Access-Control-Allow-Origin: *");
            out.println("Access-Control-Allow-Headers: authorization,ocp-apim-subscription-key");
            out.println("");
            String request = br.readLine();
            String[] requestParam = request.split(" ");
            path = requestParam[1];
            return path.substring(path.lastIndexOf("=") + 1);
        }catch (IOException e){
            e.getMessage();
            return null;
        }
    }

    @Override
    public void run() {
        secret = startWebServer();
    }
}
