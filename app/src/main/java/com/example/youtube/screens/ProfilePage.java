package com.example.youtube.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.youtube.MainActivity;
import com.example.youtube.R;
import com.example.youtube.entities.user;
import com.example.youtube.entities.video;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class ProfilePage extends AppCompatActivity {

    private user user;
    private ArrayList<video> videos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);

        setupWindow();
        initializeData();
        setupUI();
        setupBottomNavigation();
    }

    private void setupWindow() {
        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));
    }

    private void initializeData() {
        Intent intent = getIntent();
        user = intent.getParcelableExtra("user");
        videos = intent.getParcelableArrayListExtra("videos");
        if (videos == null) {
            videos = new ArrayList<>();
        }
    }

    private void setupUI() {
        Button btnLogIn = findViewById(R.id.btn_logIn);
        TextView username = findViewById(R.id.username);
        TextView userEmail = findViewById(R.id.user_email);
        ShapeableImageView userPic = findViewById(R.id.user_pic);

        if (user != null) {
            displayUserInfo(username, userEmail, userPic, btnLogIn);
        } else {
            displayGuestInfo(username, userEmail, userPic, btnLogIn);
        }
    }

    private void displayUserInfo(TextView username, TextView userEmail, ShapeableImageView userPic, Button btnLogIn) {
        username.setText(user.getName());
        userEmail.setText(user.getEmail());
        String profilePic = user.getProfile_pic();
        int profilePicId = getResources().getIdentifier(profilePic, "drawable", getPackageName());
        if (profilePicId != 0) {
            userPic.setImageResource(profilePicId);
        } else {
            userPic.setImageURI(Uri.parse(profilePic));
        }

        btnLogIn.setText(R.string.logOut);
        btnLogIn.setOnClickListener(v -> onConfirmClick());
    }

    private void displayGuestInfo(TextView username, TextView userEmail, ShapeableImageView userPic, Button btnLogIn) {
        username.setText(getString(R.string.guest));
        userEmail.setText(R.string.guest);
        userPic.setImageResource(R.drawable.ic_account);

        btnLogIn.setText(R.string.login);
        btnLogIn.setOnClickListener(v -> {
            Intent i = new Intent(this, LogIn.class);
            startActivity(i);
        });
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home) {
                navigateToHome();
                return true;
            }
            else if (item.getItemId() == R.id.navigation_add_video) {
                navigateToAddVideo();
                return true;
            }
            return false;
        });
    }

    private void navigateToHome() {
        Intent intent = new Intent(ProfilePage.this, MainActivity.class);
        intent.putExtra("video_list", videos);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    private void navigateToAddVideo() {
        if (user != null) {
            Intent intent = new Intent(ProfilePage.this, AddVideoActivity.class);
            intent.putExtra("videos", videos);
            intent.putExtra("user", user);
            startActivity(intent);
        } else {
            Toast.makeText(ProfilePage.this, "Please log in to add a video", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(ProfilePage.this, LogIn.class);
            startActivity(intent);
        }
    }

    public void onConfirmClick() {
        user = null;
        Toast.makeText(this, "You logged out", Toast.LENGTH_SHORT).show();
        navigateToHome();
    }
}
