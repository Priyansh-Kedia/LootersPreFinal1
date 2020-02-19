package com.example.looters;

import android.app.Activity;
import android.app.Dialog;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.DrawableImageViewTarget;

import java.util.Random;

public class CustLoad {
    Activity activity;
    Dialog dialog;

    public CustLoad(Activity activity) {
        this.activity = activity;
    }

    public void showloader()
    {
        Random random = new Random();
        int i = random.nextInt(100);
        int string;
        dialog = new Dialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setContentView(R.layout.loader);
        ImageView imageView = dialog.findViewById(R.id.customloader);

        if (i%2==0)
        {
            string = R.drawable.glass;
        }
        else string = R.drawable.burgerloader;

        Glide.with(activity).load(string).into(new DrawableImageViewTarget(imageView));
        dialog.show();
    }
    public void hideloader()
    {
        dialog.dismiss();
    }
}
