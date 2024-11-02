package com.coma.app.view.function;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Random;

public class SMSPush {

	//cafe24 문자 API 발송 요청 URL
	private static String smsUrl = "https://sslsms.cafe24.com/sms_sender.php";
	private static String cafe24_id = "";// TODO 키값 제거하도록 합시다.
	private static String cafe24_secure = "";// TODO 키값 제거하도록 합시다.
	
//	//cafe24 문자 api 사용가능 여부 확인 URL
//	private String apiUrl = "https://sslsms.cafe24.com/smsSenderPhone.php";
//	private String userAgent = "Mozilla/5.0";
//	private String postParams = "";
//
//	public void callcheck() {
//		try {
//			URL obj = new URL(this.apiUrl);
//
//			HttpURLConnection con = (HttpURLConnection) obj.openConnection();
//			con.setRequestMethod("POST");
//			con.setRequestProperty("User-Agent", this.userAgent);
//
//			// POST 요청을 보내기 위한 설정
//			con.setDoOutput(true);
//
//			// try-with-resources를 사용해 자동으로 스트림을 닫도록 함
//			try (OutputStream os = con.getOutputStream()) {
//				os.write(this.postParams.getBytes("UTF-8"));  // UTF-8 인코딩 사용
//				os.flush();
//			}
//
//			int responseCode = con.getResponseCode();
//			System.out.println("POST Response Code :: " + responseCode);
//
//			// 응답 코드가 200일 경우, 즉 성공했을 때
//			if (responseCode == HttpURLConnection.HTTP_OK) {
//				try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"))) {
//					String inputLine;
//					StringBuilder buf = new StringBuilder();
//
//					while ((inputLine = in.readLine()) != null) {
//						buf.append(inputLine);
//					}
//					// 응답 결과 출력
//					System.out.println(buf.toString());
//				}
//			} else {
//				System.err.println("POST 요청 실패");
//			}
//
//		} catch (IOException ex) {
//			ex.printStackTrace();  // 예외가 발생하면 스택 트레이스 출력
//		}
//	}


	/**==============================================================
    Description        :  사용 함수 선언
  ==============================================================**/
	/**
	 * nullcheck
	 * @param str, Defaultvalue
	 * @return
	 */

	public static String nullcheck(String str,  String Defaultvalue ) throws Exception
	{ //throws 예외처리 미루기
		//입력된(들어온)값이 즉 개발자 / 사용자 외부 요인으로 오류가 발생할 수 있으니
		//Error 처리해서 사용해라 라는 뜻
		//Defaultvalue input Tag의 처음 값
		String ReturnDefault = "" ;  // 전달할 기본값을 초기변수로 설정해준다.
		if (str == null) // 만약 받아온 파라메타 값이 null 이면
		{
			ReturnDefault =  Defaultvalue ; //기본값을 전달해주고
		}
		else if (str == "" ) // 만약 받아온 파라메타 값이 공백 이면
		{
			ReturnDefault =  Defaultvalue ; //기본값으로 전달해주고
		}
		else // null이거나 공백이 아니라면
		{
			ReturnDefault = str ; //받아온 파라메타 값으로 변경한다.
		}
		return ReturnDefault ; //최종적으로 변경된 값을 반환해준다.
	}
	/**
	 * BASE64 Encoder
	 * @param str
	 * @return
	 */
	public static String base64Encode(String str)  throws java.io.IOException {
		//throws 예외처리 미루기
		//입출력되는 값이 오류가 발생할 수 는 있다.
		return java.util.Base64.getEncoder().encodeToString(str.getBytes());
		//인코더를 Base64로 맞춰서 넘겨준다.
	}

	/**
	 * BASE64 Decoder
	 * @param str
	 * @return
	 */
	public static String base64Decode(String str)  throws java.io.IOException {
		//throws 예외처리 미루기
		//입출력되는 값이 오류가 발생할 수 는 있다.
		byte[] decodedBytes = java.util.Base64.getDecoder().decode(str);		
		//인코더를 base64로 변경 후 bytes수(보통 배열로 많이 불러온다)를
		//받아서 문자열로 형변환 후 반환해준다.
		return new String(decodedBytes);

	}


