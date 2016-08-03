package uz.chamber.maroqand.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
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
    String language;

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
                SharedPreferences sharedPreferences = getSharedPreferences("pref",MODE_PRIVATE);
                language=sharedPreferences.getString("language","");
                //if(true){
                if(language.equals("")) { // Not setting yet
                    startActivity(new Intent(getApplicationContext(), LanguageSelectActivity.class)); // todo setting language
                    finish();
                } else{
                    for(int i=0; i<AppConfig.language.length; i++){
                        if(AppConfig.language[i].equals(language))
                            AppConfig.languageNum = i;
                    }
                    changeLanguage(AppConfig.getLanguage());
                    startActivity(new Intent(getApplicationContext(), Main.class));
                    finish();
                }
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
