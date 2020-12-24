package co.ke.ikocare.activities;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import co.ke.ikocare.App;
import co.ke.ikocare.R;
import co.ke.ikocare.fragments.DrugAnalysis;
import co.ke.ikocare.fragments.Loader;
import co.ke.ikocare.fragments.Login;
import co.ke.ikocare.fragments.Register;
import co.ke.ikocare.fragments.Verify;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.models.auth.UserData_;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.PreferenceManager;
import co.ke.ikocare.utilities.StaticVariables;
import co.ke.ikocare.utilities.TokenPrefUtil;
import io.github.inflationx.viewpump.ViewPumpContextWrapper;
import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import co.ke.ikocare.fragments.DrugSelector;

public class MainActivity extends AppCompatActivity implements Login.OnLoginFormActivityListener ,
        NavigationView.OnNavigationItemSelectedListener {

    static PreferenceManager preferenceManager;
    static Activity activity;
    DrawerLayout drawer;
    Toolbar toolbar;
    NavigationView navigationView;
    Window window;
    MaterialSearchView searchView;
    private Box<UserData> userBox;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        preferenceManager = new PreferenceManager(this);
        userBox = ((App)this.getApplicationContext()).getBoxStore().boxFor(UserData.class);

        activity = this;
         window  = activity.getWindow();

        if (findViewById(R.id.fragmentContainerDash) != null) {

            if (savedInstanceState != null) {
                return;
            }
            toolbar = findViewById(R.id.app_bar);
            searchView = findViewById(R.id.search_view_mat);
            navigationView = findViewById(R.id.nav_view);
            navigationView.setItemIconTintList(null);
            navigationView.setNavigationItemSelectedListener(this);
            navigationView.setCheckedItem(R.id.nav_home);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_name)).setText(StaticVariables.first_name);
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email)).setText(StaticVariables.email);
            setSupportActionBar(toolbar);
            drawer = findViewById(R.id.drawer_layout);

            Intent intent = getIntent();
            if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
                String query = intent.getStringExtra(SearchManager.QUERY);
//                doMySearch(query);
            }

            if (preferenceManager.getLoginStatus()) {
                Query<UserData> userQuery = userBox.query()
                        .order(UserData_.id, QueryBuilder.DESCENDING)
                        .build();
                UserData user = userQuery.findFirst();

                String subtitle = TokenPrefUtil.getName(getApplicationContext());
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer,toolbar,
                        R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.addDrawerListener(toggle);
                toggle.syncState();
                toolbar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
                if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorWhite));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
