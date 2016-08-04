package uz.chamber.maroqand.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import uz.chamber.maroqand.AppConfig;
import uz.chamber.maroqand.R;

/**
 * Created by WTF on 2016-08-01.
 */
public class LanguageSelectActivity extends AppCompatActivity {
    Button btn_en, btn_ru, btn_uz, btn_uzk;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        btn_en=(Button)findViewById(R.id.btn_en);
        btn_ru=(Button)findViewById(R.id.btn_ru);
        btn_uz=(Button)findViewById(R.id.btn_uz);
        btn_uzk=(Button)findViewById(R.id.btn_uzk);

    }

    public void onClick(View v){
        int num=0;
        switch (v.getId()){
            case R.id.btn_en:
                num=0;
                break;
            case R.id.btn_ru:
                num=1;
                break;
            case R.id.btn_uz:
                num=2;
                break;
            case R.id.btn_uzk:
                num=3;
                break;
        }
        AppConfig.languageNum=num;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("language", AppConfig.language[num]);
        editor.commit();
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        this.finish();
    }
}
