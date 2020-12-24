package co.ke.ikocare.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import co.ke.ikocare.App;
import co.ke.ikocare.MutationAnalysisRequestQuery;
import co.ke.ikocare.R;
import co.ke.ikocare.adapters.MutationResultsAdapter;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.models.auth.UserData_;
import io.objectbox.Box;
import io.objectbox.query.Query;
import io.objectbox.query.QueryBuilder;
import io.paperdb.Paper;

public class MutationResultActivity extends AppCompatActivity {

    RecyclerView results;
    List<MutationAnalysisRequestQuery.DrugResistance> analysisResultData;
    MutationAnalysisRequestQuery.Data nData;
    MutationResultsAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mutation_result);
        results = findViewById(R.id.mutation_result_recycler);
        analysisResultData = new ArrayList<>();
        analysisResultData.addAll(Paper.book().read("MUTRESULT"));
        nData = Paper.book().read("DATA");
        myAdapter = new MutationResultsAdapter(analysisResultData,this,nData);
        results.setLayoutManager(new LinearLayoutManager(this));
        results.setAdapter(myAdapter);

    }
}