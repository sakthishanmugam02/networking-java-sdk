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
package com.ibm.cloud.networking.caching_api.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * The updateQueryStringSort options.
 */
public class UpdateQueryStringSortOptions extends GenericModel {

  /**
   * on/off property value.
   */
  public interface Value {
    /** on. */
    String ON = "on";
    /** off. */
    String OFF = "off";
  }

  protected String value;

  /**
   * Builder.
   */
  public static class Builder {
    private String value;

    private Builder(UpdateQueryStringSortOptions updateQueryStringSortOptions) {
      this.value = updateQueryStringSortOptions.value;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Builds a UpdateQueryStringSortOptions.
     *
     * @return the new UpdateQueryStringSortOptions instance
     */
    public UpdateQueryStringSortOptions build() {
      return new UpdateQueryStringSortOptions(this);
    }

    /**
     * Set the value.
     *
     * @param value the value
     * @return the UpdateQueryStringSortOptions builder
     */
    public Builder value(String value) {
      this.value = value;
      return this;
    }
  }

  protected UpdateQueryStringSortOptions(Builder builder) {
    value = builder.value;
  }

  /**
   * New builder.
   *
   * @return a UpdateQueryStringSortOptions builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the value.
   *
   * on/off property value.
   *
   * @return the value
   */
  public String value() {
    return value;
  }
}

