package kotlin.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.internal.PlatformImplementationsKt;
import kotlin.jvm.internal.Intrinsics;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0001\n\u0000\n\u0002\u0010 \n\u0000\n\u0002\u0010\u0011\n\u0000\n\u0002\u0010\u000b\n\u0002\u0010\u0018\n\u0002\u0010\u0005\n\u0002\u0010\u0012\n\u0002\u0010\f\n\u0002\u0010\u0019\n\u0002\u0010\u0006\n\u0002\u0010\u0013\n\u0002\u0010\u0007\n\u0002\u0010\u0014\n\u0002\u0010\b\n\u0002\u0010\u0015\n\u0002\u0010\t\n\u0002\u0010\u0016\n\u0002\u0010\n\n\u0002\u0010\u0017\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\f\n\u0002\u0010\u000e\n\u0002\b\u0017\n\u0002\u0010\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u001f\n\u0002\b\u0005\n\u0002\u0010\u001e\n\u0002\b\u0004\n\u0002\u0010\u000f\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\b\f\u001a#\u0010\u0000\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0001\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010\u0004\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001*\u00020\u0006\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00070\u0001*\u00020\b\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\t0\u0001*\u00020\n\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0001*\u00020\f\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\r0\u0001*\u00020\u000e\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0001*\u00020\u0010\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00110\u0001*\u00020\u0012\u001a\u0010\u0010\u0000\u001a\b\u0012\u0004\u0012\u00020\u00130\u0001*\u00020\u0014\u001aU\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001c\u001a9\u0010\u0015\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010\u001d\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010\u0015\u001a\u00020\u000f*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a2\u0010\u001e\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\f¢\u0006\u0004\b \u0010!\u001a\"\u0010\"\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\b¢\u0006\u0004\b#\u0010$\u001a\"\u0010%\u001a\u00020&\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\b¢\u0006\u0004\b'\u0010(\u001a0\u0010)\u001a\u00020\u0005\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u000e\u0010\u001f\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\f¢\u0006\u0002\u0010!\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00062\u0006\u0010\u001f\u001a\u00020\u0006H\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\b2\u0006\u0010\u001f\u001a\u00020\bH\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\n2\u0006\u0010\u001f\u001a\u00020\nH\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\f2\u0006\u0010\u001f\u001a\u00020\fH\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u000e2\u0006\u0010\u001f\u001a\u00020\u000eH\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00102\u0006\u0010\u001f\u001a\u00020\u0010H\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00122\u0006\u0010\u001f\u001a\u00020\u0012H\f\u001a\u0015\u0010)\u001a\u00020\u0005*\u00020\u00142\u0006\u0010\u001f\u001a\u00020\u0014H\f\u001a \u0010*\u001a\u00020\u000f\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\b¢\u0006\u0002\u0010$\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0006H\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\bH\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\nH\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\fH\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u000eH\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0010H\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0012H\b\u001a\r\u0010*\u001a\u00020\u000f*\u00020\u0014H\b\u001a \u0010+\u001a\u00020&\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\b¢\u0006\u0002\u0010(\u001a\r\u0010+\u001a\u00020&*\u00020\u0006H\b\u001a\r\u0010+\u001a\u00020&*\u00020\bH\b\u001a\r\u0010+\u001a\u00020&*\u00020\nH\b\u001a\r\u0010+\u001a\u00020&*\u00020\fH\b\u001a\r\u0010+\u001a\u00020&*\u00020\u000eH\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0010H\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0012H\b\u001a\r\u0010+\u001a\u00020&*\u00020\u0014H\b\u001aQ\u0010,\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\f\u0010-\u001a\b\u0012\u0004\u0012\u0002H\u00020\u00032\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007¢\u0006\u0002\u00101\u001a2\u0010,\u001a\u00020\u0006*\u00020\u00062\u0006\u0010-\u001a\u00020\u00062\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\b*\u00020\b2\u0006\u0010-\u001a\u00020\b2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\n*\u00020\n2\u0006\u0010-\u001a\u00020\n2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\f*\u00020\f2\u0006\u0010-\u001a\u00020\f2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010-\u001a\u00020\u000e2\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0010*\u00020\u00102\u0006\u0010-\u001a\u00020\u00102\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0012*\u00020\u00122\u0006\u0010-\u001a\u00020\u00122\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a2\u0010,\u001a\u00020\u0014*\u00020\u00142\u0006\u0010-\u001a\u00020\u00142\b\b\u0002\u0010.\u001a\u00020\u000f2\b\b\u0002\u0010/\u001a\u00020\u000f2\b\b\u0002\u00100\u001a\u00020\u000fH\u0007\u001a$\u00102\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u0003H\b¢\u0006\u0002\u00103\u001a.\u00102\u001a\n\u0012\u0006\u0012\u0004\u0018\u0001H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u00104\u001a\u00020\u000fH\b¢\u0006\u0002\u00105\u001a\r\u00102\u001a\u00020\u0006*\u00020\u0006H\b\u001a\u0015\u00102\u001a\u00020\u0006*\u00020\u00062\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\b*\u00020\bH\b\u001a\u0015\u00102\u001a\u00020\b*\u00020\b2\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\n*\u00020\nH\b\u001a\u0015\u00102\u001a\u00020\n*\u00020\n2\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\f*\u00020\fH\b\u001a\u0015\u00102\u001a\u00020\f*\u00020\f2\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\u000e*\u00020\u000eH\b\u001a\u0015\u00102\u001a\u00020\u000e*\u00020\u000e2\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\u0010*\u00020\u0010H\b\u001a\u0015\u00102\u001a\u00020\u0010*\u00020\u00102\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\u0012*\u00020\u0012H\b\u001a\u0015\u00102\u001a\u00020\u0012*\u00020\u00122\u0006\u00104\u001a\u00020\u000fH\b\u001a\r\u00102\u001a\u00020\u0014*\u00020\u0014H\b\u001a\u0015\u00102\u001a\u00020\u0014*\u00020\u00142\u0006\u00104\u001a\u00020\u000fH\b\u001a6\u00106\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0004\b7\u00108\u001a\"\u00106\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a\"\u00106\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\b¢\u0006\u0002\b7\u001a5\u00109\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0004\b6\u00108\u001a!\u00109\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\b*\u00020\b2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\n*\u00020\n2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\f*\u00020\f2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a!\u00109\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u001a\u001a\u00020\u000f2\u0006\u0010\u001b\u001a\u00020\u000fH\u0001¢\u0006\u0002\b6\u001a(\u0010:\u001a\u0002H\u0002\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u0006\u0010;\u001a\u00020\u000fH\b¢\u0006\u0002\u0010<\u001a\u0015\u0010:\u001a\u00020\u0005*\u00020\u00062\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\u0007*\u00020\b2\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\t*\u00020\n2\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\u000b*\u00020\f2\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\r*\u00020\u000e2\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\u000f*\u00020\u00102\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\u0011*\u00020\u00122\u0006\u0010;\u001a\u00020\u000fH\b\u001a\u0015\u0010:\u001a\u00020\u0013*\u00020\u00142\u0006\u0010;\u001a\u00020\u000fH\b\u001a7\u0010=\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u00022\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010?\u001a&\u0010=\u001a\u00020>*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u00052\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u00072\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\n2\u0006\u0010\u0016\u001a\u00020\t2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\r2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u00112\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a&\u0010=\u001a\u00020>*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u00132\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a-\u0010@\u001a\b\u0012\u0004\u0012\u0002HA0\u0001\"\u0004\b\u0000\u0010A*\u0006\u0012\u0002\b\u00030\u00032\f\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0C¢\u0006\u0002\u0010D\u001aA\u0010E\u001a\u0002HF\"\u0010\b\u0000\u0010F*\n\u0012\u0006\b\u0000\u0012\u0002HA0G\"\u0004\b\u0001\u0010A*\u0006\u0012\u0002\b\u00030\u00032\u0006\u0010-\u001a\u0002HF2\f\u0010B\u001a\b\u0012\u0004\u0012\u0002HA0C¢\u0006\u0002\u0010H\u001a,\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\u0002¢\u0006\u0002\u0010J\u001a4\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u000e\u0010K\u001a\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\u0002¢\u0006\u0002\u0010L\u001a2\u0010I\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\f\u0010K\u001a\b\u0012\u0004\u0012\u0002H\u00020MH\u0002¢\u0006\u0002\u0010N\u001a\u0015\u0010I\u001a\u00020\u0006*\u00020\u00062\u0006\u0010\u0016\u001a\u00020\u0005H\u0002\u001a\u0015\u0010I\u001a\u00020\u0006*\u00020\u00062\u0006\u0010K\u001a\u00020\u0006H\u0002\u001a\u001b\u0010I\u001a\u00020\u0006*\u00020\u00062\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00050MH\u0002\u001a\u0015\u0010I\u001a\u00020\b*\u00020\b2\u0006\u0010\u0016\u001a\u00020\u0007H\u0002\u001a\u0015\u0010I\u001a\u00020\b*\u00020\b2\u0006\u0010K\u001a\u00020\bH\u0002\u001a\u001b\u0010I\u001a\u00020\b*\u00020\b2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00070MH\u0002\u001a\u0015\u0010I\u001a\u00020\n*\u00020\n2\u0006\u0010\u0016\u001a\u00020\tH\u0002\u001a\u0015\u0010I\u001a\u00020\n*\u00020\n2\u0006\u0010K\u001a\u00020\nH\u0002\u001a\u001b\u0010I\u001a\u00020\n*\u00020\n2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\t0MH\u0002\u001a\u0015\u0010I\u001a\u00020\f*\u00020\f2\u0006\u0010\u0016\u001a\u00020\u000bH\u0002\u001a\u0015\u0010I\u001a\u00020\f*\u00020\f2\u0006\u0010K\u001a\u00020\fH\u0002\u001a\u001b\u0010I\u001a\u00020\f*\u00020\f2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000b0MH\u0002\u001a\u0015\u0010I\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\rH\u0002\u001a\u0015\u0010I\u001a\u00020\u000e*\u00020\u000e2\u0006\u0010K\u001a\u00020\u000eH\u0002\u001a\u001b\u0010I\u001a\u00020\u000e*\u00020\u000e2\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\r0MH\u0002\u001a\u0015\u0010I\u001a\u00020\u0010*\u00020\u00102\u0006\u0010\u0016\u001a\u00020\u000fH\u0002\u001a\u0015\u0010I\u001a\u00020\u0010*\u00020\u00102\u0006\u0010K\u001a\u00020\u0010H\u0002\u001a\u001b\u0010I\u001a\u00020\u0010*\u00020\u00102\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u000f0MH\u0002\u001a\u0015\u0010I\u001a\u00020\u0012*\u00020\u00122\u0006\u0010\u0016\u001a\u00020\u0011H\u0002\u001a\u0015\u0010I\u001a\u00020\u0012*\u00020\u00122\u0006\u0010K\u001a\u00020\u0012H\u0002\u001a\u001b\u0010I\u001a\u00020\u0012*\u00020\u00122\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00110MH\u0002\u001a\u0015\u0010I\u001a\u00020\u0014*\u00020\u00142\u0006\u0010\u0016\u001a\u00020\u0013H\u0002\u001a\u0015\u0010I\u001a\u00020\u0014*\u00020\u00142\u0006\u0010K\u001a\u00020\u0014H\u0002\u001a\u001b\u0010I\u001a\u00020\u0014*\u00020\u00142\f\u0010K\u001a\b\u0012\u0004\u0012\u00020\u00130MH\u0002\u001a,\u0010O\u001a\b\u0012\u0004\u0012\u0002H\u00020\u0003\"\u0004\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020\u00032\u0006\u0010\u0016\u001a\u0002H\u0002H\b¢\u0006\u0002\u0010J\u001a\u001d\u0010P\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010Q\u001a*\u0010P\u001a\u00020>\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020R*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003H\b¢\u0006\u0002\u0010S\u001a1\u0010P\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010T\u001a\n\u0010P\u001a\u00020>*\u00020\b\u001a\u001e\u0010P\u001a\u00020>*\u00020\b2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\n\u001a\u001e\u0010P\u001a\u00020>*\u00020\n2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\f\u001a\u001e\u0010P\u001a\u00020>*\u00020\f2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u000e\u001a\u001e\u0010P\u001a\u00020>*\u00020\u000e2\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0010\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00102\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0012\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00122\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a\n\u0010P\u001a\u00020>*\u00020\u0014\u001a\u001e\u0010P\u001a\u00020>*\u00020\u00142\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f\u001a9\u0010U\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010V\u001aM\u0010U\u001a\u00020>\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u00192\b\b\u0002\u0010\u001a\u001a\u00020\u000f2\b\b\u0002\u0010\u001b\u001a\u00020\u000f¢\u0006\u0002\u0010W\u001a-\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u00020Y\"\u000e\b\u0000\u0010\u0002*\b\u0012\u0004\u0012\u0002H\u00020R*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u0003¢\u0006\u0002\u0010Z\u001a?\u0010X\u001a\b\u0012\u0004\u0012\u0002H\u00020Y\"\u0004\b\u0000\u0010\u0002*\n\u0012\u0006\b\u0001\u0012\u0002H\u00020\u00032\u001a\u0010\u0017\u001a\u0016\u0012\u0006\b\u0000\u0012\u0002H\u00020\u0018j\n\u0012\u0006\b\u0000\u0012\u0002H\u0002`\u0019¢\u0006\u0002\u0010[\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00050Y*\u00020\u0006\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00070Y*\u00020\b\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\t0Y*\u00020\n\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000b0Y*\u00020\f\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\r0Y*\u00020\u000e\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u000f0Y*\u00020\u0010\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00110Y*\u00020\u0012\u001a\u0010\u0010X\u001a\b\u0012\u0004\u0012\u00020\u00130Y*\u00020\u0014\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00050\u0003*\u00020\u0006¢\u0006\u0002\u0010]\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00070\u0003*\u00020\b¢\u0006\u0002\u0010^\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\t0\u0003*\u00020\n¢\u0006\u0002\u0010_\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u000b0\u0003*\u00020\f¢\u0006\u0002\u0010`\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\r0\u0003*\u00020\u000e¢\u0006\u0002\u0010a\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0003*\u00020\u0010¢\u0006\u0002\u0010b\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00110\u0003*\u00020\u0012¢\u0006\u0002\u0010c\u001a\u0015\u0010\\\u001a\b\u0012\u0004\u0012\u00020\u00130\u0003*\u00020\u0014¢\u0006\u0002\u0010d¨\u0006e"}, mo6929d2 = {"asList", "", "T", "", "([Ljava/lang/Object;)Ljava/util/List;", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "binarySearch", "element", "comparator", "Ljava/util/Comparator;", "Lkotlin/Comparator;", "fromIndex", "toIndex", "([Ljava/lang/Object;Ljava/lang/Object;Ljava/util/Comparator;II)I", "([Ljava/lang/Object;Ljava/lang/Object;II)I", "contentDeepEquals", "other", "contentDeepEqualsInline", "([Ljava/lang/Object;[Ljava/lang/Object;)Z", "contentDeepHashCode", "contentDeepHashCodeInline", "([Ljava/lang/Object;)I", "contentDeepToString", "", "contentDeepToStringInline", "([Ljava/lang/Object;)Ljava/lang/String;", "contentEquals", "contentHashCode", "contentToString", "copyInto", "destination", "destinationOffset", "startIndex", "endIndex", "([Ljava/lang/Object;[Ljava/lang/Object;III)[Ljava/lang/Object;", "copyOf", "([Ljava/lang/Object;)[Ljava/lang/Object;", "newSize", "([Ljava/lang/Object;I)[Ljava/lang/Object;", "copyOfRange", "copyOfRangeInline", "([Ljava/lang/Object;II)[Ljava/lang/Object;", "copyOfRangeImpl", "elementAt", "index", "([Ljava/lang/Object;I)Ljava/lang/Object;", "fill", "", "([Ljava/lang/Object;Ljava/lang/Object;II)V", "filterIsInstance", "R", "klass", "Ljava/lang/Class;", "([Ljava/lang/Object;Ljava/lang/Class;)Ljava/util/List;", "filterIsInstanceTo", "C", "", "([Ljava/lang/Object;Ljava/util/Collection;Ljava/lang/Class;)Ljava/util/Collection;", "plus", "([Ljava/lang/Object;Ljava/lang/Object;)[Ljava/lang/Object;", "elements", "([Ljava/lang/Object;[Ljava/lang/Object;)[Ljava/lang/Object;", "", "([Ljava/lang/Object;Ljava/util/Collection;)[Ljava/lang/Object;", "plusElement", "sort", "([Ljava/lang/Object;)V", "", "([Ljava/lang/Comparable;)V", "([Ljava/lang/Object;II)V", "sortWith", "([Ljava/lang/Object;Ljava/util/Comparator;)V", "([Ljava/lang/Object;Ljava/util/Comparator;II)V", "toSortedSet", "Ljava/util/SortedSet;", "([Ljava/lang/Comparable;)Ljava/util/SortedSet;", "([Ljava/lang/Object;Ljava/util/Comparator;)Ljava/util/SortedSet;", "toTypedArray", "([Z)[Ljava/lang/Boolean;", "([B)[Ljava/lang/Byte;", "([C)[Ljava/lang/Character;", "([D)[Ljava/lang/Double;", "([F)[Ljava/lang/Float;", "([I)[Ljava/lang/Integer;", "([J)[Ljava/lang/Long;", "([S)[Ljava/lang/Short;", "kotlin-stdlib"}, mo6930k = 5, mo6931mv = {1, 1, 15}, mo6933xi = 1, mo6934xs = "kotlin/collections/ArraysKt")
/* compiled from: _ArraysJvm.kt */
class ArraysKt___ArraysJvmKt extends ArraysKt__ArraysKt {
    private static final <T> T elementAt(T[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final byte elementAt(byte[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final short elementAt(short[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final int elementAt(int[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final long elementAt(long[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final float elementAt(float[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final double elementAt(double[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final boolean elementAt(boolean[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    private static final char elementAt(char[] $this$elementAt, int index) {
        return $this$elementAt[index];
    }

    public static final <R> List<R> filterIsInstance(Object[] $this$filterIsInstance, Class<R> klass) {
        Intrinsics.checkParameterIsNotNull($this$filterIsInstance, "$this$filterIsInstance");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        return (List) ArraysKt.filterIsInstanceTo($this$filterIsInstance, new ArrayList(), klass);
    }

    public static final <C extends Collection<? super R>, R> C filterIsInstanceTo(Object[] $this$filterIsInstanceTo, C destination, Class<R> klass) {
        Intrinsics.checkParameterIsNotNull($this$filterIsInstanceTo, "$this$filterIsInstanceTo");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        Intrinsics.checkParameterIsNotNull(klass, "klass");
        for (Object element : $this$filterIsInstanceTo) {
            if (klass.isInstance(element)) {
                destination.add(element);
            }
        }
        return destination;
    }

    public static final <T> List<T> asList(T[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        List<T> asList = ArraysUtilJVM.asList($this$asList);
        Intrinsics.checkExpressionValueIsNotNull(asList, "ArraysUtilJVM.asList(this)");
        return asList;
    }

    public static final List<Byte> asList(byte[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$1<>($this$asList);
    }

    public static final List<Short> asList(short[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$2<>($this$asList);
    }

    public static final List<Integer> asList(int[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$3<>($this$asList);
    }

    public static final List<Long> asList(long[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$4<>($this$asList);
    }

    public static final List<Float> asList(float[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$5<>($this$asList);
    }

    public static final List<Double> asList(double[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$6<>($this$asList);
    }

    public static final List<Boolean> asList(boolean[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$7<>($this$asList);
    }

    public static final List<Character> asList(char[] $this$asList) {
        Intrinsics.checkParameterIsNotNull($this$asList, "$this$asList");
        return new ArraysKt___ArraysJvmKt$asList$8<>($this$asList);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, Comparator comparator, int i, int i2, int i3, Object obj2) {
        if ((i3 & 4) != 0) {
            i = 0;
        }
        if ((i3 & 8) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch(objArr, obj, comparator, i, i2);
    }

    public static final <T> int binarySearch(T[] $this$binarySearch, T element, Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element, comparator);
    }

    public static /* synthetic */ int binarySearch$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        return ArraysKt.binarySearch((T[]) objArr, (T) obj, i, i2);
    }

    public static final <T> int binarySearch(T[] $this$binarySearch, T element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        return ArraysKt.binarySearch(bArr, b, i, i2);
    }

    public static final int binarySearch(byte[] $this$binarySearch, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        return ArraysKt.binarySearch(sArr, s, i, i2);
    }

    public static final int binarySearch(short[] $this$binarySearch, short element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.binarySearch(iArr, i, i2, i3);
    }

    public static final int binarySearch(int[] $this$binarySearch, int element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        return ArraysKt.binarySearch(jArr, j, i, i2);
    }

    public static final int binarySearch(long[] $this$binarySearch, long element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        return ArraysKt.binarySearch(fArr, f, i, i2);
    }

    public static final int binarySearch(float[] $this$binarySearch, float element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        return ArraysKt.binarySearch(dArr, d, i, i2);
    }

    public static final int binarySearch(double[] $this$binarySearch, double element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    public static /* synthetic */ int binarySearch$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        return ArraysKt.binarySearch(cArr, c, i, i2);
    }

    public static final int binarySearch(char[] $this$binarySearch, char element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$binarySearch, "$this$binarySearch");
        return Arrays.binarySearch($this$binarySearch, fromIndex, toIndex, element);
    }

    private static final <T> boolean contentDeepEqualsInline(T[] $this$contentDeepEquals, T[] other) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepEquals($this$contentDeepEquals, other);
        }
        return Arrays.deepEquals($this$contentDeepEquals, other);
    }

    private static final <T> int contentDeepHashCodeInline(T[] $this$contentDeepHashCode) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepHashCode($this$contentDeepHashCode);
        }
        return Arrays.deepHashCode($this$contentDeepHashCode);
    }

    private static final <T> String contentDeepToStringInline(T[] $this$contentDeepToString) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.contentDeepToString($this$contentDeepToString);
        }
        String deepToString = Arrays.deepToString($this$contentDeepToString);
        Intrinsics.checkExpressionValueIsNotNull(deepToString, "java.util.Arrays.deepToString(this)");
        return deepToString;
    }

    private static final <T> boolean contentEquals(T[] $this$contentEquals, T[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(byte[] $this$contentEquals, byte[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(short[] $this$contentEquals, short[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(int[] $this$contentEquals, int[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(long[] $this$contentEquals, long[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(float[] $this$contentEquals, float[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(double[] $this$contentEquals, double[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(boolean[] $this$contentEquals, boolean[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final boolean contentEquals(char[] $this$contentEquals, char[] other) {
        return Arrays.equals($this$contentEquals, other);
    }

    private static final <T> int contentHashCode(T[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(byte[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(short[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(int[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(long[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(float[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(double[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(boolean[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final int contentHashCode(char[] $this$contentHashCode) {
        return Arrays.hashCode($this$contentHashCode);
    }

    private static final <T> String contentToString(T[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(byte[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(short[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(int[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(long[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(float[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(double[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(boolean[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    private static final String contentToString(char[] $this$contentToString) {
        String arrays = Arrays.toString($this$contentToString);
        Intrinsics.checkExpressionValueIsNotNull(arrays, "java.util.Arrays.toString(this)");
        return arrays;
    }

    public static /* synthetic */ Object[] copyInto$default(Object[] objArr, Object[] objArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = objArr.length;
        }
        return ArraysKt.copyInto((T[]) objArr, (T[]) objArr2, i, i2, i3);
    }

    public static final <T> T[] copyInto(T[] $this$copyInto, T[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ byte[] copyInto$default(byte[] bArr, byte[] bArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = bArr.length;
        }
        return ArraysKt.copyInto(bArr, bArr2, i, i2, i3);
    }

    public static final byte[] copyInto(byte[] $this$copyInto, byte[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ short[] copyInto$default(short[] sArr, short[] sArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = sArr.length;
        }
        return ArraysKt.copyInto(sArr, sArr2, i, i2, i3);
    }

    public static final short[] copyInto(short[] $this$copyInto, short[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ int[] copyInto$default(int[] iArr, int[] iArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = iArr.length;
        }
        return ArraysKt.copyInto(iArr, iArr2, i, i2, i3);
    }

    public static final int[] copyInto(int[] $this$copyInto, int[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ long[] copyInto$default(long[] jArr, long[] jArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = jArr.length;
        }
        return ArraysKt.copyInto(jArr, jArr2, i, i2, i3);
    }

    public static final long[] copyInto(long[] $this$copyInto, long[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ float[] copyInto$default(float[] fArr, float[] fArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = fArr.length;
        }
        return ArraysKt.copyInto(fArr, fArr2, i, i2, i3);
    }

    public static final float[] copyInto(float[] $this$copyInto, float[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ double[] copyInto$default(double[] dArr, double[] dArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = dArr.length;
        }
        return ArraysKt.copyInto(dArr, dArr2, i, i2, i3);
    }

    public static final double[] copyInto(double[] $this$copyInto, double[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ boolean[] copyInto$default(boolean[] zArr, boolean[] zArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = zArr.length;
        }
        return ArraysKt.copyInto(zArr, zArr2, i, i2, i3);
    }

    public static final boolean[] copyInto(boolean[] $this$copyInto, boolean[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    public static /* synthetic */ char[] copyInto$default(char[] cArr, char[] cArr2, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i = 0;
        }
        if ((i4 & 4) != 0) {
            i2 = 0;
        }
        if ((i4 & 8) != 0) {
            i3 = cArr.length;
        }
        return ArraysKt.copyInto(cArr, cArr2, i, i2, i3);
    }

    public static final char[] copyInto(char[] $this$copyInto, char[] destination, int destinationOffset, int startIndex, int endIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyInto, "$this$copyInto");
        Intrinsics.checkParameterIsNotNull(destination, "destination");
        System.arraycopy($this$copyInto, startIndex, destination, destinationOffset, endIndex - startIndex);
        return destination;
    }

    private static final <T> T[] copyOf(T[] $this$copyOf) {
        T[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final byte[] copyOf(byte[] $this$copyOf) {
        byte[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final short[] copyOf(short[] $this$copyOf) {
        short[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final int[] copyOf(int[] $this$copyOf) {
        int[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final long[] copyOf(long[] $this$copyOf) {
        long[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final float[] copyOf(float[] $this$copyOf) {
        float[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final double[] copyOf(double[] $this$copyOf) {
        double[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final boolean[] copyOf(boolean[] $this$copyOf) {
        boolean[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final char[] copyOf(char[] $this$copyOf) {
        char[] copyOf = Arrays.copyOf($this$copyOf, $this$copyOf.length);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, size)");
        return copyOf;
    }

    private static final byte[] copyOf(byte[] $this$copyOf, int newSize) {
        byte[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final short[] copyOf(short[] $this$copyOf, int newSize) {
        short[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final int[] copyOf(int[] $this$copyOf, int newSize) {
        int[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final long[] copyOf(long[] $this$copyOf, int newSize) {
        long[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final float[] copyOf(float[] $this$copyOf, int newSize) {
        float[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final double[] copyOf(double[] $this$copyOf, int newSize) {
        double[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final boolean[] copyOf(boolean[] $this$copyOf, int newSize) {
        boolean[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final char[] copyOf(char[] $this$copyOf, int newSize) {
        char[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final <T> T[] copyOf(T[] $this$copyOf, int newSize) {
        T[] copyOf = Arrays.copyOf($this$copyOf, newSize);
        Intrinsics.checkExpressionValueIsNotNull(copyOf, "java.util.Arrays.copyOf(this, newSize)");
        return copyOf;
    }

    private static final <T> T[] copyOfRangeInline(T[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            T[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final byte[] copyOfRangeInline(byte[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            byte[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final short[] copyOfRangeInline(short[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            short[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final int[] copyOfRangeInline(int[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            int[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final long[] copyOfRangeInline(long[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            long[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final float[] copyOfRangeInline(float[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            float[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final double[] copyOfRangeInline(double[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            double[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final boolean[] copyOfRangeInline(boolean[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            boolean[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    private static final char[] copyOfRangeInline(char[] $this$copyOfRange, int fromIndex, int toIndex) {
        if (PlatformImplementationsKt.apiVersionIsAtLeast(1, 3, 0)) {
            return ArraysKt.copyOfRange($this$copyOfRange, fromIndex, toIndex);
        }
        if (toIndex <= $this$copyOfRange.length) {
            char[] copyOfRange = Arrays.copyOfRange($this$copyOfRange, fromIndex, toIndex);
            Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
            return copyOfRange;
        }
        StringBuilder sb = new StringBuilder();
        sb.append("toIndex: ");
        sb.append(toIndex);
        sb.append(", size: ");
        sb.append($this$copyOfRange.length);
        throw new IndexOutOfBoundsException(sb.toString());
    }

    public static final <T> T[] copyOfRange(T[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        T[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final byte[] copyOfRange(byte[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        byte[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final short[] copyOfRange(short[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        short[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final int[] copyOfRange(int[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        int[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final long[] copyOfRange(long[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        long[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final float[] copyOfRange(float[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        float[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final double[] copyOfRange(double[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        double[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final boolean[] copyOfRange(boolean[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        boolean[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static final char[] copyOfRange(char[] $this$copyOfRangeImpl, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$copyOfRangeImpl, "$this$copyOfRangeImpl");
        ArraysKt.copyOfRangeToIndexCheck(toIndex, $this$copyOfRangeImpl.length);
        char[] copyOfRange = Arrays.copyOfRange($this$copyOfRangeImpl, fromIndex, toIndex);
        Intrinsics.checkExpressionValueIsNotNull(copyOfRange, "java.util.Arrays.copyOfR…this, fromIndex, toIndex)");
        return copyOfRange;
    }

    public static /* synthetic */ void fill$default(Object[] objArr, Object obj, int i, int i2, int i3, Object obj2) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.fill((T[]) objArr, (T) obj, i, i2);
    }

    public static final <T> void fill(T[] $this$fill, T element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(byte[] bArr, byte b, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.fill(bArr, b, i, i2);
    }

    public static final void fill(byte[] $this$fill, byte element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(short[] sArr, short s, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.fill(sArr, s, i, i2);
    }

    public static final void fill(short[] $this$fill, short element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(int[] iArr, int i, int i2, int i3, int i4, Object obj) {
        if ((i4 & 2) != 0) {
            i2 = 0;
        }
        if ((i4 & 4) != 0) {
            i3 = iArr.length;
        }
        ArraysKt.fill(iArr, i, i2, i3);
    }

    public static final void fill(int[] $this$fill, int element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(long[] jArr, long j, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.fill(jArr, j, i, i2);
    }

    public static final void fill(long[] $this$fill, long element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(float[] fArr, float f, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.fill(fArr, f, i, i2);
    }

    public static final void fill(float[] $this$fill, float element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(double[] dArr, double d, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.fill(dArr, d, i, i2);
    }

    public static final void fill(double[] $this$fill, double element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(boolean[] zArr, boolean z, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = zArr.length;
        }
        ArraysKt.fill(zArr, z, i, i2);
    }

    public static final void fill(boolean[] $this$fill, boolean element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static /* synthetic */ void fill$default(char[] cArr, char c, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.fill(cArr, c, i, i2);
    }

    public static final void fill(char[] $this$fill, char element, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$fill, "$this$fill");
        Arrays.fill($this$fill, fromIndex, toIndex, element);
    }

    public static final <T> T[] plus(T[] $this$plus, T element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        Object[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final byte[] plus(byte[] $this$plus, byte element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        byte[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final short[] plus(short[] $this$plus, short element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        short[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final int[] plus(int[] $this$plus, int element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        int[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final long[] plus(long[] $this$plus, long element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        long[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final float[] plus(float[] $this$plus, float element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        float[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final double[] plus(double[] $this$plus, double element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        double[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final boolean[] plus(boolean[] $this$plus, boolean element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        boolean[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final char[] plus(char[] $this$plus, char element) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        int index = $this$plus.length;
        char[] result = Arrays.copyOf($this$plus, index + 1);
        result[index] = element;
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final <T> T[] plus(T[] $this$plus, Collection<? extends T> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        Object[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Object element : elements) {
            int index2 = index + 1;
            result[index] = element;
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final byte[] plus(byte[] $this$plus, Collection<Byte> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        byte[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number byteValue : elements) {
            int index2 = index + 1;
            result[index] = byteValue.byteValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final short[] plus(short[] $this$plus, Collection<Short> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        short[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number shortValue : elements) {
            int index2 = index + 1;
            result[index] = shortValue.shortValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final int[] plus(int[] $this$plus, Collection<Integer> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        int[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number intValue : elements) {
            int index2 = index + 1;
            result[index] = intValue.intValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final long[] plus(long[] $this$plus, Collection<Long> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        long[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number longValue : elements) {
            int index2 = index + 1;
            result[index] = longValue.longValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final float[] plus(float[] $this$plus, Collection<Float> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        float[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number floatValue : elements) {
            int index2 = index + 1;
            result[index] = floatValue.floatValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final double[] plus(double[] $this$plus, Collection<Double> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        double[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Number doubleValue : elements) {
            int index2 = index + 1;
            result[index] = doubleValue.doubleValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final boolean[] plus(boolean[] $this$plus, Collection<Boolean> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        boolean[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Boolean booleanValue : elements) {
            int index2 = index + 1;
            result[index] = booleanValue.booleanValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final char[] plus(char[] $this$plus, Collection<Character> elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int index = $this$plus.length;
        char[] result = Arrays.copyOf($this$plus, elements.size() + index);
        for (Character charValue : elements) {
            int index2 = index + 1;
            result[index] = charValue.charValue();
            index = index2;
        }
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final <T> T[] plus(T[] $this$plus, T[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        Object[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final byte[] plus(byte[] $this$plus, byte[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        byte[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final short[] plus(short[] $this$plus, short[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        short[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final int[] plus(int[] $this$plus, int[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        int[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final long[] plus(long[] $this$plus, long[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        long[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final float[] plus(float[] $this$plus, float[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        float[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final double[] plus(double[] $this$plus, double[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        double[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final boolean[] plus(boolean[] $this$plus, boolean[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        boolean[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    public static final char[] plus(char[] $this$plus, char[] elements) {
        Intrinsics.checkParameterIsNotNull($this$plus, "$this$plus");
        Intrinsics.checkParameterIsNotNull(elements, "elements");
        int thisSize = $this$plus.length;
        int arraySize = elements.length;
        char[] result = Arrays.copyOf($this$plus, thisSize + arraySize);
        System.arraycopy(elements, 0, result, thisSize, arraySize);
        Intrinsics.checkExpressionValueIsNotNull(result, "result");
        return result;
    }

    private static final <T> T[] plusElement(T[] $this$plusElement, T element) {
        return ArraysKt.plus($this$plusElement, element);
    }

    public static final void sort(int[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(long[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(byte[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(short[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(double[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(float[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static final void sort(char[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    private static final <T extends Comparable<? super T>> void sort(T[] $this$sort) {
        if ($this$sort != null) {
            ArraysKt.sort((T[]) (Object[]) $this$sort);
            return;
        }
        throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.Any?>");
    }

    public static final <T> void sort(T[] $this$sort) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        if ($this$sort.length > 1) {
            Arrays.sort($this$sort);
        }
    }

    public static /* synthetic */ void sort$default(Object[] objArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sort((T[]) objArr, i, i2);
    }

    public static final <T> void sort(T[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(byte[] bArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = bArr.length;
        }
        ArraysKt.sort(bArr, i, i2);
    }

    public static final void sort(byte[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(short[] sArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = sArr.length;
        }
        ArraysKt.sort(sArr, i, i2);
    }

    public static final void sort(short[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(int[] iArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = iArr.length;
        }
        ArraysKt.sort(iArr, i, i2);
    }

    public static final void sort(int[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(long[] jArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = jArr.length;
        }
        ArraysKt.sort(jArr, i, i2);
    }

    public static final void sort(long[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(float[] fArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = fArr.length;
        }
        ArraysKt.sort(fArr, i, i2);
    }

    public static final void sort(float[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(double[] dArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = dArr.length;
        }
        ArraysKt.sort(dArr, i, i2);
    }

    public static final void sort(double[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static /* synthetic */ void sort$default(char[] cArr, int i, int i2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = 0;
        }
        if ((i3 & 2) != 0) {
            i2 = cArr.length;
        }
        ArraysKt.sort(cArr, i, i2);
    }

    public static final void sort(char[] $this$sort, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sort, "$this$sort");
        Arrays.sort($this$sort, fromIndex, toIndex);
    }

    public static final <T> void sortWith(T[] $this$sortWith, Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull($this$sortWith, "$this$sortWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        if ($this$sortWith.length > 1) {
            Arrays.sort($this$sortWith, comparator);
        }
    }

    public static /* synthetic */ void sortWith$default(Object[] objArr, Comparator comparator, int i, int i2, int i3, Object obj) {
        if ((i3 & 2) != 0) {
            i = 0;
        }
        if ((i3 & 4) != 0) {
            i2 = objArr.length;
        }
        ArraysKt.sortWith(objArr, comparator, i, i2);
    }

    public static final <T> void sortWith(T[] $this$sortWith, Comparator<? super T> comparator, int fromIndex, int toIndex) {
        Intrinsics.checkParameterIsNotNull($this$sortWith, "$this$sortWith");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        Arrays.sort($this$sortWith, fromIndex, toIndex, comparator);
    }

    public static final Byte[] toTypedArray(byte[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Byte[] result = new Byte[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Byte.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Short[] toTypedArray(short[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Short[] result = new Short[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Short.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Integer[] toTypedArray(int[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Integer[] result = new Integer[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Integer.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Long[] toTypedArray(long[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Long[] result = new Long[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Long.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Float[] toTypedArray(float[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Float[] result = new Float[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Float.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Double[] toTypedArray(double[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Double[] result = new Double[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Double.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Boolean[] toTypedArray(boolean[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Boolean[] result = new Boolean[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Boolean.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final Character[] toTypedArray(char[] $this$toTypedArray) {
        Intrinsics.checkParameterIsNotNull($this$toTypedArray, "$this$toTypedArray");
        Character[] result = new Character[$this$toTypedArray.length];
        int length = $this$toTypedArray.length;
        for (int index = 0; index < length; index++) {
            result[index] = Character.valueOf($this$toTypedArray[index]);
        }
        return result;
    }

    public static final <T extends Comparable<? super T>> SortedSet<T> toSortedSet(T[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Byte> toSortedSet(byte[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Short> toSortedSet(short[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Integer> toSortedSet(int[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Long> toSortedSet(long[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Float> toSortedSet(float[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Double> toSortedSet(double[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Boolean> toSortedSet(boolean[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final SortedSet<Character> toSortedSet(char[] $this$toSortedSet) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet());
    }

    public static final <T> SortedSet<T> toSortedSet(T[] $this$toSortedSet, Comparator<? super T> comparator) {
        Intrinsics.checkParameterIsNotNull($this$toSortedSet, "$this$toSortedSet");
        Intrinsics.checkParameterIsNotNull(comparator, "comparator");
        return (SortedSet) ArraysKt.toCollection($this$toSortedSet, new TreeSet(comparator));
    }
}
