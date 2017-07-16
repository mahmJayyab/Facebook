package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
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

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Created by mahmo on 7/9/2017.
 */

public class SupscripeActivity extends Fragment {
    public static LinkedList<Pages> pages = new LinkedList();
    SupscripeAdapter historyAdapter;
    LinearLayoutManager mLayoutManager;
    RecyclerView mRecyclerView;
    View rootView;
    FragmentManager fm;
    Context cont;
    static ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_main, container, false);
        fm =getChildFragmentManager();
        cont = rootView.getContext();
        pages.clear();
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        Log.d("ccc","test");
        Cursor res = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_NAME);
        while (res.moveToNext()) {
            String id = res.getString(0);
            String pageLink = res.getString(1);
            String temp = res.getString(2);
            String pagePic = res.getString(3);
            String pageCover = res.getString(4);
            String pageName = res.getString(5);
            Pages page = new Pages(pageLink,temp,pagePic,pageCover,pageName);
            pages.addFirst(page);
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


       /* FloatingActionButton addNewPage = (FloatingActionButton) rootView.findViewById(R.id.add);
        Log.d("ffff",addNewPage+"");
        addNewPage.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                AlertDFragment alertdFragment = new AlertDFragment();
                alertdFragment.show(fm, "Alert Dialog Fragment");

            }
        });*/


        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);


        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(cont);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //edit her visble video
        historyAdapter = new SupscripeAdapter(pages, cont);
        mRecyclerView.setAdapter(historyAdapter);



        //existIds.add(0,"non");
        /*if(pages.isEmpty()) {
            Log.d("asd","NullHis");
            progressBar.setVisibility(View.GONE);
        }*/

        if(!pages.isEmpty())
        progressBar.setVisibility(View.GONE);

        return rootView;
    }

}