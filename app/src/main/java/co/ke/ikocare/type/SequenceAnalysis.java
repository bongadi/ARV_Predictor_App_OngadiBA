package co.ke.ikocare.type;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Input;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.SequenceAnalysisRequestQuery;
import co.ke.ikocare.activities.SequenceAnalysisResults;
import co.ke.ikocare.fragments.MutationAnalysisi;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.MyApolloClient;
import io.paperdb.Paper;

public class SequenceAnalysis extends Fragment {

    ApolloClient myApolloClient;
    List<co.ke.ikocare.type.UnalignedSequenceInput> inputs;
    List<co.ke.ikocare.type.UnalignedSequenceInput> actualInputs;
    EditText header,sequence;
    Button analyzeSequence,analyzeSample,clearInputs;
    Dialog progDialog,errorDialog;
    Activity activity;

    public SequenceAnalysis() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sequence_analysis, container, false);
        activity = getActivity();
        populateView(view);
        return view;
    }

    public void populateView(View view){
        myApolloClient = new MyApolloClient().getApolloClient(getActivity());
        inputs = new ArrayList<>();
        actualInputs = new ArrayList<>();
        header = view.findViewById(R.id.tv_header);
        sequence = view.findViewById(R.id.tv_sequence);
        analyzeSequence = view.findViewById(R.id.bt_analyse);
        clearInputs = view.findViewById(R.id.clear_data);
        analyzeSample = view.findViewById(R.id.bt_analyse_sample);
        progDialog = new Dialog(getActivity());
        progDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progDialog.setContentView(R.layout.mut_prog_dialog);
        errorDialog = new Dialog(getActivity());
        errorDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        errorDialog.setContentView(R.layout.error_dialogue);
        DisplayMetrics metrics = this.getResources().getDisplayMetrics();
        int DeviceTotalWidth = metrics.widthPixels;
        int DeviceTotalHeight = metrics.heightPixels;
        progDialog.getWindow().setLayout(DeviceTotalWidth, DeviceTotalHeight);
        progDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progDialog.dismiss();
        errorDialog.getWindow().setLayout(DeviceTotalWidth, DeviceTotalHeight);
        errorDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        errorDialog.dismiss();
        inputs.add(new co.ke.ikocare.type.UnalignedSequenceInput(Input.fromNullable("AY030412"), Input.fromNullable("CCTCAAATCACTCTTTGGCAACGACCCATCGTCACAATAAAGATAGGGGGGCAGCTAARGGAAGCTCTATTAGATACAGGAGCAGATGATACAGTATTAGAAGATATAAATTTGCCAGGAAGATGGACACCAAAAATKATAGTGGGAATTGGAGGTTTTACCAAAGTAAGACAGTATGATCAGATACCTGTAGAAATTTGTGGACATAAAGCTATAGGTACAGTRTTAGTAGGACCTACACCTGCCAACATAATTGGAAGAAATCTGTTGACYCAGATTGGTTGCACTTTAAATTTTCCCATTAGTCCTATTGACACTGTACCAGTAAAATTAAAGCCAGGAATGGATGGCCCAAAAGTTAAACAATGGCCATTGACAGAAGAAAAAATAAAAGCATTAGTAGAAATTTGTGCAGAATTGGAASAGGACGGGAAAATTTCAAAAATTGGGCCTGAAAATCCATACAATACTCCAGTATTTGCCATAAAGAAAAAGAACAGYGATAAATGGAGAAAATTAGTAGATTTCAGAGAACTTAATAAGAGAACTCAAGACTTCTGGGAAGTTCAATTAGGAATACCACATCCCGGAGGGTTAAAAAAGAACAAATCAGTAACAGTACTGGATGTGGGTGATGCATATTTTTCARTTCCCTTAGATGAAGACTTCAGGAAGTATACTGCATTTACCATACCTAGTATAAACAATGAGACACCAGGGACTAGATATCAGTACAATGTGCTTCCACAGGGATGGAAAGGATCACCAGCAATATTCCAAAGTAGCATGACAAGAATCTTAGAACCTTTTAGAAAACAGAATCCAGACATAGTTATCTGTCAATAYGTGGATGATTTGTATGTAGGATCTGACTTAGAAATAGAGMAGCATAGAACAAAAGTAGAGGAACTGAGACAACATTTGTGGAAGTGGGGNTTTTACACACCAGACAAMAAACATCAGAAAGAACCTCCATTCCTTTGGATGGGTTATGAACTCCATCCTGATAAATGGACA")));
        inputs.add(new co.ke.ikocare.type.UnalignedSequenceInput(Input.fromNullable("AY030413"),Input.fromNullable("CCTCAAATCACTCTTTGGCAACGACCCATCGTCACAATAAGGATAGGAGGGCAACTAAAGGAAGCTCTATTAGATACAGGAGCAGATGATACAGTATTAGAAGAAATGAATTTGCCAGGAAAATGGAAACCAAAAATGATAGGGGGAATTGGAGGTTTTGTCAAAGTAAGACAGTATGAGCAGATACCCGTAGAAATCTGCGGACATAAAGTTATAGGTACAGTATTAGTAGGACCTACACCTGCCAACATAATTGGAAGAAATCTGATGACTCAGCTTGGTTGTACTTTAAATTTTCCCATTAGTCCTATTGAAACTGTACCAGTAAAATTAAAGCCAGGAATGGATGGCCCAAAAGTTAAACAATGGCCATTGACAGAGGAAAAAATAAATGCATTAGTAGAAATTTGTGCAGAAATGGAAAAGGAAGGGAAAATTTCWAAAATTGGGCCTGAAAATCCATACAATACTCCAGTATTTGCYATAAAGAAAAAGAACAGTACTAGATGGAGAAAATTAGTAGATTTCAGAGAACTTAATAAGAGAACTCAAGACTTCTGGGAAGTTCAATTAGGAATACCACATCCCKCAGGGTTAAAAAAGAAAAAATCAGTAACAGTACTGGATGTGGGTGATGCATACTTTTCAGTTCCCTTATATGAAGACTTTAGAAAGTATACTGCATTTACCATACCTAGTAAAAACAATGAGACACCAGGGATTAGATACCAGTATAATGTGCTTCCACAGGGATGGAAAGGATCACCAGCAATATTCCAAAGTAGCATGACAAAAATCTTAGAGCCTTTTAGACAACAAAATCCAGACCTAGTTATCTATCAATACATGGATGATTTGTATGTAGGATCTGACTTAGAAATAGGGCAGCATAGAACAAAAATAGAGGAACTGAGACAACATCTGTTGAGGTGGGGATTTTTCACACCAGATCAAAAACATCAGAARGAACCYCCATTCCTTTGGATGGGTTATGAACTCCATCCTGATAAATGGACAGTACAGCCTATACAGCTGCCAGAA")));


        FragmentTransaction ft = getFragmentManager().beginTransaction();

        analyzeSample.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progDialog.show();
                myApolloClient.query(SequenceAnalysisRequestQuery.builder().sequences(inputs).build())
                        .enqueue(new ApolloCall.Callback<SequenceAnalysisRequestQuery.Data>() {
                            @Override
                            public void onResponse(@NotNull Response<SequenceAnalysisRequestQuery.Data> response) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Paper.book().write("SEQDATA",response.getData().sequenceAnalysis());
                                        Paper.book().write("RAWDATA",response.getData());
                                        progDialog.dismiss();
                                        Intent intent = new Intent(getActivity(), SequenceAnalysisResults.class);
                                        startActivity(intent);
                                    }
                                });
                            }

                            @Override
                            public void onFailure(@NotNull ApolloException e) {
                                progDialog.dismiss();
                                errorDialog.show();
                            }
                        });
            }
        });
        clearInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                header.setText("");
                sequence.setText("");
                ft.detach(SequenceAnalysis.this).attach(SequenceAnalysis.this).commit();
                Toast.makeText(getActivity(),"Input fields cleared.", Toast.LENGTH_LONG).show();

            }
        });

        analyzeSequence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progDialog.show();

                if (!valid(header,sequence)){
                    progDialog.dismiss();
                    Message.makeToast(activity,activity,"Please make sure you have provided at least one input sequence");
                    return;
                }

                header.setText(R.string.sample_headers);
