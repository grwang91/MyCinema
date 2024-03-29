package techtown.org.mycinema;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import techtown.org.mycinema.R;


public class ListFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        PagerFragment pagerFragment = new PagerFragment();
        getFragmentManager().beginTransaction().add(R.id.container, pagerFragment).commit();

        return rootView;
    }

}