//                    assert user != null;
                    ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_name)).setText(user.getFirstName());
                    ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email)).setText(user.getEmail());
                }
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerDash, new DrugSelector()).commitAllowingStateLoss();
//                Objects.requireNonNull(getSupportActionBar()).setTitle(null);

            } else {

                if (Build.VERSION.SDK_INT >= 21) {
                    window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                    window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                    window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
                    window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
                }

                if (getSupportActionBar() != null) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                    getSupportActionBar().setTitle(null);
                }
                getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerDash, new Login()).commit();
            }

        }

        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {
//                toolbar.setVisibility(View.GONE);

                searchView.setSuggestions(getResources().getStringArray(R.array.query_suggestions));
                searchView.setCursorDrawable(R.drawable.custom_cusor);
                searchView.setMinimumWidth(Integer.MAX_VALUE);
            }

            @Override
            public void onSearchViewClosed() {
//                toolbar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public boolean onSearchRequested() {
//        pauseSomeStuff();
        return super.onSearchRequested();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doDrugAnalysis() {
        if (Build.VERSION.SDK_INT >= 21) {
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainerDash, new DrugAnalysis()).commitAllowingStateLoss();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
                window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            }
            drawer.closeDrawer(GravityCompat.START);
//            if (searchView.isSearchOpen()) {
//                searchView.closeSearch();
//            }else {
//                super.onBackPressed();
//            }
        } else {
            super.onBackPressed();
//            finish();
        }
    }

    public void customizeToolbar(Toolbar toolbar){
        // Save current title and subtitle
        final CharSequence originalTitle = toolbar.getTitle();
        final CharSequence originalSubtitle = toolbar.getSubtitle();

        // Temporarily modify title and subtitle to help detecting each
        toolbar.setTitle("title");
        toolbar.setSubtitle("subtitle");

        for(int i = 0; i < toolbar.getChildCount(); i++){
            View view = toolbar.getChildAt(i);

            if(view instanceof TextView){
                TextView textView = (TextView) view;
                if(textView.getText().equals("title")){
                    // Customize title's TextView
//                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
////                    params.gravity = Gravity.START;
//                    params.leftMargin = 20;
//                    textView.setLayoutParams(params);

                    // Apply custom font using the Calligraphy library
//                    Typeface typeface = TypefaceUtils.load(getAssets(), "fonts/myfont-1.otf");
//                    textView.setTypeface(typeface);

                } else if(textView.getText().equals("subtitle")){
                    // Customize subtitle's TextView
                    Toolbar.LayoutParams params = new Toolbar.LayoutParams(Toolbar.LayoutParams.WRAP_CONTENT, Toolbar.LayoutParams.MATCH_PARENT);
                    params.gravity = Gravity.END;
                    params.setMargins(0,-10,20,0);
                    textView.setLayoutParams(params);

                    // Apply custom font using the Calligraphy library
//                    Typeface typeface = TypefaceUtils.load(getAssets(), "fonts/myfont-2.otf");
//                    textView.setTypeface(typeface);
                }
            }
        }

        // Restore title and subtitle
        toolbar.setTitle(originalTitle);
        toolbar.setSubtitle(originalSubtitle);
    }

    @Override
    public void passResetCancel() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doLogin() {
        Query<UserData> userQuery = userBox.query()
                .order(UserData_.id, QueryBuilder.DESCENDING)
                .build();
        UserData user = userQuery.findFirst();
        String name = TokenPrefUtil.getName(getApplicationContext());
        toolbar.setBackgroundColor(getResources().getColor(R.color.colorWhite));
        if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorWhite));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerDash, new DrugSelector()).commitAllowingStateLoss();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(activity, drawer,toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(preferenceManager.getLoginStatus()) {
            assert user != null;
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_user_name)).setText(user.getFirstName());
            ((TextView) navigationView.getHeaderView(0).findViewById(R.id.nav_email)).setText(user.getEmail());
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doRegister() {
        if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerDash, new Register()).commitAllowingStateLoss();

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }

    }

    @Override
    public void doPassReset() {

    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doLoginPage() {

        if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }

        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerDash, new Login()).commitAllowingStateLoss();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doCode() {
        if (Build.VERSION.SDK_INT >= 21) {
//                window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(ContextCompat.getColor(activity,R.color.colorPrimary));
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainerDash, new Verify()).commitAllowingStateLoss();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setTitle(null);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.nav_home:
                doLogin();
                break;
            case R.id.nav_logout:
                PreferenceManager pref = new PreferenceManager(activity);
                pref.setLoginStatus(false);
                Intent intent = new Intent(activity,MainActivity.class);
                startActivity(intent);
                Message.makeToast(activity,activity,"Logged out successfully");
                break;
        }
//        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.search_menu,menu);
//        MenuItem item = menu.findItem(R.id.search_menu);
//        SearchView searchView = (SearchView) item.getActionView();
//        searchView.setQueryHint(getString(R.string.search_services));
//        int searchPlateId = searchView.getContext().getResources().getIdentifier("android:id/search_plate", null, null);
//        View searchPlate = searchView.findViewById(searchPlateId);
//        searchPlate.setBackgroundResource(R.drawable.search_tv);
//
//        int searchSrcTextId = getResources().getIdentifier("android:id/search_src_text", null, null);
//        EditText searchEditText = (EditText) searchView.findViewById(searchSrcTextId);
//        searchEditText.setTextColor(Color.BLACK);
//        searchEditText.setHintTextColor(Color.LTGRAY);
//
//        int closeButtonId = searchView.getContext().getResources().getIdentifier("android:id/search_close_btn", null, null);
//        ImageView closeButtonImage = (ImageView) searchView.findViewById(closeButtonId);
//        closeButtonImage.setColorFilter(ContextCompat.getColor(MainActivity.this, R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        return true;

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem item = menu.findItem(R.id.search_menu);
        searchView.setMenuItem(item);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);


        return true;
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(ViewPumpContextWrapper.wrap(newBase));
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doProducts() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doServices() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doFaqs() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doProfile() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doMyServices() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doLoader() {

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void doInvite() {

    }


}
