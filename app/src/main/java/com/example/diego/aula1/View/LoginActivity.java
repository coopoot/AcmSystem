package com.example.diego.aula1.View;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.diego.aula1.Model.RequestHandler;
import com.example.diegos.acmm.R;

import java.util.HashMap;

import static com.example.diego.aula1.Controller.Config.URL_LOGIN;

public class LoginActivity extends AppCompatActivity {


    Button btnEntrar;
    EditText edLogin;
    EditText edSenha;
    TextView mensagens;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        edLogin = (EditText)findViewById(R.id.edLogin);
        edSenha = (EditText)findViewById(R.id.edSenha);
        btnEntrar = (Button) findViewById(R.id.btnEntrar);
        mensagens = (TextView)findViewById(R.id.tvMsg);

        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Logar();
            }
        });
    }

    void Logar() {

        final String emailString = edLogin.getText().toString().trim();
        final String senhaString = edSenha.getText().toString().trim();

        class Logar extends AsyncTask<Void, Void, String> {

            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(LoginActivity.this, "Logando ", "por favor aguarde", false, false);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                String a = "1";
                if (s.equals(a)) {
                    mensagens.setTextColor(Color.BLUE);
                    mensagens.setText("Autenticado com sucesso");
                    Intent intent = new Intent(getBaseContext(),PrincipalActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("login",edLogin.getText().toString());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {

                    mensagens.setTextColor(Color.RED);
                    mensagens.setText("Usuario ou senha invalidos");
                }
            }

            @Override
            protected String doInBackground(Void... v) {

                HashMap<String, String> parametros = new HashMap<>();
                parametros.put("login", emailString);
                parametros.put("senha", senhaString);

                RequestHandler rh = new RequestHandler();
                String res = rh.sendPostRequest(URL_LOGIN, parametros);

                return res;

            }
        }

        Logar logar = new Logar();
        logar.execute();
    }
}
