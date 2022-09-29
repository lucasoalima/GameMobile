package com.example.toDoGame;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private List<Modelclass>userlist;
    private Context context;

    Adapter(List<Modelclass> userlist, Context context)
    {
        this.context=context;
        this.userlist=userlist;
    }

    @NonNull
    @org.jetbrains.annotations.NotNull
    @Override
    public Adapter.ViewHolder onCreateViewHolder(@NonNull @org.jetbrains.annotations.NotNull ViewGroup parent, int viewType) {
       View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.bars,parent,false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull @org.jetbrains.annotations.NotNull Adapter.ViewHolder holder, int position) {
        // Getter de cada posicao da lista
        String Color = userlist.get(position).getColor();

        // Setter da cor
        if (Color.equals("Yellow"))
        {
           holder.linearLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.Yellow));
        }
        else if(Color.equals("Red"))
        {
            holder.linearLayout.setBackgroundTintList(context.getResources().getColorStateList(R.color.Red));
        }
    }

    @Override
    public int getItemCount() {
        return userlist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private LinearLayout linearLayout;
        public ViewHolder(@NonNull @org.jetbrains.annotations.NotNull View itemView) {
            super(itemView);
            linearLayout=itemView.findViewById(R.id.bars);
        }
    }
}
