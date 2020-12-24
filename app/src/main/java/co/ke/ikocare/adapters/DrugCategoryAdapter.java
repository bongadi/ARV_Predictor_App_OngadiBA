package co.ke.ikocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.models.Drug.Drug;
import co.ke.ikocare.models.Drug.DrugCategory;
import co.ke.ikocare.models.Faq;

public class DrugCategoryAdapter extends RecyclerView.Adapter<DrugCategoryAdapter.ViewHolder> {

    private List<DrugCategory> data;
    private Context context;
    private DrugAdapter drugAdapter;

    public DrugCategoryAdapter(List<DrugCategory> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public DrugCategoryAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_display_category, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DrugCategoryAdapter.ViewHolder holder, int position) {
        try{
            holder.categoryName.setText(data.get(position).getName());
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            drugAdapter = new DrugAdapter(data.get(position).getDrugs(),context);
            holder.recyclerView.setAdapter(drugAdapter);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView categoryName;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryName = itemView.findViewById(R.id.category_name);
            recyclerView = itemView.findViewById(R.id.drug_display_recycler);
        }
    }
}
