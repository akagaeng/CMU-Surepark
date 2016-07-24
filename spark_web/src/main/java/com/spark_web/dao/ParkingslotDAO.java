package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spark_web.domain.ParkingSlot;
import com.spark_web.domain.Result_Resv_Info;

public class ParkingslotDAO {

	private static SqlSessionFactory sqlSessionFactory;

	public ParkingslotDAO() {

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
		ParkingslotDAO.sqlSessionFactory = sqlSessionFactory;
	}

	public int FindAvailableParkingSlot() throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingslotMapper mapper = session.getMapper(ParkingslotMapper.class);

			int parkingslot_seq = mapper.FindAvailableParkingSlot().get(0).getParkingslot_seq();

			return parkingslot_seq;

		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	public boolean UpdateParkingSlotResvStatus(int parkingslot_seq) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingslotMapper mapper = session.getMapper(ParkingslotMapper.class);

			mapper.UpdateParkingSlotResvStatus(parkingslot_seq);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean UpdateParkingSlotEmptyStatus(int parkingslot_seq) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingslotMapper mapper = session.getMapper(ParkingslotMapper.class);

			mapper.UpdateParkingSlotEmptyStatus(parkingslot_seq);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public int FindResvParkingSlotSeq(Result_Resv_Info result_resv) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingslotMapper mapper = session.getMapper(ParkingslotMapper.class);

			return mapper.FindResvParkingSlotSeq(result_resv);

		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}

	public int FindResvParkingSlotState(int parkingslot_seq) {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ParkingslotMapper mapper = session.getMapper(ParkingslotMapper.class);

			return mapper.FindResvParkingSlotState(parkingslot_seq);

		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}

	}

	public List<ParkingSlot> FindParkingSlotInfo() {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			// ParkingslotMapper mapper =
			// session.getMapper(ParkingslotMapper.class);
			// return mapper.FindParkingSlotInfo();//hashmap으로 하면 list로 받을 수 있음
			return session.selectList("FindParkingSlotInfo"); // mapper 안 쓰고 직접
																// 참조

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
	}

}
