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
import com.jinkun_innovation.pastureland.bean.QueryByYang;
import com.jinkun_innovation.pastureland.bean.RegisterBean;
import com.jinkun_innovation.pastureland.bean.SelectVariety;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.view.AmountView;
import com.jinkun_innovation.pastureland.ui.view.AmountViewAge;
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
import java.util.List;
import java.util.Random;

import cn.pedant.SweetAlert.SweetAlertDialog;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.jinkun_innovation.pastureland.R.id.ivTakePhoto;

/**
 * Created by Guan on 2018/3/20.
 */

public class RegisterActivity extends Activity {

    private static final String TAG1 = RegisterActivity.class.getSimpleName();
    private static final int IV_OPEN = 1001;

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

    String mImgUrl = "";
    String mLogin_success;
    Bitmap bmp;


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

    File mFile;
    private File mFile1;
    private List<QueryByYang.LivestockVarietyListBean> mLivestockVarietyList;

    int type2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String login_success = PrefUtils.getString(getApplicationContext(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(login_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        setContentView(R.layout.activity_register);
        mIvTakePhoto = (ImageView) findViewById(ivTakePhoto);

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

        //0001 头羊  0002 小羊  0003 大牲畜

        final Spinner spinner1 = (Spinner) findViewById(R.id.spinner1);

        if (mDeviceNO.startsWith("0003")) {
            //大牲畜
            spinner1.setSelection(1);

        }

        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] type = getResources().getStringArray(R.array.type);
                mType1 = type[pos];

                if (pos == 4) {
                    type2 = 7;
                } else if (pos == 5) {
                    type2 = 8;
                } else {
                    type2 = pos + 1;
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

                                    if (mVariety==null){

                                        ToastUtils.showShort("抱歉，后台没有数据");

                                    }else {

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

        AmountViewAge avAge = findViewById(R.id.avAge);

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

                Log.d(TAG1, "mWeightAm=" + mWeightAm + ",mAgeAm=" + mAgeAm);


                if (mWeightAm == 0 || mAgeAm == 0) {

                    ToastUtils.showShort("年龄和重量都不能为0");

                    return;

                } else {

                    if (mDeviceNO.startsWith("0003") && type == 1) {

                        ToastUtils.showShort("0003是大牲畜，品种不能选择羊");
                    } else if ((mDeviceNO.startsWith("0001") || mDeviceNO.startsWith("0002"))
                            && type != 1) {

                        ToastUtils.showShort("0001/0002是羊，品种不能选择其他品种");

                    } else {

                        Log.d(TAG1, "mImgUrl=" + mImgUrl);
                        OkGo.<String>post(Constants.SAVELIVESTOCK)
                                .tag(this)
                                .params("token", mLoginSuccess.getToken())
                                .params("username", mUsername)
                                .params("deviceNO", mDeviceNO)
                                .params("ranchID", mLoginSuccess.getRanchID())
                                .params("livestockType", type2)
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

                                            startActivity(new Intent(getApplicationContext(),
                                                    HomeActivity.class));

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


                    }

                }

            }

        });


    }


}
