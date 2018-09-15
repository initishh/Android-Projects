package com.example.initish.tic_tac_toe;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private int player=1;
    int[] gameState={2,2,2,2,2,2,2,2,2};
    int[][] winnningState={{0,1,2},{3,4,5},{6,7,8},{0,3,6},{1,4,7},{2,5,8},{0,4,8},{2,4,6}};
    public void playAgain(View view){
        TextView textView=(TextView)findViewById(R.id.textView);
        Button button=(Button)findViewById(R.id.button);
        textView.setVisibility(View.INVISIBLE);
        button.setVisibility(View.INVISIBLE);

        player=1;
        for(int i=0;i<gameState.length;i++)
            gameState[i]=2;
        ImageView img=(ImageView)findViewById(R.id.imageView2);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView3);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView4);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView5);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView6);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView7);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView8);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView9);
        img.setImageResource(0);
        img=(ImageView)findViewById(R.id.imageView10);
        img.setImageResource(0);

    }
    public void toggle(View view){
         ImageView cross=(ImageView) view;
         TextView textView=(TextView)findViewById(R.id.textView);
         Button button=(Button)findViewById(R.id.button);
         System.out.println(cross.getTag().toString());
         int TappedPlayer=Integer.parseInt(cross.getTag().toString());
         if(gameState[TappedPlayer]==2){
             cross.setTranslationY(-1000f);
             gameState[TappedPlayer]=player;
         if(player==1)
          cross.setImageResource(R.drawable.cross);
         else
             cross.setImageResource(R.drawable.circle);
         int winner=0;
         int flag=1;
         cross.animate().translationYBy(1000f).rotation(360).setDuration(300) ;

             for(int[] x:winnningState)
             {
                 if(((gameState[x[0]]==gameState[x[1]])&&(gameState[x[1]]==gameState[x[2]]))&&(gameState[x[0]]!=2))
                 {
                     flag=0;
                     winner=gameState[x[0]];
                     String Win;
                     if(winner>0)
                         Win="Cross";
                     else
                         Win="Zero";
                     Log.i("info",Win+" has won");
                     textView.setText(Win+" has won!");
                     textView.setVisibility(View.VISIBLE);
                     button.setVisibility(View.VISIBLE);
                     for(int i=0;i<gameState.length;i++)
                         gameState[i]=1;
                     break;
                     }
             }
             int cnt=0;
             for(int i=0;i<gameState.length;i++)
                 if(gameState[i]!=2)
                     cnt++;
             if(cnt==gameState.length&&flag==1)
             {
                 textView.setText("Match Tied!");
                 textView.setVisibility(View.VISIBLE);
                 button.setVisibility(View.VISIBLE);
             }
             }
        player*=-1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
