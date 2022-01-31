package com.example.backswim.admin;

import com.example.backswim.admin.service.AdminService;
import com.example.backswim.pool.service.PoolService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/admin/makedatabaseset")
    public void MakeDataBaseSet(){

    }

    @GetMapping("/admin/makechosung")
    public void MakeChoSung() throws Exception{
        adminService.SetChoSung();
    }
}
