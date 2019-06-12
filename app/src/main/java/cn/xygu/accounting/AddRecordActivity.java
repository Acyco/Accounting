package cn.xygu.accounting;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddRecordActivity extends AppCompatActivity implements View.OnClickListener, CategoryRecyclerAdapter.OnCategoryClickListener {
    public static String TAG = "AddRecordActivity";
    private String userInput = "";
    private TextView amountText;
    private EditText editText;

    private RecyclerView recyclerView;
    private CategoryRecyclerAdapter recyclerAdapter;

    private String category = "General";
    private RecordBean.RecordType type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
    private String remark = category;
    RecordBean record = new RecordBean();
    private boolean inEdit = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setElevation(0);
        setContentView(R.layout.activity_add_record);
        findViewById(R.id.keyboard_one).setOnClickListener(this);
        findViewById(R.id.keyboard_two).setOnClickListener(this);
        findViewById(R.id.keyboard_three).setOnClickListener(this);
        findViewById(R.id.keyboard_four).setOnClickListener(this);
        findViewById(R.id.keyboard_five).setOnClickListener(this);
        findViewById(R.id.keyboard_six).setOnClickListener(this);
        findViewById(R.id.keyboard_serven).setOnClickListener(this);
        findViewById(R.id.keyboard_eight).setOnClickListener(this);
        findViewById(R.id.keyboard_nine).setOnClickListener(this);
        findViewById(R.id.keyboard_zero).setOnClickListener(this);

        amountText = (TextView) findViewById(R.id.textView_amount_m);
        editText = (EditText) findViewById(R.id.editText);
        editText.setText(remark);

        handleDot();
        handleTypeChange();
        handleBackspace();
        handleDone();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerAdapter = new CategoryRecyclerAdapter(getApplicationContext());
        recyclerView.setAdapter(recyclerAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 4);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerAdapter.notifyDataSetChanged();
        recyclerAdapter.setOnCategoryClickListener(this);


        RecordBean record = (RecordBean) getIntent().getSerializableExtra("record");
        System.out.println(record);
        if(record !=null){
         inEdit = true;
            this.record = record;
        }
    }

    private void handleDot() {
        findViewById(R.id.keyboard_dot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!userInput.contains(".")) {
                    userInput += ".";
                }
                updateAmountText();
            }
        });
    }

    private void handleTypeChange() {
        findViewById(R.id.keyboard_type).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                    type = RecordBean.RecordType.RECORD_TYPE_INCOME;
                } else {
                    type = RecordBean.RecordType.RECORD_TYPE_EXPENSE;
                }

                recyclerAdapter.changeType(type);
                category = recyclerAdapter.getSelected();

            }

        });
    }

    private void handleBackspace() {
        findViewById(R.id.keyboard_backspace).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userInput.length() > 0) {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                if (userInput.length() > 0 && userInput.charAt(userInput.length() - 1) == '.') {
                    userInput = userInput.substring(0, userInput.length() - 1);
                }
                updateAmountText();
            }

        });
    }

    private void handleDone() {
        findViewById(R.id.keyboard_done).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userInput.equals("")) {
                    double amount = Double.valueOf(userInput);
                    record.setAmount(amount);
                    if (type == RecordBean.RecordType.RECORD_TYPE_EXPENSE) {
                        record.setType(1);
                    } else {
                        record.setType(2);
                    }
                    record.setCategory(recyclerAdapter.getSelected());
                    record.setRemark(editText.getText().toString());
                    if(inEdit){
                        GlobalUtil.getInstance().databaseHelper.editRecord(record.getUuid(),record);
                    }else {
                        GlobalUtil.getInstance().databaseHelper.addRecord(record);
                    }
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "Amount is 0!!!!", Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    @Override
    public void onClick(View view) {
        Button button = (Button) view;
        String input = button.getText().toString();
        if (userInput.contains(".")) {
            // 11. 11.1 11.11
            if (userInput.split("\\.").length == 1 || userInput.split("\\.")[1].length() < 2) {
                userInput += input;
            }
        } else {
            userInput += input;
        }
        updateAmountText();
    }


    public void updateAmountText() {
        if (userInput.contains(".")) {
            if (userInput.split("\\.").length == 1) {
                amountText.setText(userInput + "00");
            } else if (userInput.split("\\.")[1].length() == 1) {
                amountText.setText(userInput + "0");

            } else if (userInput.split("\\.")[1].length() == 2) {
                amountText.setText(userInput);
            }
        } else {
            if (userInput.equals("")) {
                amountText.setText("0.00");
            } else {
                amountText.setText(userInput + ".00");
            }
        }
    }

    @Override
    public void onClick(String category) {
        this.category = category;
        editText.setText(category);

    }
}
