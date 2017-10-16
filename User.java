public class User {
    private String username;
    private int points;

    public User() {
        this.username = "";
        this.points = 0;
    }
    public User(String username) {
        this.username = username;
        this.points = 0;
    }
    public String getUsername() {
        return username;
    }

    public void givePoints(int amount) {
        points = points + amount;
    }
}