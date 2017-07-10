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
import java.util.LinkedList;
import java.util.List;



public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    List<Video> videos;
    Context mContext;
    int index = 0;
    public static ArrayList<Video> historyVideos;
    public static LinkedList<Video> hv = new LinkedList<>();
    public VideoAdapter(List<Video> videos, Context mContext) {
        this.videos = videos;
        this.mContext = mContext;
        holders = new ArrayList<>();
    }

    @Override
    public VideoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.video_item, parent, false);
        //view.setId(R.id.videoView);
        historyVideos = new ArrayList<>();
        return new ViewHolder(view);
    }

    static List<ViewHolder> holders;
    boolean firstTime;

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.mTitleTextView.setText(video.getTitle());
        holder.mDescriptionTextView.setText(video.getDescription());
        Picasso.with(mContext).load(video.getPicture()).into(holder.imageView);
        final MediaController mediaController = new MediaController(mContext);
        holder.videoView.setVideoPath(video.getSource());

        holder.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(holder.videoView);



        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:ADD your Intent;
                VideoPlayer.video = video;
                hv.addFirst(video);
                //historyVideos.add(0,video);
                /*for(int i= 0;i < historyVideos.size();i++){
                    historyVideos.add(i+1,historyVideos.get(i));
                }*/
                Intent myIntent = new Intent(mContext, VideoPlayer.class);
               // myIntent.putExtra("key", value); //Optional parameters
                mContext.startActivity(myIntent);
                //CurrentActivity.this.startActivity(myIntent);


            }
        });
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitleTextView;
        public TextView mDescriptionTextView;
        public ImageView imageView;
        public View view;
        public VideoView videoView;
        public ProgressBar progressBar;
        //public ImageButton imageButton;
        public int spec;
        public ViewHolder(View v) {
            super(v);
            view = v;
            spec = 1;
            mTitleTextView = (TextView) v.findViewById(R.id.title);
            mDescriptionTextView = (TextView) v.findViewById(R.id.description);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            videoView = (VideoView) v.findViewById(R.id.videoView);
            progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
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