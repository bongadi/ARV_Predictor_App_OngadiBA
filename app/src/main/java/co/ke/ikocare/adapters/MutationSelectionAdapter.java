package co.ke.ikocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.models.Drug.Drug;
import co.ke.ikocare.models.mutation.MutationData;
import co.ke.ikocare.utilities.PreferenceManager;

public class MutationSelectionAdapter extends RecyclerView.Adapter<MutationSelectionAdapter.ViewHolder> {


    private List<MutationData> data;
    private Context context;
    PreferenceManager preferenceManager;
    EditText textView;

    public MutationSelectionAdapter(List<MutationData> data, Context context,EditText textView) {
        this.data = data;
        this.context = context;
        this.textView = textView;
    }

    @NonNull
    @Override
    public MutationSelectionAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mutation,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutationSelectionAdapter.ViewHolder holder, int position) {

        try {
            holder.number.setText(String.valueOf(data.get(position).getNumber()));
            ArrayAdapter<CharSequence> adapter = new ArrayAdapter<CharSequence>(context,android.R.layout.simple_spinner_item,data.get(position).getCharacterOptions());
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            holder.spinner.setAdapter(adapter);
            holder.spinner.setSelection(-1);

            holder.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    if (i == 0){

                    }else{
                        String text="";
                        if (String.valueOf(textView.getText()).equals("")){
                            if (data.get(position).getNumber() == 68){
                                text ="S68Deletion ";
                            }else {
                                text =String.valueOf(data.get(position).getInitLetter())+String.valueOf(data.get(position).getNumber()+String.valueOf(data.get(position).getCharacterOptions().get(i)))+" ";
                            }
                        }else {
                            if (data.get(position).getNumber() == 68){
                                text =textView.getText()+" S68Deletion";
                            }else {
                                text =textView.getText()+" "+String.valueOf(data.get(position).getInitLetter())+String.valueOf(data.get(position).getNumber()+String.valueOf(data.get(position).getCharacterOptions().get(i)));
                            }
                        }
                        textView.setText(text);
                    }
//                    Toast.makeText(context,String.valueOf(adapterView.getItemAtPosition(i)),Toast.LENGTH_LONG).show();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

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
        TextView number;
        Spinner spinner;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            preferenceManager= new PreferenceManager(context);
            number = itemView.findViewById(R.id.tv_mut_number);
            spinner = itemView.findViewById(R.id.value_spinner);

        }
    }
}
