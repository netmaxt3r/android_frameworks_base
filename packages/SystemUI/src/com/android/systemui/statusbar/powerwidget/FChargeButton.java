package com.android.systemui.statusbar.powerwidget;

import com.android.systemui.R;

import android.content.Context;
import android.net.Uri;
import java.util.ArrayList;
import java.util.List;

public class FChargeButton extends PowerButton {
    String FILE = "/sys/kernel/fast_charge/force_fast_charge";
    private static final List<Uri> OBSERVED_URIS = new ArrayList<Uri>();
    static {
        
    }

    public FChargeButton() { mType = BUTTON_FCHARGE; }

    @Override
    protected void updateState(Context context) {
        if (getFastChargeState(context)) {
            mIcon = R.drawable.stat_fcharge_on;
            mState = STATE_ENABLED;
        } else {
            mIcon = R.drawable.stat_fcharge_off;
            mState = STATE_DISABLED;
        }
    }

    @Override
    protected void toggleState(Context context) {
        boolean enabled = getFastChargeState(context);
        setFastChargeState(context,!enabled);
    }


    @Override
    protected boolean handleLongClick(Context context) {
        return true;
    }

    @Override
    protected List<Uri> getObservedUris() {
        return OBSERVED_URIS;
    }

    private boolean getFastChargeState(Context context) {
        String val = getRawValue();
        boolean rVal=false;
        if(val!=null){
            if("1".equalsIgnoreCase(val.trim())) rVal=true;
        }
        return rVal;
    }
    private void setFastChargeState(Context context, boolean b) {
       if(b){
           setRawValue("1");
       }else
           setRawValue("0");
        
    }
    private String getRawValue(){
        return fileReadOneLine(FILE);
    }
    private void setRawValue(String val){
        fileWriteOneLine(FILE, val);
    }
}
