package com.example.evenalone.a300hero.base;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class LibaryActivity extends AppCompatActivity
{


    /**
     * 跳转界面
     * @param c
     * @param flag
     */
    protected void startActivty(Class c,boolean flag)
    {
        if (flag)
        {
            Intent u = new Intent(this,c);
            startActivity(u);
            finish();
        }
        else
        {
            Intent u = new Intent(this,c);
            startActivity(u);
        }

    }

}
