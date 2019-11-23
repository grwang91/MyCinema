package techtown.org.mycinema;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;

import techtown.org.mycinema.R;

public class OneLineActivity extends AppCompatActivity {
    CommentAdapter adapter;
    ListView listView;
    TextView movieName;
    ImageView gradeImage;
    RatingBar ratingBar;
    TextView rating;
    TextView total;
    int grade;
    int id;

    String movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_line);

        movieName = (TextView) findViewById(R.id.movieName);
        gradeImage = (ImageView) findViewById(R.id.gradeImage);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        rating = (TextView) findViewById(R.id.rating);
        total = (TextView) findViewById(R.id.total);

        Intent passedIntent = getIntent();
        adapter = new CommentAdapter();
        listView = (ListView) findViewById(R.id.listview_all);
        processIntent(passedIntent);
        listView.setAdapter(adapter);

        //작성하기 버튼
        Button jaksung = (Button) findViewById(R.id.jaksung);
        jaksung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("movie", movie);
                intent.putExtra("grade",grade);
                startActivityForResult(intent, 103);
            }
        });

        //뒤로가기 버튼
        Button button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("comments", adapter.items);
                setResult(Activity.RESULT_OK, intent);
                finish();
            }
        });

    }

    //뒤로가기 버튼
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent();
        intent.putExtra("comments", adapter.items);        // 질문 1. 뒤로가기 버튼 눌렸을때 button_back이 눌렸을 때와 똑같이 동작시키고 싶은데 그냥 뒤로가기밖에 되지 않습니다...
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//
//        if (requestCode == 103 && resultCode == Activity.RESULT_OK) {
//
//            String content = data.getStringExtra("content");
//            float rating = data.getFloatExtra("rating", 0.0f);
//            int time = data.getIntExtra("time",0);
//            adapter.addItem(new CommentItem("grwang", time, rating, content));
//            listView.setAdapter(adapter);
//        }
//
//    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            ArrayList<CommentItem> items2 = new ArrayList<CommentItem>();
            items2 = (ArrayList<CommentItem>) intent.getSerializableExtra("comments");

            movie = (String) intent.getStringExtra("movie");
            grade = (int) intent.getIntExtra("grade",0);
            float ratingScore = (float)intent.getFloatExtra("rating",0);
            int totalNumber = (int) intent.getIntExtra("total",0);
            id = (int) intent.getIntExtra("id",0);

            if(items2 != null) {
                adapter.items = items2;
            }
            movieName.setText(movie);
            rating.setText(" " + Float.toString(ratingScore*2) +" ");
            ratingBar.setRating(ratingScore);
            total.setText("(" + Integer.toString(totalNumber)+"명 참여)");

            if (grade == 12) {
                gradeImage.setImageResource(R.drawable.ic_12);
            } else if (grade == 15) {
                gradeImage.setImageResource(R.drawable.ic_15);
            } else if (grade == 19) {
                gradeImage.setImageResource(R.drawable.ic_19);
            }


        }
    }

    class CommentAdapter extends BaseAdapter {
        ArrayList<CommentItem> items = new ArrayList<CommentItem>();

        @Override
        public int getCount() {

            return items.size();
        }

        @Override
        public Object getItem(int position) {

            return items.get(position);
        }

        @Override
        public long getItemId(int position) {

            return position;
        }

        public void addItem(CommentItem item) {
            items.add(item);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            OneLineView view = new OneLineView(getApplicationContext());

            CommentItem item = items.get(position);
            view.setId1(item.getId());
            view.setTime1(item.getTime());
            view.setRating1(item.getRating());
            view.setComment1(item.getComment());

            return view;
        }
    }

}
