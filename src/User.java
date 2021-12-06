public class User implements Comparable<User> {

    private int id;
    private String username;
    private int points;

    public User(int id, String username, int points) {
        this.id = id;
        this.username = username;
        this.points = points;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void addPoints(int points) {
        this.points = this.points + points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int getPoints() {
        return this.points;
    }

    @Override
    public int compareTo(User user) {
        return (this.points - user.points);
    }
}
