package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@ConfigurationProperties
@RefreshScope
public class Configurations {

	private String busvalue;

	public String getBusvalue() {
		return busvalue;
	}

	public void setBusvalue(String busvalue) {
		this.busvalue = busvalue;
	}

}
