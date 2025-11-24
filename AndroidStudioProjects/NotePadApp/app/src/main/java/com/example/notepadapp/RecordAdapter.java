package com.example.notepadapp;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

public class RecordAdapter extends CursorAdapter {

    public RecordAdapter(Context context, Cursor c) {
        super(context, c, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.item_record, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView tvTitle = view.findViewById(R.id.tvTitle);
        TextView tvTime = view.findViewById(R.id.tvTime);

        String title = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TITLE));
        String time = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_TIME));

        tvTitle.setText(title);
        tvTime.setText(time);

        // 点击项查看详情
        view.setOnClickListener(v -> {
            String content = cursor.getString(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_CONTENT));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MyDbHelper.COLUMN_ID));

            Intent intent = new Intent(context, RecordDetailActivity.class);
            intent.putExtra("title", title);
            intent.putExtra("content", content);
            intent.putExtra("time", time);
            intent.putExtra("id", id);
            context.startActivity(intent);
        });
    }
}