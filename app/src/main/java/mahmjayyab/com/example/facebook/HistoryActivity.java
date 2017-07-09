package mahmjayyab.com.example.facebook;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by mahmo on 7/9/2017.
 */

public class HistoryActivity extends AppCompatActivity {
    HistoryAdapter historyAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> videos = new ArrayList();
    ArrayList<Video> visibleVideos = new ArrayList<>();
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    int lastIndex;
    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_history);

        //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
       // setSupportActionBar(toolbar);

        //DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                //this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        //drawer.setDrawerListener(toggle);
       // toggle.syncState();

        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        historyAdapter = new HistoryAdapter(visibleVideos, this);
        mRecyclerView.setAdapter(historyAdapter);

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        });

        for (Video video : VideoAdapter.hv) {

            videos.add(video);

        }
        add10();
        if(!videos.isEmpty())
        progressBar.setVisibility(View.GONE);
    }

    public void add10() {
        Log.d("asd", lastIndex + "::" + videos.size());
        int max = Math.min(lastIndex +5 , videos.size());
        for (int i = lastIndex; i < max; i++) {
            visibleVideos.add(videos.get(i));
        }
        lastIndex += 5;
    }
}
