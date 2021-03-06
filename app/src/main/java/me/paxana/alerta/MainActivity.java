package me.paxana.alerta;

import android.app.Activity;
import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.app.Fragment;
import android.content.Intent;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MenuInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.support.v7.widget.Toolbar;
import com.parse.ParseUser;
import java.util.ArrayList;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import me.paxana.alerta.adapter.SlidingMenuAdapter;
import me.paxana.alerta.fragment.Fragment1;
import me.paxana.alerta.fragment.Fragment2;
import me.paxana.alerta.fragment.Fragment3;
import me.paxana.alerta.model.ItemSlideMenu;

public class MainActivity extends AppCompatActivity {
    public static final String TAG = MainActivity.class.getSimpleName();
    private List<ItemSlideMenu> listSliding;
    private SlidingMenuAdapter adapter;
    private ListView listViewSliding;
    private DrawerLayout drawerLayout;
    private android.support.v7.app.ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar mToolbar;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);


        ParseUser currentUser = ParseUser.getCurrentUser();
            //check to see if user is logged in
            if (currentUser == null) {
                navigateToLogin();  //if not, send them to the login page
            }

        else {  //if they are, send the username to the log (debug only)
                Log.i(TAG, currentUser.getUsername());
            }

        listViewSliding = (ListView)findViewById(R.id.lv_sliding_menu);
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);
        listSliding = new ArrayList<>();
        //add item for sliding list
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_settings, "Map"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_action_about, "Emergency Call"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_account_plus_black_48dp, "Friends"));
        listSliding.add(new ItemSlideMenu(R.drawable.ic_logout_black_48dp, "Log Out"));

        adapter = new SlidingMenuAdapter(this, listSliding);
        listViewSliding.setAdapter(adapter);

        //display icon to open/close slider
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackgroundColor(Color.DKGRAY);
        mToolbar.setTitleTextColor(Color.BLACK);
        assert getSupportActionBar() != null;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //item selected
        listViewSliding.setItemChecked(0, true);
        //close menu
        drawerLayout.closeDrawer(listViewSliding);
        //handle on item click
        //at some point it may make sense to change this to sort by item ID instead of position
        listViewSliding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 3) {
                    ParseUser.logOut();
                    navigateToLogin();
                } else {

                    //replace fragment
                    replaceFragment(position);
                    //item selected
                    listViewSliding.setItemChecked(position, true);
                    //close menu
                    drawerLayout.closeDrawer(listViewSliding);
                }
            }

        });

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.drawer_opened, R.string.drawer_closed) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

    }

    private void navigateToLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        //these flags remove the mainactivity from the history, so you can't back your way into mainactivity from the login screen
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
//create method replace fragment
    private void replaceFragment(int position) {
        android.support.v4.app.Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment1();
                    break;
            case 1:
                fragment = new Fragment2();
                break;
            case 2:
                fragment = new Fragment3();
                break;
            default:
                fragment = new Fragment1();
                break;
        }
        if(null != fragment) {
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            android.support.v4.app.FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.replace(R.id.main_content, fragment);
            transaction.addToBackStack(null);
            transaction.commit();

        }
    }
}