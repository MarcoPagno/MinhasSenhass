package com.minhassenhas;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minhassenhas.database.MyDataBaseHelper;
import com.minhassenhas.repositories.PasswordObjectRepository;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutPassword;
    private TextInputEditText inputEditTextPassword;

    private Button buttonEntrar;
    private Button buttonCriar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        verifyIfPasswordExists();

        inputLayoutPassword = findViewById(R.id.textInputLayout_Senha);
        inputEditTextPassword = findViewById(R.id.textInputEditText_Senha);

        buttonEntrar = findViewById(R.id.button_Entrar);
        buttonCriar = findViewById(R.id.button_Criar);

        buttonEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(verifyPassword(inputEditTextPassword.getText().toString()))
                {
                    Intent intent = new Intent(
                            getApplicationContext(),
                            PasswordList.class
                    );
                    startActivity(intent);
                }
                else
                {
                    inputLayoutPassword.setError("Senha Incorreta!");
                }
            }
        });

        buttonCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PasswordObjectRepository.getInstance().saveMainPassword(inputEditTextPassword.getText().toString() , MainActivity.this);

                Intent intent = new Intent(
                        getApplicationContext(),
                        PasswordList.class
                );
                startActivity(intent);
            }
        });


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(verifyIfPasswordExists()) {
            buttonEntrar.setVisibility(View.VISIBLE);
            buttonCriar.setVisibility(View.INVISIBLE);
        }
        else {
            buttonEntrar.setVisibility(View.INVISIBLE);
            buttonCriar.setVisibility(View.VISIBLE);
        }

    }

    private boolean verifyIfPasswordExists(){

        MyDataBaseHelper myDB = new MyDataBaseHelper(MainActivity.this);

        return myDB.verifyIfMainPasswordExists();
    }

    private boolean verifyPassword(String inputedPassword){

        MyDataBaseHelper myDB = new MyDataBaseHelper(MainActivity.this);

        if(verifyIfPasswordExists())
        {
            return myDB.verifyPassword(inputedPassword);
        }

        return false;
    }


}