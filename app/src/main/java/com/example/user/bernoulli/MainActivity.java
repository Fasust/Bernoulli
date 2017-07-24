package com.example.user.bernoulli;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import org.w3c.dom.Text;
import java.lang.Math;

public class MainActivity extends AppCompatActivity {

    private EditText iterationsTxt;
    private EditText probabilityTxt;
    private EditText eventCountTxt;

    private TextView description;

    private TextView resultTxt;
    private Button calcBtn;
    private Switch bisZuSwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        description =(TextView) findViewById(R.id.description);
        resultTxt = (TextView) findViewById(R.id.Result);
        bisZuSwt = (Switch) findViewById(R.id.switch1);
        calcBtn = (Button) findViewById(R.id.calcBtn);

        calcBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateInputs();

                double n = 0;
                double p = 0;
                double k = 0;

                double res = 0;

                try {
                    n = Integer.parseInt(iterationsTxt.getText().toString());
                    p = (double) (Integer.parseInt(probabilityTxt.getText().toString())) / 100;
                    k = Integer.parseInt(eventCountTxt.getText().toString());

                    if(bisZuSwt.isChecked()){
                        res = calcBernoulliUpTo(n ,k, p);
                    }else {
                        res = calcBernoulli(n, k, p);
                    }

                    resultTxt.setText(String.format("%.2f", res * 100) + " %");
                }catch (Exception e){
                    resultTxt.setText("ERROR");
                }
            }
        });

        bisZuSwt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bisZuSwt.isChecked()){
                    description.setText(R.string.bisZuKmal);
                }else{
                    description.setText(R.string.genauKmal);
                }

            }
        });
    }
    private double calcBernoulli(double n, double k, double p){
        return binomial((int)n,(int)k) * (Math.pow(p,k)) * (Math.pow((1-p),(n-k)));
    }
    private double calcBernoulliUpTo(double n, double k, double p){
        double res = 0;
        for(int i = 1; i<= k; i++){
            res += calcBernoulli(n,i,p);
        }
        return res;
    }
    private void updateInputs(){
        eventCountTxt = (EditText) findViewById(R.id.EintrittInput);
        probabilityTxt = (EditText) findViewById(R.id.WahrscheinlichkeitInput);
        iterationsTxt = (EditText) findViewById(R.id.VersuchInput);
    }
    private long binomial(int n, int k)
    {
        if (k>n-k){
            k=n-k;
        }

        long b=1;

        for (int i=1, m=n; i<=k; i++, m--){
            b=b*m/i;
        }

        return b;
    }
}
