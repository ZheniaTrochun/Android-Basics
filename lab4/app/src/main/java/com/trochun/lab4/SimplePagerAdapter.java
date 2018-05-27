package com.trochun.lab4;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimplePagerAdapter extends FragmentStatePagerAdapter {
    private List<Page> pages;

    public SimplePagerAdapter(FragmentManager fm, Page... pages) {
        super(fm);
        this.pages = Arrays.asList(pages);
    }

    public SimplePagerAdapter(FragmentManager fm, Fragment... fragments) {
        super(fm);
        this.pages = new ArrayList<>(fragments.length);
        for (Fragment fragment: fragments) {
            this.pages.add(new SimplePage(fragment));
        }
    }

    @Override
    public Fragment getItem(int position) {
        return pages.get(position).getFragment();
    }

    @Override
    public int getCount() {
        return pages.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return pages.get(position).getTitle();
    }

    public interface Page {

        Fragment getFragment();
        String getTitle();
    }

    public static class SimplePage implements Page {

        private Fragment fragment;
        private String title;

        public SimplePage(Fragment fragment, String title) {
            this.fragment = fragment;
            this.title = title;
        }

        public SimplePage(Fragment fragment) {
            this.fragment = fragment;
        }

        @Override
        public Fragment getFragment() {
            return fragment;
        }

        @Override
        public String getTitle() {
            return title;
        }
    }
}
