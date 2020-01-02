package techtown.org.mycinema;

import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;


import techtown.org.mycinema.Lists.List1Fragment;
import techtown.org.mycinema.MovieListApi.Movie;
import techtown.org.mycinema.MovieListApi.MovieList;
import techtown.org.mycinema.R;

import static techtown.org.mycinema.AppHelper.println;
import static techtown.org.mycinema.MainActivity.database;

public class PagerFragment extends Fragment {

    public static MovieList movieList;
    private MovieListAdapter adapter;
    private ViewPager pager;
    private static int total;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.movie_lists, container, false);

        if (AppHelper.requestQueue == null) {
            AppHelper.requestQueue = Volley.newRequestQueue(getContext());
        }


        pager = (ViewPager) rootView.findViewById(R.id.pager);
        pager.setOffscreenPageLimit(3);

        adapter = new MovieListAdapter(getFragmentManager());

        RequestSend rs = new RequestSend() {
            @Override
            public void processResponse(String response) {
                super.processResponse(response);
                Gson gson = new Gson();
                movieList = gson.fromJson(response, MovieList.class);
                total = movieList.result.size();
                List1Fragment[] fragments = new List1Fragment[total];

                int i;

                for (i=0; i<=total-1; i++) {
                    fragments[i] = new List1Fragment();
                    fragments[i].movie = movieList.result.get(i);
                    adapter.addItem(fragments[i]);
                    AppHelper.insertDataOutline(database,fragments[i].movie);
                }
                pager.setAdapter(adapter);
            }
        };

        if (NetworkStatus.getConnectivityStatus(getContext()) == NetworkStatus.TYPE_NOT_CONNECTED) {
            // DB에서 불러오기
            Cursor cursor = AppHelper.selectTable(database, AppHelper.selectTableOutlineSql, "outline");
            total = cursor.getCount();
            println(Integer.toString(total));
            List1Fragment[] fragments = new List1Fragment[total];
            Movie[] movies = new Movie[total];
            int i;

            for (i=0; i<=total-1; i++) {


                cursor.moveToNext();

                movies[i] = new Movie();
                movies[i].id = cursor.getString(0);
                movies[i].title = cursor.getString(1);
                movies[i].reservation_rate = cursor.getFloat(2);
                movies[i].grade = cursor.getInt(3);
                fragments[i] = new List1Fragment();
                fragments[i].movie = movies[i];
                println(movies[i].title);
                adapter.addItem(fragments[i]);
            }
            pager.setAdapter(adapter);



        } else {
            rs.sendRequest("http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList?type=1");

        }
        return rootView;
    }



    class MovieListAdapter extends FragmentStatePagerAdapter {
        ArrayList<Fragment> items = new ArrayList<Fragment>();

        public MovieListAdapter(@NonNull FragmentManager fm) {
            super(fm);
        }

        public void addItem(Fragment item) {
            items.add(item);
        }

        @Override
        public Fragment getItem(int position) {
            return items.get(position);
        }

        @Override
        public int getCount() {
            return items.size();
        }
    }
}
