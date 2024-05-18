package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uco.twilliams84.termprojecttimothyw.Database.DatabaseOperator;
import edu.uco.twilliams84.termprojecttimothyw.Database.PlayerTable;
import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class SignUpActivity extends Activity {

    private SignUpActivity signUpActivity;
    private EditText signupNameText;
    private EditText signupEmailText;
    private EditText signupPasswordText;
    private EditText signupCityText;
    private EditText signupStateText;
    private Button signupRegisterButton;
    private final String[] stateNames = {
            "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado",
            "Connecticut", "Delaware", "Florida", "Georgia", "Hawaii", "Idaho", "Illinois",
            "Indiana", "Iowa", "Kansas", "Kentucky", "Louisiana", "Maine", "Maryland", "Massachusetts",
            "Michigan", "Minnesota", "Mississippi", "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire",
            "New Jersey", "New Mexico", "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon",
            "Pennsylvania", "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas" , "Utah", "Vermont",
            "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        builder = null;
        signUpActivity = this;

        signupNameText = (EditText) findViewById(R.id.signup_name);
        signupEmailText = (EditText) findViewById(R.id.signup_email);
        signupPasswordText = (EditText) findViewById(R.id.signup_password);
        signupCityText = (EditText) findViewById(R.id.signup_city);
        signupStateText = (EditText) findViewById(R.id.signup_state);
        signupRegisterButton = (Button) findViewById(R.id.signup_register);

        signupRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signupNameText.getText().toString().equals("") ||
                        signupEmailText.getText().toString().equals("") ||
                        signupPasswordText.getText().toString().equals("") ||
                        signupCityText.getText().toString().equals("") ||
                        signupStateText.getText().toString().equals("")) {
                    Toast.makeText(getBaseContext(), "Please fill out all fields.", Toast.LENGTH_SHORT).show();
                } else if (signupNameText.length() < 6) {
                    Toast.makeText(getBaseContext(), "Name must be at least 6 characters long.", Toast.LENGTH_SHORT).show();
                } else if (signupEmailText.getText().toString().indexOf('@') < 0) {
                    Toast.makeText(getBaseContext(), "Email address not in valid format.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean stateValid = false;
                    for (int i = 0; i < stateNames.length; i++) {
                        if (stateNames[i].toLowerCase().equals(signupStateText.getText().toString().toLowerCase())) {
                            stateValid = true;
                        }
                    }
                    if (!stateValid) {
                        Toast.makeText(getBaseContext(), "State name not valid.", Toast.LENGTH_SHORT).show();
                    } else {
                        String email = signupEmailText.getText().toString();

                        //Check database for matching entries
                        DatabaseOperator databaseOperator = new DatabaseOperator(getBaseContext());
                        SQLiteDatabase database = databaseOperator.getReadableDatabase();

                        String[] projection = {
                                PlayerTable.PlayerEntry.COLUMN_EMAIL
                        };

                        //Filter results where "COLUMN_EMAIL = email
                        String selection = PlayerTable.PlayerEntry.COLUMN_EMAIL + " = ?";
                        String[] selectionArgs = {email};

                        //Sorting order
                        String sortOrder = PlayerTable.PlayerEntry.COLUMN_EMAIL + " DESC";

                        Cursor cursor = database.query(
                                PlayerTable.PlayerEntry.TABLE_NAME, //Table being queried
                                projection,                         //The columns being returned
                                selection,                          //The columns for the WHERE clause
                                selectionArgs,                      //The values for the WHERE clause
                                null,                               //No row grouping
                                null,                               //No row group filtering
                                sortOrder                           //Order of results
                        );

                        //Navigate the results
                        if (!cursor.moveToFirst()) {
                            database.close();
                            //If there are no results, the entry is valid
                            String encryptedPassword = encryptPassword(signupPasswordText.getText().toString());
                            Player player = new edu.uco.twilliams84.termprojecttimothyw.Model.Player(
                                    signupNameText.getText().toString(), signupCityText.getText().toString(), signupStateText.getText().toString(),
                                    signupEmailText.getText().toString(), encryptedPassword
                            );
                            databaseOperator.addPlayer(player);

                            if (builder == null) {
                                if (builder == null) {
                                    builder = new AlertDialog.Builder(signUpActivity);
                                    builder.setTitle("Signed Up!")
                                            .setMessage("You have successfully signed up");
                                    builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            Intent intent = new Intent(SignUpActivity.this, MenuActivity.class);
                                            startActivity(intent);
                                        }
                                    });
                                    builder.setCancelable(false);
                                    builder.show();
                                }
                            }
                        } else {
                            Toast.makeText(getBaseContext(), "Email already in use.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });
    }

    private static String encryptPassword(String password) {
        /*
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(password.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++ ) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        */
        return password;
    }
}
