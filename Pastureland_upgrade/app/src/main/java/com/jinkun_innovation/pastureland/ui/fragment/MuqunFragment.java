package com.jinkun_innovation.pastureland.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.FrameLayout;

import com.jinkun_innovation.pastureland.R;



/**
 * Created by Guan on 2018/4/28.
 */

public class MuqunFragment extends Fragment {

    View view;
    View viewMuqun;
    View viewMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        view = View.inflate(getActivity(), R.layout.fragment_muqun1, null);

        final FrameLayout flMuqun1 = view.findViewById(R.id.flMuqun1);

        if (viewMuqun == null) {
            viewMuqun = View.inflate(getActivity(), R.layout.layout_muqun, null);
            flMuqun1.addView(viewMuqun);
        }
        viewMuqun.setVisibility(View.VISIBLE);

        if (viewMap == null) {
            viewMap = View.inflate(getActivity(), R.layout.layout_muqun_map, null);
            flMuqun1.addView(viewMap);
        }
        viewMap.setVisibility(View.GONE);


        Button btnMuqun = viewMuqun.findViewById(R.id.btnMuqun);
        btnMuqun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                viewMuqun.setVisibility(View.GONE);
                viewMap.setVisibility(View.VISIBLE);

            }

        });

        return view;

    }


    private boolean isGetData = false;

    @Override
    public Animation onCreateAnimation(int transit, boolean enter, int nextAnim) {
        //   进入当前Fragment
        if (enter && !isGetData) {
            isGetData = true;
            //   这里可以做网络请求或者需要的数据刷新操作


        } else {
            isGetData = false;
        }
        return super.onCreateAnimation(transit, enter, nextAnim);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!isGetData) {
            //   这里可以做网络请求或者需要的数据刷新操作


            isGetData = true;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        isGetData = false;
    }

}
