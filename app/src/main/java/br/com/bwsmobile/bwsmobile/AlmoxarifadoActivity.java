package br.com.bwsmobile.bwsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AlmoxarifadoActivity extends AppCompatActivity {

    Button ferramentas, epi, patrimonio, locacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_almoxarifado);

        ferramentas = (Button)findViewById(R.id.btnFerramentas);
        patrimonio = (Button)findViewById(R.id.btnPatrimonio);
        epi = (Button)findViewById(R.id.btnEPI);
        locacao = (Button)findViewById(R.id.btnLocacao);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        ferramentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(AlmoxarifadoActivity.this, MenuEntregaRecebimentoActivity.class);
                startActivity(i);

            }
        });


        patrimonio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlmoxarifadoActivity.this, MenuEntregaRecebimentoActivity.class);
                startActivity(i);
            }
        });


        epi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlmoxarifadoActivity.this, MenuEntregaRecebimentoActivity.class);
                startActivity(i);
            }
        });

        locacao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AlmoxarifadoActivity.this, MenuEntregaRecebimentoActivity.class);
                startActivity(i);
            }
        });



    }


}
