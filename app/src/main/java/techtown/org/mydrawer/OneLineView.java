package techtown.org.mydrawer;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatRatingBar;

public class OneLineView extends LinearLayout {

    TextView id1;
    TextView time1;
    AppCompatRatingBar rating1;
    TextView comment1;

    public OneLineView(Context context) {
        super(context);
        init(context);
    }

    public OneLineView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private void init(Context context) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.one_line, this, true);

        id1 = (TextView) findViewById(R.id.id);
        time1 = (TextView) findViewById(R.id.time);
        rating1 = (AppCompatRatingBar) findViewById(R.id.rating);
        comment1 = (TextView) findViewById(R.id.comment);

    }

    public void setId1(String id) {
        id1.setText(id);
    }

    public void setTime1(int time) {
        time1.setText(time + "분전");
    }

    public void setRating1(float rating) {
        rating1.setRating(rating);
    }

    public void setComment1(String comment) {
        comment1.setText(comment);
    }
}
