package com.monse.andrea.proyectofinal.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.monse.andrea.proyectofinal.fragment.ConductorFragment;
import com.monse.andrea.proyectofinal.fragment.PedirFragment;

public class TabsAdapter extends FragmentStatePagerAdapter
{
    private int numberOfTab;

    public TabsAdapter(FragmentManager fm, int tabs)
    {
        super(fm);
        this.numberOfTab = tabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                return new PedirFragment();
            case 1:
                return new ConductorFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numberOfTab;
    }
}
