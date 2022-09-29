package com.example.toDoGame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private Adapter adapter;
    private List<Modelclass> userlist;

    int n=0;
    int count = 1000;
    int time =15000;
    int counter,timeout=0;
    int lifevalue=3;
    int levelvalue = 1;
    int onfinish=0;
    int onfinish2=0;
    private TextView timer,level,life;
    private CountDownTimer countDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.start);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button start = dialog.findViewById(R.id.start);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startmain();
                dialog.cancel();
            }
        });
        Button homepage = dialog.findViewById(R.id.Homepage);
        homepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        dialog.show();


    }

    private void startcount() {
        counter = time/1000;
        countDownTimer = new CountDownTimer(time,1000)
        {

            @Override
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf(counter));
                counter--;
            }

            @Override
            public void onFinish() {
                Toast.makeText(MainActivity.this, "Tempo Esgotado", Toast.LENGTH_SHORT).show();
                timer.setText("0");
                timeout=1;
                if(onfinish!=1 && onfinish2!=1) {
                    showtryagain();
                }


            }
        }.start();
    }


    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }


        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position = viewHolder.getPosition();
            if (timeout != 1 && lifevalue != 0 ) {
                if (adapter.getItemCount() != 1) {
                    switch (direction) {
                        // right side para amarelo
                        case ItemTouchHelper.RIGHT: {
                            if ((userlist.get(position).getColor()).equals("Yellow")) {
                                userlist.remove(position);
                                adapter.notifyDataSetChanged();
                            } else {
                                lifevalue--;
                                life.setText(""+lifevalue);
                                adapter.notifyDataSetChanged();
                            }
                            break;
                        }
                        // left side para esquerda
                        case ItemTouchHelper.LEFT: {
                            if ((userlist.get(position).getColor()).equals("Red")) {
                                userlist.remove(position);
                                adapter.notifyDataSetChanged();
                            } else {
                                lifevalue--;
                                life.setText(""+lifevalue);
                                adapter.notifyDataSetChanged();
                            }
                        }
                    }
                }
                else
                {
                    showwin();
                    onfinish=1;
                }
            }
        else {

                if(onfinish!=1 ) {
                    showtryagain();
                    adapter.notifyDataSetChanged();
                    onfinish2=1;
                }
            }
        }

    };

    // Deseja tentar novamente?
    private void showtryagain() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.tryagaincard);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button tryagain = dialog.findViewById(R.id.tryagain1);
        tryagain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 countDownTimer.cancel();
                 n=0;
                 count = 1000;
                 time =15000;
                 timeout=0;
                 lifevalue=3;
                 levelvalue = 1;
                 onfinish=0;
                 onfinish2=0;
                 life.setText(""+lifevalue);
                initrecyclerview();
                initdata();
                startcount();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    private void showwin() {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.winnercard);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        Button nextlevel = dialog.findViewById(R.id.nextlevel);
        nextlevel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                countDownTimer.cancel();
                levelvalue=levelvalue+1;
                level.setText("Level "+levelvalue);
                n=0;
                count = 1000;
                time =15000;
                timeout=0;
                lifevalue=3;
                time=time+5000;
                n=n+5;
                onfinish=0;
                onfinish=0;
                lifevalue=3;
                life.setText(""+lifevalue);
                initrecyclerview();
                initdata();
                startcount();
                dialog.cancel();
            }
        });
        dialog.show();
    }

    // Colorindo as barras do recyclerview aleatoriamente
    private void initrecyclerview() {
        int i;
        String color;
        Random random=new Random();
        userlist=new ArrayList<>();
        for(i=0;i<20+n;i++)
        {
            if(random.nextInt(6)%2==0)
            {
                color="Yellow";
                userlist.add(new Modelclass(color));
            }
            else
            {
                color="Red";
                userlist.add(new Modelclass(color));
            }
        }

    }
    private void initdata()
    {
        recyclerView=findViewById(R.id.recyclerview);
        layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        adapter=new Adapter(userlist,MainActivity.this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    private void startmain()
    {
        initrecyclerview();
        initdata();
        ItemTouchHelper  itemTouchHelper= new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        startcount();
        timer=findViewById(R.id.Timer);
        life = findViewById(R.id.Life);
        level=findViewById(R.id.Level);
    }
}