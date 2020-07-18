package com.iktpreobuka.project_services.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(path = "/project")
public class UploadController {

	@RequestMapping(value= "/upload")
    public String upload() {
        return "upload.html";
    }

}
