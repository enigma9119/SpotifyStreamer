package com.android.portfolio.spotifystreamer;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;


/**
 * A placeholder fragment containing a simple view.
 */
public class DetailFragment extends Fragment {

    public static final String EXTRA_ARTIST = "parcelable_artist";

    public DetailFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        Intent intent = getActivity().getIntent();

        if (intent != null && intent.hasExtra(EXTRA_ARTIST)) {
            ParcelableArtist parcelableArtist = intent.getParcelableExtra(EXTRA_ARTIST);
            Toast.makeText(getActivity(), parcelableArtist.getArtistName(), Toast.LENGTH_SHORT).show();
        }

        return rootView;
    }
}
