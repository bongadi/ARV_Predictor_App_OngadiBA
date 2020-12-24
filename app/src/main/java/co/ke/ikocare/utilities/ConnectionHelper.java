package co.ke.ikocare.utilities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import co.ke.ikocare.activities.ConnectionAlert;

public class ConnectionHelper {

    public static void launchConnectionAlert(Activity activity, Context context, String message){

        try{
            if(message.equals("Unable to resolve host \"ikocare.co.ke\": No address associated with hostname")){

                Intent intent = new Intent(context, ConnectionAlert.class);
                intent.putExtra(StaticVariables.CALLER, activity.getClass().getName());
                context.startActivity(intent);
                activity.finish();

            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

}
