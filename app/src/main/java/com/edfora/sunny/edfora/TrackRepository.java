package com.edfora.sunny.edfora;

import com.edfora.sunny.edfora.models.Track;

import java.util.List;

/**
 * Created by Sunny on 19-03-2017.
 */

public class TrackRepository {

    private static TrackRepository trackRepository;

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    List<Track> tracks;

    private TrackRepository() {
    }


    public static TrackRepository getInstance() {
        if (null == trackRepository) {
            trackRepository = new TrackRepository();
        }

        return trackRepository;
    }




}
