package com.develrm.f1historicalstandings.api;


import com.develrm.f1historicalstandings.xml.MRData;
import com.develrm.f1historicalstandings.xml.MRDataRaces;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIInterface {

    @GET("api/f1/{year}/{round}/driverStandings")
    Call<MRData> getDriverStandings(
            @Path("year") String year,
            @Path("round") String round
    );

    @GET("api/f1/{year}/races")
    Call<MRDataRaces> getRacesByYear(
            @Path("year") String year
    );
}