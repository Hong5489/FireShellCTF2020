package com.arconsultoria.cars.activity;

import android.widget.Toast;
import androidx.core.app.NotificationCompat;
import com.arconsultoria.cars.domain.CommentResponse;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000)\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0003\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000*\u0001\u0000\b\n\u0018\u00002\b\u0012\u0004\u0012\u00020\u00020\u0001J\u001e\u0010\u0003\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016J$\u0010\t\u001a\u00020\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00020\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00020\u000bH\u0016¨\u0006\f"}, mo6929d2 = {"com/arconsultoria/cars/activity/CommentActivity$send_comment$1", "Lretrofit2/Callback;", "Lcom/arconsultoria/cars/domain/CommentResponse;", "onFailure", "", "call", "Lretrofit2/Call;", "t", "", "onResponse", "response", "Lretrofit2/Response;", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: CommentActivity.kt */
public final class CommentActivity$send_comment$1 implements Callback<CommentResponse> {
    final /* synthetic */ CommentActivity this$0;

    CommentActivity$send_comment$1(CommentActivity $outer) {
        this.this$0 = $outer;
    }

    public void onFailure(Call<CommentResponse> call, Throwable t) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(t, "t");
        Toast.makeText(this.this$0.getBaseContext(), "Error posting comment!", 1).show();
    }

    public void onResponse(Call<CommentResponse> call, Response<CommentResponse> response) {
        Intrinsics.checkParameterIsNotNull(call, NotificationCompat.CATEGORY_CALL);
        Intrinsics.checkParameterIsNotNull(response, "response");
        if (response.code() != 200) {
            Toast.makeText(this.this$0.getBaseContext(), "Error posting comment!", 1).show();
            return;
        }
        CommentResponse commentResponse = (CommentResponse) response.body();
        Toast.makeText(this.this$0.getBaseContext(), commentResponse != null ? commentResponse.getMessage() : null, 1).show();
        this.this$0.finish();
    }
}
