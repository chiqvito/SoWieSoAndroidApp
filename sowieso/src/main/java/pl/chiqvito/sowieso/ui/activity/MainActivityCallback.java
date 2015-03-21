package pl.chiqvito.sowieso.ui.activity;

import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;

public interface MainActivityCallback {
    void onSectionAttached(FragmentBuilder.FragmentName fn);
}
