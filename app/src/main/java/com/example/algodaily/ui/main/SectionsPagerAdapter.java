package com.example.algodaily.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.algodaily.AlgoDatabaseHelper;
import com.example.algodaily.R;

import org.jetbrains.annotations.NotNull;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2};
    private final Context mContext;
    private final AlgoDatabaseHelper dbHelper;
    public SectionsPagerAdapter(Context context, FragmentManager fm, AlgoDatabaseHelper algoDatabaseHelper)
    {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mContext = context;
        dbHelper = algoDatabaseHelper;
    }

    @NotNull
    @Override
    public Fragment getItem(int position)
    {
        if(position == 1)
        {
            return AlgoListFragment.newInstance(dbHelper);
        }
        else
        {
            return new DayFragment(dbHelper);
        }
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}