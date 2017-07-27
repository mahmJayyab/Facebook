package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {


    List<Video> videos;
    Context mContext;
    public static String id;

    public FavoriteAdapter(List<Video> videos, Context mContext) {
        this.videos = videos;
        this.mContext = mContext;
        holders = new ArrayList<>();
    }

    @Override
    public FavoriteAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                         int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);

        return new ViewHolder(view);
    }

    static List<ViewHolder> holders;


    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.mTitleTextView.setText(video.getTitle());
        holder.pageName.setText(video.getPageName());
        id = video.getId();
        Picasso.with(mContext).load(video.getPicture()).into(holder.imageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Main2Activity.smallVideoLayout.setVisibility(View.GONE);
                Main2Activity.smallVideo.setVisibility(View.GONE);
                VideoPlayer.video = video;

                MainActivity.myDb.deleteData(video.getPicture(),DatabaseHelper.TABLE_HISTORY);
                MainActivity.myDb.insertData(video.getPageName(), video.getTitle(), video.getSource(), video.getPicture(),video.getFavorite(),video.getPage_pic(),
                        video.getLikes(),video.getDescription());

                Intent myIntent = new Intent(mContext, VideoPlayer.class);

                mContext.startActivity(myIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView pageName;
        public double id;
        public ImageView imageView;
        public View view;


        public ViewHolder(View v) {
            super(v);
            view = v;

            mTitleTextView = (TextView) v.findViewById(R.id.title_history);
            pageName = (TextView) v.findViewById(R.id.pageName_history);
            imageView = (ImageView) v.findViewById(R.id.imageView_history);

            holders.add(this);

        }

    }

}
