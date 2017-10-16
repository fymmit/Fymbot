public class Message {
    private User user;
    private String content;

    public Message() {

    }
    public Message(User user, String content) {
        this.user = user;
        this.content = content;
    }

    public User getUser() {
        return this.user;
    }
    public String getContent() {
        return this.content;
    }
    public String toString() {
        return user.getUsername() + ": " + content;
    }
}