package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import Interfaces.OnDataReceive;

/**
 * Created by Pars on 5/16/2016.
 */
public class Signup extends Activity {
    private EditText edtUsername;
    private EditText edtPassword;
    private EditText edtRPassword;
    private EditText edtEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ui();
    }

    private void ui() {
        edtUsername = (EditText) findViewById(R.id.edtusername);
        edtPassword = (EditText) findViewById(R.id.edtpassword);
        edtRPassword = (EditText) findViewById(R.id.edtrepeatpassword);
        edtEmail = (EditText) findViewById(R.id.edtemail);
        Button btnSignup = (Button) findViewById(R.id.btnsignup);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean[] ok = new boolean[4];
                ok[0] = validation(edtUsername, getResources().getString(R.string.errorusername));
                ok[1] = validation(edtPassword, getResources().getString(R.string.errorpassword));
                ok[2] = validation(edtRPassword, getResources().getString(R.string.errorrpassword));
                ok[3] = validation(edtEmail, getResources().getString(R.string.erroremail));
                for (boolean b : ok) {
                    if (b == false)
                        return;
                }
                if (edtPassword.getText().toString().trim().equals(edtRPassword.getText().toString().trim())) {
                    signup();
                } else {
                    edtRPassword.setError("پسورد ها با هم مطابقت ندارند");
                }
            }
        });
    }

    private void signup() {
        final String strUsername = edtUsername.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
        final String strPassword = edtPassword.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
        String strEmail = edtEmail.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
        String[] values = {strUsername, strPassword, strEmail, "u"};
        new RestWebService(this, new OnDataReceive() {
            @Override
            public void Received(String data) {
                if (data.equals("ru")) {
                    edtUsername.setError("نام کاربری تکراری است");
                    Toast.makeText(getApplicationContext(), "نام کاربری تکراری است", Toast.LENGTH_LONG).show();
                } else if (data.equals("re")) {
                    edtEmail.setError("ایمیل تکراری است");
                    Toast.makeText(getApplicationContext(), "ایمیل تکراری است", Toast.LENGTH_LONG).show();
                } else if (data.startsWith("true")) {
                    App.setSetting(Signup.this, App.xmlSettingName, "username", strUsername);
                    App.setSetting(Signup.this, App.xmlSettingName, "password", strPassword);
                    Intent i = new Intent(Signup.this, Signin.class);
                    startActivity(i);
                } else {
                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.failinsenddata), Toast.LENGTH_SHORT).show();
                }
            }
        }, App.keysSignup, values, App.urlsignup);
    }

    private boolean validation(EditText edt, String error) {
        if (edt.getText().toString().trim().length() <= 0) {
            edt.setError(error);
            return false;
        } else {
            return true;
        }
    }

}
