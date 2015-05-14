package pl.chiqvito.sowieso.ui.model;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;

public class CarConsumptionModel extends BaseModel {

    private InventoryCarConsumptionDTO dto;

    public CarConsumptionModel(InventoryCarConsumptionDTO dto) {
        this.dto = dto;
    }

    public CarConsumptionModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_car_consumption));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(dto);
    }

    @Override
    public int getViewType() {
        return ModelType.CAR_CONSUMPTION;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private TextView txtDate;
        private TextView txtCombustion;

        private ViewHolder(View itemView) {
            super(itemView);
            txtCombustion = (TextView) itemView.findViewById(R.id.txt_car_cons_combustion);
            txtDate = (TextView) itemView.findViewById(R.id.txt_car_cons_date);
            txtName = (TextView) itemView.findViewById(R.id.txt_car_cons_name);
        }

        public void bindView(InventoryCarConsumptionDTO dto) {
            txtName.setText(dto.getCar().getName());
            txtCombustion.setText(dto.getCombustion().toString());
            txtDate.setText(dto.getRefuelDateString());
        }
    }

}
