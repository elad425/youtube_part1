package com.example.youtube.screens;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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

        Window window = getWindow();
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.transparent));

        Button btnLogIn = findViewById(R.id.btn_logIn);

        Intent intent = getIntent();
        user tempUser = intent.getParcelableExtra("user");
        videos = intent.getParcelableArrayListExtra("videos");
        if (tempUser != null){
            user = tempUser;
            TextView username = findViewById(R.id.username);
            TextView user_email = findViewById(R.id.user_email);
            ShapeableImageView user_pic = findViewById(R.id.user_pic);

            username.setText(user.getName());
            user_email.setText(user.getEmail());
            String creator_pic = user.getProfile_pic();
            int creatorPicId = getResources().getIdentifier(creator_pic, "drawable",getPackageName());
            if (creatorPicId!=0){
                user_pic.setImageResource(creatorPicId);
            }
            else{
                user_pic.setImageURI(Uri.parse(creator_pic));
            }

            btnLogIn.setText(R.string.logOut);
            btnLogIn.setOnClickListener(v -> onConfirmClick());

        } else {
            TextView username = findViewById(R.id.username);
            TextView user_email = findViewById(R.id.user_email);
            ShapeableImageView user_pic = findViewById(R.id.user_pic);

            username.setText(getString(R.string.guest));
            user_email.setText(R.string.guest);
            user_pic.setImageResource(R.drawable.ic_account);

            btnLogIn.setText(R.string.login);
            btnLogIn.setOnClickListener(v -> {
                Intent i = new Intent(this, LogIn.class);
                startActivity(i);
            });
        }

        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        bottomNav.setSelectedItemId(R.id.navigation_profile);
        bottomNav.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.navigation_home){
                Intent i = new Intent(ProfilePage.this, MainActivity.class);
                i.putExtra("video_list", videos);
                i.putExtra("user",user);
                startActivity(i);
                return true;
            }  else if (item.getItemId() == R.id.navigation_add_video){
                if (user != null) {
                    Intent i = new Intent(ProfilePage.this, AddVideoActivity.class);
                    i.putExtra("videos", videos);
                    i.putExtra("user",user);
                    startActivity(i);
                    return true;
                } else {
                    Toast.makeText(ProfilePage.this,
                            "please log in in order to add a video", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(ProfilePage.this, LogIn.class);
                    startActivity(i);
                }
            }
            return false;
        });
    }

    public void onConfirmClick() {
        user=null;
        Toast.makeText(this, "you logged out",
                Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("user",user);
        i.putExtra("video_list",videos);
        startActivity(i);
    }

}