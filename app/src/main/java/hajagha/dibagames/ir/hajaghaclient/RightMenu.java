package hajagha.dibagames.ir.hajaghaclient;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationSet;
import android.view.animation.BounceInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Space;

import java.util.PriorityQueue;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import Interfaces.QuestionsRecv;

/**
 * Created by Pars on 4/28/2016.
 */
public class RightMenu {
    private Context _context;
    private DrawerLayout _drawerlayout;
    private Button[] buttons;
    private String[] data = {"صفحه اصلی", "پرسیدن سوال", "سوالات من", "درباره ما"};

    public RightMenu(Activity a) {
        _context = App.getContext();
        _drawerlayout = (DrawerLayout) a.findViewById(R.id.drawerlayout);
        buttons = new Button[data.length];
        LinearLayout linearLayout = new LinearLayout(_context);
        linearLayout.setGravity(Gravity.CENTER);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        ScrollView scrollView = (ScrollView) a.findViewById(R.id.right_menu_scrollview);
        scrollView.addView(linearLayout);
        int width = App.getDisplay().getWidth();
        int myWidth = width / 5;
        final LinearLayout.LayoutParams buttonsParams = new LinearLayout.LayoutParams(myWidth, myWidth);

        ////
        Space space = new Space(_context);
        space.setLayoutParams(new LinearLayout.LayoutParams(myWidth, myWidth / 2));
        linearLayout.addView(space);
        ////
        Space space1 = new Space(_context);
        space1.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, myWidth / 3));
        linearLayout.addView(space1);
        for (int i = 0; i < data.length; i++) {
            Button b = new Button(_context);
            b.setLayoutParams(buttonsParams);
            b.setBackgroundResource(R.drawable.btn00);
            b.setTextColor(Color.BLACK);
            b.setText(data[i]);
            b.setTextSize(13);
            buttons[i] = b;
            final int tmp = i;
            b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch (tmp) {
                        case 0: {
                            Intent i = new Intent(_context, MainQuestion.class);
                            i.putExtra("questionsRecv", QuestionsRecv.ALL.ordinal());
                            ((Activity) _context).finish();
                            _context.startActivity(i);
                            break;
                        }
                        case 1: {
                            Intent i = new Intent(_context, AskQuestion.class);
                            _context.startActivity(i);
                            break;
                        }
                        case 2: {
                            Intent i = new Intent(_context, MainQuestion.class);
                            i.putExtra("questionsRecv", QuestionsRecv.MINE.ordinal());
                            ((Activity) _context).finish();
                            _context.startActivity(i);
                            break;
                        }
                        case 3: {
                            break;
                        }
                    }
                }
            });
            linearLayout.addView(b);
        }
        _drawerlayout.setDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                _drawerlayout.setScrimColor(0x00000000);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                drawerOpened();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                drawerClosed();
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });
        drawerClosed();
    }

    private void drawerOpened() {
        for (int i = 0; i < data.length; i++) {
            Animation.RightMenuAnimationOpen(buttons[i], i * 50);
        }
    }

    private void drawerClosed() {
        for (int i = 0; i < data.length; i++) {
            Animation.RightMenuAnimationClose(buttons[i], 0);
        }
    }

    public void toggleDrawer() {
        if (!_drawerlayout.isDrawerOpen(Gravity.RIGHT))
            _drawerlayout.openDrawer(Gravity.RIGHT);
        else
            _drawerlayout.closeDrawers();
    }
}
