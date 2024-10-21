package com.pd.authentication;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import lombok.Getter;

@Getter
public class AuthorizationResponse {
  private final String state;
  private final String authenticationCode;

  public AuthorizationResponse(String authorizationResponseUrl) {
    var params = extractParams(authorizationResponseUrl);
    this.state = extractParamValue(params, "state");
    this.authenticationCode = extractParamValue(params, "code");
  }

  private static Collection<String> extractParams(String authorizationResponseUrl) {
    return Arrays.stream(authorizationResponseUrl.split("\\?")[1].split("&")).toList();
  }

  private String extractParamValue(Collection<String> params, String paramName) {
    List<String> paramsMatchingName = params.stream().filter(p -> p.contains(paramName)).toList();
    return paramsMatchingName.get(0).split("=")[1];
  }
}
