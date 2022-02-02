package com.example.bakelsfirebase;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bakelsfirebase.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ActivityRegistration extends AppCompatActivity {

    // One Button
    private MaterialButton BSelectImage;

    // One Preview Image
    private ImageView IVPreviewImage;

    // constant to compare
    // the activity result code
    int SELECT_PICTURE = 200;

    private TextInputEditText usernameText;
    private TextInputEditText passwordText;
    private TextInputEditText confirmPasswordText;

    private ProgressBar loadingBl;
    private TextView loginTV;

    private MaterialButton registerButton;
    private FirebaseAuth mAuth;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        usernameText = findViewById(R.id.register_username_edit_text);
        passwordText = findViewById(R.id.register_password_edit_text);
        confirmPasswordText = findViewById(R.id.register_confirm_password_edit_text);
        registerButton = findViewById(R.id.register_button);
        loginTV = findViewById(R.id.link_login_text);
        loadingBl = findViewById(R.id.progress_bar_loading);
        mAuth = FirebaseAuth.getInstance();

        //FirebaseDatabase.getInstance().getReference().child("User Permissions").child("Brand").setValue("Inventory");

        loginTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
                startActivity(i);
            }
        });

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBl.setVisibility(View.VISIBLE);
                String username = usernameText.getText().toString();
                String password = passwordText.getText().toString();
                String confirmPassword = confirmPasswordText.getText().toString();



                if(!password.equals(confirmPassword)) {
                    Toast.makeText(ActivityRegistration.this, "Please enter the same password, check both password field", Toast.LENGTH_LONG).show();
                } else if(TextUtils.isEmpty(username) && TextUtils.isEmpty(password) && TextUtils.isEmpty(confirmPassword)) {
                    Toast.makeText(ActivityRegistration.this, "Please add your credentials", Toast.LENGTH_LONG).show();
                } else {
                    //addPermission();
                    mAuth.createUserWithEmailAndPassword(username, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){



                                //FirebaseDatabase.getInstance().getReference().child("User Permissions").child(username).setValue("Inventory");

                                loadingBl.setVisibility(View.GONE);
                                Toast.makeText(ActivityRegistration.this, "User Register successfully", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(ActivityRegistration.this, ActivityLogin.class);
                                startActivity(i);
                                finish();
                            } else {
                                loadingBl.setVisibility(View.GONE);
                                Toast.makeText(ActivityRegistration.this, "Register failure, please try again", Toast.LENGTH_LONG).show();
                            }
                        }
                    });

                }
            }
        });
    }

    void imageChooser() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                }
            }
        }
    }

//    public void addPermission(){
//        HashMap<String, Object> map = new HashMap<>();
//        map.put(usernameText.getText().toString(),"Inventory");
//
//        FirebaseDatabase.getInstance().getReference().child("User Permissions")
//                .push()
//                .setValue(map)
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        Toast.makeText(ActivityRegistration.this, "Permisos añadidos correctamente.", Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ActivityRegistration.this, "Error al añadir permisos.", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }
}
