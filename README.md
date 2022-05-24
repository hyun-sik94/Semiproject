# Semiproject

자바만을 사용하여 만든 [스파이더맨]컨셉의 미사일 슈팅게임 입니다.

# 플레이 방법

소스파일 다운로드 후 SpiderGame.java를 실행시켜주면 됩니다.

방향키와 컨트롤키, 스페이스바키를 통해 조작하며, 적군의 공격을 피하고 적군을 물리쳐 점수를 쌓아 목표 스테이지에 도달하면 승리하는 게임 방식입니다.
적군을 미사일을 통해 제거하는 경우 게임 스코어와, 필살기 점수가 증가되며 필살기의 경우 일정 점수를 소모하여 사용이 가능합니다.

게임 실행 시, 로딩 화면으로 게임 조작방법이 등장하고 배경음악이 재생됩니다.

미사일 발사 시 미사일 효과음과, 적군 제거시 적군제거 효과음이 재생됩니다.

조건에 따라 게임을 승리하는 경우 게임 승리화면으로 변경되고, 게임 패배 시 패배 화면으로 전환되며 약 3초간 실행되었다가 자동 종료됩니다.

# 개발환경


개발 언어 : Java(Tm) SE Development Kit 11.0.13 


개발 도구 : Eclipse IDE for Enterprise Java and Web Developers

# 게임화면 
+ 설명(로딩)화면
![loading](https://user-images.githubusercontent.com/79977761/170080787-48784e57-2a9b-4d77-9e4d-ed231c39c5b7.png)
+ 게임화면
![캡처](https://user-images.githubusercontent.com/79977761/170080917-81807a8d-b94f-4ea6-b28b-eafca130dcab.PNG)


# 게임시현 영상
https://user-images.githubusercontent.com/79977761/170133810-66f8befb-b436-4199-902b-59ef8c718fed.mp4

# 흐름도와 클래스다이어그램
![캡처2](https://user-images.githubusercontent.com/79977761/170082187-c3bcf03e-841e-4aeb-95e3-6905013929f0.PNG)
![캡처3](https://user-images.githubusercontent.com/79977761/170082258-c9629c1f-2780-45a5-a44d-079f2c7f527e.PNG)

# 게임기능 
## 조작방법
1.방향키 ( →←↑↓) 로 플레이어의 비행기를 움직일 수 있다.
2.스페이스바(SPACE)로 플레이어의 미사일을 발사 할 수 있다.
3.컨트롤(Ctrl)로 플레이어가 필살기를 발사할 수 있다.(점수 20점 소모, 미사일과 적군 모두 제거)

## 플레이어
1.키보드 조작을 통해 움직이며 화면 밖으로는 나갈 수 없다.
2.스페이스바를 통해 미사일(횡방향 진행 x = 8 증가) 을 발사할 수 있으며, 적군에게 적중한 경우 게임 스코어와 필살기 점수가 증가한다.


## 적군
1. 그린고블린 - 횡방향(x = -1 증가)으로 이동하며 점수에 따라 등장하는 적의 수가 증가 되며, 플레이어에 부딪힐 경우 플레이어의 체력을 감소시킨다.
2. 닥터옥터퍼스 -  오른쪽 고정 된 x 좌표, 랜덤한 y좌표로 등장하며 위아래로 움직이며 미사일을 발사한다.
3. 펌킨(닥터옥터퍼스 미사일) - 닥터옥터퍼스의 좌표에서 발사되어 횡방향(x = -3 증가)으로 이동하며, 플레이어에 부딪힐 경우 플레이어의 체력을 감소시킨다.

## 승리조건
1. 적군을 물리치는 경우 게임 스코어가 1점 증가하며, 20점마다 게임 스테이지가 증가한다.
이를 통해 11스테이지를 클리어하는 경우 승리 화면이 나타난다.

## 패배조건
1. 적군에게 부딪히거나 미사일에 적중당해 체력이 0이 된 경우 패배 화면이 나타난다.

## 느낀점
상속을 통한 다중 클래스 구현을 사용해볼 수 있었고, 프로젝트 제작 중 쓰레드 사용, 승리＊패배 조건에 따른 화면전환, 자바 SWING기능 적용에 어려움이 있었지만 이를 해결하기 위해 팀원과의 소통을 통해 복사붙여놓기로는 얻을 수 없는 생각하는 과정을 통해 실력을 향상시킬 수 있었으며 결과물을 완성했을 때에는 높은 성취감을 얻을 수 있었다.

