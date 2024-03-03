package com.develrm.f1historicalstandings;

import static android.widget.NumberPicker.OnScrollListener.SCROLL_STATE_IDLE;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import com.develrm.f1historicalstandings.adapter.StandingsAdapter;
import com.develrm.f1historicalstandings.api.APIInterface;
import com.develrm.f1historicalstandings.api.APIClient;
import com.develrm.f1historicalstandings.xml.DriverStanding;
import com.develrm.f1historicalstandings.xml.MRData;
import com.develrm.f1historicalstandings.xml.MRDataRaces;
import com.develrm.f1historicalstandings.xml.Race;

import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.stream.Format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private Spinner spinnerYear;
    private Spinner spinnerRace;
    private RecyclerView recyclerView;
    private APIInterface apiInterface;
    private StandingsAdapter adapter;
    private ProgressDialog progressDialog;
    private int currentYear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerRace = findViewById(R.id.spinnerRace);
        recyclerView = findViewById(R.id.standings);
        spinnerYear = findViewById(R.id.spinnerYear);


        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                progressDialog.show();
                loadRaces(2024 - pos);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });


        spinnerRace.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener()
        {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
            {
                progressDialog.show();
                loadStandings((int) (currentYear - spinnerYear.getSelectedItemId()),pos + 1);
            }

            public void onNothingSelected(AdapterView<?> parent)
            {

            }
        });

        apiInterface = APIClient.getClient().create(APIInterface.class);

        currentYear = Calendar.getInstance().get(Calendar.YEAR);

        List<String> years = new ArrayList<>();

        for (int i = currentYear; i >= 1950; i--) {
            years.add(String.valueOf(i));
        }

        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, years);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerYear.setAdapter(ad);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading");
        progressDialog.setMessage("Wait while loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();

    }

    private void loadStandings(int year, int round) {

        String xmlString = AssetReader.readXmlFile(getApplicationContext(), "xml_"+ year +"_"+ round +".xml");
        String xmlStringLoaded = loadXml(getApplicationContext(),"xml_"+ year +"_"+ round +".xml");
        try {

            if (xmlString != null) {

                Serializer serializer = new Persister();
                MRData data = serializer.read(MRData.class, new StringReader(xmlString));

                loadRecyclerView(data);

            }else if(!xmlStringLoaded.isEmpty()){

                Serializer serializer = new Persister();
                MRData data = serializer.read(MRData.class, new StringReader(xmlStringLoaded));

                loadRecyclerView(data);

            }else{

                Call<MRData> callStandings = apiInterface.getDriverStandings(String.valueOf(year),String.valueOf(round));

                callStandings.enqueue(new Callback<MRData>() {
                    @Override
                    public void onResponse(Call<MRData> call, Response<MRData> response) {

                        MRData data = response.body();
                        saveXml(getApplicationContext(),"xml_"+ year +"_"+ round +".xml",serializeToXML(data));
                        loadRecyclerView(data);

                    }

                    @Override
                    public void onFailure(Call<MRData> call, Throwable t) {
                        call.cancel();
                        loadRecyclerView(null);
                    }

                });

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    private void loadRecyclerView(MRData data) {

        adapter = new StandingsAdapter(data == null ? new ArrayList<>() : data.getStandingsTable().getStandingsList().getDriverStandings());
        adapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onChanged() {
                progressDialog.dismiss();
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void loadRaces(int year) {

        String xmlString = AssetReader.readXmlFile(getApplicationContext(), "races_" + year +".xml");
        String xmlStringLoaded = loadXml(getApplicationContext(), "races_" + year +".xml");

        try {
            if (xmlString != null) {

                Serializer serializer = new Persister();
                MRDataRaces data = serializer.read(MRDataRaces.class, new StringReader(xmlString));

                loadSpinnerRace(data);

            }else if(!xmlStringLoaded.isEmpty()){

                Serializer serializer = new Persister();
                MRDataRaces data = serializer.read(MRDataRaces.class, new StringReader(xmlStringLoaded));

                loadSpinnerRace(data);

            }else{

                Call<MRDataRaces> call = apiInterface.getRacesByYear(String.valueOf(year));

                call.enqueue(new Callback<MRDataRaces>() {
                    @Override
                    public void onResponse(Call<MRDataRaces> call, Response<MRDataRaces> response) {

                        MRDataRaces data = response.body();
                        saveXml(getApplicationContext(),"races_" + year +".xml",serializeToXML(data));
                        loadSpinnerRace(data);

                    }

                    @Override
                    public void onFailure(Call<MRDataRaces> call, Throwable t) {
                        call.cancel();
                    }
                });

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadSpinnerRace(MRDataRaces data ) {

        List<String> races = new ArrayList<>();
        Date lastDate = new Date();
        for (Race race: data.getRaceTable().getRaces()) {
            if(race.getDate().before(lastDate)){
                races.add(race.getRaceName());
            }else{
                break;
            }
        }

        ArrayAdapter ad = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, races);
        ad.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerRace.setAdapter(ad);
        if(races.size() == 0){
            progressDialog.dismiss();
        }

    }

    private String serializeToXML(MRDataRaces data) {
        StringWriter writer = new StringWriter();

        try {

            Serializer serializer = new Persister(new Format(0));
            serializer.write(data, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return writer.toString();
    }

    private String serializeToXML(MRData data) {
        StringWriter writer = new StringWriter();

        try {

            Serializer serializer = new Persister(new Format(0));
            serializer.write(data, writer);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return writer.toString();
    }


    public static void saveXml(Context context, String filename, String xmlData) {
        FileOutputStream outputStream = null;

        try {
            outputStream = context.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(xmlData.getBytes());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String loadXml(Context context, String filename) {
        FileInputStream inputStream = null;
        BufferedReader reader = null;
        StringBuilder conteudo = new StringBuilder();

        try {
            inputStream = context.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                conteudo.append(line).append("\n");
            }
        } catch (IOException e) {
           e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return conteudo.toString();
    }
}