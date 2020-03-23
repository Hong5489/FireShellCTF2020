package com.arconsultoria.cars.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.arconsultoria.cars.C0452R;
import com.arconsultoria.cars.domain.Car;
import com.squareup.picasso.Picasso;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000@\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001:\u0001\u0015B\u001b\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005¢\u0006\u0002\u0010\u0007J\b\u0010\b\u001a\u00020\tH\u0016J\u0010\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tH\u0016J\u0010\u0010\r\u001a\u00020\u000e2\u0006\u0010\f\u001a\u00020\tH\u0016J$\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\t2\b\u0010\u0012\u001a\u0004\u0018\u00010\u00102\b\u0010\u0013\u001a\u0004\u0018\u00010\u0014H\u0016R\u000e\u0010\u0002\u001a\u00020\u0003X\u0004¢\u0006\u0002\n\u0000R\u0014\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0016"}, mo6929d2 = {"Lcom/arconsultoria/cars/adapter/CarAdapter;", "Landroid/widget/BaseAdapter;", "context", "Landroid/content/Context;", "dataSource", "", "Lcom/arconsultoria/cars/domain/Car;", "(Landroid/content/Context;Ljava/util/List;)V", "getCount", "", "getItem", "", "position", "getItemId", "", "getView", "Landroid/view/View;", "i", "view", "viewGroup", "Landroid/view/ViewGroup;", "ViewHolder", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: CarAdapter.kt */
public final class CarAdapter extends BaseAdapter {
    private final Context context;
    private final List<Car> dataSource;

    @Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\b\b\u0002\u0018\u00002\u00020\u0001B\u0005¢\u0006\u0002\u0010\u0002R\u001a\u0010\u0003\u001a\u00020\u0004X.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\bR\u001a\u0010\t\u001a\u00020\nX.¢\u0006\u000e\n\u0000\u001a\u0004\b\u000b\u0010\f\"\u0004\b\r\u0010\u000eR\u001a\u0010\u000f\u001a\u00020\nX.¢\u0006\u000e\n\u0000\u001a\u0004\b\u0010\u0010\f\"\u0004\b\u0011\u0010\u000e¨\u0006\u0012"}, mo6929d2 = {"Lcom/arconsultoria/cars/adapter/CarAdapter$ViewHolder;", "", "()V", "image", "Landroid/widget/ImageView;", "getImage", "()Landroid/widget/ImageView;", "setImage", "(Landroid/widget/ImageView;)V", "name", "Landroid/widget/TextView;", "getName", "()Landroid/widget/TextView;", "setName", "(Landroid/widget/TextView;)V", "year", "getYear", "setYear", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
    /* compiled from: CarAdapter.kt */
    private static final class ViewHolder {
        public ImageView image;
        public TextView name;
        public TextView year;

        public final TextView getName() {
            TextView textView = this.name;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("name");
            }
            return textView;
        }

        public final void setName(TextView textView) {
            Intrinsics.checkParameterIsNotNull(textView, "<set-?>");
            this.name = textView;
        }

        public final TextView getYear() {
            TextView textView = this.year;
            if (textView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("year");
            }
            return textView;
        }

        public final void setYear(TextView textView) {
            Intrinsics.checkParameterIsNotNull(textView, "<set-?>");
            this.year = textView;
        }

        public final ImageView getImage() {
            ImageView imageView = this.image;
            if (imageView == null) {
                Intrinsics.throwUninitializedPropertyAccessException("image");
            }
            return imageView;
        }

        public final void setImage(ImageView imageView) {
            Intrinsics.checkParameterIsNotNull(imageView, "<set-?>");
            this.image = imageView;
        }
    }

    public CarAdapter(Context context2, List<Car> dataSource2) {
        Intrinsics.checkParameterIsNotNull(context2, "context");
        Intrinsics.checkParameterIsNotNull(dataSource2, "dataSource");
        this.context = context2;
        this.dataSource = dataSource2;
    }

    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        View view2 = view;
        if (view2 == null) {
            view2 = LayoutInflater.from(this.context).inflate(C0452R.layout.listview_item, viewGroup, false);
            viewHolder = new ViewHolder();
            if (view2 == null) {
                Intrinsics.throwNpe();
            }
            TextView textView = (TextView) view2.findViewById(C0452R.C0454id.name);
            Intrinsics.checkExpressionValueIsNotNull(textView, "view!!.name");
            viewHolder.setName(textView);
            TextView textView2 = (TextView) view2.findViewById(C0452R.C0454id.year);
            Intrinsics.checkExpressionValueIsNotNull(textView2, "view!!.year");
            viewHolder.setYear(textView2);
            ImageView imageView = (ImageView) view2.findViewById(C0452R.C0454id.image);
            Intrinsics.checkExpressionValueIsNotNull(imageView, "view!!.image");
            viewHolder.setImage(imageView);
        } else {
            Object tag = view2.getTag();
            if (tag != null) {
                viewHolder = (ViewHolder) tag;
            } else {
                throw new TypeCastException("null cannot be cast to non-null type com.arconsultoria.cars.adapter.CarAdapter.ViewHolder");
            }
        }
        Car car = (Car) this.dataSource.get(i);
        viewHolder.getName().setText(car.getName());
        viewHolder.getYear().setText(String.valueOf(car.getYear()));
        Picasso.get().load(car.getImage()).into(viewHolder.getImage());
        view2.setTag(viewHolder);
        return view2;
    }

    public Object getItem(int position) {
        return this.dataSource.get(position);
    }

    public long getItemId(int position) {
        return (long) position;
    }

    public int getCount() {
        return this.dataSource.size();
    }
}
