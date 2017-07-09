package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;


public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.ViewHolder> {


    List<Video> videos;
    Context mContext;
    public static String id;

    public HistoryAdapter(List<Video> videos, Context mContext) {
        this.videos = videos;
        this.mContext = mContext;
        holders = new ArrayList<>();
    }

    @Override
    public HistoryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                        int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.history_item, parent, false);
        //view.setId(R.id.videoView);

        return new ViewHolder(view);
    }

    static List<ViewHolder> holders;
    boolean firstTime;

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
        //public VideoView videoView;
        //public ProgressBar progressBar;
        //public ImageButton imageButton;

        public ViewHolder(View v) {
            super(v);
            view = v;

            mTitleTextView = (TextView) v.findViewById(R.id.title_history);
            pageName = (TextView) v.findViewById(R.id.pageName_history);
            imageView = (ImageView) v.findViewById(R.id.imageView_history);
            //videoView = (VideoView) v.findViewById(R.id.videoView);
           // progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
           // imageButton = (ImageButton) v.findViewById(R.id.play_button);

            holders.add(this);

        }

    }



}


/*holder.view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN: {
                        Log.d("item","Yes");
                        /*mediaController.hide();
                        showController = false;
                        if(mediaController.isActivated()){

                            Log.d("item","YesShowing");
                            mediaController.hide();
                        }

                        break;
                    }
                    default:
                    {
                        //showController = true;
                        Log.d("item","NO");
                    }
                }

                return false;
            }
        });*/