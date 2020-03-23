package kotlin.text;

import kotlin.Metadata;

@Metadata(mo6927bv = {1, 0, 3}, mo6928d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bÂ\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0004¢\u0006\u0002\n\u0000¨\u0006\u0005"}, mo6929d2 = {"Lkotlin/text/ScreenFloatValueRegEx;", "", "()V", "value", "Lkotlin/text/Regex;", "kotlin-stdlib"}, mo6930k = 1, mo6931mv = {1, 1, 15})
/* compiled from: StringNumberConversionsJVM.kt */
final class ScreenFloatValueRegEx {
    public static final ScreenFloatValueRegEx INSTANCE = new ScreenFloatValueRegEx();
    public static final Regex value;

    static {
        String Digits = "(\\p{Digit}+)";
        String HexDigits = "(\\p{XDigit}+)";
        StringBuilder sb = new StringBuilder();
        sb.append("[eE][+-]?");
        sb.append(Digits);
        String Exp = sb.toString();
        StringBuilder sb2 = new StringBuilder();
        String str = "(0[xX]";
        sb2.append(str);
        sb2.append(HexDigits);
        sb2.append("(\\.)?)|");
        sb2.append(str);
        sb2.append(HexDigits);
        sb2.append("?(\\.)");
        sb2.append(HexDigits);
        sb2.append(')');
        String HexString = sb2.toString();
        StringBuilder sb3 = new StringBuilder();
        sb3.append('(');
        sb3.append(Digits);
        sb3.append("(\\.)?(");
        sb3.append(Digits);
        sb3.append("?)(");
        sb3.append(Exp);
        String str2 = ")?)|";
        sb3.append(str2);
        sb3.append("(\\.(");
        sb3.append(Digits);
        sb3.append(")(");
        sb3.append(Exp);
        sb3.append(str2);
        sb3.append("((");
        sb3.append(HexString);
        sb3.append(")[pP][+-]?");
        sb3.append(Digits);
        sb3.append(')');
        String Number = sb3.toString();
        StringBuilder sb4 = new StringBuilder();
        sb4.append("[\\x00-\\x20]*[+-]?(NaN|Infinity|((");
        sb4.append(Number);
        sb4.append(")[fFdD]?))[\\x00-\\x20]*");
        value = new Regex(sb4.toString());
    }

    private ScreenFloatValueRegEx() {
    }
}
