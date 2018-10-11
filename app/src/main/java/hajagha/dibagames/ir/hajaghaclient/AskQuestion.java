package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import Interfaces.OnDataReceive;

/**
 * Created by Pars on 5/14/2016.
 */
public class AskQuestion extends Activity {
    private EditText edtTitle;
    private EditText edtBody;
    private Button btnSend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.askquestion);
//        new RightMenu(this);
        ui();
    }

    private void ui() {
        edtTitle = (EditText) findViewById(R.id.edttitle);
        edtBody = (EditText) findViewById(R.id.edtbody);
        btnSend = (Button) findViewById(R.id.btnsend);
        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validate()) return;
                String strTitle = edtTitle.getText().toString().trim();
                String strBody = edtBody.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
                String[] values = {String.valueOf(App.ID), strTitle, strBody};
                new RestWebService(AskQuestion.this, new OnDataReceive() {
                    @Override
                    public void Received(String data) {
                        if (!data.equals("false")) {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.datasend), Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(AskQuestion.this, MainQuestion.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), getResources().getString(R.string.failinsenddata), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, App.keysSetQuestion, values, App.urlsetquestion);
            }
        });
    }

    private boolean validate() {
        String strTitle = edtTitle.getText().toString().trim();
        String strBody = edtBody.getText().toString().trim();
        boolean[] ok = new boolean[2];
        if (strTitle.length() == 0) {
            edtTitle.setError(getResources().getString(R.string.enterthisfield));
            ok[0] = false;
        } else {
            ok[0] = true;
        }
        if (strBody.length() == 0) {
            edtBody.setError(getResources().getString(R.string.enterthisfield));
            ok[1] = false;
        } else {
            ok[1] = true;
        }
        for (boolean b : ok) {
            if (b == false)
                return false;
        }
        return true;
    }
}
