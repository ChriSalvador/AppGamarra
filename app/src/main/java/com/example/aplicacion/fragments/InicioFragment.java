package com.example.aplicacion.fragments;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.aplicacion.MainActivity;
import com.example.aplicacion.R;
import com.example.aplicacion.db.DatabaseHelper;
import com.example.aplicacion.interfaces.IComunicaFragments;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link InicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    IComunicaFragments interfaceComunicaFragments;

    View vista;
    Activity actividad;
    Button btnRegistro;
    Button btnBusqueda;
    Button btnCrear;

    public InicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment InicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InicioFragment newInstance(String param1, String param2) {
        InicioFragment fragment = new InicioFragment();
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
        vista = inflater.inflate(R.layout.fragment_inicio, container, false);
        btnRegistro = vista.findViewById(R.id.btnRegistro);
        btnBusqueda = vista.findViewById(R.id.btnBusqueda);

        btnRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.registrarTienda();
            }
        });

        btnBusqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceComunicaFragments.consultarTienda();
            }
        });

        /*
        btnCrear = vista.findViewById(R.id.btnCrearBD);
        btnCrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseHelper dbHelper = new DatabaseHelper(actividad,"geo_gamarra.db",null,1);
                SQLiteDatabase db = dbHelper.getWritableDatabase();
                if(db != null){
                    Toast.makeText(actividad, "BASE DE DATOS CREADA", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(actividad, "ERROR AL CREAR BASE DE DATOS", Toast.LENGTH_LONG).show();
                }
            }
        });
         */
        // DESCOMENTAR LO ANTERIOR PARA CREAR LA BASE DE DATOS, LUEGO DE ESTAR CREADA VOLVER A COMENTAR Y AGREGAR android:visibility="invisible" AL BOTON CREAD BD en fragment_inicio.xml
        return vista;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof Activity) {
            this.actividad=(Activity) context;
            interfaceComunicaFragments= (IComunicaFragments) this.actividad;
        }
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}