package com.example.a2048essai1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class jeu extends AppCompatActivity {


    HashMap<String, Integer> map= null;
    HashMap<String, Integer> mapprec= null;
    GridView grille=null;
    public jeu(HashMap m)
    {
        map=m;
    }
    public jeu()
    {
        map=new HashMap<>();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jeu);
        grille=(GridView)findViewById(R.id.gamebackground);
        //initialise la partie avec 2 nombres aléatoires sur des case placées sur la grille
        Random aléa1=new Random();
        int a1 = aléa1.nextInt(16-1+1)+1;
        Random aléa2=new Random();
        int a2 = aléa2.nextInt(16-1+1)+1;
        if(a1==a2 && a2!=16)
        {
            a2++;
        }
        else
        {
            if(a1==a2)
            {
                a2--;
            }
        }

        String[] nombres = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
        Random a3 = new Random();
        Random a4 = new Random();
        int[] aléatoire={2,4};
        int ind1=a3.nextInt(aléatoire.length);
        int ind2=a4.nextInt(aléatoire.length);

        for(int i=0 ;i < nombres.length;i++)
        {
            if(Integer.parseInt(nombres[i])==a1)
            {
                map.put(nombres[i],aléatoire[ind1]);
            }
            if(Integer.parseInt(nombres[i])==a2)
            {
                map.put(nombres[i],aléatoire[ind2]);
            }
            if(Integer.parseInt(nombres[i])!=a2 && Integer.parseInt(nombres[i])!=a1)
            {
                map.put(nombres[i],0);
            }

        }

        final List<String> liste = new ArrayList<String>(Arrays.asList(nombres));

        changerGrille(liste);

        grille.setOnTouchListener(new TouchListener(jeu.this){

            //déplace les case quand un mouvement swipe est effectué
            public void onSwipeTop() {
                mapprec = new HashMap<>(map);
                int k = 0;
                while(!(checkTop(1) && checkTop(2) && checkTop(3) && checkTop(4))) {
                    k = 1;
                    for (int i = 16; i > 4; i--) {
                        int j = i;
                        if (j > 4 && map.get(j + "") == map.get((j - 4) + "")) {
                            map.put((j - 4) + "", map.get(j + "") + map.get((j - 4) + ""));
                            map.put(j + "", 0);
                        } else if (j > 4 && map.get((j - 4) + "") == 0) {

                            map.put((j - 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }

                changerGrille(liste);
                nombreAléatoire();
                checkSwipeOk();
            }
            public void onSwipeRight() {
                mapprec=new HashMap<>(map);
                int k=0;
                int l=0;
                while(l==0 || !(checkRight(4) && checkRight(8) && checkRight(12) && checkRight(16))) {
                    l=1;
                    for (int i = 1; i < 17; i++) {
                        int j = i;


                        if (k == 0 && map.get(j + "") == map.get((j + 1) + "")) {
                            map.put((j + 1) + "", map.get(j + "") + map.get((j + 1) + ""));
                            map.put(j + "", 0);
                        } else if (k == 0 && map.get((j + 1) + "") == 0) {

                            map.put((j + 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                        if ((j / 4) != (j + 1) / 4) {
                            k = 1;
                        } else {
                            k = 0;
                        }
                    }
                }
                changerGrille(liste);
                nombreAléatoire();
                checkSwipeOk();
            }
            public void onSwipeLeft() {
                mapprec=new HashMap<>(map);
                int k=0;
                int l=0;
                while(l==0 || !(checkLeft(1) && checkLeft(5) && checkLeft(9) && checkLeft(13))) {
                    l=1;
                    for (int i = 16; i > 1; i--) {
                        int j = i;
                        if (j == 13 || j == 9 || j == 5) {
                            k = 1;
                        } else {
                            k = 0;
                        }


                        if (k == 0 && map.get(j + "") == map.get((j - 1) + "")) {
                            map.put((j - 1) + "", map.get(j + "") + map.get((j - 1) + ""));
                            map.put(j + "", 0);
                        } else if (k == 0 && map.get((j - 1) + "") == 0) {

                            map.put((j - 1) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }

                    }
                }
                changerGrille(liste);
                nombreAléatoire();
                checkSwipeOk();
            }
            public void onSwipeBottom() {
                mapprec=new HashMap<>(map);
                int k=0;
                while (k==0 || !(checkBottom(13) && checkBottom(14) && checkBottom(15) && checkBottom(16))) {
                    k=1;
                    for (int i = 1; i < 13; i++) {
                        int j = i;

                        if (j < 13 && map.get(j + "") == map.get((j + 4) + "")) {

                            map.put((j + 4) + "", map.get(j + "") + map.get((j + 4) + ""));
                            map.put(j + "", 0);
                        } else if (j < 13 && map.get((j + 4) + "") == 0) {

                            map.put((j + 4) + "", map.get(j + ""));
                            map.put(j + "", 0);
                        }
                    }
                }

                changerGrille(liste);
                nombreAléatoire();
                checkSwipeOk();
            }
        });

    }

    //vérifie si les mouvements swipe effectués sont conformes et ne posent pas de problème par rapport aux placement des case sur la grille
    public boolean checkTop(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0)
        {
            return true;
        }
        else if(map.get((loc+4)+"")==0 && map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+8))==0 && map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+12))==0 && map.get(""+(loc+4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc+8))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc+4)+"")!=0 && map.get(""+(loc+8))!=0 && map.get(""+(loc+12))!=0)
        {
            return true;
        }
        return false;
    }
    public boolean checkBottom(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0)
        {
            return true;
        }
        else if(map.get((loc-4)+"")==0 && map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-8))==0 && map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-12))==0 && map.get(""+(loc-4))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-8))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-4)+"")!=0 && map.get(""+(loc-8))!=0 && map.get(""+(loc-12))!=0)
        {
            return true;
        }
        return false;
    }
    public boolean checkRight(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0)
        {
            return true;
        }
        else if(map.get((loc-1)+"")==0 && map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-2))==0 && map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc-3))==0 && map.get(""+(loc-1))!=0 && map.get(""+loc)!=0 && map.get(""+(loc-2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc-1)+"")!=0 && map.get(""+(loc-2))!=0 && map.get(""+(loc-3))!=0)
        {
            return true;
        }
        return false;
    }
    public boolean checkLeft(int loc)
    {
        if(map.get(loc+"")==0 && map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0)
        {
            return true;
        }
        else if(map.get((loc+1)+"")==0 && map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+loc)!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+2))==0 && map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0)
        {
            return true;
        }
        else if(map.get(""+(loc+3))==0 && map.get(""+(loc+1))!=0 && map.get(""+(loc))!=0 && map.get(""+(loc+2))!=0)
        {
            return true;
        }
        else if(map.get(loc+"")!=0 && map.get((loc+1)+"")!=0 && map.get(""+(loc+2))!=0 && map.get(""+(loc+3))!=0)
        {
            return true;
        }
        return false;
    }
    public void checkSwipeOk()
    {
        ConstraintLayout cl = (ConstraintLayout) findViewById(R.id.layout_jeu);
        LinearLayout ll= new LinearLayout(this);
        ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));
        ll.setGravity(Gravity.CENTER);
        TextView result= new TextView(this);
        for(String clé : map.keySet())
        {
            if(map.get(clé)==2048)
            {

                result.setGravity(Gravity.CENTER);
                ll.addView(result);
                cl.addView(ll);
                Intent i= new Intent(jeu.this,Resultat.class);
                i.putExtra("Result",0);
                startActivity(i);
                return;
            }
        }
        for(String clé : map.keySet())
        {
            if(map.get(clé)!=mapprec.get(clé))
            {

                return;
            }
        }


        result.setGravity(Gravity.CENTER);
        ll.addView(result);
        cl.addView(ll);
        Intent i= new Intent(jeu.this,Resultat.class);
        i.putExtra("Result",1);
        startActivity(i);

    }

    //génère un nombre aléatoire pour qu'il soit placé sur la grille lorsqu'un mouvement swipe est effectué
    public void nombreAléatoire()
    {
        Random aléa = new Random();
        int i =  aléa.nextInt(16)+1;
        int j = 0;
        for(String clé: map.keySet())
        {
            if(map.get(clé)!=0)
            {
                j++;
            }
        }
        if(j!=16)
        {
            while(map.get(i+"")!=0)
            {
                i=aléa.nextInt(16)+1;
            }
            int[] aléatoire={2,4};
            int ind1=aléa.nextInt(aléatoire.length);
            map.put(i+"",aléatoire[ind1]);
            String[] nombres = new String[]{"1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16"};
            List<String> liste=Arrays.asList(nombres);

            changerGrille(liste);
        }

    }

    //actualise la grille à chaque changement
    public void changerGrille(List liste)
    {
        final List<String> l=liste;
        //affiche les éléments de la liste, donc les case
        grille.setAdapter(new ArrayAdapter<String>(
                this, android.R.layout.simple_list_item_1, l){
            public View getView(int position, View view, ViewGroup parent) {


                View view2 = super.getView(position,view,parent);
                TextView text = (TextView) view2;
                RelativeLayout.LayoutParams layout =  new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
                text.setLayoutParams(layout);
                text.setWidth(100);
                text.setHeight(210);
                text.setGravity(Gravity.CENTER);
                text.setTextSize(20);

                if(map.get(l.get(position))!=0)
                {
                    text.setText(map.get(l.get(position))+"");
                }
                else
                {
                    text.setText("");
                }

                text.setId(position);

                //change la couleur des case
                text.setBackgroundColor(Color.parseColor("#959e9d"));


                return text;
            }
        });
    }
}