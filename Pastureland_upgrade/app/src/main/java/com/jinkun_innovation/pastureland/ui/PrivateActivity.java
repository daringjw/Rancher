package com.jinkun_innovation.pastureland.ui;

import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.base.BaseActivity;
import com.jinkun_innovation.pastureland.bean.ClaimListBean;
import com.jinkun_innovation.pastureland.bean.ConfirmClaimBean;
import com.jinkun_innovation.pastureland.net.ApiCall;
import com.jinkun_innovation.pastureland.net.RetrofitManger;
import com.jinkun_innovation.pastureland.utilcode.SpUtil;
import com.jinkun_innovation.pastureland.utilcode.util.LogUtils;
import com.jinkun_innovation.pastureland.utilcode.util.ToastUtils;
import com.jinkun_innovation.pastureland.view.recyclerview.CommonAdapter;
import com.jinkun_innovation.pastureland.view.recyclerview.base.ViewHolder;
import com.jinkun_innovation.pastureland.view.recyclerview.wrapper.EmptyWrapper;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by yangxing on 2018/1/22.
 */

public class PrivateActivity extends BaseActivity {
    @BindView(R.id.recycle_private)
    RecyclerView mRecyclePrivate;
    @BindView(R.id.pb_private)
    ProgressBar mPbPrivate;
    @BindView(R.id.cc_private_error)
    ConstraintLayout mCcPrivateError;

    private EmptyWrapper mEmptyWrapper;

    private List<ClaimListBean.DataBean> mClaimList = new ArrayList<>();

    @Override
    protected void initToolBar() {
        setTitle("私人定制");
        setIsShowBack(true);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_private;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);
        initRecycle();
        initDate();
    }

    private void initDate() {
        RetrofitManger.getInstance().createReq(ApiCall.class)
                .getClaimList(SpUtil.getToken(), SpUtil.getUserId(), SpUtil.getAccount())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ClaimListBean>() {
                    @Override
                    public void onStart() {
                        mPbPrivate.setVisibility(View.VISIBLE);
                        mCcPrivateError.setVisibility(View.GONE);
                        mRecyclePrivate.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        mPbPrivate.setVisibility(View.GONE);
                        mCcPrivateError.setVisibility(View.VISIBLE);
                        mRecyclePrivate.setVisibility(View.GONE);

                    }

                    @Override
                    public void onNext(ClaimListBean claimListBean) {
                        if (claimListBean.getCode().equals("200")) {
                            for (ClaimListBean.DataBean dataBean : claimListBean.getData()) {
                                mClaimList.add(dataBean);
                            }
                            mPbPrivate.setVisibility(View.GONE);
                            mCcPrivateError.setVisibility(View.GONE);
                            mRecyclePrivate.setVisibility(View.VISIBLE);
                            mEmptyWrapper.notifyDataSetChanged();
                        }

                    }
                });
    }

    private void initRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclePrivate.setLayoutManager(linearLayoutManager);

        CommonAdapter<ClaimListBean.DataBean> claimCommonAdapter = new CommonAdapter<ClaimListBean.DataBean>(PrivateActivity.this, R.layout.item_private, mClaimList) {
            @Override
            protected void convert(final ViewHolder holder, ClaimListBean.DataBean dataBean, final int position) {
                holder.setText(R.id.tv_private_variety, "领养种类：" + dataBean.getAnimalVariety());
                holder.setText(R.id.tv_private_name, "领养人姓名：" + dataBean.getClaimName());
                holder.setText(R.id.tv_private_address, "领养人地址：" + dataBean.getClaimAddr());
                holder.setText(R.id.tv_private_phone, "领养人手机号：" + dataBean.getClaimPhone());
                if (dataBean.getState()==1) {
                    holder.setText(R.id.btn_private_comfire, "确认");
                } else {
                    holder.setText(R.id.btn_private_comfire, "已确认");
                }
                holder.setOnClickListener(R.id.btn_private_comfire, new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (((Button) view).getText().equals("确认")) {
                            toConfirm(position, holder.getView(R.id.btn_private_comfire));
                        } else {
                            ToastUtils.showShort("已确认");
                        }

                    }
                });
            }
        };
        mEmptyWrapper = new EmptyWrapper(claimCommonAdapter);
        mEmptyWrapper.setEmptyView(R.layout.empty_view);
        mRecyclePrivate.setAdapter(mEmptyWrapper);
    }

    private void toConfirm(final int position, final View view) {
        RetrofitManger.getInstance().createReq(ApiCall.class)
                .confireClaimList(mClaimList.get(position).getOrderId(), SpUtil.getToken(), SpUtil.getUserId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<ConfirmClaimBean>() {
                    @Override
                    public void onStart() {
                        showProgress("正在确认");
                    }

                    @Override
                    public void onCompleted() {
                        hiddenProgress();

                    }

                    @Override
                    public void onError(Throwable e) {
                        hiddenProgress();
                        LogUtils.e(e.getMessage());
                        ToastUtils.showShort(e.getMessage());

                    }

                    @Override
                    public void onNext(ConfirmClaimBean confirmClaimBean) {
                        if (confirmClaimBean.getCode().equals("200")) {
                            ((Button) view).setText("已确认");
                            mClaimList.get(position).setState(2);
                            mEmptyWrapper.notifyDataSetChanged();
                        } else {
                            ToastUtils.showShort("确认失败，请重试");
                        }
                    }
                });
    }

    @OnClick(R.id.cc_private_error)
    public void onClick() {
        initDate();
    }


}
