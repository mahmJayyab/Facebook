package mahmjayyab.com.example.facebook;

import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mahmo on 7/9/2017.
 */

public class SupscripeActivity extends AppCompatActivity {
    public static LinkedList<Pages> pages = new LinkedList();
    SupscripeAdapter historyAdapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    FragmentManager fm = getSupportFragmentManager();
    static ProgressBar progressBar;

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setContentView(R.layout.activity_supscripe);
        pages.clear();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        Log.d("ccc","test");
        for(int i=0; i<MainActivity.pages.size();i++){
            pages.addFirst(MainActivity.pages.get(i));
        }
        Log.d("ss",MainActivity.pages.size()+"");
       /* Cursor res1 = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_NAME);
        while (res1.moveToNext()) {
            String pageName = res1.getString(1);
            String isSupscripe = res1.getString(2);
            String pagePic = res1.getString(3);
            String pageCover = res1.getString(4);
            //Pages page = new Pages(pageName, isSupscripe, pagePic, pageCover);
           // pages.add(page);

        }*/


        FloatingActionButton addNewPage = (FloatingActionButton) findViewById(R.id.add);

        addNewPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDFragment alertdFragment = new AlertDFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");

            }
        });


        mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);


        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //edit her visble video
        historyAdapter = new SupscripeAdapter(pages, this);
        mRecyclerView.setAdapter(historyAdapter);



        //existIds.add(0,"non");
        /*if(pages.isEmpty()) {
            Log.d("asd","NullHis");
            progressBar.setVisibility(View.GONE);
        }*/

        if(!pages.isEmpty())
        progressBar.setVisibility(View.GONE);


    }

}
