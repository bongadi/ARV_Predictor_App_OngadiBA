package co.ke.ikocare.fragments;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.apollographql.apollo.ApolloCall;
import com.apollographql.apollo.ApolloCallback;
import com.apollographql.apollo.ApolloClient;
import com.apollographql.apollo.api.Response;
import com.apollographql.apollo.exception.ApolloException;
import com.apollographql.apollo.fetcher.ApolloResponseFetchers;
import com.apollographql.apollo.rx2.Rx2Apollo;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.Logger;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import co.ke.ikocare.App;
import co.ke.ikocare.MutationAnalysisRequestQuery;
import co.ke.ikocare.R;
import co.ke.ikocare.activities.MutationResultActivity;
import co.ke.ikocare.adapters.MutationSelectionAdapter;
import co.ke.ikocare.apollomodels.Data;
import co.ke.ikocare.apollomodels.MutationLoad;
import co.ke.ikocare.models.auth.UserData;
import co.ke.ikocare.models.mutation.MutationData;
import co.ke.ikocare.utilities.Message;
import co.ke.ikocare.utilities.MyApolloClient;
import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.paperdb.Paper;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static android.content.ContentValues.TAG;

public class MutationAnalysisi extends Fragment {

    RecyclerView transRecycler;
    RecyclerView protaseRecycler;
    RecyclerView integraseRecycler;

    MutationSelectionAdapter trMutationSelectionAdapter;
    MutationSelectionAdapter prMutationSelectionAdapter;
    MutationSelectionAdapter intMutationSelectionAdapter;

    List<MutationData> trData;
    List<MutationData> prData;
    List<MutationData> intData;
    List<CharSequence> trCharOptions, tr41, tr44, tr62, tr65, tr67, tr68, tr69, tr70, tr74, tr75, tr77, tr90, tr98, tr100, tr101, tr103, tr106, tr108, tr115, tr116, tr118, tr138, tr151, tr179, tr181, tr184, tr188, tr190, tr210, tr215, tr219, tr221, tr225, tr227, tr230, tr234, tr236, tr238, tr318, tr348;
    List<CharSequence> prCharOptions, pr11, pr13, pr20, pr23, pr24, pr30, pr32, pr33, pr35, pr36, pr43, pr46, pr47, pr48, pr50, pr53, pr54, pr58, pr63, pr71, pr73, pr74, pr76, pr77, pr82, pr83, pr84, pr85, pr88, pr89, pr90, pr93;
    List<CharSequence> intCharOptions, int66, int74, int92, int95, int97, int114, int118, int121, int128, int138, int140, int143, int145, int146, int147, int148, int151, int153, int155, int157, int163, int230, int263;
    List<String> mutations;
    EditText transcripase, protase, integrase;
    Button analyze,clearInputs;
    ApolloClient myApolloClient;
    Dialog progDialog,errorDialog;

//    private Box<MutationAnalysisRequestQuery.Data> mutationData;


