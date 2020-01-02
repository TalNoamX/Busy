package com.example.busy.restaurant.update;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.busy.MainActivity;
import com.example.busy.R;
import com.example.busy.restaurant.Rforms.Restaurant_Form;
import com.example.busy.users.Home_users;
import com.example.busy.users.Profile_Update.Edit_address;
import com.example.busy.users.Profile_Update.Edit_name;
import com.example.busy.users.Profile_Update.Edit_password;
import com.example.busy.users.personal_settings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class rest_update extends AppCompatActivity {
    private TextView edit_description;
    private TextView edit_isKosher;
    private TextView edit_Type;
    private TextView edit_Location;
    private TextView edit_Name;
    private TextView edit_Phone;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private DatabaseReference ref_users;
    private Button logout_btn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rest_update);

        //init the vals
        ref_users = FirebaseDatabase.getInstance().getReference("Restaurant");
        edit_description = findViewById(R.id.Description_view);
        edit_isKosher = findViewById(R.id.isKosher_view);
        edit_Type = findViewById(R.id.Type_view);
        edit_Location = findViewById(R.id.Location_view);
        edit_Name = findViewById(R.id.Name_view);
        edit_Phone = findViewById(R.id.Phone_view);
        logout_btn = findViewById(R.id.Logout_btn);

        //this will make the relevant rest data to appear in the activity
        ref_users.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Restaurant_Form curr_user = dataSnapshot.child(user.getUid()).getValue(Restaurant_Form.class);
                edit_description.setText("Description: " + curr_user.getDescription());
                edit_isKosher.setText("Kosher: " + curr_user.getKosher());
                edit_Location.setText("Location: " + curr_user.getLocation());
                edit_Name.setText("Name: " + curr_user.getName());
                edit_Phone.setText("Phone: " + curr_user.getPhone());
                edit_Type.setText("Type: " + curr_user.getType());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        //EDIT DESCRIPTION BUTTON
        edit_description.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ED = new Intent(rest_update.this, Edit_Desc.class);
                startActivity(i_ED);
            }
        });

        //EDIT KOSHER BUTTON
        edit_isKosher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_iK = new Intent(rest_update.this, Edit_Kosher.class);
                startActivity(i_iK);
            }
        });

        //EDIT TYPE BUTTON
        edit_Type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_ET = new Intent(rest_update.this, Edit_Type.class);
                startActivity(i_ET);
            }
        });

        //EDIT LOCATION BUTTON
        edit_Location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EL = new Intent(rest_update.this, Edit_Location.class);
                startActivity(i_EL);
            }
        });

        //EDIT NAME BUTTON
        edit_Name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EN = new Intent(rest_update.this, Edit_Name.class);
                startActivity(i_EN);
            }
        });

        //EDIT PHONE BUTTON
        edit_Phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i_EP = new Intent(rest_update.this, Edit_Phone.class);
                startActivity(i_EP);
            }
        });

        //LOGOUT BUTTON
        logout_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent i_LO = new Intent(rest_update.this, MainActivity.class);
                startActivity(i_LO);
            }
        });
    }
}