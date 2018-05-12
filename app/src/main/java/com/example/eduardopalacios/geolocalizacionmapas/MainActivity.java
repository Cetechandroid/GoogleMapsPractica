package com.example.eduardopalacios.geolocalizacionmapas;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.eduardopalacios.geolocalizacionmapas.Descargar.ObtenerCoordenadas;
import com.example.eduardopalacios.geolocalizacionmapas.Descargar.obtenerDireccion;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    EditText latitud,longitud,direccion;
    Button btnUbicacion,btnLongLat,btnDireccion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        latitud=findViewById(R.id.ETLat);
        longitud=findViewById(R.id.ETLong);
        direccion=findViewById(R.id.ETDir);

        btnUbicacion=findViewById(R.id.btn1);
        btnUbicacion.setOnClickListener(this);

        btnLongLat=findViewById(R.id.btn2);
        btnLongLat.setOnClickListener(this);

        btnDireccion=findViewById(R.id.btn3);
        btnDireccion.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId())
        {
            case R.id.btn1:

                break;

            case R.id.btn2:
                obtenerDireccion  obtenerDireccion=new obtenerDireccion();
                obtenerDireccion.execute(longitud.getText().toString(),latitud.getText().toString());
                String direccion="";

                try {
                  direccion = obtenerDireccion.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Toast.makeText(getApplicationContext(),direccion,Toast.LENGTH_SHORT).show();

                break;

            case R.id.btn3:
                ObtenerCoordenadas obtenerCoordenadas=new ObtenerCoordenadas();
                obtenerCoordenadas.execute("upiicsa");
                double[]coordenadas=new double[2];
                try {
                    coordenadas=obtenerCoordenadas.get();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                Bundle bundle=new Bundle();

                bundle.putDoubleArray("COOR",coordenadas);

                intent.putExtras(bundle);
                startActivity(intent);



                break;

                default:

                    break;
        }

    }
}
