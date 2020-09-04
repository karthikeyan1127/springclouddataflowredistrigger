package com.example.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.dataflow.Dataflow;
import com.google.api.services.dataflow.model.LaunchTemplateParameters;
import com.google.api.services.dataflow.model.RuntimeEnvironment;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import static com.example.constants.CloudFunctionConstants.*;

public class DataFlowServiceUtil {

    String projectId = "tidal-pathway-285009";
    String applicationName = "Google Cloud Platform Sample";
    String tempLocation = "gs://cloud-dataflow-pipeline-bucket-karthik/tmp/";
    String jobName = "CloudStorageToRedis";
    String inputFileName = "gs://bucket12345678jj/*.txt";
    String templateGCSPath = "gs://cloud-dataflow-pipeline-bucket-karthik/templates/dataflow-custom-redis-template";

    private static final Logger logger = Logger.getLogger(DataFlowServiceUtil.class.getName());


    public void launchDataFlow(GoogleCredential credential) throws GeneralSecurityException, IOException {

        try {
            RuntimeEnvironment runtimeEnvironment = new RuntimeEnvironment();
            runtimeEnvironment.setBypassTempDirValidation(false);
            runtimeEnvironment.setTempLocation(tempLocation);

            LaunchTemplateParameters launchTemplateParameters = new LaunchTemplateParameters();
            launchTemplateParameters.setEnvironment(runtimeEnvironment);
            launchTemplateParameters.setJobName(jobName + (new Date()).getTime());
            Map<String, String> params = new HashMap<String, String>();
            params.put(INPUT_FILE, inputFileName);
            launchTemplateParameters.setParameters(params);

            if (credential.createScopedRequired()) {
                credential = credential.createScoped(Collections.singletonList(SCOPE));
            }

            Dataflow dataflowService = buildDataFlow(credential);
            Dataflow.Projects.Templates.Launch launch = dataflowService.projects().templates().launch(projectId, launchTemplateParameters);
            launch.setGcsPath(templateGCSPath);
            launch.execute();
            logger.info(SUCCESS_MESSAGE);

        }catch(Exception e){
            logger.info(e.toString());
        }
    }

    Dataflow buildDataFlow(GoogleCredential credential) throws GeneralSecurityException, IOException {

        HttpTransport httpTransport = GoogleNetHttpTransport.newTrustedTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        return new Dataflow.Builder(httpTransport, jsonFactory, credential)
                .setApplicationName(applicationName)
                .build();
    }

}
