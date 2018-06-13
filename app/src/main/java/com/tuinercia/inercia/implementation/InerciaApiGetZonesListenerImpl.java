package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;
import android.widget.ArrayAdapter;

import com.tuinercia.inercia.DTO.Zone;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiGetZonesListener;

/**
 * Created by ricar on 08/11/2017.
 */

public class InerciaApiGetZonesListenerImpl implements InerciaApiGetZonesListener {


    ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment;

    public InerciaApiGetZonesListenerImpl(ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment) {
        this.reservacionGeolocalizacionFragment = reservacionGeolocalizacionFragment;
    }

    @Override
    public void onZonesReceived(Zone[] zones) {
        ArrayAdapter<Zone> adapter = new ArrayAdapter<>(reservacionGeolocalizacionFragment.getActivity(),android.R.layout.simple_spinner_dropdown_item,zones);
        reservacionGeolocalizacionFragment.spn_Zonas.setAdapter(adapter);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }

    @Override
    public void onZonesNoReceived(String errorMessage) {
        DialogFragment dialog = new ErrorConexionDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }
}
