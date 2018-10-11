package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import Interfaces.OnDataReceive;
import Interfaces.QuestionsRecv;

/**
 * Created by Pars on 5/14/2016.
 */
public class MainQuestion extends Activity {
    private ArrayList<MainQuestionData> info;
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private int index = 0;
    private RecyclerViewAdapter adapter;
    private RightMenu rightMenu;

    private QuestionsRecv questionsRecv = QuestionsRecv.ALL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mainquestion);
        questionsRecv = getIntent().getIntExtra("questionsRecv", 0) == 0 ? QuestionsRecv.ALL : QuestionsRecv.MINE;
        rightMenu = new RightMenu(this);
        info = new ArrayList<MainQuestionData>();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerviewquestions);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == info.size() - 1) {
////                    index += 10;
////                    load(index);
//                }
//            }
//        });
        adapter = new RecyclerViewAdapter(info);
        recyclerView.setAdapter(adapter);
        load(index);
    }

    private void createItems(String data) {
        String[] splitedData1 = data.split(App.spliter2);
        for (int i = 0; i < splitedData1.length; i++) {
            String[] splitedData2 = splitedData1[i].split(App.spliter1);
            MainQuestionData mainQuestionData = new MainQuestionData();
            try {
                mainQuestionData.id = Integer.valueOf(splitedData2[0]);
                mainQuestionData.txtTitle = splitedData2[1].toString().replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER);
                mainQuestionData.txtBody = splitedData2[2].toString().replaceAll(App.DOTE_CHARACTER, App.READ_DOTE_CHARACTER);

                info.add(mainQuestionData);
            } catch (Exception e) {
            }
        }
        adapter.notifyItemInserted(info.size() - 1);
    }

    private void load(int index) {
        if (questionsRecv == QuestionsRecv.ALL) {
            String[] values = {String.valueOf(index)};
            new RestWebService(this, new OnDataReceive() {
                @Override
                public void Received(String data) {
                    if (data != "null" && data != "false" && data != "true") {
                        createItems(data);
                    }
                }
            }, App.keysGetQuestions, values, App.urlquestions);
        }
        else if(questionsRecv == QuestionsRecv.MINE)
        {
            String[] values = {String.valueOf(App.ID)};
            new RestWebService(this, new OnDataReceive() {
                @Override
                public void Received(String data) {
                    if (data != "null" && data != "false" && data != "true") {
                        createItems(data);
                    }
                }
            }, App.keysGetMyQuestions, values, App.urlgetmyquestions);
        }
    }

    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.MyViewHolder> {

        private Context _context;
        private ArrayList<MainQuestionData> _data;

        public RecyclerViewAdapter(ArrayList<MainQuestionData> data) {
            this._context = App.getContext();
            _data = data;
        }

        private View view;

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(_context);
            view = layoutInflater.inflate(R.layout.mainquestionitem, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        public void onBindViewHolder(MyViewHolder holder, final int position) {
            final MainQuestionData m = _data.get(position);
            holder.txtTitle.setText(m.txtTitle);
            holder.txtBody.setText(m.txtBody);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(MainQuestion.this, Question.class);
                    i.putExtra("id", m.id);
                    startActivity(i);
                }
            });
        }

        @Override
        public int getItemCount() {
            return _data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView txtTitle;
            TextView txtBody;

            public MyViewHolder(View itemView) {
                super(itemView);
                txtTitle = (TextView) itemView.findViewById(R.id.txttitle);
                txtBody = (TextView) itemView.findViewById(R.id.txtbody);
            }
        }
    }

    class MainQuestionData {
        public int id;
        public String txtTitle;
        public String txtBody;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu: {
                rightMenu.toggleDrawer();
            }
        }
        return true;
    }
}
