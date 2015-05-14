package pl.chiqvito.sowieso.ui.adapter;

import pl.chiqvito.sowieso.ui.model.CarConsumptionModel;

public class CarConsumptionAdapter extends BaseRecyclerViewAdapter {
    public CarConsumptionAdapter() {
        super(CarConsumptionAdapter.class.getName());
        new CarConsumptionModel(mViewModels);
    }
}
