package co.ke.ikocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.ke.ikocare.R;

public class MutationValueAdapter extends RecyclerView.Adapter<MutationValueAdapter.ViewHolder> {

    List<String> values;
    Context context;

    public MutationValueAdapter(List<String> values, Context context) {
        this.values = values;
        this.context = context;
    }

    @NonNull
    @Override
    public MutationValueAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mutation_value,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutationValueAdapter.ViewHolder holder, int position) {
        holder.val.setText(values.get(position));
    }

    @Override
    public int getItemCount() {
        return values.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView val;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            val = itemView.findViewById(R.id.tv_mut_val);
        }
    }
}
