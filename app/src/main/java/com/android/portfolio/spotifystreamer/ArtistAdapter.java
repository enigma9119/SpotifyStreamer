package com.android.portfolio.spotifystreamer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import butterknife.ButterKnife;
import butterknife.InjectView;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.Pager;

/**
 * Created by Smitesh on 6/28/2015.
 * An adapter to load a list of artists in the search screen.
 */
public class ArtistAdapter extends RecyclerView.Adapter<ArtistAdapter.ViewHolder> {
    private Pager<Artist> mArtists;

    public void updateAdapter(Pager<Artist> artists) {
        mArtists = artists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_artist, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Artist artist = mArtists.items.get(position);
        Context context = holder.mArtistImage.getContext();

        holder.mArtistName.setText(artist.name);
        if (artist.images.size() != 0) {
            Picasso.with(context).load(artist.images.get(0).url)
                    .placeholder(R.drawable.artist_placeholder)
                    .into(holder.mArtistImage);
        } else {
            // Load a placeholder image if no artist image is found
            Picasso.with(context).load(R.drawable.artist_placeholder).into(holder.mArtistImage);
        }
    }

    @Override
    public int getItemCount() {
        if (mArtists != null && mArtists.items.size() != 0)
            return mArtists.items.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @InjectView(R.id.artist_name)
        TextView mArtistName;
        @InjectView(R.id.artist_image)
        ImageView mArtistImage;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
        }
    }
}
