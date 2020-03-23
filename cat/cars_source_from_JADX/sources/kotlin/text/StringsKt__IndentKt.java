package kotlin.text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;
import kotlin.sequences.SequencesKt;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u001e\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u000b\u001a!\u0010\u0000\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u0002H\u0002¢\u0006\u0002\b\u0004\u001a\u0011\u0010\u0005\u001a\u00020\u0006*\u00020\u0002H\u0002¢\u0006\u0002\b\u0007\u001a\u0014\u0010\b\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0003\u001a\u00020\u0002\u001aJ\u0010\t\u001a\u00020\u0002*\b\u0012\u0004\u0012\u00020\u00020\n2\u0006\u0010\u000b\u001a\u00020\u00062\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0002\u0012\u0004\u0012\u00020\u00020\u00012\u0014\u0010\r\u001a\u0010\u0012\u0004\u0012\u00020\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001H\b¢\u0006\u0002\b\u000e\u001a\u0014\u0010\u000f\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u0002\u001a\u001e\u0010\u0011\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0010\u001a\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002\u001a\n\u0010\u0013\u001a\u00020\u0002*\u00020\u0002\u001a\u0014\u0010\u0014\u001a\u00020\u0002*\u00020\u00022\b\b\u0002\u0010\u0012\u001a\u00020\u0002¨\u0006\u0015"}, mo6929d2 = {"getIndentFunction", "Lkotlin/Function1;", "", "indent", "getIndentFunction$StringsKt__IndentKt", "indentWidth", "", "indentWidth$StringsKt__IndentKt", "prependIndent", "reindent", "", "resultSizeEstimate", "indentAddFunction", "indentCutFunction", "reindent$StringsKt__IndentKt", "replaceIndent", "newIndent", "replaceIndentByMargin", "marginPrefix", "trimIndent", "trimMargin", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/text/StringsKt")
/* compiled from: Indent.kt */
class StringsKt__IndentKt {
    public static /* synthetic */ String trimMargin$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "|";
        }
        return StringsKt.trimMargin(str, str2);
    }

    public static final String trimMargin(String $this$trimMargin, String marginPrefix) {
        Intrinsics.checkParameterIsNotNull($this$trimMargin, "$this$trimMargin");
        Intrinsics.checkParameterIsNotNull(marginPrefix, "marginPrefix");
        return StringsKt.replaceIndentByMargin($this$trimMargin, "", marginPrefix);
    }

    public static /* synthetic */ String replaceIndentByMargin$default(String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        if ((i & 2) != 0) {
            str3 = "|";
        }
        return StringsKt.replaceIndentByMargin(str, str2, str3);
    }

    public static final String replaceIndentByMargin(String $this$replaceIndentByMargin, String newIndent, String marginPrefix) {
        Collection destination$iv$iv$iv;
        Object obj;
        String str = $this$replaceIndentByMargin;
        String str2 = marginPrefix;
        Intrinsics.checkParameterIsNotNull(str, "$this$replaceIndentByMargin");
        Intrinsics.checkParameterIsNotNull(newIndent, "newIndent");
        Intrinsics.checkParameterIsNotNull(str2, "marginPrefix");
        if (!StringsKt.isBlank(str2)) {
            List lines = StringsKt.lines(str);
            int resultSizeEstimate$iv = $this$replaceIndentByMargin.length() + (newIndent.length() * lines.size());
            List $this$reindent$iv = lines;
            Function1 indentAddFunction$iv = getIndentFunction$StringsKt__IndentKt(newIndent);
            int lastIndex$iv = CollectionsKt.getLastIndex($this$reindent$iv);
            Iterable destination$iv$iv$iv2 = (Collection) new ArrayList();
            int index$iv$iv$iv$iv = 0;
            for (Object item$iv$iv$iv$iv : $this$reindent$iv) {
                int index$iv$iv$iv$iv2 = index$iv$iv$iv$iv + 1;
                if (index$iv$iv$iv$iv < 0) {
                    CollectionsKt.throwIndexOverflow();
                }
                Object value$iv = (String) item$iv$iv$iv$iv;
                int index$iv = index$iv$iv$iv$iv;
                Object obj2 = null;
                if ((index$iv == 0 || index$iv == lastIndex$iv) && StringsKt.isBlank(value$iv)) {
                    destination$iv$iv$iv = destination$iv$iv$iv2;
                } else {
                    String line = value$iv;
                    CharSequence $this$indexOfFirst$iv = line;
                    boolean z = false;
                    int length = $this$indexOfFirst$iv.length();
                    int index$iv2 = 0;
                    while (true) {
                        boolean z2 = z;
                        if (index$iv2 >= length) {
                            index$iv2 = -1;
                            break;
                        } else if ((CharsKt.isWhitespace($this$indexOfFirst$iv.charAt(index$iv2)) ^ 1) != 0) {
                            break;
                        } else {
                            index$iv2++;
                            z = z2;
                        }
                    }
                    if (index$iv2 == -1) {
                        String str3 = line;
                        int i = index$iv;
                        destination$iv$iv$iv = destination$iv$iv$iv2;
                        obj = null;
                    } else {
                        String line2 = line;
                        int i2 = index$iv;
                        destination$iv$iv$iv = destination$iv$iv$iv2;
                        if (StringsKt.startsWith$default(line, marginPrefix, index$iv2, false, 4, null)) {
                            int length2 = marginPrefix.length() + index$iv2;
                            String line3 = line2;
                            if (line3 != null) {
                                obj = line3.substring(length2);
                                Intrinsics.checkExpressionValueIsNotNull(obj, "(this as java.lang.String).substring(startIndex)");
                            } else {
                                throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                            }
                        } else {
                            obj = null;
                        }
                    }
                    if (obj != null) {
                        String str4 = (String) indentAddFunction$iv.invoke(obj);
                        if (str4 != null) {
                            obj2 = str4;
                        }
                    }
                    obj2 = value$iv;
                }
                if (obj2 != null) {
                    destination$iv$iv$iv.add(obj2);
                }
                String str5 = $this$replaceIndentByMargin;
                destination$iv$iv$iv2 = destination$iv$iv$iv;
                index$iv$iv$iv$iv = index$iv$iv$iv$iv2;
                String str6 = marginPrefix;
            }
            String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv$iv2, new StringBuilder(resultSizeEstimate$iv), "\n", null, null, 0, null, null, 124, null)).toString();
            Intrinsics.checkExpressionValueIsNotNull(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
            return sb;
        }
        throw new IllegalArgumentException("marginPrefix must be non-blank string.".toString());
    }

    public static final String trimIndent(String $this$trimIndent) {
        Intrinsics.checkParameterIsNotNull($this$trimIndent, "$this$trimIndent");
        return StringsKt.replaceIndent($this$trimIndent, "");
    }

    public static /* synthetic */ String replaceIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "";
        }
        return StringsKt.replaceIndent(str, str2);
    }

    /* JADX WARNING: Code restructure failed: missing block: B:29:0x0107, code lost:
        if (r0 != null) goto L_0x010d;
     */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static final java.lang.String replaceIndent(java.lang.String r27, java.lang.String r28) {
        /*
            r0 = r27
            java.lang.String r1 = "$this$replaceIndent"
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r0, r1)
            java.lang.String r1 = "newIndent"
            r2 = r28
            kotlin.jvm.internal.Intrinsics.checkParameterIsNotNull(r2, r1)
            r1 = r0
            java.lang.CharSequence r1 = (java.lang.CharSequence) r1
            java.util.List r1 = kotlin.text.StringsKt.lines(r1)
            r3 = r1
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            r4 = 0
            java.util.ArrayList r5 = new java.util.ArrayList
            r5.<init>()
            java.util.Collection r5 = (java.util.Collection) r5
            r6 = r3
            r7 = 0
            java.util.Iterator r8 = r6.iterator()
        L_0x002c:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x0049
            java.lang.Object r9 = r8.next()
            r10 = r9
            java.lang.String r10 = (java.lang.String) r10
            r11 = 0
            r12 = r10
            java.lang.CharSequence r12 = (java.lang.CharSequence) r12
            boolean r12 = kotlin.text.StringsKt.isBlank(r12)
            r10 = r12 ^ 1
            if (r10 == 0) goto L_0x002c
            r5.add(r9)
            goto L_0x002c
        L_0x0049:
            r3 = r5
            java.util.List r3 = (java.util.List) r3
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            r4 = 0
            java.util.ArrayList r5 = new java.util.ArrayList
            r6 = 10
            int r6 = kotlin.collections.CollectionsKt.collectionSizeOrDefault(r3, r6)
            r5.<init>(r6)
            java.util.Collection r5 = (java.util.Collection) r5
            r6 = r3
            r7 = 0
            java.util.Iterator r8 = r6.iterator()
        L_0x0062:
            boolean r9 = r8.hasNext()
            if (r9 == 0) goto L_0x007c
            java.lang.Object r9 = r8.next()
            r10 = r9
            java.lang.String r10 = (java.lang.String) r10
            r11 = 0
            int r10 = indentWidth$StringsKt__IndentKt(r10)
            java.lang.Integer r10 = java.lang.Integer.valueOf(r10)
            r5.add(r10)
            goto L_0x0062
        L_0x007c:
            r3 = r5
            java.util.List r3 = (java.util.List) r3
            java.lang.Iterable r3 = (java.lang.Iterable) r3
            java.lang.Comparable r3 = kotlin.collections.CollectionsKt.min(r3)
            java.lang.Integer r3 = (java.lang.Integer) r3
            if (r3 == 0) goto L_0x008e
            int r3 = r3.intValue()
            goto L_0x008f
        L_0x008e:
            r3 = 0
        L_0x008f:
            int r4 = r27.length()
            int r5 = r28.length()
            int r6 = r1.size()
            int r5 = r5 * r6
            int r4 = r4 + r5
            kotlin.jvm.functions.Function1 r5 = getIndentFunction$StringsKt__IndentKt(r28)
            r6 = r1
            r7 = 0
            int r8 = kotlin.collections.CollectionsKt.getLastIndex(r6)
            r9 = r6
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            r10 = 0
            java.util.ArrayList r11 = new java.util.ArrayList
            r11.<init>()
            java.util.Collection r11 = (java.util.Collection) r11
            r12 = r9
            r13 = 0
            r14 = r12
            r15 = 0
            r16 = 0
            java.util.Iterator r17 = r14.iterator()
        L_0x00c1:
            boolean r18 = r17.hasNext()
            if (r18 == 0) goto L_0x011b
            java.lang.Object r18 = r17.next()
            int r19 = r16 + 1
            if (r16 >= 0) goto L_0x00d2
            kotlin.collections.CollectionsKt.throwIndexOverflow()
        L_0x00d2:
            r20 = r18
            r21 = 0
            r22 = r20
            java.lang.String r22 = (java.lang.String) r22
            r23 = r16
            r24 = 0
            r0 = r23
            if (r0 == 0) goto L_0x00e4
            if (r0 != r8) goto L_0x00f3
        L_0x00e4:
            r23 = r22
            java.lang.CharSequence r23 = (java.lang.CharSequence) r23
            boolean r23 = kotlin.text.StringsKt.isBlank(r23)
            if (r23 == 0) goto L_0x00f3
            r23 = 0
            r0 = r23
            goto L_0x010c
        L_0x00f3:
            r23 = r22
            r25 = 0
            r26 = r0
            r0 = r23
            java.lang.String r0 = kotlin.text.StringsKt.drop(r0, r3)
            if (r0 == 0) goto L_0x010a
            java.lang.Object r0 = r5.invoke(r0)
            java.lang.String r0 = (java.lang.String) r0
            if (r0 == 0) goto L_0x010a
            goto L_0x010c
        L_0x010a:
            r0 = r22
        L_0x010c:
            if (r0 == 0) goto L_0x0115
            r22 = 0
            r11.add(r0)
            goto L_0x0116
        L_0x0115:
        L_0x0116:
            r0 = r27
            r16 = r19
            goto L_0x00c1
        L_0x011b:
            r0 = r11
            java.util.List r0 = (java.util.List) r0
            r9 = r0
            java.lang.Iterable r9 = (java.lang.Iterable) r9
            java.lang.StringBuilder r0 = new java.lang.StringBuilder
            r0.<init>(r4)
            r10 = r0
            java.lang.Appendable r10 = (java.lang.Appendable) r10
            java.lang.String r0 = "\n"
            r11 = r0
            java.lang.CharSequence r11 = (java.lang.CharSequence) r11
            r12 = 0
            r13 = 0
            r14 = 0
            r15 = 0
            r16 = 0
            r17 = 124(0x7c, float:1.74E-43)
            r18 = 0
            java.lang.Appendable r0 = kotlin.collections.CollectionsKt.joinTo$default(r9, r10, r11, r12, r13, r14, r15, r16, r17, r18)
            java.lang.StringBuilder r0 = (java.lang.StringBuilder) r0
            java.lang.String r0 = r0.toString()
            java.lang.String r9 = "mapIndexedNotNull { inde…\"\\n\")\n        .toString()"
            kotlin.jvm.internal.Intrinsics.checkExpressionValueIsNotNull(r0, r9)
            return r0
        */
        throw new UnsupportedOperationException("Method not decompiled: kotlin.text.StringsKt__IndentKt.replaceIndent(java.lang.String, java.lang.String):java.lang.String");
    }

    public static /* synthetic */ String prependIndent$default(String str, String str2, int i, Object obj) {
        if ((i & 1) != 0) {
            str2 = "    ";
        }
        return StringsKt.prependIndent(str, str2);
    }

    public static final String prependIndent(String $this$prependIndent, String indent) {
        Intrinsics.checkParameterIsNotNull($this$prependIndent, "$this$prependIndent");
        Intrinsics.checkParameterIsNotNull(indent, "indent");
        return SequencesKt.joinToString$default(SequencesKt.map(StringsKt.lineSequence($this$prependIndent), new StringsKt__IndentKt$prependIndent$1(indent)), "\n", null, null, 0, null, null, 62, null);
    }

    private static final int indentWidth$StringsKt__IndentKt(String $this$indentWidth) {
        CharSequence $this$indexOfFirst$iv = $this$indentWidth;
        int length = $this$indexOfFirst$iv.length();
        int index$iv = 0;
        while (true) {
            if (index$iv >= length) {
                index$iv = -1;
                break;
            } else if ((CharsKt.isWhitespace($this$indexOfFirst$iv.charAt(index$iv)) ^ 1) != 0) {
                break;
            } else {
                index$iv++;
            }
        }
        int it = index$iv;
        return it == -1 ? $this$indentWidth.length() : it;
    }

    private static final Function1<String, String> getIndentFunction$StringsKt__IndentKt(String indent) {
        if (indent.length() == 0) {
            return StringsKt__IndentKt$getIndentFunction$1.INSTANCE;
        }
        return new StringsKt__IndentKt$getIndentFunction$2<>(indent);
    }

    private static final String reindent$StringsKt__IndentKt(List<String> $this$reindent, int resultSizeEstimate, Function1<? super String, String> indentAddFunction, Function1<? super String, String> indentCutFunction) {
        int lastIndex;
        boolean z = false;
        int lastIndex2 = CollectionsKt.getLastIndex($this$reindent);
        Iterable $this$mapIndexedNotNull$iv = $this$reindent;
        Collection destination$iv$iv = new ArrayList();
        int index$iv$iv = 0;
        for (Object item$iv$iv$iv : $this$mapIndexedNotNull$iv) {
            int index$iv$iv$iv = index$iv$iv + 1;
            if (index$iv$iv < 0) {
                if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
                    CollectionsKt.throwIndexOverflow();
                } else {
                    throw new ArithmeticException("Index overflow has happened.");
                }
            }
            Object value = (String) item$iv$iv$iv;
            boolean z2 = z;
            int index = index$iv$iv;
            if ((index == 0 || index == lastIndex2) && StringsKt.isBlank(value)) {
                lastIndex = lastIndex2;
                value = null;
                Function1<? super String, String> function1 = indentAddFunction;
            } else {
                int i = index;
                String str = (String) indentCutFunction.invoke(value);
                if (str != null) {
                    lastIndex = lastIndex2;
                    String str2 = (String) indentAddFunction.invoke(str);
                    if (str2 != null) {
                        value = str2;
                    }
                } else {
                    lastIndex = lastIndex2;
                    Function1<? super String, String> function12 = indentAddFunction;
                }
            }
            if (value != null) {
                destination$iv$iv.add(value);
            }
            index$iv$iv = index$iv$iv$iv;
            z = z2;
            lastIndex2 = lastIndex;
        }
        int $i$f$reindent$StringsKt__IndentKt = z;
        String sb = ((StringBuilder) CollectionsKt.joinTo$default((List) destination$iv$iv, new StringBuilder(resultSizeEstimate), "\n", null, null, 0, null, null, 124, null)).toString();
        Intrinsics.checkExpressionValueIsNotNull(sb, "mapIndexedNotNull { inde…\"\\n\")\n        .toString()");
        return sb;
    }
}
