package com.example.utils;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.services.dataflow.Dataflow;
import org.junit.Before;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class DataFlowServiceUtilTest {


    DataFlowServiceUtil dataFlowServiceUtil = spy(new DataFlowServiceUtil());
    Dataflow dataFlow = mock(Dataflow.class);

    @Before
    public void init() throws GeneralSecurityException, IOException {

        doReturn(dataFlow).when(dataFlowServiceUtil).buildDataFlow(any(GoogleCredential.class));
    }



}
