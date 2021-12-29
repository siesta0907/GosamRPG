package com.example.gosamrpg;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ScheduleActivity extends AppCompatActivity {

    private EditText mWorkEditText;
    private long mMemoId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        mWorkEditText = (EditText) findViewById(R.id.work_edit);

        Intent intent = getIntent();

        if (intent != null) {
            mMemoId = intent.getLongExtra("id", -1);
            String work = intent.getStringExtra("work");
            mWorkEditText.setText(work);
        }

    }

    public void onSaveButtonClicked(View view) {
        String work = mWorkEditText.getText().toString();
        ContentValues contentValues = new ContentValues();//contentValues 객체 생성
        contentValues.put(ScheduleContract.ScheduleEntry.COLUMN_NAME_WORK, work);
        SQLiteDatabase db = ScheduleDbHelper.getInstance(this).getWritableDatabase();

        if (mMemoId == -1) {
            long newRowId = db.insert(ScheduleContract.ScheduleEntry.TABLE_NAME, null, contentValues); //요거위치
            if (newRowId == -1) {
                Toast.makeText(this, "저장에 문제가 발생하였습니다",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "일정이 저장되었습니다", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }
        } else {
            int count = db.update(ScheduleContract.ScheduleEntry.TABLE_NAME, contentValues, ScheduleContract.ScheduleEntry._ID + " = " +mMemoId, null);
            if(count ==0) {
                Toast.makeText(this,"수정에 문제가 발생하였습니다",
                        Toast.LENGTH_SHORT).show();
            } else{
                Toast.makeText(this, "일정이 수정되었습니다", Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }



        }
    }
}

