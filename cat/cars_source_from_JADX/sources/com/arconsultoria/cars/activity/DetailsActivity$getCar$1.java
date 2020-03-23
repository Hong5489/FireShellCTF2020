package com.arconsultoria.cars.activity;

import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.arconsultoria.cars.C0452R;
import com.arconsultoria.cars.domain.Car;
import com.squareup.picasso.Picasso;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J$\u0010\t\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016¨\u0006\f"}, mo6929d2 = {"com/arconsultoria/cars/activity/DetailsActivity$getCar$1", "Lretrofit2/Callback;", "Lcom/arconsultoria/cars/domain/Car;", "onFailure", "", "call", "Lretrofit2/Call;", "t", "", "onResponse", "response", "Lretrofit2/Response;", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: DetailsActivity.kt */
public final class DetailsActivity$getCar$1 implements Callback<Car> {
    final /* synthetic */ DetailsActivity this$0;

    DetailsActivity$getCar$1(DetailsActivity $outer) {
        this.this$0 = $outer;
    }

    public void onFailure(Call<Car> call, Throwable t) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(t, "t");
        Toast.makeText(this.this$0.getBaseContext(), "Error retrieving car!", 1).show();
    }

    public void onResponse(Call<Car> call, Response<Car> response) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.code() != 200) {
            Toast.makeText(this.this$0.getBaseContext(), "Error retrieving car!", 1).show();
            return;
        }
        TextView textView = (TextView) this.this$0._$_findCachedViewById(C0452R.C0454id.details_name);
        Intrinsics.checkExpressionValueIsNotNull(textView, "details_name");
        Car car = (Car) response.body();
        String str = null;
        textView.setText(car != null ? car.getName() : null);
        TextView textView2 = (TextView) this.this$0._$_findCachedViewById(C0452R.C0454id.details_year);
        Intrinsics.checkExpressionValueIsNotNull(textView2, "details_year");
        Car car2 = (Car) response.body();
        textView2.setText(String.valueOf(car2 != null ? Integer.valueOf(car2.getYear()) : null));
        Picasso picasso = Picasso.get();
        Car car3 = (Car) response.body();
        if (car3 != null) {
            str = car3.getImage();
        }
        picasso.load(str).into((ImageView) this.this$0._$_findCachedViewById(C0452R.C0454id.details_image));
    }
}
