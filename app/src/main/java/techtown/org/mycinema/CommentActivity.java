package techtown.org.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import techtown.org.mycinema.R;

public class CommentActivity extends AppCompatActivity {

    int id;
    EditText editText;
    RatingBar ratingBar;
    int grade;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        ratingBar = (RatingBar) findViewById(R.id.ratings);
        Button cancel = (Button) findViewById(R.id.cancel);
        Button save = (Button) findViewById(R.id.save);
        editText = (EditText) findViewById(R.id.editText);

        TextView movieName = (TextView) findViewById(R.id.movieNameComment);
        ImageView gradeImage = (ImageView) findViewById(R.id.gradeImageComment);

        Intent passedIntent = getIntent();
        processIntent(passedIntent);

        movieName.setText(title);

        if(grade == 12) {
            gradeImage.setImageResource(R.drawable.ic_12);
        } else if (grade == 15) {
            gradeImage.setImageResource(R.drawable.ic_15);
        } else {
            gradeImage.setImageResource(R.drawable.ic_19);
        }

        //확인버튼 눌렀을 때
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();

                //int time = (int)(System.currentTimeMillis()/1000);
                String url = "http://boostcourse-appapi.connect.or.kr:10000/movie/createComment?id="
                        + id + "&writer=grwang&rating=" + ratingBar.getRating() + "&contents=" + editText.getText().toString();
                RequestSend rq = new RequestSend();
                rq.sendRequest(url);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

        //취소버튼 눌렀을 때
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            id = (int) intent.getIntExtra("id",0);
            grade = (int) intent.getIntExtra("grade",0);
            title = (String) intent.getStringExtra("movie");
        }
    }

}
