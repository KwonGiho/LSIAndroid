package com.example.kwongyo.lsi;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Movie;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.sendBtn)
    Button sendBtn;
    @Bind(R.id.videoView)
    VideoView videoView;
    Bitmap bitmap;

    private MediaController mediaController;
    private String selectedVideoPath;
    private static int RESULT_LOAD_IMAGE = 1;
    private static int RESULT_LOAD_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.sendBtn)
    public void sendBtnClick(View view) {
        //Retrofit movieUploadRetrofit = new Retrofit.Builder().baseUrl("127.0.0.1:8089/MovieUploadServlet").build();
        Log.d("here","click데스~~1111");
        Intent i = new Intent(Intent.ACTION_PICK,MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        i.setType("video/*, images/*");
        startActivityForResult(i,RESULT_LOAD_VIDEO);
        Log.d("here","click데스~~");


    }
    @OnClick(R.id.videoView)
    public void imageViewClick(View view) {


        /*Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);*/
        /*if( mediaController == null ) {
            mediaController = new MediaController(this);
        }*/
    }
    /**
     * 사진 앱범을 열어서 사진을 선택하고 닫으면 이 메소드가 호출 된다.
     * 파일을 카카오스토리에 올리기 전에 썸네일 이미지로 imageView에 보여주게 한다.
     * http://blog.saltfactory.net/mobile/using-kakaostory-api.html
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = {MediaStore.Images.Media.DATA};

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            bitmap = BitmapFactory.decodeFile(picturePath);
            //imageView.setImageBitmap(bitmap);

        }
        //http://stackoverflow.com/questions/23994616/android-video-picker-from-gallery
        else if (requestCode == RESULT_LOAD_VIDEO && resultCode == RESULT_OK && null != data) {

            selectedVideoPath = getPath(data.getData());
            Toast.makeText(getApplicationContext(), selectedVideoPath, Toast.LENGTH_SHORT).show();
            try {
                if(selectedVideoPath == null) {
                    //Log.e("selected video path = null!","null!!");
                    finish();
                } else {
                    /**
                     * try to do something there
                     * selectedVideoPath is path to the selected video
                     */
                }
            }
            catch(Exception ex) {
                ex.printStackTrace();
            }

        }
        else {
            super.onBackPressed();
        }
    }
    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if(cursor!=null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        else return null;
    }
}
