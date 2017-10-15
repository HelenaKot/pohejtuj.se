package com.fancytank.kit.pohejtujse.holder;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fancytank.kit.pohejtujse.ImageUtil;
import com.fancytank.kit.pohejtujse.R;
import com.mindorks.paracamera.Camera;

// 10/15/2017.
public class CameraViewHolder {
    private ImageView container;
    private Activity root;
    private Camera camera;
    static private Bitmap bitmap; // todo oh god forgive me for i have sinned

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
        buildCamera();

        try {
            camera.takePicture();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildCamera() {
        if (camera != null) {
            return;
        }
        camera = new Camera.Builder()
                .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
                .setTakePhotoRequestCode(1)
                .setDirectory("pics")
                .setName("ali_" + System.currentTimeMillis())
                .setImageFormat(Camera.IMAGE_JPEG)
                .setCompression(75)
                .setImageHeight(500)// it will try to achieve this height as close as possible maintaining the aspect ratio;
                .build(root);
    }

    public void notifyImageTaken() {
        bitmap = camera.getCameraBitmap();
        if (bitmap != null) {
            setImageBitmap(bitmap);
        }
    }

    private void setImageBitmap(Bitmap imageBitmap) {
        ViewGroup.LayoutParams params = container.getLayoutParams();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.MATCH_PARENT;
        container.setLayoutParams(params);
        container.setImageBitmap(imageBitmap);
    }

    public static String getImage() {
        if (bitmap == null)
            return null;
        return ImageUtil.convert(bitmap);
    }
}
