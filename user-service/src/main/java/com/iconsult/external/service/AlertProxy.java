package com.iconsult.external.service;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(url = "")
public interface AlertProxy {
}
