package com.mar.fer.cuentasfer;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.nfc.tech.TagTechnology;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.FileProvider;
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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.Vector;

import javax.net.ssl.HttpsURLConnection;

import static android.os.Environment.MEDIA_MOUNTED;
import static java.lang.StrictMath.abs;
import static java.lang.StrictMath.round;
import static java.util.Collections.*;

public class CosultaActivity extends AppCompatActivity {

    private static final String URL_CLAN = "https://api.royaleapi.com/clan/2Q2GCPQV";
    private static final String URL_PLAYER = "https://api.royaleapi.com/player/";

    private static final String URL_CLAN_ORIG_2 = "https://api.clashroyale.com/v1/clans/#2Q2GCPQV/members";
    private static final String URL_CLAN_ORIG_1 = "https://api.clashroyale.com/v1/clans/%232Q2GCPQV/members";

    private static final String URL_PLAYER_ORIG = "https://api.clashroyale.com/v1/players/";

    private static final String KEY_API = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTYzMywiaWRlbiI6IjQ4NzI2OTIwMTUwNjA3NDYzNCIsIm1kIjp7InVzZXJuYW1lIjoiZmVybmFuZG9tZyIsImtleVZlcnNpb24iOjMsImRpc2NyaW1pbmF0b3IiOiIwMzA3In0sInRzIjoxNTM4MzM3Nzg2ODgzfQ.hOrrDta7hlkOJ_xgW9Cj5522A7PqIiUJrigEcCYCEN0";
    private static final String KEY_API_ORIG_1 = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6IjQ2MTlmODRiLWFjNTYtNGU1Yy1iODY4LTJhMzA4MzA2YzIxNSIsImlhdCI6MTUzODMyMTAwOSwic3ViIjoiZGV2ZWxvcGVyLzc0M2U4ZmJhLWU3MDMtYzViMS0yZDg4LTA1MzMyM2QyODg1MSIsInNjb3BlcyI6WyJyb3lhbGUiXSwibGltaXRzIjpbeyJ0aWVyIjoiZGV2ZWxvcGVyL3NpbHZlciIsInR5cGUiOiJ0aHJvdHRsaW5nIn0seyJjaWRycyI6WyI4OS43LjEyMi42NiJdLCJ0eXBlIjoiY2xpZW50In1dfQ.6L5SpN-UfehK4CIkUpln6xdkKZEZOSQPdkjCOwQdrXxGSAJ9zy8bQoGXTTnClFVeSg8Z_SNsLPgZ1gewK73s5Q";
    private static final String KEY_API_ORIG = "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiIsImtpZCI6IjI4YTMxOGY3LTAwMDAtYTFlYi03ZmExLTJjNzQzM2M2Y2NhNSJ9.eyJpc3MiOiJzdXBlcmNlbGwiLCJhdWQiOiJzdXBlcmNlbGw6Z2FtZWFwaSIsImp0aSI6ImU3OTk2YmNlLWRjZmUtNGI3MC04OTk2LTQ3MGQwYTE5ZmM0NyIsImlhdCI6MTU0Mzk1ODEzMiwic3ViIjoiZGV2ZWxvcGVyLzc0M2U4ZmJhLWU3MDMtYzViMS0yZDg4LTA1MzMyM2QyODg1MSIsInNjb3BlcyI6WyJyb3lhbGUiXSwibGltaXRzIjpbeyJ0aWVyIjoiZGV2ZWxvcGVyL3NpbHZlciIsInR5cGUiOiJ0aHJvdHRsaW5nIn0seyJjaWRycyI6WyI5NS42My42MC4xMzIiLCI0Ny42MC4zNi4yMzQiXSwidHlwZSI6ImNsaWVudCJ9XX0.Dx3kySa8I43NnXLfr91KA1yt-MNI44DZlyu9vRnI_Uur-ELWj97AVZgnixRKzBDx6c1ZQS1TGMIqBbizUAKHQQ";

    private Vector vPlayerOK = new Vector();
    private Vector vPlayerError = new Vector();
    Vector  vEstadisticasPlayer = new Vector();
    int contadorPlayerError = 0;
    private static Boolean boFinalizado = false;

    String jsonClan = "";

    Boolean boApiOrig = true;

    private ProgressDialog progress;

    private static int ultfila = -1;
    private static int filaSelec = -1;
    private ListView listPagos;
    TextView  etiqSaldo;
    TextView  etiqTotBea;
    TextView  etiqTotFer;
    private DatabaseReference dbReference;
    LinearLayout zoFilaLista;
    Vector  vUsuarios = null;
    Integer nordenHistorico = 0;
    Integer numTemporada = 0;
    Date daFtemporada = null;
    Integer contadorLlamadas = 0;
    Pagos pago = null;

    private static final String TAG = "ConsultaActVent";

    ArrayList<Pagos> pagosArrayList = new ArrayList<Pagos>();

    private String  stCuerpoMail = "";
    private String  iniciales = "";
    private String  stDescrip = "";
    private String  stEspacios;
    private String stSaldo = "";
    private int idusuario = 0;
    private CosultaActivity contexto;
    private int    iTotalUserFER = 0;
    private int    iTotalUserBEA = 0;
    private int    iSaldo = 0;
    private int    iDiferencia = 0;
    private int    iDifEspacios = 0;
    private int    iTamaño = 0;
    private int    iProgress = 0;
    private int    iProgresstotal = 0;
    Vector vPagos = new Vector();
    Date daFechaActual = new Date();
    String stFechaActual = "";
    String stFechaInicio = "";

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


                abrirUrlWhatsapp ();
                //enviarMsgApp();

                return true;

            case R.id.menu_historico:

                abrirPantallaHistorico();

                return true;

            case R.id.menu_generarhistorico:

                generarHistorico();

                return true;

            case R.id.menu_playernuevos:
                contadorLlamadas = 0;
                comprobarPlayerNuevos();
                return true;


            case R.id.menu_guardartemporada:
                contadorLlamadas = 0;
                obtenerDatosClashRoyale(true, false, false );
                return true;

