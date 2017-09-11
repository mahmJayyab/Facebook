package mahmjayyab.com.example.facebook;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.content.res.TypedArrayUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DialogSettings extends android.support.v4.app.DialogFragment {
    boolean[] checkedItems;
     String[] items;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
       // getCheckedItems();
        final int size = MainActivity.news.size();
        checkedItems = new boolean[size];
        items = new String[size];

        Log.d("asd",size+"");
       // MainActivity.allPages.toArray(items);
        for(int i=0; i< size; i++){
            //items[i] = MainActivity.allPages.get(i);
            items[i] = MainActivity.news.get(i);
        }
        for (int i =0;i<size;i++) checkedItems[i] = MainActivity.isSupscripe.get(i);
        //MainActivity.checked.toArray(checkedItems);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = getActivity().getLayoutInflater().inflate(R.layout.start_menu, null);
        builder
                .setView(view)
                .setTitle("Channels")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                    for(int i =0; i< checkedItems.length; i++){
                        MainActivity.isSupscripe.set(i,checkedItems[i]);
                        Log.d("asd",MainActivity.isSupscripe.get(i)+"");
                        if(checkedItems[i])
                            MainActivity.myDb.updateData(items[i],"true");
                        else
                            MainActivity.myDb.updateData(items[i],"false");
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

        Spinner sp = (Spinner)view.findViewById(R.id.spin);
        final List<String> categories = new ArrayList<>();
        categories.add("News");categories.add("Sports");
        categories.add("Education");categories.add("Comedy");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(Main2Activity.myContext,
                android.R.layout.simple_spinner_item, categories);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sp.setAdapter(adapter);
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> arg0, View v, int position, long id)
            {
                Log.d("sele",categories.get(position));
                switch (categories.get(position)){
                    case "News":
                    {
                        items[0] = "MEQBAS";
                        items[1] = "ajplusarabi";
                        break;
                    }
                    case "Sports":
                        items[0] = "ajplusarabi";
                        items[1]="";
                        break;
                }
            }

            public void onNothingSelected(AdapterView<?> arg0)
            {
                Log.v("routes", "nothing selected");
            }
        });


        return builder.create();
    }

    public void addPage(String category){

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