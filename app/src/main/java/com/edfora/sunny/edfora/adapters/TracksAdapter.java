package com.edfora.sunny.edfora.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.edfora.sunny.edfora.EdforaConstans;
import com.edfora.sunny.edfora.utils.ImageUtils;
import com.edfora.sunny.edfora.PlayerActivity;
import com.edfora.sunny.edfora.R;
import com.edfora.sunny.edfora.models.Track;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sunny on 19-03-2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements Filterable {


    private List<Track> tracks;
    private List<Track> filteredList;
    private Context context;
    private TrackFilter mFilter;

    public TracksAdapter(Context context, List<Track> items) {
        this.context = context;
        this.tracks = items;
        this.filteredList =new ArrayList<>();
        filteredList.addAll(items);
        mFilter = new TrackFilter(TracksAdapter.this);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder = null;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View v1 = inflater.inflate(R.layout.item_track, parent, false);
        viewHolder = new TrackItemViewHolder(v1);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        TrackItemViewHolder trackItemViewHolder = (TrackItemViewHolder) holder;
        trackItemViewHolder.setTrackValues(filteredList.get(position));
    }

    @Override
    public int getItemCount() {
        return filteredList.size();
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }

    public class TrackItemViewHolder extends RecyclerView.ViewHolder {

        ImageView trackImage;
        TextView trackDetails;
        TextView trackName;
        CardView trackCard;

        public TrackItemViewHolder(View v) {
            super(v);
            trackImage = (ImageView) v.findViewById(R.id.iv_track_image);
            trackDetails = (TextView) v.findViewById(R.id.tv_track_details);
            trackName = (TextView) v.findViewById(R.id.tv_track_title);
            trackCard = (CardView) v.findViewById(R.id.cv_track);
        }

        public void setTrackValues(final Track track) {
            ImageUtils.loadImage(context, trackImage, track.getCoverImage());

            trackName.setText(track.getSong());
            trackDetails.setText(track.getArtists());
            trackCard.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra(EdforaConstans.EXTRA_TRACK, tracks.indexOf(track));
                    context.startActivity(intent);
                }
            });
        }

    }

    public class TrackFilter extends Filter {
        private TracksAdapter mAdapter;

        private TrackFilter(TracksAdapter mAdapter) {
            super();
            this.mAdapter = mAdapter;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();
            if (constraint.length() == 0) {
                filteredList.addAll(tracks);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();
                for (final Track track : tracks) {
                    if (track.getSong().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(track);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            this.mAdapter.notifyDataSetChanged();
        }
    }


}