            case R.id.generar_ranking:
                contadorLlamadas = 0;
                opMenuRanking();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private Boolean obtenerDatosClashRoyale(Boolean boPrimeraLlamada, Boolean boNuevosPlayer, Boolean boRankings){
/*
                progress=new ProgressDialog(this);
                progress.setMessage("Descargando clan Codigo = 2Q2GCPQV");
                progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progress.setProgress(1);
                progress.show();
*/

        String stNombresNuevos = "";

        Vector vPlayersTemporadaActual = GlobalClass.getInstance().getvPlayerTemporadaActual();
        Vector vPlayersNuevosExistente = GlobalClass.getInstance().getvPlayerNuevos();
        Vector vPlayerNuevos = new Vector();
        Vector vRankings = new Vector();

        // Rankings Globales
        ArrayList  listTrofeos_gl = new ArrayList<Ordenar>();
        ArrayList listWinwars_gl = new ArrayList<Ordenar>();
        ArrayList  listCartas_gl = new ArrayList<Ordenar>();
        ArrayList  listDonaciones_gl = new ArrayList<Ordenar>();
        /*
        List<Ordenar> reportListTrofeos_gl      = new ArrayList<>();
        List<Ordenar> reportListWinwars_gl      = new ArrayList<>();
        List<Ordenar> reportListCartas_gl       = new ArrayList<>();
        List<Ordenar> reportListDonaciones_gl   = new ArrayList<>();
        */
        if (contadorLlamadas > 2) {
            return true;
        }

        if (boFinalizado){
            Log.i(TAG,"FINALIZADO");
            return true;
        }

        String stURLClan = "";

        if (boApiOrig) {
            stURLClan = URL_CLAN_ORIG_1;
        }else{
            stURLClan = URL_CLAN;
        }

        if (boPrimeraLlamada) {
            Log.i(TAG,"url="+stURLClan);
            jsonClan = llamarSW(stURLClan, boApiOrig);
            Log.i(TAG, "JSON CLAN = " + jsonClan);
        }
/*
                progress.setProgress(3);
                Log.i(TAG, "Progress Bar - 3" );
                progress.show();
*/
        if (jsonClan.equals("ERROR")){

            obtenerDatosClashRoyale(true, boNuevosPlayer, boRankings);

            if (contadorLlamadas > 2) {
                return true;
            }
        }

        String jsonPlayer = "";
        String url_player = "";

        try
        {
            JSONObject mainObjectClan = new JSONObject(jsonClan.toString());
            //Obtenemos los objetos dentro del objeto principal.

            String stPlayer = "";
            contadorPlayerError = 0;

            if (boApiOrig) {
                stPlayer = "items";
            }else{
                stPlayer = "members";
            }

            vPlayerError.clear();

            JSONArray vMiembros = mainObjectClan.getJSONArray(stPlayer);

            iTamaño = vMiembros.length();

            //iTamaño = 10;
            //iProgresstotal = 5;

            //iProgress = 95 / iTamaño;

            //Log.i(TAG,"Progress = " + iProgress);

            for (int i = 0; i < iTamaño; i++) {

                //iProgresstotal = iProgresstotal + iProgress;
                //progress.setProgress(iProgresstotal);
                //progress.show();
                //Log.i(TAG, "Progress Bar = + " +  iProgresstotal);
                JSONObject miembro = vMiembros.getJSONObject(i);

                String stName = miembro.getString("name");
                String stCodigo = miembro.getString("tag");

                //progress.setMessage("Descargando player = " + stName);

                if (boApiOrig) {
                    String stCodigo_aux = stCodigo.replace("#", "%23");
                    url_player = URL_PLAYER_ORIG + stCodigo_aux;
                } else {
                    url_player = URL_PLAYER + stCodigo;
                }

                Log.i(TAG, "PLAYER [" + String.valueOf(i) + "] =" + stName + "-" + stCodigo);

                if (existenDatosUsuario(stCodigo)){
                    Log.i(TAG, "JSON PLAYER [" + String.valueOf(i) + "] =" + stName + "-" + stCodigo + "; OK - TENGO SUS DATOS");
                    continue;
                }

                jsonPlayer = llamarSW(url_player, boApiOrig);

                Log.i(TAG, "JSON PLAYER [" + String.valueOf(i) + "] =" + stName + "-" + stCodigo + ";" + jsonPlayer);

                if (jsonPlayer.equals("ERROR"))
                    return false;

                if (!jsonPlayer.equals("ERROR")) {
                    JSONObject datosPlayer = new JSONObject(jsonPlayer.toString());

                    String trophies = "";
                    String totalDonations = "";
                    String clanCardsCollected = "";
                    String warDayWins = "";

                    if (boApiOrig){

                        String stTipoTrofeos = GlobalClass.getInstance().getStTipoTrofeos();

                        String stTrofeos = "ULTIMA";

                        if (boRankings){

                            if (stTipoTrofeos != "ULTIMA"){
                                stTrofeos = "ACTUAL";
                            }
                        }

                        if (stTrofeos == "ULTIMA") {
                            try {
                                trophies = datosPlayer.getJSONObject("leagueStatistics").getJSONObject("previousSeason").getString("trophies");
                            } catch (Exception e) {
                                trophies = datosPlayer.getString("trophies");
                            }
                        }else{
                            trophies = datosPlayer.getString("trophies");
                        }

                        totalDonations = datosPlayer.getString("totalDonations");
                        clanCardsCollected = datosPlayer.getString("clanCardsCollected");
                        warDayWins = datosPlayer.getString("warDayWins");

                    }else {

                        trophies = datosPlayer.getString("leagueStatistics");
                        JSONObject jDatosLeague = datosPlayer.getJSONObject("leagueStatistics");



                        JSONObject jStats = datosPlayer.getJSONObject("stats");
                        JSONObject jGames = datosPlayer.getJSONObject("games");

                        totalDonations = jStats.getString("totalDonations");
                        clanCardsCollected = jStats.getString("clanCardsCollected");
                        warDayWins = jGames.getString("warDayWins");
                    }

                    HashMap hmDatos = new HashMap();

                    hmDatos.put("codusuario", stCodigo);
                    hmDatos.put("nombre", stName);
                    hmDatos.put("trofeos", trophies);
                    hmDatos.put("winwars", warDayWins);
                    hmDatos.put("cartas", clanCardsCollected);
                    hmDatos.put("donaciones", totalDonations);

                    vEstadisticasPlayer.add(hmDatos);
                    vPlayerOK.add(stCodigo);
                }else{
                    vPlayerError.add(stCodigo);
                }
            }

            Log.i(TAG, "vPlayerOK.size = [" + String.valueOf(vPlayerOK.size()) + "] ;;; iTamaño = [" + String.valueOf(iTamaño) + "]");

            if (vPlayerOK.size() < iTamaño){

                if (contadorLlamadas < 3) {
                    contadorLlamadas ++;
                    obtenerDatosClashRoyale(false, boNuevosPlayer, boRankings);
                    return true;
                }
            }

            //progress.setMessage("Descarga finalizada correctamente.");
            //progress.setProgress(100);

            if (boNuevosPlayer) {

                Boolean boEncontrado = false;

                for (int i = 0; i < vEstadisticasPlayer.size(); i ++){

                    HashMap hmPlayer = (HashMap)vEstadisticasPlayer.get(i);
                    String stCodigoPlayer = String.valueOf(hmPlayer.get("codusuario"));
                    String stNamePlayer = String.valueOf(hmPlayer.get("nombre"));

                    Log.d(TAG, "vEstadisticasPlayer.Nombre = [" + stNamePlayer + "], vEstadisticasPlayer.Codigo = [" + stCodigoPlayer + "]" );

                    boEncontrado = false;

                    if (boRankings){ //Para el Ranking Global guaramos ya para el REPORT.

                        String stNombre = hmPlayer.get("nombre").toString();
                        double dotrofeos = Double.parseDouble(hmPlayer.get("trofeos").toString());
                        double dowinwars = Double.parseDouble(hmPlayer.get("winwars").toString());
                        double docartas = Double.parseDouble(hmPlayer.get("cartas").toString());
                        double dodonaciones = Double.parseDouble(hmPlayer.get("donaciones").toString());

                        listTrofeos_gl.add(new Ordenar(stNombre, dotrofeos));
                        listWinwars_gl.add(new Ordenar(stNombre, dowinwars));
                        listCartas_gl.add(new Ordenar(stNombre, docartas));
                        listDonaciones_gl.add(new Ordenar(stNombre, dodonaciones));
                    }

                    for (int j = 0; j < vPlayersTemporadaActual.size(); j ++){

                        HashMap hmPlayerTemporada = (HashMap)vPlayersTemporadaActual.get(j);
                        String stCodigoPlayerTemporada = String.valueOf(hmPlayerTemporada.get("codusuario"));

                        if (stCodigoPlayer.equals(stCodigoPlayerTemporada)){

                            Log.d(TAG, "Encontrado en Temporada - TRUE - vPlayersTemporadaActual[" + j + "]");

                            boEncontrado = true;

                            if (boRankings){ //Para el Ranking por intervalo guardamos la diferencia con lo anterior.

                                HashMap hmRankingIntervalo = new HashMap();

                                hmRankingIntervalo.put("codusuario",hmPlayer.get("codusuario"));
                                hmRankingIntervalo.put("nombre",hmPlayer.get("nombre"));

                                double doTrofeos_int = 0;
                                double doWinWars_int = 0;
                                double doCartas_int = 0;
                                double doDonaciones_int = 0;

                                double valorActual = 0;
                                double valorTemporada = 0;

                                valorActual     = Double.parseDouble(hmPlayer.get("trofeos").toString());
                                valorTemporada  = Double.parseDouble(hmPlayerTemporada.get("trofeos").toString());

                                doTrofeos_int = valorActual - valorTemporada;

                                valorActual     = Double.parseDouble(hmPlayer.get("winwars").toString());
                                valorTemporada  = Double.parseDouble(hmPlayerTemporada.get("winwars").toString());

                                doWinWars_int = valorActual - valorTemporada;

                                valorActual     = Double.parseDouble(hmPlayer.get("cartas").toString());
                                valorTemporada  = Double.parseDouble(hmPlayerTemporada.get("cartas").toString());

                                doCartas_int = valorActual - valorTemporada;

                                valorActual     = Double.parseDouble(hmPlayer.get("donaciones").toString());
                                valorTemporada  = Double.parseDouble(hmPlayerTemporada.get("donaciones").toString());

                                doDonaciones_int = valorActual - valorTemporada;

                                hmRankingIntervalo.put("trofeos", hmPlayer.get("trofeos"));
                                hmRankingIntervalo.put("trofeos_int", doTrofeos_int);
                                hmRankingIntervalo.put("winwars", doWinWars_int);
                                hmRankingIntervalo.put("cartas", doCartas_int);
                                hmRankingIntervalo.put("donaciones", doDonaciones_int);

                                vRankings.add(hmRankingIntervalo);
                            }

                            break;
                        }
                    }

                    if (!boEncontrado){

                        Log.d(TAG, "NO Encontrado en Temporada - FALSE - vPlayersTemporadaActual[NO EXISTE] ... Buscamos en Nuevos");

                        for (int j = 0; j < vPlayersNuevosExistente.size(); j ++){

                            HashMap hmPlayerNuevoExistente = (HashMap)vPlayersNuevosExistente.get(j);
                            String stCodigoNuevoExistente = String.valueOf(hmPlayerNuevoExistente.get("codusuario"));

                            if (stCodigoPlayer.equals(stCodigoNuevoExistente)) {
                                Log.d(TAG, "Encontrado en Nuevos - TRUE - vPlayersNuevosExistente[" + j + "]");
                                boEncontrado = true;
                                break;
                            }
                        }
                    }

                    if (!boEncontrado){
                        Log.d(TAG, "NO Encontrado en Nuevos - FALSE - vPlayersNuevosExistente[NO EXISTE] ... Insertamos como Nuevo");
                        vPlayerNuevos.add(hmPlayer);
                    }
                }

                stNombresNuevos = "";

                if (vPlayerNuevos.size()>0){

                    Tempplayers tp = new Tempplayers();
                    tp.setvDatosplayers(vPlayerNuevos);

                    DatabaseReference dbReferenceCLRoyale;
                    dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child("nuevos");
                    dbReferenceCLRoyale.setValue(tp);

                    GlobalClass.getInstance().setvPlayerNuevos(vPlayerNuevos);

                    //Los incorporammos a la ultima temporada asi ya están guardados.
                    for (int i = 0; i < vPlayerNuevos.size(); i ++){
                        HashMap hmPlayerNuevo = (HashMap)vPlayerNuevos.get(i);
                        stNombresNuevos = stNombresNuevos + hmPlayerNuevo.get("nombre") + "\n";
                        vPlayersTemporadaActual.add(hmPlayerNuevo);
                    }

                    numTemporada = GlobalClass.getInstance().getNumTemporadaActual();

                    tp = new Tempplayers();
                    tp.setvDatosplayers(vPlayersTemporadaActual);

                    dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child(String.valueOf(numTemporada));
                    dbReferenceCLRoyale.setValue(tp);

                }
            }else{
                HashMap hmDatos = new HashMap();

                String stFila = "";
                String stFicheroExcel = "";

                Log.i(TAG, "vEstadisticasPlayer.size = [" + String.valueOf(vEstadisticasPlayer.size()) + "]");

                Vector vDatosPlayer = new Vector();

                for (int i = 0; i < vEstadisticasPlayer.size(); i++) {

                    hmDatos = (HashMap) vEstadisticasPlayer.get(i);

                    Datosplayers dp = new Datosplayers();

                    dp.setCodusuario(hmDatos.get("codusuario").toString().trim());
                    dp.setNombre(hmDatos.get("nombre").toString().trim());
                    dp.setTrofeos(new Double(hmDatos.get("trofeos").toString()));
                    dp.setWinwars(new Double(hmDatos.get("winwars").toString()));
                    dp.setCartas(new Double(hmDatos.get("cartas").toString()));
                    dp.setDonaciones(new Double(hmDatos.get("donaciones").toString()));

                    vDatosPlayer.add(dp);

                    stFila = hmDatos.get("codusuario").toString().trim() + "|" + hmDatos.get("nombre").toString().trim() + "|" +
                            hmDatos.get("trofeos").toString().trim() + "|" + hmDatos.get("winwars").toString().trim() + "|" +
                            hmDatos.get("cartas").toString().trim() + "|" + hmDatos.get("donaciones").toString().trim();

                    stFicheroExcel = stFicheroExcel + stFila + "<br/>";
                }

                Tempplayers tp = new Tempplayers();
                tp.setvDatosplayers(vDatosPlayer);

                DatabaseReference dbReferenceCLRoyale;
                numTemporada++;
                dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child(String.valueOf(numTemporada));
                dbReferenceCLRoyale.setValue(tp);

                GlobalClass.getInstance().setNumTemporadaActual(numTemporada);

                DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
                daFtemporada = new Date();
                String stFtemporada = "";

                stFtemporada = df.format(daFtemporada);

                try {
                    daFtemporada = df.parse(stFtemporada);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                GlobalClass.getInstance().setDaFtemporada(daFtemporada);

                //Actualizamos los campos del Historico que dejara de ser actual
                DatabaseReference dbReferenceHistoricoActual;

                //Actualizamos Historico Actual
                HistoricoActual historicoactual = new HistoricoActual();

                dbReferenceHistoricoActual = FirebaseDatabase.getInstance().getReference().child("histactual");

                nordenHistorico = GlobalClass.getInstance().getHistoricoActual();

                historicoactual.setNorden(nordenHistorico);
                historicoactual.setNumtemporada(numTemporada);
                historicoactual.setFtemporada(daFtemporada);

                dbReferenceHistoricoActual.setValue(historicoactual);

                int TAM_MAX = 50;
                Random rand = new Random();

                for (int i = vEstadisticasPlayer.size(); i < TAM_MAX; i++) {
                    int numero = rand.nextInt(10000) + 1;
                    stFila = "C" + numero + "|" + "VACIO" + "|" + "0" + "|" + "0" + "|" + "0" + "|" + "0";
                    stFicheroExcel = stFicheroExcel + stFila + "<br/>";
                }

                Log.i(TAG, "vPlayerError=" + vPlayerError.size());

                if (vPlayerError.size() > 0) {

                    for (int i = 0; i < vPlayerError.size(); i++) {
                        Log.i(TAG, "Player ERROR = " + vPlayerError.get(i));
                    }
                }

                for (int i = vEstadisticasPlayer.size(); i < TAM_MAX; i++) {
                    int numero = rand.nextInt(10000) + 1;
                    stFila = "C" + numero + "|" + "VACIO" + "|" + "0" + "|" + "0" + "|" + "0" + "|" + "0";
                    stFicheroExcel = stFicheroExcel + stFila + "<br/>";
                }

                /* Ahota en pruebas todavía no lo borramos.
                Vector vPlayerNuevos = new Vector();

                tp.setvDatosplayers(vPlayerNuevos);

                dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child("nuevos");
                dbReferenceCLRoyale.setValue(tp);

                GlobalClass.getInstance().setvPlayerNuevos(vPlayerNuevos);
                */
                Log.i(TAG, "stFicheroExcel=" + stFicheroExcel);
                Log.i(TAG, "Enviamos por MAIL");
                enviarMailClashRoyale(stFicheroExcel);

                Log.i(TAG, "FINALIZADO.............");
            }

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, e.getMessage());
        }

        Runtime garbage = Runtime.getRuntime();
        garbage.gc();
        Log.i(TAG, "Liberando Recursos GC.............");

        if (boNuevosPlayer)
        {
            Log.d(TAG, "Nuevos Jugadore vPlayerNuevos.size() =" + vPlayerNuevos.size() + " - Nombres = [" + stNombresNuevos + "]");
        }

        if (boNuevosPlayer && !boRankings)
        {
            Toast t=Toast.makeText(this,"Nuevos Jugadores Encontrados : " + vPlayerNuevos.size() + "\n" + stNombresNuevos, Toast.LENGTH_SHORT);
            t.show();
        }

        if (!boNuevosPlayer && !boRankings)
        {
            Toast t=Toast.makeText(this,"La temporada ha sido guardada", Toast.LENGTH_SHORT);
            t.show();
        }

        if (boRankings){

            // Rankings por Intervalo
            ArrayList listTrofeos_in= new ArrayList<>();
            ArrayList listWinwars_in = new ArrayList<>();
            ArrayList listCartas_in = new ArrayList<>();
            ArrayList listDonaciones_in= new ArrayList<>();

            for (int i = 0; i < vRankings.size(); i ++){

                HashMap hmRanking = (HashMap) vRankings.get(i);

                String stNombre = hmRanking.get("nombre").toString();
                double dotrofeos = Double.parseDouble(hmRanking.get("trofeos_int").toString());
                double dowinwars = Double.parseDouble(hmRanking.get("winwars").toString());
                double docartas = Double.parseDouble(hmRanking.get("cartas").toString());
                double dodonaciones = Double.parseDouble(hmRanking.get("donaciones").toString());

                listTrofeos_in.add(new Ordenar(stNombre, dotrofeos));
                listWinwars_in.add(new Ordenar(stNombre, dowinwars));
                listCartas_in.add(new Ordenar(stNombre, docartas));
                listDonaciones_in.add(new Ordenar(stNombre, dodonaciones));
            }

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) { //Ordenamos todos los Rankings (GLobal e Intervalo)

                //Clase anónima
                Collections.sort(listTrofeos_gl, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listTrofeos_in, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listWinwars_gl, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listWinwars_in, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listCartas_gl, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listCartas_in, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listDonaciones_gl, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                Collections.sort(listDonaciones_in, new Comparator<Ordenar>() {
                    @Override
                    public int compare(Ordenar o1, Ordenar o2) {

                        return new Double(o2.getValor()).compareTo(o1.getValor());
                    }
                });

                //listDonaciones_gl.forEach(System.out::println);
                /*
                //Globales
                sort(reportListTrofeos_gl, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListWinwars_gl, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListCartas_gl, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListDonaciones_gl, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                //Intervalo
                sort(reportListTrofeos_in, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListWinwars_in, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListCartas_in, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                sort(reportListDonaciones_in, Comparator.comparing(Ordenar::getValor).thenComparing(Ordenar::getNombre).reversed());
                */
            }

            HashMap hmReport = new HashMap();

            Date daFechaActual = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            hmReport.put("fechareport",sdf.format(daFechaActual));
            hmReport.put("reportListTrofeos_gl",listTrofeos_gl);
            hmReport.put("reportListWinwars_gl",listWinwars_gl);
            hmReport.put("reportListCartas_gl",listCartas_gl);
            hmReport.put("reportListDonaciones_gl",listDonaciones_gl);

            hmReport.put("reportListTrofeos_in",listTrofeos_in);
            hmReport.put("reportListWinwars_in",listWinwars_in);
            hmReport.put("reportListCartas_in",listCartas_in);
            hmReport.put("reportListDonaciones_in",listDonaciones_in);

            DatabaseReference dbReferenceCLRoyale;
            dbReferenceCLRoyale = FirebaseDatabase.getInstance().getReference().child("clroyale").child("reports");
            dbReferenceCLRoyale.setValue(hmReport);

            Log.d(TAG, "Guardamos Rankings en BD - Procedemos a genenerar los HTML");

            String stResultado = generarContenidoFicheroHTML(listTrofeos_gl, listWinwars_gl, listCartas_gl, listDonaciones_gl,
                                                             listTrofeos_in, listWinwars_in, listCartas_in, listDonaciones_in);

            if (!stResultado.equals("OK")){
                return false;
            }
        }

        return true;
    }

