package com.spark_web.dao;

import java.io.IOException;
import java.io.InputStream;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spark_web.domain.ParkingStaticalData;
import com.spark_web.domain.Result_Resv_Info;
import com.spark_web.domain.Resv;
import com.spark_web.domain.SlotUsage;

public class ResvDAO {

	private static SqlSessionFactory sqlSessionFactory;

	public ResvDAO() {

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
		ResvDAO.sqlSessionFactory = sqlSessionFactory;
	}

	public int FindMaximumResvid() {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindMaximumResvid();

		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}
	}

	public boolean InsertResv(Resv resv) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.InsertResv(resv);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public boolean UpdateCancelResv(String resv_authenticationnum) throws IOException {

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.UpdateCancelResv(resv_authenticationnum);
			return true;

		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	}

	public Result_Resv_Info FindResv(String phonenumber) {
		// TODO Auto-generated method stub

		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindResv(phonenumber);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	// ������ȣ�� ���� ��ȸ
	public Result_Resv_Info FindResvAuthenticationInfo(String resv_authenticationnum) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindResvAuthenticationInfo(resv_authenticationnum);

		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}

	}

	// ������ ������ȣ ��ȸ
	public String FindResvAuthenticationNum(int parkingslot_seq) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindResvAuthenticationNum(parkingslot_seq);

		} catch (Exception e) {
			// TODO: handle exception
			return "fail";
		}
	}

	// ekfms tkfka���� ������Ʈ
	public void UpdateParkingSlotSeq(Resv otherreallocateuserresv) {
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.UpdateParkingSlotSeq(otherreallocateuserresv);

		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	// ������ ������ ��
	public void UpdateParkingStartTime(Resv resv) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.UpdateParkingStartTime(resv);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}

	// ������ �����忡�� �̵��Ҷ�
	public void UpdateParkingStatetoMoving(Resv resv) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.UpdateParkingStatetoMoving(resv);
		} catch (Exception e) {
			// TODO: handle exception
		}

	}
	
	public Resv FindParkingSlotStateMoving(String resv_cancel) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindParkingSlotStateMoving(resv_cancel);
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}	
	}

	// ������ ������ ������
	public void UpdateParkingExitTime(Resv resv) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			mapper.UpdateParkingExitTime(resv);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public int FindStaricalParkingSlotDayUsage(SlotUsage slotusage){
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindStaricalParkingSlotDayUsage(slotusage);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}	
		
	}
	
	public int FindStaricalParkingSlotMonthUsage(SlotUsage slotusage){
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindStaricalParkingSlotMonthUsage(slotusage);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}	
		
	}

	public int FindStaricalParkingPeakTimeHour(ParkingStaticalData parkingstaticaldata) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindStaricalParkingPeakTimeHour(parkingstaticaldata);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}	
	}

	public int FindStaricalParkingPeakTimeDay(ParkingStaticalData parkingstaticaldata) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindStaricalParkingPeakTimeDay(parkingstaticaldata);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}	
	}

	public int FindStaricalParkingPeakTimeMonth(ParkingStaticalData parkingstaticaldata) {
		// TODO Auto-generated method stub
		try (SqlSession session = sqlSessionFactory.openSession()) {
			ResvMapper mapper = session.getMapper(ResvMapper.class);
			return mapper.FindStaricalParkingPeakTimeMonth(parkingstaticaldata);
		} catch (Exception e) {
			// TODO: handle exception
			return -1;
		}	
	}
}
