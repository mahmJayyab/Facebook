package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class SupscripeAdapter extends RecyclerView.Adapter<SupscripeAdapter.ViewHolder> {

    List<Pages> pages;
    Context mContext;
    public static String id;


    public SupscripeAdapter(List<Pages> Pages, Context mContext) {
        this.pages = Pages;
        this.mContext = mContext;
        holders = new ArrayList<>();
    }

    @Override
    public SupscripeAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.page_item, parent, false);
        //view.setId(R.id.videoView);


        return new ViewHolder(view);
    }

    static List<ViewHolder> holders;


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
          final Pages page = pages.get(position);
        //Log.d("ccc",page.getPagePic());
        Log.d("ccc",page.getPageName() +"  "+position);
        holder.mpageNameTextView.setText(page.getPageName());

        //holder.pageName.setText(page.getIsSupscripe());
        if(page.getIsSupscripe().equals("true")){
            holder.supscripeBtn.setText("Supscribed");
            holder.supscripeBtn.setTextColor(Color.rgb(181,195,250));
            //holder.supscripeBtn.setBackgroundColor(Color.rgb(181,195,250));
        }
        else{
            holder.supscripeBtn.setText("Supscribe");
            holder.supscripeBtn.setTextColor(Color.rgb(84,108,202));
           // holder.supscripeBtn.setBackgroundColor(Color.rgb(84,108,202));
        }

        Picasso.with(mContext).load(page.getPagePic()).into(holder.pagePic);
        Picasso.with(mContext).load(page.getPageCover()).into(holder.pageCover);
        //here
        holder.deletePageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                page.setIsSupscripe("false");
               // holder.pageCard.setVisibility(View.GONE);
                MainActivity.myDb.deleteData_P(page.getPageName(),DatabaseHelper.TABLE_NAME);
                SupscripeActivity.mRecyclerView.setAdapter(SupscripeActivity.historyAdapter);
                SupscripeActivity.ft.detach(SupscripeActivity.conttt).attach(SupscripeActivity.conttt).commit();
               // holder.pageCard.setVisibility(View.GONE);
            }
        });
       /* holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/
        holder.supscripeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


            if(page.getIsSupscripe().equals("true")){
                page.setIsSupscripe("false");
                MainActivity.myDb.deleteData_P(page.getPageName(),DatabaseHelper.TABLE_NAME);
                MainActivity.myDb.insertData_Pages(page.getPageName(), "false",page.getPagePic(),page.getPageCover(),page.getLink());
                holder.supscripeBtn.setText("Supscribe");
                holder.supscripeBtn.setTextColor(Color.rgb(84,108,202));

                //didnt worked
               // holder.supscripeBtn.setBackgroundColor(Color.rgb(84,108,202));
            }
            else{
                page.setIsSupscripe("true");
                MainActivity.myDb.deleteData_P(page.getPageName(),DatabaseHelper.TABLE_NAME);
                MainActivity.myDb.insertData_Pages(page.getPageName(), "true",page.getPagePic(),page.getPageCover(),page.getLink());
                holder.supscripeBtn.setText("Supscribed");
                holder.supscripeBtn.setTextColor(Color.rgb(181,195,250));

                //didnt worked
                //holder.supscripeBtn.setBackgroundColor(Color.rgb(181,195,250));
            }
            }
        });
    }

    @Override
    public int getItemCount() {
        return pages.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mpageNameTextView;
        public Button supscripeBtn;
        public double id;
        public ImageView pagePic;
        public ImageView pageCover;
        public View view;
        public ImageButton deletePageBtn;
        public LinearLayout pageCard;

        public ViewHolder(View v) {
            super(v);
            view = v;

            mpageNameTextView = (TextView) v.findViewById(R.id.title);
            supscripeBtn = (Button) v.findViewById(R.id.supscripe);
            pagePic = (ImageView) v.findViewById(R.id.pagePic);
            pageCover = (ImageView) v.findViewById(R.id.pageCover);
            pageCard = (LinearLayout)view.findViewById(R.id.pageCard);
            deletePageBtn = (ImageButton)view.findViewById(R.id.deletePageBtn);


            holders.add(this);

        }

    }

}
