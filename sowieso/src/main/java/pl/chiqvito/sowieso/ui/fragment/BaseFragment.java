package pl.chiqvito.sowieso.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.ui.activity.MainActivityCallback;

public class BaseFragment extends Fragment {

    protected FragmentBuilder.FragmentName fragmentName() {
        return FragmentBuilder.FragmentName.valueOf(getArguments().getString(Constants.FRAGMENT_NAME));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        ((MainActivityCallback) activity).onSectionAttached(fragmentName());
    }

}
