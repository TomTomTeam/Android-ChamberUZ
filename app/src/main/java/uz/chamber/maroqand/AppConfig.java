package uz.chamber.maroqand;

import android.util.Log;

/**
 * Created by lk on 2016. 7. 29..
 */
public class AppConfig {

    public final static String[] language = new String[]{"en", "ru", "uz", "uzk"};
    public static int languageNum = 0; //defult is English

    public static String getRealPath(String absHref) {
        try {

            String url = "";
            if (isAlreadyRealPath(absHref))                                     // already real path
                return absHref;
            for (String t : language) {
                url = absHref.replace("/" + t + "/", "");
            }
            if (url.charAt(0) == '/') {                                               // other url
                Log.i("AppConfig", "http://chamber.uz/" + absHref);
                return "http://chamber.uz/" + absHref;
            } else {
                Log.i("AppConfig", "http://chamber.uz/" + language[languageNum] + "/" + url);
                return "http://chamber.uz/" + language[languageNum] + "/" + url;
            }
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
            return absHref;
        }
    }


    private static boolean isAlreadyRealPath(String absHref) {
        if (absHref.contains("chamber.uz"))
            return true;
        if (absHref.contains("http://"))
            return true;
        return false;
    }

    public static String getLanguage(){
        return language[languageNum];
    }
}
