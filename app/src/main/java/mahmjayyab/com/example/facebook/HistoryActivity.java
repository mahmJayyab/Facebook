package mahmjayyab.com.example.facebook;

import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

public class HistoryActivity extends Fragment {
    public static LinkedList<Video> videos = new LinkedList();
    static HistoryAdapter historyAdapter;
    LinearLayoutManager mLayoutManager;
    static ArrayList<Video> visibleVideos = new ArrayList<>();
    static RecyclerView mRecyclerView;
    ProgressBar progressBar;
    TextView emptyText;
    int lastIndex;
    ArrayList<String> existIds = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_history, container, false);
        Log.d("ggg","History");
        Log.d("accc",rootView+"");



        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        emptyText = (TextView) rootView.findViewById(R.id.empty);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(rootView.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        //edit her visble video
        historyAdapter = new HistoryAdapter(videos, rootView.getContext());
        mRecyclerView.setAdapter(historyAdapter);


        //When watch video more than one time >> up to the history one time
        existIds.add(0,"non");
        if(videos.isEmpty()) {
            Log.d("asd","NullHis");
            progressBar.setVisibility(View.GONE);
        }
        if(!videos.isEmpty()) {
            progressBar.setVisibility(View.GONE);
            emptyText.setVisibility(View.GONE);
        }else
            emptyText.setVisibility(View.VISIBLE);

        getVideos();
        return rootView;
    }


    public void getVideos()
    {
        videos.clear();
        Cursor res1 = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_HISTORY);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String title = res1.getString(2);
            String source = res1.getString(3);
            String picture = res1.getString(4);
            String fav = res1.getString(5);
            String pagePic = res1.getString(6);
            String likes = res1.getString(7);
            String descripyion = res1.getString(8);
            Log.d("asd", pageName + "\t" + title);
            Video video = new Video(pageName, title, source, picture,fav ,pagePic ,likes,descripyion);
            videos.addFirst(video);
            Log.d("aa",video.getTitle()+"    "+ video.getPage_pic() +"   "+ video.getLikes()+"   "+ "History Activity");
        }
        historyAdapter.notifyDataSetChanged();

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser)
        {
            Log.d("ggg","h");
            Main2Activity.clearFavorite.setVisibility(View.GONE);
            Main2Activity.clearHistory.setVisibility(View.VISIBLE);
            Main2Activity.addPage.setVisibility(View.GONE);
            Main2Activity.clearHistory.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("ggg","Hhhhh");
                    clearHistory();


                }
            });
        }
    }
   public void clearHistory(){
       visibleVideos.clear();
       videos.clear();
       emptyText.setVisibility(View.VISIBLE);
       mRecyclerView.setAdapter(historyAdapter);
       MainActivity.myDb.deleteDataAll(DatabaseHelper.TABLE_HISTORY);


    }

}
