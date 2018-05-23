# CamGal allows you to easily capture image from the gallery, camera without creating lots of boilerplate.

## Gradle depl̥endency

```
repositories {
        maven { url 'https://jitpack.io' }
}

dependencies {
        implementation 'com.github.gsanthosh91:CamGal:0.1.1'
}

```

## Usage

```        
CamGal camGal = new CamGal(this);
        
Button button = (Button) findViewById(R.id.select);
button.setOnClickListener(new View.OnClickListener() {
    public void onClick(View v) {
        camGal.selectImage();
    }
});

```

## Getting the photo file

```
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
            }
     });
}

```
