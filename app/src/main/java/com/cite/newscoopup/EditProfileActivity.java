package com.cite.newscoopup;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class EditProfileActivity extends AppCompatActivity {
    private TextView userNameEdt, pwdEdt, nameEdt;
    private Button logoutBtn;
    private ProgressBar loadingPB;
    private TextView loginTV;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_edit_profile);

        userNameEdt = findViewById (R.id.idEdtUserName);
        pwdEdt = findViewById (R.id.idEdtPwd);
        nameEdt = findViewById (R.id.idEdtName);
        //logoutBtn = findViewById (R.id.idBtnLogout);
        loadingPB = findViewById (R.id.idPBLoading);
        loginTV = findViewById (R.id.idTVLogin);
        mAuth = FirebaseAuth.getInstance ();

        user = FirebaseAuth.getInstance ().getCurrentUser ();
        reference = FirebaseDatabase.getInstance ().getReference ("Users");
        userID = user.getUid ();

        reference.child (userID).addListenerForSingleValueEvent (new ValueEventListener () {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User userProfile = snapshot.getValue (User.class);

                if(userProfile!=null){
                    String userName = userProfile.userName;
                    String pwd = userProfile.pwd;
                    String fullName = userProfile.fullName;

                    userNameEdt.setText (userName);
                    pwdEdt.setText (pwd);
                    nameEdt.setText (fullName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText (EditProfileActivity.this, "Something wrong", Toast.LENGTH_SHORT).show ();
            }
        });
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
                startActivity (new Intent (EditProfileActivity.this, MainActivity.class));
                this.finish ();
                return true;

            case R.id.idEdtProfile:
                startActivity (new Intent (EditProfileActivity.this, EditProfileActivity.class));
                this.finish ();
                return true;


            case R.id.idEdtAbout:
                startActivity (new Intent (EditProfileActivity.this, AboutUsActivity.class));
                this.finish ();
                return true;

            case R.id.idEdtLogout:
                Toast.makeText (EditProfileActivity.this, "Log out successful", Toast.LENGTH_SHORT).show ();
                mAuth.signOut ();
                Intent i = new Intent (EditProfileActivity.this, LoginActivity.class);
                startActivity (i);
                this.finish ();
                return true;
            default:

                return super.onOptionsItemSelected (item);
        }

    }
}