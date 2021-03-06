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
package com.ibm.cloud.networking.direct_link_provider.v2;

import static org.junit.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.util.Date;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import com.ibm.cloud.networking.direct_link.v1.DirectLink;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ListProviderPortsOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderPortCollection;
import com.ibm.cloud.networking.direct_link_provider.v2.model.GetProviderPortOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderPort;
import com.ibm.cloud.networking.direct_link_provider.v2.model.CreateProviderGatewayOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGateway;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ListProviderGatewaysOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayCollection;
import com.ibm.cloud.networking.direct_link_provider.v2.model.ProviderGatewayPortIdentity;
import com.ibm.cloud.networking.direct_link_provider.v2.model.GetProviderGatewayOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.DeleteProviderGatewayOptions;
import com.ibm.cloud.networking.direct_link_provider.v2.model.UpdateProviderGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GetGatewayOptions;
import com.ibm.cloud.networking.direct_link.v1.model.Gateway;
import com.ibm.cloud.networking.direct_link.v1.model.CreateGatewayActionOptions;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayActionTemplateUpdatesItemGatewayClientSpeedUpdate;
import com.ibm.cloud.networking.direct_link.v1.model.GatewayActionTemplateUpdatesItem;
import com.ibm.cloud.networking.test.SdkIntegrationTestBase;
import com.ibm.cloud.sdk.core.http.Response;
import com.ibm.cloud.sdk.core.util.CredentialUtils;

/**
 * Integration test class for the DirectLink Provider service.
 * 
 * How to run the tests:
 *    mvn -Dtest=DirectLinkProviderIT -DfailIfNoTests=false test
 */

public class DirectLinkProviderIT extends SdkIntegrationTestBase {
    // Directlink Provider service v2
    public DirectLinkProvider dlProviderTestService = null;

    // Directlink service v1
    public DirectLink dlTestService = null;
    
	String gatewayId = null;
	String firstPortId = null;

	Map<String, String> config = null;

	/**
	 * This method provides our config filename to the base class.
	 */
	public String getConfigFilename() {
		return "../../directlink.env";
	}

