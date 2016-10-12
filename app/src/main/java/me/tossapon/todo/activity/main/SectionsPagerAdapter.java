package me.tossapon.todo.activity.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;

/**
 * Created by benvo_000 on 12/10/2559.
 */

public class SectionsPagerAdapter extends FragmentPagerAdapter {
    ArrayList<FragmentItem> fragmentsList = new ArrayList<>();

    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentsList.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return fragmentsList.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return fragmentsList.get(position).getTitle();
    }

    public SectionsPagerAdapter AddFragment(Fragment fragment, String title){
        fragmentsList.add(new FragmentItem(fragment, title));
        return this;
    }

    public class FragmentItem{
        private Fragment fragment;
        private String title;

        public FragmentItem(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public Fragment getFragment() {
            return fragment;
        }

        public String getTitle() {
            return title;
        }
    }
}
