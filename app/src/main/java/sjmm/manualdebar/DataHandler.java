package sjmm.manualdebar;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Created by SJMM on 07/01/2017.
 */

public class DataHandler extends SQLiteAssetHelper {

    private static final String DATABASE_NAME = "Bar";
    private static final int DATABASE_VERSION = 1;
    private SQLiteDatabase db;

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db=getWritableDatabase();

    }

    public ArrayList<Object[]> getProducts(){

        ArrayList<Object[]> array= new ArrayList<>();
        String[] cols = new String[]{"ID","NAME","AMOUNT","MEASURE","INGREDIENT","ADVISE","PROCESS"};
        Cursor cursor = db.query("products",cols,null,null,null,null,null);
        if(cursor.moveToFirst())
        {
            do {
                Object[] obj = new Object[7];
                obj[0]=cursor.getInt(0);
                obj[1]=cursor.getString(1);
                obj[2]=cursor.getInt(2);
                obj[3]=cursor.getString(3);
                obj[4]=cursor.getString(4);
                obj[5]=cursor.getString(5);
                obj[6]=cursor.getString(6);
                array.add(obj);
            } while (cursor.moveToNext());

        }
        return array;
    }
}
