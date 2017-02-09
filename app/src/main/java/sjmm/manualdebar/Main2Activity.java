package sjmm.manualdebar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    String got;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent callTable=getIntent();
        got =callTable.getStringExtra("Sent");
        System.out.println(got);
        TextView Name = (TextView) findViewById(R.id.TextView2);
        Name.setText(got);
        loadData();
    }

    public void loadData(){
        TextView ing = (TextView) findViewById(R.id.textView_ingredients);
        TextView comment = (TextView) findViewById(R.id.textView_recomendation);
        TextView receip = (TextView) findViewById(R.id.textView_receip);
        String textIng="";
        String textRecomend="";
        String textReceip= "";
        DataHandler handler;
        handler = new DataHandler(getApplicationContext());
        ArrayList<Object[]> reader = handler.getProducts();
        for(int j=0;j<reader.size();j++){
            if(reader.get(j)[1].equals(got)){
                textIng=textIng.concat("> ");
                textIng=textIng.concat(reader.get(j)[4].toString().trim());
                textIng=textIng.concat("\n");
            }
        }
        for(int j=0;j<reader.size();j++){
            if(reader.get(j)[1].equals(got)){
                textRecomend=textRecomend.concat(reader.get(j)[5].toString().trim());
                textRecomend=textRecomend.concat("\n");
                break;
            }
        }
        for(int j=0;j<reader.size();j++){
            if(reader.get(j)[1].equals(got)){
                textReceip=textReceip.concat(reader.get(j)[6].toString().trim());
                textReceip=textReceip.concat("\n");
                break;
            }
        }
        ing.setText(textIng);
        comment.setText(textRecomend);
        receip.setText(textReceip);
    }
}
