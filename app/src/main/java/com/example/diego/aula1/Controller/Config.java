package com.example.diego.aula1.Controller;

/**
 * Created by diego on 20/03/2017.
 */

public class Config {
    //JSON Tags
    public static final String TAG_JSON_ARRAY="result";
    public static final String TAG_CODIGO = "codigo";
    public static final String TAG_NOME = "nome";
    public static final String TAG_COD_FORNECEDOR = "fornecedores_codigo";
    public static final String TAG_VALOR = "valor";
    public static final String TAG_ESTOQUE = "estoque";
    public static final String TAG_COD_BARRAS = "codigo_barras";
    public static final String TAG_COD_CLIENTE = "clientes_codigo";
    public static final String TAG_DATA_VENDA = "data_venda";
    public static final String TAG_VALOR_TOTAL = "valor_total";
    public static final String TAG_DESCONTO = "desconto";
    public static final String TAG_TIPO_PAGAMENTO = "tipo_pagamento";
    // url
    public static final String URL_GET_ALL = "http://192.168.0.6/acm/produtos.php";
    public static final String URL_GET_VENDAS = "http://192.168.0.6/acm/vendas.php";
    public static final String URL_LOGIN = "http://192.168.0.6/acm/login.php";
}
