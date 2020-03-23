package com.arconsultoria.cars.activity;

import android.view.View;
import android.view.View.OnClickListener;
import kotlin.Metadata;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0010\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0010\u0000\u001a\u00020\u00012\u000e\u0010\u0002\u001a\n \u0004*\u0004\u0018\u00010\u00030\u0003H\n¢\u0006\u0002\b\u0005"}, mo6929d2 = {"<anonymous>", "", "it", "Landroid/view/View;", "kotlin.jvm.PlatformType", "onClick"}, mo6930k = 3, mo6931mv = {1, 1, 15})
/* compiled from: CommentActivity.kt */
final class CommentActivity$onCreate$1 implements OnClickListener {
    final /* synthetic */ CommentActivity this$0;

    CommentActivity$onCreate$1(CommentActivity commentActivity) {
        this.this$0 = commentActivity;
    }

    public final void onClick(View it) {
        this.this$0.send_comment();
    }
}
