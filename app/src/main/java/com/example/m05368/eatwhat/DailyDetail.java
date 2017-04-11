package com.example.m05368.eatwhat;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;



public class DailyDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private Button btn_camera,btn_album;
    private ImageView image;
    static final int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY= 2 ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        image = (ImageView)findViewById(R.id.image);

        cameraOnclick();
        albumOnclick();

    }

    private void cameraOnclick(){
        btn_camera = (Button) findViewById(R.id.btn_camera);
        btn_camera.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, PICK_FROM_CAMERA);
                }
            }
        });
    }

    private void albumOnclick(){
        btn_album = (Button) findViewById(R.id.btn_album);
        btn_album.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_PICK, null);
                i.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
                startActivityForResult(i, PICK_FROM_GALLERY);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_FROM_CAMERA && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            image.setImageBitmap(imageBitmap);
        }else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();
            // String picturePath contains the path of selected Image
            // Show the Selected Image on ImageView
            image.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        }
    }
}



