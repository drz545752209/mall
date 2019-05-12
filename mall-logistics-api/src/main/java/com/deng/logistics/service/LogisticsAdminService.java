package com.deng.logistics.service;

import com.deng.logistics.domain.LogisticsAdmin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public interface LogisticsAdminService {
      boolean hasAdmin(LogisticsAdmin logisticsAdmin);

      boolean hasAdminName(String adminName);

      boolean insertSelective(LogisticsAdmin logisticsAdmin);

      boolean hasCookie(HttpServletRequest request, HttpServletResponse resp, boolean isValidate, LogisticsAdmin logisticsAdmin);

      List<String> getCompanyNames();
}
