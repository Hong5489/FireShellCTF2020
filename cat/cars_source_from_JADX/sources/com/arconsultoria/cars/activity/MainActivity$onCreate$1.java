package com.arconsultoria.cars.activity;

import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import com.arconsultoria.cars.C0452R;
import com.arconsultoria.cars.domain.Car;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\"\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\t\n\u0000\u0010\u0000\u001a\u00020\u00012\u0016\u0010\u0002\u001a\u0012\u0012\u0002\b\u0003 \u0004*\b\u0012\u0002\b\u0003\u0018\u00010\u00030\u00032\u000e\u0010\u0005\u001a\n \u0004*\u0004\u0018\u00010\u00060\u00062\u0006\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\n¢\u0006\u0002\b\u000b"}, mo6929d2 = {"<anonymous>", "", "<anonymous parameter 0>", "Landroid/widget/AdapterView;", "kotlin.jvm.PlatformType", "<anonymous parameter 1>", "Landroid/view/View;", "position", "", "<anonymous parameter 3>", "", "onItemClick"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: MainActivity.kt */
final class MainActivity$onCreate$1 implements OnItemClickListener {
    final /* synthetic */ MainActivity this$0;

    MainActivity$onCreate$1(MainActivity mainActivity) {
        this.this$0 = mainActivity;
    }

    public final void onItemClick(AdapterView<?> $noName_0, View $noName_1, int position, long $noName_3) {
        ListView listView = (ListView) this.this$0._$_findCachedViewById(C0452R.C0454id.listView);
        Intrinsics.checkExpressionValueIsNotNull(listView, "listView");
        Object item = listView.getAdapter().getItem(position);
        if (item != null) {
            Car car = (Car) item;
            Intent intent = new Intent(this.this$0.getBaseContext(), DetailsActivity.class);
            intent.putExtra("id", car.getId());
            this.this$0.startActivity(intent);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type com.arconsultoria.cars.domain.Car");
    }
}
