package techtown.org.mycinema.Lists.Sangse;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.BaseAdapter;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.text.NumberFormat;
import java.util.ArrayList;

import techtown.org.mycinema.MovieListApi.Comments;
import techtown.org.mycinema.MovieListApi.MovieComment;
import techtown.org.mycinema.MovieListApi.MovieSangse;
import techtown.org.mycinema.CommentActivity;
import techtown.org.mycinema.CommentItem;
import techtown.org.mycinema.ImageLoadTask;
import techtown.org.mycinema.OneLineActivity;
import techtown.org.mycinema.OneLineView;
import techtown.org.mycinema.R;
import techtown.org.mycinema.RequestSend;

public class SangseList1 extends Fragment {

    public MovieSangse movieSangse;
    protected int index = 0;
    protected TextView goodNum;
    protected TextView badNum;
    protected Button good;
    protected Button bad;
    protected int goodTmp;
    protected int badTmp;
    protected CommentAdapter adapter;
    protected ListView listView;
    public ImageView movieImage;
    public TextView movieName;
    public MovieComment movieComment;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = (View) inflater.inflate(R.layout.sangse_list1, container,false);

        final int id = movieSangse.id;
        good = (Button) rootView.findViewById(R.id.good);
        bad = (Button) rootView.findViewById(R.id.bad);
        goodNum = (TextView) rootView.findViewById(R.id.goodNum);
        badNum = (TextView) rootView.findViewById(R.id.badNum);
        movieImage = (ImageView) rootView.findViewById(R.id.movieImage);
        movieName = (TextView) rootView.findViewById(R.id.movieName);
        ImageView gradeImage = (ImageView) rootView.findViewById(R.id.gradeImage);
        TextView openDate = (TextView) rootView.findViewById(R.id.openDate);
        TextView genreTime = (TextView) rootView.findViewById(R.id.genreTime);
        TextView reservation = (TextView) rootView.findViewById(R.id.reservation);
        AppCompatRatingBar ratingBar = (AppCompatRatingBar) rootView.findViewById(R.id.ratingBar);
        TextView ratingScore = (TextView) rootView.findViewById(R.id.ratingScore);
        TextView viewers = (TextView) rootView.findViewById(R.id.viewers);
        TextView synopsis = (TextView) rootView.findViewById(R.id.synopsis);
        TextView director = (TextView) rootView.findViewById(R.id.director);
        TextView actor = (TextView) rootView.findViewById(R.id.actor);

        Button jaksung = (Button) rootView.findViewById(R.id.jacksung);
        Button show_all = (Button) rootView.findViewById(R.id.show_all);
        listView = (ListView) rootView.findViewById(R.id.listView);



        goodTmp = movieSangse.like;
        badTmp = movieSangse.dislike;

        sendImageRequest(movieSangse.thumb,movieImage);
        movieName.setText(movieSangse.title);
        if (movieSangse.grade == 15) {
            gradeImage.setImageResource(R.drawable.ic_15);
        } else if (movieSangse.grade == 12) {
            gradeImage.setImageResource(R.drawable.ic_12);
        } else if (movieSangse.grade == 19) {
            gradeImage.setImageResource(R.drawable.ic_19);
        }
        openDate.setText(movieSangse.date);
        genreTime.setText(movieSangse.genre + " / " + movieSangse.duration + "분");
        reservation.setText("예매율\n" + movieSangse.reservation_grade + "위 " + movieSangse.reservation_rate + "%");
        ratingScore.setText(String.format("%.1f",movieSangse.audience_rating));
        ratingBar.setRating(movieSangse.audience_rating/2);
        viewers.setText("누적관객수\n" + NumberFormat.getInstance().format(movieSangse.audience)+"명");
        synopsis.setText(movieSangse.synopsis);
        director.setText(movieSangse.director);
        actor.setText(movieSangse.actor);
        goodNum.setText(Integer.toString(goodTmp));
        badNum.setText(Integer.toString(badTmp));




        adapter = new CommentAdapter();

