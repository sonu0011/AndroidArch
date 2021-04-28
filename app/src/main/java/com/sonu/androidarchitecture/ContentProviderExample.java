package com.sonu.androidarchitecture;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.TextView;

public class ContentProviderExample extends AppCompatActivity {

    private TextView textView;

    private String[] mColumnProjection = new String[]{

            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER
    };

    //    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Sonu Kumar'";
    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";

    private String[] mselectionArg = new String[]{"Sonu Kumar"};
    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_example);

        textView = findViewById(R.id.textView);

        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                mColumnProjection,
                null,
                null, mOrderBy);
        StringBuilder stringBuilder = new StringBuilder();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                stringBuilder.append(cursor.getString(0) + " " + cursor.getString(1) + "  " + cursor.getString(2) + "\n");


            }
        }
        assert cursor != null;
        cursor.close();
        textView.setText(stringBuilder.toString());


    }
}