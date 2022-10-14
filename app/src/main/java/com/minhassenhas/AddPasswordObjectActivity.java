package com.minhassenhas;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.minhassenhas.models.PasswordObject;
import com.minhassenhas.repositories.PasswordObjectRepository;

public class AddPasswordObjectActivity extends AppCompatActivity {

    private TextInputLayout inputLayoutName;
    private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutAccount;
    private TextInputLayout inputLayoutPassword;
    private TextInputLayout inputLayoutDescription;

    private TextInputEditText inputEditTextName;
    private TextInputEditText inputEditTextEmail;
    private TextInputEditText inputEditTextAccount;
    private TextInputEditText inputEditTextPassword;
    private TextInputEditText inputEditTextDescription;

    private Button buttonAddGift;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_password_object);

        inputLayoutName = findViewById(R.id.input_layout_name);
        inputLayoutEmail = findViewById(R.id.input_layout_email);
        inputLayoutAccount = findViewById(R.id.input_layout_account);
        inputLayoutPassword = findViewById(R.id.input_layout_password);
        inputLayoutDescription = findViewById(R.id.input_layout_description);

        inputEditTextName = findViewById(R.id.input_text_name);
        inputEditTextEmail = findViewById(R.id.input_text_email);
        inputEditTextAccount = findViewById(R.id.input_text_account);
        inputEditTextPassword = findViewById(R.id.input_text_password);
        inputEditTextDescription = findViewById(R.id.input_text_description);

        buttonAddGift = findViewById(R.id.button_add_gift);

        ActionBar ab = getSupportActionBar();

        Bundle extras = getIntent().getExtras();
        if(extras != null){
            inputEditTextName.setText(extras.getString("input_text_name"));
            inputEditTextEmail.setText(extras.getString("input_text_email"));
            inputEditTextAccount.setText(extras.getString("input_text_account"));
            inputEditTextPassword.setText(extras.getString("input_text_password"));
            inputEditTextDescription.setText(extras.getString("input_text_description"));
            if(ab != null)
                ab.setTitle(extras.getString("input_text_name"));
        }

        if(ab != null)
            ab.setTitle("Nova senha");

        buttonAddGift.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(extras != null)
                    saveGift(extras.getInt("index_text"));
                else
                    saveGift(-1);
            }
        });

    }

    private void saveGift(int editContent){

        String name = inputEditTextName.getText().toString();
        String email = inputEditTextEmail.getText().toString();
        String conta = inputEditTextAccount.getText().toString();
        String passWord = inputEditTextPassword.getText().toString();
        String description = inputEditTextDescription.getText().toString();

        if(name.isEmpty()){
            inputLayoutName.setError("Favor inserir nome");
            return;
        }
        if(passWord.isEmpty()){
            inputEditTextPassword.setError("Favor inserir Senha");
            return;
        }

        PasswordObject password = new PasswordObject(
                editContent,
                name,
                email,
                conta,
                passWord,
                description
        );

        if (editContent == -1)
            PasswordObjectRepository.getInstance().save(password, AddPasswordObjectActivity.this );
        else
            PasswordObjectRepository.getInstance().update(editContent, password, AddPasswordObjectActivity.this);

        onBackPressed();
    }
}