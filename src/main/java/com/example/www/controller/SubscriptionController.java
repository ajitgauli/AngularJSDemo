package com.example.www.controller;

import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.portlet.bind.annotation.RenderMapping;

@Controller
@RequestMapping(value = "VIEW")
public class SubscriptionController {

	
	@RenderMapping
	public String show(RenderRequest renderRequest) throws PortletException {	

		return "view";
	}

}
