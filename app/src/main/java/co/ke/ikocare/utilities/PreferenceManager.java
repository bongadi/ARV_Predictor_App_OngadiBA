package co.ke.ikocare.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import co.ke.ikocare.MutationAnalysisRequestQuery;
import co.ke.ikocare.R;

public class PreferenceManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    public Context _context;
    List<MutationAnalysisRequestQuery.Data> mutationResults = new ArrayList<>();

    // shared pref mode
    private int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "intro_slider-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setLoginStatus( boolean status)
    {
        editor.putBoolean(_context.getString(R.string.pref_login_status),status);
        editor.commit();
    }

    public  boolean getLoginStatus(){
        return pref.getBoolean(_context.getString(R.string.pref_login_status),false);
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void showToast(String message){
        Toast.makeText(_context, message, Toast.LENGTH_SHORT).show();
    }

    public void setDrugSelected(boolean selected, String name){
        editor.putBoolean(name,selected);
        editor.apply();
    }

    public boolean getDrugStatus(String key){
        return pref.getBoolean(key,false);
    }

//    public void setMutationResultData(String name,List<MutationAnalysisRequestQuery.Data> data){
//        mutationResults.addAll(data);
//        editor.putString(name,data);
//        editor.apply();
//    }

}
