package co.ke.ikocare.utilities;

import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import kotlin.jvm.JvmStatic;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MyApolloClient {

    private final String BASE_URL = "https://hivdb.stanford.edu/graphql";
    private com.apollographql.apollo.ApolloClient apolloClient = null;
    private OkHttpClient okHttpClient = null;

    @JvmStatic
    public com.apollographql.apollo.ApolloClient getApolloClient(Context context){
        apolloClient = com.apollographql.apollo.ApolloClient.builder()
                .okHttpClient(getOkHttpClient(context))
                .serverUrl(BASE_URL)
                .build();

        return apolloClient;
    }

    public OkHttpClient getOkHttpClient(Context context){
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @NotNull
                    @Override
                    public Response intercept(@NotNull Chain chain) throws IOException {
                        Request newRequest  = chain.request().newBuilder()
                                .addHeader("Content-Type", "application/json")
                                .method("POST",chain.request().body())
                                .build();
//                        .method(chain.request().method(),chain.request().body())
                        return chain.proceed(newRequest);
                    }
                })
                .build();


        return okHttpClient;
    }

}
