package co.ke.ikocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.models.Drug.Drug;
import co.ke.ikocare.utilities.PreferenceManager;

public class DrugAdapter extends RecyclerView.Adapter<DrugAdapter.ViewHolder> {

    private List<Drug> data;
    private Context context;
    PreferenceManager preferenceManager;

    public DrugAdapter(List<Drug> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_display_drug, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        try{
            holder.drug.setText(data.get(position).getName());
            holder.drug.setChecked(data.get(position).getSelected());
            holder.drug.setTag(position);
            holder.drug.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View view) {
                    Integer pos = (Integer) holder.drug.getTag();
                    String name = data.get(pos).getName();
                    if (preferenceManager.getDrugStatus(name)){
                        preferenceManager.setDrugSelected(false,name);
                    }
                    else {
                        preferenceManager.setDrugSelected(true,name);
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox drug;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceManager = new PreferenceManager(context);
            drug = itemView.findViewById(R.id.drug_check);
        }
    }
}
