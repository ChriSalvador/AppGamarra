package com.example.aplicacion;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.aplicacion.fragments.BusquedaFragment;
import com.example.aplicacion.fragments.InicioFragment;
import com.example.aplicacion.fragments.RegistroFragment;
import com.example.aplicacion.interfaces.IComunicaFragments;

public class MainActivity extends AppCompatActivity implements IComunicaFragments, InicioFragment.OnFragmentInteractionListener, RegistroFragment.OnFragmentInteractionListener,
                                                                BusquedaFragment.OnFragmentInteractionListener {

    Fragment fragmentInicio, registroFragment, busquedaFragment;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentInicio = new InicioFragment();
        registroFragment = new RegistroFragment();
        busquedaFragment = new BusquedaFragment();

        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }

    @Override
    public void mostrarMenu() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,fragmentInicio).commit();
    }

    @Override
    public void registrarTienda() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,registroFragment).commit();
    }

    @Override
    public void consultarTienda() {
        getSupportFragmentManager().beginTransaction().replace(R.id.contenedorFragments,busquedaFragment).commit();
    }

}