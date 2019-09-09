package com.mar.fer.cuentasfer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.IDNA;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Vector;
import java.util.logging.Level;

import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;

public class HistorialActivity extends AppCompatActivity {

    private static int ultfila = -1;
    private static int filaSelec = -1;
    private ListView listHistorial;
    TextView  etiqSaldo;
    TextView  etiqTotBea;
    TextView  etiqTotFer;
    private DatabaseReference dbReference;

    Vector  vUsuarios = null;

    String stFechaInicio_act = "";
    Integer idusuario_act = 0;
    Integer iSaldo_act = 0;
    Integer iSaldo_global = 0;
    Integer idusuario_global = 0;

    HistoricoPagos historicopagos = null;

    ArrayList<HistoricoPagos> pagosHistArrayList = new ArrayList<HistoricoPagos>();

    private String  stCuerpoMail = "";
    private String  nombre = "";
    private String  stDescrip = "";
    private String  stEspacios;

    private int idusuario = 0;
    private HistorialActivity contexto;
    private int    iTotalUserFER = 0;
    private int    iTotalUserBEA = 0;
    private int    iSaldo = 0;
    private int    iDiferencia = 0;
    private int    iDifEspacios = 0;
    private static final String TAG = "HistorialActVent";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menuconsulta, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.menu_mail:

                enviarMail();

                return true;

            case R.id.menu_whatsapp:

                abrirUrlWhatsapp();

                return true;

            case R.id.menu_historico:


                abrirPantallaHistorico();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_modif:

                    return true;
                case R.id.navigation_borrar:

                    return true;
                case R.id.navigation_nuevo:

