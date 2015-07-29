package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class CarConsumptionFragment extends BaseFragment {
    public static CarConsumptionFragment newInstance(FragmentBuilder.FragmentName fn) {
        return newInstance(fn, null);
    }

    public static CarConsumptionFragment newInstance(FragmentBuilder.FragmentName fn, InventoryCarConsumptionDTO dto) {
        CarConsumptionFragment fragment = new CarConsumptionFragment();
        Bundle args = new Bundle();
        if (dto != null)
            args.putParcelable("dto", dto);
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    private InventoryCarConsumptionDTO dto() {
        return getArguments().getParcelable("dto");
    }
}
