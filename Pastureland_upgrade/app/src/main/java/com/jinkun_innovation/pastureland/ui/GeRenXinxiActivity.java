package com.jinkun_innovation.pastureland.ui;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.jinkun_innovation.pastureland.BuildConfig;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.AdminInfo;
import com.jinkun_innovation.pastureland.bean.ImgUrlBean;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.activity.ClipImageActivity;
import com.jinkun_innovation.pastureland.ui.activity.ModifyNameActivity;
import com.jinkun_innovation.pastureland.ui.activity.ModifyPhoneActivity;
import com.jinkun_innovation.pastureland.ui.view.CircleImageView;
import com.jinkun_innovation.pastureland.utilcode.AppManager;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.AppUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ImageUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.FileUtil;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.bugly.beta.Beta;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static com.jinkun_innovation.pastureland.utilcode.AppManager.getAppManager;
import static com.jinkun_innovation.pastureland.utils.FileUtil.getRealFilePathFromUri;

/**
 * Created by Guan on 2018/3/16.
 */

public class GeRenXinxiActivity extends AppCompatActivity {

    private static final String TAG1 = GeRenXinxiActivity.class.getSimpleName();

    private SweetAlertDialog mPDialog;

    String mLogin_success;
    LoginSuccess mLoginSuccess;
    String mUsername;

    //请求相机
    private static final int REQUEST_CAPTURE = 100;
    //请求相册
    private static final int REQUEST_PICK = 101;
    //请求截图
    private static final int REQUEST_CROP_PHOTO = 102;
    //请求访问外部存储
    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 103;
    //请求写入外部存储
    private static final int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 104;
    //头像1
    private CircleImageView headImage1;
    //头像2
    private ImageView headImage2;
    //调用照相机返回图片文件
    private File tempFile;
    // 1: qq, 2: weixin
    private int type;