                    return true;
            }
            return false;
        }

    };

    public void abrirUrlWhatsapp () {

        Uri uriUrl = Uri.parse("https://api.whatsapp.com/send?phone=34686454520&text=Me%20gustaría%20saber%20el%20precio%20del%20coche");
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    public void enviarMsgApp(View view) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent .setType("text/plain");
        String texto = "Mensaje para enviar aqui";
        intent .setPackage("com.whatsapp");
        if (intent != null) {
            intent .putExtra(Intent.EXTRA_TEXT, texto);
            startActivity(Intent.createChooser(intent, texto));
        } else {
            Toast.makeText(this,"Nooo whatsapp, whatsapp man",
                    Toast.LENGTH_SHORT)
                    .show();
        }
    }

    private void enviarMail(){

        String[] TO = {"nanonaninonano@gmail.com"}; //aquí pon tu correo
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:fernandom@gmail.com"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
// Esto podrás modificarlo si quieres, el asunto y el cuerpo del mensaje
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Cuentas Gastos");
        //emailIntent.putExtra(Intent.EXTRA_TEXT, stCuerpoMail);
        emailIntent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(stCuerpoMail.toString()).toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(HistorialActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }


    };

    private void abrirPantallaHistorico() {

        //Intent CosultaActivity = new Intent(this, CosultaActivity.class);
        //startActivity(CosultaActivity);
    }

    public void cancelar() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_historial);

        vUsuarios = (Vector) GlobalClass.getInstance().getUsuarios();
        Log.i(TAG, "Abre Ventana -1");
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        Log.i(TAG, "Abre Ventana -2");
        listHistorial = (ListView) findViewById(R.id.historial_list);

        Log.i(TAG, "Abre Ventana -3");
        contexto = this;
        Log.i(TAG, "Abre Ventana -4");

        dbReference = FirebaseDatabase.getInstance().getReference().child("cuentasFer");

        dbReference.orderByKey().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                String stIniciales = "";

                for (DataSnapshot singleChild : dataSnapshot.getChildren()) {

                    String stKey = singleChild.getKey();

                    if (GlobalClass.isNum(stKey)){

                        Log.i(TAG,"Registro obtenido...[" + stKey + "]");

                        HashMap hmPagos = (HashMap) singleChild.getValue();

                        if (hmPagos != null && hmPagos.get("idusuario") != null){

                            stIniciales = GlobalClass.getIniciales(vUsuarios, new Integer(String.valueOf(hmPagos.get("idusuario"))));
                            HistoricoPagos historicoPagos = new HistoricoPagos();

                            Log.i(TAG,"Finihist = " + hmPagos.get("finihist"));
                            Log.i(TAG,"Ffinhist = " + hmPagos.get("ffinhist"));
                            Log.i(TAG,"Saldo = " + hmPagos.get("saldo"));
                            Log.i(TAG,"Iniciales = " + stIniciales);

                            historicoPagos.setFinihist((String) hmPagos.get("finihist"));
                            historicoPagos.setFfinhist((String) hmPagos.get("ffinhist"));
                            historicoPagos.setSaldo(new Integer(hmPagos.get("saldo").toString()));
                            historicoPagos.setIdusuario(new Integer (hmPagos.get("idusuario").toString()));

                            if (stIniciales.toString().equals("FMG")){
                                iTotalUserFER += historicoPagos.getSaldo();
                                Log.i(TAG,"Sumando a FMG = " + historicoPagos.getSaldo());
                            }else{
                                Log.i(TAG,"Sumando a BEA = " + historicoPagos.getSaldo());
                                iTotalUserBEA += historicoPagos.getSaldo();
                            }

                            pagosHistArrayList.add(historicoPagos);

                        }else{

                            HistoricoPagos historicoPagos = new HistoricoPagos();

                            Log.i(TAG,"REGISTRO ACTUAL");
                            stFechaInicio_act = getIntent().getStringExtra("finihist_act");
                            iSaldo_act = getIntent().getIntExtra("saldo_act",0);
                            idusuario_act = getIntent().getIntExtra("idusuario_act",0);

                            historicoPagos.setFinihist(stFechaInicio_act);
                            historicoPagos.setFfinhist("ACTUAL");
                            historicoPagos.setSaldo(iSaldo_act);
                            historicoPagos.setIdusuario(idusuario_act);

                            stIniciales = GlobalClass.getIniciales(vUsuarios, historicoPagos.getIdusuario());

                            Log.i(TAG,"Finihist="+stFechaInicio_act);
                            Log.i(TAG,"Ffinhist="+"ACTUAL");
                            Log.i(TAG,"iSaldo_act="+iSaldo_act);
                            Log.i(TAG,"Iniciales = " + stIniciales);

                            if (stIniciales.toString().equals("FMG")){
                                iTotalUserFER += historicoPagos.getSaldo();
                                Log.i(TAG,"Sumando a FMG = " + historicoPagos.getSaldo());
                            }else{
                                iTotalUserBEA += historicoPagos.getSaldo();
                                Log.i(TAG,"Sumando a BEA = " + historicoPagos.getSaldo());
                            }

                            pagosHistArrayList.add(historicoPagos);

                        }
                    }


                }

                String stTotBea = "";
                String stTotFer = "";
                String stSaldo = "";

                if (iTotalUserFER > iTotalUserBEA) {
                    stSaldo = "Saldo: ";
                } else{
                    stSaldo = "Saldo: ";
                }

                iDiferencia = abs(iTotalUserFER - iTotalUserBEA);

                iSaldo  =  round(iDiferencia / 2);

                stSaldo = stSaldo + String.valueOf(iSaldo) + " €";

                stTotBea = "BEA: " + String.valueOf(iTotalUserBEA) + " €";
                stTotFer = "FER: " + String.valueOf(iTotalUserFER) + " €";

                etiqSaldo = (TextView) findViewById(R.id.etSaldo);
                etiqTotBea = (TextView) findViewById(R.id.etTotalBea);
                etiqTotFer = (TextView) findViewById(R.id.etTotalFer);

                etiqSaldo.setTextColor(Color.GREEN);

                if(iTotalUserFER > iTotalUserBEA){
                    etiqTotFer.setTextColor(Color.GREEN);
                    etiqTotBea.setTextColor(Color.RED);
                }else{
                    etiqTotFer.setTextColor(Color.RED);
                    etiqTotBea.setTextColor(Color.GREEN);
                }

                etiqSaldo.setText(stSaldo);
                etiqTotBea.setText(stTotBea);
                etiqTotFer.setText(stTotFer);

                Log.i(TAG,"PRUEBAAAAAA ------------ Cargamos Lista en Activity...");
                cargarLista(contexto, R.layout.lista_historial_activity, pagosHistArrayList);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void cargarLista(HistorialActivity contexto, int R_layout_IdView, ArrayList pagosHistArrayList){

        listHistorial.setAdapter(new Lista_adaptador(contexto, R_layout_IdView, pagosHistArrayList){

            @Override
            public void onEntrada(Object entrada, View view) {

                if (entrada != null) {

                    String stIniciales = "";
                    TextView etUsuario = (TextView) view.findViewById(R.id.etUsuario);
                    TextView etFechaIni = (TextView) view.findViewById(R.id.etFechaInicio);
                    TextView etFechaFin = (TextView) view.findViewById(R.id.etFechaFin);
                    TextView etImporte = (TextView) view.findViewById(R.id.etImporte);

                    if(etUsuario!=null){
                        stIniciales = GlobalClass.getIniciales(vUsuarios, ((HistoricoPagos) entrada).getIdusuario());
                        etUsuario.setText(stIniciales);
                    }

                    if(etFechaIni!=null)
                        etFechaIni.setText(String.valueOf(((HistoricoPagos) entrada).getFinihist()));

                    if(etFechaFin!=null)
                        etFechaFin.setText(String.valueOf(((HistoricoPagos) entrada).getFfinhist()));

                    if(etImporte!=null)
                        etImporte.setText(String.valueOf(((HistoricoPagos) entrada).getSaldo()) + " €");

                }
            }
        });
    }


    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás
            //Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",Toast.LENGTH_SHORT).show();
            filaSelec = -1;
            //return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
