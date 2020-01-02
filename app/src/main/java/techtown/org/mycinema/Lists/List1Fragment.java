package techtown.org.mycinema.Lists;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;

import java.net.URL;

import techtown.org.mycinema.AppHelper;
import techtown.org.mycinema.Lists.Sangse.SangseList1;
import techtown.org.mycinema.MovieListApi.Movie;
import techtown.org.mycinema.MovieListApi.MovieContent;
import techtown.org.mycinema.ImageLoadTask;
import techtown.org.mycinema.NetworkStatus;
import techtown.org.mycinema.R;
import techtown.org.mycinema.RequestSend;

import static techtown.org.mycinema.MainActivity.database;

public class List1Fragment extends Fragment {

    public Movie movie;
    public static int network;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.list_1, container, false);

        final String id = movie.id;


        network = NetworkStatus.getConnectivityStatus(getContext());

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

        if (network == NetworkStatus.TYPE_MOBILE || network == NetworkStatus.TYPE_WIFI) {
            ImageLoadTask task = new ImageLoadTask(url, imageView);
            task.execute();

            //DB 저장
            try {
                URL uurl = new URL(url);
                Bitmap bitmap = BitmapFactory.decodeStream(uurl.openConnection().getInputStream());
                if (bitmap == null){
                    AppHelper.println("널");
                }
                byte[] bytes = AppHelper.bitmapToByteArray(bitmap);
                database.execSQL("update outline set image="+bytes + " where id = " + movie.id);
            } catch(Exception e) {
                e.printStackTrace();
            }


        } else {

        }

    }

    public void setText(TextView textView, String string) {
        textView.setText(string);
    }
}