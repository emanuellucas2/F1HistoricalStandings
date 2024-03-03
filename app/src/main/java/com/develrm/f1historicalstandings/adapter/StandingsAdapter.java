package com.develrm.f1historicalstandings.adapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.develrm.f1historicalstandings.R;
import com.develrm.f1historicalstandings.xml.Driver;
import com.develrm.f1historicalstandings.xml.DriverStanding;

import java.util.List;

public class StandingsAdapter extends RecyclerView.Adapter<StandingsAdapter.StringViewHolder> {

    private List<DriverStanding> dataList;

    public StandingsAdapter(List<DriverStanding> dataList) {
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public StringViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.driver_view, parent, false);
        return new StringViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StringViewHolder holder, int position) {
        DriverStanding driverStanding = dataList.get(position);

        holder.position.setText(String.valueOf(position + 1));
        holder.driverName.setText(driverStanding.getDriver().getGivenName() + " " +
                driverStanding.getDriver().getFamilyName());
        holder.points.setText(driverStanding.getPoints());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    static class StringViewHolder extends RecyclerView.ViewHolder {
        TextView position;
        TextView driverName;
        TextView points;

        StringViewHolder(@NonNull View itemView) {
            super(itemView);
            position = itemView.findViewById(R.id.position);
            driverName = itemView.findViewById(R.id.driver);
            points = itemView.findViewById(R.id.points);
        }
    }
}