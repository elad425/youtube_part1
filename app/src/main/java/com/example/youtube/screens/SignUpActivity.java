package com.example.youtube.screens;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.widget.Button;

import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.youtube.R;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class SignUpActivity extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private TextInputLayout usernameEditText, emailEditText, passwordEditText, confirmPasswordEditText;
    private Button uploadButton, signUpButton,loginButton;
    private Uri imageUri;
    private Bitmap selectedImageBitmap;
    private ArrayList<video> videos;
    private ArrayList<user> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        usernameEditText = findViewById(R.id.sign_up_username);

        emailEditText = findViewById(R.id.sign_up_email);
        passwordEditText = findViewById(R.id.sign_up_password);
        confirmPasswordEditText = findViewById(R.id.sign_up_confirm_password);
        uploadButton = findViewById(R.id.sign_up_upload_button);
        signUpButton = findViewById(R.id.sign_up_button);
        loginButton = findViewById(R.id.sign_up_login_button);
        //profileImageView = findViewById(R.id.profile_image);

        Intent intent = getIntent();
        videos = intent.getParcelableArrayListExtra("video_list");
        users = intent.getParcelableArrayListExtra("users");

        uploadButton.setOnClickListener(v -> openFileChooser());
        signUpButton.setOnClickListener(v -> signUp());
        loginButton.setOnClickListener(v->login());
        clearErrorOnTyping(usernameEditText);
        clearErrorOnTyping(emailEditText);
        clearErrorOnTyping(passwordEditText);
        clearErrorOnTyping(confirmPasswordEditText);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void login(){
        resetFields();
        Intent intent = new Intent(SignUpActivity.this, LogIn.class);
        intent.putParcelableArrayListExtra("video_list", videos);
        intent.putParcelableArrayListExtra("users", users);
        startActivity(intent);
    }

    private void resetFields() {
        usernameEditText.getEditText().setText("");
        emailEditText.getEditText().setText("");
        passwordEditText.getEditText().setText("");
        confirmPasswordEditText.getEditText().setText("");
        imageUri = null;
        selectedImageBitmap = null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void signUp() {
        String username = usernameEditText.getEditText().getText().toString().trim();
        String email = emailEditText.getEditText().getText().toString().trim();
        String password = passwordEditText.getEditText().getText().toString().trim();
        String confirmPassword = confirmPasswordEditText.getEditText().getText().toString().trim();
        if (username.isEmpty()){
            usernameEditText.setError("You need to enter a name");
        }
        if (email.isEmpty()){
            emailEditText.setError("Please enter an email");
        }
        if (password.isEmpty()){
            passwordEditText.setError("Please enter a password");

        }
        if(confirmPassword.isEmpty()){
            confirmPasswordEditText.setError("You need to enter a password confirmation");
        }

        if (username.isEmpty()||email.isEmpty()||password.isEmpty()||confirmPassword.isEmpty()){
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

        if (password.length() < 8 || !password.matches(".*\\d.*")) {
            passwordEditText.setError("Password must be at least 8 characters long and contain at least one number");
            return;
        }

        if (imageUri == null) {
            Toast.makeText(this, "Please upload an image", Toast.LENGTH_SHORT).show();
            return;
        }

        user new_user = new user(username,email,password,imageUri.toString());
        if (users==null){
            users=new ArrayList<>();
        }
        else {
            Log.d("my shit",users.get(0).getEmail());
        }
        users.add(new_user);
        // Proceed with sign-up logic (e.g., send data to server)
//        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString("username", username);
//        editor.putString("email", email);
//        editor.putString("password", password);
//        editor.putString("image", imageUri.toString());
//        editor.apply();

        Toast.makeText(this, "Sign-up successful", Toast.LENGTH_SHORT).show();
        login();
    }


    private String encodeImageToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void clearErrorOnTyping(TextInputLayout textInputLayout) {
        TextInputEditText editText = (TextInputEditText) textInputLayout.getEditText();
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                textInputLayout.setError(null); // Clear error when user starts typing
                textInputLayout.setErrorEnabled(false);
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    @Override
    protected void onDestroy() {
        //todo might cause errors check in case user details gets deleted
        super.onDestroy();
        // Clear SharedPreferences when the app is destroyed
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }
}
