package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import dto.AreaVo;
import util.DBManager;

public class OperatingSystem {
	public static void main(String[] args) {
//		##### 1행 데이터 활용 #####
//		AreaVo aVo = selectArea(3);
//		System.out.println(aVo);
//		System.out.println("이동한 지역은?: "+ aVo.getName());
//		System.out.println("================");
		
//		##### 전체(여러행) 데이터 활용 #####
		int code = 0;
		String name = null;
		String is_around = null;
		int country_code = 0;
		
		// <저장할 개체의 데이터타입>
		List<AreaVo> areaList = selectAllArea();
		
		for(AreaVo aVo : areaList) {
			System.out.println(aVo);
			
			code = aVo.getCode();
			name = aVo.getName();
			is_around = aVo.getIs_around();
			country_code = aVo.getCountry_code();
			
//			System.out.print(code+"\t");
//			System.out.print(name+"\t");
//			System.out.print(is_around+"\t");
//			System.out.println(country_code);
		}
		
//		##### 데이터 삽입 #####
//		insertArea(10, "청도", "1", 3);
//		insertArea(11, "하얼빈", "0", 2);		
	}
	
//	컬렉션(3): List컬렉션 => 배열처럼 인덱스번호로 데이터를 저장, 로드 가능
//	관련 함수:
//	1. list.add(객체);		=> 데이터 객체 추가
//	2. list.remove(인덱스);	=> 데이터 객체 삭제
//	3. list.get(인덱스);		=> 데이터 객체 로드
//	4. list.size();			=> 데이터 객체 크기 조회
	
	// 디비 삽입(insert) 함수
	static void insertArea(int code, String name, String is_around, int country_code) {
		String sql = "INSERT INTO area VALUES(?,?,?,?)";
		
		PreparedStatement pstmt = null;
		Connection conn = null;
		
		try {
			conn = DBManager.getConnection();		// 디비 연결
			
	//		(3단계) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);		// 쿼리문 실행
			pstmt.setInt(1, code);
			pstmt.setString(2, name);
			pstmt.setString(3, is_around);
			pstmt.setInt(4, country_code);
			
	//		(4단계) SQL문 실행 결과 처리
//			pstmt.executeQuery();				// select 쿼리문 결과 처리
			pstmt.executeUpdate();				// insert/update/delete 쿼리문 결과 처리
				
		} catch(Exception e) {
			System.out.println("예외 발생시 처리할 코드: 쿼리문 삽입");
		}
		
		DBManager.close(conn, pstmt);		// 디비 닫기
	}
		
	// 디비 조회(select) 함수: 1행 데이터
	static AreaVo selectArea(int code) {
		String sql = "SELECT * FROM area where code=?";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		int code1 = 0;
		String name = null;
		String is_around = null;
		int country_code = 0;
		
		AreaVo aVo = null;		 	// 디비에서 데이터를 담아 오는 객체
		
		try {
			conn = DBManager.getConnection();		// 디비 연결
			
	//		(3단계) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);		// 쿼리문 실행
			pstmt.setInt(1, code);			// 1:물음표 순서, code:물음표 값
			
	//		(4단계) SQL문 실행 결과 처리
			rs = pstmt.executeQuery();				// 쿼리문 결과 처리
			
//			rs.next(): 다음 행(row)을 확인(반환값타입: 불리언)			
			while(rs.next()) {
				aVo = new AreaVo();
				
				code1 = rs.getInt("code");
				name = rs.getString("name");
				is_around = rs.getString("is_around");
				country_code = rs.getInt("country_code");				
//				System.out.println(aVo);
				
				aVo.setCode(code1);		// aVo 객체의 필드에 데이터 저장
				aVo.setName(name);
				aVo.setIs_around(is_around);
				aVo.setCountry_code(country_code);				
//				System.out.println(aVo);
			}
		} catch(Exception e) {
			System.out.println("예외 발생시 처리할 코드: 쿼리문 조회");
		}
		DBManager.close(conn, pstmt, rs);		// 디비 닫기
		return aVo;				// 저장한 필드 데이터를 포함한 객체를 반환
	}
	
	// 디비 조회(select) 함수: 모든(여러행) 데이터
	static List selectAllArea() {		// [5]. 반환 타입 변경
		String sql = "SELECT * FROM area";
		
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		Connection conn = null;
		
		int code1 = 0;
		String name = null;
		String is_around = null;
		int country_code = 0;
		
		List list = new ArrayList();	// [1]. ArrayList 객체 생성
		AreaVo aVo = null;		
		
		try {
			conn = DBManager.getConnection();		// 디비 연결
			
	//		(3단계) PreparedStatement 객체 생성
			pstmt = conn.prepareStatement(sql);		// 쿼리문 실행
			
	//		(4단계) SQL문 실행 결과 처리
			rs = pstmt.executeQuery();				// 쿼리문 결과 처리
			
//			rs.next(): 다음 행(row)을 확인(반환값타입: 불리언)			
			while(rs.next()) {
				aVo = new AreaVo();		// [2]. Vo 객체를 반복 생성 
				
				code1 = rs.getInt("code");
				name = rs.getString("name");
				is_around = rs.getString("is_around");
				country_code = rs.getInt("country_code");
				
				aVo.setCode(code1);		// aVo 객체의 필드에 데이터 저장
				aVo.setName(name);
				aVo.setIs_around(is_around);
				aVo.setCountry_code(country_code);				
//				System.out.println(aVo);
				
				list.add(aVo);			// [3]. 데이터 객체 추가
			}
		} catch(Exception e) {
			System.out.println("예외 발생시 처리할 코드: 쿼리문 조회");
		}
		DBManager.close(conn, pstmt, rs);		// 디비 닫기
		return list;			// [4]. 저장한 List 컬렉션 객체 반환
	}
	
	
}
