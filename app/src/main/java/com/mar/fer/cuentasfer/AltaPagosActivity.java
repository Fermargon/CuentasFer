package com.mar.fer.cuentasfer;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.BoolRes;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View.OnFocusChangeListener;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AltaPagosActivity extends AppCompatActivity {

    private FirebaseDatabase database = null;

    private DatabaseReference dbReferenceUsuarios;
    private DatabaseReference dbReferenceHistoricoActual;
    private DatabaseReference dbReferencePlayerNuevos;
    private DatabaseReference dbReferenceReports;
    private DatabaseReference dbReferencePago = null;

    private static final String TAG = "AltaPagosActVent";

    int filaselec = -1;

    ImageButton     botonComprar;
    ImageButton     botonPicoteo;
    ImageButton     botonJuegosAzar;
    ImageButton     botonEntradas;
    ImageButton     botonViajes;
    ImageButton     botonDatePicker;
    ImageButton     botonUsuario;
    ImageButton     botonEnzo;
    ImageButton     botonFarmacia;
    ImageButton     botonHogar;
    ImageButton     botonHiper;
    ImageButton     botonAlcampo;
    ImageButton     botonCarrefour;
    ImageButton     botonOtrCompras;
    ImageButton     botonEuromillones;
    ImageButton     botonOnce;
    ImageButton     botonLoteria;
    ImageButton     botonHotel;
    ImageButton     botonAvion;
    ImageButton     botonTren;
    ImageButton     botonGasolina;
    ImageButton     botonTaxi;
    ImageButton     botonTeatro;
    ImageButton     botonCine;
    ImageButton     botonMuseo;
    ImageButton     botonOtrasEntradas;
    ImageButton     botonRestaurante;
    ImageButton     botonCopas;
    ImageButton     botonHelados;
    ImageButton     botonOtrosPicoteos;
    ImageButton     botonMobiliario;
    ImageButton     botonFerreterias;
    ImageButton     botonPlantas;
    ImageButton     botonElectrodomesticos;
    ImageButton     botonRegalos;
    ImageButton     botonPrestar;
    ImageButton     botonCoche;

    ImageButton     botonOtrosPrestar;
    ImageButton     botonPrestarFinCuentas;
    ImageButton     botonPrestarCompra;
    ImageButton     botonPrestarAmigos;

    ImageButton     botonGastosMensuales;

    ImageButton     botonReyes;
    ImageButton     botonCumple;
    ImageButton     botonBoda;
    ImageButton     botonOtrosRegalos;
    ImageButton     botonItv;
    ImageButton     botonTaller;
    //ImageButton     botonGasolina;
    ImageButton     botonOtrosCoche;

    LinearLayout    zoBotFechUsu;
    LinearLayout    zoBotPrimera;
    LinearLayout    zoBotSegunda;
    LinearLayout    zoFijo1;
    LinearLayout    zoFijo2;
    LinearLayout    zoBotSecCompras;
    LinearLayout    zoBotSecPicoteo;
    LinearLayout    zoBotSecEntradas;
    LinearLayout    zoBotSecJuegosAzar;
    LinearLayout    zoBotSecViajes;
    LinearLayout    zoBoSecHogar;
    LinearLayout    zoBotSecRegalos;
    LinearLayout    zoBotSecPrestar;
    LinearLayout    zoBotSecGastosMensuales;
    LinearLayout    zoBotSecCoche;
    LinearLayout    zoLitDesAdicional;
    LinearLayout    zoBotGrabar;

    TextView        etiqUsuario;
    TextView        etiqTipoDescrip;
    TextView        etiqFecha;
    EditText        edtImporte;
    EditText        edtDescripAdicional;
    Button          buGrabar;

    Vector          vUsuarios = null;
    Usuarios        usuario = null;
    HistoricoActual historicoactual = null;

    Integer         nordenHistorico = 0;
    Integer         numTemporada = 0;
    Date            daFtemporada = new Date();

    Pagos           pago     = null;

    private String  tipo1;
    private String  tipo2;
    private static int filaSelec = -1;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_settings:

                abrirPantallaConsulta();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alta_pagos);

        Log.i(TAG,"Inicio-OnCreate");

        botonComprar        = (ImageButton) findViewById(R.id.buCompra);
        botonPicoteo        = (ImageButton) findViewById(R.id.buPicoteo);
        botonEntradas       = (ImageButton) findViewById(R.id.buEntradas);
        botonJuegosAzar     = (ImageButton) findViewById(R.id.buJuegosAzar);
        botonViajes         = (ImageButton) findViewById(R.id.buViajes);
        botonDatePicker     = (ImageButton) findViewById(R.id.buFecha);
        botonUsuario        = (ImageButton) findViewById(R.id.buUsuario);
        botonFarmacia       = (ImageButton) findViewById(R.id.buFarmacia);
        botonEnzo           = (ImageButton) findViewById(R.id.buEnzo);
        botonHogar          = (ImageButton) findViewById(R.id.buHogar);
        botonHiper          = (ImageButton) findViewById(R.id.buHiper);
        botonAlcampo        = (ImageButton) findViewById(R.id.buAlcampo);
        botonCarrefour      = (ImageButton) findViewById(R.id.buCarrefour);
        botonOtrCompras     = (ImageButton) findViewById(R.id.buOtrcompras);
        botonEuromillones   = (ImageButton) findViewById(R.id.buEuromillones);
        botonOnce           = (ImageButton) findViewById(R.id.buOnce);
        botonLoteria        = (ImageButton) findViewById(R.id.buLoteria);
        botonHotel          = (ImageButton) findViewById(R.id.buHotel);
        botonAvion          = (ImageButton) findViewById(R.id.buAvion);
        botonTren           = (ImageButton) findViewById(R.id.buTren);
        botonGasolina       = (ImageButton) findViewById(R.id.buGasolina);
        botonTaxi           = (ImageButton) findViewById(R.id.buTaxi);
        botonTeatro         = (ImageButton) findViewById(R.id.buTeatro);
        botonCine           = (ImageButton) findViewById(R.id.buCine);
        botonMuseo          = (ImageButton) findViewById(R.id.buMuseo);
        botonOtrasEntradas  = (ImageButton) findViewById(R.id.buOtrentradas);
        botonRestaurante    = (ImageButton) findViewById(R.id.buRestaurante);
        botonCopas          = (ImageButton) findViewById(R.id.buCopas);
        botonHelados        = (ImageButton) findViewById(R.id.buHelados);
        botonOtrosPicoteos  = (ImageButton) findViewById(R.id.buOtrpicoteo);
        botonMobiliario     = (ImageButton) findViewById(R.id.buMobiliario);
        botonFerreterias    = (ImageButton) findViewById(R.id.buFerreterias);
        botonPlantas         = (ImageButton) findViewById(R.id.buPlantas);
        botonElectrodomesticos  = (ImageButton) findViewById(R.id.buElectrodomesticos);

        botonRegalos              = (ImageButton) findViewById(R.id.buRegalos);
        botonGastosMensuales       = (ImageButton) findViewById(R.id.buGastosMensuales);

        botonReyes              = (ImageButton) findViewById(R.id.buReyes);
        botonCumple             = (ImageButton) findViewById(R.id.buCumples);
        botonBoda               = (ImageButton) findViewById(R.id.buBoda);
        botonOtrosRegalos       = (ImageButton) findViewById(R.id.buOtrosRegalos);

        botonPrestar              = (ImageButton) findViewById(R.id.buPrestar);
        botonOtrosPrestar        = (ImageButton) findViewById(R.id.buOtrosPrestar);
        botonPrestarFinCuentas        = (ImageButton) findViewById(R.id.buPrestarFinCuentas);
        botonPrestarCompra        = (ImageButton) findViewById(R.id.buPrestarCompras);
        botonPrestarAmigos        = (ImageButton) findViewById(R.id.buPrestarAmigos);

        botonCoche               = (ImageButton) findViewById(R.id.buCoche);
        botonItv                = (ImageButton) findViewById(R.id.buItv);
        botonTaller             = (ImageButton) findViewById(R.id.buTaller);
        botonOtrosCoche         = (ImageButton) findViewById(R.id.buOtrosCoche);

        etiqFecha = (TextView) findViewById(R.id.etFecha);
        etiqUsuario = (TextView) findViewById(R.id.etUsuario);
        etiqTipoDescrip = (TextView) findViewById(R.id.etTipoDescrip);

        edtImporte = (EditText) findViewById(R.id.edtImporte);
        edtDescripAdicional = (EditText) findViewById(R.id.edtDesAdicional);

        zoBotFechUsu  = (LinearLayout) findViewById(R.id.zoBotFechUsu);
        zoBotPrimera = (LinearLayout)  findViewById(R.id.zoBotPrimera);
        zoBotSegunda = (LinearLayout)  findViewById(R.id.zoBotSegunda);
        zoBotSecCompras = (LinearLayout) findViewById(R.id.zoBotSecCompras);
        zoBotSecPicoteo = (LinearLayout) findViewById(R.id.zoBotSecPicoteo);
        zoBotSecEntradas = (LinearLayout) findViewById(R.id.zoBotSecEntradas);
        zoBotSecJuegosAzar = (LinearLayout) findViewById(R.id.zoBotSecJuegosAzar);
        zoBotSecViajes = (LinearLayout) findViewById(R.id.zoBotSecViajes);
        zoBoSecHogar = (LinearLayout) findViewById(R.id.zoBotSecHogar);
        zoBotSecRegalos = (LinearLayout) findViewById(R.id.zoBotSecRegalos);
        zoBotSecPrestar  = (LinearLayout) findViewById(R.id.zoBotSecPrestar);
        zoBotSecCoche = (LinearLayout) findViewById(R.id.zoBotSecCoche);
        zoBotSecGastosMensuales = (LinearLayout) findViewById(R.id.zoBotSecGastosMensuales);

        zoFijo1 = (LinearLayout)  findViewById(R.id.zoFijo1);
        zoFijo2 = (LinearLayout)  findViewById(R.id.zoFijo2);
        zoLitDesAdicional = (LinearLayout) findViewById(R.id.zoLitDesAdicional);
        zoBotGrabar = (LinearLayout) findViewById(R.id.zoBotGrabar);
        buGrabar = (Button) findViewById(R.id.buGrabar);

        vUsuarios = (Vector) GlobalClass.getInstance().getUsuarios();
        nordenHistorico = GlobalClass.getInstance().getHistoricoActual();
        numTemporada = GlobalClass.getInstance().getNumTemporadaActual();
        daFtemporada = GlobalClass.getInstance().getDaFtemporada();

        if (vUsuarios != null && vUsuarios.size() > 0){

            filaselec = getIntent().getIntExtra("filaselec",-1);

            if (filaselec<0){

                Log.i(TAG,"PRUEBA ------------- EXISTE VECTOR - Modo ALTA");
                incializarPantalla();

                etiqUsuario.setText(r_obt_nombreUsuario(GlobalClass.getIdusuarioConectado()));

            }else{

                Log.i(TAG,"PRUEBA ------------- EXISTE VECTOR - Modo MODIF");
                pago = (Pagos) getIntent().getExtras().getSerializable("pago");
                incializarPantalla();
                cargarDetalle(pago);
            }

        }else {

            inciarConexionesFireBase();
            filaselec = -1;
            vUsuarios = new Vector();
            Log.i(TAG,"antes-cargarDatosBD");
            cargarDatosBD();
            Log.i(TAG,"despues-cargarDatosBD");
            incializarPantalla();
            Log.i(TAG,"despues-inicializarPantalla");
            ;
        }

        nordenHistorico = GlobalClass.getInstance().getHistoricoActual();
        numTemporada = GlobalClass.getInstance().getNumTemporadaActual();
        daFtemporada = GlobalClass.getInstance().getDaFtemporada();

        Log.i(TAG,"PRUEBA ------------- filaselec="+ String.valueOf(filaselec));
        Log.i(TAG,"PRUEBA ------------- nordenHistorico = " + String.valueOf(nordenHistorico));
        Log.i(TAG,"PRUEBA ------------- numTemporada = " + String.valueOf(numTemporada));
        Log.i(TAG,"PRUEBA ------------- daFtemporada = " + String.valueOf(daFtemporada));

        zoBotFechUsu.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotPrimera.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSegunda.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoFijo1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoFijo2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecCompras.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecPicoteo.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecEntradas.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecJuegosAzar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecViajes.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecRegalos.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotSecCoche.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoLitDesAdicional.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        zoBotGrabar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
            }
        });

        buGrabar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                actualizarDatos();
            }
        });

        botonDatePicker.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                pulsarPantalla();
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.show(getSupportFragmentManager(), "datePicker");
            }
        });

        botonUsuario.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                abrirDialogoUsuarios();
            }
        });

        botonComprar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecCompras.setVisibility(View.VISIBLE);
                zoBotSecCompras.getLayoutParams().height = 140;
                zoBotSecCompras.requestLayout();
                tipo1 = "Alimentacion";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonPicoteo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecPicoteo.setVisibility(View.VISIBLE);
                zoBotSecPicoteo.getLayoutParams().height = 140;
                zoBotSecPicoteo.requestLayout();
                tipo1 = "Picoteo";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecEntradas.setVisibility(View.VISIBLE);
                zoBotSecEntradas.getLayoutParams().height = 140;
                zoBotSecEntradas.requestLayout();
                tipo1 = "Entradas";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonFarmacia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoFijo2.getLayoutParams().height = 300;
                zoFijo2.requestLayout();
                tipo1 = "Farmacia";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonEnzo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoFijo2.getLayoutParams().height = 300;
                zoFijo2.requestLayout();
                tipo1 = "Enzo";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonHogar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBoSecHogar.setVisibility(View.VISIBLE);
                zoBoSecHogar.getLayoutParams().height = 140;
                zoBoSecHogar.requestLayout();
                tipo1 = "Hogar";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonJuegosAzar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecJuegosAzar.setVisibility(View.VISIBLE);
                zoBotSecJuegosAzar.getLayoutParams().height = 140;
                zoBotSecJuegosAzar.requestLayout();
                tipo1 = "Juegos Azar";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });


        botonViajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecViajes.setVisibility(View.VISIBLE);
                zoBotSecViajes.getLayoutParams().height = 140;
                zoBotSecViajes.requestLayout();
                tipo1 = "Viajes";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonRegalos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecRegalos.setVisibility(View.VISIBLE);
                zoBotSecRegalos.getLayoutParams().height = 140;
                zoBotSecRegalos.requestLayout();
                tipo1 = "Regalo";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonPrestar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecPrestar.setVisibility(View.VISIBLE);
                zoBotSecPrestar.getLayoutParams().height = 140;
                zoBotSecPrestar.requestLayout();
                tipo1 = "Prestar";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecCoche.setVisibility(View.VISIBLE);
                zoBotSecCoche.getLayoutParams().height = 140;
                zoBotSecCoche.requestLayout();
                tipo1 = "Coche";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonGastosMensuales.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setOcultarLayoutsSec();
                zoBotSecGastosMensuales.setVisibility(View.VISIBLE);
                zoBotSecGastosMensuales.getLayoutParams().height = 140;
                zoBotSecGastosMensuales.requestLayout();
                tipo1 = "Gasto Mensual";
                tipo2 = "";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonHiper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Hiper";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonAlcampo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Alcampo";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonCarrefour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Carrefour";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonOtrCompras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Otros";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonEuromillones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Euromillones";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonOnce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Once";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonLoteria.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Loteria";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Hotel";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonAvion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Avion";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonTren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Tren";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonGasolina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Gasolina";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonTaxi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Taxi";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonTeatro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Teatro";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonCine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Cine";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonMuseo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Museo";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonOtrasEntradas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Otros";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonRestaurante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Restaurante";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonCopas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Copas";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonHelados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Helados";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonOtrosPicoteos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Otros Picoteos";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonMobiliario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Muebles";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonFerreterias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Herramientas";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonPlantas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Plantas";
                cargarDescripcion(tipo1, tipo2);
            }
        });
        botonElectrodomesticos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Electrodomesticos";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonReyes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Reyes";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonCumple.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Cumple";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonBoda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Boda";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonOtrosRegalos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Otros Regalos";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonPrestarCompra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Compra";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonPrestarAmigos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Amigos";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonPrestarFinCuentas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Fin de Cuentas";
                cargarDescripcion(tipo1, tipo2);
            }
        });


        botonItv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "ITV";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonTaller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Taller";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        botonOtrosCoche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tipo2 = "Otros";
                cargarDescripcion(tipo1, tipo2);
            }
        });

        edtImporte.setOnFocusChangeListener(new OnFocusChangeListener() {
            public void onFocusChange(View v, boolean gainFocus) {

                if (gainFocus) {  //onFocus
                    //buGrabar2.setVisibility(View.VISIBLE);
                }
                else {  //onBlur
                    //buGrabar2.setVisibility(View.INVISIBLE);
                }
            }
        });

        Log.i(TAG,"Final-OnCreate");
    }

    public void cargarDetalle(Pagos pago){

        edtDescripAdicional.setText(pago.getDescrip());
        etiqFecha.setText(pago.getFecha());
        edtImporte.setText("" + String.valueOf(pago.getImporte()));
        tipo1 = pago.getTipo1();
        tipo2 = pago.getTipo2();
        etiqUsuario.setText(r_obt_nombreUsuario(pago.getIdusuario()));
        cargarDescripcion(tipo1, tipo2);
    }
    public void pulsarPantalla(){
        ocultarTeclado();
    }

    public void ocultarTeclado(){

        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void setOcultarLayoutsSec() {

        pulsarPantalla();
        zoBotSecCompras.setVisibility(View.INVISIBLE);
        zoBotSecPicoteo.setVisibility(View.INVISIBLE);
        zoBotSecEntradas.setVisibility(View.INVISIBLE);
        zoBotSecJuegosAzar.setVisibility(View.INVISIBLE);
        zoBotSecViajes.setVisibility(View.INVISIBLE);
        zoBoSecHogar.setVisibility(View.INVISIBLE);
        zoBotSecRegalos.setVisibility(View.INVISIBLE);
        zoBotSecPrestar.setVisibility(View.INVISIBLE);
        zoBotSecCoche.setVisibility(View.INVISIBLE);
        zoBotSecGastosMensuales.setVisibility(View.INVISIBLE);

        zoBotSecCompras.getLayoutParams().height = 0;
        zoBotSecPicoteo.getLayoutParams().height = 0;
        zoBotSecEntradas.getLayoutParams().height = 0;
        zoBotSecJuegosAzar.getLayoutParams().height = 0;
        zoBotSecViajes.getLayoutParams().height = 0;
        zoBoSecHogar.getLayoutParams().height = 0;
        zoBotSecRegalos.getLayoutParams().height = 0;
        zoBotSecPrestar.getLayoutParams().height = 0;
        zoBotSecCoche.getLayoutParams().height = 0;
        zoBotSecGastosMensuales.getLayoutParams().height = 0;

        zoFijo1.getLayoutParams().height = 140;
        zoFijo2.getLayoutParams().height = 160;

        zoBotSecCompras.requestLayout();
        zoBotSecPicoteo.requestLayout();
        zoBotSecPicoteo.requestLayout();
        zoBotSecJuegosAzar.requestLayout();
        zoBotSecViajes.requestLayout();
        zoBoSecHogar.requestLayout();
        zoBotSecRegalos.requestLayout();
        zoBotSecPrestar.requestLayout();
        zoBotSecCoche.requestLayout();
        zoBotSecGastosMensuales.requestLayout();

        zoFijo1.requestLayout();
    }

    public void inciarConexionesFireBase(){

        FirebaseApp.initializeApp(this);
        database = FirebaseDatabase.getInstance();
        dbReferencePago = database.getReference();

    }

    public void cargaPlayerTemporadaActual(Integer pv_numTemporada)
    {
        DatabaseReference dbReferenceCLRoyale;
        dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child(String.valueOf(pv_numTemporada));

        dbReferenceCLRoyale.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                Vector vPlayerTemporadaActual = null;
                HashMap hmTemporada = null;

                vPlayerTemporadaActual = new Vector();

                if (dataSnapshot.getValue() != null) {

                    hmTemporada = (HashMap) dataSnapshot.getValue();

                    ArrayList adatosplayers = new ArrayList<>();

                    adatosplayers = (ArrayList) hmTemporada.get("vDatosplayers");

                    for (int i = 0; i < adatosplayers.size(); i++) {
                        HashMap hmPlayer = (HashMap) adatosplayers.get(i);
                        vPlayerTemporadaActual.add(hmPlayer);
                    }
                }

                GlobalClass.getInstance().setvPlayerTemporadaActual(vPlayerTemporadaActual);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "ERROR AL LEER DE DATABASE FIREBASE TEMPORADA ACTUAL.", error.toException());
            }
        });

        dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale");

        dbReferenceCLRoyale.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                if (dataSnapshot.getValue() != null) {

                    HashMap hmDatos = (HashMap) dataSnapshot.getValue();

                    String stToken1 = (String) hmDatos.get("token1");
                    String stToken2 = (String) hmDatos.get("token2");
                    String stToken3 = (String) hmDatos.get("token3");

                    Log.i(TAG, "TOKEN1=[+"+ stToken1.toString() + "]");

                    Log.i(TAG, "TOKEN2=[+"+ stToken2.toString() + "]");

                    Log.i(TAG, "TOKEN3=[+"+ stToken3.toString() + "]");

                    GlobalClass.getInstance().setToken1(stToken1);
                    GlobalClass.getInstance().setToken2(stToken2);
                    GlobalClass.getInstance().setToken3(stToken3);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "ERROR AL LEER DE DATABASE TOKENS.", error.toException());
            }
        });
    }

    public void cargarDatosBD(){

        Log.i(TAG,"Inicio Metodo cargarDatosBD");

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Log.i(TAG,"Calculamos IP Local");

        //Obtener IP Publica
        InetAddress IP;

        try {

            IP = InetAddress.getLocalHost();
            Log.e(TAG,"IP Publica Local = "+IP.getHostAddress());

        } catch (UnknownHostException e) {
            Log.e(TAG,"MalformedURLException = "+ e.getMessage() + "-" + e.toString());

        }

        try {

            URL whatismyip = new URL("http://checkip.amazonaws.com");

            BufferedReader in = new BufferedReader(new InputStreamReader(whatismyip.openStream()));

            String ip = in.readLine();

            Log.i(TAG,"IP Publica URL AMAZON = "+ ip);

            in.close();

        } catch (MalformedURLException ex) {
            Log.e(TAG,"MalformedURLException = "+ ex.getMessage() + "-" + ex.toString());

            //Logger.getLogger(Obtener_Ip.class.getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Log.e(TAG,"IOException = "+ ex.getMessage() + "-" + ex.toString());

            //Logger.getLogger(Obtener_Ip.class.getName()).log(Level.SEVERE, null, ex);

        }


        dbReferenceHistoricoActual = FirebaseDatabase.getInstance().getReference().child("histactual");
        dbReferenceHistoricoActual.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                historicoactual = dataSnapshot.getValue(HistoricoActual.class);

                nordenHistorico = historicoactual.getNorden();
                numTemporada = historicoactual.getNumtemporada();
                daFtemporada = historicoactual.getFtemporada();

                if (nordenHistorico == null){
                    nordenHistorico = 0;
                }
                if (numTemporada == null){
                    numTemporada = 0;
                }
                if (daFtemporada == null){
                    daFtemporada = new Date();
                }

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

                String stFtemporada = "";

                stFtemporada = df.format(daFtemporada);

                try {
                    daFtemporada = df.parse(stFtemporada);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                Log.i(TAG,"PRUEBA onDataChange ----- nordenActual = " + nordenHistorico);
                Log.i(TAG,"PRUEBA onDataChange ----- numTemporada = " + numTemporada);
                Log.i(TAG,"PRUEBA onDataChange ----- stFtemporada = " + stFtemporada);

                GlobalClass.getInstance().setHistoricoActual(nordenHistorico);
                GlobalClass.getInstance().setNumTemporadaActual(numTemporada);
                GlobalClass.getInstance().setDaFtemporada(daFtemporada);

                cargaPlayerTemporadaActual(numTemporada);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "ERROR AL LEER DE DATABASE FIREBASE HISTORICO ACTUAL.", error.toException());
            }
        });

        dbReferenceUsuarios = FirebaseDatabase.getInstance().getReference().child("usuarios");
        dbReferenceUsuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot singleChild : dataSnapshot.getChildren()) {

                    usuario = singleChild.getValue(Usuarios.class);
                    vUsuarios.add(usuario);

                    Log.i(TAG,"PRUEBA ----- usuarios = " + usuario.getIniciales());
                }

                GlobalClass.getInstance().setUsuarios(vUsuarios);


                // Hasta que el login identifique quien entra, cogemos el primer registro de usuarios.
                usuario = (Usuarios) vUsuarios.get(0);
                etiqUsuario.setText(usuario.getNombre());
                GlobalClass.setIdusuarioConectado(usuario.getIdusuario());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i(TAG, "ERROR AL LEER DE DATABASE FIREBASE - USUARIOS", databaseError.toException());
            }
        });

        dbReferencePlayerNuevos = FirebaseDatabase.getInstance().getReference().child("clroyale").child("nuevos");
        dbReferencePlayerNuevos.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                Vector vPlayerNuevos = null;
                HashMap hmTemporada = null;

                vPlayerNuevos = new Vector();

                if (dataSnapshot.getValue() != null) {

                    hmTemporada = (HashMap) dataSnapshot.getValue();

                    ArrayList adatosplayers = new ArrayList<>();

                    adatosplayers = (ArrayList) hmTemporada.get("vDatosplayers");

                    for (int i = 0; i < adatosplayers.size(); i++) {
                        HashMap hmPlayer = (HashMap) adatosplayers.get(i);
                        vPlayerNuevos.add(hmPlayer);
                    }
                }

                GlobalClass.getInstance().setvPlayerNuevos(vPlayerNuevos);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "ERROR AL LEER DE DATABASE FIREBASE PLAYER NUEVOS.", error.toException());
            }
        });


        dbReferenceReports = FirebaseDatabase.getInstance().getReference().child("clroyale").child("reports");
        dbReferenceReports.addValueEventListener(new ValueEventListener() {
            @Override

            public void onDataChange(DataSnapshot dataSnapshot) {

                HashMap hmReports = null;

                if (dataSnapshot.getValue() != null) {

                    Date daFechaActual = new Date();
                    Date daFechaReport = null;

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

                    hmReports = (HashMap) dataSnapshot.getValue();

                    try {
                        daFechaReport = sdf.parse(hmReports.get("fechareport").toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    //GlobalClass.getInstance().setReports(hmReports);
                    //GlobalClass.getInstance().setInrankings("S");
                    int diaAct = daFechaActual.getDate();
                    int mesAct = daFechaActual.getMonth();
                    int diaRep = daFechaReport.getDate();
                    int mesRep = daFechaReport.getMonth();

                    if (mesAct != mesRep || diaAct != diaRep)
                    {
                        Log.i(TAG,"Hay que generar los Rankings");
                        GlobalClass.getInstance().setInrankings("N");
                    }else{
                        GlobalClass.getInstance().setInrankings("S");
                        GlobalClass.getInstance().setReports(hmReports);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.i(TAG, "ERROR AL LEER DE DATABASE FIREBASE REPORTS.", error.toException());
            }
        });

    }

    public void incializarPantalla() {

        dbReferencePago = null;

        setOcultarLayoutsSec();

        final Calendar c = Calendar.getInstance();

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        month ++;

        String stDia = String.valueOf(day);
        String stMes = String.valueOf(month);

        if (day < 10){
            stDia = "0" + String.valueOf(day);
        }
        if (month < 10){
            stMes = "0" + String.valueOf(month);
        }

        zoFijo1.getLayoutParams().height = 140;
        zoFijo1.requestLayout();
        zoFijo2.getLayoutParams().height = 300;
        zoFijo2.requestLayout();

        etiqFecha.setText(stDia+"/"+stMes+"/"+year);
        edtDescripAdicional.setText("");
        edtImporte.setText("");
        tipo1 = "";
        tipo2 = "";
        cargarDescripcion(tipo1, tipo2);
    }

    public void actualizarDatos() {

        Date dafcompra = null;

        Boolean boValidar = false;
        String descrip = edtDescripAdicional.getText().toString();
        String fecha = etiqFecha.getText().toString();
        int importe = 0;

        if (!edtImporte.getText().toString().trim().equals("")){
            importe = Integer.parseInt(edtImporte.getText().toString());
        }

        int idusuario = usuario.getIdusuario();
        String idpago = "";
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String sdtfregistr = df.format(new Date(System.currentTimeMillis()));
        String stPrestadoA = "";

        if (idusuario == 1){
            stPrestadoA = GlobalClass.getIniciales(vUsuarios, 2);
        }else{
            stPrestadoA = GlobalClass.getIniciales(vUsuarios, 1);
        }


        if (String.valueOf(tipo1).equals("Prestar")){
            descrip = descrip + " a " + stPrestadoA + " (" + String.valueOf(importe) + "€)";
            importe = importe * 2;
        }

        df = new SimpleDateFormat("dd/MM/yyyy");

        try {
            dafcompra = df.parse(fecha);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        String  key_pagos = "";

        if (filaselec >= 0){    // Modificación
            key_pagos = pago.getIdpago();

            //Map<String, Object> mappago = new HashMap<>();

            pago.setIdusuario(idusuario);
            pago.setTipo1(tipo1);
            pago.setTipo2(tipo2);
            pago.setDescrip(descrip);
            pago.setFecha(fecha);
            pago.setFcompra(dafcompra);
            pago.setImporte(importe);
            pago.setIdpago(key_pagos);
            pago.setFecharegistro(sdtfregistr);

            boValidar = validacionGrabar(pago);

            if (boValidar){
                //mappago.put(key_pagos, pago);
                dbReferencePago = FirebaseDatabase.getInstance().getReference().child("cuentasFer").child(String.valueOf(nordenHistorico)).child("pagos");
                Log.i(TAG,"PRUEBARR ACTTTT ----------- Key_pagos="+key_pagos);
                //dbReferencePago.updateChildren(mappago);

                dbReferencePago.child(key_pagos).setValue(pago);
            }

        }else{
            Pagos pagoNew = null;
            dbReferencePago = null;
            dbReferencePago = FirebaseDatabase.getInstance().getReference().child("cuentasFer").child(String.valueOf(nordenHistorico)).child("pagos");
            key_pagos = dbReferencePago.push().getKey();
            idpago = key_pagos;
            pagoNew   =   new Pagos(tipo1, tipo2, descrip, fecha, dafcompra, importe, idusuario, idpago, sdtfregistr);

            boValidar = validacionGrabar(pagoNew);

            if (boValidar) {
                Log.i(TAG,"PRUEBARR INSERT ----------- Key_pagos=" + key_pagos);
                dbReferencePago.child(key_pagos).setValue(pagoNew);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Actualizar Datos");

        if (boValidar){
            builder.setMessage("Datos actualizados correctamente.");
        }else{
            builder.setMessage("Error Actualización. Datos no rellenos.");
        }

        builder.setPositiveButton("Aceptar",null);
        builder.create();
        builder.show();

        incializarPantalla();
        pago = null;

        if (!boValidar && filaselec >= 0){
            abrirPantallaConsulta();
            return;
        }

        filaselec = -1;
    }

    public void abrirDialogoUsuarios() {

        String[] stusuarios = new String[2];

        pulsarPantalla();

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona Usuario:");
        //builder.setMessage("Usuario.");

        for (int i = 0; i < vUsuarios.size(); i++) {
            usuario = (Usuarios) vUsuarios.get(i);
            stusuarios[i] = usuario.getIniciales() + " - " + usuario.getNombre();
        }

        builder.setItems(stusuarios, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                usuario = (Usuarios) vUsuarios.get(which);
                etiqUsuario.setText(usuario.getNombre());
            }
        });

       // builder.setPositiveButton("Aceptar",null);
        builder.create();
        builder.show();
    }

    public void cargarDescripcion(String tipo1, String tipo2){

        String stCadena = tipo1 + " " + tipo2;
        etiqTipoDescrip.setText(stCadena.trim());

    }

    public boolean validacionGrabar(Pagos pagoVal){

        String stDescrip = "";

        if (pagoVal == null){
            Log.i(TAG,"PRUEBA ---- validacionGrabar - pago = null");
            return false;
        }

        if (pagoVal.getFecha() == null){
            Log.i(TAG,"PRUEBA ---- validacionGrabar - getfecha = null");
            return false;
        }

        if (pagoVal.getIdusuario() < 0){
            Log.i(TAG,"PRUEBA ---- validacionGrabar - getIdusuario = -1");
            return false;
        }
        stDescrip = pagoVal.getTipo1().trim() + " " + pagoVal.getTipo2().trim() + " " + pagoVal.getDescrip();

        if (stDescrip.trim().equals("")){
            Log.i(TAG,"PRUEBA ---- validacionGrabar - stDescrip = VACIO");
            return false;
        }

        Log.i(TAG,"PRUEBA ---- validacionGrabar - OK");
        return true;
    }



    public String r_obt_nombreUsuario(int vl_idusuario){

        String nombre = "";

        for (int i = 0; i<vUsuarios.size();i++){

            usuario = (Usuarios) vUsuarios.get(i);

            if (usuario.getIdusuario() == vl_idusuario){

                nombre  = usuario.getNombre();
                break;
            }
        }
        return nombre;
    }

    private void abrirPantallaConsulta() {
        filaselec = -1;
        Intent CosultaActivity = new Intent(this, CosultaActivity.class);
        startActivity(CosultaActivity);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
// Esto es lo que hace mi botón al pulsar ir a atrás
            //Toast.makeText(getApplicationContext(), "Voy hacia atrás!!",Toast.LENGTH_SHORT).show();
            //return true;
            pago = null;
            incializarPantalla();
        }
        return super.onKeyDown(keyCode, event);
    }
}
