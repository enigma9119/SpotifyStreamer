package com.android.portfolio.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A placeholder fragment containing a simple view.
 */
public class SearchFragment extends Fragment {

    private ArtistAdapter mArtistAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @InjectView(R.id.recyclerview_artists)
    RecyclerView mRecyclerView;

    public SearchFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        ButterKnife.inject(this, rootView);

        // Improve performance since changes in adapter content won't change size of RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // Use a linear layout manager
        mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Specify an adapter
        mArtistAdapter = new ArtistAdapter();
        mRecyclerView.setAdapter(mArtistAdapter);

        return rootView;
    }

    public void searchArtist(String query) {
        FetchArtistsTask artistsTask = new FetchArtistsTask();
        artistsTask.execute();
    }

    private class FetchArtistsTask extends AsyncTask<Void, Void, ArtistsPager> {

        @Override
        protected ArtistsPager doInBackground(Void... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            return spotify.searchArtists("beyonce");
        }

        @Override
        protected void onPostExecute(ArtistsPager artistsPager) {
            if (artistsPager != null) {
                mArtistAdapter.updateAdapter(artistsPager.artists);
                mArtistAdapter.notifyDataSetChanged();
            }
        }
    }
}