	public static boolean smsSend(String member_PhoneNumber, String controllerSMS) {
		/**==============================================================
	      Description        : 캐릭터셋 정의
	      EUC-KR: @ page contentType="text/html;charset=EUC-KR
	      UTF-8: @ page contentType="text/html;charset=UTF-8
	    ==============================================================**/
		String charsetType = "UTF-8"; //EUC-KR 또는 UTF-8

		//문자 인증 성공 여부를 확인 하기 위해 사용
		boolean flag = false;
		
		//Cafe24 SMS 전송요청 URL
		String sms_url = SMSPush.smsUrl; 
		String smsMsg = "인증번호는 "+controllerSMS + " 입니다.";
		try {
			//Cafe24 SMS아이디
			String user_id = base64Encode(SMSPush.cafe24_id); 
			//Cafe24 인증키
			String secure = base64Encode(SMSPush.cafe24_secure);

			//메시지 전달할 내용
			String msg = base64Encode(nullcheck(smsMsg, ""));

			//문자를 받을 전화번호 - 추가해서 입력 010-0000-0000
			String rphone = base64Encode(nullcheck(member_PhoneNumber, "")); 

			//보낼 전화번호 시작번호 
			String sphone1 = base64Encode(nullcheck("010", ""));

			//보낼 전화번호 중간번호 기업전화번호는 중간(두번째)번호까지만 입력해도 문자가 간다.
			String sphone2 = base64Encode(nullcheck("4301", ""));

			//보낼 전화번호 끝번호
			String sphone3 = base64Encode(nullcheck("7553", ""));

			//예약 문자를 보낼 날짜 - 없이 작성 20090909
			String rdate = base64Encode(nullcheck("", ""));

			//예약 문자를 보낼 시간 - 없이 작성 173000 : 17시 30분 00초
			//예약 시간은 최소 10분이상
			String rtime = base64Encode(nullcheck("", ""));

			String mode = base64Encode("1");
			String subject = "";
			if(nullcheck("", "").equals("L")) {
				subject = base64Encode(nullcheck("", ""));
			}
			//실제로 문자를 보내면 금액이 지불되기 때문에
			//문자 테스트를 할지 여부를 입력
//			String testflag = base64Encode(nullcheck("Y", ""));
			String testflag = base64Encode(nullcheck("Y", ""));

			//이름을 입력 010-000-0000|홍길동
			String destination = base64Encode(nullcheck("", ""));

			//반복 설정 여부
			String repeatFlag = base64Encode(nullcheck("", ""));

			//반복 횟수 설정
			String repeatNum = base64Encode(nullcheck("", ""));

			//반복할 시간 설정 15분 이상부터 가능
			String repeatTime = base64Encode(nullcheck("", ""));

			//문자를 전송 후 넘어갈 주소를 입력
			String returnurl = nullcheck("", "");

			//발송 오류 및 성공 실패 여부를 alert창으로 보여줄지 체크
			//사용할 경우 : 1, 성공시 대화상자(alert)를 생략.
			String nointeractive = nullcheck("", "");

			//문자 형식을 지정
			//S단문, L장문
			String smsType = base64Encode(nullcheck("S", ""));

			String[] host_info = sms_url.split("/");
			String host = host_info[2];
			String path = "/" + host_info[3];
			int port = 80;

			// 데이터 맵핑 변수 정의
			// cafe24 문자 api는 문자 전송에 따른
			// 변수가 설정되어 있기 때문에
			// 모든 변수를 사용해야한다.
			String arrKey[]
					= new String[] {"user_id","secure","msg", "rphone","sphone1","sphone2","sphone3","rdate","rtime"
							,"mode","testflag","destination","repeatFlag","repeatNum", "repeatTime", "smsType", "subject"};
			String valKey[]= new String[arrKey.length] ;
			valKey[0] = user_id; //cafe24 문자 API 를 사용할 아이디
			valKey[1] = secure; //cafe24 문자 API 를 사용할 인증키
			valKey[2] = msg;	//문자 전송할 내용
			valKey[3] = rphone; //문자가 전송될 전화번호
			valKey[4] = sphone1; //문자 전송에 사용할 전화번호 첫 3자리
			valKey[5] = sphone2; //문자 전송에 사용할 전화번호 첫 4자리
			valKey[6] = sphone3; //문자 전송에 사용할 전화번호 첫 4자리
			valKey[7] = rdate; //예약 문자를 보낼 날짜
			valKey[8] = rtime; //예약 문자를 보낼 시간
			valKey[9] = mode; //문자 발송 모드를 지정 기본 모드 1
			valKey[10] = testflag; //문자 발송을 하기 위해서는 금액이 들기에 Test 모드를 지원
			valKey[11] = destination; // 전화번호의 이름을 추가하기 위함
			valKey[12] = repeatFlag; //반복설정 여부
			valKey[13] = repeatNum; // 반복 횟수 지정
			valKey[14] = repeatTime; // 반복 시간 설정
			valKey[15] = smsType; // 문자 발송 형식을 지정 S 단문 L 장문
			valKey[16] = subject; //장문일때 발송될 제목

			//보안을 위해 인코드를 못하게
			//앞뒤로 hex코드를 추가
			String boundary = "";
			//hex코드를 만들기 위한 Random 함수
			Random rnd = new Random();
			//hex 코드 생성
			String rndKey = Integer.toString(rnd.nextInt(32000));
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] bytData = rndKey.getBytes();
			md.update(bytData);
			byte[] digest = md.digest();
			for(int i =0;i<digest.length;i++)
			{
				boundary = boundary + Integer.toHexString(digest[i] & 0xFF);
			}
			boundary = "---------------------"+boundary.substring(0,11);

			// 본문 생성
			// 작성 한 모든 변수를 cafe24 서버로 전달하여
			// 문자 발송을 준비함
			String data = "";
			String index = "";
			String value = "";
			for (int i=0;i<arrKey.length; i++)
			{
				index =  arrKey[i];
				value = valKey[i];
				data +="--"+boundary+"\r\n";
				data += "Content-Disposition: form-data; name=\""+index+"\"\r\n";
				data += "\r\n"+value+"\r\n";
				data +="--"+boundary+"\r\n";
			}

			InetAddress addr = InetAddress.getByName(host);
			Socket socket = new Socket(host, port);
			// server 에서 전달 받은 Socket 에 값을 작성하고
			// 문자를 발송함
			
			BufferedWriter wr = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), charsetType));
			wr.write("POST "+path+" HTTP/1.0\r\n");
			wr.write("Content-Length: "+data.length()+"\r\n");
			wr.write("Content-type: multipart/form-data, boundary="+boundary+"\r\n");
			wr.write("\r\n");

			// 데이터 전송
			wr.write(data);
			wr.flush();

			// 결과값 얻기
			BufferedReader rd = new BufferedReader(new InputStreamReader(socket.getInputStream(),charsetType));
			String line;
			String alert = "";
			// 순차적으로 발송 정보와
			// error 및 발송 여부를 전달해줌
			ArrayList tmpArr = new ArrayList();
			while ((line = rd.readLine()) != null) {
				tmpArr.add(line);
			}
			wr.close();
			rd.close();

			String tmpMsg = (String)tmpArr.get(tmpArr.size()-1);
			String[] rMsg = tmpMsg.split(",");
			String Result= rMsg[0]; //발송결과
			String Count= ""; //잔여건수
			
			//발송 후 잔여 건수를 반송해줍니다.
			if(rMsg.length>1) {Count= rMsg[1]; }

			if(Result.equals("Test Success!") || Result.equals("success") || Result.equals("reserved") ) {
				System.err.println("SMSPush 로그 : 문자발송 성공 " + Result);
				//문자 발송에 성공하면
				flag = true;
			}
			else if(Result.equals("3205")) {
				System.err.println("SMSPush 로그 : 잘못된 번호 형식 " + Result);
			}
			else{
				System.err.println("SMSPush 로그 : 문자발송 실패 " + Result);
			}
		} catch (Exception e) {
			//오류가 발생한다면 false 를 반환
			return flag;
		}
		
		//문자 전송 결과 반환
		return flag;
	}

}
