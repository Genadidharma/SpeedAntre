package bamboomedia.speedantre.FragmentAdmin;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import bamboomedia.speedantre.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Fragment_BookAdmin extends Fragment {


    public Fragment_BookAdmin() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_admin, container, false);
    }

}
