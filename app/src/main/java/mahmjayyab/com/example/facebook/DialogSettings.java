package mahmjayyab.com.example.facebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.PrintWriter;

public class DialogSettings extends android.support.v4.app.DialogFragment {
    boolean[] checkedItems;
     String[] items;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // getCheckedItems();
        final int size = MainActivity.allPages.size();
        checkedItems = new boolean[size];
        items = new String[size];
        Log.d("asd",size+"");
        MainActivity.allPages.toArray(items);
        for (int i =0;i<size;i++) checkedItems[i] = MainActivity.checked.get(i);
        //MainActivity.checked.toArray(checkedItems);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder
                .setTitle("Channels")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    for(int i =0; i< checkedItems.length; i++){
                        MainActivity.checked.set(i,checkedItems[i]);
                        Log.d("asd",MainActivity.checked.get(i)+"");
                        Log.d("asd","Edit");
                        Log.d("asd","wwwwww");
                        Log.d("asd","wqe");
                    }
                    }
                })

                // Negative Button
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,	int which) {

                    }
                })
                .setMultiChoiceItems(items, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        //Log.d("asd",isChecked+"        "+which);
                       // MainActivity.checked.set(which,isChecked);

                    }
                });

        return builder.create();
    }

    /*private void getCheckedItems(){
        if(System.showNotificationsLow){
            checkedItems[0]=true;
        }
        if(System.showNotificationsMedium){
            checkedItems[1]=true;
        }
        if(System.showNotificationsHigh){
            checkedItems[2]=true;
        }
    }*/

}