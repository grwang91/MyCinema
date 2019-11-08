package techtown.org.mydrawer;

import java.io.Serializable;

public class CommentItem implements Serializable {

    String id;
    int time;
    float rating;
    String comment;

    public CommentItem(String id, int time, float rating, String comment) {
        this.id = id;
        this.time = time;
        this.rating = rating;
        this.comment = comment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentItem{" +
                "id='" + id + '\'' +
                ", time='" + time + '\'' +
                ", rating=" + rating +
                ", comment='" + comment + '\'' +
                '}';
    }
}
