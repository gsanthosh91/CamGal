package com.santhosh.camgal;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.app.Activity.RESULT_OK;

/**
 * Created by santhosh@appoets.com on 23-05-2018.
 */
public class CamGal {

    private static final Integer SELECT_FILE = 1;
    private static final int REQUEST_CAMERA = 2;
    private Activity activity;
    private final CharSequence[] items = {"Take Photo", "Choose from library", "Cancel"};
    AlertDialog alertDialog;

    public CamGal(Activity activity) {
        this.activity = activity;
    }

    public void selectImage() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.alert_layout, null);
        dialogBuilder.setView(dialogView);

        ImageView camera = (ImageView) dialogView.findViewById(R.id.camera);
        camera.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                cameraIntent(activity);
                alertDialog.dismiss();
                alertDialog.dismiss();
            }
        });

        ImageView gallery = (ImageView) dialogView.findViewById(R.id.gallery);
        gallery.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                alertDialog.dismiss();
                galleryIntent(activity);
            }
        });
        alertDialog = dialogBuilder.create();
        alertDialog.show();

        /*AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (item == 0) {
                    cameraIntent(activity);
                } else if (item == 1) {
                    galleryIntent(activity);
                } else if (item == 2) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();*/
    }

    private void galleryIntent(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            activity.startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
        } else {
            Log.e("CamGal", "WRITE_EXTERNAL_STORAGE permission is not given");
        }
    }

    private void cameraIntent(Activity activity) {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            activity.startActivityForResult(intent, REQUEST_CAMERA);
        } else {
            Log.e("CamGal", "CAMERA permission is not given");
        }
    }

    public static void handleActivityResult(int requestCode, int resultCode, Intent data, Activity activity, @NonNull Callbacks callbacks) {
        if (requestCode == SELECT_FILE && resultCode == RESULT_OK) {
            onPictureReturnedFromGallery(data, activity, callbacks);
        } else if (requestCode == REQUEST_CAMERA && resultCode == RESULT_OK) {
            onPictureReturnedFromCamera(activity, callbacks);
        }
    }

    public interface Callbacks {
        void onImagePickerError(Exception e);

        void onImagePicked(File file);

        void onCanceled(int type);
    }

    private static void onPictureReturnedFromGallery(Intent data, Activity activity, @NonNull Callbacks callbacks) {
        try {
            Uri uri = data.getData();
            File file = new File(uri.toString());
            callbacks.onImagePicked(file);
        } catch (Exception e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e);
        }
    }

    private static void onPictureReturnedFromCamera(Activity activity, @NonNull Callbacks callbacks) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();

        File photoFile = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            photoFile.createNewFile();
            fo = new FileOutputStream(photoFile);
            fo.write(bytes.toByteArray());
            fo.close();
            callbacks.onImagePicked(photoFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e);
        } catch (IOException e) {
            e.printStackTrace();
            callbacks.onImagePickerError(e);
        }
    }


}
