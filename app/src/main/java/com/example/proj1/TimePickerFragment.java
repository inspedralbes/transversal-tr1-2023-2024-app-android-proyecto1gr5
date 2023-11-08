package com.example.proj1;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import java.util.Calendar;

public class TimePickerFragment extends DialogFragment {
    private TimePickerListener timePickerListener;
    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        final Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        return new TimePickerDialog(requireActivity(), new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                if(timePickerListener !=null){
                    timePickerListener.onTimeSelected(hourOfDay, minute);
                }
            }
        }, hour ,minute, true);
    }

    public interface TimePickerListener{
        void onTimeSelected(int hour, int minute);
    }

    public void setTimePickerListener(TimePickerListener listener){
        this.timePickerListener = listener;
    }
}
