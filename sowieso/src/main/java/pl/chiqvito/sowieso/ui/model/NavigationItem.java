package pl.chiqvito.sowieso.ui.model;

import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;

public class NavigationItem {

    private FragmentBuilder.FragmentName fragmentName;

    public NavigationItem(FragmentBuilder.FragmentName fragmentName) {
        this.fragmentName = fragmentName;
    }

    public FragmentBuilder.FragmentName getFragmentName() {
        return fragmentName;
    }
}