    private Boolean existenDatosUsuario(String stCodigo){

        String stPlayerArray = "";

        for (int j = 0; j<vPlayerOK.size();j++){

            stPlayerArray = (String)vPlayerOK.get(j);

            if (stPlayerArray.equals(stCodigo)){
                return true;
            }
        }

        return false;
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {

                case R.id.navigation_modif:

                    navegarPantallaActualización("MODIF");

                    return true;
                case R.id.navigation_borrar:

                    borrarRegistro();

                    return true;
                case R.id.navigation_nuevo:

                    navegarPantallaActualización("ALTA");

                    return true;
            }
            return false;
        }

    };

    private void navegarPantallaActualización(String modo){

        if (modo == "MODIF"){
            pago = (Pagos) pagosArrayList.get(filaSelec);
        }else{
            pago = null;
            filaSelec = -1;
        }

        Intent iAltaPagosActivity = new Intent(this, AltaPagosActivity.class);
        iAltaPagosActivity.putExtra("filaselec", filaSelec);
        iAltaPagosActivity.putExtra("pago", pago);
        startActivity(iAltaPagosActivity);
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

    private void comprobarPlayerNuevos(){
        obtenerDatosClashRoyale(true, true, false);
    }

    private void opMenuRanking(){

        String inExisteRankingActualizado = GlobalClass.getInstance().getInrankings();

        if (inExisteRankingActualizado.equals("N")) {
            abrirDialogoRanking();
        }else
        {
            generarRankings();
        }
    }

    private void generarRankings(){

        String inExisteRankingActualizado = GlobalClass.getInstance().getInrankings();
        String stResultado = "";

        if (inExisteRankingActualizado.equals("N")) {
            if (!obtenerDatosClashRoyale(true, true, true)){
                Log.i(TAG,"Ocurrio un Error al Llamar a los SW o Generar los Rankings");
                Toast t=Toast.makeText(this,"Ocurrio un Error al Llamar a los SW ó Generar los Rankings", Toast.LENGTH_SHORT);
                t.show();
                return;
            }
        }else{

            HashMap hmReports = GlobalClass.getInstance().getReports();

            ArrayList listTrofeos_gl = new ArrayList<>();
            ArrayList listWinwars_gl = new ArrayList<>();
            ArrayList listCartas_gl = new ArrayList<>();
            ArrayList listDonaciones_gl = new ArrayList<>();
            ArrayList listTrofeos_in = new ArrayList<>();
            ArrayList listWinwars_in = new ArrayList<>();
            ArrayList listCartas_in = new ArrayList<>();
            ArrayList listDonaciones_in = new ArrayList<>();

            listTrofeos_gl = (ArrayList) hmReports.get("reportListTrofeos_gl");
            listWinwars_gl = (ArrayList) hmReports.get("reportListWinwars_gl");
            listCartas_gl = (ArrayList) hmReports.get("reportListCartas_gl");
            listDonaciones_gl = (ArrayList) hmReports.get("reportListDonaciones_gl");
            listTrofeos_in = (ArrayList) hmReports.get("reportListTrofeos_in");
            listWinwars_in = (ArrayList) hmReports.get("reportListWinwars_in");
            listCartas_in = (ArrayList) hmReports.get("reportListCartas_in");
            listDonaciones_in = (ArrayList) hmReports.get("reportListDonaciones_in");


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                Log.i(TAG, "ANTES de ORDENAR");

                for (int i = 0; i < listTrofeos_gl.size(); i++) {

                    HashMap hmPlayer = (HashMap) listTrofeos_gl.get(i);
                    String stNombre = hmPlayer.get("nombre").toString();
                    Double doValor = new Double(hmPlayer.get("valor").toString());
                    String stValor = String.valueOf(doValor.intValue());

                    Log.i(TAG, stNombre + " -- " + stValor);
                }

                //Clase anónima
                Collections.sort(listTrofeos_gl, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });

                Collections.sort(listTrofeos_in, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });


                Collections.sort(listWinwars_gl, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });


                Collections.sort(listWinwars_in, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });

                Collections.sort(listCartas_gl, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });


                Collections.sort(listCartas_in, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });

                Collections.sort(listDonaciones_gl, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });

                Collections.sort(listDonaciones_in, new Comparator<HashMap>() {
                    @Override
                    public int compare(HashMap h1, HashMap h2) {

                        double dovalor1 = new Double(h1.get("valor").toString());
                        double dovalor2 = new Double(h2.get("valor").toString());

                        return new Double(dovalor2).compareTo(dovalor1);
                    }
                });

                //listTrofeos_in.sort(new ComparardorValores());

                Log.i(TAG, "DESPUES de ORDENAR");

                for (int i = 0; i < listTrofeos_gl.size(); i++) {

                    HashMap hmPlayer = (HashMap) listTrofeos_gl.get(i);
                    String stNombre = hmPlayer.get("nombre").toString();
                    Double doValor = new Double(hmPlayer.get("valor").toString());
                    String stValor = String.valueOf(doValor.intValue());

                    Log.i(TAG, stNombre + " -- " + stValor);
                }

                /*listWinwars_in.sort(new ComparardorValores());
                listCartas_in.sort(new ComparardorValores());
                listDonaciones_in.sort(new ComparardorValores());
                listTrofeos_gl.sort(new ComparardorValores());
                listWinwars_gl.sort(new ComparardorValores());
                listCartas_gl.sort(new ComparardorValores());
                listDonaciones_gl.sort(new ComparardorValores());
                */
            }

            stResultado = generarContenidoFicheroHTML(listTrofeos_gl, listWinwars_gl, listCartas_gl, listDonaciones_gl,
                                                      listTrofeos_in, listWinwars_in, listCartas_in, listDonaciones_in);

            if (!stResultado.equals("OK")){
                Log.i(TAG,"Ocurrio un Error al Generar los Rankings");
                Toast t=Toast.makeText(this,"Ocurrio Generar los Rankings", Toast.LENGTH_SHORT);
                t.show();
                return;
            }

        }

        Log.i(TAG,"Los ficheros del  Ranking se han generado.");
        Toast t=Toast.makeText(this,"Los ficheros del Ranking se han generado correctamente...", Toast.LENGTH_SHORT);
        t.show();
    }

    private String generarContenidoFicheroHTML(ArrayList reportListTrofeos_gl, ArrayList reportListWinwars_gl,
                                               ArrayList reportListCartas_gl,  ArrayList reportListDonaciones_gl,
                                               ArrayList reportListTrofeos_in, ArrayList reportListWinwars_in,
                                               ArrayList reportListCartas_in,  ArrayList reportListDonaciones_in){

        Log.i(TAG, "Inicio funcion generarContenidoFicheroHTML");

        GenerarRankings gr = new GenerarRankings();
        String stPaginaHTML_Global = "";
        String stPaginaHTML_Intervalo = "";

        try {
            stPaginaHTML_Global = gr.generarPaginaRankingHMTL("GLOBAL", reportListTrofeos_gl, reportListWinwars_gl, reportListCartas_gl, reportListDonaciones_gl);
            Log.d(TAG, "Generado HTML - Ranking Intervalo");
            stPaginaHTML_Intervalo = gr.generarPaginaRankingHMTL("INTERVALO", reportListTrofeos_in, reportListWinwars_in, reportListCartas_in, reportListDonaciones_in);
            Log.d(TAG, "Generado HTML - Ranking Global");
        }catch(Exception e){
            Log.e(TAG, e.getMessage() + "-" + e.toString());
            e.printStackTrace();
            return e.getMessage() + "-" + e.toString();
        }

        Log.d(TAG, "Procedemos a guardarlo en tarjeta SD");

        String stResultado = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            stResultado = guardarFicheroExterno("GLOBAL", stPaginaHTML_Global);

            if (!stResultado.equals("OK")){
                return stResultado;
            }

            Log.d(TAG, "Guardardo con éxito en tarjeta SD - GLOBAL");
            stResultado = guardarFicheroExterno("INTERVALO", stPaginaHTML_Intervalo);

            if (!stResultado.equals("OK")){
                return stResultado;
            }

            Log.d(TAG, "Guardardo con éxito en tarjeta SD - INTERVALO");
        }

        return "OK";
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private String guardarFicheroExterno(String stTipo, String pv_stCadena) {

        try {
            String estadoSD = Environment.getExternalStorageState();

            if (!estadoSD.equals(Environment.MEDIA_MOUNTED)) {
                Log.i(TAG, "No hay permisos de escritura en SD");
                return "No hay permisos de escritura en SD";
            }

            String archivoHTML = "";
            File ficheroHTML = null;

            if (stTipo.equals("INTERVALO")) {
                archivoHTML = "Redbuls_Rank_Inter.html";
            } else {
                archivoHTML = "Redbuls_Rank_Global.html";
            }

            File[] ruta_sd = new File[0];
            String stRuta_SD = "";

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {
                ruta_sd = getExternalFilesDirs(null);
            }

            if (ruta_sd.length > 1 && ruta_sd[0] != null && ruta_sd[1] != null)
                stRuta_SD = ruta_sd[1].toString() + "/mifileapp";
            else
                stRuta_SD = ruta_sd[1].toString() + "/mifileapp";

            File dir = new File(stRuta_SD);

            if (!dir.exists()) {
                Log.i(TAG, "creando directorio: " + "mifileapp");
                dir.mkdir();
            }

            ficheroHTML = new File(dir, archivoHTML);

            try {
                OutputStreamWriter osw = new OutputStreamWriter(new FileOutputStream(ficheroHTML));
                osw.write(pv_stCadena.toString());
                osw.close();
                Log.i(TAG, "Cerramos Fichero en tarjeta SD");
            } catch (Exception ex) {
                Log.e(TAG, "Error al escribir fichero a tarjeta SD");
                return "Error al escribir fichero a tarjeta SD";
            }
        }catch (Exception e){
            Log.e(TAG, e.getMessage() + "-" + e.toString());
            return e.getMessage() + "-" + e.toString();
        }

        return "OK";
    }

    private void enviarMailClashRoyale(String pv_stFicheroExcel){

        Date daHoy = new Date();
        String stFechaHoy = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar calendario = new GregorianCalendar();

        int hora, minutos, segundos;
        String sthora, stminutos;

        hora =calendario.get(Calendar.HOUR_OF_DAY);
        minutos = calendario.get(Calendar.MINUTE);

        if (hora < 10){
            sthora = "0" + String.valueOf(hora);
        }else{
            sthora = String.valueOf(hora);
        }

        if (minutos < 10){
            stminutos = "0" + String.valueOf(minutos);
        }else{
            stminutos = String.valueOf(minutos);
        }

        String stHora = sthora + ":" + stminutos;

        stFechaHoy = sdf.format(daHoy);

        //String[] TO = {"iyagredbulls@gmail.com"}; //aquí pon tu correo
        String[] TO = {"nanonaninonano@gmail.com"};
        String[] CC = {""};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setData(Uri.parse("mailto:iyagredbulls@gmail.com"));
        emailIntent.setData(Uri.parse("mailto:nanonaninonano@gmail.com"));
        emailIntent.setType("text/html");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
        emailIntent.putExtra(Intent.EXTRA_CC, CC);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Clasif Global CLAN España Redbulls - Fecha " + stFechaHoy + " - " + stHora);
        emailIntent.putExtra(Intent.EXTRA_TEXT,Html.fromHtml(pv_stFicheroExcel).toString());

        try {
            startActivity(Intent.createChooser(emailIntent, "Enviar email..."));
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(CosultaActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }
    };

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
            Toast.makeText(CosultaActivity.this,
                    "No tienes clientes de email instalados.", Toast.LENGTH_SHORT).show();
        }


    };

    private String llamarSW(String stURL, boolean pv_boApiOrig){

        Log.i(TAG, "Contador Llamada - Llamar SW = " + contadorLlamadas );

        String stKey = "";

        if (pv_boApiOrig){

            if (contadorLlamadas == 0){
                stKey = GlobalClass.getInstance().getToken1();
            }else if (contadorLlamadas == 1){
                stKey = GlobalClass.getInstance().getToken2();
            }else if (contadorLlamadas == 2){
                stKey = GlobalClass.getInstance().getToken3();
            }

        }else{
            stKey = KEY_API;
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        URL url = null;
        HttpURLConnection conn;
        HttpsURLConnection conn2;

        try {

            url = new URL(stURL);

            Log.i(TAG, "url="+url.toString());

            BufferedReader reader = null;

            if (contadorLlamadas <= 1){

                conn2 = (HttpsURLConnection) url.openConnection();
                conn2.setRequestProperty("Authorization",stKey);
                conn2.connect();

                if (conn2.getResponseCode() == 200){

                    reader = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"), 8);

                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    String json = sb.toString();

                    return json;

                }else if (conn2.getResponseCode() == 403){

                    //Usamos otro token que tengamos registrado.
                    Toast t=Toast.makeText(this,"Llamada " + contadorLlamadas + "ERROR CREDENCIALES - CODIGO = " + conn2.getResponseCode(), Toast.LENGTH_SHORT);
                    t.show();
                    contadorLlamadas ++;
                    return "ERROR";

                }else {
                    Toast t=Toast.makeText(this,"Llamada " + contadorLlamadas + "ERROR CODIGO = " + conn2.getResponseCode(), Toast.LENGTH_SHORT);
                    t.show();
                    contadorLlamadas ++;
                    return "ERROR";
                }

            }else {

                conn = (HttpURLConnection) url.openConnection();
                conn.setRequestProperty("Authorization",stKey);
                conn.connect();

                if (conn.getResponseCode() == 200){

                    reader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"), 8);

                    StringBuilder sb = new StringBuilder();

                    String line = null;
                    while ((line = reader.readLine()) != null)
                    {
                        sb.append(line + "\n");
                    }
                    String json = sb.toString();

                    return json;

                }else if (conn.getResponseCode() == 403){

                    //Usamos otro token que tengamos registrado.
                    Toast t=Toast.makeText(this,"Llamada " + contadorLlamadas + "ERROR CREDENCIALES - CODIGO = " + conn.getResponseCode(), Toast.LENGTH_SHORT);
                    t.show();
                    contadorLlamadas ++;
                    return "ERROR";

                }else {
                    Toast t=Toast.makeText(this,"Llamada " + contadorLlamadas + "ERROR CODIGO = " + conn.getResponseCode(), Toast.LENGTH_SHORT);
                    t.show();
                    contadorLlamadas ++;
                    return "ERROR";
                }
            }

        } catch (IOException e) {
            Toast t=Toast.makeText(this,"ERROR EXCEPCION:" + e.toString(), Toast.LENGTH_LONG);
        }

        return "ERROR";

        /*
        Iterator<String> keys = mainObject.keys();

        while (keys.hasNext())
        {
            // obtiene el nombre del objeto.
            String key = keys.next();
            Log.i(TAG, "objeto : " + key);

            if (String.valueOf(key).equals("members")){

                JSONObject jsonObject = mainObject.getJSONObject(key);

                valorName = jsonObject.getString("name");
                valorCodigo = jsonObject.getString("tag");

                //Imprimimos los valores.
                Log.i(TAG, "valorName="+valorName);
                Log.i(TAG, "valorCodigo="+valorCodigo);
            }
        }
        */
    }

    private void abrirPantallaHistorico() {

        Intent HistorialActivity = new Intent(this, HistorialActivity.class);
        HistorialActivity.putExtra("finihist_act", stFechaInicio);
        HistorialActivity.putExtra("saldo_act", iSaldo);
        HistorialActivity.putExtra("idusuario_act", idusuario);
        startActivity(HistorialActivity);
    }

    private void generarHistorico() {

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Generar Historico");
        dialogo1.setMessage("¿Deséa guardar todos los pagos en nuevo Historico?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

                //Actualizamos los campos del Historico que dejara de ser actual
                DatabaseReference dbReferenceHistoricoActual;
                HistoricoPagos historicopagos = new HistoricoPagos();
                dbReferenceHistoricoActual = FirebaseDatabase.getInstance().getReference().child("cuentasFer").child(String.valueOf(nordenHistorico));

                historicopagos.setSaldo(iSaldo);
                historicopagos.setIdusuario(idusuario);
                historicopagos.setFfinhist(stFechaActual);
                historicopagos.setFinihist(stFechaInicio);
                historicopagos.setvPagos(vPagos);

                Log.i(TAG,"Tamaño Vpagos.size =" + vPagos.size());

                dbReferenceHistoricoActual.setValue(historicopagos);

                //Actualizamos Historico Actual
                HistoricoActual historicoactual = new HistoricoActual();

                dbReferenceHistoricoActual = FirebaseDatabase.getInstance().getReference().child("histactual");

                Log.i(TAG,"PRUEBA ----- nordenActual ANTES = " + nordenHistorico);
                nordenHistorico ++;
                historicoactual.setNorden(nordenHistorico);

                numTemporada = GlobalClass.getInstance().getNumTemporadaActual();
                daFtemporada = GlobalClass.getInstance().getDaFtemporada();

                historicoactual.setNumtemporada(numTemporada);
                historicoactual.setFtemporada(daFtemporada);

                dbReferenceHistoricoActual.setValue(historicoactual);
                GlobalClass.getInstance().setHistoricoActual(nordenHistorico);

                historicoGenerado();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void historicoGenerado() {
        Toast t=Toast.makeText(this,"Se ha generado el Historico.", Toast.LENGTH_SHORT);
        t.show();
    }

    private void borrarRegistro(){

        AlertDialog.Builder dialogo1 = new AlertDialog.Builder(this);
        dialogo1.setTitle("Borrar Registro");
        dialogo1.setMessage("¿Deséa borrar el registro seleccionado.?");
        dialogo1.setCancelable(false);
        dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {

            pago = (Pagos) pagosArrayList.get(filaSelec);
            String stKey = pago.getIdpago();
            dbReference = FirebaseDatabase.getInstance().getReference().child("cuentasFer").child(String.valueOf(nordenHistorico)).child("pagos").child(stKey);
            dbReference.removeValue();
            registroBorrado();
            }
        });
        dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogo1, int id) {
                cancelar();
            }
        });
        dialogo1.show();
    }

    public void registroBorrado() {
        Toast t=Toast.makeText(this,"Registro borrado.", Toast.LENGTH_SHORT);
        t.show();
    }

    public void cancelar() {

    }

    private void cargarLista(CosultaActivity contexto, int R_layout_IdView, ArrayList pagosArrayList){

        listPagos.setAdapter(new Lista_adaptador(contexto, R_layout_IdView, pagosArrayList){

            @Override
            public void onEntrada(Object entrada, View view) {

                if (entrada != null) {

                    TextView etUsuario = (TextView) view.findViewById(R.id.etUsuario);
                    TextView etFecha = (TextView) view.findViewById(R.id.etFecha);
                    TextView etDescrip = (TextView) view.findViewById(R.id.etDescripcion);
                    TextView etImporte = (TextView) view.findViewById(R.id.etImporte);
                    TextView etIdPago = (TextView) view.findViewById(R.id.etidPago);

                    if(etUsuario!=null)
                        etUsuario.setText(String.valueOf(((Pagos) entrada).getIniciales()));

                    if(etFecha!=null)
                        etFecha.setText(String.valueOf(((Pagos) entrada).getFecha()));

                    if(etDescrip!=null){
                        String stDescrip =  String.valueOf(((Pagos) entrada).getTipo1()) + " " +
                                            String.valueOf(((Pagos) entrada).getTipo2()) + " " +
                                            String.valueOf(((Pagos) entrada).getDescrip());
                        etDescrip.setText(stDescrip.trim());
                    }

                    if(etImporte!=null)
                        etImporte.setText(String.valueOf(((Pagos) entrada).getImporte()) + " €");

                    if(etIdPago!=null)
                        etIdPago.setText(String.valueOf(((Pagos) entrada).getIdpago()));



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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cosulta);

        vUsuarios = (Vector) GlobalClass.getInstance().getUsuarios();
        nordenHistorico = GlobalClass.getInstance().getHistoricoActual();

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        stFechaActual = sdf.format(daFechaActual);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        listPagos = (ListView) findViewById(R.id.pagos_list);
        zoFilaLista  = (LinearLayout) findViewById(R.id.zoFilaLista);

        contexto = this;

        Log.i(TAG, "nordenHistorico = " + nordenHistorico);

        dbReference = FirebaseDatabase.getInstance().getReference().child("cuentasFer").child(String.valueOf(nordenHistorico)).child("pagos");

        dbReference.orderByChild("fcompra/time").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                iTotalUserFER = 0;
                iTotalUserBEA = 0;
                iSaldo = 0;
                iDiferencia = 0;
                ultfila = -1;
                filaSelec = 0;

                pagosArrayList.clear();
                Log.i(TAG, "getValue="+dataSnapshot.getValue());

                for (DataSnapshot singleChild : dataSnapshot.getChildren()) {

                    pago = singleChild.getValue(Pagos.class);

                    vPagos.add(pago);
                    // Para el primer Registro Guardamos la fecha Historico Inicio.
                    if (vPagos.size() == 1)
                    {
                        stFechaInicio = pago.getFecha();
                    }

                    idusuario = pago.getIdusuario();

                    iniciales = GlobalClass.getIniciales(vUsuarios, idusuario);

                    pago.setIniciales(iniciales);
                    pago.setIdpago(singleChild.getKey());
                    pago.setIdusuario(idusuario);

                    if (iniciales.toString().equals("FMG")){
                        iTotalUserFER += pago.getImporte();
                    }else{
                        iTotalUserBEA += pago.getImporte();
                    }

                    pagosArrayList.add(pago);

                    if (pago.getTipo1()!=null && !String.valueOf(pago.getTipo1()).equals("null"))
                        stDescrip = pago.getTipo1();

                    if (pago.getTipo2()!=null && !String.valueOf(pago.getTipo2()).equals("null"))
                        stDescrip = stDescrip + " " + pago.getTipo2();

                    if (pago.getDescrip()!=null && !String.valueOf(pago.getDescrip()).equals("null"))
                        stDescrip = stDescrip + " " + pago.getDescrip().trim();

                    int iDifEspacios = 40 - stDescrip.length();

                    stEspacios = "";

                    for (int i= 0; i<iDifEspacios; i++)
                    {
                        stEspacios = stEspacios + "&nbsp;";
                    }

                    stDescrip = stDescrip + stEspacios;

                    stCuerpoMail = stCuerpoMail +  iniciales +  "&nbsp;&nbsp;" + pago.getFecha()  +  "&nbsp;&nbsp;" +
                            stDescrip +  String.valueOf(pago.getImporte() + "€")  +  "<br/>";

                }

                String stTotBea = "";
                String stTotFer = "";

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

                stCuerpoMail = "<html><body>" + stTotBea + "<br/>" + stTotFer + "<br/>" + "------------------------" + "<br/>" + stSaldo +
                        "<br/>******************* Detalle Gastos *******************<br/>" + stCuerpoMail + "</body></html>";

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
                cargarLista(contexto, R.layout.lista_pagos_activity, pagosArrayList);

                if (iTotalUserFER > iTotalUserBEA){
                    idusuario = 1;
                }else{
                    idusuario = 2;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listPagos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> pariente, View view, int posicion, long id) {

                Log.i(TAG,"PRUEBA ----- onItemClick - posicion = " + posicion);
                Log.i(TAG,"PRUEBA ----- onItemClick - id = " + id);
                Log.i(TAG,"PRUEBA ----- onItemClick - pariente.getCount() = " + pariente.getCount());
                Log.i(TAG,"PRUEBA ----- onItemClick - pariente.getFirstVisiblePosition() = " + pariente.getFirstVisiblePosition());
                Log.i(TAG,"PRUEBA ----- onItemClick - pariente.getLastVisiblePosition() = " + pariente.getLastVisiblePosition());

                Log.i(TAG,"***************************************************************");
                filaSelec = posicion;
                int filapintar = 0;

                if (posicion < pariente.getCount()){

                    filapintar = posicion;

                    for (int i = 0; i <= pariente.getLastVisiblePosition() - pariente.getFirstVisiblePosition(); i++){
/*
                        Log.i(TAG,"PRUEBA ----- pariente.getFirstVisiblePosition() = " + pariente.getFirstVisiblePosition());
                        Log.i(TAG,"PRUEBA ----- pariente.getLastVisiblePosition() = " + pariente.getLastVisiblePosition());
                        Log.i(TAG,"PRUEBA ----- pariente.i() = " + i);
                        Log.i(TAG,"PRUEBA ----- pariente.getChildAt() = " + pariente.getChildAt(i));

                        Log.i(TAG,"PRUEBA -------------------------------------");
*/
                        pariente.getChildAt(i).setBackgroundColor(Color.WHITE);
                    }

                    if (pariente.getFirstVisiblePosition() > 0) {
                        filapintar = posicion - pariente.getFirstVisiblePosition();
                    }

                    Log.i(TAG,"PRUEBA ----- onItemClick - filapintar = " + filapintar);
                }

                Log.i(TAG,"***************************************************************");

                int orange = getResources().getColor(R.color.Orange);

                // pariente.setBackgroundColor(Color.WHITE);
                pariente.getChildAt(filapintar).setBackgroundColor(orange);

                // if (ultfila != -1 && ultfila != posicion){
                //    pariente.getChildAt(ultfila).setBackgroundColor(Color.WHITE);
                // }

                ultfila = posicion;


                pago = (Pagos) pariente.getItemAtPosition(posicion);

                String texto  =     pago.getTipo1() + " " +
                        pago.getTipo2() + " " +
                        pago.getDescrip();

                Toast toast = Toast.makeText(CosultaActivity.this, texto.trim(), Toast.LENGTH_LONG);

                TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
                v.setTextColor(Color.CYAN);

                toast.show();
            }
        });
    }

    public void abrirDialogoRanking() {

        String[] stTrofeos = new String[2];

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Selecciona la obtación de los trofeos:");

        stTrofeos[0] = "Trofeos Actuales";
        stTrofeos[1] = "Trofeos Ultima Temporada";

        builder.setItems(stTrofeos, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {

                if (which == 1){
                    GlobalClass.getInstance().setStTipoTrofeos("ULTIMA");
                }else{
                    GlobalClass.getInstance().setStTipoTrofeos("ACTUAL");
                }

                generarRankings();
            }
        });

        // builder.setPositiveButton("Aceptar",null);
        builder.create();
        builder.show();
    }
}
