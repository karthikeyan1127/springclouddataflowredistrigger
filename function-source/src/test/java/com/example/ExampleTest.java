package com.example;

import com.example.utils.DataFlowServiceUtil;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.functions.Context;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.security.GeneralSecurityException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


public class ExampleTest {

    Example example = new Example();

    DataFlowServiceUtil dataFlowServiceUtil = mock(DataFlowServiceUtil.class);

    @Before
    public void init() throws GeneralSecurityException, IOException {
        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocationOnMock) throws Throwable {
                return null;
            }
        }).when(dataFlowServiceUtil).launchDataFlow(any(GoogleCredential.class));
        ReflectionTestUtils.setField(example,"dataFlowServiceUtil", dataFlowServiceUtil);
    }

    @Test
    public void testAccept() throws GeneralSecurityException, IOException {
        example.accept(new Example.GCSEvent(), new Context() {
            @Override
            public String eventId() {
                return null;
            }

            @Override
            public String timestamp() {
                return null;
            }

            @Override
            public String eventType() {
                return null;
            }

            @Override
            public String resource() {
                return null;
            }
        });

        verify(dataFlowServiceUtil, times(1)).launchDataFlow(any());
    }

}
