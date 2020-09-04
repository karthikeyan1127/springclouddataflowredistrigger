package com.example;

import com.example.Example.GCSEvent;
import com.example.utils.DataFlowServiceUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class Example implements BackgroundFunction<GCSEvent> {

  private DataFlowServiceUtil dataFlowServiceUtil;

  public Example(){
    dataFlowServiceUtil = new DataFlowServiceUtil();
  }
  @Override
  public void accept(GCSEvent event, Context context) throws GeneralSecurityException, IOException {

    dataFlowServiceUtil.launchDataFlow(GoogleCredential.getApplicationDefault());
  }

  public static class GCSEvent {
    String bucket;
    String name;
    String metageneration;
  }

}