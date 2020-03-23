package com.arconsultoria.cars.domain;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\b\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0002\u0010\u0004J\t\u0010\b\u001a\u00020\u0003HÆ\u0003J\u0013\u0010\t\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u0003HÆ\u0001J\u0013\u0010\n\u001a\u00020\u000b2\b\u0010\f\u001a\u0004\u0018\u00010\u0001HÖ\u0003J\t\u0010\r\u001a\u00020\u000eHÖ\u0001J\t\u0010\u000f\u001a\u00020\u0003HÖ\u0001R\u001a\u0010\u0002\u001a\u00020\u0003X\u000e¢\u0006\u000e\n\u0000\u001a\u0004\b\u0005\u0010\u0006\"\u0004\b\u0007\u0010\u0004¨\u0006\u0010"}, mo6929d2 = {"Lcom/arconsultoria/cars/domain/CommentResponse;", "", "message", "", "(Ljava/lang/String;)V", "getMessage", "()Ljava/lang/String;", "setMessage", "component1", "copy", "equals", "", "other", "hashCode", "", "toString", "app_debug"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: CommentResponse.kt */
public final class CommentResponse {
    private String message;

    public static /* synthetic */ CommentResponse copy$default(CommentResponse commentResponse, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            str = commentResponse.message;
        }
        return commentResponse.copy(str);
    }

    public final String component1() {
        return this.message;
    }

    public final CommentResponse copy(String str) {
        Intrinsics.checkParameterIsNotNull(str, "message");
        return new CommentResponse(str);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:4:0x0010, code lost:
        if (kotlin.jvm.internal.Intrinsics.areEqual((java.lang.Object) r1.message, (java.lang.Object) ((com.arconsultoria.cars.domain.CommentResponse) r2).message) != false) goto L_0x0015;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public boolean equals(java.lang.Object r2) {
        /*
            r1 = this;
            if (r1 == r2) goto L_0x0015
            boolean r0 = r2 instanceof com.arconsultoria.cars.domain.CommentResponse
            if (r0 == 0) goto L_0x0013
            com.arconsultoria.cars.domain.CommentResponse r2 = (com.arconsultoria.cars.domain.CommentResponse) r2
            java.lang.String r0 = r1.message
            java.lang.String r2 = r2.message
            boolean r2 = kotlin.jvm.internal.Intrinsics.areEqual(r0, r2)
            if (r2 == 0) goto L_0x0013
            goto L_0x0015
        L_0x0013:
            r2 = 0
            return r2
        L_0x0015:
            r2 = 1
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.arconsultoria.cars.domain.CommentResponse.equals(java.lang.Object):boolean");
    }

    public int hashCode() {
        String str = this.message;
        if (str != null) {
            return str.hashCode();
        }
        return 0;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("CommentResponse(message=");
        sb.append(this.message);
        sb.append(")");
        return sb.toString();
    }

    public CommentResponse(String message2) {
        Intrinsics.checkParameterIsNotNull(message2, "message");
        this.message = message2;
    }

    public final String getMessage() {
        return this.message;
    }

    public final void setMessage(String str) {
        Intrinsics.checkParameterIsNotNull(str, "<set-?>");
        this.message = str;
    }
}