        RequestSend rq = new RequestSend(){
            @Override
            public void processResponse(String response) {
                super.processResponse(response);

                Gson gson = new Gson();
                movieComment = gson.fromJson(response, MovieComment.class);
                ArrayList<Comments> comments = movieComment.result;

                int i;
                Comments temp;

                for (i=0; i<comments.size(); i++) {
                    temp = comments.get(i);
                    adapter.addItem(new CommentItem(temp.writer, temp.timestamp ,temp.rating, temp.contents));
                }
                listView.setAdapter(adapter);



            }
        };
        rq.sendRequest("http://boostcourse-appapi.connect.or.kr:10000//movie/readCommentList?id=" + movieSangse.id);


        //작성하기 버튼 눌렸을 때
        jaksung.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommentActivity.class);
                intent.putExtra("id", movieSangse.id);
                startActivityForResult(intent, 102);
            }
        });

        //모두보기 버튼 눌렸을 때
        show_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), OneLineActivity.class);
                intent.putExtra("comments", adapter.items);
                intent.putExtra("movie", movieSangse.title);
                intent.putExtra("grade", movieSangse.grade);
                intent.putExtra("rating", movieSangse.user_rating);
                intent.putExtra("total", movieComment.totalCount);
                intent.putExtra("id", movieSangse.id);
                startActivityForResult(intent, 101);
            }
        });






        good.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (index == 0) {    //index == 0 -> 좋아요 싫어요 아무것도 눌려있지 않으면.
                    index = 1;
                    good.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    goodNum.setText(Integer.toString(goodTmp+1));
                }
                else if (index == 1) {   //index ==1 -> 좋아요 눌려있으면
                    index = 0;
                    good.setBackgroundResource(R.drawable.ic_thumb_up);
                    goodNum.setText(Integer.toString(goodTmp));
                }

                else if (index == 2){    //index == 2 -> 싫어요 눌려있으면
                    index = 1;
                    good.setBackgroundResource(R.drawable.ic_thumb_up_selected);
                    bad.setBackgroundResource(R.drawable.ic_thumb_down);
                    goodNum.setText(Integer.toString(goodTmp+1));
                    badNum.setText(Integer.toString(badTmp));

                }
            }
        });

        bad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (index == 0) {
                    index = 2;
                    bad.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    badNum.setText(Integer.toString(badTmp+1));

                }

                else if (index == 1) {
                    index = 2;
                    bad.setBackgroundResource(R.drawable.ic_thumb_down_selected);
                    good.setBackgroundResource(R.drawable.ic_thumb_up);
                    badNum.setText(Integer.toString(badTmp+1));
                    goodNum.setText(Integer.toString(goodTmp));


                }

                else {
                    index = 0;
                    bad.setBackgroundResource(R.drawable.ic_thumb_down);
                    badNum.setText(Integer.toString(badTmp));

                }
            }
        });

        return rootView;
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

            OneLineView view = new OneLineView(getContext());

            CommentItem item = items.get(position);
            view.setId1(item.getId());
            view.setTime1(item.getTime());
            view.setRating1(item.getRating());
            view.setComment1(item.getComment());

            return view;
        }
    }

    public void setImage(Bitmap bitmap) {
        movieImage.setImageBitmap(bitmap);
    }

    public void setText(String name) {
        movieName.setText(name);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 102 && resultCode == Activity.RESULT_OK) {
            String content = data.getStringExtra("content");
            float rating = data.getFloatExtra("rating", 0.0f);
            int time = data.getIntExtra("time", 0);
            adapter.addItem(new CommentItem("grwang", time, rating, content));
            listView.setAdapter(adapter);
        }

        if (requestCode == 101 && resultCode == Activity.RESULT_OK) {
            ArrayList<CommentItem> items2 = new ArrayList<CommentItem>();
            items2 = (ArrayList<CommentItem>) data.getSerializableExtra("comments");
            adapter.items = items2;
            listView.setAdapter(adapter);
        }
    }

    public void sendImageRequest(String address, ImageView imageView) {
        String url = address;

        ImageLoadTask task = new ImageLoadTask(url, imageView);
        task.execute();
    }



}
