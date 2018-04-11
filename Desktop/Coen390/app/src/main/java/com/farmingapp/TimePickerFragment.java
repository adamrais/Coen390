package com.farmingapp;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.widget.TimePicker;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener{

    OnSelectListener mOnTimeSetListener;

    private int hour;
    private int minute;
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            return new TimePickerDialog(getActivity(), this, hour, minute,
                    DateFormat.is24HourFormat(getActivity()));
        }

        public void setTime(int hour,int minute){
            Calendar calendar = Calendar.getInstance();
            if(hour == 0) hour = calendar.get(Calendar.HOUR_OF_DAY);
            if(minute == 0) minute = calendar.get(Calendar.MINUTE);
            this.hour = hour;
            this.minute = minute;
        }

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            //
//            Log.d("onTimeSet", "hourOfDay: " + hourOfDay + "Minute: " + minute);
            mOnTimeSetListener.getValue( hourOfDay,minute);
        }

        public void setOnSelectListener(OnSelectListener onSelectListener) {
            mOnTimeSetListener = onSelectListener;
        }

        public interface OnSelectListener {
            public void getValue( int hourOfDay, int minute);
        }
}
