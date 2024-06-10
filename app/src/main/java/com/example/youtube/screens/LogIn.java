package com.example.youtube.screens;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Patterns;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.google.android.material.textfield.TextInputLayout;

public class LogIn extends AppCompatActivity {

    private TextInputLayout emailEditText, passwordEditText;
    private Button loginButton;

    private Button signUpButton;
    private ImageView loginLogo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        emailEditText = findViewById(R.id.login_email);
        passwordEditText = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_login_button);
        loginLogo = findViewById(R.id.login_logo);
        signUpButton = findViewById(R.id.login_to_signup_button);
        emailEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                emailEditText.setError(null); // Clear error when user starts typing
                emailEditText.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        passwordEditText.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // No action needed before text changed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                passwordEditText.setError(null); // Clear error when user starts typing
                passwordEditText.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {
                // No action needed after text changed
            }
        });

        loginButton.setOnClickListener(v -> login());
        signUpButton.setOnClickListener(v->signUp());
    }

    private void signUp(){
        Intent intent = new Intent(LogIn.this, SignUpActivity.class);
        resetFields();
        startActivity(intent);
    }
    private void login() {
        String email = emailEditText.getEditText().getText().toString().trim();
        String password = passwordEditText.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            passwordEditText.setError("Please enter a password");
        }
        if (email.isEmpty()) {
            emailEditText.setError("Please enter an email");
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailEditText.setError("Please enter a valid email address");
        }

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String savedEmail = sharedPreferences.getString("email", null);
        String savedPassword = sharedPreferences.getString("password", null);
//        String encodedImage = sharedPreferences.getString("image", null);
//        if (encodedImage != null) {
//            Bitmap decodedImage = decodeBase64ToBitmap(encodedImage);
//            loginLogo.setImageBitmap(decodedImage);
//        }

        if (email.equals(savedEmail) && password.equals(savedPassword)) {
            Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LogIn.this, MainActivity.class);
            resetFields();
            startActivity(intent);
            finish();
        } else {
            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT).show();
        }
    }

    private Bitmap decodeBase64ToBitmap(String encodedImage) {
        byte[] decodedByte = Base64.decode(encodedImage, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedByte, 0, decodedByte.length);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Clear SharedPreferences when the app is destroyed
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    private void resetFields() {
        emailEditText.getEditText().setText("");
        passwordEditText.getEditText().setText("");
    }
}
