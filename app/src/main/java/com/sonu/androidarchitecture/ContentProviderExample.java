package com.sonu.androidarchitecture;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.LoaderManager;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.Loader;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sonu.androidarchitecture.R;

import java.util.ArrayList;

public class ContentProviderExample extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {

    private TextView textView;
    private boolean hasLoaded = false;
    private EditText userName;
    private static final String TAG = "ContentProviderExample";

    private String[] mColumnProjection = new String[]{

            ContactsContract.Contacts.DISPLAY_NAME_PRIMARY,
            ContactsContract.Contacts.CONTACT_STATUS,
            ContactsContract.Contacts.HAS_PHONE_NUMBER,
            ContactsContract.Contacts._ID,
    };


    //    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = 'Sonu Kumar'";
    private String mSelectionClause = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY + " = ?";

    private String[] mselectionArg = new String[]{"Sonu Kumar"};
    private String mOrderBy = ContactsContract.Contacts.DISPLAY_NAME_PRIMARY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_provider_example);

        userName = findViewById(R.id.userName);
        textView = findViewById(R.id.textView);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 121);

//        Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
//                mColumnProjection,
//                null,
//                null, mOrderBy);
//        StringBuilder stringBuilder = new StringBuilder();
//        if (cursor != null && cursor.getCount() > 0) {
//            while (cursor.moveToNext()) {
//                stringBuilder.append(cursor.getString(0) + " " + cursor.getString(1) + "  " + cursor.getString(2) + "\n");
//
//
//            }
//        }
//        assert cursor != null;
//        cursor.close();
//        textView.setText(stringBuilder.toString());


    }

    public void loadData(View view) {

        if (!hasLoaded) {
            getLoaderManager().initLoader(1, null, this);
            hasLoaded = true;
        } else {
            getLoaderManager().restartLoader(1, null, this);

        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(TAG, "onCreateLoader: ");
        Log.d(TAG, "onCreateLoader: " + Thread.currentThread().getName());
        if (id == 1) {
            return new CursorLoader(this, ContactsContract.Contacts.CONTENT_URI, mColumnProjection, null, null, null);
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(TAG, "onLoadFinished: ");
        StringBuilder stringBuilder = new StringBuilder();
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {

                //for getting phone numbers
//                Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + cursor.getString(3), null, null);
//                while (phones.moveToNext()) {
//                    String phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
//                    stringBuilder.append(phoneNumber);
//                }
//                phones.close();

                stringBuilder.append(cursor.getString(0) + " " + cursor.getString(1) + "  " + cursor.getString(2) + "  " + cursor.getString(3) + "\n");


            }
        }
        assert cursor != null;
        cursor.close();
        textView.setText(stringBuilder.toString());
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(TAG, "onLoaderReset: ");

    }

    public void remove(View view) {
        String where = ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY + " = '" + userName.getText().toString() + "'";
        int i = getContentResolver().delete(ContactsContract.RawContacts.CONTENT_URI, where, null);
        Log.d(TAG, "remove: " + i);
        if (i > 0) Toast.makeText(this, "Contact remove successfully", Toast.LENGTH_SHORT).show();
        loadData(view);
    }

    public void add(View view) {
        ArrayList<ContentProviderOperation> cops = new ArrayList<ContentProviderOperation>();

//        cops.add(ContentProviderOperation.newInsert(ContactsContract.RawContacts.CONTENT_URI)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
//                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
//                .build());

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Contacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, "skr@gmail.com")
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, "sonu kumar")
                .build());

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, userName.getText().toString())
                .build());

//        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
//                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
//                .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, userName.getText().toString())
//                .build());

//        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
//                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID , 0)
//                .withValue(ContactsContract.Data.MIMETYPE , ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
//                .build());

        cops.add(ContentProviderOperation.newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(ContactsContract.Data.MIMETYPE, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, "879458")
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE, ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE)
                .build());


        try {
            getContentResolver().applyBatch(ContactsContract.AUTHORITY, cops);
            Toast.makeText(this, "Contact inserted", Toast.LENGTH_SHORT).show();
            loadData(view);
        } catch (Exception exception) {
            Log.i(TAG, exception.getMessage());
        }

    }


    public void update(View view) {

        String text = userName.getText().toString();
        String[] abc = text.split(" ");
        String id = abc[0];
        String content = abc[1];
        String where = ContactsContract.RawContacts._ID + " = ? ";
        String[] projection = new String[]{id};
        ContentValues contentValues = new ContentValues();
        contentValues.put(ContactsContract.RawContacts.DISPLAY_NAME_PRIMARY, content);

        int i = getContentResolver().update(ContactsContract.RawContacts.CONTENT_URI, contentValues, where, projection);
        Log.d(TAG, "update: " + i);
        if (i > 0) Toast.makeText(this, "Contact updated successfully", Toast.LENGTH_SHORT).show();
        loadData(view);

    }


    /*Things not working
    1. Data is not getting update if underlying data gets changed
    2.Data are being stored locally on real device but in emulator it works
    * */
}