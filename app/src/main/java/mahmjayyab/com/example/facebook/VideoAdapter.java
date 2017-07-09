package mahmjayyab.com.example.facebook;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;


public class VideoAdapter extends RecyclerView.Adapter<VideoAdapter.ViewHolder> {


    public static ArrayList<Video> historyVideos;
    public static LinkedList<Video> hv = new LinkedList<>();
    static List<ViewHolder> holders;
    List<Video> videos;
    Context mContext;
    int index = 0;
    boolean firstTime;

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

        try {
            File history_videos_file = new File(mContext.getFilesDir(), "history.txt");
            FileOutputStream out = new FileOutputStream(history_videos_file);
            //out.write(("الاثاث الذكي|مقبس - Meqbas"+"|https://video.xx.fbcdn.net/v/t43.1792-2/19874580_252527078569826_5361061552772349952_n.mp4?efg=eyJybHIiOjE1MDAsInJsYSI6MTAyNCwidmVuY29kZV90YWciOiJzdmVfaGQifQ%3D%3D&rl=1500&vabr=437&oh=ec3a33ff309d041b4864198fed069a4e&oe=596447AD|https://fb-s-a-a.akamaihd.net/h-ak-fbx/v/t15.0-10/p128x128/19983168_1906094842994908_5479280862602199040_n.jpg?oh=078916f9293398e5d58ec06487f40444&oe=5A04C35D&__gda__=1510498972_adcaaf0091708a8db21e620ecc63760a").getBytes());
            BufferedReader reader = new BufferedReader(new FileReader(MainActivity.history_videos_file));
            String link;
            Scanner s;
            Log.d("add", reader.readLine());
            while ((link = reader.readLine()) != null) {
                s = new Scanner(link).useDelimiter("|");
                String pName = s.next();
                String pTitle = s.next();
                String pSource = s.next();
                String pic = s.next();
                Video video = new Video(pSource, "", pTitle, "", pic, "", "", pName);
                hv.addFirst(video);

                Log.d("add", "2" + video.toString());
            }
        } catch (Exception ex) {
            Log.d("add", ex.toString());
        }
        historyVideos = new ArrayList<>();
        return new ViewHolder(view);
    }

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