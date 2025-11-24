package com.example.notepadapp;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RecordListActivity extends AppCompatActivity {

    private ListView listView;
    private MyDbHelper dbHelper;
    private RecordAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record_list);

        listView = findViewById(R.id.listView);
        dbHelper = new MyDbHelper(this);

        loadRecords();
    }

    private void loadRecords() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query(
                MyDbHelper.TABLE_RECORDS,
                null, null, null, null, null,
                MyDbHelper.COLUMN_TIME + " DESC"
        );

        if (cursor.getCount() == 0) {
            Toast.makeText(this, "暂无记录", Toast.LENGTH_SHORT).show();
        }

        adapter = new RecordAdapter(this, cursor);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (adapter != null) {
            adapter.getCursor().close();
        }
        if (dbHelper != null) {
            dbHelper.close();
        }
    }
}