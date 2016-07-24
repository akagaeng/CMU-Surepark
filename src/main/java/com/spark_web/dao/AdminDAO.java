package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;


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

	public static SqlSessionFactory getSqlSessionFactory() {
		return sqlSessionFactory;
	}

	public static void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		AdminDAO.sqlSessionFactory = sqlSessionFactory;
	}

	public boolean FindAdminData(String userId, String password) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			AdminMapper mapper = session.getMapper(AdminMapper.class);
			
			if(mapper.FindAdminData(userId).equals(password)){

				return true;
			}else {
				return false;
			}
			
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

}
