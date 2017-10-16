import java.net.*;
import java.io.*;

public class Fymbot {
    public static void main(String[] args) {
        Fymbot bot = new Fymbot("irc.chat.twitch.tv", "#lulzik", "fymbot", "oauth:50f1fqsuwlckwpcq8kbteta0h39fxr");
        try {
            bot.connect();
            bot.sendMessage("haHAA");
        }
        catch(Exception e) {

        }
    }
    private String server;
    private String channel;
    private String nick;
    private String auth;
    private Reader reader;
    private BufferedReader br;
    private BufferedWriter bw;
    
    public Fymbot() {

    }
    public Fymbot(String server, String channel, String nick, String auth) {
        this.server = server;
        this.channel = channel;
        this.nick = nick;
        this.auth = auth;
    }

    private void connect() throws Exception {
        reader = new Reader(this, server, channel, nick, auth);
        reader.start();
        // Socket socket = new Socket(server, 6667);
        // br = new BufferedReader(
        //     new InputStreamReader(socket.getInputStream()));
        // bw = new BufferedWriter(
        //     new OutputStreamWriter(socket.getOutputStream()));
        // bw.write("PASS " + this.auth + "\r\n");
        // bw.write("NICK " + this.nick + "\r\n");
        // bw.write("JOIN " + this.channel + "\r\n");
        // bw.flush();
    }

    public void sendMessage(String message) throws Exception {
        System.out.println("Trying to send: " + message);
        bw.write("PRIVMSG " + this.channel + " :" + message + "\r\n");
        bw.flush();
    }
    public void handleIncomingMessage(String author, String message) {
        System.out.println(author + ": " + message);
    }

    // public void read() throws Exception {
    //     System.out.println("twitch read");
    //     String line = null;
        
    //     while ((line = br.readLine()) != null) {
    //         if (line.indexOf("004") >= 0) {
    //             // Login successful.
    //             break;
    //         }
    //         else if (line.indexOf("433") >= 0) {
    //             System.out.println("Nick already in use.");
    //             System.exit(1);
    //         }
    //     }
    //     while ((line = br.readLine()) != null) {
    //         if (line.contains("PRIVMSG " + channel)){
    //             String sender = line.substring(1, line.indexOf('!'));
    //             String message = line.substring(line.indexOf(':', line.indexOf("PRIVMSG " + channel)) + 1);
    //             handleIncomingMessage(sender, message);
    //         }
            
    //         if (line.contains("PING")){
    //             bw.write("PONG :" + this.server + "\r\n");
    //             bw.flush();
    //         }
    //         // else {
    //         //     System.out.println(line);   //Print out the line received from the server
    //         // }                               
    //     }
    // }
}