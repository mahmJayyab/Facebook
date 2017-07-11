package mahmjayyab.com.example.facebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mahmo on 7/9/2017.
 */

public class HistoryActivity extends AppCompatActivity {
    public static LinkedList<Video> videos = new LinkedList();
    HistoryAdapter historyAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> visibleVideos = new ArrayList<>();
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    int lastIndex;
    ArrayList<String> existIds = new ArrayList<>();
    FragmentManager fm = getSupportFragmentManager();
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_history);
        videos.clear();
        Cursor res1 = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_HISTORY);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String title = res1.getString(2);
            String source = res1.getString(3);
            String picture = res1.getString(4);
            Log.d("asd", pageName + "\t" + title);
            Video video = new Video(pageName, title, source, picture);
            videos.addFirst(video);
            Log.d("aa", videos + " Main");
            Log.d("aa", video.getPageName() + "556565");
        }

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
       // toggle.syncState();

        //Clear history
        FloatingActionButton clear = (FloatingActionButton) findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                visibleVideos.clear();
                videos.clear();
               // VideoAdapter.hv.clear();
                MainActivity.myDb.deleteDataAll();
                mRecyclerView.setAdapter(historyAdapter);

                // I did it haha

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //edit her visble video
        historyAdapter = new HistoryAdapter(videos, this);
        mRecyclerView.setAdapter(historyAdapter);

        /*mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int totalItemCount = mLayoutManager.getItemCount();
                int lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
                Log.d("asd", totalItemCount + ":" + lastVisibleItem);
                if (totalItemCount - 1 == lastVisibleItem) {
                    add10();
                }
            }
        });*/

        //When watch video more than one time >> up to the history one time
        existIds.add(0,"non");
        if(videos.isEmpty()) {
            Log.d("asd","NullHis");
            progressBar.setVisibility(View.GONE);
        }
       /* for (Video video : VideoAdapter.hv) {

                videos.add(video);
            }*/
           // existIds.add(video.getId());

        //add10();
        if(!videos.isEmpty())
        progressBar.setVisibility(View.GONE);
    }

   /* public void add10() {
        Log.d("asd", lastIndex + "::" + videos.size());
        int max = Math.min(lastIndex +15 , videos.size());
        for (int i = lastIndex; i < max; i++) {
            visibleVideos.add(videos.get(i));
        }
        lastIndex += 15;
    }*/
}
