package com.tuinercia.inercia.implementation;

import android.support.v4.app.DialogFragment;

import com.tuinercia.inercia.DTO.User;
import com.tuinercia.inercia.R;
import com.tuinercia.inercia.fragments.dialogs.ErrorServerDialog;
import com.tuinercia.inercia.fragments.dialogs.GeneralDialogFragment;
import com.tuinercia.inercia.interfaces.InerciaApiCreateUserListener;

/**
 * Created by ricar on 22/01/2018.
 */

public class InerciaApiCreateUserListenerImpl implements InerciaApiCreateUserListener {

    CrearCuentaWizard2Fragment crearCuentaWizard2Fragment;

    public InerciaApiCreateUserListenerImpl(CrearCuentaWizard2Fragment crearCuentaFragment) {
        this.crearCuentaWizard2Fragment = crearCuentaFragment;
    }

    @Override
    public void onCreateUserSuccefull(User user, String userD, String password) {
        crearCuentaWizard2Fragment.messageSuccessCreation(userD,password);
    }

    @Override
    public void onCreateUserError(String errorMessage) {
        GeneralDialogFragment.getInstance(errorMessage, crearCuentaWizard2Fragment.getContext().getString(R.string.btn_login_check),null)
                             .show(crearCuentaWizard2Fragment.getFragmentManager(),crearCuentaWizard2Fragment.FRAGMENT_TAG);
    }

    @Override
    public void onErrorServer(int statusCode) {
        DialogFragment dialog = new ErrorServerDialog();
        dialog.setCancelable(false);
        dialog.show(crearCuentaWizard2Fragment.getFragmentManager(),crearCuentaWizard2Fragment.FRAGMENT_TAG);

    }
}
