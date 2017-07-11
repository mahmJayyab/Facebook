package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static BufferedReader reader;
    public static ArrayList<String> links = new ArrayList<>();
    public static ArrayList<String> linksPaging = new ArrayList<>();
    public static ArrayList<String> allPages = new ArrayList<>();
    public static ArrayList<Boolean> checked = new ArrayList<>();
    public static FileOutputStream outputStream;
    public static File file;
    public static DatabaseHelper myDb;
    static int lastVisibleItemIndex = 1;
    FileReader in;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> videos = new ArrayList();
    ArrayList<Video> visibleVideos = new ArrayList<>();
    int lastIndex = 0;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    FragmentManager fm = getSupportFragmentManager();
    Context cont;
    boolean isClosed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        links.clear();
        allPages.clear();
        checked.clear();
        cont =this;
        //creat database
        myDb=new DatabaseHelper(this);
        //creat cursor to read from database
        Cursor res = myDb.getAllData(DatabaseHelper.TABLE_NAME);
        if(res.getCount() == 0) {
            // show message
            myDb.insertData("MEQBAS","true");
            myDb.insertData("ajplusarabi","true");
            Log.d("asd","Null");
        }
        while (res.moveToNext()) {
            String item = res.getString(1);
            String temp = res.getString(2);
            Log.d("asd", item + "\t" + temp);
            allPages.add(item);
            if (temp.equals("true")) {
                links.add(item);
                checked.add(true);
            } else {
                checked.add(false);
            }
        }

        initialize();
        getVideos();
        Cursor res1 =MainActivity.myDb.getAllData(DatabaseHelper.TABLE_HISTORY);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String title = res1.getString(2);
            String source = res1.getString(3);
            String picture = res1.getString(4);
            Log.d("asd", pageName + "\t" + title);
            Video video = new Video(pageName,title,source,picture);
            HistoryActivity.videos.addFirst(video);
            Log.d("aa",HistoryActivity.videos+" Main");
            Log.d("aa",video.getPageName()+"556565");
        }



    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("asd", "startMethod");
    }

    public void add10() {
        Log.d("asd", lastIndex + "::" + videos.size());
        int max = Math.min(lastIndex + 10, videos.size());
        Log.d("asd", "ADD VIDEO INDEX " + lastIndex + ":" + max);
        for (int i = lastIndex; i < max; i++) {
            Log.d("asd", "ADD VIDEO INDEX " + i + ":" + videos.get(i).getTitle());
            visibleVideos.add(videos.get(i));
        }
        Log.d("asd", "TOTAL VISABLE:" + visibleVideos.size());
        lastIndex = Math.min(lastIndex + 10, videos.size());
        if (lastIndex == videos.size()) {
            getVideos();
            Log.d("asd", "getFUCKINGnew");
        }
    }

    public void initialize() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                 AlertDFragment alertdFragment = new AlertDFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");

            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoAdapter(visibleVideos, this);
        mRecyclerView.setAdapter(mAdapter);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemIndex < lastVisibleItem) {
                    Log.d("asd", totalItemCount + ":" + lastVisibleItem);
                    if (totalItemCount - 1 == lastVisibleItem) {
                        add10();
                    }
                    lastVisibleItemIndex = lastVisibleItem;
                }
            }
        });
    }

    public void getVideos() {
        if (links.isEmpty()) return;
        AccessToken token = new AccessToken(getString(R.string.accesstoken), getString(R.string.appId), "128841827707620",
                null, null, null, null, null);
        GraphRequestBatch batch = new GraphRequestBatch();
        if (links.size() != linksPaging.size()) {
            linksPaging.clear();
            for (int i = 0; i < links.size(); i++) {
                linksPaging.add("");
            }
        }
        for (int i = 0; i < links.size(); i++) {
            String link = links.get(i);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -4);
            String afterQ = "&since=" + (cal.getTimeInMillis() / 1000);
            if (!linksPaging.get(i).isEmpty()) afterQ = "&after=" + linksPaging.get(i);
            final int linkIndex = i;


            batch.add(new GraphRequest(token,
                            link + "/videos?fields=from,source,id,picture,created_time,likes.limit(0).summary(true),description,title&limit=10" + afterQ
                    , null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    Video video;
                    String picture = "", source = "", title = "", description = "", id = "", created_time = "",pageName;
                    try {
                        JSONObject jsPageName =  response.getJSONObject().getJSONArray("data").getJSONObject(0).getJSONObject("from");
                         pageName = (jsPageName.has("name") && !jsPageName.isNull("name")) ? jsPageName.getString("name") : "";

                        // Log.d("asd",response.toString());
                        for (int i = 1; i < response.getJSONObject().getJSONArray("data").length(); i++) {
                            JSONObject js = response.getJSONObject().getJSONArray("data").getJSONObject(i);
                            //picture=js.getJSONObject("thumbnails").getJSONArray("data").getJSONObject(0).getString("uri");
                            picture = (js.has("picture") && !js.isNull("picture")) ? js.getString("picture") : "";
                            source = (js.has("source") && !js.isNull("source")) ? js.getString("source") : "";
                            title = (js.has("title") && !js.isNull("title")) ? js.getString("title") : "";
                            description = (js.has("description") && !js.isNull("description")) ? js.getString("description") : "";
                            id = (js.has("id") && !js.isNull("id")) ? js.getString("id") : "";
                            created_time = (js.has("created_time") && !js.isNull("created_time")) ? js.getString("created_time") : "";
                            //String summary = js.getString("summary");
                            String likes = js.getJSONObject("likes").getJSONObject("summary").getString("total_count");
                            String page_pic = js.getJSONObject("from").getString("id");
                            video = new Video(source, description, title, id, picture, created_time, likes, pageName, "false", page_pic);

                            videos.add(video);
                        }
                        linksPaging.set(linkIndex, response.getJSONObject().getJSONObject("paging").getJSONObject("cursors").getString("after"));
                        Log.d("asdnewpage", linksPaging.get(linkIndex) + "-" + linkIndex);
                        // String linkNext = response.getJSONObject().getJSONObject("paging").getString("next");


                    } catch (Exception ex) {
                        Log.d("asd", ex.toString());
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            })

            );

        }
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                Collections.sort(videos, new Comparator<Video>() {
                    @Override
                    public int compare(Video video2, Video video1) {
                        return video1.getCreated_date().compareTo(video2.getCreated_date());
                    }
                });
                //visibleVideos.addAll( videos);
                add10();
            }
        });
        batch.executeAsync();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Integer test = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:
                DialogSettings dS = new DialogSettings();
                // Show Alert DialogFragment
                dS.show(fm, "Alert Dialog Fragment");
                break;

            case R.id.action_history:

                Log.d("asd", cont + "");
                Intent myIntent = new Intent(cont, HistoryActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(myIntent);
                break;
            case R.id.action_favorite:

                Log.d("asd", cont + "");
                Intent myIntent1 = new Intent(cont, FavoriteActivity.class);
                myIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(myIntent1);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();


            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
            return true;
        }

       /* @Override
        public void onDestroy ()
        {
            myDb.close();
            super.onDestroy();

        }

        @Override
        public void onStop ()
        {
            myDb.close();
            super.onStop();
        }*/
}
