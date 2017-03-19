package com.edfora.sunny.edfora.network;

import com.edfora.sunny.edfora.EdforaConstans;
import com.edfora.sunny.edfora.models.Track;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Sunny on 19-03-2017.
 */

public class EdforaApi {

    private static EdforaApiInterface sEdforaApiInterface;

    public static EdforaApiInterface getApi() {
        if (sEdforaApiInterface == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(EdforaConstans.API_URL)
                    .addConverterFactory(GsonConverterFactory.create(new GsonBuilder()
                            .create()))
                    .build();

            sEdforaApiInterface = retrofit.create(EdforaApiInterface.class);
        }
        return sEdforaApiInterface;
    }

    public interface EdforaApiInterface {


        @GET("cokestudio")
        Call<List<Track>> getTrackDetails();


    }
}

