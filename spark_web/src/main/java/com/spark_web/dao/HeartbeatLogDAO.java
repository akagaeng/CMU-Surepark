package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spark_web.domain.HeartbeatLog;

public class HeartbeatLogDAO {


	private static SqlSessionFactory sqlSessionFactory;

	public HeartbeatLogDAO() {

		try {
			InputStream inputStream = Resources.getResourceAsStream("mybatis/config/mybatis-config.xml");
			sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	
	public boolean UpdateHeartbeatLog(HeartbeatLog heartbeatlog) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			HeartbeatLogMapper mapper = session.getMapper(HeartbeatLogMapper.class);
			
			mapper.UpdateHeartbeatLog(heartbeatlog);
			return true;
			
		}catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public List<HeartbeatLog> FindHeartbeatLog() {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			return session.selectList("FindHeartbeatLog");
			
		}catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}
	
}
