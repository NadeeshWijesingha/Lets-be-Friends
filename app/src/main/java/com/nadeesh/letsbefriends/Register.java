package com.nadeesh.letsbefriends;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.nadeesh.letsbefriends.dbHelpers.usersHelper;

public class Register extends AppCompatActivity {


    EditText register_email, register_username, register_password, register_confirm_password;
    TextView back_to_login;
    Button button_register;

    private usersHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        SharedPreferences sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        boolean isLogged = sh.getBoolean("isLogged", false);

        if(isLogged){
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        }

        back_to_login = findViewById(R.id.back_to_login);
        button_register = findViewById(R.id.buttonRegister);

        register_email = findViewById(R.id.register_email);
        register_username = findViewById(R.id.register_username);
        register_password = findViewById(R.id.register_password);
        register_confirm_password = findViewById(R.id.register_confirm_password);

        DB = new usersHelper(this);



        back_to_login.setOnClickListener(view -> {
            startActivity(new Intent(Register.this, Login.class));
            finish();
        });


        button_register.setOnClickListener(view -> {

            String reg_email =   register_email.getText().toString();
            String reg_username =  register_username.getText().toString();
            String reg_password = register_password.getText().toString();
            String reg_cnfPassword = register_confirm_password.getText().toString();
            Boolean sgValidity = validation(reg_email, reg_username, reg_password, reg_cnfPassword);

            if(sgValidity == true){

                Boolean checkUser = DB.checkUserName(reg_username);
                if (checkUser == false){
                    Boolean insert = DB.insertData( reg_username,reg_email, reg_password);
                    if(insert ==  true){
                        Intent intent = new Intent(getApplicationContext(), Login.class);
                        Toast.makeText(Register.this, "Registration Success", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Registration Failed!", Toast.LENGTH_SHORT).show();
                    }

                }else{
                    Toast.makeText(Register.this, "User Already Exist!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }
    private Boolean validation(String reg_email,String reg_username,String reg_password,String reg_cnfPassword){
        if(reg_email.length() == 0 || reg_username.length() == 0 || reg_password.length() == 0 ||  reg_cnfPassword.length() == 0){
            Toast.makeText(getApplicationContext(),"Empty Input!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!reg_email.contains("@")){
            Toast.makeText(getApplicationContext(),"Please Enter a Valid Email",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(reg_password.length() <6){
            Toast.makeText(getApplicationContext(),"Password must contain at least 6 letters",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else if(!reg_password.equals(reg_cnfPassword)){
            Toast.makeText(getApplicationContext(),"Password miss match!",
                    Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }
}