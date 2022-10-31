package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.aplicacion.db.DatabaseHelper;
import com.example.aplicacion.entidades.Prenda;
import com.example.aplicacion.entidades.Publico;
import com.example.aplicacion.entidades.Tienda;
import com.example.aplicacion.entidades.Zona;

import java.util.ArrayList;

public class RegistroActivity extends AppCompatActivity {

    Button btnRegresoRegistro, btnRegistrar;
    EditText ruc,nombres,apellidos,direccion,referencia;
    Spinner comboPrenda,comboPublico,comboZona;     

    ArrayList<String>listaPrendas;
    ArrayList<Prenda> prendasList;
    ArrayList<String>listaPublicos;
    ArrayList<Publico> publicosList;
    ArrayList<String>listaZonas;
    ArrayList<Zona> zonasList;

    DatabaseHelper conn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        btnRegresoRegistro = findViewById(R.id.btnRegresoRegistro);
        btnRegresoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegistroActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        ruc = (EditText) findViewById(R.id.txtRuc);
        nombres = (EditText) findViewById(R.id.txtNombres);
        apellidos = (EditText) findViewById(R.id.txtApellidos);
        direccion = (EditText) findViewById(R.id.txtDireccion);
        referencia = (EditText) findViewById(R.id.txtReferencia);
        comboPrenda = (Spinner) findViewById(R.id.comboPrenda);
        comboPublico = (Spinner) findViewById(R.id.comboPublico);
        comboZona = (Spinner) findViewById(R.id.comboZona);

        conn = new DatabaseHelper(getApplicationContext(),"geoapp.db",null,1);

        consultarListaPrendas();
        consultarListaPublicos();
        consultarListaZonas();

        ArrayAdapter<CharSequence> adaptadorPrenda = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaPrendas);
        comboPrenda.setAdapter(adaptadorPrenda);
        ArrayAdapter<CharSequence> adaptadorPublico = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaPublicos);
        comboPublico.setAdapter(adaptadorPublico);
        ArrayAdapter<CharSequence> adaptadorZona = new ArrayAdapter(this, android.R.layout.simple_spinner_item,listaZonas);
        comboZona.setAdapter(adaptadorZona);

        btnRegistrar = findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnRegistrar: registrarTienda();
                }
            }
        });
    }

    private void consultarListaPrendas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Prenda p = null;
        prendasList = new ArrayList<Prenda>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_PRENDA,null);
        while (cursor.moveToNext()) {
            p = new Prenda();
            p.setIdprenda(cursor.getInt(0));
            p.setNombreprenda(cursor.getString(1));
            Log.i("idprenda",p.getIdprenda().toString());
            Log.i("nombreprenda",p.getNombreprenda());
            prendasList.add(p);
        }
        obtenerListaPrenda();
    }
    private void obtenerListaPrenda() {
        listaPrendas = new ArrayList<String>();
        listaPrendas.add("Seleccione");
        for(int i=0;i<prendasList.size();i++) {
            listaPrendas.add(prendasList.get(i).getIdprenda()+" - "+prendasList.get(i).getNombreprenda());
        }
    }

    private void consultarListaPublicos() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Publico pu = null;
        publicosList = new ArrayList<Publico>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_PUBLICO,null);
        while (cursor.moveToNext()) {
            pu = new Publico();
            pu.setIdpublico(cursor.getInt(0));
            pu.setDescripcion(cursor.getString(1));
            Log.i("idpublico",pu.getIdpublico().toString());
            Log.i("descripcion",pu.getDescripcion());
            publicosList.add(pu);
        }
        obtenerListaPublico();
    }
    private void obtenerListaPublico() {
        listaPublicos = new ArrayList<String>();
        listaPublicos.add("Seleccione");
        for(int i=0;i<publicosList.size();i++) {
            listaPublicos.add(publicosList.get(i).getIdpublico()+" - "+publicosList.get(i).getDescripcion());
        }
    }

    private void consultarListaZonas() {
        SQLiteDatabase db = conn.getReadableDatabase();
        Zona zo = null;
        zonasList = new ArrayList<Zona>();
        Cursor cursor = db.rawQuery("SELECT * FROM "+DatabaseHelper.TABLE_ZONA,null);
        while (cursor.moveToNext()) {
            zo = new Zona();
            zo.setIdzona(cursor.getInt(0));
            zo.setNombrezona(cursor.getString(1));
            Log.i("idzona",zo.getIdzona().toString());
            Log.i("nombrezona",zo.getNombrezona());
            zonasList.add(zo);
        }
        obtenerListaZona();
    }
    private void obtenerListaZona() {
        listaZonas = new ArrayList<String>();
        listaZonas.add("Seleccione");
        for(int i=0;i<zonasList.size();i++) {
            listaZonas.add(zonasList.get(i).getIdzona()+" - "+zonasList.get(i).getNombrezona());
        }
    }

    private void registrarTienda() {
        SQLiteDatabase db = conn.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("ruc", ruc.getText().toString());
        values.put("nombre", nombres.getText().toString());
        values.put("apellido", apellidos.getText().toString());
        values.put("direccion", direccion.getText().toString());
        values.put("referencia", referencia.getText().toString());

        int idComboTienda = (int) comboPrenda.getSelectedItemId();
        int idComboPublico = (int) comboPublico.getSelectedItemId();
        int idComboZona = (int) comboZona.getSelectedItemId();

        if (idComboTienda != 0 && idComboPublico != 0 && idComboZona != 0) {
            Log.i("TAMAÑO",prendasList.size()+"");
            Log.i("id tienda", idComboTienda+"");
            Log.i("id tienda-1", (idComboTienda-1)+"");
            int idpre = prendasList.get(idComboTienda-1).getIdprenda();
            int idpub = publicosList.get(idComboPublico-1).getIdpublico();
            int idzon = zonasList.get(idComboZona-1).getIdzona();
            Log.i("id tienda", idpre+"");

            values.put("idprenda", idpre);
            values.put("idpublico", idpub);
            values.put("idzona", idzon);

            db.insert(DatabaseHelper.TABLE_TIENDA,"idtienda", values);

            Toast.makeText(getApplicationContext(), "Tienda Registrada con Exito!", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(getApplicationContext(), "Debe seleccionar una opcion de cada lista desplegable", Toast.LENGTH_SHORT).show();
        }
    }
}