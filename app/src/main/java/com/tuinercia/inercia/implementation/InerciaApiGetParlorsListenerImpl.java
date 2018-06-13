package com.tuinercia.inercia.implementation;

import android.graphics.BitmapFactory;
import android.support.v4.app.DialogFragment;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tuinercia.inercia.DTO.Parlor;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.ErrorConexionDialog;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.interfaces.InerciaApiGetParlorsListener;

import java.util.ArrayList;

/**
 * Created by ricar on 08/11/2017.
 */

public class InerciaApiGetParlorsListenerImpl implements InerciaApiGetParlorsListener {

    ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment;

    public InerciaApiGetParlorsListenerImpl(ReservacionGeolocalizacionFragment reservacionGeolocalizacionFragment) {
        this.reservacionGeolocalizacionFragment = reservacionGeolocalizacionFragment;
    }

    @Override
    public void onGetParlorsSuccess(ArrayList<Parlor> parlors) {
        reservacionGeolocalizacionFragment.res_parlors = parlors;
        int i = 0;
        for (Parlor p : reservacionGeolocalizacionFragment.res_parlors){
            MarkerOptions marker;
            try{
                // create marker
                marker = new MarkerOptions().position(new LatLng(Double.parseDouble(p.getCoord_x()),Double.parseDouble(p.getCoord_y()))).title(Integer.toString(i));
                // Changing marker icon
                marker.icon(BitmapDescriptorFactory.fromBitmap(
                        BitmapFactory.decodeResource(reservacionGeolocalizacionFragment.getContext().getResources(), R.drawable.tag_map)
                ));
                // adding marker
                reservacionGeolocalizacionFragment.gMap.addMarker(marker);

                if (!p.getPic1_url().substring(0,5).equalsIgnoreCase("http:")){
                    if (!p.getPic1_url().substring(0,6).equalsIgnoreCase("https:")){
                        p.setPic1_url("http:" + p.getPic1_url());
                    }
                }
            }catch(Exception e){
                e.getStackTrace();
            }
            i++;
        }
        reservacionGeolocalizacionFragment.gMap.setOnInfoWindowClickListener(reservacionGeolocalizacionFragment);
        reservacionGeolocalizacionFragment.gMap.setInfoWindowAdapter(new ReservacionGeolocalizacionFragment.MapsAdapterCustom());
        reservacionGeolocalizacionFragment.gMap.setOnMarkerClickListener(reservacionGeolocalizacionFragment);
    }

    @Override
    public void onFailChargeParlors(String errorMessage) {
        DialogFragment dialog = new ErrorConexionDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.show(reservacionGeolocalizacionFragment.getFragmentManager(),reservacionGeolocalizacionFragment.FRAGMENT_TAG);
    }
}
