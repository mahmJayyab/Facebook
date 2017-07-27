package mahmjayyab.com.example.facebook;


import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mahmo on 7/9/2017.
 */

public class FavoriteActivity extends Fragment {

    public static LinkedList<Video> videos = new LinkedList();
    FavoriteAdapter favoriteAdapter;
    LinearLayoutManager mLayoutManager;
    ArrayList<Video> visibleVideos = new ArrayList<>();
    RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView emptyText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //super.onCreate(bundle);
        //setContentView(R.layout.activity_history);
        View rootView = inflater.inflate(R.layout.activity_history, container, false);
        Log.d("ggg","Favour");
        videos.clear();
        Cursor res1 = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_FAV);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String title = res1.getString(2);
            String source = res1.getString(3);
            String picture = res1.getString(4);
            String fav = res1.getString(5);
            String pagePic = res1.getString(6);
            String likes = res1.getString(7);
            String descripyion = res1.getString(8);
            Video video = new Video(pageName, title, source, picture ,fav,pagePic ,likes,descripyion);
            videos.addFirst(video);

        }



        //Clear history
        /*FloatingActionButton clear = (FloatingActionButton) rootView.findViewById(R.id.clear);

        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                visibleVideos.clear();
                videos.clear();
                // VideoAdapter.hv.clear();
                MainActivity.myDb.deleteDataAll(DatabaseHelper.TABLE_FAV);
                mRecyclerView.setAdapter(favoriteAdapter);

            }
        });*/


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        emptyText = (TextView) rootView.findViewById(R.id.empty);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //edit her visble video
        favoriteAdapter = new FavoriteAdapter(videos, rootView.getContext());
        mRecyclerView.setAdapter(favoriteAdapter);



        //When watch video more than one time >> up to the history one time
        if(!videos.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
        }else {
            emptyText.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.GONE);
        }
        return  rootView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {   Log.d("ggg","f");
            Main2Activity.clearHistory.setVisibility(View.GONE);
            Main2Activity.addPage.setVisibility(View.GONE);
            Main2Activity.clearFavorite.setVisibility(View.VISIBLE);
            Main2Activity.clearFavorite.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("ggg","Ffffff");
                    clearFavoutire();


                }
            });
        }
    }

    public void clearFavoutire(){
        videos.clear();
        // VideoAdapter.hv.clear();
        emptyText.setVisibility(View.VISIBLE);
        mRecyclerView.setAdapter(favoriteAdapter);
        MainActivity.myDb.deleteDataAll(DatabaseHelper.TABLE_FAV);

    }


}

