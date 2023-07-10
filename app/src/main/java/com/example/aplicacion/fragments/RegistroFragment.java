package com.example.aplicacion.fragments;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.aplicacion.R;
import com.example.aplicacion.db.DatabaseHelper;
import com.example.aplicacion.entidades.Prenda;
import com.example.aplicacion.entidades.Publico;
import com.example.aplicacion.entidades.Zona;
import com.example.aplicacion.interfaces.IComunicaFragments;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RegistroFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RegistroFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private InicioFragment.OnFragmentInteractionListener mListener;

    IComunicaFragments interfaceComunicaFragments;

    View vista;
    Activity actividad;
    Button btnRegresoRegistro, btnRegistrar, btnBuscaRuc;
    EditText razonsocial,ruc,direccion,referencia;
    TextView propietario;
    Spinner comboPrenda,comboPublico,comboZona;

    ArrayList<String> listaPrendas;
    ArrayList<Prenda> prendasList;
    ArrayList<String>listaPublicos;
    ArrayList<Publico> publicosList;
    ArrayList<String>listaZonas;
    ArrayList<Zona> zonasList;

    DatabaseHelper conn;

    RequestQueue requestQueue;
    String api_principal = "https://api.apis.net.pe/v1/ruc?numero=";

    public RegistroFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RegistroFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RegistroFragment newInstance(String param1, String param2) {
        RegistroFragment fragment = new RegistroFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        vista = inflater.inflate(R.layout.fragment_registro, container, false);

        btnRegresoRegistro = (Button) vista.findViewById(R.id.btnRegresoReg);

        razonsocial = (EditText) vista.findViewById(R.id.txtRazon);
        ruc = (EditText) vista.findViewById(R.id.txtRuc);
        propietario = (TextView) vista.findViewById(R.id.txtPropietario);
        direccion = (EditText) vista.findViewById(R.id.txtDireccion);
        referencia = (EditText) vista.findViewById(R.id.txtReferencia);
        comboPrenda = (Spinner) vista.findViewById(R.id.comboPrenda);
        comboPublico = (Spinner) vista.findViewById(R.id.comboPublico);
        comboZona = (Spinner) vista.findViewById(R.id.comboZona);

        conn = new DatabaseHelper(actividad,"geo_gamarra.db",null,1);

        consultarListaPrendas();
        consultarListaPublicos();
        consultarListaZonas();

        ArrayAdapter<CharSequence> adaptadorPrenda = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item,listaPrendas);
        comboPrenda.setAdapter(adaptadorPrenda);
        ArrayAdapter<CharSequence> adaptadorPublico = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item,listaPublicos);
        comboPublico.setAdapter(adaptadorPublico);
        ArrayAdapter<CharSequence> adaptadorZona = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item,listaZonas);
        comboZona.setAdapter(adaptadorZona);

        btnRegresoRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.mostrarMenu();
            }
        });

        btnRegistrar = vista.findViewById(R.id.btnRegistrar);
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btnRegistrar: registrarTienda();
                }
            }
        });

        btnBuscaRuc = vista.findViewById(R.id.btnBuscaRuc);
        btnBuscaRuc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ruc.getText().length() == 11) {
                    requestQueue = Volley.newRequestQueue(actividad);
                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, api_principal + ruc.getText(), null,
                        new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String raz_soc = response.getString("nombre");
                                propietario.setText(raz_soc);
                            } catch (JSONException e) {
                                Log.i("error", e.toString());
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Log.i("error", error.toString());
                        }
                    });
                    request.setRetryPolicy(new DefaultRetryPolicy(1500, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    requestQueue.add(request);
                } else {
                    Toast.makeText(actividad, "N° de RUC no valido!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return vista;
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
        values.put("razonsocial", razonsocial.getText().toString());
        values.put("ruc", ruc.getText().toString());
        values.put("propietario", propietario.getText().toString());
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

            Toast.makeText(actividad, "Tienda Registrada con Exito!", Toast.LENGTH_SHORT).show();
            db.close();
        } else {
            Toast.makeText(actividad, "Debe seleccionar una opcion de cada lista desplegable", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad=(Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        }
        if (context instanceof InicioFragment.OnFragmentInteractionListener) {
            mListener = (InicioFragment.OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}