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
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.sendBtn)
    Button sendBtn;
    @Bind(R.id.imageView)
    ImageView imageView;
    Bitmap bitmap;
    private static int RESULT_LOAD_IMAGE = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

    }
    @OnClick(R.id.sendBtn)
    public void sendBtnClick(View view) {
        Retrofit movieUploadRetrofit = new Retrofit.Builder().baseUrl("127.0.0.1:8089/MovieUploadServlet").build();


    }
    @OnClick(R.id.imageView)
    public void imageViewClick(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, RESULT_LOAD_IMAGE);
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
            imageView.setImageBitmap(bitmap);

        } else {
            super.onBackPressed();
        }
    }
}
