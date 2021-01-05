# 🏡Housing

#### 우리집 관리를 위한 세입자 임대인 소통 서비스

> 27th ON SOPT Appjam '하우징'
>
> 기분 좋은 문의 요청, 부담 없는 문제 해결! 방구석 소통의 시작, 하우징.
>
> 프로젝트 기간 2020.12.26 ~ 진행중

<div span=center>
<img src="https://user-images.githubusercontent.com/39720852/103648655-8fbfb780-4fa0-11eb-8c01-d10da8e37ea3.png" width = "40%" >
<img src="https://user-images.githubusercontent.com/39720852/103648648-8df5f400-4fa0-11eb-8f8a-70937a755c45.png" width = "40%" > 
</div>

<br/>

## 🚩 Project Core Value

- **짓다** - 소통의 공간을 짓다
- **잇다** - 자취생과 집주인을 잇다
- **얻다** - 좋은 집주인과 세입자를 얻다  

<br/>

## 👩‍💻Developer & Role👨‍💻

🐶**가은** [xxeun](https://github.com/xxeun)

```
- 온보딩
- 회원가입
- 우리집 소식
```

🟢**승완** [wandukong](https://github.com/wandukong)

```
- 문의하기
- 약속하기
```

🌊**예인** [yenny07](https://github.com/yenny07)

```
- 캘린더
```

🦕**명희** [Haeeul](https://github.com/Haeeul)

```
- 소통하기
```

<br/>

## 📑Meeting Log

**1주차**

- 20.12.27 1차 회의 - [일정 및 목표 공유, 과제 확인](https://www.notion.so/2020-12-27-1-340cd3cf97c2427a8d5fd79eec42cbf9)
- 20.12.28 2차 회의 - [git issue 설명](https://www.notion.so/2020-12-28-2-git-issue-d2d6d3d76a884c9aa2686403ae806d02)
- 20.12.29 3차 회의 - [룰세팅](https://www.notion.so/2020-12-29-3-bb31c936a5b54d9cae6f6f047dd72c56)
- 20.12.30 4차 회의 - [DataBinding에 대해](https://www.notion.so/2020-12-30-4-DataBinding-be32c6fa317b4dbbb9407f0ccd7acf6b)
- 20.01.01 5차 회의 - [역할분담 및 ViewModel에 대해](https://www.notion.so/2021-01-01-5-ViewModel-aaf099b3a8c14d4ab8823ff103143c21)
- 20.01.02 6차 회의 - [git issue에 대해](https://www.notion.so/2021-01-02-6-git-issue-b01c951d43d947b698a4fb9caf632a11)

**2주차**

- 20.01.03 7차 회의 - [진행 상황 공유](https://www.notion.so/2021-01-03-7-50245148eb654d98ad03438206ae470e)
- 20.01.05 8차 회의 - [오프라인 모임 및 과제 점검](https://www.notion.so/2021-01-05-8-70dadf0af5044ab0877efc5eedbbf135)

<br/>

## 🔧 Tools

- Android Studio
- Zeplin
- Figma

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

## 🧱Project Structure

```
🏡housing
 ┣ 📂network
 ┣ 📂ui
 ┃ ┣ 📂calender
 ┃ ┃ ┗ 📜CalenderFragment.kt
 ┃ ┣ 📂home
 ┃ ┃ ┗ 📜HomeFragment.kt
 ┃ ┣ 📂main
 ┃ ┃ ┗ 📜MainActivity.kt
 ┃ ┗ 📂notice
 ┃ ┃ ┗ 📜NoticeFragment.kt
 ┣ 📂util
 ┗ 📂vo
```

