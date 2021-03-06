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
package com.ibm.cloud.networking.zone_rate_limits.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * Determines which traffic the rate limit counts towards the threshold. Needs to be one of "request" or "response"
 * objects.
 */
public class RatelimitInputMatch extends GenericModel {

  protected RatelimitInputMatchRequest request;
  protected RatelimitInputMatchResponse response;

  /**
   * Builder.
   */
  public static class Builder {
    private RatelimitInputMatchRequest request;
    private RatelimitInputMatchResponse response;

    private Builder(RatelimitInputMatch ratelimitInputMatch) {
      this.request = ratelimitInputMatch.request;
      this.response = ratelimitInputMatch.response;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Builds a RatelimitInputMatch.
     *
     * @return the new RatelimitInputMatch instance
     */
    public RatelimitInputMatch build() {
      return new RatelimitInputMatch(this);
    }

    /**
     * Set the request.
     *
     * @param request the request
     * @return the RatelimitInputMatch builder
     */
    public Builder request(RatelimitInputMatchRequest request) {
      this.request = request;
      return this;
    }

    /**
     * Set the response.
     *
     * @param response the response
     * @return the RatelimitInputMatch builder
     */
    public Builder response(RatelimitInputMatchResponse response) {
      this.response = response;
      return this;
    }
  }

  protected RatelimitInputMatch(Builder builder) {
    request = builder.request;
    response = builder.response;
  }

  /**
   * New builder.
   *
   * @return a RatelimitInputMatch builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the request.
   *
   * request.
   *
   * @return the request
   */
  public RatelimitInputMatchRequest request() {
    return request;
  }

  /**
   * Gets the response.
   *
   * response.
   *
   * @return the response
   */
  public RatelimitInputMatchResponse response() {
    return response;
  }
}

