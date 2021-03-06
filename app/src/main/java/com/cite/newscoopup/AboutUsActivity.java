package com.cite.newscoopup;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AboutUsActivity extends AppCompatActivity {
    private TextView userNameEdt, pwdEdt, cnfPwdEdt;
    private Button logoutBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;
    private ImageView fb, email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about_us);

        fb = findViewById (R.id.idFB);
        email = findViewById (R.id.idEmail);
        userNameEdt = findViewById (R.id.idEdtUserName);
        pwdEdt = findViewById (R.id.idEdtPwd);
        cnfPwdEdt = findViewById (R.id.idedtCnfPwd);
        // logoutBtn = findViewById (R.id.idBtnLogout);
        loadingPB = findViewById (R.id.idPBLoading);
        loginTV = findViewById (R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance ();

        user = FirebaseAuth.getInstance ().getCurrentUser ();
        reference = FirebaseDatabase.getInstance ().getReference ("Users");
        userID = user.getUid ();

        fb.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                gotoUrl("https://www.facebook.com/pofile.php?id=100067156040939");
            }
        });
        email.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                gotoUrl("https://mail.google.com");
            }
        });
    }

    private void gotoUrl(String s) {
        Uri uri = Uri.parse (s);
        startActivity (new Intent (Intent.ACTION_VIEW,uri));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater ().inflate (R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId ();
        switch (id) {
            case R.id.idEdtDashboard:
                startActivity (new Intent (AboutUsActivity.this, AdminDashboad.class));
                return true;


            //news headlines
            case R.id.idEdtHeadlines:
                startActivity (new Intent (AboutUsActivity.this, ApiMainActivity.class));
                return true;


            case R.id.idEdtProfile1:
                startActivity (new Intent (AboutUsActivity.this, EditProfileActivity.class));
                return true;


            case R.id.idEdtAbout:
                startActivity (new Intent (AboutUsActivity.this, AboutUsActivity.class));
                return true;


            case R.id.idEdtLogout:
                Toast.makeText (AboutUsActivity.this, "Log out successful", Toast.LENGTH_SHORT).show ();
                mAuth.signOut();
                Intent i = new Intent (AboutUsActivity.this, LoginActivity.class);
                startActivity (i);
                this.finish ();
                return true;
            default:

                return super.onOptionsItemSelected (item);
        }

    }
}