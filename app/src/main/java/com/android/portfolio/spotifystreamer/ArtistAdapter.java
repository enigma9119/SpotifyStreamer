package com.android.portfolio.spotifystreamer;

import android.content.Context;
import android.content.Intent;
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
        if (!artist.images.isEmpty()) {
            Picasso.with(context).load(artist.images.get(0).url)
                    .placeholder(R.drawable.artist_placeholder)
                    .into(holder.mArtistImage);
            holder.createParcelable(artist, artist.images.get(0).url);
        } else {
            // Load a placeholder image if no artist image is found
            Picasso.with(context).load(R.drawable.artist_placeholder).into(holder.mArtistImage);
            holder.createParcelable(artist, null);
        }
    }

    @Override
    public int getItemCount() {
        if (mArtists != null)
            return mArtists.items.size();
        else
            return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @InjectView(R.id.artist_name)
        TextView mArtistName;
        @InjectView(R.id.artist_image)
        ImageView mArtistImage;

        private ParcelableArtist mParcelableArtist;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.inject(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra(DetailFragment.EXTRA_ARTIST, mParcelableArtist);
            v.getContext().startActivity(intent);
        }

        public void createParcelable(Artist artist, String artistImageUrl) {
            mParcelableArtist = new ParcelableArtist();
            mParcelableArtist.setArtistName(artist.name);
            mParcelableArtist.setArtistImageUrl(artistImageUrl);
            mParcelableArtist.setArtistId(artist.id);
        }
    }
}
