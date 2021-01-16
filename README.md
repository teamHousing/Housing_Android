# 🏡Housing

#### 우리집 관리를 위한 세입자 임대인 소통 서비스

> 27th ON SOPT Appjam '하우징'
>
> 기분 좋은 문의 요청, 부담 없는 문제 해결! 방구석 소통의 시작, 하우징.
>
> 프로젝트 기간 2020.12.26 ~ 2021.1.16

<div span=center>
<img src="https://user-images.githubusercontent.com/47289479/104741217-3f104180-578c-11eb-96d4-c19ba5d7119e.png" width = "40%" >
<img src="https://user-images.githubusercontent.com/47289479/104741220-40416e80-578c-11eb-8f60-f0017637d679.png" width = "40%" > 
</div>

<br/>

## 🚩 Project Core Value

- **짓다** - 소통의 공간을 짓다
- **잇다** - 자취생과 집주인을 잇다
- **얻다** - 좋은 집주인과 세입자를 얻다  

<br/>


 
## 📑Meeting Log

- [20.12.27 1차 회의 ~ 21.01.15 정기 회의](https://www.notion.so/4f27928b98fb4d8bbe55b07dd3f30799)

<br/>

## 🛠 Tech Stack

| Category |       What we used           |
|:--------| :------------------ |
|JetPack Components | ViewModel, LiveData, Lifecycle, ViewBinding
| Network | OkHttp, Retrofit2|
| Other Tools | Notion, Slack, Zeplin, Figma |

<br/>

## ⚡Branching

- **Git-flow**

  ```
  master : 제품으로 출시될 수 있는 브랜치
  develop : 다음 출시 버전을 개발하는 브랜치
  feature : 기능을 개발하는 브랜치
  release : 이번 출시 버전을 준비하는 브랜치
  hotfix : 출시 버전에서 발생한 버그를 수정하는 브랜치
  ```

- **브랜치명**

  - 접두어/담당뷰

    ex) feature/home, feature/home-detail
	
  - 접두어/기능명  

	ex) feature/date-picker, feature/time-picker  

<br/>

## 🚨Issue Naming

- **Labels**

  - *Android* : 공통 작업

  - *docs* : 문서 작업

  - *enhancement* : 새로운 기능

  - *network* : 통신 작업

  - *ui* : 뷰 작업

    

- **Title** 

  - [담당뷰] title

    ex) [Home] 소통하기 리스트 추가

<br/>

## 🌈Naming Rule

- **drawables**

  - **shapable : border_color_line/fill_[radius]**

    ex) border_gray_fill, border_gray_line_16

  - **selector : selector_[where]_[description]**

    ex) selector_ sign_up_gender_man / selector_ sign_up_gender_woman

  - **icon : icon_name**

- **view**

  **elementType_where_action**  ex) btn_home_login

  - btn (Button)
  - img (ImageView)
  - txt (TextView)
  - edt (EditText)
  - vp (ViewPager)
  - rv (RecyclerView)
  - rv_item (RecyclerView Item)
  - tl (TabLayout)

<br/>

## 💬Commit Message Convention

- 대문자 TYPE 접두어 사용

- 깃 이슈 번호 붙이기

- [TYPE] #이슈번호 : 내용(한글)

  ex) **[FEAT] #1 : 회원가입 추가**

<br/>

## 📜Libraries

