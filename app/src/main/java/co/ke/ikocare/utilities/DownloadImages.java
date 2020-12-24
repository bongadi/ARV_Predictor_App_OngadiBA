package co.ke.ikocare.utilities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class DownloadImages {

    @SuppressLint("StaticFieldLeak")
    private static Context contxt;
    private static String filename;
    @SuppressLint("StaticFieldLeak")
    static Activity activty;

    public static void executeDownload(Activity activity, Context context, String filename, String url){
        contxt = context;

        DownloadImages.filename = filename;

        new DownloadTask(contxt).execute(StaticVariables.server_url+"/uploads/services/"+ url);

    }


    static class DownloadTask extends AsyncTask<String, Integer, String> {

        @SuppressLint("StaticFieldLeak")
        Context context;
        @SuppressLint("StaticFieldLeak")
        Activity activity;

        DownloadTask(Context ctx) {
            activity = (Activity) ctx;
            context = ctx;
        }

        @Override
        protected void onPreExecute() {
            StaticVariables.isimagedownloading = true;
        }

        @Override
        protected String doInBackground(String... params) {
            String path = params[0];
            int file_length = 0;

            try {
                URL url = new URL(path);
                URLConnection urlConnection = url.openConnection();
                urlConnection.connect();
                file_length = urlConnection.getContentLength();

                InputStream inputStream = new BufferedInputStream(url.openStream(),8192);

                byte[] data = new byte[1024];
                int total = 0;
                int count = 0;

                FileOutputStream outputStream = contxt.openFileOutput(filename, Context.MODE_PRIVATE);

                while ((count=inputStream.read(data)) != -1){
                    total += count;
                    outputStream.write(data,0,count);
                    int progress = total *100/file_length;
                    publishProgress(progress);
                }
                inputStream.close();
                outputStream.close();

            } catch (MalformedURLException e) {
                e.printStackTrace();
                Log.i("Error1",e.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
                Log.i("Error2",e.getMessage());
            }
            return "Download Complete...";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {

        }

        @Override
        protected void onPostExecute(String result) {
            StaticVariables.isimagedownloading = false;
        }
    }

}
