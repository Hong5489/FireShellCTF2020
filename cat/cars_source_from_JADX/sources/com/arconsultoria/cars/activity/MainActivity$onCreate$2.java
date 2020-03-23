package com.arconsultoria.cars.activity;

import android.content.Context;
import android.widget.ListView;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.arconsultoria.cars.C0452R;
import com.arconsultoria.cars.adapter.CarAdapter;
import com.arconsultoria.cars.domain.Car;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000-\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001J$\u0010\u0004\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00072\u0006\u0010\b\u001a\u00020\tH\u0016J0\u0010\n\u001a\u00020\u00052\u0012\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u00072\u0012\u0010\u000b\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\fH\u0016¨\u0006\r"}, mo6929d2 = {"com/arconsultoria/cars/activity/MainActivity$onCreate$2", "Lretrofit2/Callback;", "", "Lcom/arconsultoria/cars/domain/Car;", "onFailure", "", "call", "Lretrofit2/Call;", "t", "", "onResponse", "response", "Lretrofit2/Response;", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: MainActivity.kt */
public final class MainActivity$onCreate$2 implements Callback<List<? extends Car>> {
    final /* synthetic */ MainActivity this$0;

    MainActivity$onCreate$2(MainActivity $outer) {
        this.this$0 = $outer;
    }

    public void onFailure(Call<List<Car>> call, Throwable t) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(t, "t");
        Toast.makeText(this.this$0.getBaseContext(), "Error retrieving cars!", 1).show();
    }

    public void onResponse(Call<List<Car>> call, Response<List<Car>> response) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.code() != 200) {
            Toast.makeText(this.this$0.getBaseContext(), "Error retrieving cars!", 1).show();
            return;
        }
        Context baseContext = this.this$0.getBaseContext();
        Intrinsics.checkExpressionValueIsNotNull(baseContext, "baseContext");
        List list = (List) response.body();
        if (list == null) {
            list = CollectionsKt.emptyList();
        }
        CarAdapter adapter = new CarAdapter(baseContext, list);
        ListView listView = (ListView) this.this$0._$_findCachedViewById(C0452R.C0454id.listView);
        Intrinsics.checkExpressionValueIsNotNull(listView, "listView");
        listView.setAdapter(adapter);
    }
}
