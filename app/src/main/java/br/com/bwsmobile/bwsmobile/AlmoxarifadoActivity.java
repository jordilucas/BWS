package br.com.bwsmobile.bwsmobile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class AlmoxarifadoActivity extends AppCompatActivity implements View.OnClickListener{

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


       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {

        switch(v.getId()){
            case R.id.btnFerramentas:
                Intent i = new Intent(this, MenuEntregaRecebimentoActivity.class);
                startActivity(i);
                break;

            case R.id.btnPatrimonio:
                Intent ii = new Intent(this, MenuEntregaRecebimentoActivity.class);
                startActivity(ii);

            case R.id.btnEPI:
                Intent iii = new Intent(this, MenuEntregaRecebimentoActivity.class);
                startActivity(iii);


        }
    }
}
