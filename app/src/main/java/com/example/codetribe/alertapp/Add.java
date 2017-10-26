package com.example.codetribe.alertapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {

    EditText txtName, txtSurname;
    Button btnSave;
    Spinner spinner;
    Content person = new Content();
    String getSelected;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        //databaseReference = FirebaseDatabase.getInstance().getReference().child("Contacts");

        btnSave = (Button) findViewById(R.id.btnsave);
        txtName = (EditText) findViewById(R.id.name);
        txtSurname = (EditText) findViewById(R.id.surname);
        spinner = (Spinner) findViewById(R.id.options);

        getSelected = spinner.getSelectedItem().toString();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                person.setName(txtName.getText().toString());
                person.setSurname(txtSurname.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference();

                databaseReference.child(spinner.getSelectedItem().toString()).push().setValue(person);
                Toast.makeText(getApplicationContext(), spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
