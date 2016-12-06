package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;
import java.security.PrivateKey;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spark_web.domain.Admin;


public class AdminDAO {

	private static SqlSessionFactory sqlSessionFactory;

	public AdminDAO() {

		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis/config/mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public boolean FindAdminData(String userid, String password) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AdminMapper mapper = session.getMapper(AdminMapper.class);
			
			if(mapper.FindAdminData(userid).equals(password)){
				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}
	
	public boolean UpdateAdminToken(Admin admin) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AdminMapper mapper = session.getMapper(AdminMapper.class);
			mapper.UpdateAdminToken(admin);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean FindAdminToken(String token) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AdminMapper mapper = session.getMapper(AdminMapper.class);
			if(mapper.FindAdminToken(token).equals(token)){
				return true;
			}else {
				return false;
			}

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean UpdateAdminlogout(String token) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			AdminMapper mapper = session.getMapper(AdminMapper.class);
			mapper.UpdateAdminlogout(token);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
