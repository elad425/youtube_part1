package com.example.youtube.screens;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtube.R;

import java.io.IOException;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private EditText usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button uploadButton, signUpButton;
    private Uri imageUri;
    //private ImageView profileImageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        usernameEditText = findViewById(R.id.username);
        emailEditText = findViewById(R.id.email);
        passwordEditText = findViewById(R.id.password);
        confirmPasswordEditText = findViewById(R.id.confirm_password);
        uploadButton = findViewById(R.id.upload_button);
        signUpButton = findViewById(R.id.sign_up_button);
        //profileImageView = findViewById(R.id.profile_image);

        uploadButton.setOnClickListener(v -> openFileChooser());
        signUpButton.setOnClickListener(v -> signUp());
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
//            try {
//                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
//                profileImageView.setImageBitmap(bitmap);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
        }
    }

    private void signUp() {
        String username = usernameEditText.getText().toString().trim();
        String email = emailEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getText().toString().trim();

        if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        // Proceed with sign-up logic (e.g., send data to server)
        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show();
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.putString("email", email);
        editor.putString("password", password);
        editor.apply();

        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(SignUpActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}