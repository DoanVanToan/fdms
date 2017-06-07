package com.framgia.fdms.data.source.api.error;

import android.support.annotation.Nullable;
import com.framgia.fdms.data.model.Respone;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public final class BaseException extends RuntimeException {
    @Type
    private final String mType;
    @Nullable
    private Response mResponse;

    private BaseException(@Type String type, Throwable cause) {
        super(cause.getMessage(), cause);
        this.mType = type;
    }

    private BaseException(@Type String type, @Nullable Response response) {
        this.mType = type;
        this.mResponse = response;
    }

    public static BaseException toNetworkError(Throwable cause) {
        return new BaseException(Type.NETWORK, cause);
    }

    public static BaseException toHttpError(Response response) {
        return new BaseException(Type.HTTP, response);
    }

    public static BaseException toUnexpectedError(Throwable cause) {
        return new BaseException(Type.UNEXPECTED, cause);
    }

    public static BaseException toServerError(Throwable cause) {
        return new BaseException(Type.SERVER, cause);
    }

    @Type
    public String getErrorType() {
        return mType;
    }

    public String getMessage() {
        switch (mType) {
            case Type.SERVER:
                try {
                    HttpException httpException = (HttpException) getCause();
                    Response response = httpException.response();
                    String errorResponse = response.errorBody().string();
                    if (errorResponse != null) {
                        Respone error = null;
                        try {
                            error = new Gson().fromJson(errorResponse, Respone.class);
                        } catch (JsonSyntaxException e) {
                            e.printStackTrace();
                        }
                        if (error != null) {
                            return error.getMessage();
                        }
                    }
                } catch (IOException e) {
                }
                return "Error";
            case Type.NETWORK:
                return getNetworkErrorMessage(getCause());
            case Type.HTTP:
                if (mResponse != null) {
                    return getHttpErrorMessage(mResponse.code());
                }
                return "Error";
            default:
                return "Error";
        }
    }

    private String getNetworkErrorMessage(Throwable throwable) {
        if (throwable instanceof SocketTimeoutException) {
            return throwable.getMessage();
        }
        if (throwable instanceof UnknownHostException) {
            return throwable.getMessage();
        }
        if (throwable instanceof IOException) {
            return throwable.getMessage();
        }
        return throwable.getMessage();
    }

    private String getHttpErrorMessage(int httpCode) {
        if (httpCode >= 300 && httpCode <= 308) {
            // Redirection
            return "It was transferred to a different URL. I'm sorry for causing you trouble";
        }
        if (httpCode >= 400 && httpCode <= 451) {
            // Client error
            return "An error occurred on the application side. Please try again later!";
        }
        if (httpCode >= 500 && httpCode <= 511) {
            // Server error
            return "A server error occurred. Please try again later!";
        }
        // Unofficial error
        return "An error occurred. Please try again later!";
    }
}
