import java.net.*;
import java.io.*;

public class Fymbot {
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
    }
    public void sendMessage(String message) throws Exception {
        System.out.println("Trying to send: " + message);
        bw.write("PRIVMSG " + this.channel + " :" + message + "\r\n");
        bw.flush();
    }
    public void handleIncomingMessage(String author, String message) {
        System.out.println(author + ": " + message);
    }
    
    public static void main(String[] args) {
        String server = "";
        String channel = "";
        String nick = "";
        String auth = "";
        Fymbot bot = new Fymbot(server, channel, nick, auth);
        try {
            bot.connect();
            bot.sendMessage("haHAA");
        }
        catch(Exception e) {

        }
    }
}