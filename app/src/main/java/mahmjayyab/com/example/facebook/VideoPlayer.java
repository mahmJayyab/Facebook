package mahmjayyab.com.example.facebook;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class VideoPlayer extends AppCompatActivity {

    static Video video;
    ProgressDialog pDialog;
    VideoView videoview;
    //StretchVideoView sfv;
    ImageButton favoButton;
    String isFav = "false";
    static boolean run = true;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    FrameLayout.LayoutParams layoutParams;
    ProgressBar progressBar;
    ImageButton fullScreen;
    ImageButton exitFullScreen;
    ImageButton smallVideoBtn;
    Video v;
    Uri videoLink;
    int height;
    static int postion;
    static boolean setPos = false;
    boolean videoEnd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        videoEnd = false;
        linearLayout= (LinearLayout) findViewById(R.id.extra);
        progressBar = (ProgressBar) findViewById(R.id.progress);
        inst();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;



            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d("test", "gog");
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
                exitFullScreen.setVisibility(View.VISIBLE);
                fullScreen.setVisibility(View.GONE);
                //videoview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            } else {
                Log.d("test", "wp");
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/3));

                exitFullScreen.setVisibility(View.GONE);
                fullScreen.setVisibility(View.VISIBLE);
            }


            // Set progressbar title
           /* pDialog.setTitle(v.getTitle());
            // Set progressbar message
            pDialog.setMessage("Loading ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // Show progressbar
            pDialog.show();*/
            progressBar.setVisibility(View.VISIBLE);

            videoview.requestFocus();
            if(setPos){
                videoview.seekTo(postion);
                setPos = false;
            }
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {
                    //pDialog.dismiss();
                    progressBar.setVisibility(View.GONE);
                    videoview.start();
                    mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
                        @Override
                        public boolean onInfo(MediaPlayer mp, int what, int extra) {
                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_START)
                                progressBar.setVisibility(View.VISIBLE);
                            if (what == MediaPlayer.MEDIA_INFO_BUFFERING_END)
                                progressBar.setVisibility(View.GONE);
                            return false;
                        }
                    });

                }
            });


    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("fff","enter");

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
           relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.FILL_PARENT));
            exitFullScreen.setVisibility(View.VISIBLE);
            fullScreen.setVisibility(View.GONE);
            // videoview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            Toast.makeText(this, "full screen", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,height/3));
            exitFullScreen.setVisibility(View.GONE);
            fullScreen.setVisibility(View.VISIBLE);
           // Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    void inst(){
         v = video;
        // Find your VideoView in your video_main.xml layout
        videoview =(VideoView) findViewById(R.id.VideoView);


        //videoview.setMinimumHeight(400);
        // Execute StreamVideo AsyncTask
        favoButton = (ImageButton) findViewById(R.id.fav_button);
        fullScreen = (ImageButton) findViewById(R.id.full_button);
        exitFullScreen = (ImageButton) findViewById(R.id.notfull_button);
        smallVideoBtn = (ImageButton) findViewById(R.id.smallVideo_button);
        // Create a progressbar
        pDialog = new ProgressDialog(VideoPlayer.this);
        relativeLayout =(RelativeLayout) findViewById(R.id.relativeLayout1);

        ImageView page_pic = (ImageView) findViewById(R.id.page_image);
        TextView title = (TextView) findViewById(R.id.title);
        TextView pagename = (TextView) findViewById(R.id.page_Name);
        TextView description = (TextView) findViewById(R.id.description);
        TextView likes = (TextView) findViewById(R.id.likes_count);

        Picasso.with(this).load(video.getPage_pic()).into(page_pic);
        title.setText(video.getTitle());
        pagename.setText(video.getPageName());
        description.setText(video.getDescription());
        likes.setText("Likes: " + video.getLikes());

        Cursor res = MainActivity.myDb.getData(DatabaseHelper.TABLE_FAV, video.getPicture());
        while (res.moveToNext()) {
            isFav = res.getString(5);
        }

        if (isFav.equals("true"))
            favoButton.setImageResource(R.drawable.ic_star_black_24dp);
        else
            favoButton.setImageResource(R.drawable.ic_star_border_black_24dp);
        try {
            // Start the MediaController
            MediaController mediacontroller = new MediaController(
                    VideoPlayer.this);
            mediacontroller.setAnchorView(videoview);
            // Get the URL from String VideoURL

            videoLink = Uri.parse(v.getSource());
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(videoLink );

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        favoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.myDb.deleteData(video.getPicture(), DatabaseHelper.TABLE_FAV);
                if (isFav.equals("false")) {
                    isFav = "true";
                    MainActivity.myDb.insertDataFave(video.getPageName(), video.getTitle(), video.getSource(), video.getPicture(), "true",video.getPage_pic(),
                            video.getLikes(),video.getDescription());
                    favoButton.setImageResource(R.drawable.ic_star_black_24dp);

                } else {
                    isFav = "false";
                    //MainActivity.myDb.insertDataFave(video.getPageName(),video.getTitle(),video.getSource(),video.getPicture(),"false");
                    favoButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }

            }
        });

        fullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
                exitFullScreen.setVisibility(View.VISIBLE);
                fullScreen.setVisibility(View.GONE);
            }
        });

        exitFullScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
                exitFullScreen.setVisibility(View.GONE);
                fullScreen.setVisibility(View.VISIBLE);
            }
        });
        smallVideoBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Intent intent = new Intent(VideoPlayer.this,Main2Activity.class);
                //startActivity(intent);

                int pos =videoview.getCurrentPosition();
                Main2Activity.playSmallVideo(videoLink ,pos+1000,video);
                onBackPressed();

            }
        });
        videoview.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {

            @Override
            public void onCompletion(MediaPlayer mp) {
                // not playVideo
                // playVideo();
               videoEnd = true;
            }
        });
    }
    @Override
    public void onBackPressed()
    {
        super.onBackPressed();
        if(!videoEnd) {
            int pos = videoview.getCurrentPosition();
            Main2Activity.playSmallVideo(videoLink, pos + 1000, video);
        }
    }


}
