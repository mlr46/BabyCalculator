package com.mlr.calculatrice.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.mlr.calculatrice.R;
import com.mlr.calculatrice.models.Operation;

public class MainActivity extends AppCompatActivity {

    private TextView screen;
    private int leftOperand = 0;
    private int rightOperand = 0;
    private Operation operation = null;
    private boolean isLeftOperand = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        screen = findViewById(R.id.screen);
        Button equals = findViewById(R.id.btnEqual);
        equals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                compute();
            }
        });

        Button clear = findViewById(R.id.btnClear);
        clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clear();
            }
        });
    }

    private void compute() {
        if (!isLeftOperand) {
            switch (operation) {
                case PLUS: leftOperand = leftOperand + rightOperand; break;
                case MINUS: leftOperand = leftOperand - rightOperand; break;
                case DIVIDE: leftOperand = leftOperand / rightOperand; break;
                case TIMES: leftOperand = leftOperand * rightOperand; break;
                default:
                    return;
            }
            rightOperand = 0;
            isLeftOperand = true;
            updateDisplay();
        }
    }

    private void clear() {
        leftOperand = 0;
        rightOperand = 0;
        isLeftOperand = true;
        operation = null;
        updateDisplay();
    }

    /**
     * OnClick function for operation buttons.
     */
    public void setOperator(View view) {
        switch (view.getId()) {
            case R.id.btnPlus: operation = Operation.PLUS; break;
            case R.id.btnMinus: operation = Operation.MINUS; break;
            case R.id.btnTimes: operation = Operation.TIMES; break;
            case R.id.btnDivide: operation = Operation.DIVIDE; break;
            default:
                Toast.makeText(this, "Operation not recognized...", Toast.LENGTH_SHORT);
                return;
        }
        isLeftOperand = false;
        updateDisplay();
    }

    /**
     * OnClick function for digit buttons. Since we can have more than one digit,
     * to get the actual value of the number, we multiply the current by 10 and add the value read
     * from the value of the pressed button.
     * @param view
     */
    public void addDigit(View view) {
        try {
            String digitValue = ((Button) view).getText().toString();
            int digit = Integer.parseInt(digitValue);
            if (isLeftOperand) {
                leftOperand = leftOperand * 10 + digit;
            } else {
                rightOperand = rightOperand * 10 + digit;
            }
            updateDisplay();

        } catch (Throwable throwable) {
            Toast.makeText(this, "Wrong value...", Toast.LENGTH_SHORT);
        }
    }

    /**
     * This will show the value of the current operand or that of the result.
     */
    private void updateDisplay() {

        int valueToDisplay = isLeftOperand ? leftOperand : rightOperand;
        screen.setText(String.format("%d", valueToDisplay));
    }
}
