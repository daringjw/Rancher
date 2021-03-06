package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.ImgUrlBean;
import com.jinkun_innovation.pastureland.bean.LiveStock;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.SelectVariety;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.view.AmountView;
import com.jinkun_innovation.pastureland.ui.view.AmountViewAge;
import com.jinkun_innovation.pastureland.utilcode.constant.TimeConstants;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ImageUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static okhttp3.MultipartBody.ALTERNATIVE;
import static vi.com.gdi.bgl.android.java.EnvDrawText.bmp;

/**
 * Created by Guan on 2018/3/16.
 */

public class PublishClaimActivity extends AppCompatActivity {

    private static final String TAG1 = PublishClaimActivity.class.getSimpleName();

    private static final int IV_OPEN = 1001;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    private String mType1;
    private String mVariety1;
    private String mWeight1;
    private String mAge1;

    int type;
    int variety;
    int weight;
    int age;


    private String mDeviceNo;


    private File photoFile;
    private Uri imageUri;//原图保存地址
    private static final int REQUEST_CAPTURE = 2;  //拍照
    private ImageView mIvTakePhoto;
    private String mImgUrl = "";
    private String mImgUrl2 = "";


    private String mIsbn;
    private int mLivestockType;
    private List<Integer> mVariety;
    private Integer mInteger = 0;
    private Spinner mSpinner2;
    private Spinner mSpinner1;


    private void cropImage(final String imgUrl) {
        String rootDir = "/Pastureland/crop";
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);

