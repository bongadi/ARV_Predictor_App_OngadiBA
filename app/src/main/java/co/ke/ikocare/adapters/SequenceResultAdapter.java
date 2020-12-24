package co.ke.ikocare.adapters;

import android.content.Context;
import android.util.ArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.ke.ikocare.MutationAnalysisRequestQuery;
import co.ke.ikocare.R;
import co.ke.ikocare.SequenceAnalysisRequestQuery;

public class SequenceResultAdapter extends RecyclerView.Adapter<SequenceResultAdapter.ViewHolder> {

    List<SequenceAnalysisRequestQuery.SequenceAnalysi> data;
    Context context;
    SequenceAnalysisRequestQuery.Data nData;

    public SequenceResultAdapter(List<SequenceAnalysisRequestQuery.SequenceAnalysi> data, Context context, SequenceAnalysisRequestQuery.Data nData) {
        this.data = data;
        this.context = context;
        this.nData = nData;
    }

    @NonNull
    @Override
    public SequenceResultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_sequence_result,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SequenceResultAdapter.ViewHolder holder, int position) {
        try {
//            Map<String, List<SequenceAnalysisRequestQuery.AlignedGeneSequence>> dataset = new HashMap<>();
            Toast.makeText(context,String.valueOf(data.size()),Toast.LENGTH_LONG).show();
            for (SequenceAnalysisRequestQuery.SequenceAnalysi mySeq : data){
                String major="",accessory="",other="",comment = "",inhibitorTitle="";
                List<SequenceAnalysisRequestQuery.DrugScore> prohibitors = new ArrayList<>();
                List<String> inhibValues = new ArrayList<>();
                prohibitors = data.get(position).drugResistance().get(0).drugScores();
                for (SequenceAnalysisRequestQuery.AlignedGeneSequence val : mySeq.alignedGeneSequences()){
//                dataset.put()
                    String gene = val.gene().name().toString();
                    holder.drugResInterpretation.setText("Drug resistance interpretation:  "+val.gene().name().toString());
                    holder.dbVersion.setText(nData.currentVersion().text().toString()+" "+nData.currentVersion().publishDate().toString());
                    for (SequenceAnalysisRequestQuery.Mutation mut : val.mutations()){
                        if (mut.primaryType().toString().equals("Major")){
                            for (SequenceAnalysisRequestQuery.Comment comm : mut.comments()){
                                comment = comment + ". "+comm.text().toString();
                            }
                            major = major+","+mut.text().toString();
                        }else if (mut.text().toString().equals("Accessory")){
                            for (SequenceAnalysisRequestQuery.Comment comm : mut.comments()){
                                comment = comment + ". "+comm.text().toString();
                            }
                            accessory = major+","+mut.primaryType().toString();
                        }else {
                            for (SequenceAnalysisRequestQuery.Comment comm : mut.comments()){
                                comment = comment + ". "+comm.text().toString();
                            }
                            other = major+","+mut.text().toString();
                        }
                    }
                    if (major.equals("")){
                        major="None";
                    }else if(major.equals("None")){
                        major="";
                    }

                    if (accessory.equals("")){
                        accessory="None";
                    }else if (accessory.equals("None")){
                        accessory="";
                    }

                    if (other.equals("")){
                        other="None";
                    }else if (other.equals("None")){
                        other="";
                    }

                    if (gene.equals("PR")) {
                        inhibitorTitle = "Protease Inhibitors";
                    } else if (gene.equals("RT")) {
                        inhibitorTitle = "Nucleoside & Non-Nucleoside Reverse Transcriptase Inhibitors";
                    } else {
                        inhibitorTitle = "Integrase Strand Transfer Inhibitors";
                    }
                }

                holder.inhibTitle.setText(inhibitorTitle);

                if (prohibitors.size() != 0) {
                    for (SequenceAnalysisRequestQuery.DrugScore val : prohibitors) {
                        inhibValues.add(val.drug().displayAbbr().toString() + "!" + val.text().toString() + "!" + val.drugClass().name().toString());
                    }
                } else {
                    inhibValues.add("No ! Values ! Found");
                }
                SingleInhibitor singleInhibitor = new SingleInhibitor(inhibValues,context);
                holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
                holder.recyclerView.setAdapter(singleInhibitor);
                holder.piMajorResistanceMutation.setText("PI Major Resistance Mutations: "+major);
                holder.piAccessoryResistanceMutation.setText("PI Accessory Resistance Mutations:  "+accessory);
                holder.otherMutations.setText("Other Mutations:  "+other);
                holder.comment.setText(comment);

            }

//            String gene = data.get(position).gene().name().toString();
//            for (SequenceAnalysisRequestQuery.DrugClass val: data.get(position).gene().drugClasses()){
//                String initName = val.name().toString();
//
//            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView drugResInterpretation, dbVersion,piMajorResistanceMutation,piAccessoryResistanceMutation,otherMutations,mutationScoring,inhibTitle,comment;
        RecyclerView recyclerView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugResInterpretation = itemView.findViewById(R.id.drug_resistance_interpretation_seq);
            dbVersion = itemView.findViewById(R.id.db_version_seq);
            piMajorResistanceMutation = itemView.findViewById(R.id.pi_major_resistance_mutations_seq);
            piAccessoryResistanceMutation = itemView.findViewById(R.id.pi_accessory_resistance_mutations_seq);
            otherMutations = itemView.findViewById(R.id.other_mutations_seq);
            mutationScoring = itemView.findViewById(R.id.sequence_scoring);
            inhibTitle = itemView.findViewById(R.id.inhibitor_title_seq);
            recyclerView = itemView.findViewById(R.id.inhibitors_recycler_seq);
            comment = itemView.findViewById(R.id.sequent_comment);
        }
    }
}
