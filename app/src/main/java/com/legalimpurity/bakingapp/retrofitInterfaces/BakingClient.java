package com.legalimpurity.bakingapp.retrofitInterfaces;

import com.legalimpurity.bakingapp.objects.Recipe;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by rajatkhanna on 21/08/17.
 */

public interface BakingClient {
    @GET("/topher/{year}/{month}/59121517_baking/baking.json")
    Call<ArrayList<Recipe>> recipesForBaking(
            @Path("year") String year,
            @Path("month") String month
    );
}
