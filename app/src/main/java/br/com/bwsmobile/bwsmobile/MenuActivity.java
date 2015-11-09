package br.com.bwsmobile.bwsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Switch;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    Button almoxarifado, manutencao, abastecimento, usoDeFrota, cartoes, protocolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);



        almoxarifado = (Button)findViewById(R.id.btnAlmoxarifado);
        manutencao = (Button)findViewById(R.id.btnManutencao);
        abastecimento = (Button)findViewById(R.id.btnAbastecimentos);
        usoDeFrota = (Button)findViewById(R.id.btnUsoDeFrota);
        cartoes = (Button)findViewById(R.id.btnCartoes);
        protocolo = (Button)findViewById(R.id.btnProtocolo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        almoxarifado.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuActivity.this, AlmoxarifadoActivity.class);
                startActivity(i);
            }
        });


        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void onClick(View v){

        switch(v.getId()){

            case R.id.btnAlmoxarifado:
                Intent i = new Intent(MenuActivity.this, AlmoxarifadoActivity.class);
                startActivity(i);
                Log.i("Botao Almoxarifado", "Clique almoxarifado");
                break;

            case R.id.btnManutencao:
                //
            break;


        }


    }


}
