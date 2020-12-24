package co.ke.ikocare.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.adapters.DrugCategoryAdapter;
import co.ke.ikocare.models.Drug.Drug;
import co.ke.ikocare.models.Drug.DrugCategory;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.PreferenceManager;

public class DrugSelector extends Fragment {

    private Login.OnLoginFormActivityListener onLoginFormActivityListener;
//    private EditText sequence;
//    private Button sub_query;
//    private List<UnalignedSequenceInput> inputSequence;
//    private String header = "AY030413";
    private RecyclerView recyclerView;
    private Button save;
    private List<DrugCategory> drugCategories;
    private DrugCategoryAdapter drugCategoryAdapter;
    private List<Drug> nrtiDrugs;
    private List<Drug> instiDrugs;
    private List<Drug> nnrtiDrugs;
    private List<Drug> piDrugs;
    PreferenceManager preferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_drug_selector, container, false);
//        sequence = view.findViewById(R.id.tv_sequence);
//        sub_query = view.findViewById(R.id.btn_sub_seq);
//        inputSequence = new ArrayList<>();
        preferenceManager= new PreferenceManager(getActivity());
        recyclerView = view.findViewById(R.id.drug_category_recycler);
        save = view.findViewById(R.id.btn_submit_drugs);
        drugCategories = new ArrayList<>();
        nrtiDrugs = new ArrayList<>();
        instiDrugs = new ArrayList<>();
        nnrtiDrugs = new ArrayList<>();
        piDrugs = new ArrayList<>();


        nrtiDrugs.add(new Drug("ABC",preferenceManager.getDrugStatus("ABC")));
        preferenceManager.setDrugSelected(true,"ABC");
        nrtiDrugs.add(new Drug("AZT",preferenceManager.getDrugStatus("AZT")));
        preferenceManager.setDrugSelected(true,"AZT");
        nrtiDrugs.add(new Drug("FTC",preferenceManager.getDrugStatus("FTC")));
        nrtiDrugs.add(new Drug("3TC",preferenceManager.getDrugStatus("3TC")));
        preferenceManager.setDrugSelected(true,"3TC");
        nrtiDrugs.add(new Drug("TDF",preferenceManager.getDrugStatus("TDF")));
        nrtiDrugs.add(new Drug("D4T",preferenceManager.getDrugStatus("D4T")));
        preferenceManager.setDrugSelected(true,"D4T");
        nrtiDrugs.add(new Drug("DDI",preferenceManager.getDrugStatus("DDI")));

        instiDrugs.add(new Drug("BIC",preferenceManager.getDrugStatus("BIC")));
        preferenceManager.setDrugSelected(true,"BIC");
        instiDrugs.add(new Drug("DTG",preferenceManager.getDrugStatus("DTG")));
        preferenceManager.setDrugSelected(true,"DTG");
        instiDrugs.add(new Drug("EVG",preferenceManager.getDrugStatus("EVG")));
        preferenceManager.setDrugSelected(true,"EVG");
        instiDrugs.add(new Drug("RAL",preferenceManager.getDrugStatus("RAL")));

        nnrtiDrugs.add(new Drug("DOR",preferenceManager.getDrugStatus("DOR")));
        preferenceManager.setDrugSelected(true,"DOR");
        nnrtiDrugs.add(new Drug("EFV",preferenceManager.getDrugStatus("EFV")));
        preferenceManager.setDrugSelected(true,"EFV");
        nnrtiDrugs.add(new Drug("ETR",preferenceManager.getDrugStatus("ETR")));
        nnrtiDrugs.add(new Drug("NVP",preferenceManager.getDrugStatus("NVP")));
        preferenceManager.setDrugSelected(true,"NVP");
        nnrtiDrugs.add(new Drug("RPV",preferenceManager.getDrugStatus("RPV")));
        preferenceManager.setDrugSelected(true,"RPV");

        piDrugs.add(new Drug("ATV/r",preferenceManager.getDrugStatus("ATV/r")));
        preferenceManager.setDrugSelected(true,"ATV/r");
        piDrugs.add(new Drug("DRV/r",preferenceManager.getDrugStatus("DRV/r")));
        preferenceManager.setDrugSelected(true,"DRV/r");
        piDrugs.add(new Drug("LPV/r",preferenceManager.getDrugStatus("LPV/r")));
        piDrugs.add(new Drug("FPV/r",preferenceManager.getDrugStatus("FPV/r")));
        preferenceManager.setDrugSelected(true,"FPV/r");
        piDrugs.add(new Drug("IDV/r",preferenceManager.getDrugStatus("IDV/r")));
        preferenceManager.setDrugSelected(true,"IDV/r");
        piDrugs.add(new Drug("NFV",  preferenceManager.getDrugStatus("NFV")));
        piDrugs.add(new Drug("SQV/r",preferenceManager.getDrugStatus("SQV/r")));
        preferenceManager.setDrugSelected(true,"SQV/r");
        piDrugs.add(new Drug("TPV/r",preferenceManager.getDrugStatus("TPV/r")));

        drugCategories.add(new DrugCategory("NRTI:",nrtiDrugs));
        drugCategories.add(new DrugCategory("INSTI:",instiDrugs));
        drugCategories.add(new DrugCategory("NNRTI:",nnrtiDrugs));
        drugCategories.add(new DrugCategory("PI:",piDrugs));

        drugCategoryAdapter = new DrugCategoryAdapter(drugCategories,getActivity());

        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerView.setAdapter(drugCategoryAdapter);
        Logger.addLogAdapter(new AndroidLogAdapter());
        save.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                DrugAnalysis nextFrag= new DrugAnalysis();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainerDash, nextFrag, "findThisFragment")
                        .addToBackStack(null)
                        .commit();
//                onLoginFormActivityListener.doDrugAnalysis();
                Message.makeToast(getActivity(),getContext(),"Drugs Selected Successfully");
            }
        });

//        inputSequence.add(UnalignedSequenceInput.builder().header(header).sequence(sequence.getText().toString()).build());
//        ApolloClient appoloClient = ApolloClient.builder()
//                .serverUrl("ttps://hivdb.stanford.edu/graphql")
//                .build();

//        sub_query.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                appoloClient.query(new SequenceAnalysisQuery(inputSequence))
//                        .enqueue(new ApolloCall.Callback<SequenceAnalysisQuery.Data>() {
//                            @Override
//                            public void onResponse(@NotNull Response<SequenceAnalysisQuery.Data> response) {
//                                Logger.d(response.getData().toString());
//                                Toast.makeText(getActivity(),response.data().toString(),Toast.LENGTH_LONG).show();
//                            }
//
//                            @Override
//                            public void onFailure(@NotNull ApolloException e) {
//                                Logger.d(e.getLocalizedMessage());
//                                Toast.makeText(getActivity(),e.getLocalizedMessage(),Toast.LENGTH_LONG).show();
//                            }
//                        });
//            }
//        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onLoginFormActivityListener = (Login.OnLoginFormActivityListener) activity;

    }
}