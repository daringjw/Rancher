package com.jinkun_innovation.pastureland.ui.fragment;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.Gson;
import com.google.zxing.client.android.CaptureActivity2;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.bean.IsBinded;
import com.jinkun_innovation.pastureland.bean.LoginSuccess;
import com.jinkun_innovation.pastureland.bean.QueryByYang;
import com.jinkun_innovation.pastureland.common.Constants;
import com.jinkun_innovation.pastureland.ui.GrassActivity;
import com.jinkun_innovation.pastureland.ui.RegisterActivity;
import com.jinkun_innovation.pastureland.ui.ToolsActivity;
import com.jinkun_innovation.pastureland.ui.UpLoadActivity;
import com.jinkun_innovation.pastureland.ui.activity.Muchang2Activity;
import com.jinkun_innovation.pastureland.utilcode.util.FileUtils;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.TimeUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.utils.PrefUtils;
import com.jinkun_innovation.pastureland.utils.StrLengthUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cn.pedant.SweetAlert.SweetAlertDialog;


/**
 * Created by Guan on 2018/3/15.
 */

public class ManagerFragment extends Fragment {


    private static final String TAG1 = ManagerFragment.class.getSimpleName();

    private static final int SCAN_REQUEST_CODE2 = 1002;

    @BindView(R.id.llRegister)
    LinearLayout llRegister;
    @BindView(R.id.llJianMao)
    LinearLayout llJianMao;
    @BindView(R.id.llLifePhoto)
    LinearLayout llLifePhoto;
    @BindView(R.id.llTools)
    LinearLayout llTools;
    @BindView(R.id.llGrass)
    LinearLayout llGrass;
    @BindView(R.id.llMyMuChang)
    LinearLayout llMyMuChang;


    Unbinder unbinder;

    private static final int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 3;//权限请求
    private static final int SCAN_REQUEST_CODE = 100;

    private static final int CAMERA_ACTIVITY = 6;  //视频

    private String imagePath;
    private File photoFile;
    private Uri imageUri;//原图保存地址
    private static final int REQUEST_CAPTURE = 2;  //拍照

    private int checkedItem = 0;
    private String scanMessage;

    private SliderLayout mSliderShow;
    private LoginSuccess mLoginSuccess;

    private String mLogin_success;
    private String mUsername;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = View.inflate(getActivity(), R.layout.fragment_manager, null);

        unbinder = ButterKnife.bind(this, view);


        mLogin_success = PrefUtils.getString(getActivity(), "login_success", null);
        Gson gson = new Gson();
        mLoginSuccess = gson.fromJson(mLogin_success, LoginSuccess.class);
        mUsername = PrefUtils.getString(getActivity(), "username", null);


        mSliderShow = view.findViewById(R.id.slider);
        TextSliderView textSliderView = new TextSliderView(getActivity());

        textSliderView
                .description("智慧牧场")
                .setScaleType(BaseSliderView.ScaleType.Fit)//图片缩放类型
                .image("http://pic.58pic.com/58pic/14/15/79/20U58PICrcu_1024.jpg");

