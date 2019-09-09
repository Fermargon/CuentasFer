package com.mar.fer.cuentasfer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import android.util.Log;

import static java.nio.file.Paths.get;

public class GenerarRankings implements Serializable{

    private static final String TAG = "GenerarRankingActVent";

    public String generarPaginaRankingHMTL(String stTipo, ArrayList reportListTrofeos, ArrayList reportListWinwars, ArrayList reportListCartas, ArrayList reportListDonaciones){

        String stTitulo = "";
        String stTITULO = "";

        if (stTipo.equals("INTERVALO")){
            stTitulo = "Intervalo";
        }else{
            stTitulo = "Global";
        }

        stTITULO = stTitulo.toUpperCase();

        String stRetornoCarro = "\n";

        String stPaginaRanging = "<!DOCTYPE html>" + stRetornoCarro;
        stPaginaRanging += "<html>" + stRetornoCarro;
        stPaginaRanging = "<html>" + stRetornoCarro;
        stPaginaRanging += "<head>" + stRetornoCarro;
        stPaginaRanging += "<title>Ranking " + stTitulo + " España Redbulls </title>" + stRetornoCarro;
        stPaginaRanging += "<meta charset='utf-8' />" + stRetornoCarro;
        stPaginaRanging += "<style>" + stRetornoCarro;
        stPaginaRanging += "body {" + stRetornoCarro;
        stPaginaRanging += "width: 1240px;" + stRetornoCarro;
        stPaginaRanging += "height:100%;" + stRetornoCarro;
        stPaginaRanging += "margin: 40px auto;" + stRetornoCarro;
        stPaginaRanging += "font-family: 'trebuchet MS', 'Lucida sans', Arial;" + stRetornoCarro;
        stPaginaRanging += "font-size: 14px;" + stRetornoCarro;
        stPaginaRanging += "color: #444;" + stRetornoCarro;
        stPaginaRanging += "background: url(fondoazul2.jpg);" + stRetornoCarro;
        stPaginaRanging += "background-repeat: no-repeat;" + stRetornoCarro;
        stPaginaRanging += "background-size: 100%;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "table {" + stRetornoCarro;
        stPaginaRanging += "*border-collapse: collapse;" + stRetornoCarro;
        stPaginaRanging += "border-spacing: 0;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered {" + stRetornoCarro;
        stPaginaRanging += "border: solid #ccc 1px;" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 1px;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 1px;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 1px;" + stRetornoCarro;
        stPaginaRanging += "-webkit-box-shadow: 0 1px 1px #ccc;" + stRetornoCarro;
        stPaginaRanging += "-moz-box-shadow: 0 1px 1px #ccc;" + stRetornoCarro;
        stPaginaRanging += "box-shadow: 0 1px 1px #ccc;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered tr:hover {" + stRetornoCarro;
        stPaginaRanging += "background: #fbf8e9;" + stRetornoCarro;
        stPaginaRanging += "-o-transition: all 0.1s ease-in-out;" + stRetornoCarro;
        stPaginaRanging += "-webkit-transition: all 0.1s ease-in-out;" + stRetornoCarro;
        stPaginaRanging += "-moz-transition: all 0.1s ease-in-out;" + stRetornoCarro;
        stPaginaRanging += "-ms-transition: all 0.1s ease-in-out;" + stRetornoCarro;
        stPaginaRanging += "transition: all 0.1s ease-in-out;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered td, .bordered th {" + stRetornoCarro;
        stPaginaRanging += "border-left: 1px solid #ccc;" + stRetornoCarro;
        stPaginaRanging += "border-top: 1px solid #ccc;" + stRetornoCarro;
        stPaginaRanging += "padding: 5px;" + stRetornoCarro;
        stPaginaRanging += "text-align: left;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered th {" + stRetornoCarro;
        stPaginaRanging += "background-color: #dce9f9;" + stRetornoCarro;
        stPaginaRanging += "background-image: -webkit-gradient(linear, left top, left bottom, from(#ebf3fc), to(#dce9f9));" + stRetornoCarro;
        stPaginaRanging += "background-image: -webkit-linear-gradient(top, #ebf3fc, #dce9f9);" + stRetornoCarro;
        stPaginaRanging += "background-image:    -moz-linear-gradient(top, #ebf3fc, #dce9f9);" + stRetornoCarro;
        stPaginaRanging += "background-image:     -ms-linear-gradient(top, #ebf3fc, #dce9f9);" + stRetornoCarro;
        stPaginaRanging += "background-image:      -o-linear-gradient(top, #ebf3fc, #dce9f9);" + stRetornoCarro;
        stPaginaRanging += "background-image:         linear-gradient(top, #ebf3fc, #dce9f9);" + stRetornoCarro;
        stPaginaRanging += "-webkit-box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;" + stRetornoCarro;
        stPaginaRanging += "-moz-box-shadow:0 1px 0 rgba(255,255,255,.8) inset;" + stRetornoCarro;
        stPaginaRanging += "box-shadow: 0 1px 0 rgba(255,255,255,.8) inset;" + stRetornoCarro;
        stPaginaRanging += "border-top: none;" + stRetornoCarro;
        stPaginaRanging += "text-shadow: 0 1px 0 rgba(255,255,255,.5);" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered td:first-child, .bordered th:first-child {" + stRetornoCarro;
        stPaginaRanging += "border-left: none;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered th:first-child {" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 4px 0 0 0;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 4px 0 0 0;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 4px 0 0 0;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered th:last-child {" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 0 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 0 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 0 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered th:only-child{" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 4px 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 4px 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 4px 4px 0 0;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered tr:last-child td:first-child {" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 0 0 0 4px;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 0 0 0 4px;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 0 0 0 4px;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += ".bordered tr:last-child td:last-child {" + stRetornoCarro;
        stPaginaRanging += "-moz-border-radius: 0 0 4px 0;" + stRetornoCarro;
        stPaginaRanging += "-webkit-border-radius: 0 0 4px 0;" + stRetornoCarro;
        stPaginaRanging += "border-radius: 0 0 4px 0;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "table td:nth-child(2) {" + stRetornoCarro;
        stPaginaRanging += "width: 180px;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "#col1 {" + stRetornoCarro;
        stPaginaRanging += "float: left;" + stRetornoCarro;
        stPaginaRanging += "width: 310px;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "#col2 {" + stRetornoCarro;
        stPaginaRanging += "float: left;" + stRetornoCarro;
        stPaginaRanging += "width: 310px;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "#coltitulo {" + stRetornoCarro;
        stPaginaRanging += "float: left;" + stRetornoCarro;
        stPaginaRanging += "width: 1240px;" + stRetornoCarro;
        stPaginaRanging += "}" + stRetornoCarro;

        stPaginaRanging += "</style>" + stRetornoCarro;
        stPaginaRanging += "</head>" + stRetornoCarro;

        stPaginaRanging += "<body id='body1'>" + stRetornoCarro;
/*
<div id="col2" style="background-color:none" align="center">
	<img src="fondo4.jpg" style="width: 100%; height: 140px;"/>
</div>

<div id="col2" style="background-color:none" align="center">
	<img src="fondo5.jpg" style="width: 100%; height: 140px;"/>
</div>

<div id="col2" style="background-color:none" align="center">
	<img src="EspañaRedbulls.jpg" style="width: 100%; height: 140px;"/>
</div>

<div id="col2" style="background-color:none" align="center">
	<img src="fondo7.jpg" style="width: 100%; height: 140px;"/>
</div>

<div id="col2" style="background-color:none" align="center">
	<img src="fondo8.jpg" style="width: 100%; height: 140px;"/>
</div>
*/
        stPaginaRanging += "<div id='coltitulo' style='background-color:none' align='center'>" + stRetornoCarro;
        stPaginaRanging += "<br/><br/><br/><h2>RANKING " + stTITULO + "</h2>" + stRetornoCarro;
        stPaginaRanging += "</div>" + stRetornoCarro;

        stPaginaRanging += crearTablaHtml(reportListTrofeos, "TROFEOS");
        stPaginaRanging += crearTablaHtml(reportListWinwars, "WINWARS");
        stPaginaRanging += crearTablaHtml(reportListCartas, "CARTAS");
        stPaginaRanging += crearTablaHtml(reportListDonaciones, "DONACIONES");

        stPaginaRanging += "</body>" + stRetornoCarro;
        stPaginaRanging += "</html>" + stRetornoCarro;

        return stPaginaRanging;
    }

    private String crearTablaHtml(ArrayList pv_reportList, String stTipo){

        String stRetornoCarro = "\n";
        String cadenaHtml = "";

        cadenaHtml += "<div id='col1' style='background-color:none'>" + stRetornoCarro;

        cadenaHtml += "<table class='bordered'>" + stRetornoCarro;
        cadenaHtml += "<thead>" + stRetornoCarro;

        cadenaHtml += "<tr>" + stRetornoCarro;
        cadenaHtml += "<th>Pos.</th>" + stRetornoCarro;
        cadenaHtml += "<th>Player</th>" + stRetornoCarro;

        if (stTipo == "TROFEOS"){
            cadenaHtml += "<th>Trofeos</th>" + stRetornoCarro;
        }else if (stTipo == "WINWARS"){
            cadenaHtml += "<th>Wisn W.</th>" + stRetornoCarro;
        }else if (stTipo == "CARTAS"){
            cadenaHtml += "<th>Cartas</th>" + stRetornoCarro;
        }else if (stTipo == "DONACIONES"){
            cadenaHtml += "<th>Donac.</th>" + stRetornoCarro;
        }

        cadenaHtml += "</tr>" + stRetornoCarro;
        cadenaHtml += "</thead>" + stRetornoCarro;

        //La otra forma se recorria directamente el pv_reportlist y cogia con un Map, ya que el HashMap no podía convertirlo.

        String stNameObject = pv_reportList.getClass().getSimpleName();
        Log.i(TAG, "stNameObject="+stNameObject);

        if (pv_reportList.size() > 0){
            stNameObject = pv_reportList.get(0).getClass().getSimpleName();
            Log.i(TAG, "stNameObject[i]="+stNameObject);
        }

        for (int i = 0; i < pv_reportList.size(); i++) {

            String stNombre = "";
            Double doValor = null;

            if (String.valueOf(stNameObject).equals("HashMap")){
                HashMap hmPlayer = (HashMap) pv_reportList.get(i);
                stNombre = hmPlayer.get("nombre").toString();
                doValor =  new Double(hmPlayer.get("valor").toString());

            }else  {

                ArrayList adatosReport = new ArrayList<>();
                adatosReport = (ArrayList) pv_reportList;

                stNombre = ((Ordenar) adatosReport.get(i)).getNombre();
                doValor = ((Ordenar) adatosReport.get(i)).getValor();
            }


            String stValor = String.valueOf(doValor.intValue());

            cadenaHtml += "<tr>" + stRetornoCarro;

            cadenaHtml += "<td>";
            cadenaHtml += String.valueOf(i+1);
            cadenaHtml += "</td>" + stRetornoCarro;

            cadenaHtml += "<td>";
            cadenaHtml += stNombre;
            cadenaHtml += "</td>" + stRetornoCarro;

            cadenaHtml += "<td>";
            cadenaHtml += stValor;
            cadenaHtml += "</td>" + stRetornoCarro;

            cadenaHtml += "</tr>" + stRetornoCarro;
        }

        cadenaHtml += "</table>" + stRetornoCarro;
        cadenaHtml += "</div>" + stRetornoCarro;

        return cadenaHtml;
    }
}
