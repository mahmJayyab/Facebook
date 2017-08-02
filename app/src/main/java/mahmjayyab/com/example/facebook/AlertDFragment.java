package mahmjayyab.com.example.facebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.GraphRequest;
import com.facebook.GraphRequestBatch;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Calendar;

public class AlertDFragment extends DialogFragment {
    public static  String pageName;
    boolean b = true;
    String afterQ;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(getActivity());

        input.setHint("https://www.facebook.com/PageName");
        input.setText("https://www.facebook.com/");
        return new AlertDialog.Builder(getActivity())

                .setTitle("ِAdd Facebook Page Link")
                .setView(input)

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String link = input.getText().toString();
                        if(link.startsWith("https://www.facebook.com")||link.startsWith("https://m.facebook.com")||link.startsWith("www.facebook.com")){
                            String p = link.substring(link.indexOf(".com/")+5);
                            String temp = "";
                            if (p.indexOf("/") != -1) {
                                temp = p.substring(0,p.indexOf("/"));
                            } else {
                                temp = p;
                            }
                             b = true;
                            Cursor res = MainActivity.myDb.getAllData(DatabaseHelper.TABLE_NAME);
                            while (res.moveToNext()) {
                                String pageName = res.getString(1);
                                if(pageName.equals(temp) ){
                                    b=false;
                                    Log.d("ccc","false");
                                }
                            }
                            Log.d("yyy",temp);
                           pageName = temp;
                            Calendar cal = Calendar.getInstance();
                            afterQ = "&since=" + (cal.getTimeInMillis() / 1000);
                            GraphRequest request = GraphRequest.newGraphPathRequest(
                                    MainActivity.token, pageName+"/videos?fields=from{cover,name}&since=2017-05-01",
                                    new GraphRequest.Callback() {
                                        @Override
                                        public void onCompleted(GraphResponse response) {
                                            try {
                                                JSONObject jsPageName =  response.getJSONObject().getJSONArray("data").getJSONObject(0).getJSONObject("from");
                                                String ms = "Please reduce the amount of data you're asking for, then retry your request";
                                                Log.d("yyy",jsPageName+"   ");
                                                if( jsPageName.has("name")|| !jsPageName.getJSONArray("data").isNull(0)  ){
                                                    if(b) {
                                                        MainActivity.allPages.add(pageName);
                                                        MainActivity.isSupscripe.add(true);
                                                        String cover = jsPageName.getJSONObject("cover").getString("source");
                                                        String idPic = jsPageName.getString("id");
                                                        String name = jsPageName.getString("name");
                                                        String pagePic = "https://graph.facebook.com/"+idPic+"/picture?type=large";
                                                        MainActivity.myDb.insertData_Pages(name, "true",pagePic,cover,pageName);
                                                    }
                                                    else{
                                                        MainActivity.myDb.updateData(pageName,"true");
                                                    }

                                                } else{
                                                    Log.d("yyy","Error Page");
                                                }


                                            } catch (Exception ex) {
                                                Log.d("yyy", ex.toString());
                                            }
                                            SupscripeActivity.ft.detach(SupscripeActivity.conttt).attach(SupscripeActivity.conttt).commit();
                                        }

                                    });

                            request.executeAsync();

                        }


                    }

                })
                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {
                        // Do something else
                    }
                }).create();
    }
}

