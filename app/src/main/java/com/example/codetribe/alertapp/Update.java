package com.example.codetribe.alertapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class  Update extends AppCompatActivity {

    EditText Name, Number;
    Button update;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        Name = (EditText) findViewById(R.id.name);
        Number = (EditText) findViewById(R.id.number);
        update = (Button) findViewById(R.id.btnUpdate);

        Intent intent = getIntent();
        String key = intent.getStringExtra("key");
        String category = intent.getStringExtra("cat");

        if (category.equalsIgnoreCase("Fire Fighter")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Fire Fighter").child(key);
        } else if (category.equalsIgnoreCase("Emergency")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Emergency").child(key);
        }
        if (category.equalsIgnoreCase("Crime")) {
            databaseReference = FirebaseDatabase.getInstance().getReference().child("Crime").child(key);
        }



        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                final Content person = dataSnapshot.getValue(Content.class);
                Name.setText(person.getName().toString());
                Number.setText(person.getSurname().toString());

                update.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        person.setName(Name.getText().toString());
                        person.setSurname(Number.getText().toString());

                        databaseReference.setValue(person);
                        Toast.makeText(getApplicationContext(), "Record Update", Toast.LENGTH_LONG).show();

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
