package co.ke.ikocare.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import co.ke.ikocare.MutationAnalysisRequestQuery;
import co.ke.ikocare.R;
import de.codecrafters.tableview.TableDataAdapter;
import de.codecrafters.tableview.TableHeaderAdapter;
import de.codecrafters.tableview.TableView;
import de.codecrafters.tableview.toolkit.SimpleTableDataAdapter;
import de.codecrafters.tableview.toolkit.SimpleTableHeaderAdapter;

public class MutationResultsAdapter extends RecyclerView.Adapter<MutationResultsAdapter.ViewHolder> {

    List<MutationAnalysisRequestQuery.DrugResistance> data;
    MutationAnalysisRequestQuery.Data nData;
    private Context context;

    public MutationResultsAdapter(List<MutationAnalysisRequestQuery.DrugResistance> data, Context context, MutationAnalysisRequestQuery.Data nData) {
        this.data = data;
        this.context = context;
        this.nData = nData;
    }


    @NonNull
    @Override
    public MutationResultsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_mutation_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MutationResultsAdapter.ViewHolder holder, int position) {

        try {

            String initName = data.get(position).drugScores().get(0).drugClass().name().toString();
            String gene = data.get(position).gene().name().toString();
            String commentByType = "";
            String dbVersion = nData.currentVersion().text().toString() + nData.currentVersion().publishDate().toString();
            String majorMuts = "", accessoryMuts = "", otherMuts = "", inhibitorTitle = "", non_inhibitorTitle = "";
            List<MutationAnalysisRequestQuery.Mutation1> majorR = new ArrayList<>();
            List<MutationAnalysisRequestQuery.Mutation1> accessoryR = new ArrayList<>();
            List<MutationAnalysisRequestQuery.Mutation1> otherR = new ArrayList<>();
            List<MutationAnalysisRequestQuery.DrugScore> prohibitors = new ArrayList<>();
            List<String> inhibValues = new ArrayList<>();
            majorR = data.get(position).mutationsByTypes().get(0).mutations();
            accessoryR = data.get(position).mutationsByTypes().get(1).mutations();
            otherR = data.get(position).mutationsByTypes().get(2).mutations();
            prohibitors = data.get(position).drugScores();

            Map<String, List<MutationAnalysisRequestQuery.DrugScore>> tableHs = new HashMap<>();
            Set<String> drugNames = new HashSet<>();
            Set<Set<String>> headers = new HashSet<>();
            Set<Set<String>> rowData = new HashSet<>();
            Set<String> singHeadersRow = new HashSet<>();
            Set<String> singDataRow = new HashSet<>();

            for (MutationAnalysisRequestQuery.DrugScore val : data.get(position).drugScores()) {
                drugNames.add(val.drugClass().name().toString());
            }

            for (MutationAnalysisRequestQuery.DrugScore val : data.get(position).drugScores()) {
                for (String name : drugNames) {
                    if (val.drugClass().name().toString().equals(name)) {
                        singHeadersRow.add(name);
                        singHeadersRow.add(val.drug().displayAbbr().toString());
                        String drugs = "";
                        String score = "";
                        if (val.partialScores().size() != 0) {
                            for (MutationAnalysisRequestQuery.PartialScore newVal : val.partialScores()) {
                                if (newVal.mutations().size() != 0) {
                                    for (MutationAnalysisRequestQuery.Mutation mut : newVal.mutations()) {
                                        drugs = drugs + mut.text().toString();
                                    }
                                    score = String.valueOf(newVal.score());
                                    singDataRow.add(drugs);
                                    singDataRow.add(score);
                                } else {
                                    singDataRow.add("Total");
                                    singDataRow.add("0");
                                }
                            }
                        }
                    }
                }

                headers.add(singHeadersRow);
                rowData.add(singDataRow);
//
//                holder.tableLayout
            }

//            for (Set<String> val : headers){
//                TableDataAdapter<String[]> myDataAdapter = new SimpleTableDataAdapter(context, (String[][]) val.toArray());
//                TableHeaderAdapter myHeaderAdapter = new SimpleTableHeaderAdapter(context, val.toArray());
//            }


            if (majorR.size() <= 0) {
                majorMuts = "None";
            } else {
                for (MutationAnalysisRequestQuery.Mutation1 val : majorR) {
                    majorMuts = majorMuts + "," + val.text().toString();
                }
            }

            if (accessoryR.size() <= 0) {
                accessoryMuts = "None";
            } else {
                for (MutationAnalysisRequestQuery.Mutation1 val : accessoryR) {
                    accessoryMuts = accessoryMuts + "," + val.text().toString();
                }
            }

            if (otherR.size() <= 0) {
                otherMuts = "None";
            } else {
                for (MutationAnalysisRequestQuery.Mutation1 val : otherR) {
                    otherMuts = otherMuts + "," + val.text().toString();
                }
            }
            if (gene.equals("PR")) {
                inhibitorTitle = "Protease Inhibitors";
            } else if (gene.equals("RT")) {
                inhibitorTitle = "Nucleoside & Non-Nucleoside Reverse Transcriptase Inhibitors";
            } else {
                inhibitorTitle = "Integrase Strand Transfer Inhibitors";
            }
            holder.inhibTitle.setText(inhibitorTitle);

            if (prohibitors.size() != 0) {
                for (MutationAnalysisRequestQuery.DrugScore val : prohibitors) {
                    inhibValues.add(val.drug().displayAbbr().toString() + "!" + val.text().toString() + "!" + val.drugClass().name().toString());
                }
            } else {
                inhibValues.add("No ! Values ! Found");
            }
            for (MutationAnalysisRequestQuery.Comment val : data.get(position).commentsByTypes().get(0).comments()) {
                commentByType = commentByType + val.text().toString();
            }
            holder.comment.setText(commentByType);
            SingleInhibitor singleInhibitor = new SingleInhibitor(inhibValues, context);
            holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
            holder.recyclerView.setAdapter(singleInhibitor);
            holder.drugResInterpretation.setText(String.format("Drug resistance interpretation: %s", gene));
            holder.dbVersion.setText(dbVersion);
            holder.piMajorResistanceMutation.setText(String.format("%s%s%s", initName, " Major Resistance Mutations:", majorMuts));
            holder.piAccessoryResistanceMutation.setText(String.format("%s%s%s", initName, " Accessory Resistance Mutations:", accessoryMuts));
            holder.otherMutations.setText(String.format("%s%s", "Other Mutations:", otherMuts));
            holder.mutationScoring.setText(String.format("Mutation Scoring: %s", gene));
            Toast.makeText(context, String.valueOf(getItemCount()), Toast.LENGTH_LONG).show();

            holder.tableView.setColumnCount(4);
            final String[][] DATA_TO_SHOW = {{"This", "is", "a", "test"},
                    {"and", "a", "second", "test"}};
            final String[] heads = {gene,"Header1", "Header2", "Header3", "Header 4"};

            int cols = data.get(position).drugScores().size() + 1;

            if (cols > 1){

                String[] newHeads = new String[cols];
                String[][] pis = new String[cols][];
                String[] pisArr = new String[cols];
                String[][] nrti = new String[cols][];
                String[] nrtiArr = new String[cols];
                String[][] nnrti = new String[cols][];
                String[] nnrtiArr = new String[cols];
                String[][] insti = new String[cols][];
                String[] instiArr = new String[cols];
                int count=0;

                for (int i=0;i<cols;i++){
                    if (data.get(position).drugScores().get(i).drugClass().name().toString() == "PI"){
                        if (count == 0){
                            newHeads[count] = "PI";
                        }else {
                            newHeads[count] = data.get(position).drugScores().get(i).drug().name().toString();
                        }
                    }
                    count++;
                }

                for (int i=0;i<cols;i++){
                    if (i == 0){
                        String nms = "";
                        int mutationSizes = data.get(position).drugScores().get(i).partialScores().get(1).mutations().size();
                        for (int j=0;j<mutationSizes;j++){
                           nms =nms+data.get(position).drugScores().get(i).partialScores().get(1).mutations().get(j).text().toString();
                        }
                        pisArr[i] = nms;
                    }else{
                        pisArr[i] = String.valueOf(data.get(position).drugScores().get(i).partialScores().get(1).score());
                    }
                }
                pis[0] = pisArr;
//                pis[1] = pisArr;

                TableDataAdapter<String[]> myDataAdapter =
                        new SimpleTableDataAdapter(context, pis);
                TableHeaderAdapter myHeaderAdapter =
                        new SimpleTableHeaderAdapter(context, newHeads);

                holder.tableView.setHeaderAdapter(myHeaderAdapter);
                holder.tableView.setDataAdapter(myDataAdapter);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView drugResInterpretation, dbVersion, piMajorResistanceMutation, piAccessoryResistanceMutation, otherMutations, mutationScoring, inhibTitle, comment;
        RecyclerView recyclerView;
        TableLayout tableLayout;
        TableView<String[]> tableView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            drugResInterpretation = itemView.findViewById(R.id.drug_resistance_interpretation);
            dbVersion = itemView.findViewById(R.id.db_version);
            piMajorResistanceMutation = itemView.findViewById(R.id.pi_major_resistance_mutations);
            piAccessoryResistanceMutation = itemView.findViewById(R.id.pi_accessory_resistance_mutations);
            otherMutations = itemView.findViewById(R.id.other_mutations);
            mutationScoring = itemView.findViewById(R.id.mutation_scoring);
            inhibTitle = itemView.findViewById(R.id.inhibitor_title);
            recyclerView = itemView.findViewById(R.id.inhibitors_recycler);
            comment = itemView.findViewById(R.id.mutation_comment);
            tableLayout = itemView.findViewById(R.id.tableView);
            tableView = (TableView<String[]>) itemView.findViewById(R.id.my_table);

        }
    }
}
