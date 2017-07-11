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

public class FavoriteActivity extends AppCompatActivity {

    public static LinkedList<Video> videos = new LinkedList();
    FavoriteAdapter favoriteAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> visibleVideos = new ArrayList<>();
    RecyclerView mRecyclerView;
    ProgressBar progressBar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_history);

        videos.clear();
        Cursor res1 = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_FAV);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String title = res1.getString(2);
            String source = res1.getString(3);
            String picture = res1.getString(4);

            Video video = new Video(pageName, title, source, picture);
            videos.addFirst(video);

        }



        //Clear history
        FloatingActionButton clear = (FloatingActionButton) findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                visibleVideos.clear();
                videos.clear();
               // VideoAdapter.hv.clear();
                MainActivity.myDb.deleteDataAll(DatabaseHelper.TABLE_FAV);
                mRecyclerView.setAdapter(favoriteAdapter);

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //edit her visble video
        favoriteAdapter = new FavoriteAdapter(videos, this);
        mRecyclerView.setAdapter(favoriteAdapter);



        //When watch video more than one time >> up to the history one time
        if(videos.isEmpty()) {
            Log.d("asd","NullHis");
            progressBar.setVisibility(View.GONE);
        }

        if(!videos.isEmpty())
        progressBar.setVisibility(View.GONE);
    }


}