        if (FileUtils.createOrExistsDir(file)) {
            LogUtils.e(file.getAbsolutePath());
            Luban.with(this)

                    .load(FileUtils.getFileByPath(imgUrl))           // 传人要压缩的图片列表
                    .ignoreBy(100)                                  // 忽略不压缩图片的大小
                    .setTargetDir(file.getAbsolutePath())
                    .setCompressListener(new OnCompressListener() { //设置回调
                        @Override
                        public void onStart() {
                            // TODO 压缩开始前调用，可以在方法内启动 loading UI
                            LogUtils.e("onStart");
//                            mPbLoading.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onSuccess(File file) {
                            // TODO 压缩成功后调用，返回压缩后的图片文件
//                            mPhotoFile = file;
                            LogUtils.e("onSuccess");
                            LogUtils.e(file.getAbsolutePath());

                            mLogin_success = PrefUtils.getString(getApplicationContext(), "login_success", null);
                            Gson gson = new Gson();
                            mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
                            mUsername = PrefUtils.getString(getApplicationContext(), "username", null);

                            Log.d(TAG1, "token=" + mLoginSuccess.getToken());
                            Log.d(TAG1, "username=" + mUsername);
                            Log.d(TAG1, "uploadFile=" + file.getAbsolutePath());

                            final SweetAlertDialog pDialog = new SweetAlertDialog(PublishClaimActivity.this,
                                    SweetAlertDialog.PROGRESS_TYPE);
                            pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            pDialog.setTitleText("图片上传中...");
                            pDialog.setCancelable(true);
                            pDialog.show();


                            OkGo.<String>post(Constants.HEADIMGURL)
                                    .tag(this)
                                    .isMultipart(true)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername)
                                    .params("uploadFile", file)
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {


                                            String s = response.body().toString();
                                            Log.d(TAG1, s);
                                            Gson gson = new Gson();
                                            ImgUrlBean imgUrlBean = gson.fromJson(s, ImgUrlBean.class);
                                            mImgUrl = imgUrlBean.getImgUrl();
                                            int j = mImgUrl.indexOf("j");
                                            mImgUrl = mImgUrl.substring(j - 1, mImgUrl.length());
                                            Log.d(TAG1, mImgUrl);
                                            mImgUrl2 = mImgUrl;



                                            if (pDialog != null) {
                                                pDialog.cancel();
                                            }


                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);

                                            if (pDialog != null) {
                                                pDialog.cancel();
                                            }

                                            new SweetAlertDialog(PublishClaimActivity.this,
                                                    SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("抱歉...")
                                                    .setContentText("网络不稳定,上传图片失败,请重新拍摄")
                                                    .show();


                                        }
                                    });


//                            Glide.with(UpLoadActivity.this).load(file).into(mImgUpload);
//                            FileUtils.deleteFile(imgUrl);
//                            mPbLoading.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError(Throwable e) {

                            // TODO 当压缩过程出现问题时调用
                            LogUtils.e(e.getMessage());
                            ToastUtils.showShort("压缩出现问题，请重新拍摄");

//                            AppManager.getAppManager().finishActivity();
//                            mPbLoading.setVisibility(View.GONE);
                        }
                    }).launch();
        }


    }

    private void openCamera() {

        String rootDir = "/Pastureland/photo";
        FileUtils.createOrExistsDir(new File(Environment.getExternalStorageDirectory(), rootDir));
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);
        photoFile = new File(file, TimeUtils.getNowString() + ".jpg");

        Log.d(TAG1, photoFile.getAbsolutePath());
        FileUtils.createOrExistsFile(photoFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(this,
                    "com.jinkun_innovation.pastureland.fileProvider",
                    photoFile);//通过FileProvider创建一个content类型的Uri
        } else {
            imageUri = Uri.fromFile(photoFile);
        }

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);


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

                case IV_OPEN:


                    String pic = PrefUtils.getString(getApplicationContext(), "pic", null);

                    pic = Constants.BASE_URL + pic;

                    OkGo.<File>get(pic)
                            .tag(this)
                            .execute(new FileCallback() {
                                @Override
                                public void onSuccess(Response<File> response) {

                                    File file = response.body().getAbsoluteFile();
                                    Bitmap bitmap = ImageUtils.getBitmap(file);
                                    mIvTakePhoto.setImageBitmap(bitmap);

                                }
                            });


                    break;


                case REQUEST_CAPTURE:

                    cropImage(photoFile.getAbsolutePath());

                    mIvTakePhoto.setImageURI(imageUri);

                    PrefUtils.setString(getApplicationContext(), "img_route",
                            photoFile.getAbsolutePath());

                    break;

            }


        }
    }


    public class MyAdapter extends BaseAdapter {

        private List<Integer> mList;
        private Context mContext;

        public MyAdapter(Context context, List<Integer> pList) {
            this.mContext = context;
            this.mList = pList;
        }


        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public Integer getItem(int position) {
            return mList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        /**
         * 下面是重要代码
         */
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater _LayoutInflater = LayoutInflater.from(mContext);
            convertView = _LayoutInflater.inflate(R.layout.item_variety, null);

            if (convertView != null) {

                TextView tvVariety = convertView.findViewById(R.id.tvVariety);

                mInteger = mList.get(position);
                variety4 = mInteger;
                Log.d(TAG1, "mInteger=" + mInteger);

                switch (mInteger) {

                    case 100:
                        tvVariety.setText("乌珠穆沁黑头羊");
                        break;

                    case 101:
                        tvVariety.setText("山羊");
                        break;

                    case 201:
                        tvVariety.setText("西门塔尔牛");
                        break;

                    case 301:
                        tvVariety.setText("蒙古马");
                        break;

                    case 401:
                        tvVariety.setText("草原黑毛猪");
                        break;

                    case 701:

                        tvVariety.setText("骆驼");
                        break;

                    case 801:

                        tvVariety.setText("驴");
                        break;

                }


            }
            return convertView;
        }
    }

    private int mWeightAm;
    private int mAgeAm;
    String pic;

    File mFile;
    private File mFile1;


    int type2;
    int type4;
    int variety3 = 100;
    int variety4 = 100;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_publish_claim);

        Intent intent = getIntent();
        mIsbn = intent.getStringExtra("isbn");


        TextView etDeviceNo = (TextView) findViewById(R.id.etDeviceNo);
        etDeviceNo.setText(mIsbn);
        mDeviceNo = etDeviceNo.getText().toString();

        mLogin_success = PrefUtils.getString(this, "login_success", null);
        final Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);


        mIvTakePhoto = (ImageView) findViewById(R.id.ivTakePhoto);
        mIvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCamera();

            }
        });


        final Resources res = getResources();

        mFile = new File(Environment.getExternalStorageDirectory(), "/Pastureland/pic");

        ImageView ivOpen = (ImageView) findViewById(R.id.ivOpen);
        ivOpen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Random random = new Random();
                int i = random.nextInt(6);
                Log.d(TAG1, "随机数" + i);

                switch (i) {

                    case 0:

                        mIvTakePhoto.setImageResource(R.mipmap.yang_0);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_0);
                        String yang_0 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_0");
                        mFile1 = null;
                        mFile1 = new File(yang_0);


                        break;
                    case 1:

                        mIvTakePhoto.setImageResource(R.mipmap.yang_1);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_1);
                        String yang_1 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_1");
                        mFile1 = null;
                        mFile1 = new File(yang_1);

                        break;

                    case 2:

                        mIvTakePhoto.setImageResource(R.mipmap.yang_2);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_2);
                        String yang_2 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_2");
                        mFile1 = null;
                        mFile1 = new File(yang_2);
                        break;

                    case 3:
                        mIvTakePhoto.setImageResource(R.mipmap.yang_3);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_3);
                        String yang_3 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_3");
                        mFile1 = null;
                        mFile1 = new File(yang_3);
                        break;

                    case 4:
                        mIvTakePhoto.setImageResource(R.mipmap.yang_4);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_4);
                        String yang_4 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_4");
                        mFile1 = null;
                        mFile1 = new File(yang_4);
                        break;

                    case 5:
                        mIvTakePhoto.setImageResource(R.mipmap.yang_5);
                        bmp = null;
                        bmp = BitmapFactory.decodeResource(res, R.mipmap.yang_5);
                        String yang_5 = com.jinkun_innovation.pastureland.utils.ImageUtils.savePhoto(bmp, mFile.getAbsolutePath(), "yang_5");
                        mFile1 = null;
                        mFile1 = new File(yang_5);
                        break;


                }


            }
        });


        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                setResult(RESULT_OK);
                finish();

            }
        });


        /*OkGo.<String>get(Constants.LIVESTOCK)
                .tag(this)
                .params("token",)
                .params("username",)
                .params("ranchID",)
                .params("",)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                    }
                });*/


        mSpinner1 = (Spinner) findViewById(R.id.spinner1);

        mSpinner2 = (Spinner) findViewById(R.id.spinner2);


        if (mIsbn.startsWith("0003")) {
            //大牲畜
            mSpinner1.setSelection(1);

        }


        mSpinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] type = getResources().getStringArray(R.array.type);
