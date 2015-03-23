package pl.chiqvito.sowieso.ui.model;

import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import pl.chiqvito.sowieso.R;

public class TitleModel extends BaseModel {

    private String title;

    public TitleModel(String title) {
        this.title = title;
    }

    public TitleModel(SparseArray<BaseModel> viewModels) {
        super(viewModels);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup container) {
        return new ViewHolder(inflate(container, R.layout.row_header_title));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder itemHolder, int position) {
        ((ViewHolder) itemHolder).bindView(title);
    }

    @Override
    public int getViewType() {
        return ModelType.TITLE;
    }

    @Override
    public Parcelable getParcelable() {
        return null;
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView title;

        private ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.title);
        }

        public void bindView(String title) {
            this.title.setText(title);
        }

    }

}