|    라이브러리                                                                    |          목적         |      |
| :-----------------------------------------------------------------------------: | :------------------: | ---- |
| [Material-Calendar](https://github.com/Applandeo/Material-Calendar-View)        |  아이콘 캘린더 사용   |      |
|  [Lottie-for-android](https://github.com/airbnb/lottie-android)                 |  스플래시에 로티 사용   |       |
| [Retrofit2](https://github.com/square/retrofit) | 서버 통신 |
| [Gson](https://github.com/google/gson) | 서버 통신 |
|[StickyScrollView](https://github.com/amarjain07/StickyScrollView) |  홈 TabLayout에 적용  |
|[kotlinx.coroutines](https://github.com/Kotlin/kotlinx.coroutines) | 스플래시 |


<br/>

## 👩‍💻Developer & Role👨‍💻

|    기능     |        상세 기능        | 담당자 | 구현 여부 | 통신 구현 여부 |
| :---------: | :---------------------: | :----: | :-------: | :------------: |
|  스플래시   |        스플래시         |  예인  |     ✅     |       ✅        |
|   로그인    |         로그인          |  가은  |     ✅     |       ✅        |
|  회원가입   |        초대 인증        |  가은  |      ✅     |       ✅       |
|             |        회원가입         |  가은  |     ✅     |       ✅        |
|  소통하기   |        소통하기         |  명희  |     ✅     |       ✅        |
|             |       문의 작성        |  승완  |     ✅     |       ✅        |
|            |      문의 사진 첨부      |  승완  |     ✅     |       ✅        |
|            |      약속 일정 잡기      |  승완  |     ✅     |       ✅        |
|   캘린더    |         캘린더          |  예인  |     ✅     |       ✅        |
|            |  당일 문의/공지사항 목록  |  예인  |     ✅     |      ✅        |
| 우리집 소식 |      임대인 프로필      |  가은  |     ✅     |       ✅        |
|             |        공지사항         |  가은  |     ✅     |       ✅        |

<br/>
<br/>


## 🔍핵심 기능 구현 방법 및 코드, 화면
### : wiki에 맡은 부분별로 정리되어 있어요 :)
- [스플래시, 캘린더](https://github.com/teamHousing/Housing_Android/wiki/%EC%98%88%EC%9D%B8-%EC%BA%98%EB%A6%B0%EB%8D%94,-%EC%8A%A4%ED%94%8C%EB%9E%98%EC%8B%9C)
- [회원가입, 로그인, 우리집 소식](https://github.com/teamHousing/Housing_Android/wiki/%EA%B0%80%EC%9D%80--%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85,-%EB%A1%9C%EA%B7%B8%EC%9D%B8,-%EC%9A%B0%EB%A6%AC%EC%A7%91-%EC%86%8C%EC%8B%9D)
- [소통하기](https://github.com/teamHousing/Housing_Android/wiki/%EB%AA%85%ED%9D%AC-%ED%99%88,-%EC%86%8C%ED%86%B5%ED%95%98%EA%B8%B0)
- [문의 작성, 약속 일정 잡기](https://github.com/teamHousing/Housing_Android/wiki/%EC%8A%B9%EC%99%84---%EB%AC%B8%EC%9D%98-%ED%95%98%EA%B8%B0,-%EC%95%BD%EC%86%8D-%ED%95%98%EA%B8%B0)

<br/>

## 🧱Project Structure

```
📦housing
 ┣ 📂network
 ┣ 📂ui
 ┃ ┣ 📂calender
 ┃ ┣ 📂home
 ┃ ┃ ┣ 📂adapter
 ┃ ┃ ┣ 📂ask
 ┃ ┃ ┣ 📂detail
 ┃ ┃ ┃ ┣ 📂adapter
 ┃ ┃ ┣ 📂viewmodel
 ┃ ┣ 📂join
 ┃ ┣ 📂login
 ┃ ┣ 📂main
 ┃ ┣ 📂notice
 ┃ ┗ 📂splash
 ┣ 📂util
 ┣ 📂utill
 ┗ 📂vo
```


## 👩‍👩‍👧‍👦하우드안드 

> 💻 Housing Android Developer

|          **👧 [남가은](https://github.com/xxeun)**          |        **🧑 [양승완](https://github.com/wandukong)**         |          **👩 [이예인](https://github.com/yenny07)**          |        **👩‍🦰 [천명희](https://github.com/Haeeul)**         |
| :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: | :----------------------------------------------------------: |
| <img src="https://user-images.githubusercontent.com/47289479/104738681-2e120100-5789-11eb-9dc2-d2f47823d1b9.jpg" width="200" height="200" /> | <img src = "https://user-images.githubusercontent.com/47289479/104737946-3ddd1580-5788-11eb-9669-fc3190483463.jpg" width="200" height="200" /> | <img src = "https://user-images.githubusercontent.com/47289479/104737712-f5255c80-5787-11eb-9168-af495dd0cd43.jpg" width="200" height="200" /> | <img src = "https://user-images.githubusercontent.com/47289479/104737807-15551b80-5788-11eb-9b44-2d7ffa4f0b78.jpg" width="200" height="200" /> |
|                    Android Lead Developer                    |                      Android Developer                       |                      Android Developer                       |                      Android Developer                       |


