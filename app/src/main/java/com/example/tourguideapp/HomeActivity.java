package com.example.tourguideapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    Button btnLogOut, btnSearch;
    EditText txtSearch;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        btnSearch = findViewById(R.id.btnSearch);
        btnLogOut = findViewById(R.id.btnLogOut);
        txtSearch = findViewById(R.id.txtSearch);

        btnLogOut.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnLogOut:
                FirebaseAuth.getInstance().signOut();
                finish();
                intent = new Intent(HomeActivity.this, MainActivity.class);
                break;

            case R.id.btnSearch:

                /**TODO - Intent for Search Page

                 intent = new Intent(MainActivity.this, SearchActivity.class);
                 */
                break;

            default:
                Toast.makeText(this, "Invalid click operation!", Toast.LENGTH_SHORT).show();
                break;
        }

        startActivity(intent);
    }
}