    public MutationAnalysisi() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_mutation_analysisi, container, false);
        initializeUserInterface(view);
        return view;
    }

    public void initializeUserInterface(View view) {

//        mutationData = ((App)getActivity().getApplicationContext()).getBoxStore().boxFor(MutationAnalysisRequestQuery.Data.class);

        Logger.addLogAdapter(new AndroidLogAdapter());
        trData = new ArrayList<>();
        prData = new ArrayList<>();
        intData = new ArrayList<>();
        trCharOptions = new ArrayList<>();
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
        tr41 = new ArrayList<>();
        tr44 = new ArrayList<>();
        tr62 = new ArrayList<>();
        tr65 = new ArrayList<>();
        tr67 = new ArrayList<>();
        tr68 = new ArrayList<>();
        tr69 = new ArrayList<>();
        tr70 = new ArrayList<>();
        tr74 = new ArrayList<>();
        tr75 = new ArrayList<>();
        tr77 = new ArrayList<>();
        tr90 = new ArrayList<>();
        tr98 = new ArrayList<>();
        tr100 = new ArrayList<>();
        tr101 = new ArrayList<>();
        tr103 = new ArrayList<>();
        tr106 = new ArrayList<>();
        tr108 = new ArrayList<>();
        tr115 = new ArrayList<>();
        tr116 = new ArrayList<>();
        tr118 = new ArrayList<>();
        tr138 = new ArrayList<>();
        tr151 = new ArrayList<>();
        tr179 = new ArrayList<>();
        tr181 = new ArrayList<>();
        tr184 = new ArrayList<>();
        tr188 = new ArrayList<>();
        tr190 = new ArrayList<>();
        tr210 = new ArrayList<>();
        tr215 = new ArrayList<>();
        tr219 = new ArrayList<>();
        tr221 = new ArrayList<>();
        tr225 = new ArrayList<>();
        tr227 = new ArrayList<>();
        tr230 = new ArrayList<>();
        tr234 = new ArrayList<>();
        tr236 = new ArrayList<>();
        tr238 = new ArrayList<>();
        tr318 = new ArrayList<>();
        tr348 = new ArrayList<>();

        pr11 = new ArrayList<>();
        pr13 = new ArrayList<>();
        pr20 = new ArrayList<>();
        pr23 = new ArrayList<>();
        pr24 = new ArrayList<>();
        pr30 = new ArrayList<>();
        pr32 = new ArrayList<>();
        pr33 = new ArrayList<>();
        pr35 = new ArrayList<>();
        pr36 = new ArrayList<>();
        pr43 = new ArrayList<>();
        pr46 = new ArrayList<>();
        pr47 = new ArrayList<>();
        pr48 = new ArrayList<>();
        pr50 = new ArrayList<>();
        pr53 = new ArrayList<>();
        pr54 = new ArrayList<>();
        pr58 = new ArrayList<>();
        pr63 = new ArrayList<>();
        pr71 = new ArrayList<>();
        pr73 = new ArrayList<>();
        pr74 = new ArrayList<>();
        pr76 = new ArrayList<>();
        pr77 = new ArrayList<>();
        pr82 = new ArrayList<>();
        pr83 = new ArrayList<>();
        pr84 = new ArrayList<>();
        pr85 = new ArrayList<>();
        pr88 = new ArrayList<>();
        pr89 = new ArrayList<>();
        pr90 = new ArrayList<>();
        pr93 = new ArrayList<>();

        int66 = new ArrayList<>();
        int74 = new ArrayList<>();
        int92 = new ArrayList<>();
        int95 = new ArrayList<>();
        int97 = new ArrayList<>();
        int114 = new ArrayList<>();
        int118 = new ArrayList<>();
        int121 = new ArrayList<>();
        int128 = new ArrayList<>();
        int138 = new ArrayList<>();
        int140 = new ArrayList<>();
        int143 = new ArrayList<>();
        int145 = new ArrayList<>();
        int146 = new ArrayList<>();
        int147 = new ArrayList<>();
        int148 = new ArrayList<>();
        int151 = new ArrayList<>();
        int153 = new ArrayList<>();
        int155 = new ArrayList<>();
        int157 = new ArrayList<>();
        int163 = new ArrayList<>();
        int230 = new ArrayList<>();
        int263 = new ArrayList<>();

        prCharOptions = new ArrayList<>();
        intCharOptions = new ArrayList<>();
        mutations = new ArrayList<>();
        myApolloClient = new MyApolloClient().getApolloClient(getActivity());
        transcripase = view.findViewById(R.id.tv_rev_transcripase);
        analyze = view.findViewById(R.id.btn_analyze_mutation);
        protase = view.findViewById(R.id.tv_protase);
        integrase = view.findViewById(R.id.tv_integrase);

//        mutations.add("PR:V32I");
//        mutations.add("PR:L76V");
//        mutations.add("RT:E40F");

        Handler mHandler = new Handler(Looper.getMainLooper());

        transRecycler = view.findViewById(R.id.rev_trans_recycler);
        protaseRecycler = view.findViewById(R.id.protase_recycler);
        integraseRecycler = view.findViewById(R.id.integrase_recycler);
        clearInputs=view.findViewById(R.id.clear_inputs);

        trCharOptions.add("--");
        trCharOptions.add("F");
        tr41.add("--");
        tr41.add("L");
        tr44.add("--");
        tr44.add("A");
        tr44.add("D");
        tr62.add("--");
        tr62.add("V");
        tr65.add("--");
        tr65.add("E");
        tr65.add("N");
        tr65.add("R");
        tr67.add("--");
        tr67.add("E");
        tr67.add("G");
        tr67.add("H");
        tr67.add("N");
        tr67.add("S");
        tr67.add("T");
        tr68.add("--");
        tr68.add("del");
        tr69.add("--");
        tr69.add("D");
        tr69.add("G");
        tr69.add("N");
        tr70.add("--");
        tr70.add("E");
        tr70.add("G");
        tr70.add("N");
        tr70.add("Q");
        tr70.add("R");
        tr70.add("S");
        tr74.add("--");
        tr74.add("I");
        tr74.add("V");
        tr75.add("--");
        tr75.add("A");
        tr75.add("I");
        tr75.add("L");
        tr75.add("M");
        tr75.add("S");
        tr75.add("T");
        tr77.add("--");
        tr77.add("L");
        tr90.add("--");
        tr90.add("I");
        tr98.add("--");
        tr98.add("G");
        tr100.add("--");
        tr100.add("I");
        tr100.add("V");
        tr101.add("--");
        tr101.add("E");
        tr101.add("H");
        tr101.add("N");
        tr101.add("P");
        tr101.add("Q");
        tr103.add("--");
        tr103.add("E");
        tr103.add("H");
        tr103.add("N");
        tr103.add("Q");
        tr103.add("R");
        tr103.add("S");
        tr103.add("T");
        tr106.add("--");
        tr106.add("A");
        tr106.add("I");
        tr106.add("M");
        tr108.add("--");
        tr108.add("I");
        tr115.add("--");
        tr115.add("F");
        tr116.add("--");
        tr116.add("Y");
        tr118.add("--");
        tr118.add("I");
        tr138.add("--");
        tr138.add("A");
        tr138.add("G");
        tr138.add("K");
        tr138.add("Q");
        tr138.add("R");
        tr151.add("--");
        tr151.add("L");
        tr151.add("M");
        tr179.add("--");
        tr179.add("D");
        tr179.add("E");
        tr179.add("F");
        tr179.add("L");
        tr179.add("T");
        tr181.add("--");
        tr181.add("C");
        tr181.add("F");
        tr181.add("G");
        tr181.add("I");
        tr181.add("S");
        tr181.add("V");
        tr184.add("--");
        tr184.add("I");
        tr184.add("V");
        tr188.add("--");
        tr188.add("C");
        tr188.add("F");
        tr188.add("H");
        tr188.add("L");
        tr190.add("--");
        tr190.add("A");
        tr190.add("C");
        tr190.add("E");
        tr190.add("Q");
        tr190.add("S");
        tr190.add("T");
        tr190.add("V");
        tr210.add("--");
        tr210.add("W");
        tr215.add("--");
        tr215.add("A");
        tr215.add("C");
        tr215.add("D");
        tr215.add("E");
        tr215.add("F");
        tr215.add("I");
        tr215.add("L");
        tr215.add("N");
        tr215.add("S");
        tr215.add("V");
        tr215.add("Y");
        tr219.add("--");
        tr219.add("E");
        tr219.add("N");
        tr219.add("Q");
        tr219.add("R");
        tr219.add("W");
        tr221.add("--");
        tr221.add("Y");
        tr225.add("--");
        tr225.add("H");
        tr227.add("--");
        tr227.add("C");
        tr227.add("L");
        tr230.add("--");
        tr230.add("I");
        tr230.add("L");
        tr234.add("--");
        tr234.add("I");
        tr236.add("--");
        tr236.add("L");
        tr238.add("--");
        tr238.add("N");
        tr238.add("T");
        tr318.add("--");
        tr318.add("F");
        tr348.add("--");
        tr348.add("I");

        prCharOptions.add("--");
        prCharOptions.add("F");
        prCharOptions.add("I");
        prCharOptions.add("R");
        prCharOptions.add("V");
        prCharOptions.add("Y");
        pr11.add("--");
        pr11.add("I");
        pr11.add("L");
        pr13.add("--");
        pr13.add("V");
        pr20.add("--");
        pr20.add("I");
        pr20.add("M");
        pr20.add("R");
        pr20.add("T");
        pr20.add("V");
        pr23.add("--");
        pr23.add("I");
        pr24.add("--");
        pr24.add("F");
        pr24.add("I");
        pr30.add("--");
        pr30.add("N");
        pr32.add("--");
        pr32.add("I");
        pr33.add("--");
        pr33.add("F");
        pr33.add("I");
        pr33.add("V");
        pr35.add("--");
        pr35.add("G");
        pr36.add("--");
        pr36.add("I");
        pr36.add("L");
        pr36.add("T");
        pr36.add("V");
        pr43.add("--");
        pr43.add("T");
        pr46.add("--");
        pr46.add("I");
        pr46.add("L");
        pr46.add("V");
        pr47.add("--");
        pr47.add("A");
        pr47.add("V");
        pr48.add("--");
        pr48.add("A");
        pr48.add("L");
        pr48.add("M");
        pr48.add("Q");
        pr48.add("S");
        pr48.add("T");
        pr48.add("V");
        pr50.add("--");
        pr50.add("L");
        pr50.add("V");
        pr53.add("--");
        pr53.add("L");
        pr53.add("Y");
        pr54.add("--");
        pr54.add("A");
        pr54.add("L");
        pr54.add("M");
        pr54.add("S");
        pr54.add("T");
        pr54.add("V");
        pr58.add("--");
        pr58.add("E");
        pr63.add("--");
        pr63.add("P");
        pr71.add("--");
        pr71.add("I");
        pr71.add("L");
        pr71.add("T");
        pr71.add("V");
        pr73.add("--");
        pr73.add("A");
        pr73.add("C");
        pr73.add("D");
        pr73.add("S");
        pr73.add("T");
        pr73.add("V");
        pr74.add("--");
        pr74.add("P");
        pr74.add("S");
        pr76.add("--");
        pr76.add("V");
        pr77.add("--");
        pr77.add("I");
        pr82.add("--");
        pr82.add("A");
        pr82.add("C");
        pr82.add("F");
        pr82.add("I");
        pr82.add("L");
        pr82.add("M");
        pr82.add("S");
        pr82.add("T");
        pr83.add("--");
        pr83.add("D");
        pr84.add("--");
        pr84.add("A");
        pr84.add("C");
        pr84.add("V");
        pr85.add("--");
        pr85.add("V");
        pr88.add("--");
        pr88.add("D");
        pr88.add("G");
        pr88.add("S");
        pr88.add("T");
        pr89.add("--");
        pr89.add("I");
        pr89.add("M");
        pr89.add("T");
        pr89.add("V");
        pr90.add("--");
        pr90.add("M");
        pr93.add("--");
        pr93.add("L");

        intCharOptions.add("---");
        intCharOptions.add("--");
        intCharOptions.add("Y");
        int66.add("--");
        int66.add("A");
        int66.add("I");
        int66.add("K");
        int74.add("--");
        int74.add("I");
        int74.add("M");
        int92.add("--");
        int92.add("G");
        int92.add("Q");
        int92.add("V");
        int95.add("--");
        int95.add("K");
        int97.add("--");
        int97.add("A");
        int114.add("--");
        int114.add("Y");
        int118.add("--");
        int118.add("R");
        int121.add("--");
        int121.add("Y");
        int128.add("--");
        int128.add("T");
        int138.add("--");
        int138.add("A");
        int138.add("K");
        int138.add("T");
        int140.add("--");
        int140.add("A");
        int140.add("C");
        int140.add("S");
        int143.add("--");
        int143.add("A");
        int143.add("C");
        int143.add("G");
        int143.add("H");
        int143.add("K");
        int143.add("R");
        int143.add("S");
        int145.add("--");
        int145.add("S");
        int146.add("--");
        int146.add("P");
        int147.add("--");
        int147.add("G");
        int148.add("--");
        int148.add("H");
        int148.add("K");
        int148.add("N");
        int148.add("R");
        int151.add("--");
        int151.add("A");
        int151.add("I");
        int151.add("L");
        int153.add("--");
        int153.add("F");
        int153.add("Y");
        int155.add("--");
        int155.add("H");
        int155.add("S");
        int155.add("T");
        int157.add("--");
        int157.add("Q");
        int163.add("--");
        int163.add("K");
        int163.add("R");
        int230.add("--");
        int230.add("R");
        int263.add("--");
        int263.add("K");

        trData.add(new MutationData(40, "E", trCharOptions));
        trData.add(new MutationData(41, "M", tr41));
        trData.add(new MutationData(44, "E", tr44));
        trData.add(new MutationData(62, "A", tr62));
        trData.add(new MutationData(65, "K", tr65));
        trData.add(new MutationData(67, "D", tr67));
        trData.add(new MutationData(68, "S", tr68));
        trData.add(new MutationData(69, "T", tr69));
        trData.add(new MutationData(70, "K", tr70));
        trData.add(new MutationData(74, "L", tr74));
        trData.add(new MutationData(75, "V", tr75));
        trData.add(new MutationData(77, "F", tr77));
        trData.add(new MutationData(90, "V", tr90));
        trData.add(new MutationData(98, "A", tr98));
        trData.add(new MutationData(100, "L", tr100));
        trData.add(new MutationData(101, "K", tr101));
        trData.add(new MutationData(106, "V", tr106));
        trData.add(new MutationData(103, "K", tr103));
        trData.add(new MutationData(108, "V", tr108));
        trData.add(new MutationData(115, "Y", tr115));
        trData.add(new MutationData(116, "F", tr116));
        trData.add(new MutationData(118, "V", tr118));
        trData.add(new MutationData(138, "E", tr138));
        trData.add(new MutationData(151, "Q", tr151));
        trData.add(new MutationData(179, "V", tr179));
        trData.add(new MutationData(181, "Y", tr181));
        trData.add(new MutationData(184, "M", tr184));
        trData.add(new MutationData(188, "Y", tr188));
        trData.add(new MutationData(190, "G", tr190));
        trData.add(new MutationData(210, "L", tr210));
        trData.add(new MutationData(215, "T", tr215));
        trData.add(new MutationData(219, "K", tr219));
        trData.add(new MutationData(221, "H", tr221));
        trData.add(new MutationData(225, "P", tr225));
        trData.add(new MutationData(227, "F", tr227));
        trData.add(new MutationData(230, "M", tr230));
        trData.add(new MutationData(234, "L", tr234));
        trData.add(new MutationData(236, "P", tr236));
        trData.add(new MutationData(238, "K", tr238));
        trData.add(new MutationData(318, "Y", tr318));
        trData.add(new MutationData(348, "N", tr348));

        prData.add(new MutationData(10, "l", prCharOptions));
        prData.add(new MutationData(11, "V", pr11));
        prData.add(new MutationData(13, "I", pr13));
        prData.add(new MutationData(20, "K", pr20));
        prData.add(new MutationData(23, "L", pr23));
        prData.add(new MutationData(24, "L", pr24));
        prData.add(new MutationData(30, "D", pr30));
        prData.add(new MutationData(32, "V", pr32));
        prData.add(new MutationData(33, "L", pr33));
        prData.add(new MutationData(35, "E", pr35));
        prData.add(new MutationData(36, "M", pr36));
        prData.add(new MutationData(43, "K", pr43));
        prData.add(new MutationData(46, "M", pr46));
        prData.add(new MutationData(47, "I", pr47));
        prData.add(new MutationData(48, "G", pr48));
        prData.add(new MutationData(50, "I", pr50));
        prData.add(new MutationData(53, "F", pr53));
        prData.add(new MutationData(54, "I", pr54));
        prData.add(new MutationData(58, "Q", pr58));
        prData.add(new MutationData(63, "L", pr63));
        prData.add(new MutationData(71, "A", pr71));
        prData.add(new MutationData(73, "G", pr73));
        prData.add(new MutationData(74, "T", pr74));
        prData.add(new MutationData(76, "L", pr76));
        prData.add(new MutationData(77, "V", pr77));
        prData.add(new MutationData(82, "V", pr82));
        prData.add(new MutationData(83, "N", pr83));
        prData.add(new MutationData(84, "I", pr84));
        prData.add(new MutationData(85, "I", pr85));
        prData.add(new MutationData(88, "N", pr88));
        prData.add(new MutationData(89, "L", pr89));
        prData.add(new MutationData(90, "L", pr90));
        prData.add(new MutationData(93, "I", pr93));

        intData.add(new MutationData(51, "H", intCharOptions));
        intData.add(new MutationData(66, "T", int66));
        intData.add(new MutationData(74, "L", int74));
        intData.add(new MutationData(92, "E", int92));
        intData.add(new MutationData(95, "Q", int95));
        intData.add(new MutationData(97, "T", int97));
        intData.add(new MutationData(114, "H", int114));
        intData.add(new MutationData(118, "G", int118));
        intData.add(new MutationData(121, "F", int121));
        intData.add(new MutationData(128, "G", int128));
        intData.add(new MutationData(138, "E", int138));
        intData.add(new MutationData(140, "G", int140));
        intData.add(new MutationData(143, "Y", int143));
        intData.add(new MutationData(145, "P", int145));
        intData.add(new MutationData(146, "Q", int146));
        intData.add(new MutationData(147, "S", int147));
        intData.add(new MutationData(148, "Q", int148));
        intData.add(new MutationData(151, "V", int151));
        intData.add(new MutationData(153, "S", int153));
        intData.add(new MutationData(155, "N", int155));
        intData.add(new MutationData(157, "E", int157));
        intData.add(new MutationData(163, "G", int163));
        intData.add(new MutationData(230, "S", int230));
        intData.add(new MutationData(263, "R", int263));

//        Toast.makeText(getActivity(),trData.toString(),Toast.LENGTH_LONG).show();

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        trMutationSelectionAdapter = new MutationSelectionAdapter(trData, getActivity(), transcripase);
        prMutationSelectionAdapter = new MutationSelectionAdapter(prData, getActivity(), protase);
        intMutationSelectionAdapter = new MutationSelectionAdapter(intData, getActivity(), integrase);
        transRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        protaseRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        integraseRecycler.setLayoutManager(new GridLayoutManager(getActivity(), 4));
        transRecycler.setNestedScrollingEnabled(false);
        transRecycler.setAdapter(trMutationSelectionAdapter);
        protaseRecycler.setAdapter(prMutationSelectionAdapter);
        integraseRecycler.setAdapter(intMutationSelectionAdapter);
        clearInputs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(),String.valueOf(trData.size()),Toast.LENGTH_LONG).show();
                transcripase.setText("");
                protase.setText("");
                integrase.setText("");
                ft.detach(MutationAnalysisi.this).attach(MutationAnalysisi.this).commit();
                Toast.makeText(getActivity(),"Input fields cleared. Please select again.", Toast.LENGTH_LONG).show();
            }
        });

        analyze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(getActivity(), transcripase.getText().toString(), Toast.LENGTH_LONG).show();
                progDialog.show();

                if (!valid(transcripase,protase,integrase)){
                    progDialog.dismiss();
                    Message.makeToast(getActivity(),getActivity(),"You must select at least one mutation from a category");
                    return;
                }
                StringBuilder output = new StringBuilder();
                if (!String.valueOf(transcripase.getText()).equals("")){
                    String[] inputs = String.valueOf(transcripase.getText()).split(" ");
                    if (inputs.length > 0){
                        for (String val : inputs) {
                            if (val.length() > 2) {
                                mutations.add("RT:" + val);
                                output.append(" ").append(val);
                            }
                        }
                    }
                }
                if (!String.valueOf(protase.getText()).equals("")){
                    String[] prInputs = String.valueOf(protase.getText()).split(" ");
                    if (prInputs.length > 0){
                        for (String val : prInputs) {
                            if (val.length() > 2){
                                mutations.add("PR:" + val);
                                output.append(" ").append(val);
                            }
                        }
                    }
                }
                if (!String.valueOf(integrase.getText()).equals("")){
                    String[] intInputs = String.valueOf(integrase.getText()).split(" ");
                    if (intInputs.length > 0){
                        for (String val : intInputs) {
                            if (val.length() > 2) {
                                mutations.add("IN:" + val);
                                output.append(" ").append(val);
                            }
                        }
                    }
                }
