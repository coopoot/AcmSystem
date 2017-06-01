package com.example.diego.aula1.View;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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


public class VendasActivity extends AppCompatActivity {

    ListView listView;
    Button btnConsulta;
    EditText edDataInicial;
    EditText edDataFinal;
    String JSON_STRING;
    String dataInicialText;
    String dataFinalText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vendas);
        listView = (ListView)findViewById(R.id.lvVendas);
        btnConsulta = (Button)findViewById(R.id.btnConsultaVendas);
        edDataInicial = (EditText)findViewById(R.id.edDataInicial);
        edDataFinal = (EditText)findViewById(R.id.edDataFinal);
        btnConsulta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getJSON();
            }
        });

    }
    private void pesquisarVendas(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList<HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_ARRAY);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String nome = jo.getString(Config.TAG_NOME);
                String codigo = jo.getString(Config.TAG_CODIGO);
                String codClientes = jo.getString(Config.TAG_COD_CLIENTE);
                String dataVenda = jo.getString(Config.TAG_DATA_VENDA);
                String valorTotal = jo.getString(Config.TAG_VALOR_TOTAL);
                String desconto = jo.getString(Config.TAG_DESCONTO);
                String tipoPagamento = jo.getString(Config.TAG_TIPO_PAGAMENTO);

                HashMap<String,String> dados = new HashMap<>();
                dados.put(Config.TAG_NOME,nome);
                dados.put(Config.TAG_CODIGO,codigo);
                dados.put(Config.TAG_COD_CLIENTE,codClientes);
                dados.put(Config.TAG_DATA_VENDA,dataVenda);
                dados.put(Config.TAG_VALOR_TOTAL,valorTotal);
                dados.put(Config.TAG_DESCONTO,desconto);
                dados.put(Config.TAG_TIPO_PAGAMENTO,tipoPagamento);
                list.add(dados);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                VendasActivity.this, list, R.layout.list_vendas,
                new String[]{Config.TAG_NOME,Config.TAG_CODIGO,Config.TAG_COD_CLIENTE,Config.TAG_DATA_VENDA,Config.TAG_VALOR_TOTAL,
                        Config.TAG_DESCONTO,Config.TAG_TIPO_PAGAMENTO},
                new int[]{R.id.tvNome,R.id.tvCodigo, R.id.tvCodigoCliente, R.id.tvDataVenda, R.id.tvValorVenda,
                        R.id.tvDesconto, R.id.tvTipoPagamento});

        listView.setAdapter(adapter);
    }

    private void getJSON(){
        dataInicialText = edDataInicial.getText().toString();
        dataFinalText = edDataFinal.getText().toString();
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(VendasActivity.this,"Pesquisando","Aguarde...",false,false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                JSON_STRING = s;
                pesquisarVendas();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler rh = new RequestHandler();
                String s = rh.sendGetRequest(Config.URL_GET_VENDAS+"?dataInicial="+dataInicialText+"&dataFinal="+dataFinalText);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

