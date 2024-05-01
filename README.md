# Wooraegi Project  
  

> 반려동물 1500만시대!    
> 반려동물을 좋아하는 사람들이 자신의 반려동물을 등록하고 데일리 로그를 체크하며,   
> 관심사가 같은 회원들과 함께 소통하는 서비스 입니다.

**1. 팀원** </br>
&nbsp;&nbsp;&nbsp;&nbsp;● BE 김창현 </br>
&nbsp;&nbsp;&nbsp;&nbsp;🔗 https://github.com/ChangHyun92</br>
&nbsp;&nbsp;&nbsp;&nbsp;● BE 김시연</br>
&nbsp;&nbsp;&nbsp;&nbsp;🔗 https://github.com/yeonsii</br>
**2. 기간**</br>
&nbsp;&nbsp;&nbsp;&nbsp;2024.03 ~ 2024.04</br>
**3. 기술**</br>
&nbsp;&nbsp;&nbsp;&nbsp;Java, SpringBoot, Gradle, PostgreSql, Jpa, SpringSecurity</br>
&nbsp;&nbsp;&nbsp;&nbsp;Jwt, Swagger, Ec2, S3, Docker, Junit, Jira</br>
**4. 피그마**</br>
<div align="center">
<img align="center" width="47%" alt="로그인" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/0f701b25-dd2c-4fa4-9d18-639ee33b88b1">
<img align="center"  width="45%" alt="회원가입" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/214ed0fb-583c-4636-8cf6-d419863b5948">
</div>
<div align="center">  
<img align="center"  width="23%" alt="다이어리1" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/d764b84a-e010-405c-b51d-9a7ba58e9d4c">
<img align="center"  width="23%" alt="다이어리2" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/f38b702b-278b-4104-a39e-f2a9b5df694b">
<img align="center"  width="23%" alt="다이어리3" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/e19b1c83-10db-47fa-92e6-9d8b1b429d1e">
<img align="center"  width="23%" alt="다이어리4" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/3c13a4e6-a904-4e6f-b937-8cae08028112">
</div>
<div align="center">  
<img align="center"  width="45%" alt="로그" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/a1046c4d-6f1f-4761-b539-ceac51a24407">  
<img align="center"  width="45%" alt="프로필" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/cee48779-0fbe-464c-962a-a067e4f328f1">
</div>

**5. ERD**</br>
<img width="1193" alt="erd" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/bee395a1-16dd-40d6-8014-b9ceaddc8081">

**6. 주요기능**</br>
&nbsp;&nbsp;&nbsp;&nbsp;● 소셜 로그인/ 회원가입</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•Spring Security</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•JWT</br>
&nbsp;&nbsp;&nbsp;&nbsp;● 반려동물 등록</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•AWS S3 </br>
&nbsp;&nbsp;&nbsp;&nbsp;● 다이어리(board) crud</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•AWS S3</br>
&nbsp;&nbsp;&nbsp;&nbsp;● 데일리 로그</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•BABY_LOG</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•BABY_LOG_ITEM</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•BABY_LOG_HIST</br>
&nbsp;&nbsp;&nbsp;&nbsp;● 임시비번 발급 (mail)</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•javaMailSender</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•smtp</br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;**6-1 : 소셜 로그인/ 회원가입** </br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•네이버 개발자 센터 등록</br>
<img width="1246" alt="네이버개발자" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/c6d90595-552f-474e-aded-a576da16dc0d"></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•발급받은 client_id, redirect_uri, client_secret를 이용하여 로그인 요청 주소 입력</br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•사용자 id, pw로 로그인</br>
<img width="300" alt="스크린샷 2024-05-01 오후 7 09 35" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/05ea1cdc-4b30-4cbf-a31a-beb34c30d552"></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•응답받은 인증코드로 네이버에 엑세스 토큰 요청</br>
<img width="740" alt="스크린샷 2024-05-01 오후 7 13 05" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/16e0e395-6762-4fbe-9212-92f3f6beb414"></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•응답받은 토큰으로 네이버에 회원 정보 요청</br>
<img width="777" alt="스크린샷 2024-05-01 오후 7 14 02" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/c6638add-993a-4176-92ef-f6baa32e0231"></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•회원가입 여부 체크 후 비 회원일시 정보 저장</br>
<img width="697" alt="스크린샷 2024-05-01 오후 7 15 04" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/544d4621-a182-42bd-8890-7ccfd3720627"></br>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;•자체 토큰 발급</br>
<img width="849" alt="스크린샷 2024-05-01 오후 7 15 31" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/6f6f54c3-7b65-4821-bd7d-d25160240822"></br>
<img width="1506" alt="스크린샷 2024-05-01 오후 7 11 52" src="https://github.com/wooraegi/wooraegi_be/assets/37923273/1e137367-20cf-4851-9995-2b981996b4b3"></br>
</br>
&nbsp;&nbsp;&nbsp;&nbsp;**6-2 : 반려동물 등록**
&nbsp;&nbsp;&nbsp;&nbsp;**6-3 : 다이어리(board) crud**
&nbsp;&nbsp;&nbsp;&nbsp;**6-4 : 데일리 로그**
&nbsp;&nbsp;&nbsp;&nbsp;**6-5 : 임시비번 발급 (mail)**
