import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Fymbot {
    private String server;
    private String channel;
    private String nick;
    private String auth;
    private Reader reader;
    private BufferedReader br;
    private BufferedWriter bw;
    private ArrayList<User> users;
    
    public Fymbot() {
        
    }
    public Fymbot(String server, String channel, String nick, String auth) {
        this.server = server;
        this.channel = channel;
        this.nick = nick;
        this.auth = auth;
        this.users = new ArrayList<User>();
    }
    
    public void connect() throws Exception {
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
    public void handleIncomingMessage(Message message) {
        System.out.println(message.toString());
        addUserToUserlist(message.getUser());
    }
    public void addUserToUserlist(User user) {
        boolean found = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(user.getUsername())) {
                found = true;
                break;
            }
        }
        if (!found) {
            users.add(user);
            System.out.println("Added: " + user.getUsername());
        }
        else {
            System.out.println("User already in list.");
        }
    }
}