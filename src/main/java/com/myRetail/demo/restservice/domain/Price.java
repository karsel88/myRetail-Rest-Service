package com.myRetail.demo.restservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

  @JsonProperty(value = "value")
  private Double price;
  @JsonProperty(value = "currency_code")
  private String currencyCode;
  private String message;

}
