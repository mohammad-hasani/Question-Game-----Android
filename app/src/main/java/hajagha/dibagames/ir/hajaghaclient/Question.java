package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import Interfaces.OnDataReceive;

/**
 * Created by Pars on 5/21/2016.
 */
public class Question extends Activity {
    private ArrayList<String> info;
    private ListviewAdapter adapter;
    private int id = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question);
        id = getIntent().getIntExtra("id", 0);
        ListView listView = (ListView) findViewById(R.id.listveiw);
        info = new ArrayList<String>();
        adapter = new ListviewAdapter(this, info);
        listView.setAdapter(adapter);
        load(id);
    }

    private void load(final int id) {
        String[] values = {String.valueOf(id)};
        new RestWebService(this, new OnDataReceive() {
            @Override
            public void Received(String data) {
                String[] splitedData1 = data.split(App.spliter2);
                for (int i = 0; i < 1; i++) {
                    String[] splitedData2 = splitedData1[i].split(App.spliter1);
                    info.add(getResources().getString(R.string.subject));
                    info.add(splitedData2[1].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER));
                    info.add(getResources().getString(R.string.body));
                    info.add(splitedData2[2].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER));
                    info.add(getResources().getString(R.string.answer));
                    if (splitedData2[3].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER).startsWith("None"))
                        info.add("");
                    else
                        info.add(splitedData2[3].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER));
                }
                info.add(getResources().getString(R.string.comments));
                String[] values2 = {String.valueOf(id)};
                new RestWebService(Question.this, new OnDataReceive() {
                    @Override
                    public void Received(String data) {
                        String[] splitedStrings1 = data.split(App.spliter2);
                        for (int i = 0; i < splitedStrings1.length; i++) {
                            String[] splitedStrings2 = splitedStrings1[i].split(App.spliter1);
                            if (splitedStrings2.length > 1)
                                info.add(splitedStrings2[0].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER) + App.spliter1 + splitedStrings2[1].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER));
                            else
                                info.add(splitedStrings2[0].replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER));
                        }
                        adapter.notifyDataSetChanged();
                    }
                }, App.keysGetComment, values2, App.urlgetcomment);
            }
        }, App.keysGetOneQuestion, values, App.urlquestion);
    }


    private class ListviewAdapter extends BaseAdapter {
        private Context context;
        private ArrayList<String> data;
        private LayoutInflater inflater;

        public ListviewAdapter(Context context, ArrayList<String> data) {
            this.context = context;
            this.data = data;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (position <= 6) {
                LinearLayout linearLayout = new LinearLayout(context);
                TextView txt = new TextView(context);
                txt.setTextColor(getResources().getColor(R.color.black));
                linearLayout.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txt.setText(data.get(position));
                txt.setGravity(Gravity.RIGHT);
                linearLayout.addView(txt);
                if (position % 2 == 0) {
                    txt.setGravity(Gravity.CENTER);
                } else {
                    linearLayout.setBackgroundResource(R.drawable.frame_main);
                }
                return linearLayout;
            } else {
                String[] info = data.get(position).split(App.spliter1);
                LinearLayout linearLayout = new LinearLayout(context);
                linearLayout.setOrientation(LinearLayout.VERTICAL);
                TextView txt = new TextView(context);
                txt.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                txt.setTextColor(getResources().getColor(R.color.black));
                linearLayout.setLayoutParams(new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                TextView txt2 = new TextView(context);
                txt2.setTextColor(getResources().getColor(R.color.black));
                txt2.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                linearLayout.addView(txt);
                linearLayout.addView(txt2);
                if (info.length > 0) {
                    txt2.setText(info[0]);
                }
                if (info.length > 1) {
                    txt.setText(info[1]);
                }
                txt.setGravity(Gravity.RIGHT);
                txt2.setGravity(Gravity.RIGHT);
                txt.setTextSize(16);
                txt2.setTextSize(14);
                return linearLayout;
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.question, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.comment: {
                AlertDialog.Builder alert = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = LayoutInflater.from(this);
                View view = layoutInflater.inflate(R.layout.commentalert, null);
                alert.setView(view);
                final EditText edt = (EditText) view.findViewById(R.id.edtcomment);
                alert.setPositiveButton(getResources().getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String userid = String.valueOf(App.ID);
                        String questionid = String.valueOf(id);
                        String comment = edt.getText().toString().trim().replaceAll(App.READ_DOTE_CHARACTER, App.DOTE_CHARACTER);
                        String[] values = {userid, questionid, comment};
                        new RestWebService(Question.this, new OnDataReceive() {
                            @Override
                            public void Received(String data) {
                                if (data.startsWith("true")) {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.datasend), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), getResources().getString(R.string.failinsenddata), Toast.LENGTH_SHORT).show();
                                }
                            }
                        }, App.keysSetComment, values, App.urlsetcomment);
                    }
                });
                alert.setNegativeButton(getResources().getString(R.string.cancel), null);
                alert.show();
            }
        }
        return true;
    }
}
