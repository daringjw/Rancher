package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.ui.activity.MuchangDetailActivity;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ImageUtils;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;

import java.io.File;

/**
 * Created by Guan on 2018/3/15.
 */

public class LianJiangPasturelandActivity extends AppCompatActivity {

    private static final String TAG = LianJiangPasturelandActivity.class.getSimpleName();

    private SliderLayout mSliderShow;
    private ImageView mIvTu;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_lianjiangpastureland);

        mSliderShow = (SliderLayout) findViewById(R.id.slider);

        File file = new File("/storage/emulated/0/Pastureland/photo/2018-03-16 09:39:07.jpg");
        mIvTu = (ImageView) findViewById(R.id.ivTu);

        //查看本地数据，有则显示，没有不显示
        String img_route = PrefUtils.getString(getApplicationContext(), "img_route", null);
        if (!TextUtils.isEmpty(img_route)) {
            File file1 = new File(img_route);
            Bitmap bitmap = ImageUtils.getBitmap(file1);
            mIvTu.setImageBitmap(bitmap);

        }

        /*mIvTu.setImageURI(FileProvider.getUriForFile(this,
                "com.jinkun_innovation.pastureland.fileProvider",
                file));

        Bitmap bitmap = ImageUtils.getBitmap(file);
        Bitmap bitmap1 = ImageUtils.compressByScale(bitmap, 200, 100);
        mIvTu.setImageBitmap(bitmap);*/


        TextSliderView textSliderView = new TextSliderView(this);
        textSliderView
                .description("智慧牧场")
                .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                .image("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1522338598325&di=d759c65f3c5f75db49522c9d3afdd701&imgtype=0&src=http%3A%2F%2Fpic2.ooopic.com%2F12%2F48%2F45%2F77bOOOPICe3.jpg")
        ;

        TextSliderView textSliderView1 = new TextSliderView(this);
        textSliderView1
                .description("金坤科创")
                .image("https://goss1.vcg.com/creative/vcg/800/version23/VCG41544521221.jpg");

        TextSliderView textSliderView2 = new TextSliderView(this);
        textSliderView2
                .description("科技点亮牧场")
                .image("https://goss3.vcg.com/creative/vcg/800/version23/VCG41544521229.jpg");


        mSliderShow.addSlider(textSliderView);
        mSliderShow.addSlider(textSliderView1);
        mSliderShow.addSlider(textSliderView2);

        mSliderShow.setPresetTransformer(SliderLayout.Transformer.RotateUp);


        Button btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCamera();

            }
        });


        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });


        Button btnDetail = (Button) findViewById(R.id.btnDetail);
        btnDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), MuchangDetailActivity.class));

            }
        });


    }

    @Override
    protected void onStop() {

        mSliderShow.stopAutoCycle();
        super.onStop();

    }


    private File photoFile;
    private Uri imageUri;//原图保存地址
    private static final int REQUEST_CAPTURE = 2;  //拍照

    private void openCamera() {

        String rootDir = "/Pastureland/photo";
        FileUtils.createOrExistsDir(new File(Environment.getExternalStorageDirectory(), rootDir));
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);
        photoFile = new File(file, TimeUtils.getNowString() + ".jpg");

        Log.d(TAG, photoFile.getAbsolutePath());
        FileUtils.createOrExistsFile(photoFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this,
                    "com.jinkun_innovation.pastureland.fileProvider",
                    photoFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(photoFile);
        }
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
        startActivityForResult(intent, REQUEST_CAPTURE);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CAPTURE:

                    mIvTu.setImageURI(imageUri);

                    PrefUtils.setString(getApplicationContext(), "img_route",
                            photoFile.getAbsolutePath());

                    break;

            }


        }
    }
}
