package edu.uco.twilliams84.termprojecttimothyw.View;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import edu.uco.twilliams84.termprojecttimothyw.Database.DatabaseOperator;
import edu.uco.twilliams84.termprojecttimothyw.Model.Player;
import edu.uco.twilliams84.termprojecttimothyw.R;

public class LogInActivity extends Activity {

    private EditText loginEmailText;
    private EditText loginPassText;
    private Button loginSubmitButton;
    private Button loginSignupButton;
    private LogInActivity logInActivity;

    private AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        builder = null;
        logInActivity = this;

        loginEmailText = (EditText) findViewById(R.id.login_email);
        loginPassText = (EditText) findViewById(R.id.login_password);
        loginSubmitButton = (Button) findViewById(R.id.login_login);
        loginSignupButton = (Button) findViewById(R.id.login_signup);

        loginSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseOperator databaseOperator = new DatabaseOperator(getBaseContext());

                String email = loginEmailText.getText().toString();
                String password = loginPassText.getText().toString();
                String encryptedPassword = encryptPassword(password);
                Player player = new Player("","","",email, password);

                boolean found = databaseOperator.findPlayer(player);
                if (found) {
                    if (builder == null) {
                        if (builder == null) {
                            builder = new AlertDialog.Builder(logInActivity);
                            builder.setTitle("Logged in!")
                                    .setMessage("You have successfully logged in");
                            builder.setPositiveButton("Okay", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent intent = new Intent(LogInActivity.this, MenuActivity.class);
                                    startActivity(intent);
                                }
                            });
                            builder.setCancelable(false);
                            builder.show();
                        }
                    }
                } else {
                    Toast.makeText(getBaseContext(), "Could not find matching account", Toast.LENGTH_SHORT).show();
                }
            }
        });

        loginSignupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LogInActivity.this, SignUpActivity.class);
                startActivity(intent);
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
