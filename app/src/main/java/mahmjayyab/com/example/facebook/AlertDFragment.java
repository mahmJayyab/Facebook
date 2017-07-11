package mahmjayyab.com.example.facebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.widget.EditText;

public class AlertDFragment extends DialogFragment {
    public static  String pageName;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        final EditText input = new EditText(getActivity());

        input.setHint("https://www.facebook.com/PageName");
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
                            pageName = temp;
                            MainActivity.allPages.add(pageName);
                            MainActivity.checked.add(true);
                            MainActivity.myDb.insertData(pageName,"true");
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
