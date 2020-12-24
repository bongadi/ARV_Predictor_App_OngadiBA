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

public class SingleInhibitor extends RecyclerView.Adapter<SingleInhibitor.ViewHolder> {

    List<String> values;
    Context context;

    public SingleInhibitor(List<String> values, Context context) {
        this.values = values;
        this.context = context;
    }

    @NonNull
    @Override
    public SingleInhibitor.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_inhibitor,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SingleInhibitor.ViewHolder holder, int position) {

        try {

            String myString = values.get(position);

            holder.textView.setText(myString.split("!")[0]);
            holder.textView_mid.setText(myString.split("!")[1]);
            holder.textView_end.setText(myString.split("!")[2]);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return values.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textView,textView_mid, textView_end;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.single_inhibitor);
            textView_mid = itemView.findViewById(R.id.single_inhibitor_mid);
            textView_end = itemView.findViewById(R.id.single_inhibitor_end);

        }
    }
}
