package cn.xygu.accounting;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.Date;
import java.util.LinkedList;

/**
 * Created by Acyco on 2019-06-11.
 */

public class MainViewPagerAdapter extends FragmentPagerAdapter {
    LinkedList<MainFragment> fragments = new LinkedList<>();
    LinkedList<String> dates = new LinkedList<>();
    public MainViewPagerAdapter(FragmentManager fm) {
        super(fm);
        initFragments();
    }

    private void initFragments(){
        if(GlobalUtil.getInstance().databaseHelper == null){
            dates.add(DateUtil.getFormattedDate(System.currentTimeMillis()));
        }else {

            dates = GlobalUtil.getInstance().databaseHelper.getAvaliableDate();
        }
        if(!dates.contains(DateUtil.getFormattedDate())){
            dates.addLast(DateUtil.getFormattedDate());
        }
        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }

    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }


    @Override
    public int getCount() {
        return fragments.size();
    }

    public void reload() {
       /* fragments.clear();
        dates.clear();
        dates = GlobalUtil.getInstance().databaseHelper.getAvaliableDate();
        for (String date:dates){
            MainFragment fragment = new MainFragment(date);
            fragments.add(fragment);
        }
        notifyDataSetChanged();*/
        for (MainFragment fragment:fragments){
            fragment.reload();
        }
    }

    public int getLatsIndex() {
        return fragments.size()-1;
    }

    public String getDateStr(int index){
        return dates.get(index);
    }

    public int getTotalCost(int index){
        return fragments.get(index).getTotalCost();
    }
}