	/**
	 * This method is invoked before any of the @Test-annotated methods, and is
	 * responsible for creating the instance of the service that will be used by the
	 * rest of the test methods, as well as any other required initialization.
	 */
	@BeforeClass
	public void constructService() {
		// Ask super if we should skip the tests.
		if (skipTests()) {
			return;
		}

		/**
		 * Construct our service client instance via external config (see the
		 * example-service.env file for details). The newInstance() method will load up
		 * our config file and look for properties that start with "EXAMPLE_SERVICE_"
		 * (the default service name associated with the Example Service, as specified
		 * by the ExampleService.DEFAULT_SERVICE_NAME constant). Specifically,
		 * newInstance() will construct an authenticator based on the value of the
		 * EXAMPLE_SERVICE_AUTH_TYPE property. After constructing the appropriate
		 * authenticator, it will construct an instance of the service and then set the
		 * URL to the value specified by the EXAMPLE_SERVICE_URL property.
		 */
		final String providerServiceName = "network_provider_service";
		final String serviceName = "network_service";

		// Load up the config properties for this service.
		config = CredentialUtils.getServiceProperties(providerServiceName);

		String version;
		try {
            version = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            dlProviderTestService = DirectLinkProvider.newInstance(version, providerServiceName);
            dlTestService = DirectLink.newInstance(version, serviceName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@AfterClass
	public void cleanup() {
		// Construct an instance of the DeleteGatewayOptions model
		if (gatewayId == null) {
			return;
		}

		// Construct an instance of the DeleteProviderGatewayOptions model and re-send delete request using provider account
		DeleteProviderGatewayOptions deleteProviderGatewayOptionsModel = new DeleteProviderGatewayOptions.Builder(gatewayId).build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderGateway> deleteGWRes = dlProviderTestService.deleteProviderGateway(deleteProviderGatewayOptionsModel).execute();
		assertNotNull(deleteGWRes);
		assertEquals(202, deleteGWRes.getStatusCode());

		// Construct an instance of CreateGatewayActionOptions model and approve the gateway delete request using client account
		CreateGatewayActionOptions createGatewayActionOptionsModel = new CreateGatewayActionOptions.Builder()
				.id(gatewayId).action("delete_gateway_approve").build();
		
		Response<Gateway> response = dlTestService.createGatewayAction(createGatewayActionOptionsModel).execute();
		assertNotNull(response);
		assertEquals(204, response.getStatusCode());

		// Response does not have a return type. Check that the result is null.
		assertNull(response.getResult());
	}
	
	@Test 
	public void testProviderPorts() { 
        assertNotNull(dlProviderTestService);

		// Construct an instance of the ListProviderPortsOptions model
		ListProviderPortsOptions listProviderPortsOptionsModel = new ListProviderPortsOptions.Builder().build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderPortCollection> res = dlProviderTestService.listProviderPorts(listProviderPortsOptionsModel).execute();
		assertNotNull(res);
		assertEquals(200, res.getStatusCode());
		ProviderPortCollection resObj = res.getResult();
		assertNotNull(resObj);

		// Save the first port found for get port and connect gw tests
		firstPortId = resObj.getPorts().get(0).getId();

		// ***************** Get details of the 1st port *********************
		// Construct an instance of the GetProviderPortOptions model
		GetProviderPortOptions getProviderPortOptionsModel = new GetProviderPortOptions.Builder().id(firstPortId).build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderPort> resp = dlProviderTestService.getProviderPort(getProviderPortOptionsModel).execute();
		assertNotNull(resp);
		assertEquals(200, res.getStatusCode());
		ProviderPort respObj = resp.getResult();
		assertNotNull(respObj);
    }

    @Test (dependsOnMethods = "testProviderPorts")
    public void testProviderGateways(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		String customerAccId = config.get("CUSTOMER_ACCT_ID");
		String gatewayName = "JAVA-INT-SDK-PROVIDER-"+timestamp; 
		String updatedGatewayName = "JAVA-INT-SDK-PROVIDER-PATCH-"+timestamp; 
		Long bgpAsn = 64999L; 
		Long speedMbps = 1000L; 

		ProviderGatewayPortIdentity portIdentity = new ProviderGatewayPortIdentity.Builder(firstPortId).build();

		assertNotNull(dlProviderTestService);

		// Construct an instance of the CreateProviderGatewayOptions model
		CreateProviderGatewayOptions createProviderGatewayOptionsModel = new CreateProviderGatewayOptions.Builder()
				.bgpAsn(bgpAsn).customerAccountId(customerAccId).name(gatewayName).speedMbps(speedMbps).port(portIdentity).build();

		Response<ProviderGateway> createGWRes = dlProviderTestService.createProviderGateway(createProviderGatewayOptionsModel).execute();
		assertNotNull(createGWRes);
		assertEquals(201, createGWRes.getStatusCode());
		ProviderGateway createRespObj = createGWRes.getResult();
		assertNotNull(createRespObj);

		// Save the gateway created
		gatewayId = createRespObj.getId();

		// Construct an instance of the GetProviderGatewayOptions model
		GetProviderGatewayOptions getProviderGatewayOptionsModel = new GetProviderGatewayOptions.Builder(gatewayId).build();
		Response<ProviderGateway> getGWRes = dlProviderTestService.getProviderGateway(getProviderGatewayOptionsModel).execute();
		assertNotNull(getGWRes);
		assertEquals(200, getGWRes.getStatusCode());
		ProviderGateway getRespObj = getGWRes.getResult();
		assertNotNull(getRespObj);

		// Construct an instance of the ListProviderGatewaysOptions model
		ListProviderGatewaysOptions listProviderGatewaysOptionsModel = new ListProviderGatewaysOptions.Builder().build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderGatewayCollection> listGWsRes = dlProviderTestService.listProviderGateways(listProviderGatewaysOptionsModel).execute();
		assertNotNull(listGWsRes);
		assertEquals(200, listGWsRes.getStatusCode());
		ProviderGatewayCollection listRespObj = listGWsRes.getResult();
		assertNotNull(listRespObj);

		// Construct an instance of the UpdateProviderGatewayOptions model
		UpdateProviderGatewayOptions updateProviderGatewayOptionsModel = new UpdateProviderGatewayOptions.Builder()
				.id(gatewayId).name(updatedGatewayName).build();

		// Invoke operation with valid options model 
		try { 
			Response<ProviderGateway> updateGWRes = dlProviderTestService.updateProviderGateway(updateProviderGatewayOptionsModel).execute();
			assertNotNull(updateGWRes); 
			assertEquals(400, updateGWRes.getStatusCode()); 
		} catch (com.ibm.cloud.sdk.core.service.exception.BadRequestException errResponse) {
			assertEquals("Cannot update a gateway with current status", errResponse.getMessage());
			assertEquals(400, errResponse.getStatusCode());
		}

		// Construct an instance of the DeleteProviderGatewayOptions model
		DeleteProviderGatewayOptions deleteProviderGatewayOptionsModel = new DeleteProviderGatewayOptions.Builder(gatewayId).build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderGateway> deleteGWRes = dlProviderTestService.deleteProviderGateway(deleteProviderGatewayOptionsModel).execute();
		assertNotNull(deleteGWRes);
		assertEquals(204, deleteGWRes.getStatusCode());

		gatewayId = null; // already cleaned up System.out.
	}
		
	@Test(dependsOnMethods = "testProviderPorts")
	public void testProviderGatewaysWithClientApi(){
		Long timestamp = new Timestamp(System.currentTimeMillis()).getTime(); 
		String customerAccId = config.get("CUSTOMER_ACCT_ID");
		String gatewayName = "JAVA-INT-SDK-PROVIDER-"+timestamp; 
		String updatedGatewayName = "JAVA-INT-SDK-PROVIDER-PATCH-"+timestamp; 
		Long bgpAsn = 64999L; 
		Long speedMbps = 1000L;
		Long updatedSpeedMbps = 2000L; 

		ProviderGatewayPortIdentity portIdentity = new ProviderGatewayPortIdentity.Builder(firstPortId).build();

		assertNotNull(dlProviderTestService);
		assertNotNull(dlTestService);

		// Construct an instance of the CreateProviderGatewayOptions model
		CreateProviderGatewayOptions createProviderGatewayOptionsModel = new CreateProviderGatewayOptions.Builder()
				.bgpAsn(bgpAsn).customerAccountId(customerAccId).name(gatewayName).speedMbps(speedMbps).port(portIdentity).build();

		Response<ProviderGateway> createGWRes = dlProviderTestService.createProviderGateway(createProviderGatewayOptionsModel).execute();
		assertNotNull(createGWRes);
		assertEquals(201, createGWRes.getStatusCode());
		ProviderGateway createGWRespObj = createGWRes.getResult();
		assertNotNull(createGWRespObj);

		// Save the gateway created
		gatewayId = createGWRespObj.getId();

		// Construct an instance of the GetGatewayOptions model
		GetGatewayOptions getGatewayOptionsModel = new GetGatewayOptions.Builder(gatewayId).build();
		Response<Gateway> getGWRes = dlTestService.getGateway(getGatewayOptionsModel).execute();
		assertNotNull(getGWRes);
		assertEquals(200, getGWRes.getStatusCode());
		Gateway getGWRespObj = getGWRes.getResult();
		assertNotNull(getGWRespObj);
		assertEquals(getGWRespObj.getId(), gatewayId);
		assertEquals(getGWRespObj.getName(), gatewayName);
		assertEquals(getGWRespObj.getBgpAsn(), bgpAsn);
		assertEquals(getGWRespObj.getSpeedMbps(), speedMbps);
		assertEquals(getGWRespObj.getOperationalStatus(), "create_pending");
		assertEquals(getGWRespObj.getPort().getId(), firstPortId);
		assertNotNull(getGWRespObj.getChangeRequest());

		// Construct an instance of CreateGatewayActionOptions model
		CreateGatewayActionOptions createGatewayActionOptionsModel = new CreateGatewayActionOptions.Builder()
				.id(gatewayId).action("create_gateway_approve").global(false).metered(false).build();
		
		Response<Gateway> actionGWRes = dlTestService.createGatewayAction(createGatewayActionOptionsModel).execute();
		assertNotNull(actionGWRes);
		assertEquals(200, actionGWRes.getStatusCode());
		Gateway actionGWResObj = actionGWRes.getResult();
		assertNotNull(actionGWResObj);
		assertEquals(actionGWResObj.getId(), gatewayId);
		assertEquals(actionGWResObj.getName(), gatewayName);
		assertEquals(actionGWResObj.getBgpAsn(), bgpAsn);
		assertEquals(actionGWResObj.getSpeedMbps(), speedMbps);
		assertEquals(actionGWResObj.getOperationalStatus(), "create_pending");
		assertEquals(actionGWResObj.getPort().getId(), firstPortId);
		assertNull(actionGWResObj.getChangeRequest());

		// Construct an instance of GetGatewayOptions model and wait for gateway to move to provisioned state
		getGatewayOptionsModel = new GetGatewayOptions.Builder(gatewayId).build();

		boolean done = false;
		int timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			Response<Gateway> res = dlTestService.getGateway(getGatewayOptionsModel).execute();
			assertNotNull(res);
			assertEquals(200, res.getStatusCode());

			Gateway responseObj = res.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Construct an instance of the UpdateProviderGatewayOptions model and update the name
		UpdateProviderGatewayOptions updateProviderGatewayOptionsModel = new UpdateProviderGatewayOptions.Builder()
				.id(gatewayId).name(updatedGatewayName).build();
		Response<ProviderGateway> updateGWRes = dlProviderTestService.updateProviderGateway(updateProviderGatewayOptionsModel).execute();
		assertNotNull(updateGWRes); 
		assertEquals(200, updateGWRes.getStatusCode()); 
		ProviderGateway updateGWResObj = updateGWRes.getResult();
		assertNotNull(updateGWResObj);
		assertEquals(updateGWResObj.getId(), gatewayId);
		assertEquals(updateGWResObj.getName(), updatedGatewayName);

		// Request Speed update of the gateway
		UpdateProviderGatewayOptions speedUpdateProviderGatewayOptionsModel = new UpdateProviderGatewayOptions.Builder()
				.id(gatewayId).speedMbps(updatedSpeedMbps).build();
		updateGWRes = dlProviderTestService.updateProviderGateway(speedUpdateProviderGatewayOptionsModel).execute();
		assertNotNull(updateGWRes); 
		assertEquals(200, updateGWRes.getStatusCode()); 
		updateGWResObj = updateGWRes.getResult();
		assertNotNull(updateGWResObj);
		assertEquals(updateGWResObj.getId(), gatewayId);
		assertEquals(updateGWResObj.getName(), updatedGatewayName);
		assertEquals(updateGWResObj.getSpeedMbps(), speedMbps); // Speed not yet updated

		// Construct an instance GatewayActionTemplateUpdatesItemGatewayClientSpeedUpdate
		List <GatewayActionTemplateUpdatesItem> updatesAction = new ArrayList<GatewayActionTemplateUpdatesItem>();
		GatewayActionTemplateUpdatesItemGatewayClientSpeedUpdate speedUpdate = new GatewayActionTemplateUpdatesItemGatewayClientSpeedUpdate.Builder()
				.speedMbps(updatedSpeedMbps).build();

		updatesAction.add(speedUpdate);
		// Construct an instance of CreateGatewayActionOptions model
		createGatewayActionOptionsModel = new CreateGatewayActionOptions.Builder()
				.id(gatewayId).action("update_attributes_approve").updates(updatesAction).build();
		
		getGWRes = dlTestService.createGatewayAction(createGatewayActionOptionsModel).execute();
		assertNotNull(getGWRes);
		assertEquals(200, getGWRes.getStatusCode());
		Gateway getGWResObj = getGWRes.getResult();
		assertNotNull(getGWResObj);
		assertEquals(getGWResObj.getId(), gatewayId);
		assertEquals(getGWResObj.getName(), updatedGatewayName);
		assertEquals(getGWResObj.getBgpAsn(), bgpAsn);
		assertEquals(getGWResObj.getSpeedMbps(), updatedSpeedMbps);
		assertEquals(getGWResObj.getOperationalStatus(), "provisioned");
		assertEquals(getGWResObj.getPort().getId(), firstPortId);
		assertNull(getGWResObj.getChangeRequest());

		// Successfully wait for the gateway to go back to provisioned state
		// getGatewayOptionsModel = new GetGatewayOptions.Builder(gatewayId).build();
		GetProviderGatewayOptions getProviderGatewayOptionsModel = new GetProviderGatewayOptions.Builder(gatewayId).build();

		done = false;
		timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			// Response<Gateway> res = dlTestService.getGateway(getGatewayOptionsModel).execute();
			Response<ProviderGateway> GWRes = dlProviderTestService.getProviderGateway(getProviderGatewayOptionsModel).execute();
			assertNotNull(GWRes);
			assertEquals(200, GWRes.getStatusCode());

			ProviderGateway responseObj = GWRes.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Construct an instance of the DeleteProviderGatewayOptions model and send delete request using provider account
		DeleteProviderGatewayOptions deleteProviderGatewayOptionsModel = new DeleteProviderGatewayOptions.Builder(gatewayId).build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderGateway> res = dlProviderTestService.deleteProviderGateway(deleteProviderGatewayOptionsModel).execute();
		assertNotNull(res);
		assertEquals(202, res.getStatusCode());

		// Construct an instance of CreateGatewayActionOptions model and reject the gateway delete request using client account
		createGatewayActionOptionsModel = new CreateGatewayActionOptions.Builder()
				.id(gatewayId).action("delete_gateway_reject").build();
		
		getGWRes = dlTestService.createGatewayAction(createGatewayActionOptionsModel).execute();
		assertNotNull(getGWRes);
		assertEquals(200, getGWRes.getStatusCode());

		// Construct an instance of the GetGatewayOptions model and successfully verify reject gateway using client account
		getGatewayOptionsModel = new GetGatewayOptions.Builder(gatewayId).build();
		getGWRes = dlTestService.getGateway(getGatewayOptionsModel).execute();
		assertNotNull(getGWRes);
		assertEquals(200, getGWRes.getStatusCode());
		getGWResObj = getGWRes.getResult();
		assertNotNull(getGWResObj);
		assertEquals(getGWResObj.getId(), gatewayId);
		assertNull(getGWResObj.getChangeRequest());

		// Successfully wait for the gateway to go back to provisioned state
		// getGatewayOptionsModel = new GetGatewayOptions.Builder(gatewayId).build();
		getProviderGatewayOptionsModel = new GetProviderGatewayOptions.Builder(gatewayId).build();

		done = false;
		timerCount = 1;

		while (!done) {
			// Invoke operation with valid options model (positive test)
			// Response<Gateway> res = dlTestService.getGateway(getGatewayOptionsModel).execute();
			Response<ProviderGateway> GWRes = dlProviderTestService.getProviderGateway(getProviderGatewayOptionsModel).execute();
			assertNotNull(GWRes);
			assertEquals(200, GWRes.getStatusCode());

			ProviderGateway responseObj = GWRes.getResult();
			assertNotNull(responseObj);

			if (responseObj.getOperationalStatus().equals("provisioned")) {
				done = true;
				break;
			} else if (timerCount > 40) {
				assertEquals("provisioned", responseObj.getOperationalStatus());
				done = true;
			} else {
				++timerCount;
				try {
					Thread.sleep(3000);	// 2 minute wait 3sec x 40 attempts
				} catch (InterruptedException e) {
					// 
				}
			}
		}

		// Construct an instance of the DeleteProviderGatewayOptions model and re-send delete request using provider account
		deleteProviderGatewayOptionsModel = new DeleteProviderGatewayOptions.Builder(gatewayId).build();
		// Invoke operation with valid options model (positive test)
		Response<ProviderGateway> deleteGWRes = dlProviderTestService.deleteProviderGateway(deleteProviderGatewayOptionsModel).execute();
		assertNotNull(deleteGWRes);
		assertEquals(202, deleteGWRes.getStatusCode());

		// Construct an instance of CreateGatewayActionOptions model and approve the gateway delete request using client account
		createGatewayActionOptionsModel = new CreateGatewayActionOptions.Builder()
				.id(gatewayId).action("delete_gateway_approve").build();
		
		Response<Gateway> response = dlTestService.createGatewayAction(createGatewayActionOptionsModel).execute();
		assertNotNull(response);
		assertEquals(204, response.getStatusCode());

		gatewayId = null; // already cleaned up System.out.
	}
}