package com.framgia.fdms.data.source.api.service;

import com.framgia.fdms.data.model.Category;
import com.framgia.fdms.data.model.Dashboard;
import com.framgia.fdms.data.model.Device;
import com.framgia.fdms.data.model.DeviceHistoryDetail;
import com.framgia.fdms.data.model.DeviceUsingHistory;
import com.framgia.fdms.data.model.Producer;
import com.framgia.fdms.data.model.Request;
import com.framgia.fdms.data.model.Respone;
import com.framgia.fdms.data.model.Status;
import com.framgia.fdms.data.model.User;
import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
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
    @POST("api/sessions")
    Observable<Respone<User>> login(@Query("user[email]") String userName,
                                    @Query("user[password]") String passWord);
    @GET("api/devices")
    Observable<Respone<List<Device>>> getListDevices(@QueryMap Map<String, String> parrams);
    @GET("api/device_categories")
    Observable<Respone<List<Category>>> getListCategory();
    @GET("api/device_statuses")
    Observable<Respone<List<Status>>> getListStatus();
    @GET("api/device_codes")
    Observable<Respone<Device>> getDeviceByQrCode(@Query("printed_code") String qrCode);
    @Multipart
    @POST("api/devices")
    Observable<Respone<Device>> uploadDevice(@PartMap Map<String, RequestBody> parrams,
                                             @Part MultipartBody.Part picture);
    @Multipart
    @PATCH("api/devices/{id}")
    Observable<Respone<Device>> updateDevice(@Path("id") int id,
                                             @PartMap Map<String, RequestBody> params,
                                             @Part MultipartBody.Part picture);
    @GET("/api/request_dashboards")
    Observable<Respone<List<Dashboard>>> getDashboardRequest();
    @GET("api/device_dashboards")
    Observable<Respone<List<Dashboard>>> getDashboardDevice();
    @GET("api/requests")
    Observable<Respone<List<Request>>> getRequests(@QueryMap Map<String, Integer> params);
    @GET("api/request_statuses")
    Observable<Respone<List<Status>>> getListStatusRequest();
    @GET("api/user_groups")
    Observable<Respone<List<Status>>> getListRelative();
    @GET("api/user_assigns")
    Observable<Respone<List<Status>>> getListAssign();
    @POST("api/requests")
    Observable<Respone<Request>> registerRequest(@QueryMap Map<String, String> params);
    @GET("api/device_codes")
    Observable<Respone<Device>> getDevice(@Query("device_id") int deviceId);
    @GET("/api/request_dashboards")
    Observable<Respone<List<Request>>> getTopRequest(@Query("top_requests") int topRequest);
    @GET("api/device_dashboards")
    Observable<Respone<List<Device>>> getTopDevice(@Query("top_devices") int topDevice);
    @PATCH("/api/requests/{id}")
    @FormUrlEncoded
    Observable<Respone<Request>> updateActionRequest(@Path("id") int requestId,
                                                     @Field("request[request_status_id]")
                                                         int actionId);
    @GET("api/device_historys/{id}")
    Observable<Respone<List<DeviceHistoryDetail>>> getDeviceDetailHistory(@Path("id") int deviceId);
    @GET("api/device_usings/{id}")
    Observable<Respone<List<DeviceUsingHistory>>> getDeviceUsingHistory(@Path("id") int deviceId);
    @GET("api/branches")
    Observable<Respone<List<Status>>> getListBranch();
    @GET("api/device_codes/show")
    Observable<Respone<Device>> getDeviceCode(@Query("device_category_id") int deviceCategoryId,
                                              @Query("branch") int branchId);
    @GET("api/user_borrows")
    Observable<Respone<List<Device>>> getDevicesBorrow();
    @PATCH("/api/requests/{id}")
    Observable<Respone<Request>> updateRequest(@Path("id") int requestId,
                                               @QueryMap Map<String, String> params);
    @POST("api/assignments")
    Observable<Respone<Request>> registerAssignment(@QueryMap Map<String, String> params);
    @GET("api/requests/{id}")
    Observable<Respone<Request>> getRequest(@Path("id") int requetsId);
    // TODO: 05/07/2017  later
    @GET("api/makers")
    Observable<Respone<List<Producer>>> getMakers(@QueryMap Map<String, Integer> params);
}
