package com.wisdom.tcc.controller;

import com.wisdom.tcc.interceptor.Delay;
import com.wisdom.tcc.model.TccRequest;
import com.wisdom.tcc.service.CoordinateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.ApiOperation;
import javax.validation.Valid;

/**
 * @author Zhao Junjian
 */
@RestController
@RequestMapping(value = "/api/v1", produces = {MediaType.APPLICATION_JSON_UTF8_VALUE}, consumes = {MediaType.APPLICATION_JSON_UTF8_VALUE})
public class CoordinateController {

    private static final String COORDINATOR_URI_PREFIX = "/coordinator";

    @Autowired
    private CoordinateService tccService;

    @Delay
    @ApiOperation(value = "确认预留资源", notes = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = COORDINATOR_URI_PREFIX + "/confirmation", method = RequestMethod.PUT)
    public void confirm(@Valid @RequestBody TccRequest request, BindingResult result) {
        tccService.confirm(request);
    }

    @Delay
    @ApiOperation(value = "撤销预留资源", notes = "")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @RequestMapping(value = COORDINATOR_URI_PREFIX + "/cancellation", method = RequestMethod.PUT)
    public void cancel(@Valid @RequestBody TccRequest request, BindingResult result) {
        tccService.cancel(request);
    }

}
