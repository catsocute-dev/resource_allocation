package com.fsoft.erp.modules.identity.controller;

import com.fsoft.erp.common.constant.ApiConstant;
import com.fsoft.erp.common.dto.response.ApiResponse;
import com.fsoft.erp.modules.identity.dto.request.AuthenticationRequest;
import com.fsoft.erp.modules.identity.dto.request.IntrospectRequest;
import com.fsoft.erp.modules.identity.dto.response.AuthenticationResponse;
import com.fsoft.erp.modules.identity.dto.response.IntrospectResponse;
import com.fsoft.erp.modules.identity.service.interfaces.AuthenticationService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/token")
    ResponseEntity<ApiResponse<AuthenticationResponse>> authenticate(@RequestBody AuthenticationRequest request) {
        ApiResponse<AuthenticationResponse> response = ApiResponse.<AuthenticationResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(authenticationService.authenticate(request))
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/introspect")
    ResponseEntity<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest request) throws ParseException {
        ApiResponse<IntrospectResponse> response = ApiResponse.<IntrospectResponse>builder()
                .success(ApiConstant.SUCCESS)
                .data(authenticationService.introspect(request))
                .build();
        return ResponseEntity.ok(response);
    }

}
