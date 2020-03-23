package com.arconsultoria.cars.domain;

import java.io.Serializable;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000&\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\b\b\u0018\u00002\u00020\u0001B%\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0005¢\u0006\u0002\u0010\bJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0005HÆ\u0003J1\u0010\u0019\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u0005HÆ\u0001J\u0013\u0010\u001a\u001a\u00020\u001b2\b\u0010\u001c\u001a\u0004\u0018\u00010\u001dHÖ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÖ\u0001J\t\u0010\u001f\u001a\u00020\u0005HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\t\u0010\n\"\u0004\b\u000b\u0010\fR\u001a\u0010\u0007\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\r\u0010\u000e\"\u0004\b\u000f\u0010\u0010R\u001a\u0010\u0004\u001a\u00020\u0005X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0011\u0010\u000e\"\u0004\b\u0012\u0010\u0010R\u001a\u0010\u0006\u001a\u00020\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0013\u0010\n\"\u0004\b\u0014\u0010\f¨\u0006 "}, mo6929d2 = {"Lcom/arconsultoria/cars/domain/Car;", "Ljava/io/Serializable;", "id", "", "name", "", "year", "image", "(ILjava/lang/String;ILjava/lang/String;)V", "getId", "()I", "setId", "(I)V", "getImage", "()Ljava/lang/String;", "setImage", "(Ljava/lang/String;)V", "getName", "setName", "getYear", "setYear", "component1", "component2", "component3", "component4", "copy", "equals", "", "other", "", "hashCode", "toString", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: Car.kt */
public final class Car implements Serializable {

    /* renamed from: id */
    private int f60id;
    private String image;
    private String name;
    private int year;

    public static /* synthetic */ Car copy$default(Car car, int i, String str, int i2, String str2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = car.f60id;
        }
        if ((i3 & 2) != 0) {
            str = car.name;
        }
        if ((i3 & 4) != 0) {
            i2 = car.year;
        }
        if ((i3 & 8) != 0) {
            str2 = car.image;
        }
        return car.copy(i, str, i2, str2);
    }

    public final int component1() {
        return this.f60id;
    }

    public final String component2() {
        return this.name;
    }

    public final int component3() {
        return this.year;
    }

    public final String component4() {
        return this.image;
    }

    public final Car copy(int i, String str, int i2, String str2) {
        Intrinsics.checkParameterIsNotNull(str, "name");
        Intrinsics.checkParameterIsNotNull(str2, "image");
        return new Car(i, str, i2, str2);
    }

    public boolean equals(Object obj) {
        if (this != obj) {
            if (obj instanceof Car) {
                Car car = (Car) obj;
                if ((this.f60id == car.f60id) && Intrinsics.areEqual((Object) this.name, (Object) car.name)) {
                    if (!(this.year == car.year) || !Intrinsics.areEqual((Object) this.image, (Object) car.image)) {
                        return false;
                    }
                }
            }
            return false;
        }
        return true;
    }

    public int hashCode() {
        int i = this.f60id * 31;
        String str = this.name;
        int i2 = 0;
        int hashCode = (((i + (str != null ? str.hashCode() : 0)) * 31) + this.year) * 31;
        String str2 = this.image;
        if (str2 != null) {
            i2 = str2.hashCode();
        }
        return hashCode + i2;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Car(id=");
        sb.append(this.f60id);
        sb.append(", name=");
        sb.append(this.name);
        sb.append(", year=");
        sb.append(this.year);
        sb.append(", image=");
        sb.append(this.image);
        sb.append(")");
        return sb.toString();
    }

    public Car(int id, String name2, int year2, String image2) {
        Intrinsics.checkParameterIsNotNull(name2, "name");
        Intrinsics.checkParameterIsNotNull(image2, "image");
        this.f60id = id;
        this.name = name2;
        this.year = year2;
        this.image = image2;
    }

    public final int getId() {
        return this.f60id;
    }

    public final void setId(int i) {
        this.f60id = i;
    }

    public final String getName() {
        return this.name;
    }

    public final void setName(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.name = str;
    }

    public final int getYear() {
        return this.year;
    }

    public final void setYear(int i) {
        this.year = i;
    }

    public final String getImage() {
        return this.image;
    }

    public final void setImage(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.image = str;
    }
}
