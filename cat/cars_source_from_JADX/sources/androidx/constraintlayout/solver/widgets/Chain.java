package androidx.constraintlayout.solver.widgets;

import androidx.constraintlayout.solver.LinearSystem;

class Chain {
    private static final boolean DEBUG = false;

    Chain() {
    }

    static void applyChainConstraints(ConstraintWidgetContainer constraintWidgetContainer, LinearSystem system, int orientation) {
        ChainHead[] chainsArray;
        int chainsSize;
        int offset;
        if (orientation == 0) {
            offset = 0;
            chainsSize = constraintWidgetContainer.mHorizontalChainsSize;
            chainsArray = constraintWidgetContainer.mHorizontalChainsArray;
        } else {
            offset = 2;
            chainsSize = constraintWidgetContainer.mVerticalChainsSize;
            chainsArray = constraintWidgetContainer.mVerticalChainsArray;
        }
        for (int i = 0; i < chainsSize; i++) {
            ChainHead first = chainsArray[i];
            first.define();
            if (!constraintWidgetContainer.optimizeFor(4)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            } else if (!Optimizer.applyChainOptimized(constraintWidgetContainer, system, orientation, offset, first)) {
                applyChainConstraints(constraintWidgetContainer, system, orientation, offset, first);
            }
        }
    }

