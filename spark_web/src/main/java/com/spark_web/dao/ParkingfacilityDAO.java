package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spark_web.domain.ParkingFacility;

public class ParkingfacilityDAO {

	private static SqlSessionFactory sqlSessionFactory;

	public ParkingfacilityDAO() {

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
		ParkingfacilityDAO.sqlSessionFactory = sqlSessionFactory;
	}

	public ParkingFacility FindParkingFacilityInfo(int parkingfacility_id) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingfacilityMapper mapper = session.getMapper(ParkingfacilityMapper.class);

			return mapper.FindParkingFacilityInfo(parkingfacility_id);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
