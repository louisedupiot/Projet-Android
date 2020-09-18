package com.example.a2048essai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView titre;
    private Button bout_jeu;
    private Button bout_quitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        titre = findViewById(R.id.titre_jeu);
        bout_jeu = findViewById(R.id.bouton_jouer);
        bout_quitter = findViewById(R.id.bouton_quitter);
        bout_jeu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, jeu.class);
                startActivity(intent);
            }
        });


        //termine toutes les activités de l'application et revient au menu home du téléphone
        bout_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory(Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);

            }
        });

        //ne ferme que l'activité en cours et ramène donc à la précédente
        /*bout_quitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });*/
    }

}
