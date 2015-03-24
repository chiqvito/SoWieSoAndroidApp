package pl.chiqvito.sowieso.ui.dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.bus.events.LoginEvent;
import pl.chiqvito.sowieso.bus.events.LoginInfoEvent;
import pl.chiqvito.sowieso.rest.dto.CredentialDTO;
import pl.chiqvito.sowieso.utils.Boast;
import pl.chiqvito.sowieso.utils.MD5;

public class LoginDialog extends DialogFragment {

    private static final String TAG = LoginDialog.class.getName();

    private TextView login;
    private TextView password;
    private ProgressDialog progressDialog;

    public static LoginDialog newInstance() {
        LoginDialog dialog = new LoginDialog();
        Bundle args = new Bundle();
        dialog.setArguments(args);
        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage(getString(R.string.msg_working));
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(), AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);

        builder.setTitle(R.string.title_login);

        builder.setPositiveButton(R.string.action_login, null);
        builder.setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_login, null);
        builder.setView(dialogView);

        login = (TextView) dialogView.findViewById(R.id.txt_login);
        password = (TextView) dialogView.findViewById(R.id.txt_password);

        final AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface di) {
                Button b = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        login();
                    }
                });
            }
        });
        return dialog;
    }

    private void login() {
        progressDialog.show();
        final String username = login.getText().toString();
        final String passwd = password.getText().toString();
        CredentialDTO cred = new CredentialDTO();
        cred.setLogin(username);
        cred.setPassword(MD5.md5(passwd));
        EventBus.getDefault().post(new LoginEvent(cred));
    }

    public void onEventMainThread(LoginInfoEvent event) {
        Log.v(TAG, "event:" + event);
        progressDialog.dismiss();
        switch (event.getStatus()) {
            case LoginInfoEvent.LOGIN: {
                dismiss();
                break;
            }
            case LoginInfoEvent.FAIL: {
                Boast.showText(getActivity(), getString(R.string.msg_can_not_login), Toast.LENGTH_SHORT);
                break;
            }
        }
    }

}
