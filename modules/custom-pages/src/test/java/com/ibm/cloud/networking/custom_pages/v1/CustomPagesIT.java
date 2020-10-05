/*
 * (C) Copyright IBM Corp. 2020.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package com.ibm.cloud.networking.custom_pages.v1;

import com.ibm.cloud.networking.custom_pages.v1.model.CustomPageObject;
import com.ibm.cloud.networking.custom_pages.v1.model.CustomPageSpecificResp;
import com.ibm.cloud.networking.custom_pages.v1.model.GetInstanceCustomPageOptions;
import com.ibm.cloud.networking.custom_pages.v1.model.GetZoneCustomPageOptions;
import com.ibm.cloud.networking.custom_pages.v1.model.ListCustomPagesResp;
import com.ibm.cloud.networking.custom_pages.v1.model.ListCustomPagesRespResultInfo;
import com.ibm.cloud.networking.custom_pages.v1.model.ListInstanceCustomPagesOptions;
import com.ibm.cloud.networking.custom_pages.v1.model.ListZoneCustomPagesOptions;
import com.ibm.cloud.networking.custom_pages.v1.model.UpdateInstanceCustomPageOptions;
import com.ibm.cloud.networking.custom_pages.v1.model.UpdateZoneCustomPageOptions;
import com.ibm.cloud.networking.custom_pages.v1.utils.TestUtilities;
import com.ibm.cloud.networking.test.SdkIntegrationTestBase;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.cloud.sdk.core.service.model.FileWithMetadata;
import com.ibm.cloud.sdk.core.util.CredentialUtils;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Integration test class for the CustomPages service.
 */
public class CustomPagesIT extends SdkIntegrationTestBase {
  public CustomPages service = null;
  public static Map<String, String> config = null;
  final HashMap<String, InputStream> mockStreamMap = TestUtilities.createMockStreamMap();
  final List<FileWithMetadata> mockListFileWithMetadata = TestUtilities.creatMockListFileWithMetadata();

  String crn = null;
  String zoneIdentifier = null;
  /**
   * This method provides our config filename to the base class.
   */

  public String getConfigFilename() {
    return "../../cloud_internet_services.env";
  }

  @BeforeClass
  public void constructService() {
    // Ask super if we should skip the tests.
    if (skipTests()) {
      return;
    }

    final String serviceName = "cloud_internet_services";
    // Load up the config properties for this service.
    config = CredentialUtils.getServiceProperties(serviceName);
   
    // Load Config
    crn = config.get("CRN");
    zoneIdentifier = config.get("ZONE_ID");

    // set mock values for global params
    try {
      service = CustomPages.newInstance(crn, zoneIdentifier, serviceName);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Setup complete.");
  }

  @Test
  public void testListInstanceCustomPages() throws Exception {
    try {
      ListInstanceCustomPagesOptions listInstanceCustomPagesOptions = new ListInstanceCustomPagesOptions();

      // Invoke operation
      Response<ListCustomPagesResp> response = service.listInstanceCustomPages(listInstanceCustomPagesOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      ListCustomPagesResp listCustomPagesRespResult = response.getResult();

      assertNotNull(listCustomPagesRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testGetInstanceCustomPage() throws Exception {
    try {
      GetInstanceCustomPageOptions getInstanceCustomPageOptions = new GetInstanceCustomPageOptions.Builder()
      .pageIdentifier("basic_challenge")
      .build();

      // Invoke operation
      Response<CustomPageSpecificResp> response = service.getInstanceCustomPage(getInstanceCustomPageOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      CustomPageSpecificResp customPageSpecificRespResult = response.getResult();

      assertNotNull(customPageSpecificRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testUpdateInstanceCustomPage() throws Exception {
    try {
      UpdateInstanceCustomPageOptions updateInstanceCustomPageOptions = new UpdateInstanceCustomPageOptions.Builder()
      .pageIdentifier("basic_challenge")
      .url("http://beta.travis-kuganes1.sdk.cistest-load.com/")
      .state("customized")
      .build();

      // Invoke operation
      Response<CustomPageSpecificResp> response = service.updateInstanceCustomPage(updateInstanceCustomPageOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      CustomPageSpecificResp customPageSpecificRespResult = response.getResult();

      assertNotNull(customPageSpecificRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testListZoneCustomPages() throws Exception {
    try {
      ListZoneCustomPagesOptions listZoneCustomPagesOptions = new ListZoneCustomPagesOptions();

      // Invoke operation
      Response<ListCustomPagesResp> response = service.listZoneCustomPages(listZoneCustomPagesOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      ListCustomPagesResp listCustomPagesRespResult = response.getResult();

      assertNotNull(listCustomPagesRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testGetZoneCustomPage() throws Exception {
    try {
      GetZoneCustomPageOptions getZoneCustomPageOptions = new GetZoneCustomPageOptions.Builder()
      .pageIdentifier("basic_challenge")
      .build();

      // Invoke operation
      Response<CustomPageSpecificResp> response = service.getZoneCustomPage(getZoneCustomPageOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      CustomPageSpecificResp customPageSpecificRespResult = response.getResult();

      assertNotNull(customPageSpecificRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testUpdateZoneCustomPage() throws Exception {
    try {
      UpdateZoneCustomPageOptions updateZoneCustomPageOptions = new UpdateZoneCustomPageOptions.Builder()
      .pageIdentifier("basic_challenge")
      .url("http://beta.travis-kuganes1.sdk.cistest-load.com/")
      .state("customized")
      .build();

      // Invoke operation
      Response<CustomPageSpecificResp> response = service.updateZoneCustomPage(updateZoneCustomPageOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      CustomPageSpecificResp customPageSpecificRespResult = response.getResult();

      assertNotNull(customPageSpecificRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @AfterClass
  public void tearDown() {
    // Add any clean up logic here
    System.out.println("Clean up complete.");
  }
 }