        textSliderView.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {

                startActivity(new Intent(getActivity(), Muchang2Activity.class));

            }
        });


        TextSliderView textSliderView1 = new TextSliderView(getActivity());
        textSliderView1
                .description("科技点亮牧场")
                .image("http://img.pconline.com.cn/images/upload/upc/tx/photoblog/1311/13/c7/28612921_1384347978119.jpg");

        textSliderView1.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {

                startActivity(new Intent(getActivity(), Muchang2Activity.class));

            }
        });

        TextSliderView textSliderView2 = new TextSliderView(getActivity());
        textSliderView2
                .description("金坤技术")
                .image("http://pic2.ooopic.com/12/49/46/19bOOOPICb3_1024.jpg");

        textSliderView2.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
            @Override
            public void onSliderClick(BaseSliderView slider) {

                startActivity(new Intent(getActivity(), Muchang2Activity.class));

            }
        });


        mSliderShow.addSlider(textSliderView);
        mSliderShow.addSlider(textSliderView1);
        mSliderShow.addSlider(textSliderView2);

        mSliderShow.setPresetTransformer(SliderLayout.Transformer.Default);

        LinearLayout llMyMuChang = (LinearLayout) view.findViewById(R.id.llMyMuChang);
        llMyMuChang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                startActivity(new Intent(getActivity(), Muchang2Activity.class));

            }
        });


        //通过牲畜类型查询所有牲畜
        OkGo.<String>get(Constants.QUERYLIVESTOCKVARIETYLIST)
                .tag(this)
                .params("token", mLoginSuccess.getToken())
                .params("username", mUsername)
                .params("ranchID", mLoginSuccess.getRanchID())
                .params("livestockType", 1)
                .params("current", 1)
                .params("pagesize", 10)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {


                        String s = response.body().toString();


                        if (s.contains("imgUrl")) {
                            //有数据
                            Gson gson1 = new Gson();
                            QueryByYang queryByYang = gson1.fromJson(s, QueryByYang.class);
                            List<QueryByYang.LivestockVarietyListBean> mylist =
                                    queryByYang.getLivestockVarietyList();

                            String address = mylist.get(0).address;
                            PrefUtils.setString(getActivity(), "address", address);


                        } else {


                        }


                    }
                });

        return view;

    }


    @Override
    public void onDestroyView() {

        super.onDestroyView();
        unbinder.unbind();

    }

    @OnClick({R.id.llRegister, R.id.llJianMao, R.id.llLifePhoto,
            R.id.llTools, R.id.llGrass, R.id.llMyMuChang})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.llRegister:

                checkedItem = 2;
                startScanActivity();
                break;

            case R.id.llJianMao:
                checkedItem = 3;
                startScanActivity();
                break;
            case R.id.llLifePhoto:

                /*new SweetAlertDialog(getActivity())
                        .setTitleText("请选择拍摄内容")
                        .setContentText("请选择是要拍摄牲畜的个体照片，还是牧场的风景或者牧群的照片")
                        .setConfirmText("牧场牧群")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sDialog) {
                                sDialog.dismissWithAnimation();
                                //牧场牧群照
                                checkedItem = 0;
                                openCamera();
                            }
                        })
                        .setCancelText("牲畜个体")
                        .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {

                                sweetAlertDialog.dismissWithAnimation();
                                //牲畜个体
                                startScanActivity2();


                            }
                        })
                        .show();*/

                //牧场牧群照
                checkedItem = 0;
                openCamera();

                break;

            case R.id.llTools:

                startActivity(new Intent(getActivity(), ToolsActivity.class));

                break;

            case R.id.llGrass:

                startActivity(new Intent(getActivity(), GrassActivity.class));

                break;
            case R.id.llMyMuChang:

//                startActivity(new Intent(getActivity(), LianJiangPasturelandActivity.class));
                break;

        }
    }


    //  a为原字符串，b为要插入的字符串，t为插入位置
    public String Stringinsert(String a, String b, int t) {
        return a.substring(0, t) + b + a.substring(t, a.length());
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {

                case SCAN_REQUEST_CODE2:

                    final String isbn2 = data.getStringExtra("CaptureIsbn");

                    //牲畜个体
                    checkedItem = 101;


                    if (!TextUtils.isEmpty(isbn2)) {


                        Toast.makeText(getActivity(), "解析到的内容为" + isbn2, Toast.LENGTH_LONG).show();

                        if (StrLengthUtil.length(isbn2) == 16) {

                            scanMessage = isbn2;
                            OkGo.<String>post(Constants.ISDEVICEBINDED)
                                    .tag(this)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername) //用户手机号
                                    .params("deviceNO", isbn2)
                                    .params("ranchID", mLoginSuccess.getRanchID())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Log.d(TAG1, result);
                                            if (result.contains("true")) {

                                                //已绑定
                                                openCamera();


                                            } else {
                                                //未绑定
                                                new SweetAlertDialog(getActivity())
                                                        .setTitleText("未登记设备，不能拍牲畜个体照!")
                                                        .show();

                                            }


                                        }
                                    });


                        } else if (StrLengthUtil.length(isbn2) == 15) {

                            String str = Stringinsert(isbn2, "1", 7);
                            Log.d(TAG1, "15位isbn=" + str);
                            Log.d(TAG1, "新的长度" + StrLengthUtil.length(str));

                            scanMessage = str;

                            OkGo.<String>post(Constants.ISDEVICEBINDED)
                                    .tag(this)
                                    .params("token", mLoginSuccess.getToken())
                                    .params("username", mUsername) //用户手机号
                                    .params("deviceNO", scanMessage)
                                    .params("ranchID", mLoginSuccess.getRanchID())
                                    .execute(new StringCallback() {
                                        @Override
                                        public void onSuccess(Response<String> response) {

                                            String result = response.body().toString();
                                            Log.d(TAG1, result);
                                            if (result.contains("true")) {

                                                //已绑定
                                                openCamera();


                                            } else {
                                                //未绑定
                                                new SweetAlertDialog(getActivity())
                                                        .setTitleText("未登记设备，不能拍牲畜个体照!")
                                                        .show();

                                            }


                                        }
                                    });


                        } else {
                            ToastUtils.showShort("设备号必须是16位数字");
                        }


                    }


                    break;


                case SCAN_REQUEST_CODE:
                    String isbn = data.getStringExtra("CaptureIsbn");
                    if (!TextUtils.isEmpty(isbn)) {
                        LogUtils.e(isbn);

                        Toast.makeText(getActivity(), "解析到的内容为" + isbn, Toast.LENGTH_LONG).show();

                        if (StrLengthUtil.length(isbn) == 16) {
                            scanMessage = isbn;

                            register(isbn);

                        } else if (StrLengthUtil.length(isbn) == 15) {

                            String str = Stringinsert(isbn, "1", 7);
                            Log.d(TAG1, "15位isbn=" + str);
                            Log.d(TAG1, "新的长度" + StrLengthUtil.length(str));
                            scanMessage = str;
                            register(str);


                        } else {
                            ToastUtils.showShort("设备号必须是16位数字");
                        }


                    }


                    break;

                case CAMERA_ACTIVITY:

                    try {
                        AssetFileDescriptor videoAsset = getActivity().getContentResolver()
                                .openAssetFileDescriptor(data.getData(), "r");
                        FileInputStream fis = videoAsset.createInputStream();
                        File tmpFile = new File(Environment.getExternalStorageDirectory(),
                                TimeUtils.getNowString() + ".mp4");

                        FileOutputStream fos = new FileOutputStream(tmpFile);
                        byte[] buf = new byte[1024];
                        int len;
                        while ((len = fis.read(buf)) > 0) {
                            fos.write(buf, 0, len);
                        }
                        fis.close();
                        fos.close();

                        Log.d("ManagerFragment", tmpFile.getAbsolutePath());
                        PrefUtils.setString(getActivity(), "v1", tmpFile.getAbsolutePath());


                    } catch (IOException io_e) {
                        // TODO: handle error

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
                            intent.setClass(getActivity(), RegisterActivity.class);
                            break;
                        case 3:
                            //剪毛
                            intent.setClass(getActivity(), UpLoadActivity.class);

                        case 0:
                            //牧群牧场照片
                            intent.setClass(getActivity(), UpLoadActivity.class);
                            break;

                        case 101:
                            //牲畜个体照
                            intent.setClass(getActivity(), UpLoadActivity.class);

                            break;

                    }

                    startActivity(intent);
                    break;


            }
        }
    }

    private void register(final String isbn) {
        switch (checkedItem) {

            case 2:

                /*Intent intent = new Intent(getActivity()
                        , RegisterActivity.class);
                intent.putExtra(getString(R.string.scan_Message),
                        scanMessage);
                startActivity(intent);*/

                //接羔
                //判断设备是否被绑定
                OkGo.<String>post(Constants.ISDEVICEBINDED)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername) //用户手机号
                        .params("deviceNO", isbn)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                String result = response.body().toString();
                                Gson gson = new Gson();
                                IsBinded isBinded = gson.fromJson(result, IsBinded.class);
                                String code = isBinded.getCode();
                                boolean msg = isBinded.isMsg();
                                String msg1 = isBinded.getMsg1();
                                if (!msg && code.contains("error")) {
                                    //提示被其它牧场主绑定了，跳回主页面
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("被其它牧场主绑定了")
                                            .setContentText("无法登记")
                                            .show();


                                } else if (code.contains("true")) {
                                    //提示此牲畜已发布认领，不可重新发布
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("此牲畜已发布认领")
                                            .setContentText("无法重新登记")
                                            .show();


                                } else if (msg && code.contains("success")) {

                                    //提示设备已经被登记，提示是否解绑
                                    new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
                                            .setTitleText("设备已经被登记，是否解绑?")
                                            .setContentText("解绑重新登记")
                                            .setConfirmText("是")
                                            .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.cancel();
                                                    Intent intent = new Intent(getActivity()
                                                            , RegisterActivity.class);
                                                    intent.putExtra(getString(R.string.scan_Message),
                                                            scanMessage);
                                                    startActivity(intent);

                                                }
                                            })
                                            .setCancelText("否")
                                            .setCancelClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                                @Override
                                                public void onClick(SweetAlertDialog sDialog) {
                                                    sDialog.cancel();
                                                }
                                            })
                                            .show();


                                } else {

                                    // 未登记
                                    Intent intent = new Intent(getActivity()
                                            , RegisterActivity.class);
                                    intent.putExtra(getString(R.string.scan_Message),
                                            scanMessage);
                                    startActivity(intent);

                                }


                            }
                        });


                break;

            case 3:
                //剪毛
                //判断设备是否被绑定
                OkGo.<String>post(Constants.ISDEVICEBINDED)
                        .tag(this)
                        .params("token", mLoginSuccess.getToken())
                        .params("username", mUsername) //用户手机号
                        .params("deviceNO", isbn)
                        .params("ranchID", mLoginSuccess.getRanchID())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {

                                String result = response.body().toString();
                                Log.d(TAG1, result);
                                if (result.contains("true")) {

                                    //已绑定
                                    openCamera();

                                } else {
                                    //未绑定
                                    new SweetAlertDialog(getActivity())
                                            .setTitleText("未登记设备，不能剪毛!")
                                            .show();

                                }


                            }
                        });


                break;

            case 0:
                //拍照


                break;

        }
    }


    private void openCamera() {

        String rootDir = "/Pastureland/photo";
        FileUtils.createOrExistsDir(new File(Environment.getExternalStorageDirectory(), rootDir));
        File file = new File(Environment.getExternalStorageDirectory(), rootDir);
        photoFile = new File(file, UUID.randomUUID().toString() + ".jpg");
        FileUtils.createOrExistsFile(photoFile);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            imageUri = FileProvider.getUriForFile(getActivity(),
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
        Intent intent = new Intent(getActivity(), CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        intent.putExtra("inputUnable", 0);
        intent.putExtra("lightUnable", 1);
        intent.putExtra("albumUnable", 1);
        intent.putExtra("cancleUnable", 1);
        startActivityForResult(intent, SCAN_REQUEST_CODE);
    }

    private void startScanActivity2() {
        Intent intent = new Intent(getActivity(), CaptureActivity2.class);
        intent.putExtra(CaptureActivity2.USE_DEFUALT_ISBN_ACTIVITY, true);
        intent.putExtra("inputUnable", 0);
        intent.putExtra("lightUnable", 1);
        intent.putExtra("albumUnable", 1);
        intent.putExtra("cancleUnable", 1);
        startActivityForResult(intent, SCAN_REQUEST_CODE2);
    }


}
