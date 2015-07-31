package pl.chiqvito.sowieso.ui.adapter;

import android.support.v4.app.FragmentManager;

import pl.chiqvito.sowieso.ui.model.CarConsumptionModel;

public class CarConsumptionAdapter extends BaseRecyclerViewAdapter {
    public CarConsumptionAdapter(FragmentManager fm) {
        super(CarConsumptionAdapter.class.getName());
        new CarConsumptionModel(mViewModels, fm);
    }
}
