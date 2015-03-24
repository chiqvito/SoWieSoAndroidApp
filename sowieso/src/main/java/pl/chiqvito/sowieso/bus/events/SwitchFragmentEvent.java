package pl.chiqvito.sowieso.bus.events;

import android.os.Parcelable;

import pl.chiqvito.sowieso.ui.fragment.FragmentBuilder;

public class SwitchFragmentEvent {
    private final FragmentBuilder.FragmentName fragmentName;
    private Parcelable parcelable;

    public SwitchFragmentEvent(FragmentBuilder.FragmentName fragmentName) {
        this(fragmentName, null);
    }

    public SwitchFragmentEvent(FragmentBuilder.FragmentName fragmentName, Parcelable parcelable) {
        this.fragmentName = fragmentName;
        this.parcelable = parcelable;
    }

    public FragmentBuilder.FragmentName getFragmentName() {
        return fragmentName;
    }

    public Parcelable getParcelable() {
        return parcelable;
    }

    @Override
    public String toString() {
        return "SwitchFragmentEvent{fragmentName=" + fragmentName + ", parcelable=" + parcelable + '}';
    }
}
