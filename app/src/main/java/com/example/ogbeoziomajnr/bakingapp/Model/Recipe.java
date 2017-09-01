package com.example.ogbeoziomajnr.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 */

public class Recipe implements Parcelable {
    public static final Parcelable.Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel source) {
            Recipe recipe = new Recipe();
            recipe.setId(source.readInt());
            recipe.setName(source.readString());
            recipe.setServings(source.readString());
            recipe.setImage(source.readString());

            List<Ingredient> recipeIngredients = new ArrayList<>();
            List<Step> recipeSteps = new ArrayList<>();
            source.readList(recipeIngredients, null);
            source.readList(recipeSteps, null);
            recipe.setSteps(recipeSteps);
            recipe.setIngredients(recipeIngredients);
            return recipe;
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };
    // this is the id that uniquely identifies each recipe
    @SerializedName("id")
    @Expose
    private Integer id;
    // the name of the recipe
    @SerializedName("name")
    @Expose
    private String name;
    // number of items serving
    @SerializedName("servings")
    @Expose
    private String servings;
    // a link to the image url
    @SerializedName("image")
    @Expose
    private String image;
    // the ingerdients needed to make the recipe
    @SerializedName("ingredients")
    @Expose
    private List<Ingredient> ingredients;
    // the ingerdients needed to make the recipe
    @SerializedName("steps")
    @Expose
    private List<Step> steps;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public List<Step> getSteps() {
        return steps;
    }

    public void setSteps(List<Step> steps) {
        this.steps = steps;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
        dest.writeTypedList(ingredients);
        dest.writeTypedList(steps);
    }
}
