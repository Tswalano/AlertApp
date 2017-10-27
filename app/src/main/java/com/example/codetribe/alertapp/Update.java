package com.example.codetribe.alertapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  Update extends AppCompatActivity {

    EditText Name, Number;
    Button update;
    private DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    String userUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Name = (EditText) findViewById(R.id.name);
        Number = (EditText) findViewById(R.id.number);
        update = (Button) findViewById(R.id.btnUpdate);

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        userUID = mAuth.getCurrentUser().getPhoneNumber().toString();

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String category = intent.getStringExtra("cat");

        if (category.equalsIgnoreCase("Fire Fighter")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(userUID).child("Fire Fighter").child(key);
        } else if (category.equalsIgnoreCase("Emergency")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(userUID).child("Emergency").child(key);
        }
        if (category.equalsIgnoreCase("Crime")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child(userUID).child("Crime").child(key);
        }



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Content person = dataSnapshot.getValue(Content.class);
                Name.setText(person.getName().toString());
                Number.setText(person.getSurname().toString());

                if (TextUtils.isEmpty(Name.getText().toString())) {
                    Name.setError("Name is required");
                    return;
                } else if (TextUtils.isEmpty(Number.getText().toString())) {
                    Number.setError("Cell number required");
                    return;
                }else if(Number.getText().toString().length() < 10){
                    Number.setError("Number must be 10 digits");
                    return;
                }

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        person.setName(Name.getText().toString());
                        person.setSurname(Number.getText().toString());

                        databaseReference.setValue(person);
                        Toast.makeText(getApplicationContext(), "Record Update", Toast.LENGTH_LONG).show();

                        finish();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);

                    }
                });
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
