package uz.chamber.maroqand;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by WTF on 2016-07-19.
 */
public class U_TestActivity extends AppCompatActivity {

    TextView tv;
    ImageView imageView;
    ParserPictureText parserPictureText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.u_test);

        tv=(TextView)findViewById(R.id.textView);
        imageView=(ImageView)findViewById(R.id.imageView);

        CallBackNetwork title = new CallBackNetwork() {

            @Override
            public void setTitle(final String title) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try{
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    tv.setText(title);
                                    new DownloadImageTask(imageView).execute(title);
                                }
                            });
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        };
        parserPictureText= new ParserPictureText("http://chamber.uz/en/page/2417", title);

    }
}
