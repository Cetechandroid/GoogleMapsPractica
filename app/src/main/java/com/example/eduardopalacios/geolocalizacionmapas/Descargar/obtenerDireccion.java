package com.example.eduardopalacios.geolocalizacionmapas.Descargar;

import android.os.AsyncTask;
import android.util.Log;

import com.example.eduardopalacios.geolocalizacionmapas.com.example.Example;
import com.example.eduardopalacios.geolocalizacionmapas.com.example.Result;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

/**
 * Created by oemy9 on 12/05/2018.
 */

public class obtenerDireccion extends AsyncTask<String,Integer,String> {

    @Override
    protected String doInBackground (String... valores) {

        String latitud=valores[0];
        String longitud=valores[1];
        String Json="";
        int status = 0;
        String direccion="";

        String cadena="http://maps.googleapis.com/maps/api/geocode/json?latlng="+latitud+","+longitud+"&sensor=false";
        try {

            URL url=new URL(cadena);
            HttpURLConnection connection=(HttpURLConnection)url.openConnection();

            status= connection.getResponseCode();
            if (status==HttpURLConnection.HTTP_OK)
            {


                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String lectura;


                while ((lectura=bufferedReader.readLine())!=null)
                {
                    Json=Json+lectura;
                }

                Gson gson=new Gson();
                Example example=gson.fromJson(Json, Example.class);

                List<Result>results=example.getResults();
                direccion=results.get(0).getFormattedAddress();



            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return direccion;
    }
}
