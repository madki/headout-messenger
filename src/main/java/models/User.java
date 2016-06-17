package models;

/**
 * Created by madki on 17/06/16.
 */
public class User {
    private String id;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                '}';
    }
}
