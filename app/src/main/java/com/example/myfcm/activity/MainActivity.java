package com.example.myfcm.activity;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.myfcm.application.MyApplication;
import com.example.myfcm.databinding.CommonToolbarBinding;
import com.example.myfcm.R;
import com.example.myfcm.databinding.ActivityMainBinding;
import com.example.myfcm.dialog.NoticeDialog;
import com.example.myfcm.fragment.ChatFragment;
import com.example.myfcm.fragment.PeopleFragment;
import com.example.myfcm.fragment.SettingFragment;
import com.example.myfcm.model.Account;
import com.example.myfcm.model.People;
import com.example.myfcm.network.Response;
import com.example.myfcm.network.response.ResponsePeople;
import com.example.myfcm.network.resultInterface.MemberListListener;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding binding;
    private CommonToolbarBinding commonToolbarBinding;
    public Fragment fragment;
    private ArrayList<People> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        commonToolbarBinding = DataBindingUtil.bind(binding.commonToolbar.toolbar);

        initLayout();
    }

    private void initLayout() {
        initCommonActionBarLayout(commonToolbarBinding, "사람들", false);
        initList();
    }

    // 사용자 목록 초기화
    private void initList() {
        networkPresenter.memberList(MyApplication.getApiToken(), new MemberListListener() {
            @Override
            public void success(ArrayList<People> responsePeople) {
                list = responsePeople;
                initNavigation();
            }

            @Override
            public void fail(String message) {
                new NoticeDialog(MainActivity.this)
                        .setMsg(message)
                        .show();
            }
        });
    }

    private void initNavigation() {
        binding.navView.setOnItemSelectedListener(item -> {
            actionBarTitleChange(item.getItemId());
            bottomNavigate(item.getItemId());
            return true;
        });
        // 최초 프래그먼트 설정
        binding.navView.setSelectedItemId(R.id.nv_list);
    }

    private void bottomNavigate(int id) {
        String tag = String.valueOf(id);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment currentFragment = fragmentManager.getPrimaryNavigationFragment();
        if (currentFragment != null) {
            fragmentTransaction.hide(currentFragment);
        }

        fragment = fragmentManager.findFragmentByTag(tag);
        // 미 생성된 프래그먼트 객체 생성
        if (fragment == null) {
            if (id == R.id.nv_list) {
                // 사용자 목록을 전달하고 프래그먼트 생성
                fragment = PeopleFragment.newInstance(list);
            } else if (id == R.id.nv_chat) {
                fragment = ChatFragment.newInstance(list);
            } else {
                fragment = new SettingFragment();
            }
            // 트랜잭션에 생성된 프래그먼트 추가
            fragmentTransaction.add(R.id.fragment, fragment, tag);
        } else {
            // 이미 생성된 프래그먼트는 바로 출력
            fragmentTransaction.show(fragment);
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragment);
        fragmentTransaction.setReorderingAllowed(true);
        fragmentTransaction.commitNow();
    }

    private void actionBarTitleChange(int id) {
        if (id == R.id.nv_list) {
            initCommonActionBarLayout(commonToolbarBinding, "사람들", false);
        } else if (id == R.id.nv_chat) {
            initCommonActionBarLayout(commonToolbarBinding, "대화", false);
        } else {
            initCommonActionBarLayout(commonToolbarBinding, "설정", false);
        }
    }
}