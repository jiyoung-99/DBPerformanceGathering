package com.skylar.util.alarm;

import com.skylar.util.alarm.form.ConnectInfo;
import com.skylar.util.alarm.form.JandiMessage;


public interface Webhook {

    boolean sendMessage(JandiMessage message);

    boolean sendMessage(String body, String connectColor, ConnectInfo connectInfos);
}
