package com.android.greenfoodchallenge.carboncalculator;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class AboutActivity extends AppCompatActivity {

    private Button mButtonMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_savings);

        mButtonMenu = (Button)findViewById(R.id.button_menu);
        mButtonMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent goToMenu = new Intent(AboutActivity.this, MenuActivity.class);
                startActivity(goToMenu);
            }
        });
    }

    public static Intent makeIntent(Context context){
        Intent intent = new Intent(context, AboutActivity.class);
        return intent;
    }
}
