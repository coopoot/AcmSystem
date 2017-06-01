package com.example.diego.aula1.View;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.diego.aula1.Controller.Config;
import com.example.diego.aula1.Model.RequestHandler;
import com.example.diegos.acmm.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ProdutosActivity extends AppCompatActivity {

    ListView listView;
    Button btnConsulta;
    EditText edConsulta;
    String JSON_STRING;
    String consultaText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);
        listView = (ListView)findViewById(R.id.lvVendas);
        btnConsulta = (Button)findViewById(R.id.btnConsulta);
        edConsulta = (EditText)findViewById(R.id.edConsulta);
        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON();
            }
        });

    }
    private void pesquisarProdutos(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String codigo = jo.getString(Config.TAG_CODIGO);
                String nome = jo.getString(Config.TAG_NOME);
                String estoque = jo.getString(Config.TAG_ESTOQUE);
                String valor = jo.getString(Config.TAG_VALOR);
                String codFornecedor = jo.getString(Config.TAG_COD_FORNECEDOR);
                String codBarras = jo.getString(Config.TAG_COD_BARRAS);

                HashMap<String,String> dados = new HashMap<>();
                dados.put(Config.TAG_CODIGO,codigo);
                dados.put(Config.TAG_NOME,nome);
                dados.put(Config.TAG_ESTOQUE,estoque);
                dados.put(Config.TAG_VALOR,valor);
                dados.put(Config.TAG_COD_FORNECEDOR,codFornecedor);
                dados.put(Config.TAG_COD_BARRAS,codBarras);
                list.add(dados);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                ProdutosActivity.this, list, R.layout.list_produtos,
                new String[]{Config.TAG_CODIGO,Config.TAG_NOME,Config.TAG_ESTOQUE,Config.TAG_VALOR,
                Config.TAG_COD_FORNECEDOR,Config.TAG_COD_BARRAS},
                new int[]{R.id.tvCodigo, R.id.tvNome, R.id.tvQuantidade, R.id.tvValor,
                        R.id.tvCodFornecedor, R.id.tvCodigoBarras});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        consultaText = edConsulta.getText().toString();
        class GetJSON extends AsyncTask<Void,Void,String>{

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ProdutosActivity.this,"Pesquisando","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                pesquisarProdutos();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_ALL+"?consulta="+consultaText);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}
