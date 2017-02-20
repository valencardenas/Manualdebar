package sjmm.manualdebar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.AlignmentSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.util.ArrayList;

public class CoctelesLicorActivity extends AppCompatActivity {
    ListView mainList;
    public GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cocteles_licor);
        loadDataBase();
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }




    public String[] setList(ArrayList<Object[]> db){
        String[] arrayData = new String[db.size()];
        int pos=0;
        int posFirstNull=0;
        for(int i=0;i<db.size();i++)
        {
            arrayData[i]="";
            if(i<db.size()-1 ){
                if(!(db.get(i)[1].toString().equals(db.get(i+1)[1].toString()))){
                    arrayData[pos]=db.get(i)[1].toString();
                    pos++;
                }
            }
        }
        for(int i=0;i<db.size();i++){
            if(arrayData[i].equals("")){
                posFirstNull=i;
                System.out.println(posFirstNull);
                break;
            }
        }
        String[] reArrengedArrayData = new String[posFirstNull];

        for(int i=0;i<posFirstNull;i++){
            reArrengedArrayData[i]=arrayData[i];
        }

        return reArrengedArrayData;
    }

    public Spannable centerText(String string){
        Spannable centeredText = new SpannableString(string);
        centeredText.setSpan(new AlignmentSpan.Standard(Layout.Alignment.ALIGN_CENTER),
                0, string.length() - 1,
                Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        return centeredText;
    }

    public void loadDataBase() {
        DataHandler handler;
        handler = new DataHandler(getApplicationContext());
        ArrayList<Object[]> reader = handler.getProducts();
        mainList = (ListView) findViewById(R.id.ListViewProductos);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, setList(reader));
        mainList.setAdapter(adapter);
        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long l) {
                Intent callTable = new Intent(getApplicationContext(),Main2Activity.class);
                callTable.putExtra("Sent",mainList.getItemAtPosition(pos).toString().trim());
                startActivity(callTable);
            }
        });
    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

}
