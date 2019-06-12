package cn.xygu.accounting;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {
    private static String TAG = "MainActivity";
    private ViewPager viewPager;
    private MainViewPagerAdapter pagerAdapter;
    private TickerView amountText;
    private TextView dateText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Main", "onCreate: ");
        setContentView(R.layout.activity_main);
        GlobalUtil.getInstance().setContext(getApplicationContext());
        getSupportActionBar().setElevation(0);
        amountText = (TickerView) findViewById(R.id.amount_text);
        amountText.setCharacterLists(TickerUtils.provideNumberList());
        dateText = (TextView) findViewById(R.id.date_text);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        pagerAdapter = new MainViewPagerAdapter(getSupportFragmentManager());
        pagerAdapter.notifyDataSetChanged();
        viewPager.addOnPageChangeListener(this);
        viewPager.setAdapter(pagerAdapter);
        viewPager.setCurrentItem(pagerAdapter.getLatsIndex());
        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddRecordActivity.class);
                startActivityForResult(intent, 1);
            }
        });
        updateAmountAndWeekDay(pagerAdapter.getLatsIndex());

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Log.d("ddddddd", "onActivityResult: ");
        pagerAdapter.reload();
updateAmountAndWeekDay(pagerAdapter.getLatsIndex());
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


        updateAmountAndWeekDay(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    public void updateAmountAndWeekDay(int position) {
        String amount = String.valueOf(pagerAdapter.getTotalCost(position));
        amountText.setText(amount);
        String date = pagerAdapter.getDateStr(position);
        dateText.setText(DateUtil.getWeekDay(date));
    }
}