//                Toast.makeText(getActivity(), output.toString(), Toast.LENGTH_LONG).show();
//                Toast.makeText(getActivity(), mutations.toString(), Toast.LENGTH_LONG).show();
                fetchMutationData(mutations);
            }
        });
    }
    void fetchMutationData(List<String> myMutations) {
        if (myMutations.size() == 0) {
            Toast.makeText(getActivity(), "No values found", Toast.LENGTH_LONG).show();
        } else {
            myApolloClient.query(MutationAnalysisRequestQuery.builder().mutations(myMutations).build())
                    .enqueue(new ApolloCall.Callback<MutationAnalysisRequestQuery.Data>() {
                        @Override
                        public void onResponse(@NotNull Response<MutationAnalysisRequestQuery.Data> response) {
                            getActivity().runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            List<MutationAnalysisRequestQuery.DrugResistance> load = response.getData().mutationsAnalysis().drugResistance();

                                            progDialog.dismiss();
//                                            transcripase.setText(response.getData().mutationsAnalysis()
//                                                    .drugResistance().toString());
                                            if (load != null){
                                                Paper.book().write("MUTRESULT",load);
                                                Paper.book().write("DATA",response.getData());
                                            }
                                            myMutations.clear();
                                            Intent intent = new Intent(getActivity(), MutationResultActivity.class);
                                            startActivity(intent);
//                                            getActivity().finish();
                                        }
                                    }
                            );
                        }

                        @Override
                        public void onFailure(@NotNull ApolloException e) {
                            getActivity().runOnUiThread(
                                    new Runnable() {
                                        @Override
                                        public void run() {
                                            myMutations.clear();
                                            progDialog.dismiss();
//                                            transcripase.setText(e.getMessage());
                                            errorDialog.show();
                                        }
                                    }
                            );
                        }
                    });
        }
    }
    private boolean valid(EditText rt, EditText pr, EditText in ) {
        boolean valid = true;
        String reverse = rt.getText().toString();
        String protase = pr.getText().toString();
        String incripase = in.getText().toString();

        if (reverse.isEmpty() && protase.isEmpty() && incripase.isEmpty()) {
//            rt.setError("Select a mutation for the Reverse Transcripase Category!");
            valid = false;
        }
        return valid;
    }
}