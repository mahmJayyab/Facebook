package mahmjayyab.com.example.facebook;

import android.content.ComponentCallbacks2;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Main2Activity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;
    SupscripeActivity tab2 ;
    HistoryActivity tab4 ;
    FavoriteActivity tab3 ;
    MainActivity tab1;
    public static FloatingActionButton clearHistory;
    public static FloatingActionButton clearFavorite;
    public static FloatingActionButton addPage;
    public static FragmentManager fm;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
         clearHistory = (FloatingActionButton) findViewById(R.id.clearHistory);
        clearFavorite = (FloatingActionButton) findViewById(R.id.clearFavorite);
        addPage = (FloatingActionButton) findViewById(R.id.add);
        clearFavorite = (FloatingActionButton) findViewById(R.id.clearFavorite);
        clearHistory = (FloatingActionButton) findViewById(R.id.clearHistory);
        addPage.setVisibility(View.GONE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        //tabLayout.setBackgroundColor(Color.WHITE);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home_black_24dp);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_ondemand_video_black_24dp);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_favorite_black_24dp);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_history_black_24dp);
        t = tabLayout;
        fm = this.getSupportFragmentManager();


       /* FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                AlertDFragment alertdFragment = new AlertDFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");

            }
        });*/

    }


    @Override
    public void onBackPressed(){
        finish();

    }


    @Override
    public void onStart()
    {
        super.onStart();
        if(tab4 != null)
        {
            Log.d("aff","tab4");
            tab4.getVideos();
        }
        if(tab2 != null){
            Log.d("aff","Tab2");
            //tab2.getSub();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);

            Log.d("afff","99");

        }
        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            //return PlaceholderFragment.newInstance(position + 1);

            switch (position){
                case 0:
                    Log.d("ggg","0");
                    tab1 = new MainActivity();
                    //tab1 = new MainActivity();
                    return tab1;
                case 1:
                    Log.d("ggg","1");
                    tab2 = new SupscripeActivity();

                    //addPage.setVisibility(View.VISIBLE);
                    //clearHistory.setVisibility(View.GONE);
                    //clearFavorite.setVisibility(View.GONE);

                    return tab2;

                case 2:
                    Log.d("ggg","2");
                    tab3 = new FavoriteActivity();


                    return tab3;

                case 3:
                    Log.d("ggg","3");
                    tab4 = new HistoryActivity();

                    return tab4;
                default:
                    return null;
            }

        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 4;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Home";
                case 1:
                    return "Subscriptions";
                case 2:
                    return "Favourite";
                case 3:
                    return "History";
            }
            return null;
        }
    }
}
