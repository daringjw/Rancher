package com.jinkun_innovation.pastureland.ui;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
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
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.RegisterBean;
import com.jinkun_innovation.pastureland.bean.SelectVariety;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.view.AmountView;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.jinkun_innovation.pastureland.R.id.ivTakePhoto;

/**
 * Created by Guan on 2018/3/20.
 */

public class RegisterActivity extends Activity {

    private static final String TAG1 = RegisterActivity.class.getSimpleName();
    private LoginSuccess mLoginSuccess;
    private String mUsername;
    private String mDeviceNO;
//    private String mImgUrl;


    private String mType1;
    private String mWeight1;
    private String mAge1;

    int type;
    int weight;
    int age;


    private File photoFile;
    private Uri imageUri;//原图保存地址
    private static final int REQUEST_CAPTURE = 2;  //拍照
    private ImageView mIvTakePhoto;

    SweetAlertDialog mDialog;

    private int mWeightAm;
    private int mAgeAm;


    private void cropImage(final String imgUrl) {
        String rootDir = "/Pastureland/crop";
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);

        if (FileUtils.createOrExistsDir(file)) {
            LogUtils.e(file.getAbsolutePath());
            Luban.with(this)
                    .load(FileUtils.getFileByPath(imgUrl))                                   // 传人要压缩的图片列表
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

                            Log.d(TAG1, mLoginSuccess.getToken());
                            Log.d(TAG1, mUsername);
                            Log.d(TAG1, file.getAbsolutePath());


                            mDialog = new SweetAlertDialog(RegisterActivity.this,
                                    SweetAlertDialog.PROGRESS_TYPE);
                            mDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                            mDialog.setTitleText("图片正在上传...");
                            mDialog.setCancelable(true);
                            mDialog.show();

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

                                            if (mDialog != null) {
                                                mDialog.cancel();
                                            }


                                        }

                                        @Override
                                        public void onError(Response<String> response) {
                                            super.onError(response);

                                            if (mDialog != null) {
                                                mDialog.cancel();
                                            }

                                            new SweetAlertDialog(RegisterActivity.this,
                                                    SweetAlertDialog.ERROR_TYPE)
                                                    .setTitleText("抱歉...")
                                                    .setContentText("网络不稳定,上传图片失败,请重新拍摄")
                                                    .show();


                                        }
                                    });


                        }

                        @Override
                        public void onError(Throwable e) {
                            // TODO 当压缩过程出现问题时调用
                            LogUtils.e(e.getMessage());

                            mDialog.cancel();
                            ToastUtils.showShort("压缩出现问题，请重新拍摄");


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

    String mImgUrl;
    String mLogin_success;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case REQUEST_CAPTURE:

                    cropImage(photoFile.getAbsolutePath());

                    mIvTakePhoto.setImageURI(imageUri);

                    PrefUtils.setString(getApplicationContext(), "img_route",
                            photoFile.getAbsolutePath());

                    break;

            }


        }
    }

    private Integer mInteger = 0;

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


                switch (mInteger) {

                    case 100:
                        tvVariety.setText("乌珠木漆黑羊");
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


                }

            }
            return convertView;
        }
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);
        mIvTakePhoto = (ImageView) findViewById(ivTakePhoto);

        mIvTakePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                openCamera();

            }
        });


        Intent intent = getIntent();
//        mImgUrl = intent.getStringExtra("imgUrl");
        mDeviceNO = intent.getStringExtra("scanMessage");

        TextView tvDeviceNo = (TextView) findViewById(R.id.tvDeviceNo);
        tvDeviceNo.setText(mDeviceNO);

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {

                finish();
            }
        });





        Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] type = getResources().getStringArray(R.array.type);
                mType1 = type[pos];


                //根据type1 访问接口
                OkGo.<String>get(Constants.SELECTVARIETY)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername)
                        .params("livestockType", pos == 4 ? 7 : pos + 1)
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


                                    Spinner spinner2 = (Spinner) findViewById(R.id.spinner2);

                                    if (mVariety != null) {
                                        //  建立Adapter绑定数据源
                                        MyAdapter _MyAdapter = new MyAdapter
                                                (getApplicationContext(), mVariety);

                                        //绑定Adapter
                                        spinner2.setAdapter(_MyAdapter);
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


        AmountView avWeight = findViewById(R.id.avWeight);
        avWeight.setGoods_storage(10000);

        avWeight.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                /*Toast.makeText(getApplicationContext(), "Amount=>  " +
                        amount, Toast.LENGTH_SHORT).show();*/
                //重量
                mWeightAm = amount;


            }

        });

        AmountView avAge = findViewById(R.id.avAge);
        avAge.setGoods_storage(10000);

        avAge.setOnAmountChangeListener(new AmountView.OnAmountChangeListener() {
            @Override
            public void onAmountChange(View view, int amount) {

                /*Toast.makeText(getApplicationContext(), "Amount=>  " +
                        amount, Toast.LENGTH_SHORT).show();*/
                //年龄
                mAgeAm = amount;


            }

        });


        String login_success = PrefUtils.getString(getApplicationContext(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(login_success, LoginSuccess.class);

        mUsername = PrefUtils.getString(this, "username", null);

        if (mLoginSuccess != null) {

            Log.d(TAG1, mLoginSuccess.getToken());
            Log.d(TAG1, mUsername);
            Log.d(TAG1, mDeviceNO);
            Log.d(TAG1, mLoginSuccess.getRanchID() + "");


        }


        Button btnConfirm = (Button) findViewById(R.id.btnConfirm);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (mType1.equals("羊")) {

                    type = 1;
                } else if (mType1.equals("牛")) {

                    type = 2;
                } else if (mType1.equals("马")) {

                    type = 3;
                } else if (mType1.equals("猪")) {

                    type = 4;
                } else if (mType1.equals("鸡")) {

                    type = 5;
                } else if (mType1.equals("鹿")) {

                    type = 6;
                } else if (mType1.equals("骆驼")) {

                    type = 7;
                }

                if (!TextUtils.isEmpty(mImgUrl)) {

                    Log.d(TAG1, "mWeightAm=" + mWeightAm + ",mAgeAm=" + mAgeAm);

                    OkGo.<String>post(Constants.SAVELIVESTOCK)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("deviceNO", mDeviceNO)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .params("livestockType", type)
                            .params("variety", mInteger == 0 ? 100 : mInteger)
                            .params("weight", mWeightAm)
                            .params("age", mAgeAm)
                            .params("imgUrl", mImgUrl)
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {


                                    String result = response.body().toString();
                                    Log.d(TAG1, result);

                                    Gson gson1 = new Gson();
                                    RegisterBean registerBean = gson1.fromJson(result, RegisterBean.class);
                                    String msg = registerBean.getMsg();

                                    if (msg.contains("牲畜登记打疫苗成功")) {
                                        //成功
                                        Toast.makeText(getApplicationContext(),
                                                "登记成功",
                                                Toast.LENGTH_SHORT)
                                                .show();

                                        startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                                        finish();

                                    } else {
                                        //失败
                                        Toast.makeText(getApplicationContext(),
                                                msg,
                                                Toast.LENGTH_SHORT)
                                                .show();
                                    }

                                }

                                @Override
                                public void onError(Response<String> response) {
                                    super.onError(response);

                                    ToastUtils.showShort("没有网络，请检查网络");

                                }
                            });
                } else {


                    Toast.makeText(getApplicationContext(), "亲，请拍照",
                            Toast.LENGTH_SHORT).show();

                }


            }
        });

    }


}
