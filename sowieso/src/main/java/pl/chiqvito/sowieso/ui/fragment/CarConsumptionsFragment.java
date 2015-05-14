package pl.chiqvito.sowieso.ui.fragment;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import pl.chiqvito.sowieso.Constants;
import pl.chiqvito.sowieso.bus.events.CarConsumptionOperationEvent;
import pl.chiqvito.sowieso.bus.events.CarConsumptionsEvent;
import pl.chiqvito.sowieso.bus.events.Event;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;
import pl.chiqvito.sowieso.ui.adapter.BaseRecyclerViewAdapter;
import pl.chiqvito.sowieso.ui.adapter.CarConsumptionAdapter;
import pl.chiqvito.sowieso.ui.model.BaseModel;
import pl.chiqvito.sowieso.ui.model.CarConsumptionModel;

public class CarConsumptionsFragment extends BaseListFragment {

    public static final String TAG = CarConsumptionsFragment.class.getName();

    public static CarConsumptionsFragment newInstance(FragmentBuilder.FragmentName fn) {
        CarConsumptionsFragment fragment = new CarConsumptionsFragment();
        Bundle args = new Bundle();
        args.putString(Constants.FRAGMENT_NAME, fn.name());
        fragment.setArguments(args);
        return fragment;
    }

    public CarConsumptionsFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        load();
    }

    @Override
    protected BaseRecyclerViewAdapter adapter() {
        return new CarConsumptionAdapter();
    }

    @Override
    public void refresh() {
        super.refresh();
        load();
    }

    @Override
    protected void onScrollFetchData() {
        super.onScrollFetchData();
        load();
    }

    private void load() {
        if (canLoadMorePages())
            fetchData(new CarConsumptionOperationEvent(Event.Operation.GET_ALL, getPage()));
    }

    public void onEventMainThread(CarConsumptionsEvent event) {
        Log.v(TAG, "event:" + event);
        List<BaseModel> ms = new ArrayList<BaseModel>();
        List<InventoryCarConsumptionDTO> consumptions = event.getConsumptions();
        for (InventoryCarConsumptionDTO cons : consumptions) {
            ms.add(new CarConsumptionModel(cons));
        }
        load(ms, event.getPage());
    }
}