    TextView tvName, tvSex, tvPhone;
    private String mPeopleName;
    private String mSex;
    private String mHeadImgUrl;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(getApplicationContext(), HomeActivity.class));


    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_gerenxinxi);


        /**
         * 升级
         */
        Button btnVersion = (Button) findViewById(R.id.btnVersion);
        boolean appDebug = AppUtils.isAppDebug();
        if (appDebug) {
            btnVersion.setText("版本检测   （版本 1.0." + AppUtils.getAppVersionCode() + ")");
        } else {
            btnVersion.setText("版本检测   （正式版本 1.0." + AppUtils.getAppVersionCode() + ")");
        }

        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Beta.checkUpgrade();
            }
        });

        tvName = (TextView) findViewById(R.id.tvName);
        tvSex = (TextView) findViewById(R.id.tvSex);
        tvPhone = (TextView) findViewById(R.id.tvPhone);


        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), HomeActivity.class));
                finish();

            }
        });


        getAppManager().addActivity(this);

        headImage1 = (CircleImageView) findViewById(R.id.head_image1);


        mLogin_success = PrefUtils.getString(this, "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(this, "username", null);

        OkGo.<String>post(Constants.ADMINLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {

                        String s = response.body().toString();
                        Log.d(TAG1, s);

                        if (s.contains("获取个人信息异常")) {

                            ToastUtils.showShort("获取个人信息异常");

                        } else if (s.contains("获取个人信息成功")) {
                            Gson gson1 = new Gson();
                            AdminInfo adminInfo = gson1.fromJson(s, AdminInfo.class);

                            mSex = adminInfo.getAdminInfo().getSex();
                            if (mSex.contains("1")) {
                                tvSex.setText("男");
                            } else {
                                tvSex.setText("女");
                            }

                            mPeopleName = adminInfo.getAdminInfo().getPeopleName();
                            tvName.setText(mPeopleName);

                            String username = adminInfo.getAdminInfo().getUsername();
                            tvPhone.setText(username);


                            mHeadImgUrl = adminInfo.getAdminInfo().headImgUrl;
                            if (!TextUtils.isEmpty(mHeadImgUrl)) {
                                mHeadImgUrl = Constants.BASE_URL + mHeadImgUrl;
                                Log.d(TAG1, "headImgUrl=" + mHeadImgUrl);

                                PrefUtils.setString(getApplicationContext(), "touxiang", mHeadImgUrl);
                                OkGo.<File>get(mHeadImgUrl)
                                        .tag(this)
                                        .execute(new FileCallback() {
                                            @Override
                                            public void onSuccess(Response<File> response) {

                                                File file = response.body().getAbsoluteFile();
                                                Bitmap bitmap = ImageUtils.getBitmap(file);
                                                headImage1.setImageBitmap(bitmap);

                                            }
                                        });


                            }


                        }


                    }
                });


        Button btnExit = (Button) findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mPDialog = new SweetAlertDialog(GeRenXinxiActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                mPDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                mPDialog.setTitleText("正在退出...");
                mPDialog.setCancelable(false);
                mPDialog.show();

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        mPDialog.cancel();
                        SpUtil.saveLoginState(false);
                        startActivity(new Intent(getApplicationContext(), LoginActivity1.class));
                        AppManager.getAppManager().finishAllActivity();

                    }
                }, 2000);


            }
        });


        headImage1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //上传头像
                type = 1;
                uploadHeadImage();


            }
        });


        LinearLayout llName = (LinearLayout) findViewById(R.id.llName);

        LinearLayout llPhone = (LinearLayout) findViewById(R.id.llPhone);

        llName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(),
                        ModifyNameActivity.class);

                startActivityForResult(intent, 1001);

            }
        });

        LinearLayout llSex = (LinearLayout) findViewById(R.id.llSex);

        llSex.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new SweetAlertDialog(GeRenXinxiActivity.this)
                        .setTitleText("修改性别?")
                        .setContentText("你是女人还是男人?")
                        .setCancelText("女")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.cancel();

                                OkGo.<String>get(Constants.ADMINLIST)
                                        .tag(this)
                                        .params("token", mLoginSuccess.getToken())
                                        .params("username", mUsername)
                                        .params("ranchID", mLoginSuccess.getRanchID())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {

                                                Gson gson = new Gson();
                                                AdminInfo adminInfo = gson.fromJson(response.body().toString(), AdminInfo.class);

                                                OkGo.<String>get(Constants.UPDADMIN)
                                                        .tag(this)
                                                        .params("token", mLoginSuccess.getToken())
                                                        .params("username", mUsername)
                                                        .params("peopleName", adminInfo.getAdminInfo().getPeopleName())
                                                        .params("sex", 0)
                                                        .params("headImgUrl", adminInfo.getAdminInfo().headImgUrl)
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(Response<String> response) {

                                                                recreate();

                                                            }
                                                        });


                                            }
                                        });

                            }
                        })
                        .setConfirmText("男")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.cancel();

                                OkGo.<String>get(Constants.ADMINLIST)
                                        .tag(this)
                                        .params("token", mLoginSuccess.getToken())
                                        .params("username", mUsername)
                                        .params("ranchID", mLoginSuccess.getRanchID())
                                        .execute(new StringCallback() {
                                            @Override
                                            public void onSuccess(Response<String> response) {

                                                Gson gson = new Gson();
                                                AdminInfo adminInfo = gson.fromJson(response.body().toString(), AdminInfo.class);

                                                OkGo.<String>get(Constants.UPDADMIN)
                                                        .tag(this)
                                                        .params("token", mLoginSuccess.getToken())
                                                        .params("username", mUsername)
                                                        .params("peopleName", adminInfo.getAdminInfo().getPeopleName())
                                                        .params("sex", 1)
                                                        .params("headImgUrl", adminInfo.getAdminInfo().headImgUrl)
                                                        .execute(new StringCallback() {
                                                            @Override
                                                            public void onSuccess(Response<String> response) {

                                                                recreate();

                                                            }
                                                        });


                                            }
                                        });


                            }
                        })
                        .show();


            }
        });

        llPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), ModifyPhoneActivity.class);

                startActivityForResult(intent, 1003);

            }
        });


    }


    /**
     * 上传头像
     */
    private void uploadHeadImage() {
        View view = LayoutInflater.from(this).inflate(R.layout.layout_popupwindow, null);
        TextView btnCarema = (TextView) view.findViewById(R.id.btn_camera);
        TextView btnPhoto = (TextView) view.findViewById(R.id.btn_photo);
        TextView btnCancel = (TextView) view.findViewById(R.id.btn_cancel);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setBackgroundDrawable(getResources().getDrawable(android.R.color.transparent));
        popupWindow.setOutsideTouchable(true);
        View parent = LayoutInflater.from(this).inflate(R.layout.activity_main, null);
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        //popupWindow在弹窗的时候背景半透明
        final WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 0.5f;
        getWindow().setAttributes(params);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                params.alpha = 1.0f;
                getWindow().setAttributes(params);
            }
        });

        btnCarema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(GeRenXinxiActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请WRITE_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(GeRenXinxiActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                            WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到调用系统相机
                    gotoCamera();
                }
                popupWindow.dismiss();
            }
        });
        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //权限判断
                if (ContextCompat.checkSelfPermission(GeRenXinxiActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
                    //申请READ_EXTERNAL_STORAGE权限
                    ActivityCompat.requestPermissions(GeRenXinxiActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            READ_EXTERNAL_STORAGE_REQUEST_CODE);
                } else {
                    //跳转到相册
                    gotoPhoto();
                }
                popupWindow.dismiss();
            }
        });
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }


    /**
     * 外部存储权限申请返回
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == WRITE_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoCamera();
            }
        } else if (requestCode == READ_EXTERNAL_STORAGE_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission Granted
                gotoPhoto();
            }
        }
    }


    /**
     * 跳转到相册
     */
    private void gotoPhoto() {
        Log.d("evan", "*****************打开图库********************");
        //跳转到调用系统图库
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(intent, "请选择图片"), REQUEST_PICK);
    }


    /**
     * 跳转到照相机
     */
    private void gotoCamera() {
        Log.d("evan", "*****************打开相机********************");
        //创建拍照存储的图片文件
        tempFile = new File(FileUtil.checkDirPath(Environment.getExternalStorageDirectory().getPath() + "/image/"), System.currentTimeMillis() + ".jpg");

        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            //设置7.0中共享文件，分享路径定义在xml/file_paths.xml
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(GeRenXinxiActivity.this, BuildConfig.APPLICATION_ID + ".fileProvider", tempFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        }
        startActivityForResult(intent, REQUEST_CAPTURE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent) {
        switch (requestCode) {

            case 1003:
                //修改号码
                if (resultCode == RESULT_OK) {

                    final String phone = intent.getStringExtra("phone");

                    OkGo.<String>get(Constants.ADMINLIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    Gson gson = new Gson();
                                    AdminInfo adminInfo = gson.fromJson(response.body().toString(), AdminInfo.class);

                                    OkGo.<String>get(Constants.UPDADMIN)
                                            .tag(this)
                                            .params("token", mLoginSuccess.getToken())
                                            .params("username", phone)
                                            .params("peopleName", adminInfo.getAdminInfo().getPeopleName())
                                            .params("sex", adminInfo.getAdminInfo().getSex())
                                            .params("headImgUrl", adminInfo.getAdminInfo().headImgUrl)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {

                                                    recreate();

                                                }
                                            });

                                }
                            });


                }


                break;

            case 1001:
                //修改名字
                if (resultCode == RESULT_OK) {

                    final String name = intent.getStringExtra("name");

                    OkGo.<String>get(Constants.ADMINLIST)
                            .tag(this)
                            .params("token", mLoginSuccess.getToken())
                            .params("username", mUsername)
                            .params("ranchID", mLoginSuccess.getRanchID())
                            .execute(new StringCallback() {
                                @Override
                                public void onSuccess(Response<String> response) {

                                    Gson gson = new Gson();
                                    AdminInfo adminInfo = gson.fromJson(response.body().toString(), AdminInfo.class);

                                    OkGo.<String>get(Constants.UPDADMIN)
                                            .tag(this)
                                            .params("token", mLoginSuccess.getToken())
                                            .params("username", mUsername)
                                            .params("peopleName", name)
                                            .params("sex", adminInfo.getAdminInfo().getSex())
                                            .params("headImgUrl", adminInfo.getAdminInfo().headImgUrl)
                                            .execute(new StringCallback() {
                                                @Override
                                                public void onSuccess(Response<String> response) {

                                                    recreate();

                                                }
                                            });


                                }
                            });


                }

                break;

            case REQUEST_CAPTURE: //调用系统相机返回
                if (resultCode == RESULT_OK) {
                    gotoClipActivity(Uri.fromFile(tempFile));
                }
                break;
            case REQUEST_PICK:  //调用系统相册返回
                if (resultCode == RESULT_OK) {
                    Uri uri = intent.getData();
                    gotoClipActivity(uri);
                }
                break;
            case REQUEST_CROP_PHOTO:  //剪切图片返回
                if (resultCode == RESULT_OK) {
                    final Uri uri = intent.getData();
                    if (uri == null) {
                        return;
                    }
                    String cropImagePath = getRealFilePathFromUri(getApplicationContext(), uri);
                    Bitmap bitMap = BitmapFactory.decodeFile(cropImagePath);
                    if (type == 1) {
                        headImage1.setImageBitmap(bitMap);
                    } else {
                        headImage2.setImageBitmap(bitMap);
                    }
                    //此处后面可以将bitMap转为二进制上传后台网络
                    //......
                    File file = new File(cropImagePath);

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
                                    if (s.contains("上传文件成功")) {

                                        Gson gson = new Gson();
                                        ImgUrlBean imgUrlBean = gson.fromJson(s, ImgUrlBean.class);
                                        final String imgUrl = imgUrlBean.getImgUrl();
                                        Log.d(TAG1, "imgUrl=" + imgUrl);


                                        if (!TextUtils.isEmpty(imgUrl)) {

                                            OkGo.<String>get(Constants.ADMINLIST)
                                                    .tag(this)
                                                    .params("token", mLoginSuccess.getToken())
                                                    .params("username", mUsername)
                                                    .params("ranchID", mLoginSuccess.getRanchID())
                                                    .execute(new StringCallback() {
                                                        @Override
                                                        public void onSuccess(Response<String> response) {

                                                            Gson gson = new Gson();
                                                            AdminInfo adminInfo = gson.fromJson(response.body().toString(), AdminInfo.class);

                                                            OkGo.<String>get(Constants.UPDADMIN)
                                                                    .tag(this)
                                                                    .params("token", mLoginSuccess.getToken())
                                                                    .params("username", mUsername)
                                                                    .params("peopleName", adminInfo.getAdminInfo().getPeopleName())
                                                                    .params("sex", adminInfo.getAdminInfo().getSex())
                                                                    .params("headImgUrl", imgUrl)
                                                                    .execute(new StringCallback() {
                                                                        @Override
                                                                        public void onSuccess(Response<String> response) {

                                                                            new Handler().postDelayed(new Runnable() {
                                                                                @Override
                                                                                public void run() {
                                                                                    recreate();
                                                                                }
                                                                            }, 1000);

                                                                        }
                                                                    });


                                                        }
                                                    });


                                        } else {

                                            ToastUtils.showShort("图片上传失败");
                                        }


                                    } else {

                                        ToastUtils.showShort("fail to upload");
                                    }


                                }
                            });


                }

                break;
        }
    }


    /**
     * 打开截图界面
     */
    public void gotoClipActivity(Uri uri) {

        if (uri == null) {
            return;
        }
        Intent intent = new Intent();
        intent.setClass(this, ClipImageActivity.class);
        intent.putExtra("type", type);
        intent.setData(uri);
        startActivityForResult(intent, REQUEST_CROP_PHOTO);

    }


}
