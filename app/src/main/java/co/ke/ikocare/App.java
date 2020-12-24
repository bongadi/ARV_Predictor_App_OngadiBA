package co.ke.ikocare;

import android.app.Application;

import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Operation;
import com.apollographql.apollo.api.ResponseField;
import com.apollographql.apollo.cache.normalized.CacheKey;
import com.apollographql.apollo.cache.normalized.CacheKeyResolver;
import com.apollographql.apollo.cache.normalized.NormalizedCacheFactory;
import com.apollographql.apollo.cache.normalized.lru.EvictionPolicy;
import com.apollographql.apollo.cache.normalized.lru.LruNormalizedCacheFactory;
import com.apollographql.apollo.subscription.WebSocketSubscriptionTransport;
import com.google.android.gms.maps.MapsInitializer;

import org.jetbrains.annotations.NotNull;

import java.util.Map;

import co.ke.ikocare.models.auth.MyObjectBox;
import io.github.inflationx.calligraphy3.CalligraphyConfig;
import io.github.inflationx.calligraphy3.CalligraphyInterceptor;
import io.github.inflationx.viewpump.ViewPump;
import io.objectbox.BoxStore;
import io.paperdb.Paper;
import okhttp3.OkHttpClient;

public class App extends Application {

    private static App sApp;
    private BoxStore mBoxStore;
    private ApolloClient apolloClient;
    private static final String BASE_URL = "https://api.githunt.com/graphql";
    private static final String SUBSCRIPTION_BASE_URL = "wss://api.githunt.com/subscriptions";

    private static final String SQL_CACHE_NAME = "githuntdb";

    @Override
    public void onCreate() {
        super.onCreate();
        ViewPump.init(ViewPump.builder()
                .addInterceptor(new CalligraphyInterceptor(
                        new CalligraphyConfig.Builder()
                                .setDefaultFontPath("fonts/Seravek-Medium.ttf")
                                .setFontAttrId(R.attr.fontPath)
                                .build()))
                .build()
        );

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .build();

        MapsInitializer.initialize(this);
        Paper.init(this);

//        NormalizedCacheFactory normalizedCacheFactory = new LruNormalizedCacheFactory(EvictionPolicy.NO_EVICTION)
//                .chain(new SqlNo(this, SQL_CACHE_NAME));

        CacheKeyResolver cacheKeyResolver = new CacheKeyResolver() {
            @NotNull
            @Override
            public CacheKey fromFieldArguments(@NotNull ResponseField responseField, @NotNull Operation.Variables variables) {
                return CacheKey.NO_KEY;
            }

            @NotNull
            @Override
            public CacheKey fromFieldRecordSet(@NotNull ResponseField field, @NotNull Map<String, Object> recordSet) {
                String typeName = (String) recordSet.get("__typename");
                if ("User".equals(typeName)) {
                    String userKey = typeName + "." + recordSet.get("login");
                    return CacheKey.from(userKey);
                }
                if (recordSet.containsKey("id")) {
                    String typeNameAndIDKey = recordSet.get("__typename") + "." + recordSet.get("id");
                    return CacheKey.from(typeNameAndIDKey);
                }
                return CacheKey.NO_KEY;
            }
        };

//        apolloClient = ApolloClient.builder()
//                .serverUrl(BASE_URL)
//                .okHttpClient(okHttpClient)
//                .normalizedCache(normalizedCacheFactory, cacheKeyResolver)
//                .subscriptionTransportFactory(new WebSocketSubscriptionTransport.Factory(SUBSCRIPTION_BASE_URL, okHttpClient))
//                .build();

        mBoxStore = MyObjectBox.builder().androidContext(App.this).build();
    }

    public ApolloClient apolloClient() {
        return apolloClient;
    }

    public static App getApp() {
        return sApp;
    }
    public BoxStore getBoxStore() {
        return mBoxStore;
    }
}
