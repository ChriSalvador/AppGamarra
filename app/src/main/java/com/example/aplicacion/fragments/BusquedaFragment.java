package com.example.aplicacion.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.aplicacion.R;
import com.example.aplicacion.adapters.AdaptadorResultados;
import com.example.aplicacion.db.DatabaseHelper;
import com.example.aplicacion.entidades.Resultados;
import com.example.aplicacion.interfaces.IComunicaFragments;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BusquedaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BusquedaFragment extends Fragment {

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
    Button btnRegresoBusqueda,btnBuscar;
    Spinner comboPrenda,comboPublico,comboZona;
    RecyclerView recyclerTiendas;
    TextView txtSeparador,txtMensaje;

    ArrayList<Resultados> listaResultados;

    public BusquedaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment BusquedaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static BusquedaFragment newInstance(String param1, String param2) {
        BusquedaFragment fragment = new BusquedaFragment();
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
        vista = inflater.inflate(R.layout.fragment_busqueda, container, false);

        comboPrenda = vista.findViewById(R.id.comboPrenda);
        comboPublico = vista.findViewById(R.id.comboPublico);
        comboZona = vista.findViewById(R.id.comboZona);
        txtMensaje = vista.findViewById(R.id.txtSinDatos);

        llenarListas();

        recyclerTiendas = vista.findViewById(R.id.recyclerTiendasId);
        recyclerTiendas.setLayoutManager(new LinearLayoutManager(this.actividad));
        recyclerTiendas.setHasFixedSize(true);

        txtSeparador = vista.findViewById(R.id.separadorId);
        btnBuscar = vista.findViewById(R.id.btnBuscar);

        btnRegresoBusqueda = vista.findViewById(R.id.btnRegresoBu);
        btnRegresoBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.mostrarMenu();
            }
        });

        consultarResultados("SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona");

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tipoPrenda = comboPrenda.getSelectedItem().toString();
                String tipoPublico = comboPublico.getSelectedItem().toString();
                String tipoZona = comboZona.getSelectedItem().toString();
                String consulta = "";

                if (tipoPrenda.equals("Todos")) {
                    if (tipoPublico.equalsIgnoreCase("Todos") && tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona";
                    } else if (!tipoPublico.equalsIgnoreCase("Todos") && tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and pu.descripcion='"+tipoPublico+"'";
                    } else if (tipoPublico.equalsIgnoreCase("Todos") && !tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and z.nombrezona='"+tipoZona+"'";
                    } else if (!tipoPublico.equalsIgnoreCase("Todos") && !tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and pu.descripcion='"+tipoPublico+"' and z.nombr ezona='"+tipoZona+"'";
                    }
                } else {
                    if (tipoPublico.equalsIgnoreCase("Todos") && tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and p.nombreprenda='"+tipoPrenda+"'";
                    } else if (!tipoPublico.equalsIgnoreCase("Todos") && tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and p.nombreprenda='"+tipoPrenda+"' and pu.descripcion='"+tipoPublico+"'";
                    } else if (tipoPublico.equalsIgnoreCase("Todos") && !tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and p.nombreprenda='"+tipoPrenda+"' and z.nombrezona='"+tipoZona+"'";
                    } else if (!tipoPublico.equalsIgnoreCase("Todos") && !tipoZona.equalsIgnoreCase("Todos")) {
                        consulta = "SELECT t.idtienda,t.razonsocial,t.direccion,t.referencia,p.nombreprenda,pu.descripcion,z.nombrezona " +
                                "FROM " + DatabaseHelper.TABLE_TIENDA + " t," + DatabaseHelper.TABLE_PRENDA + " p," +
                                DatabaseHelper.TABLE_PUBLICO + " pu," + DatabaseHelper.TABLE_ZONA + " z " +
                                "WHERE t.idprenda=p.idprenda and t.idpublico=pu.idpublico and t.idzona=z.idzona " +
                                "and p.nombreprenda='"+tipoPrenda+"' and pu.descripcion='"+tipoPublico+"' and z.nombrezona='"+tipoZona+"'";
                    }
                }

                consultarResultados(consulta);
            }
        });

        return vista;
    }

    private void consultarResultados(String consulta) {
        DatabaseHelper conn = new DatabaseHelper(actividad, "geo_gamarra.db", null, 1);
        SQLiteDatabase db = conn.getReadableDatabase();

        Resultados resultados = null;
        listaResultados = new ArrayList<Resultados>();

        Cursor cursor = db.rawQuery(consulta, null);
        while (cursor.moveToNext()) {
            resultados = new Resultados();
            resultados.setId(cursor.getInt(0));
            resultados.setRazonSoc(cursor.getString(1));
            resultados.setDireccion(cursor.getString(2));
            resultados.setReferencia(cursor.getString(3));
            resultados.setPrenda(cursor.getString(4));
            resultados.setPublico(cursor.getString(5));
            resultados.setZona(cursor.getString(6));
            listaResultados.add(resultados);
        }

        if (listaResultados.size() > 0) {
            txtMensaje.setText("Se encontraron " + listaResultados.size() + " resultados");
        } else {
            txtMensaje.setText("No se encontraron resultados");
        }

        llenarAdaptadorTiendas();
    }

    private void llenarAdaptadorTiendas() {
        AdaptadorResultados adaptadorResultados = new AdaptadorResultados(listaResultados);
        recyclerTiendas.setAdapter(adaptadorResultados);
    }

    public void llenarListas() {
        ArrayList<String> listaPrenda = new ArrayList<>();
        listaPrenda.add("Todos");
        listaPrenda.add("Polos");
        listaPrenda.add("Casacas");
        listaPrenda.add("Camisas");
        listaPrenda.add("Poleras");
        listaPrenda.add("Jeans");
        listaPrenda.add("Shorts");

        ArrayList<String> listaPublico = new ArrayList<>();
        listaPublico.add("Todos");
        listaPublico.add("Hombres");
        listaPublico.add("Mujeres");
        listaPublico.add("Niños");
        listaPublico.add("Niñas");

        ArrayList<String> listaZona = new ArrayList<>();
        listaZona.add("Todos");
        listaZona.add("Norte");
        listaZona.add("Sur");
        listaZona.add("Este");
        listaZona.add("Oeste");

        ArrayAdapter<CharSequence> adapterPrenda = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item, listaPrenda);
        comboPrenda.setAdapter(adapterPrenda);
        ArrayAdapter<CharSequence> adapterPublico = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item, listaPublico);
        comboPublico.setAdapter(adapterPublico);
        ArrayAdapter<CharSequence> adapterZona = new ArrayAdapter(actividad, android.R.layout.simple_spinner_item, listaZona);
        comboZona.setAdapter(adapterZona);
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