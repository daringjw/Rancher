package com.jinkun_innovation.pastureland.base;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.jinkun_innovation.pastureland.R;
import com.jinkun_innovation.pastureland.utilcode.AppManager;

import butterknife.ButterKnife;


/**
 * Created by yangxing on 2017/10/26.
 */

public abstract class BaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private ActionBar mActionBar;
    private TextView mTextView_title;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getContentView());
        ButterKnife.bind(this);
        initActionBar();
        AppManager.getAppManager().addActivity(this);
    }

    private void initActionBar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTextView_title = (TextView) findViewById(R.id.toolbar_title);
        if (mToolbar != null) {
            setSupportActionBar(mToolbar);
            mActionBar = getSupportActionBar();
            mActionBar.setDisplayShowTitleEnabled(false);
            if (mActionBar != null) {
                //设置返回按钮
                mActionBar.setHomeAsUpIndicator(R.drawable.navigation_back);
            }
        }

        initToolBar();
    }

    protected abstract void initToolBar();


    protected void setIsShowBack(boolean isShow) {
        mActionBar.setDisplayHomeAsUpEnabled(isShow);
    }

    protected Toolbar getToolBar() {
        return mToolbar;
    }

    /**
     * toolbar设置标题
     *
     * @param title
     */
    @Override
    public void setTitle(CharSequence title) {
        if (mToolbar != null) {
            mTextView_title.setText(title);
        } else {
            mTextView_title.setText(title);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                AppManager.getAppManager().finishActivity();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 获取全局的application
     *
     * @return
     */
    public Context getContext() {
        return getApplicationContext();
    }


    /**
     * 隐藏toolbar
     */
    public void hideToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.GONE);
        }
    }

    /**
     * 隐藏toolbar
     */
    public void showToolBar() {
        if (mToolbar != null) {
            mToolbar.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 加载对话框
     */
    private MaterialDialog mProgressDialog;

    protected void showProgress(String content) {
        if (null == mProgressDialog) {
            mProgressDialog = new MaterialDialog.Builder(this)
                    .content(content)
                    .progress(true, 0)
                    .canceledOnTouchOutside(false)
                    .show();
        } else {
            mProgressDialog.setContent(content);
            mProgressDialog.show();
        }
    }

    /**
     * 隐藏对话框
     */
    protected void hiddenProgress() {
        if (null != mProgressDialog && mProgressDialog.isShowing())
            mProgressDialog.dismiss();
    }


    protected abstract int getContentView();
}
