package com.exem.util.alarm;

import com.exem.util.alarm.form.ConnectInfo;
import com.exem.util.alarm.form.JandiMessage;


public interface Webhook {

    boolean sendMessage(JandiMessage message);

    boolean sendMessage(String body, String connectColor, ConnectInfo connectInfos);
}
