package com.example.ogbeoziomajnr.bakingapp.Util;

import android.content.Context;

import com.example.ogbeoziomajnr.bakingapp.R;

/**
 * Created by SQ-OGBE PC on 11/07/2017.
 */

public  class Utility {
    public static boolean isTablet (Context context) {
      return  context.getResources().getBoolean(R.bool.isTablet);
    }
}
