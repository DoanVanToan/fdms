package com.framgia.fdms.widget;

import android.app.Activity;
import android.view.View;

import uk.co.deanwild.materialshowcaseview.MaterialShowcaseSequence;
import uk.co.deanwild.materialshowcaseview.MaterialShowcaseView;

/**
 * Created by MyPC on 06/07/2017.
 */
public class FDMSShowcaseSequence extends MaterialShowcaseSequence {
    private int mCount = 0;

    public FDMSShowcaseSequence(Activity activity) {
        super(activity);
    }

    @Override
    public MaterialShowcaseSequence addSequenceItem(MaterialShowcaseView sequenceItem) {
        return super.addSequenceItem(sequenceItem);
    }

    @Override
    public MaterialShowcaseSequence addSequenceItem(View targetView, String content,
                                                    String dismissText) {
        mCount++;
        return super.addSequenceItem(targetView, content, dismissText);
    }

    public int getCount() {
        return mCount;
    }

    public void setCount(int count) {
        this.mCount = count;
    }
}
