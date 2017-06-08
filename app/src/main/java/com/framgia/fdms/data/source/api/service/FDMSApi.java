package com.framgia.fdms.data.source.api.service;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import com.framgia.fdms.data.source.api.request.DeviceRequest;
import java.util.List;
import java.util.Map;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import rx.Observable;

public interface FDMSApi {
    @POST("api/v1/sessions")
    Observable<Respone<User>> login(@Query("user[email]") String userName,
            @Query("user[password]") String passWord);

    @GET("api/v1/devices")
    Observable<Respone<List<Device>>> getListDevices(@QueryMap Map<String, String> parrams);

    @GET("api/v1/device_categories")
    Observable<Respone<List<Category>>> getListCategory();

    @GET("api/v1/device_status")
    Observable<Respone<List<Status>>> getListStatus();

    @GET("api/v1/device_code")
    Observable<Respone<Device>> getDeviceByQrCode(@Query("printed_code") String qrCode);

    @Multipart
    @POST("api/v1/devices")
    Observable<Respone<Device>> uploadDevice(@PartMap Map<String, RequestBody> parrams,
            @Part MultipartBody.Part picture);

    @Multipart
    @PATCH("api/v1/devices/{id}")
    Observable<Respone<Device>> updateDevice(@Path("id") int id,
            @PartMap Map<String, RequestBody> params, @Part MultipartBody.Part picture);

    @GET("/api/v1/request_dashboard")
    Observable<Respone<List<Dashboard>>> getDashboardRequest();

    @GET("api/v1/device_dashboard")
    Observable<Respone<List<Dashboard>>> getDashboardDevice();

    @GET("api/v1/requests")
    Observable<Respone<List<Request>>> getRequests(@QueryMap Map<String, Integer> params);

    @GET("api/v1/request_status")
    Observable<Respone<List<Status>>> getListStatusRequest();

    @GET("api/v1/user_group")
    Observable<Respone<List<Status>>> getListRelative();

    @GET("api/v1/user_assign")
    Observable<Respone<List<Status>>> getListAssign();

    @POST("api/v1/requests")
    @FormUrlEncoded
    Observable<Respone<Request>> registerRequest(@FieldMap Map<String, String> params,
            @Field("request[request_details_attributes]") List<DeviceRequest> deviceRequests);

    @GET("api/v1/device_code")
    Observable<Respone<Device>> getDevice(@Query("device_id") int deviceId);

    @GET("/api/v1/request_dashboard")
    Observable<Respone<List<Request>>> getTopRequest(@Query("top_requests") int topRequest);

    @GET("api/v1/device_dashboard")
    Observable<Respone<List<Device>>> getTopDevice(@Query("top_devices") int topDevice);

    @PATCH("/api/v1/requests/{id}")
    @FormUrlEncoded
    Observable<Respone<Request>> updateActionRequest(@Path("id") int requestId,
            @Field("request[request_status_id]") int actionId);

    @GET("api/v1/device_history/{id}")
    Observable<Respone<List<DeviceHistoryDetail>>> getDeviceDetailHistory(@Path("id") int deviceId);
}
