package com.example.m05368.eatwhat;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;

import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.m05368.eatwhat.Fragment.DiaryFragment;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.text.SimpleDateFormat;


public class DiaryDetail extends AppCompatActivity {

    private Toolbar toolbar;
    private Button save,btn_camera,btn_gallery;
    private TextView date,name;
    private EditText meal,price,comment;
    private Spinner score;
    private ImageView image;
    static final int PICK_FROM_CAMERA = 1, PICK_FROM_GALLERY= 2 ;
    private SQLiteDatabase db;
    private DBHelper helper;
    private int id,score_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = this.openOrCreateDatabase("eatWhat_database", android.content.Context.MODE_PRIVATE, null);
        helper = new DBHelper(this.getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.diary_detail);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        date = (TextView) findViewById(R.id.date);
        name = (TextView) findViewById(R.id.name);
        meal = (EditText) findViewById(R.id.meal);
        price = (EditText) findViewById(R.id.price);
        score = (Spinner) findViewById(R.id.score);
        comment = (EditText) findViewById(R.id.comment);

        image = (ImageView)findViewById(R.id.image);

        Intent intent = this.getIntent();
        id = intent.getIntExtra("id",0);

        Cursor c=db.rawQuery("SELECT * FROM diary WHERE _id = "+id,null);
        c.moveToFirst();


        date.setText(c.getString(1)+"/"+c.getString(2)+"/"+c.getString(3));
        name.setText(c.getString(4));
        meal.setText(c.getString(6));
        price.setText(c.getString(7));
        comment.setText(c.getString(9));
        if(c.getBlob(10)!=null) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(c.getBlob(10), 0, c.getBlob(10).length);
            image.setImageBitmap(bitmap);
        }

        scoreAdapter();
        score.setSelection(c.getInt(8));

        saveOnClick();
        cameraOnClick();
        galleryOnClick();

    }


    private void scoreAdapter(){
        ArrayAdapter<CharSequence> nAdapter = ArrayAdapter.createFromResource(
                this, R.array.score_array, android.R.layout.simple_spinner_item );
        nAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        score.setAdapter(nAdapter);

        score.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                score_position = score.getSelectedItemPosition();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void saveOnClick() {
        save = (Button) findViewById(R.id.save);
        save.setOnClickListener(new Button.OnClickListener() {

            @Override
            public void onClick(View v) {

                helper.adddiaryinfo(id,meal.getText().toString(),price.getText().toString(),score_position,comment.getText().toString());
                Toast.makeText(DiaryDetail.this,"儲存成功",Toast.LENGTH_SHORT);
                finish();
            }
        });
    }

    private void cameraOnClick(){
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

    private void galleryOnClick(){
        btn_gallery = (Button) findViewById(R.id.btn_gallery);
        btn_gallery.setOnClickListener(new Button.OnClickListener() {

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

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
            byte[] img = bos.toByteArray();

            ContentValues values=new ContentValues();
            values.put("D_picture",img);
            db.update("diary",values,"_id="+ id ,null);

        }else if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK && null != data) {
            Uri imageUri = data.getData();
            try {
                InputStream imageStream = getContentResolver().openInputStream(imageUri);
                Bitmap imageBitmap = BitmapFactory.decodeStream(imageStream);
                image.setImageBitmap(imageBitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}



