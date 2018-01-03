package com.example.mg.tryappkillan.form.help;

import android.drm.DrmEvent;
import android.drm.DrmManagerClient;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mg on 17/11/16.
 */

public class CompositeListener implements View.OnClickListener{

    //ArrayList == List<View.OnClickListener>?
    private List<View.OnClickListener> clickListeners = new ArrayList<>();

    public void registerListener(View.OnClickListener clickListener)
    {
       this.clickListeners.add(clickListener);
    }
    @Override
    public void onClick(View view) {

    }
}
