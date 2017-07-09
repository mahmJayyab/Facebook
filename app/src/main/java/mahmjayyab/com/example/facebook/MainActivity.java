package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Scanner;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FileReader in;
    public static BufferedReader reader;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> videos = new ArrayList();
    public  static ArrayList<String> links = new ArrayList<>();
    public  static ArrayList<String> allPages = new ArrayList<>();
    public  static ArrayList<Boolean> checked = new ArrayList<>();
    ArrayList<Video> visibleVideos = new ArrayList<>();
    int lastIndex;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    FragmentManager fm = getSupportFragmentManager();

    public static FileOutputStream outputStream;
    public static File file;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         file = new File(getFilesDir(), "data.txt");
        try  {
           // outputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
           // outputStream.write("MEQBAS#true\najplusarabi#true\n".getBytes());
            //outputStream.close();
        } catch(Exception ex)
        {
            Log.d("asd",ex.toString());
        }
        //links.add("MEQBAS");
        //links.add("ajplusarabi");
        try  {

            //InputStream is = getResources().openRawResource(R.raw.data);
            reader = new BufferedReader(new FileReader(file));
            String link;
            Scanner s;
            while((link = reader.readLine()) != null)
            {
                s = new Scanner(link).useDelimiter("#");
                String item = s.next();
                String temp = s.next();
                Log.d("asd",item+"\t"+temp);
                allPages.add(item);
                if(temp.equals("true")) {
                    links.add(item);
                    checked.add(true);
                }
                else {
                    checked.add(false);
                }
                Log.d("asd",checked.get(0)+"");
            }
            //reader.close();
           // Log.d("asd",reader.readLine());
        } catch (Exception ex)
        {
            Log.d("asd",ex.toString());
            links.add("MEQBAS");
        }
        initialize();
        getVideos();


    }
    @Override
    public void onStart()
    {
        super.onStart();
        Log.d("asd","startMethod");
    }
    public void add10(){
        Log.d("asd",lastIndex+":"+videos.size());
        int max = Math.min(lastIndex+10,videos.size());
        for(int i=lastIndex;i<max;i++){
            visibleVideos.add(videos.get(i));
        }
        lastIndex += 10;
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
                Log.d("asd",totalItemCount+":"+lastVisibleItem);
                if(totalItemCount-1 == lastVisibleItem){
                    add10();
                }
            }
        });
    }

    public void getVideos()
    {
        if(links.isEmpty()) return;
        AccessToken token = new AccessToken(getString(R.string.accesstoken), getString(R.string.appId), "128841827707620",
                null, null, null, null, null);
        GraphRequestBatch batch = new GraphRequestBatch();
        for (String link : links) {

            batch.add(new GraphRequest(token,
                    link+"/videos?fields=source,id,picture,created_time,likes.limit(0).summary(true),description,title"
                    , null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    Video video;
                    String picture="",source="",title="",description="",id="",created_time="";
                    try {
                       // Log.d("asd",response.toString());
                        for (int i =0; i < response.getJSONObject().getJSONArray("data").length();i++) {
                            JSONObject js = response.getJSONObject().getJSONArray("data").getJSONObject(i);
                            //picture=js.getJSONObject("thumbnails").getJSONArray("data").getJSONObject(0).getString("uri");
                            picture = (js.has("picture") && !js.isNull("picture")) ? js.getString("picture") : "";
                            source = (js.has("source") && !js.isNull("source")) ? js.getString("source") : "";
                            title = (js.has("title") && !js.isNull("title")) ? js.getString("title") : "";
                            description = (js.has("description") && !js.isNull("description")) ? js.getString("description") : "";
                            id = (js.has("id") && !js.isNull("id")) ? js.getString("id") : "";
                            created_time = (js.has("created_time") && !js.isNull("created_time")) ? js.getString("created_time") : "";
                            //String summary = js.getString("summary");

                            video = new Video(source, description, title, id, picture, created_time, "");

                            videos.add(video);
                        }
                       // String linkNext = response.getJSONObject().getJSONObject("paging").getString("next");


                    } catch (Exception ex) {
                        Log.d("asd",ex.toString());
                    }
                    new Handler().post(new Runnable() {
                        @Override
                        public void run() {
                            mAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        }
                    });

                }
            }));
        }
        batch.addCallback(new GraphRequestBatch.Callback() {
            @Override
            public void onBatchCompleted(GraphRequestBatch graphRequests) {
                Collections.sort(videos, new Comparator<Video>() {
                    @Override
                    public int compare(Video video2, Video video1)
                    {
                        return  video1.getCreated_date().compareTo(video2.getCreated_date());
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

        DialogSettings dS = new DialogSettings();
        // Show Alert DialogFragment
        dS.show(fm, "Alert Dialog Fragment");

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
        if(!isClosed){
            saveChanges();
            isClosed = true;
        }
    }
    @Override
    public void onStop()
    {
        super.onStop();
        if(!isClosed){
            saveChanges();
            isClosed = true;
        }
    }
    boolean isClosed = false;

    public void saveChanges(){
        Log.d("asd","destriy");
        //if(isClosed) return;
        try {  // Do something else
            outputStream = openFileOutput("data.txt", Context.MODE_PRIVATE);
            outputStream.write("".getBytes());
            for (int i = 0; i < allPages.size(); i++) {
                outputStream.write((allPages.get(i) + "#" + checked.get(i)
                        + "\n").getBytes());
                Log.d("asd", allPages.get(i) + "#" + checked.get(i) + "\n");
            }
            outputStream.close();
            links.clear();
            allPages.clear();
        }catch (Exception ex)
        {
            Log.d("asd",ex.toString());
        }
        //isClosed = true;
    }

}