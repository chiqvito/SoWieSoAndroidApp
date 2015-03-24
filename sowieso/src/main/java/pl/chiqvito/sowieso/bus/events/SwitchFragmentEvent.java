package pl.chiqvito.sowieso.bus.events;

import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;

public class SwitchFragmentEvent {
    private final FragmentBuilder.FragmentName fragmentName;

    public SwitchFragmentEvent(FragmentBuilder.FragmentName fragmentName) {
        this.fragmentName = fragmentName;
    }

    public FragmentBuilder.FragmentName getFragmentName() {
        return fragmentName;
    }

    @Override
    public String toString() {
        return "SwitchFragmentEvent{fragmentName=" + fragmentName + '}';
    }
}
