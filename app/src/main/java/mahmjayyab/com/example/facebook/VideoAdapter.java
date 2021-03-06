package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;



public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    public static ArrayList<Video> historyVideos;
    static List<ViewHolder> holders;
    List<Video> videos;
    Context mContext;
    int index = 0;
    int hourI;
    String hourS;
    int mintI;
    String mintS;
    String timezone;
    boolean firstTime;

   // public static LinkedList<Video> hv = new LinkedList<>();
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

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Video video = videos.get(position);
        holder.mTitleTextView.setText(video.getTitle());
        if(video.getCreated_date().getHours() >12) {
            hourI = video.getCreated_date().getHours()-12;
            timezone = "PM";
        }else  timezone = "AM";
        if(hourI<10)hourS = "0"+hourI;
        else hourS = ""+hourI;
        mintI = video.getCreated_date().getMinutes();
        if(mintI < 10)mintS = "0"+ mintI ;
        else mintS = ""+mintI;
        holder.date.setText(video.getCreated_date().getDay()+"/"+video.getCreated_date().getMonth()+"/"+(video.getCreated_date().getYear()+1900+"  "+hourS+":"+mintS+" "+timezone));
        holder.time.setVisibility(View.GONE);
        holder.mDescriptionTextView.setText(video.getDescription());
        Picasso.with(mContext).load(video.getPicture()).into(holder.imageView);
        final MediaController mediaController = new MediaController(mContext);
        holder.videoView.setVideoPath(video.getSource());
        holder.videoView.setMediaController(mediaController);
        mediaController.setAnchorView(holder.videoView);

        holder.mpageNameTextView.setText(video.getPageName());
        Picasso.with(mContext).load(video.getPage_pic()).into(holder.pagePic);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO:ADD your Intent;
                Main2Activity.smallVideoLayout.setVisibility(View.GONE);
                Main2Activity.smallVideo.setVisibility(View.GONE);
                VideoPlayer.video = video;
                boolean done = true;

                Log.d("aa",video.getPage_pic()+"  "+video.getLikes()+"  "+"vdieo Addpter");
                //How !!
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
        public TextView mDescriptionTextView;
        public ImageView imageView;
        public View view;
        public VideoView videoView;
        public TextView mpageNameTextView;
        public ImageView pagePic;
        public TextView date;
        public TextView time;

        public ProgressBar progressBar;
        //public ImageButton imageButton;
        public int spec;
        public ViewHolder(View v) {
            super(v);
            view = v;
            spec = 1;
            mTitleTextView = (TextView) v.findViewById(R.id.title);
            date = (TextView)  v.findViewById(R.id.date);
            time = (TextView) v.findViewById(R.id.time);
            mDescriptionTextView = (TextView) v.findViewById(R.id.description);
            imageView = (ImageView) v.findViewById(R.id.imageView);
            videoView = (VideoView) v.findViewById(R.id.videoView);
            mpageNameTextView = (TextView) v.findViewById(R.id.pageName);
            pagePic = (ImageView) v.findViewById(R.id.pagePic);
            //progressBar = (ProgressBar) v.findViewById(R.id.progressbar);
           // imageButton = (ImageButton) v.findViewById(R.id.play_button);

            holders.add(this);

        }

    }



}