//                Log.d(TAG1, "种类" + type[pos]);
//                mType1 = type[mLivestockType - 1];
                mType1 = type[pos];

                if (pos == 4) {
                    type2 = 7;
                    type4 = 7;

                } else if (pos == 5) {
                    type2 = 8;
                    type4 = 8;

                } else {
                    type2 = pos + 1;
                    type4 = pos + 1;

                }

                //根据type1 访问接口
                OkGo.<String>get(Constants.SELECTVARIETY)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("livestockType", type2)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                String s = response.body().toString();
                                Gson gson1 = new Gson();
                                SelectVariety selectVariety = gson1.fromJson(s, SelectVariety.class);
                                String msg = selectVariety.getMsg();
                                if (msg.contains("获取品种成功")) {

                                    List<Integer> mVariety = selectVariety.getVariety();


                                    for (int i = 0; i < mVariety.size(); i++) {

                                        Log.d(TAG1, mVariety.get(i) + "");


                                    }


                                    if (mVariety != null) {
                                        //  建立Adapter绑定数据源
                                        MyAdapter _MyAdapter = new MyAdapter
                                                (getApplicationContext(), mVariety);

                                        //绑定Adapter
                                        mSpinner2.setAdapter(_MyAdapter);
                                    }


                                }


                            }
                        });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback

            }

        });


        final AmountView avWeight = findViewById(R.id.avWeight);
        avWeight.setGoods_storage(10000);
        mWeightAm = 10;
        avWeight.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                /*Toast.makeText(getApplicationContext(), "Amount=>  " +
                        amount, Toast.LENGTH_SHORT).show();*/
                //重量
                mWeightAm = amount;


            }

        });

        final AmountViewAge avAge = findViewById(R.id.avAge);
        avAge.setGoods_storage(10000);
        mAgeAm = 1;
        avAge.setOnAmountChangeListener(new AmountViewAge.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                /*Toast.makeText(getApplicationContext(), "Amount=>  " +
                        amount, Toast.LENGTH_SHORT).show();*/
                //年龄
                mAgeAm = amount;


            }

        });


        //查看发布情况selectLivestock.do
        OkGo.<String>get(Constants.LIVESTOCK)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("deviceNO", mIsbn)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String result = response.body().toString();
                        Log.d(TAG1, "result=" + result);
                        Gson gson1 = new Gson();
                        LiveStock selectLivestock = gson1.fromJson(result, LiveStock.class);
                        String msg = selectLivestock.getMsg();

                        Log.d(TAG1, "msg=" + msg);
                        if (TextUtils.isEmpty(msg)) {

                            return;

                        } else {

                            if (msg.contains("成功")) {

                                mSpinner1.setVisibility(View.GONE);
                                mSpinner2.setVisibility(View.GONE);
                                avWeight.setVisibility(View.GONE);
                                avAge.setVisibility(View.GONE);

                                TextView tvType = findViewById(R.id.tvType);
                                TextView tvVariety = findViewById(R.id.tvVariety);
                                tvType.setVisibility(View.VISIBLE);
                                tvVariety.setVisibility(View.VISIBLE);

                                TextView tvWeight = findViewById(R.id.tvWeight);
                                TextView tvAge = findViewById(R.id.tvAge);
                                tvWeight.setVisibility(View.VISIBLE);
                                tvAge.setVisibility(View.VISIBLE);

                                tvWeight.setText(selectLivestock.getLivestock().getWeight());

                                String createTime = selectLivestock.getLivestock().getCreateTime();
                                long timeSpanByNow = TimeUtils.getTimeSpanByNow(createTime, TimeConstants.DAY) + 2;
                                Log.d(TAG1, timeSpanByNow + "天=timeSpanByNow");
                                int age = (int) timeSpanByNow / 30;
                                Log.d(TAG1, age + "个月");
                                tvAge.setText(age + "");


                                String imgUrl = selectLivestock.getLivestock().getImgUrl();
                                mImgUrl = imgUrl;
                                imgUrl = Constants.BASE_URL + imgUrl;
                                OkGo.<File>get(imgUrl)
                                        .tag(this)
                                        .execute(new FileCallback() {
                                            @Override
                                            public void onSuccess(Response<File> response) {

                                                String path = response.body().getAbsolutePath();
                                                mIvTakePhoto.setImageURI(Uri.parse(path));

                                            }
                                        });


                                String variety = selectLivestock.getLivestock().getVariety();
                                if (variety.equals("100")) {

                                    tvType.setText("羊");
                                    tvVariety.setText("乌珠穆沁黑头羊");
                                    type2 = 1;
                                    variety3 = 100;

                                } else if (variety.equals("101")) {

                                    tvType.setText("羊");
                                    tvVariety.setText("山羊");
                                    type2 = 1;
                                    variety3 = 101;

                                } else if (variety.equals("201")) {

                                    tvType.setText("牛");
                                    tvVariety.setText("西门塔尔牛");
                                    type2 = 2;
                                    variety3 = 201;

                                } else if (variety.equals("301")) {

                                    tvType.setText("马");
                                    tvVariety.setText("蒙古马");
                                    type2 = 3;
                                    variety3 = 301;

                                } else if (variety.equals("401")) {

                                    tvType.setText("猪");
                                    tvVariety.setText("草原黑毛猪");

                                    type2 = 4;
                                    variety3 = 401;


                                } else if (variety.equals("701")) {

                                    tvType.setText("骆驼");
                                    tvVariety.setText("骆驼");

                                    type2 = 7;
                                    variety3 = 701;


                                } else if (variety.equals("801")) {

                                    tvType.setText("驴");
                                    tvVariety.setText("驴");

                                    type2 = 8;
                                    variety3 = 801;


                                }


                            }
                        }


                    }
                });


        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Log.d(TAG1, "mImgUrl1==" + mImgUrl);
                Log.d(TAG1, "mWeightAm=" + mWeightAm + ",mAgeAm=" + mAgeAm);
