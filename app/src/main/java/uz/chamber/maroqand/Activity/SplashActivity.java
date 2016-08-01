package uz.chamber.maroqand.Activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import java.util.Locale;

import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.R;

/**
 * Created by lk on 2016. 7. 29..
 */
public class SplashActivity extends AppCompatActivity {


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        initView();
        languageCheck();
        changeLanguage(AppConfig.getLanguage());
    }

    private void languageCheck() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(getApplicationContext(), Main.class));

            }
        }, 2000);
    }

    public void changeLanguage(String languageToLoad) {
        //for language change
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }

    private void initView() {
    }


}
