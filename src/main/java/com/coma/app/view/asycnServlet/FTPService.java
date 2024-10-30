package com.coma.app.view.asycnServlet;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

@Slf4j
@Service
public class FTPService {

    //이미지를 불러올 주소
    private static final String UPLOAD_DIRECTORY = "https://comapro.cdn1.cafe24.com/";
    //FTP 서버 주소
    private static final String FTP_SERVER = "iup.cdn1.cafe24.com";
    //FTP PORT 번호
    private static final int PORT = 21;
    //FTP 로그인 아이디
    private static final String USER_ID = ""; // TODO 키값 제거하도록 합시다.
    //FTP 로그인 비밀번호
    private static final String USER_PASSWORD = ""; // TODO 키값 제거하도록 합시다.
    // FTP 서버 초기 주소
    private static final String FTP_FILE_PATH = "/www/";


    @Autowired
    private FTPClient ftpClient;

    public static String img_security() {
        String security_code = "";
        try {
            //랜덤한 보안키를 만들기 위한 Random 함수를 추가
            Random rnd = new Random();

            //랜덤한 숫자를 받아오고
            String rndKey = Integer.toString(rnd.nextInt(32000));
            //랜덤 숫자를 변환할 형식을 지정해줍니다.
            MessageDigest md = MessageDigest.getInstance("MD5");

            //랜덤 숫자를 바이트 형식으로 변환하고
            byte[] bytData = rndKey.getBytes();
            //"MD5" 형식으로 변환합니다.
            md.update(bytData);
            //랜덤 숫자로 만들어야 하므로 변환한 형식을 바이트로 만들어주고
            byte[] digest = md.digest();
            for(int i =0;i<digest.length;i++){
                //변환된 형식의 보안 코드를 생성해줍니다.
                security_code = security_code + Integer.toHexString(digest[i] & 0xFF);
            }
            //해당 보안코드를 모두 사용하게 되면 너무 길기 때문에 11자리 숫자만 사용합니다.
            security_code = security_code.substring(0,11);

        } catch (NoSuchAlgorithmException e) {
            System.out.println("CKEditor_Upload.java 로그 MessageDigest.getInstance(\"MD5\") 생성오류 발생");
        }
        //보안코드를 반환해줍니다.
        return security_code;
    }

