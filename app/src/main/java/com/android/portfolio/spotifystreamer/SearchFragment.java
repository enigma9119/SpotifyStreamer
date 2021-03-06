package com.android.portfolio.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;


/**
 * A fragment used to search and populate search results.
 */
public class SearchFragment extends Fragment {

    private ArtistAdapter mArtistAdapter;

    @InjectView(R.id.recyclerview_artists)
    RecyclerView mRecyclerView;
    @InjectView(R.id.no_artists)
    TextView mNoArtists;

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
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Specify an adapter
        mArtistAdapter = new ArtistAdapter();
        mRecyclerView.setAdapter(mArtistAdapter);

        return rootView;
    }

    public void searchArtist(String query) {
        FetchArtistsTask artistsTask = new FetchArtistsTask();
        artistsTask.execute(query);
    }

    private class FetchArtistsTask extends AsyncTask<String, Void, ArtistsPager> {

        @Override
        protected ArtistsPager doInBackground(String... params) {
            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            return spotify.searchArtists(params[0]);
        }

        @Override
        protected void onPostExecute(ArtistsPager artistsPager) {
            if (artistsPager != null && !artistsPager.artists.items.isEmpty()) {
                mNoArtists.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.VISIBLE);
                mArtistAdapter.updateAdapter(artistsPager.artists);
                mArtistAdapter.notifyDataSetChanged();
            } else {
                // If no artists are found, display a message
                mNoArtists.setVisibility(View.VISIBLE);
                mRecyclerView.setVisibility(View.GONE);
            }
        }
    }
}
