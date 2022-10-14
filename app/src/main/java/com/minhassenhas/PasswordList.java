package com.minhassenhas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.minhassenhas.adapters.PasswordObjectAdapter;
import com.minhassenhas.database.MyDataBaseHelper;
import com.minhassenhas.models.PasswordObject;
import com.minhassenhas.repositories.PasswordObjectRepository;

import java.util.ArrayList;

public class PasswordList extends AppCompatActivity {

    private FloatingActionButton fabAddPasswordObject;
    private RecyclerView recyclerViewPasswords;
    private PasswordObjectAdapter.RecyclerViewClickListener listener;

    ArrayList<PasswordObject> passwords;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_list);

        passwords = PasswordObjectRepository.getInstance().getAll(PasswordList.this);

        if(passwords.size() == 0)
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

        fabAddPasswordObject = findViewById(R.id.fab_add_password);
        recyclerViewPasswords = findViewById(R.id.recycler_passwords);

        fabAddPasswordObject.setOnClickListener(
                v -> {

                    Intent intent = new Intent(
                            getApplicationContext(),
                            AddPasswordObjectActivity.class
                    );
                    startActivity(intent);

                }
        );

        recyclerViewPasswords.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));

    }

    @Override
    protected void onResume() {
        super.onResume();
        passwords.clear();

        passwords = PasswordObjectRepository.getInstance().getAll(PasswordList.this);
        PasswordObjectAdapter adapter = new PasswordObjectAdapter(passwords, listener);

        adapter.notifyDataSetChanged();

        setOnClickListener();
        recyclerViewPasswords.setAdapter(adapter);
    }

    int counter = 0;

    @Override
    public void onBackPressed() {
        counter++;
        if(counter == 2)
            super.onBackPressed();
    }

    private void setOnClickListener() {
        listener = (v, position) -> {
            Intent intent = new Intent(getApplicationContext(), AddPasswordObjectActivity.class);
            intent.putExtra("index_text", passwords.get(position).getId());
            intent.putExtra("input_text_name", passwords.get(position).getName());
            intent.putExtra("input_text_email", passwords.get(position).getEmail());
            intent.putExtra("input_text_account", passwords.get(position).getConta());
            intent.putExtra("input_text_password", passwords.get(position).getPassword());
            intent.putExtra("input_text_description", passwords.get(position).getDescription());
            startActivity(intent);
        };
    }
}