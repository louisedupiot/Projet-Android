package com.example.a2048essai1;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class Resultat extends AppCompatActivity {

    @Override

    //affiche une page de résultat quand la grille est remplie ou quand une case a le nombre 2048
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultat);
        int result = getIntent().getExtras().getInt("Result");
        Button bout_retour = findViewById(R.id.bouton_resultat);
        Button bout_quitter2 = findViewById(R.id.bouton_quitter2);
        TextView resultat = findViewById(R.id.texte_resultat);
        if (result == 0) {
            bout_retour.setText("RECOMMENCER");
            resultat.setText("GAGNÉ");
        } else {
            bout_retour.setText("RECOMMENCER");
            resultat.setText("PERDU");

        }

        //retourne au menu du jeu
        bout_retour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Resultat.this, MainActivity.class);
                startActivity(intent);
            }
        });

        //ferme l'application
        bout_quitter2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(Intent.ACTION_MAIN);
                homeIntent.addCategory( Intent.CATEGORY_HOME);
                homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(homeIntent);
            }
        });
    }
}