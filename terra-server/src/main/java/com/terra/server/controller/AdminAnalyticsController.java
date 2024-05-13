package com.terra.server.controller;


import ch.qos.logback.core.status.Status;
import com.terra.server.model.request.LoginDataRequest;
import com.terra.server.model.request.SearchDataRequest;
import com.terra.server.model.responce.LoginDataResponse;
import com.terra.server.model.responce.SearchDataResponse;
import com.terra.server.service.AdminAnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping(value = "/admin")
@RequiredArgsConstructor
public class AdminAnalyticsController {
    private final AdminAnalyticsService adminAnalyticsService;

    @PostMapping("/loginData")
    public ResponseEntity<LoginDataResponse> getLoginData(@RequestBody LoginDataRequest loginDataRequest) {
        LoginDataResponse response = adminAnalyticsService.getLoginData(loginDataRequest.getPage() != null?
                loginDataRequest.getPage() : 1, loginDataRequest.getDateFrom(), loginDataRequest.getDateFrom(), loginDataRequest.getSearchString());
        if (response.getErrorText() != null){
            return ResponseEntity.status(422).body(response);
        }
        return ResponseEntity.ok(response);
    }

    @PostMapping("/searchData")
    public ResponseEntity<SearchDataResponse> getSearchData(@RequestBody SearchDataRequest searchDataRequest) {
        SearchDataResponse response = new SearchDataResponse();
        response.setPage(searchDataRequest.getPage() != null ? searchDataRequest.getPage() : 1);
        response.setData(adminAnalyticsService.getSearchData(searchDataRequest.getPage() != null?
                searchDataRequest.getPage() : 1, searchDataRequest.getSearchType()));
        return ResponseEntity.ok(response);
    }
}
