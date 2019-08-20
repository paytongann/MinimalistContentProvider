package com.example.minimalistcontentprovider;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView tvResponse;
    private Button listAllWords, listFirstWord;
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listAllWords = findViewById(R.id.btn_list_all_words);
        listFirstWord = findViewById(R.id.btn_list_first_word);
        tvResponse = findViewById(R.id.tv_response);
        listAllWords.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDisplayEntries(view);
            }
        });
        listFirstWord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickDisplayEntries(view);
            }
        });
    }


    public void onClickDisplayEntries (View view){
        String selectionClause;
        String selectionArgs[];
        switch (view.getId()) {

            case R.id.btn_list_all_words:
                selectionClause = null;
                selectionArgs = null;
                break;
            case R.id.btn_list_first_word:
                selectionClause = Contract.WORD_ID + " = ?";
                selectionArgs = new String[] {"0"};
                break;
            default:
                selectionClause = null;
                selectionArgs = null;
        }
        String queryUri = Contract.CONTENT_URI.toString();
        String[] projection = new String[] {Contract.CONTENT_PATH};

        String sortOrder = null;
        Cursor cursor = getContentResolver().query(Uri.parse(queryUri), projection, selectionClause, selectionArgs, sortOrder);
        if (cursor != null) {
            if (cursor.getCount() > 0) {
                cursor.moveToFirst();
                int columnIndex = cursor.getColumnIndex(projection[0]);
                do {
                    String word = cursor.getString(columnIndex);
                    tvResponse.append(word + "\n");
                } while (cursor.moveToNext());
            } else {
                Log.d(TAG, "onClickDisplayEntries " + "No data returned.");
                tvResponse.append("No data returned." + "\n");
            }
            cursor.close();
        } else {
            Log.d(TAG, "onClickDisplayEntries " + "Cursor is null.");
            tvResponse.append("Cursor is null." + "\n");
        }
    }
}
