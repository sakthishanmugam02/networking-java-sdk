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

package com.ibm.cloud.networking.firewall_api.v1;

import com.ibm.cloud.networking.firewall_api.v1.model.GetSecurityLevelSettingOptions;
import com.ibm.cloud.networking.firewall_api.v1.model.ResultInfo;
import com.ibm.cloud.networking.firewall_api.v1.model.SecurityLevelSettingResp;
import com.ibm.cloud.networking.firewall_api.v1.model.SecurityLevelSettingRespMessagesItem;
import com.ibm.cloud.networking.firewall_api.v1.model.SecurityLevelSettingRespResult;
import com.ibm.cloud.networking.firewall_api.v1.model.SetSecurityLevelSettingOptions;
import com.ibm.cloud.networking.firewall_api.v1.utils.TestUtilities;
import com.ibm.cloud.networking.test.SdkIntegrationTestBase;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.service.exception.ServiceResponseException;
import com.ibm.cloud.sdk.core.service.model.FileWithMetadata;
import com.ibm.cloud.sdk.core.util.CredentialUtils;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.testng.Assert.*;

/**
 * Integration test class for the FirewallApi service.
 */
public class FirewallApiIT extends SdkIntegrationTestBase {
  public FirewallApi service = null;
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
      service = FirewallApi.newInstance(crn, zoneIdentifier, serviceName);
    } catch (Exception e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    System.out.println("Setup complete.");
  }

  @Test
  public void testGetSecurityLevelSetting() throws Exception {
    try {
      GetSecurityLevelSettingOptions getSecurityLevelSettingOptions = new GetSecurityLevelSettingOptions();

      // Invoke operation
      Response<SecurityLevelSettingResp> response = service.getSecurityLevelSetting(getSecurityLevelSettingOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      SecurityLevelSettingResp securityLevelSettingRespResult = response.getResult();

      assertNotNull(securityLevelSettingRespResult);
    } catch (ServiceResponseException e) {
        fail(String.format("Service returned status code %d: %s\nError details: %s",
          e.getStatusCode(), e.getMessage(), e.getDebuggingInfo()));
    }
  }

  @Test
  public void testSetSecurityLevelSetting() throws Exception {
    try {
      SetSecurityLevelSettingOptions setSecurityLevelSettingOptions = new SetSecurityLevelSettingOptions.Builder()
      .value("under_attack")
      .build();

      // Invoke operation
      Response<SecurityLevelSettingResp> response = service.setSecurityLevelSetting(setSecurityLevelSettingOptions).execute();
      // Validate response
      assertNotNull(response);
      assertEquals(response.getStatusCode(), 200);

      SecurityLevelSettingResp securityLevelSettingRespResult = response.getResult();

      assertNotNull(securityLevelSettingRespResult);
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
