package com.giteat.pr.controller;


import com.giteat.pr.service.PrServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pr")
public class PrController {

    @Autowired
    @Qualifier("PrServiceImpl")
    private PrServiceImpl prService;
}
