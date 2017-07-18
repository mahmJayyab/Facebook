package mahmjayyab.com.example.facebook;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);
        Log.d("test","start");
        Cursor res = MainActivity.myDb.getData(DatabaseHelper.TABLE_FAV,video.getTitle());
        while (res.moveToNext()){
            isFav = res.getString(5);
        }
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            Log.d("test","gog");
        }
        else {
            Log.d("test","wp");
        }

       Video v = video;
        // Find your VideoView in your video_main.xml layout
        videoview = (VideoView) findViewById(R.id.VideoView);
        // Execute StreamVideo AsyncTask
        favoButton =(ImageButton) findViewById(R.id.fav_button);
        // Create a progressbar
        pDialog = new ProgressDialog(VideoPlayer.this);
        // Set progressbar title
        pDialog.setTitle(v.getTitle());
        // Set progressbar message
        pDialog.setMessage("Buffering...");
        pDialog.setIndeterminate(false);
        pDialog.setCancelable(false);
        // Show progressbar
        pDialog.show();
        ImageView page_pic = (ImageView) findViewById(R.id.page_image);
        TextView title = (TextView) findViewById(R.id.title);
        TextView pagename = (TextView) findViewById(R.id.page_Name);
        TextView description = (TextView) findViewById(R.id.description);
        TextView likes = (TextView) findViewById(R.id.likes_count);

        Picasso.with(this).load(video.getPage_pic()).into(page_pic);
        title.setText(video.getTitle());
        pagename.setText(video.getPageName());
        description.setText(video.getDescription());
        likes.setText("Likes: "+video.getLikes());
        if(isFav.equals("true"))
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
                MainActivity.myDb.deleteData(video.getTitle(),DatabaseHelper.TABLE_FAV);
                if(isFav.equals("false")){
                     isFav="true";
                    MainActivity.myDb.insertDataFave(video.getPageName(),video.getTitle(),video.getSource(),video.getPicture(),"true");
                    favoButton.setImageResource(R.drawable.ic_star_black_24dp);

                }
                else{
                    isFav="false";
                    //MainActivity.myDb.insertDataFave(video.getPageName(),video.getTitle(),video.getSource(),video.getPicture(),"false");
                    favoButton.setImageResource(R.drawable.ic_star_border_black_24dp);
                }

            }
        });

        videoview.requestFocus();
        if(!videoview.isPlaying()) {
            videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                // Close the progress bar and play the video
                public void onPrepared(MediaPlayer mp) {

                    pDialog.dismiss();
                    videoview.start();

                }
            });
        }
    }

}
