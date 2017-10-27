package com.example.codetribe.alertapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Add extends AppCompatActivity {

    EditText txtName, txtSurname;
    Button btnSave;
    Spinner spinner;
    Content person = new Content();
    String getSelected;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        btnSave = (Button) findViewById(R.id.btnsave);
        txtName = (EditText) findViewById(R.id.name);
        txtSurname = (EditText) findViewById(R.id.surname);
        spinner = (Spinner) findViewById(R.id.options);

        getSelected = spinner.getSelectedItem().toString();

        databaseReference = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(txtName.getText().toString())) {
                    txtName.setError("Name is required");
                    return;
                } else if (TextUtils.isEmpty(txtSurname.getText().toString())) {
                    txtSurname.setError("Cell number required");
                    return;
                } else if (txtSurname.getText().toString().length() < 10) {
                    txtSurname.setError("Number must be 10 digits");
                    return;
                }

                person.setName(txtName.getText().toString());
                person.setSurname(txtSurname.getText().toString());
                databaseReference = FirebaseDatabase.getInstance().getReference();
                String userUID = mAuth.getCurrentUser().getPhoneNumber().toString();

                databaseReference.child(userUID).child(spinner.getSelectedItem().toString()).push().setValue(person);
                Toast.makeText(getApplicationContext(), "User " + userUID + " " + spinner.getSelectedItem().toString(), Toast.LENGTH_LONG).show();

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
