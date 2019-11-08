package techtown.org.mydrawer.Lists;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.util.ArrayList;

import techtown.org.mydrawer.ImageLoadTask;
import techtown.org.mydrawer.Lists.Sangse.SangseList1;
import techtown.org.mydrawer.MovieListApi.Movie;
import techtown.org.mydrawer.MovieListApi.MovieContent;
import techtown.org.mydrawer.MovieListApi.MovieList;
import techtown.org.mydrawer.R;
import techtown.org.mydrawer.RequestSend;

public class List1Fragment extends Fragment {

    public Movie movie;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_1, container, false);

        final String id = movie.id;

        ImageView imageView = (ImageView) rootView.findViewById(R.id.imageView);
        TextView nameText = (TextView) rootView.findViewById(R.id.nameText);
        TextView reserveRateText = (TextView) rootView.findViewById(R.id.reservationRate);
        TextView ageText = (TextView) rootView.findViewById(R.id.age);

        setText(nameText, movie.id + ". " + movie.title);
        setText(reserveRateText, "예매율 " + movie.reservation_rate + "%");
        setText(ageText, movie.grade + "세 관람가");

        sendImageRequest(movie.image, imageView);

        Button button = (Button) rootView.findViewById(R.id.sangse);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SangseList1 sangse1 = new SangseList1();
                RequestSend rq = new RequestSend() {
                    @Override
                    public void processResponse(String response) {
                        super.processResponse(response);
                        Gson gson = new Gson();
                        //Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                        MovieContent movieContent = gson.fromJson(response, MovieContent.class);

                        sangse1.movieSangse = movieContent.result.get(0);
                        getFragmentManager().beginTransaction().replace(R.id.container, sangse1).commit();
                    }
                };

                    rq.sendRequest("http://boostcourse-appapi.connect.or.kr:10000/movie/readMovie?id="+id);


            }
        });

        return rootView;
    }

    public void sendImageRequest(String address, ImageView imageView) {
        String url = address;

        ImageLoadTask task = new ImageLoadTask(url, imageView);
        task.execute();
    }

    public void setText(TextView textView, String string) {
        textView.setText(string);
    }
}