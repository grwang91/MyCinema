package techtown.org.mydrawer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
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


import techtown.org.mydrawer.Lists.List1Fragment;
import techtown.org.mydrawer.MovieListApi.MovieList;

public class PagerFragment extends Fragment {

    public static MovieList movieList;
    private MovieListAdapter adapter;
    private ViewPager pager;

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
                int total = movieList.result.size();
                List1Fragment[] fragments = new List1Fragment[total];

                int i;

                for (i=0; i<=total-1; i++) {
                    fragments[i] = new List1Fragment();
                    fragments[i].movie = movieList.result.get(i);
                    adapter.addItem(fragments[i]);
                }
                pager.setAdapter(adapter);
            }
        };

        rs.sendRequest("http://boostcourse-appapi.connect.or.kr:10000/movie/readMovieList?type=1");

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
