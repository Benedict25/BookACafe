package com.example.bookacafe.view.adminTransaction;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;


public class AdminTransactionAdapter extends FragmentPagerAdapter {
    private Context myContext;
    int totalTabs;
    public AdminTransactionAdapter(Context c, FragmentManager fm, int totalTabs) {
        super(fm);
        myContext = c;
        this.totalTabs = totalTabs;
    }
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new SeatFragment();
            case 1:
                return new FoodFragment();
            case 2:
                return new BeverageFragment();
            case 3:
                return new BookFragment();
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 4;
    }

    @Override
    public CharSequence getPageTitle(int position)
    {
        String title = null;
        if (position == 0)
            title = "Seat";
        else if (position == 1)
            title = "Food";
        else if (position == 2)
            title = "Beverage";
        else if (position == 3)
            title = "Book";

        return title;
    }
}