package comcesar1287.github.bolocopadomundo2018.preferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class MainPreference {

    private static final String USER_REFERENCE = "userReference";
    private static final String BET_REFERENCE = "betReference";

    public static void setUserReference(Context context, String userReference){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(USER_REFERENCE, userReference);
        editor.apply();
    }

    public static String getUserReference(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(USER_REFERENCE, null);
    }

    public static void setBetReference(Context context, String companyReference){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(BET_REFERENCE, companyReference);
        editor.apply();
    }

    public static String getBetReference(Context context){
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPref.getString(BET_REFERENCE, null);
    }

    public static void cleanPreference(Context context){
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().apply();
    }
}