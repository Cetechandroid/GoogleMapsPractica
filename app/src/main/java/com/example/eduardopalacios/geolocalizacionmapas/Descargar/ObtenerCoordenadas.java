package com.example.eduardopalacios.geolocalizacionmapas.Descargar;

import android.os.AsyncTask;
import android.util.Log;

import com.example.eduardopalacios.geolocalizacionmapas.clasesDireccion.Example;
import com.example.eduardopalacios.geolocalizacionmapas.clasesDireccion.Geometry;
import com.example.eduardopalacios.geolocalizacionmapas.clasesDireccion.Location;
import com.example.eduardopalacios.geolocalizacionmapas.clasesDireccion.Result;
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

public class ObtenerCoordenadas  extends AsyncTask<String,Integer,double[]> {
    @Override
    protected double[] doInBackground (String... valor) {

        double latitud,longitud;

        double coordenadas[]=new double[2];



        String direccion="";

        direccion=valor[0];

        String cadena="http://maps.googleapis.com/maps/api/geocode/json?address="+direccion+"+&sensor=false";





        try {

            URL uri=new URL(cadena);
            HttpURLConnection conection=(HttpURLConnection)uri.openConnection();

            int respuesta=conection.getResponseCode();


            if (respuesta==HttpURLConnection.HTTP_OK)

            {

                String line;
                String Json="";
                BufferedReader reader=new BufferedReader(new InputStreamReader(conection.getInputStream()));


                while ((line=reader.readLine())!=null)

                {

                    Json=Json+line;

                }

                Gson gson=new Gson();

                Example example=gson.fromJson(Json,Example.class);

                List<Result> results=example.getResults();

                Geometry geometry=results.get(0).getGeometry();

                Location location=geometry.getLocation();



                latitud=location.getLat();

                longitud=location.getLng();

                coordenadas[0]=latitud;

                coordenadas[1]=longitud;

            }

        } catch (MalformedURLException e) {



            e.printStackTrace();

        } catch (IOException e) {


            e.printStackTrace();
        }
        return coordenadas;
    }
}
