package co.ke.ikocare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.ke.ikocare.R;
import co.ke.ikocare.SequenceAnalysisRequestQuery;
import co.ke.ikocare.adapters.SequenceResultAdapter;
import io.paperdb.Paper;

public class SequenceAnalysisResults extends AppCompatActivity {

    TextView prIncluded,rtIncluded,subTypes;
    List<SequenceAnalysisRequestQuery.SequenceAnalysi> analysisResultData;
   SequenceAnalysisRequestQuery.Data rawData;
    RecyclerView recyclerView;
    SequenceResultAdapter sequenceResultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sequence_analysis_results);
        analysisResultData = new ArrayList<>();
        analysisResultData.addAll(Paper.book().read("SEQDATA"));
        rawData=Paper.book().read("RAWDATA");

        prIncluded = findViewById(R.id.pr_included);
        rtIncluded = findViewById(R.id.rt_included);
        subTypes = findViewById(R.id.subt_types);
        recyclerView = findViewById(R.id.sequence_result_recycler);
        prIncluded.setText("Sequence includes PR :    "+analysisResultData.get(0).alignedGeneSequences().get(0).firstAA().toString()+"-"+analysisResultData.get(0).alignedGeneSequences().get(0).lastAA().toString());
        rtIncluded.setText("Sequence includes RT :    "+analysisResultData.get(0).alignedGeneSequences().get(0).firstNA().toString()+"-"+analysisResultData.get(0).alignedGeneSequences().get(0).lastNA().toString());
        subTypes.setText("Subtype                :    "+analysisResultData.get(0).firstTenCloseSubtypes().get(0).displayWithoutDistance().toString()+"  ("+analysisResultData.get(0).firstTenCloseSubtypes().get(0).distancePcnt().toString()+")");
        sequenceResultAdapter = new SequenceResultAdapter(analysisResultData,this,rawData);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(sequenceResultAdapter);

    }
}