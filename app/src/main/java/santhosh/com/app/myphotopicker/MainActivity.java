package santhosh.com.app.myphotopicker;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.santhosh.camgal.CamGal;
import com.santhosh.camgal.DefaultCallback;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    CamGal camGal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        camGal = new CamGal(this);

        Button button = (Button) findViewById(R.id.select);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                camGal.selectImage();
            }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CamGal.handleActivityResult(requestCode, resultCode, data, this, new DefaultCallback() {
            @Override
            public void onImagePickerError(Exception e) {
                Log.e("Error", e.getLocalizedMessage());
            }

            @Override
            public void onImagePicked(@NonNull File file) {
                Log.d("File Name", file.getName());
                Toast.makeText(MainActivity.this, file.getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
