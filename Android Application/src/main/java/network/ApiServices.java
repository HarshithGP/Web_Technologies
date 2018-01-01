package com.harshith.hw9.network;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.harshith.hw9.models.Bill;
import com.harshith.hw9.models.BillsResponseModel;
import com.harshith.hw9.models.Committee;
import com.harshith.hw9.models.CommitteeResposeModel;
import com.harshith.hw9.models.Legislator;
import com.harshith.hw9.models.LegislatorsResponseModel;

import java.lang.ref.WeakReference;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * Created by yash on 25-11-2016.
 */

public class ApiServices {

	WeakReference<ApiResponseCallbacks> apiResponseCallbacks;

	private final String API_GATEWAY_BASE_URL="http://104.198.0.197:8080/";

	private final String LEGISLATORS_API_END_POINT = "http://104.198.0.197:8080/";

	private final String BILLS_API_END_POINT = "http://104.198.0.197:8080/bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50";

	private final String COMMITTEE_API_END_POINT = "http://104.198.0.197:8080/committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all";

	public ApiServices(Fragment fragment) {
		apiResponseCallbacks=new WeakReference<ApiResponseCallbacks>((ApiResponseCallbacks)fragment);
	}

	public void fetchLegislators() {
		ApiCalls apiCalls=NetworkManager.getInstance().createService(ApiCalls.class,API_GATEWAY_BASE_URL);
		Call apiResponse=apiCalls.getLegislators("legislators?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all");
		Callback<LegislatorsResponseModel> callback= new Callback<LegislatorsResponseModel>() {
			@Override
			public void onResponse(Call<LegislatorsResponseModel> call, Response<LegislatorsResponseModel> response) {
				if(response.isSuccessful()) {
					apiResponseCallbacks.get().onFetchLegislatorsSuccessful(response.body().getResult());
				} else {
					apiResponseCallbacks.get().onFetchLegislatorsFailure();
				}
			}

			@Override
			public void onFailure(Call<LegislatorsResponseModel> call, Throwable t) {
				apiResponseCallbacks.get().onFetchLegislatorsFailure();
			}
		};
		apiResponse.enqueue(callback);
	}

	public void fetchBills() {
		ApiCalls apiCalls = NetworkManager.getInstance().createService(ApiCalls.class,API_GATEWAY_BASE_URL);
		final Call apiResponse = apiCalls.getBills("bills?apikey=apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=50");
		Callback<BillsResponseModel> callback =  new Callback<BillsResponseModel>() {
			@Override
			public void onResponse(Call<BillsResponseModel> call, Response<BillsResponseModel> response) {
				if(response.isSuccessful()) {
					apiResponseCallbacks.get().onFetchBillsSuccessful(response.body().getResults());
				} else {
					apiResponseCallbacks.get().onFetchBillsFailure();
				}
			}

			@Override
			public void onFailure(Call<BillsResponseModel> call, Throwable t) {
				apiResponseCallbacks.get().onFetchBillsFailure();
			}
		};
		apiResponse.enqueue(callback);
	}

	public void fetchCommittees() {
		ApiCalls apiCalls = NetworkManager.getInstance().createService(ApiCalls.class,API_GATEWAY_BASE_URL);
		final Call apiResponse = apiCalls.getCommittees("committees?apikey=b2aff0a1fbed4ac08b2daefb56f7c9a4&per_page=all");
		Callback<CommitteeResposeModel> callback =  new Callback<CommitteeResposeModel>() {
			@Override
			public void onResponse(Call<CommitteeResposeModel> call, Response<CommitteeResposeModel> response) {
				if(response.isSuccessful()) {
					apiResponseCallbacks.get().onFetchCommitteesSuccessful(response.body().getResults());
				} else {
					apiResponseCallbacks.get().onFetchCommitteesFailure();
				}
			}

			@Override
			public void onFailure(Call<CommitteeResposeModel> call, Throwable t) {
				apiResponseCallbacks.get().onFetchCommitteesFailure();
			}
		};
		apiResponse.enqueue(callback);
	}

	public interface ApiResponseCallbacks {
		void onFetchLegislatorsSuccessful(List<Legislator> legislatorList);
		void onFetchLegislatorsFailure();
		void onFetchBillsSuccessful(List<Bill> billList);
		void onFetchBillsFailure();
		void onFetchCommitteesSuccessful(List<Committee> committeeList);
		void onFetchCommitteesFailure();
	}

	public interface ApiCalls {

		@GET
		Call<LegislatorsResponseModel> getLegislators(@Url String url);

		@GET
		Call<BillsResponseModel> getBills(@Url String url);

		@GET
		Call<CommitteeResposeModel> getCommittees(@Url String url);
	}
}
