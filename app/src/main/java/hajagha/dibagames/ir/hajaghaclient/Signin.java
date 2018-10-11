package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import Interfaces.OnDataReceive;

/**
 * Created by Pars on 5/14/2016.
 */
public class Signin extends Activity {
    private EditText edtUsername;
    private EditText edtPassword;
    private CheckBox chkRememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);
        ui();
    }

    private void ui() {
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        chkRememberMe = (CheckBox) findViewById(R.id.chkrememberme);
        Button btnLogin = (Button) findViewById(R.id.btnlogin);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] ok = new boolean[2];
                ok[0] = validation(edtUsername, getResources().getString(R.string.errorusername));
                ok[1] = validation(edtPassword, getResources().getString(R.string.errorpassword));
                for (boolean b : ok) {
                    if (b == false) {
                        return;
                    }
                }
                login();
            }
        });
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Signin.this, Signup.class);
                startActivity(i);
            }
        });
        load();
    }

    private boolean validation(final EditText edt, final String error) {

        if (edt.getText().toString().trim().length() <= 0) {
            edt.setError(error);
            return false;
        } else {
            return true;
        }

    }

    private void login() {
        final String strUsername = edtUsername.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
        final String strPassword = edtPassword.getText().toString().trim().replace(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
        String[] values = {strUsername, strPassword, "u"};
        new RestWebService(this, new OnDataReceive() {
            @Override
            public void Received(String data) {
                if (!data.equals("false")) {
                    boolean isChecked = chkRememberMe.isChecked();
                    if (isChecked) {
                        App.setSetting(Signin.this, App.xmlSettingName, "username", strUsername);
                        App.setSetting(Signin.this, App.xmlSettingName, "password", strPassword);
                    }
                    App.ID = Integer.valueOf(data.replaceAll("\n", "").trim());
                    String msg = getResources().getString(R.string.welcome);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                    Intent i = new Intent(Signin.this, MainQuestion.class);
                    startActivity(i);
                } else {
                    String msg = getResources().getString(R.string.failinlogin);
                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
                }
            }
        }, App.keysLogin, values, App.urlsignin);
    }

    private void load() {
        String strUsername = App.getSetting(this, App.xmlSettingName, "username");
        String strPassword = App.getSetting(this, App.xmlSettingName, "password");
        if (strUsername == null || strPassword == null) {
            edtUsername.setText("");
            edtPassword.setText("");
            chkRememberMe.setChecked(false);
        } else {
            edtUsername.setText(strUsername);
            edtPassword.setText(strPassword);
            chkRememberMe.setChecked(true);
        }
    }


}
