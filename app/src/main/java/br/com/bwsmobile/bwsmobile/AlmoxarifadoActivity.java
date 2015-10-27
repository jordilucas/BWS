package br.com.bwsmobile.bwsmobile;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AlmoxarifadoActivity extends AppCompatActivity {

    Button btnAlmoxarifado, btnManutencao, btnAbastecimento, btnUsoFrota, btnCartoes, btnProtocolo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almoxarifado);

        btnAlmoxarifado = (Button)findViewById(R.id.btnAlmoxarifado);
        btnManutencao = (Button)findViewById(R.id.btnManutencao);
        btnAbastecimento = (Button)findViewById(R.id.btnAbastecimentos);
        btnUsoFrota = (Button)findViewById(R.id.btnUsoDeFrota);
        btnCartoes = (Button)findViewById(R.id.btnCartoes);
        btnProtocolo = (Button)findViewById(R.id.btnProtocolo);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