//                sequence.setText("CCTCAAATCACTCTTTGGCAACGACCCATCGTCACAATAAAGATAGGGGGGCAGCTAARGGAAGCTCTATTAGATACAGGAGCAGATGATACAGTATTAGAAGATATAAATTTGCCAGGAAGATGGACACCAAAAATKATAGTGGGAATTGGAGGTTTTACCAAAGTAAGACAGTATGATCAGATACCTGTAGAAATTTGTGGACATAAAGCTATAGGTACAGTRTTAGTAGGACCTACACCTGCCAACATAATTGGAAGAAATCTGTTGACYCAGATTGGTTGCACTTTAAATTTTCCCATTAGTCCTATTGACACTGTACCAGTAAAATTAAAGCCAGGAATGGATGGCCCAAAAGTTAAACAATGGCCATTGACAGAAGAAAAAATAAAAGCATTAGTAGAAATTTGTGCAGAATTGGAASAGGACGGGAAAATTTCAAAAATTGGGCCTGAAAATCCATACAATACTCCAGTATTTGCCATAAAGAAAAAGAACAGYGATAAATGGAGAAAATTAGTAGATTTCAGAGAACTTAATAAGAGAACTCAAGACTTCTGGGAAGTTCAATTAGGAATACCACATCCCGGAGGGTTAAAAAAGAACAAATCAGTAACAGTACTGGATGTGGGTGATGCATATTTTTCARTTCCCTTAGATGAAGACTTCAGGAAGTATACTGCATTTACCATACCTAGTATAAACAATGAGACACCAGGGACTAGATATCAGTACAATGTGCTTCCACAGGGATGGAAAGGATCACCAGCAATATTCCAAAGTAGCATGACAAGAATCTTAGAACCTTTTAGAAAACAGAATCCAGACATAGTTATCTGTCAATAYGTGGATGATTTGTATGTAGGATCTGACTTAGAAATAGAGMAGCATAGAACAAAAGTAGAGGAACTGAGACAACATTTGTGGAAGTGGGGNTTTTACACACCAGACAAMAAACATCAGAAAGAACCTCCATTCCTTTGGATGGGTTATGAACTCCATCCTGATAAATGGACA>CCTCAAATCACTCTTTGGCAACGACCCATCGTCACAATAAGGATAGGAGGGCAACTAAAGGAAGCTCTATTAGATACAGGAGCAGATGATACAGTATTAGAAGAAATGAATTTGCCAGGAAAATGGAAACCAAAAATGATAGGGGGAATTGGAGGTTTTGTCAAAGTAAGACAGTATGAGCAGATACCCGTAGAAATCTGCGGACATAAAGTTATAGGTACAGTATTAGTAGGACCTACACCTGCCAACATAATTGGAAGAAATCTGATGACTCAGCTTGGTTGTACTTTAAATTTTCCCATTAGTCCTATTGAAACTGTACCAGTAAAATTAAAGCCAGGAATGGATGGCCCAAAAGTTAAACAATGGCCATTGACAGAGGAAAAAATAAATGCATTAGTAGAAATTTGTGCAGAAATGGAAAAGGAAGGGAAAATTTCWAAAATTGGGCCTGAAAATCCATACAATACTCCAGTATTTGCYATAAAGAAAAAGAACAGTACTAGATGGAGAAAATTAGTAGATTTCAGAGAACTTAATAAGAGAACTCAAGACTTCTGGGAAGTTCAATTAGGAATACCACATCCCKCAGGGTTAAAAAAGAAAAAATCAGTAACAGTACTGGATGTGGGTGATGCATACTTTTCAGTTCCCTTATATGAAGACTTTAGAAAGTATACTGCATTTACCATACCTAGTAAAAACAATGAGACACCAGGGATTAGATACCAGTATAATGTGCTTCCACAGGGATGGAAAGGATCACCAGCAATATTCCAAAGTAGCATGACAAAAATCTTAGAGCCTTTTAGACAACAAAATCCAGACCTAGTTATCTATCAATACATGGATGATTTGTATGTAGGATCTGACTTAGAAATAGGGCAGCATAGAACAAAAATAGAGGAACTGAGACAACATCTGTTGAGGTGGGGATTTTTCACACCAGATCAAAAACATCAGAARGAACCYCCATTCCTTTGGATGGGTTATGAACTCCATCCTGATAAATGGACAGTACAGCCTATACAGCTGCCAGAA");
                String headerString = header.getText().toString();
                if (header.getText().toString().split(" ").length == 0){
                    headerString = null;
                    for (String val : sequence.getText().toString().split(">")){
                        if (!val.equals("")){
                            actualInputs.add(new UnalignedSequenceInput(Input.fromNullable(headerString),Input.fromNullable(val)));
                        }
                    }
                }else if (header.getText().toString().split(" ").length == 1){
                    headerString = header.getText().toString().split(" ")[0];
                    for (String val : sequence.getText().toString().split(">")){
                        if (!val.equals("")){
                            actualInputs.add(new UnalignedSequenceInput(Input.fromNullable(headerString),Input.fromNullable(val)));
                        }
                    }
                }else {
                    for (int i =0;i<sequence.getText().toString().split(">").length;i++){
                        if (!sequence.getText().toString().split(">")[i].equals("")){
                            actualInputs.add(new UnalignedSequenceInput(Input.fromNullable(header.getText().toString().split(" ")[i]),Input.fromNullable(sequence.getText().toString().split(">")[i])));
                        }
                    }
                }
                Toast.makeText(getActivity(),actualInputs.toString(),Toast.LENGTH_LONG).show();

                myApolloClient.query(SequenceAnalysisRequestQuery.builder().sequences(actualInputs).build())
                        .enqueue(new ApolloCall.Callback<SequenceAnalysisRequestQuery.Data>() {
                            @Override
                            public void onResponse(@NotNull Response<SequenceAnalysisRequestQuery.Data> response) {
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
//                                        sequence.setText(response.getData().toString());
                                        Paper.book().write("SEQDATA",response.getData().sequenceAnalysis());
                                        Paper.book().write("RAWDATA",response.getData());
                                        progDialog.dismiss();
                                        header.setText("");
                                        sequence.setText("");
                                        actualInputs.clear();
                                        Intent intent = new Intent(getActivity(), SequenceAnalysisResults.class);
                                        startActivity(intent);
                                    }
                                });
                            }
                            @Override
                            public void onFailure(@NotNull ApolloException e) {
//                                sequence.setText(e.getMessage());
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        header.setText("");
                                        sequence.setText("");
                                        header.setText("");
                                        sequence.setText("");
                                        progDialog.dismiss();
                                        errorDialog.show();
                                    }
                                });
                            }
                        });
            }
        });
    }

    private boolean valid(EditText headerTv, EditText sequenceTv ) {
        boolean valid = true;
        String header = headerTv.getText().toString();
        String sequence = sequenceTv.getText().toString();

        if (sequence.isEmpty()) {
            sequenceTv.setError("No Sequences found!");
            valid = false;
        }else {
            sequenceTv.setError(null);
        }

        return valid;
    }
}