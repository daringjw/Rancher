package com.jinkun_innovation.pastureland.ui;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.zxing.client.android.CaptureActivity2;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.base.BaseActivity;
import com.jinkun_innovation.pastureland.utilcode.AppManager;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.ActivityUtils;
import com.jinkun_innovation.pastureland.utilcode.util.AppUtils;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.tencent.bugly.beta.Beta;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {
    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 3;//权限请求
    private static final int SCAN_REQUEST_CODE = 100;

    private String imagePath;
    private File photoFile;
    private Uri imageUri;//原图保存地址
    private static final int REQUEST_CAPTURE = 2;  //拍照

    private int checkedItem = 0;
    private String scanMessage;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        checkRuntimePermissions();


        Button btnVersion = (Button) findViewById(R.id.btnVersion);
        boolean appDebug = AppUtils.isAppDebug();
        if (appDebug) {
            btnVersion.setText("测试版本 1.0." + AppUtils.getAppVersionCode());
        } else {
            btnVersion.setText("正式版本 1.0." + AppUtils.getAppVersionCode());
        }

        btnVersion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Beta.checkUpgrade();
            }
        });


        Button btnInterface = (Button) findViewById(R.id.btnInterface);
        btnInterface.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getApplicationContext(), NetInterfaceActivity.class));

            }
        });

    }

    @Override
    protected void initToolBar() {
        setTitle(getString(R.string.app_name));
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_main;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkRuntimePermissions() {
        List<String> permissions = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.CAMERA);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
        }
        if (permissions.size() > 0) {
            ActivityCompat.requestPermissions(MainActivity.this, permissions.toArray(new String[permissions.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
        } else {
//            startScanActivity();

        }

    }


    private void openCamera() {

        String rootDir = "/Pastureland/photo";
        FileUtils.createOrExistsDir(new File(Environment.getExternalStorageDirectory(), rootDir));
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);
        photoFile = new File(file, UUID.randomUUID().toString() + ".jpg");
        FileUtils.createOrExistsFile(photoFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(MainActivity.this,
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


    private void startScanActivity() {
        Intent intent = new Intent(MainActivity.this, CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        intent.putExtra("inputUnable", 0);
        intent.putExtra("lightUnable", 1);
        intent.putExtra("albumUnable", 1);
        intent.putExtra("cancleUnable", 1);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case SCAN_REQUEST_CODE:
                    String isbn = data.getStringExtra("CaptureIsbn");
                    if (!TextUtils.isEmpty(isbn)) {
                        LogUtils.e(isbn);
                        scanMessage = isbn;
                        Toast.makeText(this, "解析到的内容为" + isbn, Toast.LENGTH_LONG).show();
                        openCamera();
                    }
                    break;

                case REQUEST_CAPTURE://拍照
                    LogUtils.e(imageUri);
                    Intent intent = new Intent();
                    intent.putExtra(getString(R.string.checked_Item), checkedItem);
                    intent.putExtra(getString(R.string.img_Url), photoFile.getAbsolutePath());
                    intent.putExtra(getString(R.string.scan_Message), scanMessage);

                    switch (checkedItem) {
                        case 2:
                            intent.setClass(MainActivity.this, UploadCheckedActivity.class);
                            break;
                        case 3:
                        case 0:
                            intent.setClass(MainActivity.this, UpLoadActivity.class);
                            break;
                    }
                    startActivity(intent);
                    break;


            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_login_out, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_out:
                new MaterialDialog.Builder(MainActivity.this)
                        .content("是否注销当前账户？")
                        .positiveText("确定")
                        .negativeText("取消")
                        .onPositive(new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                SpUtil.saveLoginState(false);
                                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                                AppManager.getAppManager().finishActivity();

                            }
                        }).show();

                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS:
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                } else {
                    Toast.makeText(MainActivity.this, "请手动打开摄像头权限", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @OnClick({R.id.ibtn_birthday, R.id.ibtn_crop, R.id.ibtn_life, R.id.ibtn_private})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ibtn_birthday:
                checkedItem = 2;

                startScanActivity();

                break;
            case R.id.ibtn_crop:
                checkedItem = 3;
                startScanActivity();
                break;
            case R.id.ibtn_life:
                checkedItem = 0;
                openCamera();
                break;
            case R.id.ibtn_private:
                ActivityUtils.startActivity(PrivateActivity.class);
                break;


        }
    }
}
