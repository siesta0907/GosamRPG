package com.example.gosamrpg;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.gesture.Gesture;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int Experience = 0;

    TextView textView2;

    ImageView A_clothes;  //
    ImageView B_clothes;
    ImageView C_clothes;
    ImageView D_clothes;
    ImageView A_weapons;
    ImageView B_weapons;
    ImageView C_weapons;
    ImageView D_weapons;
    ImageView A_shields;
    ImageView B_shields;
    ImageView C_shields;
    ImageView D_shields;
    ImageView A_shoes;
    ImageView B_shoes;
    ImageView C_shoes;
    ImageView D_shoes;





    private static final int REQUEST_COD_INSRET = 1000;

    private WorkAdapter mAdapter;

    Cursor clickedItem = null;
 //   long clickedTime = 0;

    Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        A_clothes = findViewById(R.id.a_clothes);
        B_clothes = findViewById(R.id.b_clothes);
        C_clothes = findViewById(R.id.c_clothes);
        D_clothes= findViewById(R.id.d_clothes);
        A_weapons = findViewById(R.id.a_weapons);
        B_weapons = findViewById(R.id.b_weapons);
        C_weapons = findViewById(R.id.c_weapons);
        D_weapons= findViewById(R.id.d_weapons);
        A_shields = findViewById(R.id.a_shield);
        B_shields = findViewById(R.id.b_shield);
        C_shields = findViewById(R.id.c_shield);
        D_shields = findViewById(R.id.d_shield);
        A_shoes = findViewById(R.id.a_shoes);
        B_shoes= findViewById(R.id.b_shoes);
        C_shoes = findViewById(R.id.c_shoes);
        D_shoes = findViewById(R.id.d_shoes);

        if( Experience == 0 ) {
            A_clothes.setVisibility(View.INVISIBLE);
            A_weapons.setVisibility(View.INVISIBLE);
            A_shields.setVisibility(View.INVISIBLE);
            A_shoes.setVisibility(View.INVISIBLE);
            B_clothes.setVisibility(View.INVISIBLE);
            B_weapons.setVisibility(View.INVISIBLE);
            B_shields.setVisibility(View.INVISIBLE);
            B_shoes.setVisibility(View.INVISIBLE);
            C_clothes.setVisibility(View.INVISIBLE);
            C_weapons.setVisibility(View.INVISIBLE);
            C_shields.setVisibility(View.INVISIBLE);
            C_shoes.setVisibility(View.INVISIBLE);
            D_clothes.setVisibility(View.VISIBLE);
            D_weapons.setVisibility(View.VISIBLE);
            D_shields.setVisibility(View.VISIBLE);
            D_shoes.setVisibility(View.VISIBLE);

        }

        textView2 = (TextView)findViewById(R.id.textView2);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {  //????????? ????????? ????????????????????? ????????????.
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent(MainActivity.this, ScheduleActivity.class), REQUEST_COD_INSRET);
                //????????? REQUEST_COD_INSRET??? ??? ?????? ???????????????
            }
        });

        ListView listView = (ListView) findViewById(R.id.work_list);
        ScheduleDbHelper dbHelper = ScheduleDbHelper.getInstance(this); //??????????????? ????????? ??? ?????????
        Cursor cursor = dbHelper.getReadableDatabase().query(ScheduleContract.ScheduleEntry.TABLE_NAME,
                null, null, null, null, null, null);  //query

        mAdapter = new WorkAdapter(this, cursor);
        listView.setAdapter(mAdapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {
                final long deletedId = l;
                AlertDialog.Builder builder =new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("????????????");
                builder.setMessage("????????? ?????????????????????????");
                builder.setPositiveButton("??????", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        SQLiteDatabase db = ScheduleDbHelper.getInstance(MainActivity.this).getWritableDatabase();
                        int deletedCount =  db.delete(ScheduleContract.ScheduleEntry.TABLE_NAME, ScheduleContract.ScheduleEntry._ID + "=" +deletedId, null);
                       if (deletedCount == 0 ) {
                           Toast.makeText(MainActivity.this, "????????? ????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                       }else {
                           mAdapter.swapCursor(getWorkCursor());
                           Toast.makeText(MainActivity.this, "????????? ?????????????????????.",Toast.LENGTH_SHORT).show();
                       }

                    }
                });
                builder.setNegativeButton("??????", null);
                builder.show();
                return true;
            }
        });




