package com.example.temperature_convertor;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.temperature_convertor.databinding.ActivityMainBinding;
import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    private Spinner spinnerFrom;
    private Spinner spinnerTo;
    private EditText inputTemperature;
    private TextView resultTextView;


    private Expression expression;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        spinnerFrom = findViewById(R.id.spinner01);
        spinnerTo = findViewById(R.id.spinner02);
        inputTemperature = findViewById(R.id.solution_tv);
        resultTextView = findViewById(R.id.result_tv);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.temperature_units, R.layout.spinner_text);
        adapter.setDropDownViewResource(R.layout.spinner_down);

        spinnerFrom.setAdapter(adapter);
        spinnerTo.setAdapter(adapter);

        spinnerFrom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });

        spinnerTo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                convertTemperature();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // Do nothing
            }
        });
    }

    private void convertTemperature() {
        // Get the selected units from the spinners
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();

        // Get the temperature value from the input field
        String temperatureStr = inputTemperature.getText().toString();
        String a = null;
        if (temperatureStr.isEmpty()) {
            // Handle the case where the input field is empty
            resultTextView.setText("");
            return;

        }

        try {
            double temperature = Double.parseDouble(temperatureStr);
            double convertedTemperature;

            // Perform temperature conversion based on the selected units
            if (fromUnit.equals("Fahrenheit") && toUnit.equals("Celsius")) {
                // Convert Fahrenheit to Celsius: (F - 32) * 5/9
                convertedTemperature = (temperature - 32) * 5 / 9;
                a="℃";
            } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Kelvin")) {
                // Convert Fahrenheit to Kelvin: (F - 32) * 5/9 + 273.15
                convertedTemperature = (temperature - 32) * 5 / 9 + 273.15;
                a="K";
            } else if (fromUnit.equals("Celsius") && toUnit.equals("Fahrenheit")) {
                // Convert Celsius to Fahrenheit: (C * 9/5) + 32
                convertedTemperature = (temperature * 9 / 5) + 32;
                a="℉";
            } else if (fromUnit.equals("Celsius") && toUnit.equals("Kelvin")) {
                // Convert Celsius to Kelvin: C + 273.15
                convertedTemperature = temperature + 273.15;
                a="K";
            } else if (fromUnit.equals("Kelvin") && toUnit.equals("Fahrenheit")) {
                // Convert Kelvin to Fahrenheit: (K - 273.15) * 9/5 + 32
                convertedTemperature = (temperature - 273.15) * 9 / 5 + 32;
                a="℉";
            } else if (fromUnit.equals("Kelvin") && toUnit.equals("Celsius")) {
                // Convert Kelvin to Celsius: K - 273.15
                convertedTemperature = temperature - 273.15;
                a="℃";
            } else if (fromUnit.equals("Fahrenheit") && toUnit.equals("Fahrenheit")) {
                convertedTemperature = temperature;
                a = "℉";
            }else if (fromUnit.equals("Celsius") && toUnit.equals("Celsius")) {
                // Convert Kelvin to Celsius: K - 273.15
                convertedTemperature = temperature;
                a="℃";
            }else if (fromUnit.equals("Kelvin") && toUnit.equals("Kelvin")) {
                // Convert Kelvin to Celsius: K - 273.15
                convertedTemperature = temperature;
                a="K";
            }
            else{
                convertedTemperature = temperature;
            }

            // Update the resultTextView with the converted temperature
            resultTextView.setText(convertedTemperature + " " + a);
        } catch (NumberFormatException e) {
            // Handle the case where the input is not a valid number
            resultTextView.setText("Result: Invalid Input");
        }
    }

    public void onDigitClick(View view) {
        Button button = (Button) view;
        String buttonText = button.getText().toString();
        String currentText = inputTemperature.getText().toString();

        if (currentText.equals("Result: Invalid Input")) {
            currentText = "";
        }

        if (!buttonText.equals(".") || (buttonText.equals(".") && !currentText.contains("."))) {
            currentText += buttonText;
            inputTemperature.setText(currentText);
            convertTemperature();
        }
    }

    public void onOperatorClick(View view) {
        String currentText = inputTemperature.getText().toString();
        if (!currentText.isEmpty()) {
            char lastChar = currentText.charAt(currentText.length() - 1);
            if (Character.isDigit(lastChar) || lastChar == '.') {
                String operator = ((Button) view).getText().toString();
                inputTemperature.setText(currentText + operator);
            }
        }
    }

    public void onBackClick(View view) {
        String currentText = inputTemperature.getText().toString();
        if (!currentText.isEmpty()) {
            currentText = currentText.substring(0, currentText.length() - 1);
            inputTemperature.setText(currentText);
            convertTemperature();
        }
    }

    public void onClearClick(View view) {
        inputTemperature.setText("");
        resultTextView.setText("");
    }
}