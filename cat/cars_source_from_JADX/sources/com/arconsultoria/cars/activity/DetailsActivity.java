package com.arconsultoria.cars.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import com.arconsultoria.cars.C0452R;
import com.arconsultoria.cars.rest.Rest;
import java.util.HashMap;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;
import retrofit2.Retrofit.Builder;
import retrofit2.converter.gson.GsonConverterFactory;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u0012\u0010\u0007\u001a\u00020\u00042\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\n\u001a\u00020\u000bH\u0016¨\u0006\f"}, mo6929d2 = {"Lcom/arconsultoria/cars/activity/DetailsActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "getCar", "", "id", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onSupportNavigateUp", "", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: DetailsActivity.kt */
public final class DetailsActivity extends AppCompatActivity {
    private HashMap _$_findViewCache;

    public void _$_clearFindViewByIdCache() {
        HashMap hashMap = this._$_findViewCache;
        if (hashMap != null) {
            hashMap.clear();
        }
    }

    public View _$_findCachedViewById(int i) {
        if (this._$_findViewCache == null) {
            this._$_findViewCache = new HashMap();
        }
        View view = (View) this._$_findViewCache.get(Integer.valueOf(i));
        if (view != null) {
            return view;
        }
        View findViewById = findViewById(i);
        this._$_findViewCache.put(Integer.valueOf(i), findViewById);
        return findViewById;
    }

    /* access modifiers changed from: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView((int) C0452R.layout.activity_details);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null) {
            Intrinsics.throwNpe();
        }
        actionBar.setTitle((CharSequence) "Details");
        actionBar.setDisplayHomeAsUpEnabled(true);
        getCar(getIntent().getIntExtra("id", 0));
        ((Button) _$_findCachedViewById(C0452R.C0454id.btn_comment)).setOnClickListener(new DetailsActivity$onCreate$1(this));
    }

    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public final void getCar(int id) {
        ((Rest) new Builder().baseUrl(getResources().getString(C0452R.string.url_api)).addConverterFactory(GsonConverterFactory.create()).build().create(Rest.class)).getCar(id).enqueue(new DetailsActivity$getCar$1(this));
    }
}
