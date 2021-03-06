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
package com.ibm.cloud.networking.firewall_api.v1.model;

import com.ibm.cloud.sdk.core.service.model.GenericModel;

/**
 * The setSecurityLevelSetting options.
 */
public class SetSecurityLevelSettingOptions extends GenericModel {

  /**
   * security level.
   */
  public interface Value {
    /** essentially_off. */
    String ESSENTIALLY_OFF = "essentially_off";
    /** low. */
    String LOW = "low";
    /** medium. */
    String MEDIUM = "medium";
    /** high. */
    String HIGH = "high";
    /** under_attack. */
    String UNDER_ATTACK = "under_attack";
  }

  protected String value;

  /**
   * Builder.
   */
  public static class Builder {
    private String value;

    private Builder(SetSecurityLevelSettingOptions setSecurityLevelSettingOptions) {
      this.value = setSecurityLevelSettingOptions.value;
    }

    /**
     * Instantiates a new builder.
     */
    public Builder() {
    }

    /**
     * Builds a SetSecurityLevelSettingOptions.
     *
     * @return the new SetSecurityLevelSettingOptions instance
     */
    public SetSecurityLevelSettingOptions build() {
      return new SetSecurityLevelSettingOptions(this);
    }

    /**
     * Set the value.
     *
     * @param value the value
     * @return the SetSecurityLevelSettingOptions builder
     */
    public Builder value(String value) {
      this.value = value;
      return this;
    }
  }

  protected SetSecurityLevelSettingOptions(Builder builder) {
    value = builder.value;
  }

  /**
   * New builder.
   *
   * @return a SetSecurityLevelSettingOptions builder
   */
  public Builder newBuilder() {
    return new Builder(this);
  }

  /**
   * Gets the value.
   *
   * security level.
   *
   * @return the value
   */
  public String value() {
    return value;
  }
}

