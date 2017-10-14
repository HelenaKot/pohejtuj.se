package com.fancytank.kit.pohejtujse.api.holder;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fancytank.kit.pohejtujse.R;

// 10/15/2017.
public class CameraViewHolder {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private ImageView container;
    private Activity root;


    public CameraViewHolder(final View cameraView, Activity root) {
        this.container = cameraView.findViewById(R.id.camera_image);
        this.root = root;
        cameraView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dispatchTakePictureIntent();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(root.getPackageManager()) != null) {
            root.startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    public void setImageBitmap(Bitmap imageBitmap) {
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        container.setLayoutParams(params);
        container.setImageBitmap(imageBitmap);
    }
}
