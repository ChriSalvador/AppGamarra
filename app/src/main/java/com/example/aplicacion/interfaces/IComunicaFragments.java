package com.example.aplicacion.interfaces;

import android.net.Uri;

public interface IComunicaFragments {
    void onFragmentInteraction(Uri uri);

    public void mostrarMenu();
    public void registrarTienda();
    public void consultarTienda();
}
