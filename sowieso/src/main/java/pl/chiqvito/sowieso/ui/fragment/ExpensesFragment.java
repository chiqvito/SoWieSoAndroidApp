package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.R;

public class ExpensesFragment extends BaseFragment {

    public static ExpensesFragment newInstance(FragmentBuilder.FragmentName fn) {
        ExpensesFragment fragment = new ExpensesFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public ExpensesFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        TextView txt = (TextView) rootView.findViewById(R.id.section_label);
        txt.setText("expense: " + fragmentName());
        return rootView;
    }

}
