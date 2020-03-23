package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.TuplesKt;
import kotlin.UByteArray;
import kotlin.UIntArray;
import kotlin.ULongArray;
import kotlin.UShortArray;
import kotlin.collections.unsigned.UArraysKt;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000H\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010!\n\u0002\b\u0003\n\u0002\u0010 \n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\u001a1\u0010\u0000\u001a\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u0004\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\u0005\u0010\u0006\u001a!\u0010\u0007\u001a\u00020\b\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0001¢\u0006\u0004\b\t\u0010\n\u001a?\u0010\u000b\u001a\u00020\f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\u0010\u0010\u0010\u001a\f\u0012\b\u0012\u0006\u0012\u0002\b\u00030\u00030\u0011H\u0002¢\u0006\u0004\b\u0012\u0010\u0013\u001a+\u0010\u0014\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0015\"\u0004\b\u0000\u0010\u0002*\u0012\u0012\u000e\b\u0001\u0012\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00030\u0003¢\u0006\u0002\u0010\u0016\u001a8\u0010\u0017\u001a\u0002H\u0018\"\u0010\b\u0000\u0010\u0019*\u0006\u0012\u0002\b\u00030\u0003*\u0002H\u0018\"\u0004\b\u0001\u0010\u0018*\u0002H\u00192\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u0002H\u00180\u001bH\b¢\u0006\u0002\u0010\u001c\u001a)\u0010\u001d\u001a\u00020\u0001*\b\u0012\u0002\b\u0003\u0018\u00010\u0003H\b\u0002\u000e\n\f\b\u0000\u0012\u0002\u0018\u0001\u001a\u0004\b\u0003\u0010\u0000¢\u0006\u0002\u0010\u001e\u001aG\u0010\u001f\u001a\u001a\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00020\u0015\u0012\n\u0012\b\u0012\u0004\u0012\u0002H\u00180\u00150 \"\u0004\b\u0000\u0010\u0002\"\u0004\b\u0001\u0010\u0018*\u0016\u0012\u0012\b\u0001\u0012\u000e\u0012\u0004\u0012\u0002H\u0002\u0012\u0004\u0012\u0002H\u00180 0\u0003¢\u0006\u0002\u0010!¨\u0006\""}, mo6929d2 = {"contentDeepEqualsImpl", "", "T", "", "other", "contentDeepEquals", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepToStringImpl", "", "contentDeepToString", "([Ljava/lang/Object;)Ljava/lang/String;", "contentDeepToStringInternal", "", "result", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "processed", "", "contentDeepToStringInternal$ArraysKt__ArraysKt", "([Ljava/lang/Object;Ljava/lang/StringBuilder;Ljava/util/List;)V", "flatten", "", "([[Ljava/lang/Object;)Ljava/util/List;", "ifEmpty", "R", "C", "defaultValue", "Lkotlin/Function0;", "([Ljava/lang/Object;Lkotlin/jvm/functions/Function0;)Ljava/lang/Object;", "isNullOrEmpty", "([Ljava/lang/Object;)Z", "unzip", "Lkotlin/Pair;", "([Lkotlin/Pair;)Lkotlin/Pair;", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/collections/ArraysKt")
/* compiled from: Arrays.kt */
class ArraysKt__ArraysKt extends ArraysKt__ArraysJVMKt {
    public static final <T> List<T> flatten(T[][] $this$flatten) {
        Intrinsics.checkParameterIsNotNull($this$flatten, "$this$flatten");
        Object[] $this$sumBy$iv = (Object[]) $this$flatten;
        int sum$iv = 0;
        for (int sum$iv2 = 0; sum$iv2 < $this$sumBy$iv.length; sum$iv2++) {
            sum$iv += ((Object[]) $this$sumBy$iv[sum$iv2]).length;
        }
        ArrayList result = new ArrayList(sum$iv);
        for (Object[] element : $this$flatten) {
            CollectionsKt.addAll((Collection) result, element);
        }
        return result;
    }

    public static final <T, R> Pair<List<T>, List<R>> unzip(Pair<? extends T, ? extends R>[] $this$unzip) {
        Intrinsics.checkParameterIsNotNull($this$unzip, "$this$unzip");
        ArrayList listT = new ArrayList($this$unzip.length);
        ArrayList listR = new ArrayList($this$unzip.length);
        for (Pair pair : $this$unzip) {
            listT.add(pair.getFirst());
            listR.add(pair.getSecond());
        }
        return TuplesKt.m22to(listT, listR);
    }

    private static final boolean isNullOrEmpty(Object[] $this$isNullOrEmpty) {
        if ($this$isNullOrEmpty != null) {
            if (!($this$isNullOrEmpty.length == 0)) {
                return false;
            }
        }
        return true;
    }

    private static final <C extends Object & R, R> R ifEmpty(C $this$ifEmpty, Function0<? extends R> defaultValue) {
        return $this$ifEmpty.length == 0 ? defaultValue.invoke() : $this$ifEmpty;
    }

    public static final <T> boolean contentDeepEquals(T[] $this$contentDeepEqualsImpl, T[] other) {
        Intrinsics.checkParameterIsNotNull($this$contentDeepEqualsImpl, "$this$contentDeepEqualsImpl");
        Intrinsics.checkParameterIsNotNull(other, "other");
        if ($this$contentDeepEqualsImpl == other) {
            return true;
        }
        if ($this$contentDeepEqualsImpl.length != other.length) {
            return false;
        }
        int length = $this$contentDeepEqualsImpl.length;
        for (int i = 0; i < length; i++) {
            T t = $this$contentDeepEqualsImpl[i];
            T t2 = other[i];
            if (t != t2) {
                if (t == null || t2 == null) {
                    return false;
                }
                if (!(t instanceof Object[]) || !(t2 instanceof Object[])) {
                    if (!(t instanceof byte[]) || !(t2 instanceof byte[])) {
                        if (!(t instanceof short[]) || !(t2 instanceof short[])) {
                            if (!(t instanceof int[]) || !(t2 instanceof int[])) {
                                if (!(t instanceof long[]) || !(t2 instanceof long[])) {
                                    if (!(t instanceof float[]) || !(t2 instanceof float[])) {
                                        if (!(t instanceof double[]) || !(t2 instanceof double[])) {
                                            if (!(t instanceof char[]) || !(t2 instanceof char[])) {
                                                if (!(t instanceof boolean[]) || !(t2 instanceof boolean[])) {
                                                    if (!(t instanceof UByteArray) || !(t2 instanceof UByteArray)) {
                                                        if (!(t instanceof UShortArray) || !(t2 instanceof UShortArray)) {
                                                            if (!(t instanceof UIntArray) || !(t2 instanceof UIntArray)) {
                                                                if (!(t instanceof ULongArray) || !(t2 instanceof ULongArray)) {
                                                                    if (!Intrinsics.areEqual((Object) t, (Object) t2)) {
                                                                        return false;
                                                                    }
                                                                } else if (!UArraysKt.m465contentEqualsus8wMrg(((ULongArray) t).m261unboximpl(), ((ULongArray) t2).m261unboximpl())) {
                                                                    return false;
                                                                }
                                                            } else if (!UArraysKt.m462contentEqualsctEhBpI(((UIntArray) t).m192unboximpl(), ((UIntArray) t2).m192unboximpl())) {
                                                                return false;
                                                            }
                                                        } else if (!UArraysKt.m464contentEqualsmazbYpA(((UShortArray) t).m356unboximpl(), ((UShortArray) t2).m356unboximpl())) {
                                                            return false;
                                                        }
                                                    } else if (!UArraysKt.m463contentEqualskdPth3s(((UByteArray) t).m123unboximpl(), ((UByteArray) t2).m123unboximpl())) {
                                                        return false;
                                                    }
                                                } else if (!Arrays.equals((boolean[]) t, (boolean[]) t2)) {
                                                    return false;
                                                }
                                            } else if (!Arrays.equals((char[]) t, (char[]) t2)) {
                                                return false;
                                            }
                                        } else if (!Arrays.equals((double[]) t, (double[]) t2)) {
                                            return false;
                                        }
                                    } else if (!Arrays.equals((float[]) t, (float[]) t2)) {
                                        return false;
                                    }
                                } else if (!Arrays.equals((long[]) t, (long[]) t2)) {
                                    return false;
                                }
                            } else if (!Arrays.equals((int[]) t, (int[]) t2)) {
                                return false;
                            }
                        } else if (!Arrays.equals((short[]) t, (short[]) t2)) {
                            return false;
                        }
                    } else if (!Arrays.equals((byte[]) t, (byte[]) t2)) {
                        return false;
                    }
                } else if (!ArraysKt.contentDeepEquals((Object[]) t, (Object[]) t2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public static final <T> String contentDeepToString(T[] $this$contentDeepToStringImpl) {
        Intrinsics.checkParameterIsNotNull($this$contentDeepToStringImpl, "$this$contentDeepToStringImpl");
        StringBuilder sb = new StringBuilder((RangesKt.coerceAtMost($this$contentDeepToStringImpl.length, 429496729) * 5) + 2);
        contentDeepToStringInternal$ArraysKt__ArraysKt($this$contentDeepToStringImpl, sb, new ArrayList());
        String sb2 = sb.toString();
        Intrinsics.checkExpressionValueIsNotNull(sb2, "StringBuilder(capacity).…builderAction).toString()");
        return sb2;
    }

    private static final <T> void contentDeepToStringInternal$ArraysKt__ArraysKt(T[] $this$contentDeepToStringInternal, StringBuilder result, List<Object[]> processed) {
        if (processed.contains($this$contentDeepToStringInternal)) {
            result.append("[...]");
            return;
        }
        processed.add($this$contentDeepToStringInternal);
        result.append('[');
        int length = $this$contentDeepToStringInternal.length;
        for (int i = 0; i < length; i++) {
            if (i != 0) {
                result.append(", ");
            }
            T t = $this$contentDeepToStringInternal[i];
            if (t == null) {
                result.append("null");
            } else if (t instanceof Object[]) {
                contentDeepToStringInternal$ArraysKt__ArraysKt((Object[]) t, result, processed);
            } else {
                String str = "java.util.Arrays.toString(this)";
                if (t instanceof byte[]) {
                    String arrays = Arrays.toString((byte[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays, str);
                    result.append(arrays);
                } else if (t instanceof short[]) {
                    String arrays2 = Arrays.toString((short[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays2, str);
                    result.append(arrays2);
                } else if (t instanceof int[]) {
                    String arrays3 = Arrays.toString((int[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays3, str);
                    result.append(arrays3);
                } else if (t instanceof long[]) {
                    String arrays4 = Arrays.toString((long[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays4, str);
                    result.append(arrays4);
                } else if (t instanceof float[]) {
                    String arrays5 = Arrays.toString((float[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays5, str);
                    result.append(arrays5);
                } else if (t instanceof double[]) {
                    String arrays6 = Arrays.toString((double[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays6, str);
                    result.append(arrays6);
                } else if (t instanceof char[]) {
                    String arrays7 = Arrays.toString((char[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays7, str);
                    result.append(arrays7);
                } else if (t instanceof boolean[]) {
                    String arrays8 = Arrays.toString((boolean[]) t);
                    Intrinsics.checkExpressionValueIsNotNull(arrays8, str);
                    result.append(arrays8);
                } else if (t instanceof UByteArray) {
                    result.append(UArraysKt.m471contentToStringGBYM_sE(((UByteArray) t).m123unboximpl()));
                } else if (t instanceof UShortArray) {
                    result.append(UArraysKt.m473contentToStringrL5Bavg(((UShortArray) t).m356unboximpl()));
                } else if (t instanceof UIntArray) {
                    result.append(UArraysKt.m470contentToStringajY9A(((UIntArray) t).m192unboximpl()));
                } else if (t instanceof ULongArray) {
                    result.append(UArraysKt.m472contentToStringQwZRm1k(((ULongArray) t).m261unboximpl()));
                } else {
                    result.append(t.toString());
                }
            }
        }
        result.append(']');
        processed.remove(CollectionsKt.getLastIndex(processed));
    }
}
