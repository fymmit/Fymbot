import java.io.*;
import java.net.*;

public class MessageHandler extends Thread {
    Fymbot bot;
    String server;
    String channel;
    String nick;
    String auth;
    BufferedReader br;
    BufferedWriter bw;

    public MessageHandler(Fymbot bot, String server, String channel, String nick, String auth) {
        this.bot = bot;
        this.server = server;
        this.channel = channel;
        this.nick = nick;
        this.auth = auth;
    }

    public void run() {
        try {
            connect();
            read();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void connect() throws Exception {
        Socket socket = new Socket(server, 6667);
        br = new BufferedReader(
            new InputStreamReader(socket.getInputStream()));
        bw = new BufferedWriter(
            new OutputStreamWriter(socket.getOutputStream()));
        bw.write("PASS " + this.auth + "\r\n");
        bw.write("NICK " + this.nick + "\r\n");
        bw.write("JOIN " + this.channel + "\r\n");
        bw.write("PRIVMSG " + this.channel + " :Ready for takeoff.\r\n");
        bw.flush();
    }
    public void sendMessage(String message) throws Exception {
        System.out.println("Trying to send: " + message);
        bw.write("PRIVMSG " + this.channel + " :" + message + "\r\n");
        bw.flush();
    }
    private void handleIncomingMessage(Message message) throws Exception {
        System.out.println(message.toString());
        bot.addUserToUserlist(message.getUser());
        bot.checkIfMessageIsCommand(message);
    }
    public void read() throws Exception {
        System.out.println("twitch read");
        String line = null;
        
        while ((line = br.readLine()) != null) {
            if (line.indexOf("004") >= 0) {
                // Login successful.
                break;
            }
            else if (line.indexOf("433") >= 0) {
                System.out.println("Nick already in use.");
                System.exit(1);
            }
        }
        while ((line = br.readLine()) != null) {
            if (line.contains("PRIVMSG " + channel)){
                String sender = line.substring(1, line.indexOf('!'));
                String content = line.substring(line.indexOf(':', line.indexOf("PRIVMSG " + channel)) + 1);
                Message message = new Message(new User(sender), content);

                // bot.handleIncomingMessage(sender, content);
                handleIncomingMessage(message);
            }
            
            if (line.contains("PING")){
                bw.write("PONG :" + this.server + "\r\n");
                bw.flush();
            }
            // else {
            //     System.out.println(line);   //Print out the line received from the server
            // }                               
        }
    }
}