    //FTP 서버 connect 시작
    private void FTPServiceConnect(){
        try{
            this.ftpClient.connect(FTP_SERVER,PORT);
            if(this.ftpClient.isConnected()) {
                log.error("FTP 연결 성공");
                //한글이 있을 수 있으니 인코딩을 해줍니다.
                this.ftpClient.setControlEncoding("UTF-8");
                //login 진행
                this.ftpClient.login(USER_ID, USER_PASSWORD);
                log.error("ftpClient login");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

    private void FTPServiceDisConnect(){
        try{
            //ftp server 로그아웃
            if(!this.ftpClient.logout())log.error("ftpClient logout Fail");
            //ftp 연결 종료
            this.ftpClient.disconnect();
        } catch (IOException e) {
            log.error(e.getMessage());
        }

    }

    public String ftpFolderList(MultipartFile file, String img_Folder, String FolderName) throws IOException {

        //업로드를 하기 위해 파일 이름을 받아옵니다.
        String localFileName = file.getOriginalFilename();

        //받아온 파일의 형식만 불러와줍니다.
        String fileForm = localFileName.substring(localFileName.lastIndexOf("."));

        //이미지 저장 상위 폴더
        String ftpCreateFolder = img_Folder+"/";

        // 이미지가 업로드될 주소를 지정
        //사용자 아이디
        String createFolder = FolderName+"/";

        // 두 주소를 합쳐줍니다.
        String folderPath = FTPService.FTP_FILE_PATH+ftpCreateFolder+createFolder;
        log.info("folderPath : [{}]",folderPath);

        // 랜덤 숫자를 이름으로 변경합니다.
        String img_name = img_security()+fileForm;
        log.info("img_name : [{}]",img_name);

        try {
            //FTP 서버 접속
            FTPServiceConnect();
            log.error("ftpClient connect");
            //연결 여부를 확인하고 연결 되어있다면


            //폴더 추가
            if(!this.ftpClient.makeDirectory(folderPath))log.error("ftpClient makeDirectory ROOT/test Fail");

            //최종 파일 위치 확인용 로그
            System.out.println("FTP FilePath ["+folderPath+"]");

            //파일 타입 지정
            if(!this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE))log.error("ftpClient setFileType Fail");

            //파일 데이터 전송을 위한 임시 포트 생성
            //ftpClient.enterLocalActiveMode(); 서버에 전송할 데이터 방화벽 설정이 되어있다면  연결을 못할 수 있다.
            this.ftpClient.enterLocalPassiveMode();
            log.error("ftpClient enterLocalPassiveMode");

            //ftp server 에 파일 적용
            if(!this.ftpClient.storeFile(folderPath+img_name,file.getInputStream()))log.error("ftpClient storeFile Fail");


            //FTP 서버 종료
            FTPServiceDisConnect();
        } catch (IOException e) {
            log.error("FTP 파일 입출력 문제 발생");
            log.error(e.getMessage());
        }

        //넘길 이미지 주소를 전달합니다.
        return UPLOAD_DIRECTORY+ftpCreateFolder+createFolder+img_name;
    }

    public String ftpFileUpload(MultipartFile file, String img_Folder, String FolderName,int member_folder) throws IOException {

        //업로드를 하기 위해 파일 이름을 받아옵니다.
        String localFileName = file.getOriginalFilename();

        //받아온 파일의 형식만 불러와줍니다.
        String fileForm = localFileName.substring(localFileName.lastIndexOf("."));

        //이미지 저장 상위 폴더
        String ftpCreateFolder = img_Folder+"/";

        // 이미지가 업로드될 주소를 지정
        //사용자 아이디
        String createFolder = FolderName+"/"+member_folder+"/";

        // 두 주소를 합쳐줍니다.
        String folderPath = FTPService.FTP_FILE_PATH+ftpCreateFolder+createFolder;
        log.info("folderPath : [{}]",folderPath);

        // 랜덤 숫자를 이름으로 변경합니다.
        String img_name = img_security()+fileForm;
        log.info("img_name : [{}]",img_name);

        try {
            //FTP 서버 접속
            FTPServiceConnect();
            log.error("ftpClient connect");
            //연결 여부를 확인하고 연결 되어있다면


            //폴더 추가
            if(!this.ftpClient.makeDirectory(folderPath))log.error("ftpClient makeDirectory ROOT/test Fail");

            //최종 파일 위치 확인용 로그
            System.out.println("FTP FilePath ["+folderPath+"]");

            //파일 타입 지정
            if(!this.ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE))log.error("ftpClient setFileType Fail");

            //파일 데이터 전송을 위한 임시 포트 생성
            //ftpClient.enterLocalActiveMode(); 서버에 전송할 데이터 방화벽 설정이 되어있다면  연결을 못할 수 있다.
            this.ftpClient.enterLocalPassiveMode();
            log.error("ftpClient enterLocalPassiveMode");

            //ftp server 에 파일 적용
            if(!this.ftpClient.storeFile(folderPath+img_name,file.getInputStream()))log.error("ftpClient storeFile Fail");


            //FTP 서버 종료
            FTPServiceDisConnect();
        } catch (IOException e) {
            log.error("FTP 파일 입출력 문제 발생");
            log.error(e.getMessage());
        }

        //넘길 이미지 주소를 전달합니다.
        return UPLOAD_DIRECTORY+ftpCreateFolder+createFolder+img_name;
    }

    public void ftpCreateFolder(String img_Folder, String FolderName, int createFolderNum) throws IOException {

        //이미지 저장 상위 폴더
        String ftpCreateFolder = img_Folder+"/";

        // 이미지가 업로드될 주소를 지정
        //사용자 아이디
        String createFolder = FolderName+"/";

        String createFolderPath = FTPService.FTP_FILE_PATH+ftpCreateFolder+createFolder+createFolderNum;


        //FTP 서버 접속
        FTPServiceConnect();
        log.error("ftpClient connect");

        //폴더 추가
        if(!this.ftpClient.makeDirectory(createFolderPath))log.error("ftpClient makeDirectory ROOT/test Fail");

        //FTP 서버 종료
        FTPServiceDisConnect();
    }

    public int ftpFolderCount(String img_Folder, String FolderName) throws IOException {


        //이미지 저장 상위 폴더
        String ftpCreateFolder = img_Folder+"/";

        // 이미지가 업로드될 주소를 지정
        //사용자 아이디
        String createFolder = FolderName+"/";
        // 두 주소를 합쳐줍니다.
        String folderPath = FTPService.FTP_FILE_PATH+ftpCreateFolder+createFolder;
        System.out.println(folderPath);
        FTPFile[] files =null;
        try {
            //FTP 서버 접속
            FTPServiceConnect();
            log.error("ftpClient connect");

            files =  this.ftpClient.listFiles(folderPath);
            System.out.println("현재 ftp 서버 파일 개수 ["+files.length+"]");

            //FTP 서버 종료
            FTPServiceDisConnect();
        } catch (IOException e) {
            log.error("파일 입출력 문제 발생");
            log.error(e.getMessage());
        }

        int result = 0;
        if(files != null) {
            result = files.length;
        }

        //넘길 이미지 주소를 전달합니다.
        return result;
    }

}
