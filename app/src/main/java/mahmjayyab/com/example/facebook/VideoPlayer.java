package mahmjayyab.com.example.facebook;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.squareup.picasso.Picasso;

public class VideoPlayer extends AppCompatActivity {

    static Video video;
    ProgressDialog pDialog;
    VideoView videoview;
    ImageButton favoButton;
    String isFav = "false";
    static boolean run = true;
    RelativeLayout relativeLayout;
    LinearLayout linearLayout;
    FrameLayout.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        linearLayout= (LinearLayout) findViewById(R.id.extra);
        inst();


        videoview.start();
        videoview.getCurrentPosition();

            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                Log.d("test", "gog");
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
                //videoview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

            } else {
                Log.d("test", "wp");
                relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));

            }


            // Set progressbar title
            /*pDialog.setTitle(v.getTitle());
            // Set progressbar message
            pDialog.setMessage("Buffering...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            // Show progressbar
            pDialog.show();*/


            /*videoview.requestFocus();
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {

                    //pDialog.dismiss();
                    //videoview.start();

                }
            });*/


    }
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("fff","enter");

        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
           relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
           // videoview.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
            Toast.makeText(this, "landscape", Toast.LENGTH_SHORT).show();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){
            relativeLayout.setLayoutParams(new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
            Toast.makeText(this, "portrait", Toast.LENGTH_SHORT).show();
        }
    }

    void inst(){
        Video v = video;
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask
        favoButton = (ImageButton) findViewById(R.id.fav_button);
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

        Cursor res = MainActivity.myDb.getData(DatabaseHelper.TABLE_FAV, video.getTitle());
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

            Uri video = Uri.parse(v.getSource());
            videoview.setMediaController(mediacontroller);
            videoview.setVideoURI(video);

        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            e.printStackTrace();
        }


        favoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.myDb.deleteData(video.getTitle(), DatabaseHelper.TABLE_FAV);
                if (isFav.equals("false")) {
                    isFav = "true";
                    MainActivity.myDb.insertDataFave(video.getPageName(), video.getTitle(), video.getSource(), video.getPicture(), "true");
                    favoButton.setImageResource(R.drawable.ic_star_black_24dp);

                } else {
                    isFav = "false";
                    //MainActivity.myDb.insertDataFave(video.getPageName(),video.getTitle(),video.getSource(),video.getPicture(),"false");
                    favoButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }

            }
        });
    }
}
