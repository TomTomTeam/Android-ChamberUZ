package uz.chamber.maroqand.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import uz.chamber.maroqand.Util.AppConfig;
import uz.chamber.maroqand.R;

public class LanguageSelectActivity extends AppCompatActivity {
    Button btn_en, btn_ru, btn_uz, btn_uzk, social;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_select);

        initView();

        social.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Popup facebook page
                startActivity(new Intent(Intent.ACTION_VIEW,Uri.parse("https://www.facebook.com/uzchamber")));
            }
        });
    }

    private void initView() {
        btn_en = (Button) findViewById(R.id.btn_en);
        btn_ru = (Button) findViewById(R.id.btn_ru);
        btn_uz = (Button) findViewById(R.id.btn_uz);
        btn_uzk = (Button) findViewById(R.id.btn_uzk);
        social = (Button) findViewById(R.id.social);
    }

    public void onClick(View v) {
        int num = 0;
        switch (v.getId()) {
            case R.id.btn_en:
                num = 0;
                break;
            case R.id.btn_ru:
                num = 1;
                break;
            case R.id.btn_uz:
                num = 2;
                break;
            case R.id.btn_uzk:
                num = 3;
                break;
        }

        AppConfig.languageNum = num;
        SharedPreferences pref = getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString("language", AppConfig.language[num]);
        editor.commit();
        startActivity(new Intent(getApplicationContext(), SplashActivity.class));
        this.finish();
    }
}