//????????? ?????? ??? ?????? ?????? ??????
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, final long id) {


                if (clickedItem != null && clickedItem.equals(mAdapter.getItem(position))) {
                    clickedItem = null;
                    handler.removeCallbacks(runnable);
                    Experience++;
                    final long deletedId = id;
                    SQLiteDatabase db = ScheduleDbHelper.getInstance(MainActivity.this).getWritableDatabase();
                    db.delete(ScheduleContract.ScheduleEntry.TABLE_NAME, ScheduleContract.ScheduleEntry._ID + "=" +deletedId, null);
                    mAdapter.swapCursor(getWorkCursor());
                    textView2.setText(String.valueOf(Experience) );
                    Toast.makeText(MainActivity.this, "????????? ?????????????????????.",Toast.LENGTH_SHORT).show();


                    if(3 <= Experience && Experience < 5  ) {
                        A_clothes.setVisibility(View.INVISIBLE);
                        A_weapons.setVisibility(View.INVISIBLE);
                        A_shields.setVisibility(View.INVISIBLE);
                        A_shoes.setVisibility(View.INVISIBLE);
                        B_clothes.setVisibility(View.INVISIBLE);
                        B_weapons.setVisibility(View.INVISIBLE);
                        B_shields.setVisibility(View.INVISIBLE);
                        B_shoes.setVisibility(View.INVISIBLE);
                        C_clothes.setVisibility(View.VISIBLE);
                        C_weapons.setVisibility(View.VISIBLE);
                        C_shields.setVisibility(View.VISIBLE);
                        C_shoes.setVisibility(View.VISIBLE);
                        D_clothes.setVisibility(View.INVISIBLE);
                        D_weapons.setVisibility(View.INVISIBLE);
                        D_shields.setVisibility(View.INVISIBLE);
                        D_shoes.setVisibility(View.INVISIBLE);
                    } else if(5 <= Experience && Experience < 7  ) {
                        A_clothes.setVisibility(View.INVISIBLE);
                        A_weapons.setVisibility(View.INVISIBLE);
                        A_shields.setVisibility(View.INVISIBLE);
                        A_shoes.setVisibility(View.INVISIBLE);
                        B_clothes.setVisibility(View.VISIBLE);
                        B_weapons.setVisibility(View.VISIBLE);
                        B_shields.setVisibility(View.VISIBLE);
                        B_shoes.setVisibility(View.VISIBLE);
                        C_clothes.setVisibility(View.INVISIBLE);
                        C_weapons.setVisibility(View.INVISIBLE);
                        C_shields.setVisibility(View.INVISIBLE);
                        C_shoes.setVisibility(View.INVISIBLE);
                        D_clothes.setVisibility(View.INVISIBLE);
                        D_weapons.setVisibility(View.INVISIBLE);
                        D_shields.setVisibility(View.INVISIBLE);
                        D_shoes.setVisibility(View.INVISIBLE);
                    } else if (7<= Experience) {
                        A_clothes.setVisibility(View.VISIBLE);
                        A_weapons.setVisibility(View.VISIBLE);
                        A_shields.setVisibility(View.VISIBLE);
                        A_shoes.setVisibility(View.VISIBLE);
                        B_clothes.setVisibility(View.INVISIBLE);
                        B_weapons.setVisibility(View.INVISIBLE);
                        B_shields.setVisibility(View.INVISIBLE);
                        B_shoes.setVisibility(View.INVISIBLE);
                        C_clothes.setVisibility(View.INVISIBLE);
                        C_weapons.setVisibility(View.INVISIBLE);
                        C_shields.setVisibility(View.INVISIBLE);
                        C_shoes.setVisibility(View.INVISIBLE);
                        D_clothes.setVisibility(View.INVISIBLE);
                        D_weapons.setVisibility(View.INVISIBLE);
                        D_shields.setVisibility(View.INVISIBLE);
                        D_shoes.setVisibility(View.INVISIBLE);
                    }


                    return;
                }

                clickedItem = (Cursor) mAdapter.getItem(position);

                handler.postDelayed(runnable, 1000);


            }
        });


    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {

            if (clickedItem != null) {
                //????????? ????????? ???????????? ??????
                Cursor cursor = clickedItem;

                //???????????? ????????? ??????
                String work = cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.ScheduleEntry.COLUMN_NAME_WORK));
                int id = cursor.getInt(0);

                //???????????? id??? ?????? ??????
                final Intent intent = new Intent(MainActivity.this, ScheduleActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("work", work);

                //MemoActivity ??????
                startActivityForResult(intent, REQUEST_COD_INSRET);

                clickedItem = null;
            }
        }
    };

    private Cursor getWorkCursor() {
        ScheduleDbHelper dbHelper = ScheduleDbHelper.getInstance(this);
        return dbHelper.getReadableDatabase().query(ScheduleContract.ScheduleEntry.TABLE_NAME,
                null, null, null, null, null, ScheduleContract.ScheduleEntry._ID +" DESC",null
        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_COD_INSRET && resultCode == RESULT_OK) {
            mAdapter.swapCursor(getWorkCursor());
        }
    }

    private static class WorkAdapter extends CursorAdapter {
        public WorkAdapter(Context context, Cursor c) {
            super(context,c,false);
        }
        @Override
        public View newView(Context context, Cursor cursor, ViewGroup parent) {
            return LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, parent, false);
        }

        @Override
        public void bindView(View view, Context context, Cursor cursor) {
            TextView titleText = (TextView) view.findViewById(android.R.id.text1);
            titleText.setText(cursor.getString(cursor.getColumnIndexOrThrow(ScheduleContract.ScheduleEntry.COLUMN_NAME_WORK)));
        }

        @Override
        public Object getItem(int position) {
            return super.getItem(position);
        }
    }

}
