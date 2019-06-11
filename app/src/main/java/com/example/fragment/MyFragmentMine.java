package com.example.fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.activity.AlterPwdActivity;
import com.example.activity.MainActivity;
import com.example.activity.R;

@SuppressLint("ValidFragment")
public class MyFragmentMine extends Fragment implements  View.OnClickListener{
    @Nullable

    private Button logOut;
    private TextView tvUserName;

    private String userName;

    private TextView modifyPwd;
    private TextView about;
    private TextView contactUs;

    @SuppressLint("ValidFragment")
    public MyFragmentMine(String userName) {
        this.userName = userName;
    }

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.layout_mine, container, false);

        logOut = (Button)view.findViewById(R.id.logout);
        tvUserName = (TextView)view.findViewById(R.id.user_name);
        modifyPwd = (TextView)view.findViewById(R.id.mine_change_pwd);
        about = (TextView)view.findViewById(R.id.mine_about);
        contactUs = (TextView)view.findViewById(R.id.mine_contact);


        logOut.setOnClickListener(this);
        modifyPwd.setOnClickListener(this);
        about.setOnClickListener(this);
        contactUs.setOnClickListener(this);

        setUserName();//设置用户名

        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.logout:
                Intent iLogOUt = new Intent(getContext(), MainActivity.class);
                startActivity(iLogOUt);
                Toast.makeText(getContext(), "成功退出账号", Toast.LENGTH_SHORT).show();
                getActivity().finish();
                break;
            case R.id.mine_change_pwd:
                Intent iChangePwd = new Intent(getContext(), AlterPwdActivity.class);

                Bundle bundle = new Bundle();

                bundle.putString("username", userName);

                iChangePwd.putExtras(bundle);
                startActivity(iChangePwd);
                break;
            case R.id.mine_about:
            case R.id.mine_contact:
        }
    }

    private void setUserName() {
        tvUserName.setText(userName);
    }
}
