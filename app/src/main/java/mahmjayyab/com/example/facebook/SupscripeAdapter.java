package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
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
        Log.d("ccc",page.getPagePic());
        holder.mpageNameTextView.setText(page.getPageName());
        //holder.pageName.setText(page.getIsSupscripe());

        Picasso.with(mContext).load(page.getPagePic()).into(holder.pagePic);
        Picasso.with(mContext).load(page.getPageCover()).into(holder.pageCover);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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


        public ViewHolder(View v) {
            super(v);
            view = v;

            mpageNameTextView = (TextView) v.findViewById(R.id.title);
            supscripeBtn = (Button) v.findViewById(R.id.supscripe);
            pagePic = (ImageView) v.findViewById(R.id.pagePic);
            pageCover = (ImageView) v.findViewById(R.id.pageCover);


            holders.add(this);

        }

    }



}
