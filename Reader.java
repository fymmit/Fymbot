import java.io.*;
import java.net.*;

public class Reader extends Thread {
    Fymbot bot;
    String server;
    String channel;
    String nick;
    String auth;
    BufferedReader br;
    BufferedWriter bw;

    public Reader(Fymbot bot, String server, String channel, String nick, String auth) {
        this.bot = bot;
        this.server = server;
        this.channel = channel;
        this.nick = nick;
        this.auth = auth;
    }
    
    public void run() {
        connect();
        read();
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
        bw.flush();
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
                String message = line.substring(line.indexOf(':', line.indexOf("PRIVMSG " + channel)) + 1);
                bot.handleIncomingMessage(sender, message);
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