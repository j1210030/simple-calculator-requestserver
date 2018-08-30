package com.android.accenture.simplecalculator;

import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

    // input values
    EditText EditTextNum1;
    EditText EditTextNum2;

    // selected operator
    Spinner operatorSpinner;

    // button
    Button calcButton;

    // output value
    TextView resultTextview;

    // TAG
    static final String TAG = "MainActivity";

    // Debug Mode
    static final boolean DEBUG = false;

    String objects;
    Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();

        calcButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Calculation processing
                handler = new Handler();

                // Multithread　processing
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // get input values
                        String textNum1 = EditTextNum1.getText().toString();
                        String textNum2 = EditTextNum2.getText().toString();

                        // get selected operator
                        String itemOperator = (String)operatorSpinner.getSelectedItem();

                        // Log check
                        logCheck("textNum1",textNum1);
                        logCheck("textNum2",textNum2);
                        logCheck("itemOperator",itemOperator);

                        // Converte Operater
                        itemOperator = getConvertedOperater(itemOperator);

                        // Carry out HTTP request.
                        HttpRequester httpRequester = new HttpRequester();
                        String result = httpRequester.getRequest(textNum1, textNum2, itemOperator);

                        // Log check
                        logCheck("result",result);

                        // Parse processing
                        JsonParser jsonParser = new JsonParser();
                        JSONObject response = jsonParser.getParsedJSONObject(result);

                        // Convert JSONObject to String
                        objects = jsonParser.getConvertedResponse(response);

                        // Access to UI thread
                        handler.post(new Runnable() {

                            @Override
                            public void run() {
                                // set result
                                resultTextview.setText(objects);

                            }
                        });
                    }
                }).start();
            }

            public String calculation(String num1, String num2, String itemOp) {

                String[] operator = getResources().getStringArray(R.array.items);
                try {

                    // addition
                    if(itemOp.equals(operator[0])){
                        return String.valueOf(Double.parseDouble(num1) + Double.parseDouble(num2));

                    // subtraction
                    } else if(itemOp.equals(operator[1])){
                        return String.valueOf(Double.parseDouble(num1) - Double.parseDouble(num2));

                    //division
                    } else if(itemOp.equals(operator[2])){
                        return String.valueOf(Double.parseDouble(num1) / Double.parseDouble(num2));

                    // multiplication
                    } else if(itemOp.equals(operator[3])){
                        return String.valueOf(Double.parseDouble(num1) * Double.parseDouble(num2));

                    // equal check
                    } else if(itemOp.equals(operator[4])){
                        return equalCheck(num1, num2);

                    } else {
                        return setErrorMessage();

                    }

                } catch (NumberFormatException e) {

                    return setErrorMessage();

                }

            }

            public  String equalCheck(String num1, String num2) {

                // num1 = num2
                if(num1.equals(num2)){
                    return num1 + " is equal to " + num2;

                 // num1 > num2
                }else if(Double.parseDouble(num1) > Double.parseDouble(num2)){
                    return num1 + " is greater than " + num2;

                 // num1 < num2
                }else if(Double.parseDouble(num1) < Double.parseDouble(num2)){
                    return  num1 + " is less than " + num2;

                } else {
                    return  setErrorMessage();

                }
            }

            public String getConvertedOperater(String itemOp) {

                String[] operator = getResources().getStringArray(R.array.items);
                try {

                    // addition
                    if(itemOp.equals(operator[0])){
                        return  "add";

                        // subtraction
                    } else if(itemOp.equals(operator[1])){
                        return  "sub";

                        //division
                    } else if(itemOp.equals(operator[2])){
                        return  "div";

                        // multiplication
                    } else if(itemOp.equals(operator[3])){
                        return  "mult";

                        // equal check
                    } else if(itemOp.equals(operator[4])){
                        return  "comp";

                    } else {
                        return setErrorMessage();

                    }

                } catch (NumberFormatException e) {

                    return setErrorMessage();

                }

            }

            public String setErrorMessage(){

                return  "Please enter a correct value.";
            }

            public void logCheck(String comment, String value){

                if (DEBUG) {
                    Log.d(TAG,comment + ": " +value);

                }
            }

        });

    }

    public void initView(){
        // input values
        EditTextNum1 = (EditText) findViewById(R.id.editText_num1);
        EditTextNum2 = (EditText) findViewById(R.id.editText_num2);

        // selected operator
        operatorSpinner = (Spinner) findViewById(R.id.operator_spinner);

        // button
        calcButton = (Button)findViewById(R.id.calc_button);

        // output value
        resultTextview = (TextView)findViewById(R.id.result_textview);

    }

}
