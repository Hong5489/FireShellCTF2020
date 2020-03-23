package com.arconsultoria.cars.rest;

import com.arconsultoria.cars.domain.Car;
import com.arconsultoria.cars.domain.Comment;
import com.arconsultoria.cars.domain.CommentResponse;
import java.util.List;
import kotlin.Metadata;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\bf\u0018\u00002\u00020\u0001J\u0018\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0001\u0010\u0005\u001a\u00020\u0006H'J\u0014\u0010\u0007\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\b0\u0003H'J\u0018\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00032\b\b\u0001\u0010\u000b\u001a\u00020\fH'¨\u0006\r"}, mo6929d2 = {"Lcom/arconsultoria/cars/rest/Rest;", "", "getCar", "Lretrofit2/Call;", "Lcom/arconsultoria/cars/domain/Car;", "id", "", "getCars", "", "postComment", "Lcom/arconsultoria/cars/domain/CommentResponse;", "comment", "Lcom/arconsultoria/cars/domain/Comment;", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Rest.kt */
public interface Rest {
    @GET("/car/{id}")
    Call<Car> getCar(@Path("id") int i);

    @GET("/cars")
    Call<List<Car>> getCars();

    @POST("/comment")
    Call<CommentResponse> postComment(@Body Comment comment);
}
