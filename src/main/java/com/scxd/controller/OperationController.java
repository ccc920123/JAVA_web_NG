package com.scxd.controller;

import com.scxd.beans.Response;
import com.scxd.service.OptLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("operationLog")
public class OperationController {
    @Autowired
    OptLogService operationService;

    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    public Response upDataRoleData(@RequestBody Map map, HttpSession session) {
        return operationService.getOperationList(map);
    }
    @RequestMapping(value = "/queryOperationDetail", method = RequestMethod.GET)
    public Response queryOperationDetail(String  id, HttpSession session) {
        return operationService.queryOperationDetail(id);
    }
}
