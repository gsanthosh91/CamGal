package com.santhosh.camgal;

import android.support.annotation.NonNull;

import java.io.File;

/**
 * Created by santhosh@appoets.com on 23-05-2018.
 */
public class DefaultCallback implements CamGal.Callbacks {

    @Override
    public void onImagePickerError(Exception e) {

    }

    @Override
    public void onImagePicked(@NonNull File uri) {

    }

    @Override
    public void onCanceled(int type) {

    }
}
