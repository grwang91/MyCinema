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
import android.widget.ListView;

import java.util.ArrayList;

import techtown.org.mycinema.R;

public class OneLineActivity extends AppCompatActivity {
    CommentAdapter adapter;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_line);


        Intent passedIntent = getIntent();
        adapter = new CommentAdapter();
        listView = (ListView) findViewById(R.id.listview_all);
        processIntent(passedIntent);
        listView.setAdapter(adapter);

        Button jaksung = (Button) findViewById(R.id.jaksung);
        jaksung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CommentActivity.class);
                startActivityForResult(intent, 103);
            }
        });


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 103 && resultCode == Activity.RESULT_OK) {

            String content = data.getStringExtra("content");
            float rating = data.getFloatExtra("rating", 0.0f);
            adapter.addItem(new CommentItem("grwang", 10, rating, content));
            listView.setAdapter(adapter);
        }

    }

    private void processIntent(Intent intent) {
        if(intent != null) {
            ArrayList<CommentItem> items2 = new ArrayList<CommentItem>();
            items2 = (ArrayList<CommentItem>) intent.getSerializableExtra("comments");
            if(items2 != null) {
                adapter.items = items2;
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
