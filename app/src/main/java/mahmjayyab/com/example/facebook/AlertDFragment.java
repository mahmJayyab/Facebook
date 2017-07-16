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

import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;

import org.json.JSONArray;
import org.json.JSONObject;

public class AlertDFragment extends DialogFragment {
    public static  String pageName;
    boolean b = true;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(getActivity());

        input.setHint("https://www.facebook.com/PageName");
        input.setText("https://www.facebook.com/");
        return new AlertDialog.Builder(getActivity())
                // Set Dialog Icon
                //.setIcon(R.drawable.side_nav_bar)
                // Set Dialog Title
                .setTitle("ِAdd Facebook Page Link")
                .setView(input)
                // Set Dialog Message
                //.setMessage("Alert DialogFragment Tutorial")




                // Positive button
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        String link = input.getText().toString();
                        // Do something else
                        if(link.startsWith("https://www.facebook.com")||link.startsWith("https://m.facebook.com")||link.startsWith("www.facebook.com")){
                            String p = link.substring(link.indexOf(".com/")+5);
                            String temp = "";
                            if (p.indexOf("/") != -1) {
                                temp = p.substring(p.indexOf("/"));
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
                           /* if(b) {
                                pageName = temp;
                                MainActivity.allPages.add(temp);
                                MainActivity.isSupscripe.add(true);
                                MainActivity.myDb.insertData(temp, "true");
                            }
                            else{
                                MainActivity.myDb.updateData(temp,"true");
                            }*/
                           pageName = temp;
                            GraphRequest request = GraphRequest.newGraphPathRequest(
                                    MainActivity.token, pageName+"/videos?fields=from{name}",
                                    new GraphRequest.Callback() {
                                        @Override
                                        public void onCompleted(GraphResponse response) {
                                            // Insert your code here
                                            try {
                                                //JSONObject jsPageName =  response.getJSONObject();
                                                JSONObject jsPageName =  response.getJSONObject().getJSONArray("data").getJSONObject(0).getJSONObject("from");
                                                String ms = "Please reduce the amount of data you're asking for, then retry your request";
                                                Log.d("yyy",jsPageName+"   ");
                                                //Log.d("yyy",jsPageName.getString("videos"));
                                                if( jsPageName.has("name")|| !jsPageName.getJSONArray("data").isNull(0)  ){
                                                    if(b) {

                                                        MainActivity.allPages.add(pageName);
                                                        MainActivity.isSupscripe.add(true);
                                                        MainActivity.myDb.insertData(pageName, "true");
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

