package mahmjayyab.com.example.facebook;

import android.content.BroadcastReceiver;
import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import java.net.InetAddress;

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
    public static VideoView smallVideo;
    public static RelativeLayout smallVideoLayout;
    static ProgressBar videoProgressBar;
    static Uri vLink;
    static MediaController mediacontroller;
    static Context myContext;
    static Video videoForPlay;
    ImageButton closeBtn;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private TabLayout t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main2);
        Log.d("jayab",isNetworkConnected()+"");
        myContext = Main2Activity.this;
         clearHistory = (FloatingActionButton) findViewById(R.id.clearHistory);
        clearFavorite = (FloatingActionButton) findViewById(R.id.clearFavorite);
        addPage = (FloatingActionButton) findViewById(R.id.add);
        clearFavorite = (FloatingActionButton) findViewById(R.id.clearFavorite);
        clearHistory = (FloatingActionButton) findViewById(R.id.clearHistory);
        addPage.setVisibility(View.GONE);
        smallVideo = (VideoView)findViewById(R.id.smallVideo);
        smallVideoLayout =(RelativeLayout)findViewById(R.id.smallVideoLayout);
        videoProgressBar = (ProgressBar)findViewById(R.id.videoProgressBar);
        closeBtn =(ImageButton)findViewById(R.id.close_button);
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

        smallVideoLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallVideoLayout.setVisibility(View.GONE);
                VideoPlayer.video = videoForPlay;
                Intent myIntent = new Intent(Main2Activity.this, VideoPlayer.class);
                int p =smallVideo.getCurrentPosition();
                VideoPlayer.postion =p;
                VideoPlayer.setPos= true;
                smallVideo.setVisibility(View.GONE);
                startActivity(myIntent);
            }
        });
        smallVideo.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
                smallVideoLayout.setVisibility(View.GONE);
                smallVideo.setVisibility(View.GONE);
            }
        });
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smallVideoLayout.setVisibility(View.GONE);
                smallVideo.setVisibility(View.GONE);
            }
        });

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
        Log.d("ttt","OnPack");

        this.finish();
        android.os.Process.killProcess(android.os.Process.myPid());

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



            Log.d("haha","referch");
            //tab1 = new MainActivity();

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
    public  static void playSmallVideo(Uri videoLink ,int pos ,Video video){
        //smallVideo.setLayoutParams(new RelativeLayout.LayoutParams(600,300));
        videoForPlay = video;
        smallVideoLayout.setVisibility(View.VISIBLE);
        smallVideo.setVisibility(View.VISIBLE);
        // Main2Activity.smallVideo = videoview;
        try {
            // Start the MediaController
            mediacontroller = new MediaController(
                    myContext);
            mediacontroller.setAnchorView(Main2Activity.smallVideo);
            // Get the URL from String VideoURL
            vLink = videoLink;
            smallVideo.setMediaController(Main2Activity.mediacontroller);
            smallVideo.setVideoURI(Main2Activity.vLink);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }

        smallVideo.requestFocus();
        smallVideo.seekTo(pos);
        smallVideo.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            // Close the progress bar and play the video
            public void onPrepared(MediaPlayer mp) {
                //pDialog.dismiss();
                // progressBar.setVisibility(View.GONE);
                videoProgressBar.setVisibility(View.GONE);
                smallVideo.start();
                mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                    @Override
                    public boolean onInfo(MediaPlayer mp, int what, int extra) {
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                            videoProgressBar.setVisibility(View.VISIBLE);
                        if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                            videoProgressBar.setVisibility(View.GONE);
                        return false;
                    }
                });


            }
        });
    }
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("https://www.google.com"); //You can replace it with your name
            Log.d("jayab",ipAddr.toString());
            return !ipAddr.equals("");

        } catch (Exception e) {
            Log.d("jayab",e.toString());
            return false;
        }
    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }
}

