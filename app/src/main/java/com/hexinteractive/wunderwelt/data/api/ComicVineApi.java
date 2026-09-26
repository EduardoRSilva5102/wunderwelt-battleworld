package com.hexinteractive.wunderwelt.data.api;
import com.hexinteractive.wunderwelt.data.model.Character;
import com.hexinteractive.wunderwelt.data.model.ComicVineDetailResponse;
import com.hexinteractive.wunderwelt.data.model.ComicVineListResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ComicVineApi {
    @GET("search/?resources=character&limit=50&field_list=id,name,deck")
    Call<ComicVineListResponse<Character>> search(@Query("query") String query);
    @GET("character/4005-{id}/?field_list=id,name,deck,powers,image,site_detail_url,first_appeared_in_issue")
    Call<ComicVineDetailResponse<Character>> character(@Path("id") long id);
}
