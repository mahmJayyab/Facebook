package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;

import android.support.design.widget.NavigationView;


import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;

import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.LinkedList;

public class MainActivity extends Fragment
        implements NavigationView.OnNavigationItemSelectedListener {

    public static BufferedReader reader;
    public static ArrayList<String> links = new ArrayList<>();
    public static ArrayList<String> linksPaging = new ArrayList<>();
    public static ArrayList<String> allPages = new ArrayList<>();
    public static ArrayList<Boolean> isSupscripe = new ArrayList<>();
    public static FileOutputStream outputStream;
    public static File file;
    public static DatabaseHelper myDb;
    static int lastVisibleItemIndex = 1;
    FileReader in;
    VideoAdapter mAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> videos = new ArrayList();
    ArrayList<Video> visibleVideos = new ArrayList<>();
    static ArrayList<Pages> pages = new ArrayList<>();
    int lastIndex = 0;
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    Context cont;
    View rootView;
    int idConnt = 1;
    String link;
    String pagePic;
    String pageCove;
    static GraphRequestBatch batch;
    static AccessToken token;
    boolean isClosed = false;
    static Cursor res;
    public static LinkedList<Pages> subPages = new LinkedList<>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);

        links.clear();
        allPages.clear();
        isSupscripe.clear();
        cont =rootView.getContext();
        //creat database
        myDb=new DatabaseHelper(cont);

        //creat cursor to read from database
         res = myDb.getAllData(DatabaseHelper.TABLE_NAME);
        if(res.getCount() == 0) {
            // show message

            allPages.add("MEQBAS");
            isSupscripe.add(true);
            links.add("MEQBAS");
            myDb.insertData_Pages("مقبس - Meqbas","true","https://graph.facebook.com/1860629474208112/picture?type=large"
                    ,"https://scontent.xx.fbcdn.net/v/t1.0-9/s720x720/17903377_1861123910825335_76517357315002598_n.jpg?oh=688a68958a7f9f7355d4fcc3144e1adb&oe=59FD64D2","MEQBAS");
            //myDb.insertData("ajplusarabi","true");
           // myDb.insertData("MEQBAS","true");
            // myDb.insertData("ajplusarabi","true");
            Log.d("asd","Null");
            //res = myDb.getAllData(DatabaseHelper.TABLE_NAME);
        }
        while (res.moveToNext()) {
            String id = res.getString(0);
            String pageLink = res.getString(5);
            String temp = res.getString(2);
            String pagePic = res.getString(3);
            String pageCover = res.getString(4);
            String pageName = res.getString(1);
            Log.d("cccc", id+"  "+ pageName + "\t" + temp);
            //Pages p = new Pages(pageLink,temp,pagePic,pageCover,pageName);
            //subPages.addFirst(p);
            allPages.add(pageLink);
            if (temp.equals("true")) {
                links.add(pageLink);
                isSupscripe.add(true);
            } else {
                isSupscripe.add(false);
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
            Log.d("cccc", pageName + "\t" + title);
            Video video = new Video(pageName,title,source,picture);
            HistoryActivity.videos.addFirst(video);
            Log.d("aa",HistoryActivity.videos+" Main");
            Log.d("aa",video.getPageName()+"556565");
        }

        return rootView;

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
        //Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.toolbar);
        /*rootView.setSupportActionBar(toolbar);

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
        toggle.syncState();*/

        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(cont);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new VideoAdapter(visibleVideos, cont);
        mRecyclerView.setAdapter(mAdapter);

       // NavigationView navigationView = (NavigationView) rootView.findViewById(R.id.nav_view);
       // navigationView.setNavigationItemSelectedListener(this);
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
         token = new AccessToken(getString(R.string.accesstoken), getString(R.string.appId), "128841827707620",
                null, null, null, null, null);
         batch = new GraphRequestBatch();
        if (links.size() != linksPaging.size()) {
            linksPaging.clear();
            for (int i = 0; i < links.size(); i++) {
                linksPaging.add("");
            }
        }
        for (int i = 0; i < links.size(); i++) {
             link = links.get(i);
            Calendar cal = Calendar.getInstance();
            cal.add(Calendar.MONTH, -4);
            String afterQ = "&since=" + (cal.getTimeInMillis() / 1000);
            if (!linksPaging.get(i).isEmpty() ) afterQ = "&after=" + linksPaging.get(i);
            final int linkIndex = i;

            // Change
            Log.d("eee",link+"  linksPagingsize  "+linksPaging.size());
            batch.add(new GraphRequest(token,
                            link + "/videos?fields=from{cover,name},source,id,picture,created_time,likes.limit(0).summary(true),description,title&limit=10" + afterQ
                    , null, HttpMethod.GET, new GraphRequest.Callback() {
                @Override
                public void onCompleted(GraphResponse response) {
                    Video video;
                    String picture = "", source = "", title = "", description = "", id = "", created_time = "",pageName;
                    int index=0;

                    try {
                        JSONObject jsPageName =  response.getJSONObject().getJSONArray("data").getJSONObject(0).getJSONObject("from");
                         pageName = (jsPageName.has("name") && !jsPageName.isNull("name")) ? jsPageName.getString("name") : "";

                        for (int i = 1; i < response.getJSONObject().getJSONArray("data").length(); i++) {
                            JSONObject js = response.getJSONObject().getJSONArray("data").getJSONObject(i);
                            picture = (js.has("picture") && !js.isNull("picture")) ? js.getString("picture") : "";
                            source = (js.has("source") && !js.isNull("source")) ? js.getString("source") : "";
                            title = (js.has("title") && !js.isNull("title")) ? js.getString("title") : "";
                            description = (js.has("description") && !js.isNull("description")) ? js.getString("description") : "";
                            id = (js.has("id") && !js.isNull("id")) ? js.getString("id") : "";
                            created_time = (js.has("created_time") && !js.isNull("created_time")) ? js.getString("created_time") : "";
                            String likes = js.getJSONObject("likes").getJSONObject("summary").getString("total_count");
                            String page_pic_id = js.getJSONObject("from").getString("id");
                            String page_pic = "https://graph.facebook.com/"+page_pic_id+"/picture?type=large";
                            video = new Video(source, description, title, id, picture, created_time, likes, pageName, "false", page_pic);
                            videos.add(video);
                        }
                        linksPaging.set(linkIndex, response.getJSONObject().getJSONObject("paging").getJSONObject("cursors").getString("after"));
                        String idPic = jsPageName.getString("id");

                        String pageName_Orgin = jsPageName.getString("name");
                        pagePic = "https://graph.facebook.com/"+idPic+"/picture?type=large";
                        pageCove= jsPageName.getJSONObject("cover").getString("source");
                        Log.d("asdnewpage", linksPaging.get(linkIndex) + "-" + linkIndex);
                        Log.d("ccc",link +"  "+linkIndex+" "+pagePic+" "+pageCove);
                        //pages.add(linkIndex,new Pages(link,"true",pagePic,pageCove,pageName));
                        //boolean b=myDb.updateDataByID(idConnt+"","true",pagePic,pageCove,pageName);

                        //myDb.deleteData_P(link,DatabaseHelper.TABLE_NAME);
                        //myDb.insertData_Pages(link,"true",pagePic,pageCove,pageName_Orgin);
                        res.moveToPosition(0);
                        while (res.moveToNext()) {
                            Log.d("ccc",idConnt+""+"  id = "+res.getString(0)+" page pic "+res.getString(3)
                                   + " page name "+res.getString(5));
                        }
                        idConnt++;
                        // String linkNext = response.getJSONObject().getJSONObject("paging").getString("next");

                    } catch (Exception ex) {
                        Log.d("ddd", ex.toString());
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
        //res = myDb.getAllData(DatabaseHelper.TABLE_NAME);
    }

 /*   @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }*/

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.activity_main_drawer, menu);
        return true;
    }
*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        Integer test = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent myIntent = new Intent(cont, SupscripeActivity.class);
                myIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(myIntent);
                // Show Alert DialogFragment
                break;
            case R.id.action_history:

                Log.d("asd", cont + "");
                Intent myIntent1 = new Intent(cont, HistoryActivity.class);
                myIntent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(myIntent1);
                break;
            case R.id.action_favorite:

                Log.d("asd", cont + "");
                Intent myIntent2 = new Intent(cont, FavoriteActivity.class);
                myIntent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                cont.startActivity(myIntent2);
                break;

        }
        return super.onOptionsItemSelected(item);
    }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
            // Handle navigation view item clicks here.
            int id = item.getItemId();


            DrawerLayout drawer = (DrawerLayout) rootView.findViewById(R.id.drawer_layout);
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
