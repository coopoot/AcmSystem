package com.example.diego.aula1.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.diegos.acmm.R;


public class PrincipalActivity extends AppCompatActivity {

    Button btnVendas;
    Button btnProdutos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        btnProdutos = (Button)findViewById(R.id.btnProdutos);

        btnProdutos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),ProdutosActivity.class);
                startActivity(intent);
            }
        });

        btnVendas = (Button)findViewById(R.id.btnVendas);

        btnVendas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(),VendasActivity.class);
                startActivity(intent);
            }
        });
    }

}
