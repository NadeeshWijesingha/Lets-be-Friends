package com.nadeesh.letsbefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nadeesh.letsbefriends.dbHelpers.usersHelper;

public class Login extends AppCompatActivity {


    EditText login_username, login_password;
    TextView back_to_register;
    Button button_login;

    usersHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        button_login = findViewById(R.id.button_login);

        back_to_register = findViewById(R.id.back_to_register);
        login_username = findViewById(R.id.login_username);
        login_password = findViewById(R.id.login_password);
        DB = new usersHelper(this);



        back_to_register.setOnClickListener(view -> {
            startActivity(new Intent(Login.this, Register.class));
            finish();
        });


        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SharedPreferences sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
                SharedPreferences.Editor myEdit = sharedPreferences.edit();

                String lg_username = login_username.getText().toString();
                String lg_password = login_password.getText().toString();
                Boolean lg_validation = validation(lg_username, lg_password);

                if (lg_validation == true){
                    Boolean checkUser =  DB.checkUserName(lg_username);
                    if (checkUser == true){
                        Boolean auth =  DB.checkCredentials(lg_username,lg_password);
                        if (auth == true){

                            myEdit.putString("username", lg_username);
                            myEdit.putBoolean("isLogged", true);
                            myEdit.commit();

                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            Toast.makeText(Login.this, "Login Success", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Login.this, "Wrong password!", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        Toast.makeText(Login.this, "No such user in the system!", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });

    }
    private boolean validation(String email, String password){
        if(email.length() == 0 || password.length() == 0){
            Toast.makeText(getApplicationContext(),"Fill all the fields!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if (password.length() < 6){
            Toast.makeText(getApplicationContext(),"Password must contain at least 6 letters",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}