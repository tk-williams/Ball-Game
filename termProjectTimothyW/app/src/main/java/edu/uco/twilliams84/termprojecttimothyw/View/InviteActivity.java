package edu.uco.twilliams84.termprojecttimothyw.View;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import edu.uco.twilliams84.termprojecttimothyw.Model.Contact;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class InviteActivity extends Activity {

    static ArrayList<Contact> contacts = new ArrayList<>();
    private ListView contactsList;
    private String[] contactNames;
    private int currentContact;

    //Request int
    private static final int PERMISSION_REQEST_READ_CONTACTS = 1;
    private static final int PERMISSION_REQUEST_SEND_SMS = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite);

        contacts.clear();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(android.Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PERMISSION_REQEST_READ_CONTACTS);
        } else {
            fillContacts();
        }

        contactNames = new String[contacts.size()];
        for (int i = 0; i < contacts.size(); i++) {
            contactNames[i] = contacts.get(i).getName();
        }
        contactsList = (ListView) findViewById(R.id.invite_contacts);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this,
                R.layout.contacts,
                contactNames
        );
        contactsList.setAdapter(adapter);
        contactsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                currentContact = position;
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
                    requestPermissions(new String[]{Manifest.permission.SEND_SMS}, PERMISSION_REQUEST_SEND_SMS);
                } else {
                    sendSmsMessage();
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PERMISSION_REQEST_READ_CONTACTS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                fillContacts();
            }
        } else if (requestCode == PERMISSION_REQUEST_SEND_SMS) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                sendSmsMessage();
            }
        }
    }

    public void fillContacts () {
        ContentResolver contentResolver = getContentResolver();
        Cursor contactCursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (contactCursor.getCount() > 0) {
            while (contactCursor.moveToNext()) {
                String id = contactCursor.getString(
                        contactCursor.getColumnIndex(ContactsContract.Contacts._ID)
                );
                String name = contactCursor.getString(
                        contactCursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME)
                );
                if (contactCursor.getInt(contactCursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER )) > 0) {
                    Cursor phoneCursor = contentResolver.query(
                            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(
                                ContactsContract.CommonDataKinds.Phone.NUMBER
                        ));
                        contacts.add(new Contact(name, phoneNumber));
                    }
                }
            }
        }
        Collections.sort(contacts, Contact.compareNames());
    }

    public void sendSmsMessage() {
        SmsManager smsManger = SmsManager.getDefault();
        smsManger.sendTextMessage(contacts.get(currentContact).getPhoneNumber(),
                null,
                "You've been invited by " + contacts.get(currentContact).getName() + " to play Ball Game!",
                null,
                null);
        Log.e(contacts.get(currentContact).getName(), contacts.get(currentContact).getPhoneNumber());
    }
}
