package br.com.bwsmobile.bwsmobile;

import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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



    }

    public void onClick(View v){
        int id = v.getId();
        if (id == R.id.btnAlmoxarifado) {
            Intent i = new Intent(MenuActivity.this, AlmoxarifadoActivity.class);
            startActivity(i);
            Log.i("Botao Almoxarifado", "Clique almoxarifado");
        } else if (id == R.id.btnManutencao) {
            // manutenção
        }
    }


}
