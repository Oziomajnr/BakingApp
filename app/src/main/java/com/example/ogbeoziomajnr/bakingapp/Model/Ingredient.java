package com.example.ogbeoziomajnr.bakingapp.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by SQ-OGBE PC on 27/06/2017.
 * An ingredient is the list of ingredients needed to make a recipe
 */

public class Ingredient implements Parcelable {
    public static final Creator<Ingredient> CREATOR = new Creator<Ingredient>() {
        @Override
        public Ingredient createFromParcel(Parcel in) {
            Ingredient ingredient = new Ingredient();
            ingredient.quantity = in.readString();
            ingredient.measure = in.readString();
            ingredient.ingredient = in.readString();

            return ingredient;
        }

        @Override
        public Ingredient[] newArray(int size) {
            return new Ingredient[size];
        }
    };
    // number of ingredients to put
    @SerializedName("quantity")
    private String quantity;
    // what do you use to measure the ingredients
    @SerializedName("measure")
    private String measure;
    // the name of the ingredient
    @SerializedName("ingredient")
    private String ingredient;

    protected Ingredient(Parcel in) {
        quantity = in.readString();
        measure = in.readString();
        ingredient = in.readString();
    }

    public Ingredient() {
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getMeasure() {
        return measure;
    }

    public void setMeasure(String measure) {
        this.measure = measure;
    }

    public String getIngredient() {
        return ingredient;
    }

    public void setIngredient(String ingredient) {
        this.ingredient = ingredient;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quantity);
        dest.writeString(measure);
        dest.writeString(ingredient);
    }
}
