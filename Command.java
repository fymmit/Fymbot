public class Command extends Message {
    private Fymbot bot;
    private Message message;
    private String cmd;
    private String response;

    public Command(Fymbot bot, String cmd, String response) {
        this.bot = bot;
        this.cmd = cmd;
        this.response = response;
    }

    public void respond() throws Exception {
        bot.getMessageHandler().sendMessage(response);
    }
    public String getCmd() {
        return cmd;
    }
}