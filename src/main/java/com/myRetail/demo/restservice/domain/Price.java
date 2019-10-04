package com.myRetail.demo.restservice.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Price {

  @JsonProperty(value = "value")
  @NotBlank(message = "Please provide price")
  @DecimalMin("0.01")
  private Double price;

  @JsonProperty(value = "currency_code")
  @NotBlank(message = "Please provide valid currency code")
  private String currencyCode;
  private String message;

}