//

                //查看发布情况selectLivestock.do
                OkGo.<String>get(Constants.LIVESTOCK)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("deviceNO", mIsbn)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                String result = response.body().toString();
                                Log.d(TAG1, "result=" + result);
                                Gson gson1 = new Gson();
                                LiveStock selectLivestock = gson1.fromJson(result, LiveStock.class);
                                String msg = selectLivestock.getMsg();

//                                mWeightAm   mAgeAm ;


                                Log.d(TAG1, "msg=" + msg);
                                if (TextUtils.isEmpty(msg)) {

                                    if (type2 == 1) {
                                        variety3 = mInteger;
                                    } else if (type2 == 2) {
                                        variety3 = 201;

                                    } else if (type2 == 3) {
                                        variety3 = 301;
                                    } else if (type2 == 4) {
                                        variety3 = 401;
                                    } else if (type2 == 7) {
                                        variety3 = 701;
                                    } else if (type2 == 8) {
                                        variety3 = 801;
                                    }

                                } else {

                                    if (msg.contains("成功")) {

                                        mSpinner1.setVisibility(View.GONE);
                                        mSpinner2.setVisibility(View.GONE);

                                        TextView tvType = findViewById(R.id.tvType);
                                        TextView tvVariety = findViewById(R.id.tvVariety);
                                        tvType.setVisibility(View.VISIBLE);
                                        tvVariety.setVisibility(View.VISIBLE);

                                        String imgUrl = selectLivestock.getLivestock().getImgUrl();
                                        mImgUrl = imgUrl;
                                        imgUrl = Constants.BASE_URL + imgUrl;
                                        OkGo.<File>get(imgUrl)
                                                .tag(this)
                                                .execute(new FileCallback() {
                                                    @Override
                                                    public void onSuccess(Response<File> response) {

                                                        String path = response.body().getAbsolutePath();
                                                        mIvTakePhoto.setImageURI(Uri.parse(path));

                                                    }
                                                });

                                        String weight = selectLivestock.getLivestock().getWeight();
                                        weight = weight.substring(0, 1);
                                        mWeightAm = Integer.parseInt(weight);

                                        String createTime = selectLivestock.getLivestock().getCreateTime();
                                        long timeSpanByNow = TimeUtils.getTimeSpanByNow(createTime, TimeConstants.DAY) + 2;
                                        Log.d(TAG1, timeSpanByNow + "天=timeSpanByNow");
                                        int age = (int) timeSpanByNow / 30;

                                        mAgeAm = age;

                                        Log.e(TAG1, "mWeightAm1=" + mWeightAm + "mAgeAm1=" + mAgeAm);

                                        String variety = selectLivestock.getLivestock().getVariety();
                                        if (variety.equals("100")) {

                                            tvType.setText("羊");
                                            tvVariety.setText("乌珠穆沁黑头羊");
                                            type2 = 1;
                                            variety3 = 100;

                                        } else if (variety.equals("101")) {

                                            tvType.setText("羊");
                                            tvVariety.setText("山羊");
                                            type2 = 1;
                                            variety3 = 101;

                                        } else if (variety.equals("201")) {

                                            tvType.setText("牛");
                                            tvVariety.setText("西门塔尔牛");
                                            type2 = 2;
                                            variety3 = 201;

                                        } else if (variety.equals("301")) {

                                            tvType.setText("马");
                                            tvVariety.setText("蒙古马");
                                            type2 = 3;
                                            variety3 = 301;

                                        } else if (variety.equals("401")) {

                                            tvType.setText("猪");
                                            tvVariety.setText("草原黑毛猪");

                                            type2 = 4;
                                            variety3 = 401;


                                        } else if (variety.equals("701")) {

                                            tvType.setText("骆驼");
                                            tvVariety.setText("骆驼");

                                            type2 = 7;
                                            variety3 = 701;


                                        } else if (variety.equals("801")) {

                                            tvType.setText("驴");
                                            tvVariety.setText("驴");

                                            type2 = 8;
                                            variety3 = 801;


                                        }


                                    }
                                }


                            }
                        });


                if (mWeightAm == 0 || mAgeAm == 0) {

                    ToastUtils.showShort("年龄和重量都不能为0");
                    return;

                } else {

                    if (mIsbn.startsWith("0003") && type2 == 1) {

                        ToastUtils.showShort("0003是大牲畜，品种不能选择羊");

                    } else if ((mIsbn.startsWith("0001") || mIsbn.startsWith("0002"))
                            && type2 != 1) {

                        ToastUtils.showShort("0001/0002是羊，品种不能选择其他品种");

                    } else if (TextUtils.isEmpty(mImgUrl)) {

                        ToastUtils.showShort("亲，请先拍照");

                    } else {


                        OkGo.<String>post(Constants.RELEASE)
                                .tag(this)
                                .params("token", mLoginSuccess.getToken())
                                .params("username", mUsername)
                                .params("deviceNO", mIsbn)
                                .params("ranchID", mLoginSuccess.getRanchID())
                                .params("livestockType", type2)
                                .params("variety", variety3)
                                .params("weight", mWeightAm)
                                .params("age", mAgeAm)
                                .params("imgUrl", mImgUrl)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(Response<String> response) {

                                        Log.d(TAG1, "mImgUrl2==" + mImgUrl);

                                        final String s = response.body().toString();

                                        if (s.contains("发布牲畜到认领表成功")) {

                                            //发布认领成功
                                            Toast.makeText(getApplicationContext(), "发布牲畜到认领表成功",
                                                    Toast.LENGTH_SHORT).show();

                                            setResult(RESULT_OK);
                                            finish();


                                        } else if (s.contains("发布认领牲畜异常")) {

                                            ToastUtils.showShort("发布认领牲畜异常,请拍照");

                                        } else if (s.contains("亲，同一品种第一次发布认领需要拍照")) {

                                            ToastUtils.showShort("亲，同一品种第一次发布认领需要拍照");

                                        } else if (s.contains("接收信息有空值")) {

                                            ToastUtils.showShort("请重新拍照");

                                        } else if (s.contains("已经发布过了")) {


                                            OkGo.<String>get(Constants.LIVESTOCK)
                                                    .tag(this)
                                                    .params("token", mLoginSuccess.getToken())
                                                    .params("username", mUsername)
                                                    .params("deviceNO", mIsbn)
                                                    .params("ranchID", mLoginSuccess.getRanchID())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String> response) {

                                                            String result = response.body().toString();
                                                            Log.d(TAG1, "result=" + result);
                                                            Gson gson1 = new Gson();
                                                            LiveStock selectLivestock = gson1.fromJson(result, LiveStock.class);
                                                            String msg = selectLivestock.getMsg();

//                                mWeightAm   mAgeAm ;


                                                            OkGo.<String>post(Constants.IS_CLAIMED)
                                                                    .tag(this)
                                                                    .params("token", mLoginSuccess.getToken())
                                                                    .params("username", mUsername)
                                                                    .params("deviceNO", mIsbn)
                                                                    .params("ranchID", mLoginSuccess.getRanchID())
                                                                    .params("livestockType", type2)
                                                                    .params("variety", variety3)
                                                                    .params("weight", mWeightAm)
                                                                    .params("age", mAgeAm)
                                                                    .params("imgUrl", mImgUrl2)
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onSuccess(Response<String> response) {

                                                                            String s1 = response.body().toString();
                                                                            Log.d(TAG1, "s1=" + s1);

                                                                            if (s1.contains("已被认领不可重新发布")) {

                                                                                new SweetAlertDialog(PublishClaimActivity.this,
                                                                                        SweetAlertDialog.WARNING_TYPE)
                                                                                        .setTitleText("已经被认领")
                                                                                        .setContentText("已被认领不可重新发布")
                                                                                        .setConfirmText("确定")
                                                                                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                                                            @Override
                                                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                                                ToastUtils.showShort("已被认领不可重新发布");
                                                                                                setResult(RESULT_OK);
                                                                                                finish();
                                                                                            }
                                                                                        })

                                                                                        .show();


                                                                            } else if (s1.contains("接收信息有空值")) {

                                                                                ToastUtils.showShort("请重新拍照");

                                                                            } else if (s1.contains("重新发布认领表成功")) {

                                                                                ToastUtils.showShort("重新发布认领成功");
                                                                                setResult(RESULT_OK);
                                                                                finish();

                                                                            }

                                                                        }
                                                                    });


                                                            Log.d(TAG1, "msg=" + msg);
                                                            if (TextUtils.isEmpty(msg)) {

                                                                if (type2 == 1) {
                                                                    variety3 = mInteger;
                                                                } else if (type2 == 2) {
                                                                    variety3 = 201;

                                                                } else if (type2 == 3) {
                                                                    variety3 = 301;
                                                                } else if (type2 == 4) {
                                                                    variety3 = 401;
                                                                } else if (type2 == 7) {
                                                                    variety3 = 701;
                                                                } else if (type2 == 8) {
                                                                    variety3 = 801;
                                                                }

                                                            } else {

                                                                if (msg.contains("成功")) {

                                                                    String weight = selectLivestock.getLivestock().getWeight();
                                                                    weight = weight.substring(0, 1);
                                                                    mWeightAm = Integer.parseInt(weight);

                                                                    String createTime = selectLivestock.getLivestock().getCreateTime();
                                                                    long timeSpanByNow = TimeUtils.getTimeSpanByNow(createTime, TimeConstants.DAY) + 2;
                                                                    Log.d(TAG1, timeSpanByNow + "天=timeSpanByNow");
                                                                    int age = (int) timeSpanByNow / 30;

                                                                    mAgeAm = age;

                                                                    Log.e(TAG1, "mWeightAm1=" + mWeightAm + "，mAgeAm1=" + mAgeAm);


                                                                    mSpinner1.setVisibility(View.GONE);
                                                                    mSpinner2.setVisibility(View.GONE);

                                                                    TextView tvType = findViewById(R.id.tvType);
                                                                    TextView tvVariety = findViewById(R.id.tvVariety);
                                                                    tvType.setVisibility(View.VISIBLE);
                                                                    tvVariety.setVisibility(View.VISIBLE);

                                                                    String imgUrl = selectLivestock.getLivestock().getImgUrl();
                                                                    mImgUrl = imgUrl;
                                                                    imgUrl = Constants.BASE_URL + imgUrl;
                                                                    OkGo.<File>get(imgUrl)
                                                                            .tag(this)
                                                                            .execute(new FileCallback() {
                                                                                @Override
                                                                                public void onSuccess(Response<File> response) {

                                                                                    String path = response.body().getAbsolutePath();
                                                                                    mIvTakePhoto.setImageURI(Uri.parse(path));

                                                                                }
                                                                            });


                                                                    String variety = selectLivestock.getLivestock().getVariety();
                                                                    if (variety.equals("100")) {

                                                                        tvType.setText("羊");
                                                                        tvVariety.setText("乌珠穆沁黑头羊");
                                                                        type2 = 1;
                                                                        variety3 = 100;

                                                                    } else if (variety.equals("101")) {

                                                                        tvType.setText("羊");
                                                                        tvVariety.setText("山羊");
                                                                        type2 = 1;
                                                                        variety3 = 101;

                                                                    } else if (variety.equals("201")) {

                                                                        tvType.setText("牛");
                                                                        tvVariety.setText("西门塔尔牛");
                                                                        type2 = 2;
                                                                        variety3 = 201;

                                                                    } else if (variety.equals("301")) {

                                                                        tvType.setText("马");
                                                                        tvVariety.setText("蒙古马");
                                                                        type2 = 3;
                                                                        variety3 = 301;

                                                                    } else if (variety.equals("401")) {

                                                                        tvType.setText("猪");
                                                                        tvVariety.setText("草原黑毛猪");

                                                                        type2 = 4;
                                                                        variety3 = 401;


                                                                    } else if (variety.equals("701")) {

                                                                        tvType.setText("骆驼");
                                                                        tvVariety.setText("骆驼");

                                                                        type2 = 7;
                                                                        variety3 = 701;


                                                                    } else if (variety.equals("801")) {

                                                                        tvType.setText("驴");
                                                                        tvVariety.setText("驴");

                                                                        type2 = 8;
                                                                        variety3 = 801;


                                                                    }


                                                                }
                                                            }


                                                        }
                                                    });


                                        } else if (s.contains("牲畜信息为空或者没有这个品种,发布不成功")) {

                                            if (mIsbn.startsWith("0003") && type == 1) {

//                                                ToastUtils.showShort("0003是大牲畜，品种不能选择羊");
//                                                type2 = type + 1;

                                            }

                                            new SweetAlertDialog(PublishClaimActivity.this, SweetAlertDialog.WARNING_TYPE)
                                                    .setTitleText("未登记牲畜,是否直接发布认领?")
                                                    .setContentText("按确定直接发布认领")
                                                    .setCancelText("否")
                                                    .setConfirmText("确定")
                                                    .showCancelButton(true)
                                                    .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sDialog) {
                                                            sDialog.cancel();
                                                        }
                                                    })
                                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                        @Override
                                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                            sweetAlertDialog.cancel();


                                                            Log.d(TAG1, "type4=" + type4 + ",variety4=" + variety4);
                                                            Log.d(TAG1, "type2=" + type2 + ",variety3=" + variety3);


                                                            OkGo.<String>post(Constants.RELEASE)
                                                                    .tag(this)
                                                                    .params("token", mLoginSuccess.getToken())
                                                                    .params("username", mUsername)
                                                                    .params("deviceNO", mIsbn)
                                                                    .params("ranchID", mLoginSuccess.getRanchID())
                                                                    .params("livestockType", type2)
                                                                    .params("variety", variety3)
                                                                    .params("weight", mWeightAm)
                                                                    .params("age", mAgeAm)
                                                                    .params("imgUrl", mImgUrl)
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onSuccess(Response<String> response) {

                                                                            String s1 = response.body().toString();
                                                                            Log.d(TAG1, "s1=" + s1);
                                                                            ToastUtils.showShort("发布成功");
                                                                            setResult(RESULT_OK);
                                                                            finish();

                                                                        }
                                                                    });

                                                        }
                                                    })
                                                    .show();


                                        } else {


                                            ToastUtils.showShort("图片库无数据，请拍照");
                                        }

                                    }

                                    @Override
                                    public void onError(Response<String> response) {
                                        super.onError(response);

                                        ToastUtils.showShort("没有网络，请检查网络");


                                    }

                                });


                    }
                }

            }
        });


    }


    private void multeUpload(String url, File file) {

        RequestBody multipartBody = new MultipartBody.Builder()
                .setType(ALTERNATIVE)//一样的效果
                .addFormDataPart("token", mLoginSuccess.getToken())
                .addFormDataPart("username", mUsername)
                .addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/*"), file))
                .build();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url)
//                .cacheControl(new CacheControl.Builder().noCache().noStore().build())
                .addHeader("User-Agent", "android")
                .header("Content-Type", "text/html; charset=utf-8;")
                .header("Cache-Control", "public, max-age=" + 0)
                .post(multipartBody)//传参数、文件或者混合，改一下就行请求体就行
                .build();

        client.newBuilder().connectTimeout(30, TimeUnit.SECONDS).readTimeout(30, TimeUnit.SECONDS);
        client.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.d("onFailure>>" + e.toString());
            }

            @Override
            public void onResponse(Call call, okhttp3.Response response) throws IOException {

                LogUtils.i("xxx", "请求返回结果1>>>" + response.body().toString() + ">>>" + response.toString());
                if (response.isSuccessful()) {

                    LogUtils.i("xxx", "请求返回结果2>>>" + response.body().string());

                }
            }
        });

    }
}
