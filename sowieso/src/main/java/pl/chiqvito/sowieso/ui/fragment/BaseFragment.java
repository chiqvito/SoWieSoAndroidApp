package pl.chiqvito.sowieso.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import de.greenrobot.event.EventBus;
import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.bus.events.SwitchFragmentEvent;
import pl.chiqvito.sowieso.ui.activity.MainActivityCallback;

public class BaseFragment extends Fragment implements FragmentWrapper {

    private static final String TAG = BaseFragment.class.getName();

    private NavigationDrawerFragment.NavigationDrawerCallbacks mCallbacks;

    protected FragmentBuilder.FragmentName fragmentName() {
        return FragmentBuilder.FragmentName.valueOf(getArguments().getString(Constants.FRAGMENT_NAME));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivityCallback) activity).onSectionAttached(fragmentName());
        getActivity().supportInvalidateOptionsMenu();
        try {
            mCallbacks = (NavigationDrawerFragment.NavigationDrawerCallbacks) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException("Activity must implement NavigationDrawerCallbacks.");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mCallbacks = null;
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public void refresh() {
        Log.v(TAG, "refresh");
    }

    @Override
    public void save() {
        Log.v(TAG, "save");
    }

    public void onEventMainThread(SwitchFragmentEvent event) {
        Log.v(TAG, "event:" + event);
        Fragment fragment = new FragmentBuilder(event.getFragmentName()).parcelable(event.getParcelable()).build();
        mCallbacks.onNavigationDrawerItemSelected(fragment);
    }
}
