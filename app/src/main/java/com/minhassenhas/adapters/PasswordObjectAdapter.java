package com.minhassenhas.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.minhassenhas.R;
import com.minhassenhas.models.PasswordObject;
import com.minhassenhas.repositories.PasswordObjectRepository;

import java.util.ArrayList;

public class PasswordObjectAdapter extends RecyclerView.Adapter<PasswordObjectAdapter.PasswordViewHolder> {

    private ArrayList<PasswordObject> passwords;
    private RecyclerViewClickListener listener;


    public PasswordObjectAdapter(ArrayList<PasswordObject> passwords, RecyclerViewClickListener listener){
        this.passwords = passwords;
        this.listener = listener;
    }

    @NonNull
    @Override
    public PasswordViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        View layout = layoutInflater.inflate(R.layout.view_item_password, parent,false);

        return new PasswordViewHolder(layout, layout.getContext()).linkAdapter(this);
    }

    @Override
    public void onBindViewHolder(@NonNull PasswordViewHolder holder, int position) {
        PasswordObject password = passwords.get(position);

        TextView textViewName = holder.itemView.findViewById(R.id.text_password_name);
        textViewName.setText(password.getName());


        TextView textViewEmail = holder.itemView.findViewById(R.id.text_password_email);
        textViewEmail.setText(password.getEmail());

        //fazer o banco puxar esses dados...

        //TextView textViewAccount = holder.itemView.findViewById(R.id.te);
        //textViewName.setText(password.getName());


        //TextView textViewPassword = holder.itemView.findViewById(R.id.text_password_name);
        //textViewName.setText(password.getName());

        //TextView textViewDescription = holder.itemView.findViewById(R.id.text_password_name);
        //textViewName.setText(password.getName());

    }

    @Override
    public int getItemCount() {
        return passwords.size();
    }

    class PasswordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private PasswordObjectAdapter adapter;

        public PasswordViewHolder(@NonNull View itemView, Context context){
            super(itemView);

            itemView.findViewById(R.id.card_view).setOnLongClickListener(view -> {
                PasswordObject x = adapter.passwords.get(getAdapterPosition());
                PasswordObjectRepository.getInstance().delete(x, context);
                adapter.notifyItemRemoved(getAdapterPosition());
                return true;
            });

            itemView.setOnClickListener(this);

        }

        public PasswordViewHolder linkAdapter(PasswordObjectAdapter adapter){
            this.adapter = adapter;
            return this;
        }

        @Override
        public void onClick(View view){
            listener.onClick(view, getAdapterPosition());
        }

    }

    public interface RecyclerViewClickListener{
        void onClick(View v, int position);
    }

    void confirmDialog(PasswordObjectAdapter adapter, int index,Context context)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);

        builder.setTitle("Deletar " + "title_name" + "?");
        builder.setMessage("Tem certeza que deseja deletar" + "title_name" + "?");

        builder.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        builder.setNegativeButton("Nao", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }

}