    /* JADX WARNING: Removed duplicated region for block: B:286:0x0638 A[ADDED_TO_REGION] */
    /* JADX WARNING: Removed duplicated region for block: B:290:0x064a  */
    /* JADX WARNING: Removed duplicated region for block: B:291:0x064f  */
    /* JADX WARNING: Removed duplicated region for block: B:294:0x0656  */
    /* JADX WARNING: Removed duplicated region for block: B:295:0x065b  */
    /* JADX WARNING: Removed duplicated region for block: B:297:0x065e  */
    /* JADX WARNING: Removed duplicated region for block: B:302:0x0672  */
    /* JADX WARNING: Removed duplicated region for block: B:304:0x0676  */
    /* JADX WARNING: Removed duplicated region for block: B:305:0x0682  */
    /* JADX WARNING: Removed duplicated region for block: B:307:0x0685 A[ADDED_TO_REGION] */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    static void applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer r44, androidx.constraintlayout.solver.LinearSystem r45, int r46, int r47, androidx.constraintlayout.solver.widgets.ChainHead r48) {
        /*
            r0 = r44
            r10 = r45
            r11 = r48
            androidx.constraintlayout.solver.widgets.ConstraintWidget r12 = r11.mFirst
            androidx.constraintlayout.solver.widgets.ConstraintWidget r13 = r11.mLast
            androidx.constraintlayout.solver.widgets.ConstraintWidget r14 = r11.mFirstVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r15 = r11.mLastVisibleWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r9 = r11.mHead
            r1 = r12
            r2 = 0
            r3 = 0
            float r4 = r11.mTotalWeight
            androidx.constraintlayout.solver.widgets.ConstraintWidget r8 = r11.mFirstMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r7 = r11.mLastMatchConstraintWidget
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r5 = r0.mListDimensionBehaviors
            r5 = r5[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r6 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.WRAP_CONTENT
            r16 = r1
            if (r5 != r6) goto L_0x0025
            r5 = 1
            goto L_0x0026
        L_0x0025:
            r5 = 0
        L_0x0026:
            r18 = r5
            r5 = 0
            r6 = 0
            r19 = 0
            if (r46 != 0) goto L_0x0052
            int r1 = r9.mHorizontalChainStyle
            if (r1 != 0) goto L_0x0034
            r1 = 1
            goto L_0x0035
        L_0x0034:
            r1 = 0
        L_0x0035:
            int r5 = r9.mHorizontalChainStyle
            r22 = r1
            r1 = 1
            if (r5 != r1) goto L_0x003e
            r1 = 1
            goto L_0x003f
        L_0x003e:
            r1 = 0
        L_0x003f:
            int r5 = r9.mHorizontalChainStyle
            r6 = 2
            if (r5 != r6) goto L_0x0046
            r5 = 1
            goto L_0x0047
        L_0x0046:
            r5 = 0
        L_0x0047:
            r21 = r1
            r23 = r2
            r19 = r5
            r6 = r16
            r16 = r3
            goto L_0x0075
        L_0x0052:
            int r1 = r9.mVerticalChainStyle
            if (r1 != 0) goto L_0x0058
            r1 = 1
            goto L_0x0059
        L_0x0058:
            r1 = 0
        L_0x0059:
            int r5 = r9.mVerticalChainStyle
            r22 = r1
            r1 = 1
            if (r5 != r1) goto L_0x0062
            r1 = 1
            goto L_0x0063
        L_0x0062:
            r1 = 0
        L_0x0063:
            int r5 = r9.mVerticalChainStyle
            r6 = 2
            if (r5 != r6) goto L_0x006a
            r5 = 1
            goto L_0x006b
        L_0x006a:
            r5 = 0
        L_0x006b:
            r21 = r1
            r23 = r2
            r19 = r5
            r6 = r16
            r16 = r3
        L_0x0075:
            r3 = 5
            if (r16 != 0) goto L_0x0159
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r6.mListAnchors
            r2 = r2[r47]
            r24 = 4
            if (r18 != 0) goto L_0x0082
            if (r19 == 0) goto L_0x0084
        L_0x0082:
            r24 = 1
        L_0x0084:
            int r25 = r2.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r2.mTarget
            if (r5 == 0) goto L_0x0099
            if (r6 == r12) goto L_0x0099
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r5 = r2.mTarget
            int r5 = r5.getMargin()
            int r25 = r25 + r5
            r5 = r25
            goto L_0x009b
        L_0x0099:
            r5 = r25
        L_0x009b:
            if (r19 == 0) goto L_0x00a6
            if (r6 == r12) goto L_0x00a6
            if (r6 == r14) goto L_0x00a6
            r24 = 6
            r27 = r24
            goto L_0x00b1
        L_0x00a6:
            if (r22 == 0) goto L_0x00af
            if (r18 == 0) goto L_0x00af
            r24 = 4
            r27 = r24
            goto L_0x00b1
        L_0x00af:
            r27 = r24
        L_0x00b1:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r2.mTarget
            if (r1 == 0) goto L_0x00db
            if (r6 != r14) goto L_0x00c3
            androidx.constraintlayout.solver.SolverVariable r1 = r2.mSolverVariable
            r25 = r4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r10.addGreaterThan(r1, r4, r5, r3)
            goto L_0x00cf
        L_0x00c3:
            r25 = r4
            androidx.constraintlayout.solver.SolverVariable r1 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r3 = 6
            r10.addGreaterThan(r1, r4, r5, r3)
        L_0x00cf:
            androidx.constraintlayout.solver.SolverVariable r1 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r4 = r27
            r10.addEquality(r1, r3, r5, r4)
            goto L_0x00df
        L_0x00db:
            r25 = r4
            r4 = r27
        L_0x00df:
            if (r18 == 0) goto L_0x011f
            int r1 = r6.getVisibility()
            r3 = 8
            if (r1 == r3) goto L_0x0109
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour[] r1 = r6.mListDimensionBehaviors
            r1 = r1[r46]
            androidx.constraintlayout.solver.widgets.ConstraintWidget$DimensionBehaviour r3 = androidx.constraintlayout.solver.widgets.ConstraintWidget.DimensionBehaviour.MATCH_CONSTRAINT
            if (r1 != r3) goto L_0x0109
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            int r3 = r47 + 1
            r1 = r1[r3]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r6.mListAnchors
            r3 = r3[r47]
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            r26 = r2
            r27 = r4
            r2 = 5
            r4 = 0
            r10.addGreaterThan(r1, r3, r4, r2)
            goto L_0x010d
        L_0x0109:
            r26 = r2
            r27 = r4
        L_0x010d:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r0.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            r3 = 6
            r4 = 0
            r10.addGreaterThan(r1, r2, r4, r3)
            goto L_0x0123
        L_0x011f:
            r26 = r2
            r27 = r4
        L_0x0123:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r6.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0149
            androidx.constraintlayout.solver.widgets.ConstraintWidget r2 = r1.mOwner
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x0145
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r2.mListAnchors
            r3 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintWidget r3 = r3.mOwner
            if (r3 == r6) goto L_0x0142
            goto L_0x0145
        L_0x0142:
            r23 = r2
            goto L_0x014c
        L_0x0145:
            r2 = 0
            r23 = r2
            goto L_0x014c
        L_0x0149:
            r2 = 0
            r23 = r2
        L_0x014c:
            if (r23 == 0) goto L_0x0152
            r2 = r23
            r6 = r2
            goto L_0x0155
        L_0x0152:
            r2 = 1
            r16 = r2
        L_0x0155:
            r4 = r25
            goto L_0x0075
        L_0x0159:
            r25 = r4
            if (r15 == 0) goto L_0x0183
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0183
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            int r4 = r1.getMargin()
            int r4 = -r4
            r5 = 5
            r10.addLowerThan(r2, r3, r4, r5)
            goto L_0x0184
        L_0x0183:
            r5 = 5
        L_0x0184:
            if (r18 == 0) goto L_0x01a4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            int r3 = r3.getMargin()
            r4 = 6
            r10.addGreaterThan(r1, r2, r3, r4)
        L_0x01a4:
            java.util.ArrayList<androidx.constraintlayout.solver.widgets.ConstraintWidget> r4 = r11.mWeightedMatchConstraintsWidgets
            if (r4 == 0) goto L_0x029d
            int r1 = r4.size()
            r2 = 1
            if (r1 <= r2) goto L_0x0292
            r3 = 0
            r20 = 0
            boolean r2 = r11.mHasUndefinedWeights
            if (r2 == 0) goto L_0x01be
            boolean r2 = r11.mHasComplexMatchWeights
            if (r2 != 0) goto L_0x01be
            int r2 = r11.mWidgetsMatchCount
            float r2 = (float) r2
            goto L_0x01c0
        L_0x01be:
            r2 = r25
        L_0x01c0:
            r25 = 0
            r5 = r3
            r3 = r25
            r25 = r20
        L_0x01c7:
            if (r3 >= r1) goto L_0x0283
            java.lang.Object r28 = r4.get(r3)
            r0 = r28
            androidx.constraintlayout.solver.widgets.ConstraintWidget r0 = (androidx.constraintlayout.solver.widgets.ConstraintWidget) r0
            r36 = r1
            float[] r1 = r0.mWeight
            r1 = r1[r46]
            r28 = 0
            int r29 = (r1 > r28 ? 1 : (r1 == r28 ? 0 : -1))
            if (r29 >= 0) goto L_0x020b
            r29 = r1
            boolean r1 = r11.mHasComplexMatchWeights
            if (r1 == 0) goto L_0x0202
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r0.mListAnchors
            int r28 = r47 + 1
            r1 = r1[r28]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            r37 = r4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r0.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            r38 = r6
            r6 = 4
            r39 = r7
            r7 = 0
            r10.addEquality(r1, r4, r7, r6)
            r17 = r8
            r7 = 6
            r8 = 0
            goto L_0x0273
        L_0x0202:
            r37 = r4
            r38 = r6
            r39 = r7
            r1 = 1065353216(0x3f800000, float:1.0)
            goto L_0x0213
        L_0x020b:
            r29 = r1
            r37 = r4
            r38 = r6
            r39 = r7
        L_0x0213:
            int r4 = (r1 > r28 ? 1 : (r1 == r28 ? 0 : -1))
            if (r4 != 0) goto L_0x022d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r0.mListAnchors
            int r6 = r47 + 1
            r4 = r4[r6]
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r0.mListAnchors
            r6 = r6[r47]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            r17 = r8
            r7 = 6
            r8 = 0
            r10.addEquality(r4, r6, r8, r7)
            goto L_0x0273
        L_0x022d:
            r17 = r8
            r7 = 6
            r8 = 0
            if (r5 == 0) goto L_0x026c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r4 = r5.mListAnchors
            r4 = r4[r47]
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r6 = r5.mListAnchors
            int r24 = r47 + 1
            r6 = r6[r24]
            androidx.constraintlayout.solver.SolverVariable r6 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r7 = r0.mListAnchors
            r7 = r7[r47]
            androidx.constraintlayout.solver.SolverVariable r7 = r7.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r8 = r0.mListAnchors
            int r28 = r47 + 1
            r8 = r8[r28]
            androidx.constraintlayout.solver.SolverVariable r8 = r8.mSolverVariable
            r41 = r5
            androidx.constraintlayout.solver.ArrayRow r5 = r45.createRow()
            r28 = r5
            r29 = r25
            r30 = r2
            r31 = r1
            r32 = r4
            r33 = r6
            r34 = r7
            r35 = r8
            r28.createRowEqualMatchDimensions(r29, r30, r31, r32, r33, r34, r35)
            r10.addConstraint(r5)
            goto L_0x026e
        L_0x026c:
            r41 = r5
        L_0x026e:
            r4 = r0
            r5 = r1
            r25 = r5
            r5 = r4
        L_0x0273:
            int r3 = r3 + 1
            r0 = r44
            r8 = r17
            r1 = r36
            r4 = r37
            r6 = r38
            r7 = r39
            goto L_0x01c7
        L_0x0283:
            r36 = r1
            r37 = r4
            r41 = r5
            r38 = r6
            r39 = r7
            r17 = r8
            r25 = r2
            goto L_0x02a5
        L_0x0292:
            r36 = r1
            r37 = r4
            r38 = r6
            r39 = r7
            r17 = r8
            goto L_0x02a5
        L_0x029d:
            r37 = r4
            r38 = r6
            r39 = r7
            r17 = r8
        L_0x02a5:
            if (r14 == 0) goto L_0x034c
            if (r14 == r15) goto L_0x02b6
            if (r19 == 0) goto L_0x02ac
            goto L_0x02b6
        L_0x02ac:
            r35 = r9
            r30 = r37
            r31 = r38
            r33 = r39
            goto L_0x0354
        L_0x02b6:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r13.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r12.mListAnchors
            r3 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x02d1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r12.mListAnchors
            r3 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x02d2
        L_0x02d1:
            r3 = 0
        L_0x02d2:
            r20 = r3
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            if (r3 == 0) goto L_0x02e9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r3 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r3 = r3.mSolverVariable
            goto L_0x02ea
        L_0x02e9:
            r3 = 0
        L_0x02ea:
            r24 = r3
            if (r14 != r15) goto L_0x02fb
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            r1 = r3[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r14.mListAnchors
            int r4 = r47 + 1
            r2 = r3[r4]
            r8 = r1
            r7 = r2
            goto L_0x02fd
        L_0x02fb:
            r8 = r1
            r7 = r2
        L_0x02fd:
            if (r20 == 0) goto L_0x033e
            if (r24 == 0) goto L_0x033e
            r1 = 1056964608(0x3f000000, float:0.5)
            if (r46 != 0) goto L_0x030a
            float r1 = r9.mHorizontalBiasPercent
            r26 = r1
            goto L_0x030e
        L_0x030a:
            float r1 = r9.mVerticalBiasPercent
            r26 = r1
        L_0x030e:
            int r27 = r8.getMargin()
            int r28 = r7.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r8.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r6 = r7.mSolverVariable
            r29 = 5
            r1 = r45
            r3 = r20
            r30 = r37
            r4 = r27
            r5 = r26
            r32 = r6
            r31 = r38
            r6 = r24
            r34 = r7
            r33 = r39
            r7 = r32
            r32 = r8
            r8 = r28
            r35 = r9
            r9 = r29
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x034a
        L_0x033e:
            r34 = r7
            r32 = r8
            r35 = r9
            r30 = r37
            r31 = r38
            r33 = r39
        L_0x034a:
            goto L_0x0636
        L_0x034c:
            r35 = r9
            r30 = r37
            r31 = r38
            r33 = r39
        L_0x0354:
            if (r22 == 0) goto L_0x04a2
            if (r14 == 0) goto L_0x04a2
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x0367
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x0367
            r27 = 1
            goto L_0x0369
        L_0x0367:
            r27 = 0
        L_0x0369:
            r20 = r27
            r9 = r1
            r8 = r2
        L_0x036d:
            if (r9 == 0) goto L_0x049a
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r9.mNextChainWidget
            r1 = r1[r46]
            r7 = r1
        L_0x0374:
            if (r7 == 0) goto L_0x0383
            int r1 = r7.getVisibility()
            r5 = 8
            if (r1 != r5) goto L_0x0385
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r7.mNextChainWidget
            r7 = r1[r46]
            goto L_0x0374
        L_0x0383:
            r5 = 8
        L_0x0385:
            if (r7 != 0) goto L_0x0393
            if (r9 != r15) goto L_0x038a
            goto L_0x0393
        L_0x038a:
            r0 = r5
            r39 = r7
            r40 = r8
            r41 = r9
            goto L_0x0488
        L_0x0393:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            r6 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r4 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x03a2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            goto L_0x03a3
        L_0x03a2:
            r1 = 0
        L_0x03a3:
            if (r8 == r9) goto L_0x03b0
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r8.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r1 = r2.mSolverVariable
            r23 = r1
            goto L_0x03cc
        L_0x03b0:
            if (r9 != r14) goto L_0x03ca
            if (r8 != r9) goto L_0x03ca
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            if (r2 == 0) goto L_0x03c5
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r12.mListAnchors
            r2 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r2.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x03c6
        L_0x03c5:
            r2 = 0
        L_0x03c6:
            r1 = r2
            r23 = r1
            goto L_0x03cc
        L_0x03ca:
            r23 = r1
        L_0x03cc:
            r1 = 0
            r2 = 0
            r3 = 0
            int r24 = r6.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r9.mListAnchors
            int r27 = r47 + 1
            r0 = r0[r27]
            int r0 = r0.getMargin()
            if (r7 == 0) goto L_0x03f4
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r7.mListAnchors
            r1 = r5[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r9.mListAnchors
            int r28 = r47 + 1
            r5 = r5[r28]
            androidx.constraintlayout.solver.SolverVariable r3 = r5.mSolverVariable
            r28 = r1
            r29 = r2
            r31 = r3
            goto L_0x040e
        L_0x03f4:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r13.mListAnchors
            int r28 = r47 + 1
            r5 = r5[r28]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r5.mTarget
            if (r1 == 0) goto L_0x0400
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
        L_0x0400:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r5 = r9.mListAnchors
            int r28 = r47 + 1
            r5 = r5[r28]
            androidx.constraintlayout.solver.SolverVariable r3 = r5.mSolverVariable
            r28 = r1
            r29 = r2
            r31 = r3
        L_0x040e:
            if (r28 == 0) goto L_0x0415
            int r1 = r28.getMargin()
            int r0 = r0 + r1
        L_0x0415:
            if (r8 == 0) goto L_0x0423
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r24 = r24 + r1
        L_0x0423:
            if (r4 == 0) goto L_0x047a
            if (r23 == 0) goto L_0x047a
            if (r29 == 0) goto L_0x047a
            if (r31 == 0) goto L_0x047a
            r1 = r24
            if (r9 != r14) goto L_0x043a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r2 = r2[r47]
            int r1 = r2.getMargin()
            r32 = r1
            goto L_0x043c
        L_0x043a:
            r32 = r1
        L_0x043c:
            r1 = r0
            if (r9 != r15) goto L_0x044c
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r15.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            int r1 = r2.getMargin()
            r34 = r1
            goto L_0x044e
        L_0x044c:
            r34 = r1
        L_0x044e:
            r1 = 4
            if (r20 == 0) goto L_0x0455
            r1 = 6
            r36 = r1
            goto L_0x0457
        L_0x0455:
            r36 = r1
        L_0x0457:
            r5 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r4
            r3 = r23
            r37 = r4
            r4 = r32
            r38 = r0
            r0 = 8
            r27 = r6
            r6 = r29
            r39 = r7
            r7 = r31
            r40 = r8
            r8 = r34
            r41 = r9
            r9 = r36
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0488
        L_0x047a:
            r38 = r0
            r37 = r4
            r27 = r6
            r39 = r7
            r40 = r8
            r41 = r9
            r0 = 8
        L_0x0488:
            int r1 = r41.getVisibility()
            if (r1 == r0) goto L_0x0492
            r1 = r41
            r8 = r1
            goto L_0x0494
        L_0x0492:
            r8 = r40
        L_0x0494:
            r9 = r39
            r23 = r39
            goto L_0x036d
        L_0x049a:
            r40 = r8
            r41 = r9
            r31 = r41
            goto L_0x0636
        L_0x04a2:
            r0 = 8
            if (r21 == 0) goto L_0x0636
            if (r14 == 0) goto L_0x0636
            r1 = r14
            r2 = r14
            int r3 = r11.mWidgetsMatchCount
            if (r3 <= 0) goto L_0x04b7
            int r3 = r11.mWidgetsCount
            int r4 = r11.mWidgetsMatchCount
            if (r3 != r4) goto L_0x04b7
            r27 = 1
            goto L_0x04b9
        L_0x04b7:
            r27 = 0
        L_0x04b9:
            r24 = r27
            r9 = r1
            r8 = r2
        L_0x04bd:
            if (r9 == 0) goto L_0x05ad
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r1 = r9.mNextChainWidget
            r1 = r1[r46]
        L_0x04c3:
            if (r1 == 0) goto L_0x04d0
            int r2 = r1.getVisibility()
            if (r2 != r0) goto L_0x04d0
            androidx.constraintlayout.solver.widgets.ConstraintWidget[] r2 = r1.mNextChainWidget
            r1 = r2[r46]
            goto L_0x04c3
        L_0x04d0:
            if (r9 == r14) goto L_0x0594
            if (r9 == r15) goto L_0x0594
            if (r1 == 0) goto L_0x0594
            if (r1 != r15) goto L_0x04db
            r1 = 0
            r7 = r1
            goto L_0x04dc
        L_0x04db:
            r7 = r1
        L_0x04dc:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            r6 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r5 = r6.mSolverVariable
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            if (r1 == 0) goto L_0x04eb
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r6.mTarget
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            goto L_0x04ec
        L_0x04eb:
            r1 = 0
        L_0x04ec:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r8.mListAnchors
            int r3 = r47 + 1
            r2 = r2[r3]
            androidx.constraintlayout.solver.SolverVariable r4 = r2.mSolverVariable
            r1 = 0
            r2 = 0
            r3 = 0
            int r23 = r6.getMargin()
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r9.mListAnchors
            int r28 = r47 + 1
            r0 = r0[r28]
            int r0 = r0.getMargin()
            if (r7 == 0) goto L_0x0522
            r28 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r7.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
            r28 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x051a
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x051b
        L_0x051a:
            r2 = 0
        L_0x051b:
            r31 = r2
            r29 = r28
            r28 = r1
            goto L_0x053e
        L_0x0522:
            r28 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r29 = r47 + 1
            r1 = r1[r29]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r1 = r1.mTarget
            if (r1 == 0) goto L_0x0530
            androidx.constraintlayout.solver.SolverVariable r2 = r1.mSolverVariable
        L_0x0530:
            r28 = r1
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r9.mListAnchors
            int r29 = r47 + 1
            r1 = r1[r29]
            androidx.constraintlayout.solver.SolverVariable r1 = r1.mSolverVariable
            r31 = r1
            r29 = r2
        L_0x053e:
            if (r28 == 0) goto L_0x0545
            int r1 = r28.getMargin()
            int r0 = r0 + r1
        L_0x0545:
            if (r8 == 0) goto L_0x0553
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r8.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r1 = r1.getMargin()
            int r23 = r23 + r1
        L_0x0553:
            r1 = 4
            if (r24 == 0) goto L_0x055a
            r1 = 6
            r32 = r1
            goto L_0x055c
        L_0x055a:
            r32 = r1
        L_0x055c:
            if (r5 == 0) goto L_0x0585
            if (r4 == 0) goto L_0x0585
            if (r29 == 0) goto L_0x0585
            if (r31 == 0) goto L_0x0585
            r34 = 1056964608(0x3f000000, float:0.5)
            r1 = r45
            r2 = r5
            r3 = r4
            r20 = r4
            r4 = r23
            r36 = r5
            r5 = r34
            r34 = r6
            r6 = r29
            r37 = r7
            r7 = r31
            r38 = r8
            r8 = r0
            r39 = r9
            r9 = r32
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x0591
        L_0x0585:
            r20 = r4
            r36 = r5
            r34 = r6
            r37 = r7
            r38 = r8
            r39 = r9
        L_0x0591:
            r23 = r37
            goto L_0x059a
        L_0x0594:
            r38 = r8
            r39 = r9
            r23 = r1
        L_0x059a:
            int r0 = r39.getVisibility()
            r1 = 8
            if (r0 == r1) goto L_0x05a6
            r0 = r39
            r8 = r0
            goto L_0x05a8
        L_0x05a6:
            r8 = r38
        L_0x05a8:
            r9 = r23
            r0 = r1
            goto L_0x04bd
        L_0x05ad:
            r38 = r8
            r39 = r9
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r12.mListAnchors
            r1 = r1[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r9 = r1.mTarget
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r8 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r13.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r7 = r1.mTarget
            if (r9 == 0) goto L_0x0616
            if (r14 == r15) goto L_0x05e0
            androidx.constraintlayout.solver.SolverVariable r1 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r2 = r9.mSolverVariable
            int r3 = r0.getMargin()
            r6 = 5
            r10.addEquality(r1, r2, r3, r6)
            r42 = r7
            r43 = r8
            r20 = r9
            goto L_0x061c
        L_0x05e0:
            r6 = 5
            if (r7 == 0) goto L_0x060f
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r3 = r9.mSolverVariable
            int r4 = r0.getMargin()
            androidx.constraintlayout.solver.SolverVariable r1 = r8.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r5 = r7.mSolverVariable
            int r27 = r8.getMargin()
            r28 = 5
            r29 = r1
            r1 = r45
            r20 = r5
            r5 = 1056964608(0x3f000000, float:0.5)
            r6 = r29
            r42 = r7
            r7 = r20
            r43 = r8
            r8 = r27
            r20 = r9
            r9 = r28
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x061c
        L_0x060f:
            r42 = r7
            r43 = r8
            r20 = r9
            goto L_0x061c
        L_0x0616:
            r42 = r7
            r43 = r8
            r20 = r9
        L_0x061c:
            r1 = r42
            if (r1 == 0) goto L_0x0632
            if (r14 == r15) goto L_0x0632
            r2 = r43
            androidx.constraintlayout.solver.SolverVariable r3 = r2.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r4 = r1.mSolverVariable
            int r5 = r2.getMargin()
            int r5 = -r5
            r6 = 5
            r10.addEquality(r3, r4, r5, r6)
            goto L_0x0634
        L_0x0632:
            r2 = r43
        L_0x0634:
            r31 = r39
        L_0x0636:
            if (r22 != 0) goto L_0x063a
            if (r21 == 0) goto L_0x06b7
        L_0x063a:
            if (r14 == 0) goto L_0x06b7
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r0 = r14.mListAnchors
            r0 = r0[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            if (r2 == 0) goto L_0x064f
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r0.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x0650
        L_0x064f:
            r2 = 0
        L_0x0650:
            r20 = r2
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            if (r2 == 0) goto L_0x065b
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r2 = r1.mTarget
            androidx.constraintlayout.solver.SolverVariable r2 = r2.mSolverVariable
            goto L_0x065c
        L_0x065b:
            r2 = 0
        L_0x065c:
            if (r13 == r15) goto L_0x0672
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r3 = r13.mListAnchors
            int r4 = r47 + 1
            r3 = r3[r4]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r3.mTarget
            if (r4 == 0) goto L_0x066d
            androidx.constraintlayout.solver.widgets.ConstraintAnchor r4 = r3.mTarget
            androidx.constraintlayout.solver.SolverVariable r4 = r4.mSolverVariable
            goto L_0x066e
        L_0x066d:
            r4 = 0
        L_0x066e:
            r2 = r4
            r24 = r2
            goto L_0x0674
        L_0x0672:
            r24 = r2
        L_0x0674:
            if (r14 != r15) goto L_0x0682
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            r0 = r2[r47]
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r2 = r14.mListAnchors
            int r3 = r47 + 1
            r1 = r2[r3]
            r9 = r1
            goto L_0x0683
        L_0x0682:
            r9 = r1
        L_0x0683:
            if (r20 == 0) goto L_0x06b5
            if (r24 == 0) goto L_0x06b5
            r26 = 1056964608(0x3f000000, float:0.5)
            int r27 = r0.getMargin()
            if (r15 != 0) goto L_0x0691
            r1 = r13
            r15 = r1
        L_0x0691:
            androidx.constraintlayout.solver.widgets.ConstraintAnchor[] r1 = r15.mListAnchors
            int r2 = r47 + 1
            r1 = r1[r2]
            int r28 = r1.getMargin()
            androidx.constraintlayout.solver.SolverVariable r2 = r0.mSolverVariable
            androidx.constraintlayout.solver.SolverVariable r7 = r9.mSolverVariable
            r29 = 5
            r1 = r45
            r3 = r20
            r4 = r27
            r5 = r26
            r6 = r24
            r8 = r28
            r32 = r9
            r9 = r29
            r1.addCentering(r2, r3, r4, r5, r6, r7, r8, r9)
            goto L_0x06b7
        L_0x06b5:
            r32 = r9
        L_0x06b7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.solver.widgets.Chain.applyChainConstraints(androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer, androidx.constraintlayout.solver.LinearSystem, int, int, androidx.constraintlayout.solver.widgets.ChainHead):void");
    }
}
