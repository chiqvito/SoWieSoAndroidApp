package pl.chiqvito.sowieso.ui.model;

import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;
import pl.chiqvito.sowieso.rest.dto.InventoryCarConsumptionDTO;
import pl.chiqvito.sowieso.ui.dialog.ManageCarConsumptionDialog;

public class CarConsumptionModel extends BaseModel {

    private InventoryCarConsumptionDTO dto;
    private FragmentManager fm;

    public CarConsumptionModel(InventoryCarConsumptionDTO dto) {
        this.dto = dto;
    }

    public CarConsumptionModel(SparseArray<BaseModel> viewModels, FragmentManager fm) {
        super(viewModels);
        this.fm = fm;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_car_consumption), fm);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(dto);
    }

    @Override
    public int getViewType() {
        return ModelType.CAR_CONSUMPTION.getValue();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final FragmentManager fm;
        private InventoryCarConsumptionDTO dto;

        private TextView txtName;
        private TextView txtDate;
        private TextView txtCombustion;
        private TextView txtDistance;
        private TextView txtPetrolStation;
        private TextView txtPrice;
        private TextView txtRefuelAmount;
        private TextView txtTotalCost;

        private ViewHolder(View itemView, FragmentManager fm) {
            super(itemView);
            this.fm = fm;
            itemView.setOnClickListener(this);
            itemView.setClickable(true);
            txtCombustion = (TextView) itemView.findViewById(R.id.txt_car_cons_combustion);
            txtDate = (TextView) itemView.findViewById(R.id.txt_car_cons_date);
            txtName = (TextView) itemView.findViewById(R.id.txt_car_cons_name);
            txtDistance = (TextView) itemView.findViewById(R.id.txt_car_cons_distance);
            txtPetrolStation = (TextView) itemView.findViewById(R.id.txt_car_cons_petrol_station);
            txtPrice = (TextView) itemView.findViewById(R.id.txt_car_cons_price);
            txtRefuelAmount = (TextView) itemView.findViewById(R.id.txt_car_cons_refuel_amount);
            txtTotalCost = (TextView) itemView.findViewById(R.id.txt_car_cons_total_cost);
        }

        public void bindView(InventoryCarConsumptionDTO dto) {
            this.dto = dto;
            try {
                txtName.setText(dto.getCar().getName());
                txtCombustion.setText(dto.getCombustion().toString() + " l/km");
                txtDate.setText(dto.getRefuelDateString());
                txtDistance.setText(dto.getDistance().toString() + " km");
                txtPetrolStation.setText(dto.getPetrolStation().name());
                txtPrice.setText(dto.getPrice().toString() + " " + dto.getCurrency().name());
                txtRefuelAmount.setText(dto.getRefuelAmount().toString() + " l");
                txtTotalCost.setText(dto.getTotalCost().toString() + " " + dto.getCurrency().name());
            } catch (Exception e) {
                Log.e(CarConsumptionModel.class.getName(), e.getMessage(), e);
            }
        }

        @Override
        public void onClick(View v) {
            ManageCarConsumptionDialog dialog = ManageCarConsumptionDialog.newInstance(dto);
            dialog.show(fm, null);
        }

    }

}
