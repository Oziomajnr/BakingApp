package com.example.ogbeoziomajnr.bakingapp.Interface;

import com.example.ogbeoziomajnr.bakingapp.Model.Recipe;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 */

public interface ApiInterface {

    @GET("/topher/2017/May/59121517_baking/baking.json")
    Call<List<Recipe> > getRecipes();
}
