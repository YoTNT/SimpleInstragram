package codepath.com.simple_instagram;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.LogInCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    private EditText etUsername;
    private EditText etPassword;
    private Button btnLogin;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                login(username, password);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = etUsername.getText().toString();
                String password = etPassword.getText().toString();
                signUp(username, password);
                return;
            }
        });
    }

    private void login(String username, String password){
        ParseUser.logInInBackground(username, password, new LogInCallback() {
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e != null){
                    Log.e(TAG, "Issue with login");
                    e.printStackTrace();
                    Toast.makeText(LoginActivity.this, "Invalid account!", Toast.LENGTH_LONG).show();
                    return;
                }
                // TODO: navigate to new activity if the user has signed properly
                goMainActivity();
                Log.d(TAG, "Login succeed!");
                Toast.makeText(LoginActivity.this, "Login success!", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void signUp(String username, String password){
        ParseUser user = new ParseUser();

        user.setUsername(username);
        user.setPassword(password);

        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(ParseException e) {
                if (e == null){
                    Toast.makeText(LoginActivity.this, "Sign Up Success", Toast.LENGTH_LONG).show();
                }
                else{
                    Log.e(TAG, "Sign up failed!");
                    e.printStackTrace();
                    return;
                }
            }
        });
    }

    private void goMainActivity(){
        Log.d(TAG, "Navigating to goMainActivity!");
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
        finish();
    }
}
