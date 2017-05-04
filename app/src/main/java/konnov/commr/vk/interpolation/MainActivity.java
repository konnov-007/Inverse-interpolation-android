package konnov.commr.vk.interpolation;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText a;
    private EditText b;
    private EditText c;
    private TextView outputTextView;
    String outputString = "";
    private CheckBox checkBox;

    double x0;
    double x1;
    double x2;
    double y0;
    double y1;
    double y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a = (EditText) findViewById(R.id.editTextX2);
        b = (EditText) findViewById(R.id.editTextX);
        c = (EditText) findViewById(R.id.editText);
        checkBox = (CheckBox) findViewById(R.id.checkBox);
        outputTextView = (TextView) findViewById(R.id.outputTextView);
    }

    private double equation(double a, double b, double c){
        double disc = b*b-4*a*c;
        if(disc < 0){
            Toast.makeText(this, "Disc equals " + disc + ", which is less than zero", Toast.LENGTH_SHORT).show();
            return -999;
        }
        double x1 = (-b+Math.sqrt(disc))/(2*a);
        double x2 = (-b-Math.sqrt(disc))/(2*a);
        outputString = "Discriminant = " + String.valueOf(disc) + "\nx1 = " + String.valueOf(x1) + "\nx2 = " + String.valueOf(x2);
        return x1<x2?x1:x2;
    }


    private double obratnayaInterpolation(double y, double x0, double x1, double x2, double y0, double y1, double y2){
        return x0*(((y-y1)*(y-y2))/((y0-y1)*(y0-y2))) +
                x1*(((y-y0)*(y-y2))/((y1-y0)*(y1-y2))) +
                x2*(((y-y0)*(y-y1))/((y2-y0)*(y2-y1)));
    }

    public void buttonClicked(View v){
        if(a.getText().toString().equalsIgnoreCase("") || b.getText().toString().equalsIgnoreCase("") || c.getText().toString().equalsIgnoreCase(""))
            return;

        double aa = Double.parseDouble(a.getText().toString());
        double bb = Double.parseDouble(b.getText().toString());
        double cc = Double.parseDouble(c.getText().toString());
        double x =  equation(aa, bb, cc);
        if(x == -999) {
            return;
        }
            if(checkBox.isChecked()){
                x1 = Math.floor(x);
                x0 = x1 - 1;
                x2 = x1 + 1;
                y0 = aa * x0 * x0 + bb * x0;
                y1 = aa * x1 * x1 + bb * x1;
                y2 = aa * x2 * x2 + bb * x2;
            }
            else {
                x1 = Math.floor(x) + 1;
                x0 = x1 - 1;
                x2 = x1 + 1;
                y0 = aa * x0 * x0 + bb * x0;
                y1 = aa * x1 * x1 + bb * x1;
                y2 = aa * x2 * x2 + bb * x2;
            }
            outputString = outputString + "\nx0 = " + String.valueOf(x0) + " y0 = " + String.valueOf(y0) +
                    "\nx1 = " + String.valueOf(x1) + " y1 = " + String.valueOf(y1) +
                    "\nx2 = " + String.valueOf(x2) + " y2 = " + String.valueOf(y2);
            double result = obratnayaInterpolation(cc*(-1), x0, x1, x2, y0, y1, y2);
            outputString = outputString + "\n\n g(" + cc*(-1) + ") = " + result;
            outputTextView.setText(outputString);


    }
}
