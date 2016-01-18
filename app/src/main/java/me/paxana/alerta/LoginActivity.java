    package me.paxana.alerta;

    import android.app.Activity;
    import android.app.AlertDialog;
    import android.content.Intent;
    import android.support.v7.app.AppCompatActivity;
    import android.os.Bundle;
    import android.view.View;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.TextView;

    import com.parse.LogInCallback;
    import com.parse.ParseException;
    import com.parse.ParseUser;
    import com.parse.SignUpCallback;

    import butterknife.Bind;
    import butterknife.ButterKnife;
    import butterknife.OnClick;

    public class LoginActivity extends AppCompatActivity {
        @Bind(R.id.usernameField) EditText mUsername;
        @Bind(R.id.passwordField) EditText mPassword;
        @Bind(R.id.loginButton) Button mLoginButton;
        @Bind(R.id.signupButton) TextView mSignupButton;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);

            mLoginButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String username = mUsername.getText().toString();
                    String password = mPassword.getText().toString();

                    username = username.trim();
                    password = password.trim();

                    if (username.isEmpty() || password.isEmpty()) { //if they leave a field blank give them an error
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setMessage(R.string.Login_Error_Message)
                                .setTitle(R.string.Login_Error_title)
                                .setPositiveButton(android.R.string.ok, null);
                        AlertDialog dialog = builder.create();
                        dialog.show();}
                    else { //if every field is accounted for, log in.
                        ParseUser.logInInBackground(username, password, new LogInCallback() {
                            @Override
                            public void done(ParseUser user, ParseException e) {

                                if (e == null) { //if no errors return
                                    Intent intent = new Intent(LoginActivity.this, MainActivity.class); // then we logged in, start the app!
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    startActivity(intent);} // success }
                                else {
                                    AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this); //something went wrong logging in
                                    builder.setMessage(e.getMessage())  // get a meaningful error message from parse
                                            .setTitle(R.string.Login_Error_title)
                                            .setPositiveButton(android.R.string.ok, null);
                                    AlertDialog dialog = builder.create();
                                    dialog.show();
                                }
                            }
                        });
                    }
                }
            });
            mSignupButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intent);


                }
            });
        }
    }