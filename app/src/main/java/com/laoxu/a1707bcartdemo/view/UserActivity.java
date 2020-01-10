package com.laoxu.a1707bcartdemo.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.blankj.utilcode.util.EncryptUtils;
import com.laoxu.a1707bcartdemo.MainActivity;
import com.laoxu.a1707bcartdemo.R;
import com.laoxu.a1707bcartdemo.api.Api;
import com.laoxu.a1707bcartdemo.api.UserApiService;
import com.laoxu.a1707bcartdemo.entity.UserEntity;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserActivity extends AppCompatActivity {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.et_pwd)
    EditText etPwd;
    @BindView(R.id.btn_login)
    Button btnLogin;
    @BindView(R.id.btn_reg)
    Button btnReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        ButterKnife.bind(this);
        initView();
        initData();
    }

    private void initData() {

    }

    private void initView() {

        /**
         * 注册功能
         */
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()

                        .baseUrl(Api.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserApiService userApiService = retrofit.create(UserApiService.class);
                String pwd = EncryptUtils.encryptMD5ToString(etPwd.getText().toString()).substring(0,8);
                System.out.println("pwd==="+pwd);

                userApiService.reg(etPhone.getText().toString(),pwd)


                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                        .subscribe(new Consumer<UserEntity>() {
                            @Override
                            public void accept(UserEntity userEntity) throws Exception {
                                Toast.makeText(UserActivity.this, userEntity.message, Toast.LENGTH_SHORT).show();

                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });



            }
        });

        /**
         * 登录功能
         */
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Retrofit retrofit = new Retrofit.Builder()

                        .baseUrl(Api.BASE_URL)
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                UserApiService userApiService = retrofit.create(UserApiService.class);
                String pwd = EncryptUtils.encryptMD5ToString(etPwd.getText().toString()).substring(0,8);
                System.out.println("pwd==="+pwd);

                userApiService.login(etPhone.getText().toString(),pwd)


                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())

                        .subscribe(new Consumer<UserEntity>() {
                            @Override
                            public void accept(UserEntity userEntity) throws Exception {
                                Toast.makeText(UserActivity.this, userEntity.message, Toast.LENGTH_SHORT).show();
                                if ("0000".equals(userEntity.status)){
                                    startActivity(new Intent(UserActivity.this, MainActivity.class));
                                    UserActivity.this.finish();
                                }


                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {

                            }
                        });


            }
        });
    }
}
