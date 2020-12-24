package co.ke.ikocare.utilities;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    private static final String BASE_URL = "https://bongadi.mcornel.com/api/v1/for/";
//    private static final String BASE_URL = "https://390c3f15c622.ngrok.io/api/v1/for/";
    private static Retrofit retrofit = null;

    public static Retrofit getApiClient(){

        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public Response intercept(@NonNull Chain chain) throws IOException {
                Request newRequest  = chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer " + StaticVariables.TOKEN)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        if(retrofit == null){
            retrofit = new Retrofit.Builder().client(client).baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
