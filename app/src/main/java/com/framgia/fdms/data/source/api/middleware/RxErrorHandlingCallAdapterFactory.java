package com.framgia.fdms.data.source.api.middleware;

import com.framgia.fdms.data.source.api.error.BaseException;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.HttpException;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Observable;
import rx.functions.Func1;

/**
 * ErrorHandling:
 * http://bytes.babbel.com/en/articles/2016-03-16-retrofit2-rxjava-error-handling.html
 * This class only for Call in mRetrofit 2
 */
public final class RxErrorHandlingCallAdapterFactory extends CallAdapter.Factory {
    private static final String TAG = RxErrorHandlingCallAdapterFactory.class.getName();
    private final RxJavaCallAdapterFactory mOriginal;

    private RxErrorHandlingCallAdapterFactory() {
        mOriginal = RxJavaCallAdapterFactory.create();
    }

    public static CallAdapter.Factory create() {
        return new RxErrorHandlingCallAdapterFactory();
    }

    @Override
    public CallAdapter<?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        return new RxCallAdapterWrapper(retrofit, mOriginal.get(returnType, annotations, retrofit));
    }

    /**
     * RxCallAdapterWrapper
     */
    private static class RxCallAdapterWrapper implements CallAdapter<Observable<?>> {
        private final Retrofit mRetrofit;
        private final CallAdapter<?> mWrapped;

        RxCallAdapterWrapper(Retrofit retrofit, CallAdapter<?> wrapped) {
            this.mRetrofit = retrofit;
            this.mWrapped = wrapped;
        }

        @Override
        public Type responseType() {
            return mWrapped.responseType();
        }

        @SuppressWarnings("unchecked")
        @Override
        public <R> Observable<?> adapt(Call<R> call) {
            return ((Observable) mWrapped.adapt(call)).onErrorResumeNext(
                    new Func1<Throwable, Observable>() {
                        @Override
                        public Observable call(Throwable throwable) {
                            return Observable.error(convertToBaseException(throwable));
                        }
                    });
        }

        private BaseException convertToBaseException(Throwable throwable) {
            if (throwable instanceof BaseException) {
                return (BaseException) throwable;
            }
            if (throwable instanceof IOException) {
                return BaseException.toNetworkError(throwable);
            }
            if (throwable instanceof HttpException) {
                HttpException httpException = (HttpException) throwable;
                Response response = httpException.response();
                if (response.errorBody() != null) {
                    return BaseException.toServerError(throwable);
                } else {
                    return BaseException.toHttpError(response);
                }
            }
            return BaseException.toUnexpectedError(throwable);
        }
    }
}
