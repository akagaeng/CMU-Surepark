package com.spark_web.dao;

import com.spark_web.domain.Admin;

public interface AdminMapper {

	public String FindAdminData(String userid);

	public void UpdateAdminToken(Admin admin);

	public String FindAdminToken(String token);

	public void UpdateAdminlogout(String token);

}