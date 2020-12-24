package co.ke.ikocare.utilities;

import android.util.Log;

public class StaticVariables {

    public final static String CALLER = "co.ke.ikocare.CALLER";
    public static boolean isOnPausedCausedByPayLoan = false;

    public static String TOKEN = null;

    public static String userid = "";
    public static String first_name = "John";
    public static String last_name = "Doe";
    public static String phone_number = "";
    public static String pin = "";
    public static String email = "john.doe@gmail.com";
    public static String userstatus = "";
    public static String server_url = "https://bongadi.mcornel.com/";
//    public static String server_url = "https://46dc1489.ngrok.io";
    public static String imagename = "splashimage.jpg";
    public static Boolean isimagedownloading = false;

    public static String pin_reset_phone_number = "";

    public static Double activeloan = 0.0;

    public static void log(String message){
        Log.d("LIQ::", message+"");
    }

}
