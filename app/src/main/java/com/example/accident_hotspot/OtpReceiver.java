package com.example.accident_hotspot;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;

public class OtpReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {

            Bundle extras = intent.getExtras();
            Status status = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

            if (status.getStatusCode() == CommonStatusCodes.SUCCESS) {

                String message = (String) extras.get(SmsRetriever.EXTRA_SMS_MESSAGE);

                // Extract 6 digit OTP
                String otp = message.replaceAll("\\D+", "").substring(0, 6);

                Intent i = new Intent(context, OtpVerificationActivity.class);
                i.putExtra("otp", otp);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        }
    }
}
