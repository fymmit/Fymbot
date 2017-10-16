import java.net.*;
import java.io.*;
import java.util.ArrayList;

public class Fymbot {
    private String server;
    private String channel;
    private String nick;
    private String auth;
    private MessageHandler messageHandler;
    private BufferedReader br;
    private BufferedWriter bw;
    private ArrayList<User> users;
    private ArrayList<Command> commands;
    
    public Fymbot() {
        
    }
    public Fymbot(String server, String channel, String nick, String auth) {
        this.server = server;
        this.channel = channel;
        this.nick = nick;
        this.auth = auth;
        users = new ArrayList<User>();
        commands = new ArrayList<Command>();
        try {
            Socket socket = new Socket(server, 6667);
            bw = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream()));
        }
        catch (Exception e) {

        }
    }
    
    public void connect() throws Exception {
        messageHandler = new MessageHandler(this, server, channel, nick, auth);
        messageHandler.start();
    }
    public void checkIfMessageIsCommand(Message message) throws Exception {
        boolean found = false;
        for (Command c : commands) {
            if (c.getCmd().equals(message.getContent())) {
                c.respond();
                break;
            }
        }
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
    public void addCommand(String cmd, String response) {
        commands.add(new Command(this, cmd, response));
    }
    public MessageHandler getMessageHandler() {
        return messageHandler;
    }
}