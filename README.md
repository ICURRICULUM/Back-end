# I-Curriculum(서버) Convention 정리

## 작업 FLOW

### 마일스톤 생성
- 주 마다 마일스톤 생성 
- 이번주에 해야 할일 정하고 나누고, 다같이 todo 만들기

### issue 생성 
- 각자 만든 todo에 따라 issue 생성
  - 제목 : `[{태그}]:{간단한 제목}`
- project -> todo로 issue 생성
  - assignment, label, 해당 주차 milstone 설정하기

#### 작업에 해당하는 브랜치 만들기
#### Todo 에서 In Progress 로 옮기고 작업시작


## 브랜치 관리 
### 브랜치 naming rule
```
feature/{issue 번호}  -> 각자 기능 개발할 때 사용, 로컬에서 각자 테스트

	(Squash and Merge)

hotfix/{issue 번호} -> merge후 오류 수정, 혹은 api 연동시 빠르게 수정하는 경우

develop  -> 개발한 기능을 병합, 로컬에서 테스트 진행

	(Release 해당 Develop 브랜치에서 생성)

release  -> develop 브랜치를 로컬에서 실행 시 문제가 없으면 해당 커밋에 release 브랜치 생성
-> 개발시 테스트용 서버로 배포

	(Commit and Merge)

main  -> 실제 운영할 서버로 배포, release에서 QA 후 main으로 merge
```

### pull request
- pr 이름은 issue랑 똑같이
- 대신 []안에 티켓번호(이슈 번호도 붙이기)
  - ex. [Docs-10] : github pr, commit 컨벤션 관련 README 작성
#### review
- line by line 으로 읽으면서 comment
- 초반에 코드 와르르 올라올때는 진짜 제대로 review 할것
- approve 4개 이상 못받으면 merge 불가

#### merge
- merge는 develop 브랜치를 타켓으로 `squash and merge`
- 만든 브랜치는 삭제



## code convention

### 주석 규칙 - 권장

[좋은 주석과 나쁜 주석](https://velog.io/@hangem422/clean-code-comment)

### 구글의 Java 스타일 기반으로 Code Style 구성

[intellij-java-google-style.xml](https://prod-files-secure.s3.us-west-2.amazonaws.com/0c69a890-5de7-48c1-b7e4-d54ed76f7022/40dcb948-78f2-42fd-a243-4aa32dca53b6/intellij-java-google-style.xml)

[[IntelliJ] Code Style(Google Style) 적용하기](https://tychejin.tistory.com/334)

### 디렉토리 구조

```bash
(예시)

├── 🗂com.icurri
   ├── domain
   |   ├── member
   |   |   ├── controller
   |   |   ├── dto
   |   |   ├── entity
   |   |   └── service
   |   |
   |   └── department
   |       ├── controller
   |       ├── dto
   |       ├── entity
   |       └── service
   |   
   └── global
       ├── config(설정)
       ├── response(응답관련)
       ├── common(도메인 공통 사용)
			 ├── S3
       └── util(유틸, 날짜, 스트링변환등)
```

### Java 코딩 스타일 가이드

[](http://developer.gaeasoft.co.kr/development-guide/java-guide/java-coding-style-guide/)

### 중괄호 스타일 (K&R) - Java 표준

```bash
if (condition) 
{
  // 이거 말고 X
} 
if (condition) {
  // 이거로 O
} 
```

### 클래스, 인터페이스 이름

- 클래스 이름
    - Customer
    - SalesOrder
- 패키지 이름
    - 소문자만 사용
- 변수 이름
    - camelCase

### DTO 이름

- 뒤에 붙이는 규칙

```markdown
단일+요약 조회 : SimpleInfoDTO - 단일 게시글의 일부만 반환할 때
단일+상세 조회 : DetailInfoDTO - 단일 게시글의 모든 데이터를 반환할 때
리스트+요약 조회 : SimpleListDTO - 다중 게시글 각각의 일부만 반환할 때
리스트+상세 조회 : DetailListDTO - 다중 게시글 각각의 모든 데이터를 반환할 때
```
- 도메인 별로 Response, Request DTO 클래스 파일 만들고 안에 nested static class 로 DTO 생성
- 인스턴스 생성 방지를 위해 `abstract` 추상클래스로 선언 
```java
public abstract class MemberResponse {
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class SimpleInfoDTO {
        private String name;
        private String nickname;
        private String gender;
        private String birthday;
    }
}
```
- Converter 활용
- 인스턴스 생성 방지를 위해 `abstract` 추상클래스로 선언

```java
public abstract MemberConverter {
    public static Member toMember(
            String clientId,
            MemberRequestDTO.SignUpRequestDTO signUpRequestDTO) {

        return Member.builder()
                .clientId(clientId)
                .socialType(ClientIdMaker.getSocialTypeInClientId(clientId))
                .role(Role.USER)
                .name(signUpRequestDTO.getName())
                .nickname(signUpRequestDTO.getNickname())
                .gender(Gender.MALE)
                .birthDay(signUpRequestDTO.getBirthday())
                .persona(signUpRequestDTO.getPersona())
                .build();
    }

    public static MemberResponseDTO.MemberInfoDTO toMemberInfoDTO(Member member) {

        return MemberResponseDTO.MemberInfoDTO.builder()
                .name(member.getName())
                .nickname(member.getNickname())
                .gender(member.getGender().toString())
                .birthday(member.getBirthDay().toString())
                .persona(member.getPersona())
                .build();
    }

    // ...
}
```
### 컨트롤러, 서비스 메소드명
- 메소드에 사용되는 단어, CRUD를 뜻하는 단어는 동사를 사용!!!
- CRUD단어는 접두사로!
- (crud명)- (도메인) - (suffix)
    - CRUD명 컨벤션
        - **등록 및 작성**
            - create
        - **수정**
            - 완전 수정 → update
            - 일부 수정 → change
        - **삭제**
            - 논리 삭제 → remove
            - 물리 삭제 → delete
        - **조회**
            - get사용
            - 조회는 너무 다양하고 복잡(목록 조회, 상세 조회,간단 조회 등등) → 접미사로 구분
            - 목록조회는 LIST를 접미사로!
                - getUserList
                - getRoomList
            - 단일항목은 항목 이름을 접미사로!
                - getUser
                - getRoom
            - 최소한의 데이터 호출시 SIMPLE을 접미사로!
                - getUserSimple
                - getRoomSimple

### 컨트롤러 규칙

- Controller에서 http메소드와 api명 명시
- service에서는 복잡한 로직, 혹은 다른 함수에서 사용되는 함수일 경우에 //를 통한 기입
- 파라미터는 두번째 줄에 작성
- 파라미터는 하나당 한 라인 사용

```java
@PostMapping("/sign-up")
public ApiResponse<TokenInfo> register(
	@Valid @RequestBody RegisterMember registerMember) {
		return  ApiResponse<>(memberService.registerMember)
```

### Restful Api 설계 규칙

https://dev-cool.tistory.com/32


- 엔티티만 해당 폴더에 두고, 나머지는 각 기능별로 패키지 감싸기
- 로그인 → 리프레시 토큰 저장
- ApiResponse로 감싸서 반환
- 400 BAD REQUEST로 통일
- ENUM의 사용 주의
- string 요구사항 추가되는 경우에 db에서도 추가해줘야 함
- Static 상수는 각 상수 맨 위에 Upper Case

## Commit Convention

### header
#### 태그
태그 : 제목의 형태이며, : 뒤에만 space 사용
```
feat : 새로운 기능 추가
fix : 버그 수정
docs : 문서 수정
style : 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
refactor : 코드 리펙토링
test : 테스트 코드, 리펙토링 테스트 코드 추가
chore : 빌드 업무 수정, 패키지 매니저 수정
```
#### subject
최대 50글자가 넘지 않도록
간략 한 설명을 적을것

### body
본문은 다음의 규칙을 지킨다.

본문은 한 줄 당 72자 내로 작성한다.
본문 내용은 양에 구애받지 않고 최대한 상세히 작성한다.
본문 내용은 어떻게 변경했는지 보다 무엇을 변경했는지 또는 왜 변경했는지를 설명

### footer
선택사항
꼬리말은 "유형: #이슈 번호" 형식으로 사용
여러 개의 이슈 번호를 적을 때는 쉼표(,)로 구분
이슈 트래커 유형은 다음 중 하나를 사용한다.
- Fixes: 이슈 수정중 (아직 해결되지 않은 경우)
- Resolves: 이슈를 해결했을 때 사용
- Ref: 참고할 이슈가 있을 때 사용
- Related to: 해당 커밋에 관련된 이슈번호 (아직 해결되지 않은 경우)

#### Example
```
docs: commit convention 문서화

commit convention을 문서화 하였습니다.

Resolves: #10
```


---
## 응답 및 예외 전략

### 📜 ApiResponse Class

**ApiResponse 클래스 정의**
```java
public record ApiResponse<T>(
    Boolean isSuccess,
    String code,
    String message,
    T result) {
...
```

* **Record 타입**: 이 클래스는 Java 14의 **Record** 타입으로 정의되었습니다. Record는 **불변 데이터 클래스**로 주로 데이터 전송 객체(DTO)를 단순하게 생성할 때 사용됩니다.
* **Generics 사용 (****<T>****)**: 응답이 다양한 데이터 타입을 가질 수 있도록 제네릭 타입 매개변수 T를 사용하고 있습니다.
* **필드 설명**:
    * `Boolean isSuccess`: 요청의 성공 여부를 나타냅니다.
    * `String code`: 응답 상태를 나타내는 코드입니다. 응답 코드를 담습니다.
    * `String message`: 응답에 대한 메시지입니다. 주로 응답에 대한 설명을 저장합니다.
    * `T result`: 응답의 결과 데이터를 포함합니다. 결과는 제네릭 타입으로 지정되어 있어 다양한 유형의 데이터를 담을 수 있습니다.


**상수 및 메서드**

```java
public static final ApiResponse<Void> OK = new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(), null);

```

* **OK** **상수**: OK는 성공적인 응답을 나타내는 **정적 상수**로, 아무런 결과 데이터(result)가 없을 때 사용됩니다.
    * 성공 시, 전달하고 싶은 데이터가 없을 때 Controller에서 `return ApiResponse.OK` 작성합니다.


```java
public static <T> ApiResponse<T> onSuccess(T result) {
    return new ApiResponse<>(true, SuccessStatus.OK.getCode(), SuccessStatus.OK.getMessage(),
        result);
}

```
* **onSuccess** **메서드**: 성공적인 응답을 생성하기 위한 **정적 팩토리 메서드**입니다.
    * **매개변수**: 성공적인 결과 데이터 (result).
    * **반환 값**: isSuccess가 true이고, 상태 코드는 성공 상태 (SuccessStatus.OK)인 ApiResponse 객체를 반환합니다.


```java
public static <T> ApiResponse<T> onFailure(ErrorStatus errorStatus, T data) {
    return new ApiResponse<>(false, errorStatus.getCode(), errorStatus.getMessage(), data);
}

```

* **onFailure** **메서드**: 오류 응답을 생성하기 위한 **정적 팩토리 메서드**입니다.
    * **매개변수**: 오류 상태 (errorStatus)와 추가적인 결과 데이터 (data).
    * **반환 값**: isSuccess가 false이고, 오류 상태 코드 및 메시지를 가지는 ApiResponse 객체를 반환합니다.
    * 보통 ExceptionHandler에서 사용하게 될 메서드 입니다.


### 📜 ErrorStatus Class
⠀
```java
@Getter
@RequiredArgsConstructor
public enum ErrorStatus {
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "COMMON500", "서버 에러, 관리자에게 문의 바랍니다."),

...

    private final HttpStatus httpStatus;
    private final String code;
    private final String message;

}

```

- ErrorStatus 클래스는 애플리케이션의 오류 상태를 관리하기 위한 **열거형(enum)**입니다.

* **필드**
    * `HttpStatus httpStatus`: HTTP 상태 코드를 나타냅니다. 새로 생성할 시, 컨벤션에 맞춰 400으로 통일합니다.
    * `String code`: 커스텀 오류 코드를 정의합니다.
    * `String message`: 사용자에게 표시할 오류 메시지입니다.
- **사용법**
    - 비즈니스적 오류가 발생하여 런타임 예외를 개발자가 발생시키고 싶거나, 체크 예외를 catch해서 런타임예외로 바꾸고 싶다면 ErrorStatus 클래스 내에 새로운 데이터를 정의하고 이를 GeneralException 클래스에 담아서 throw 합니다.


### 📜 GeneralException Class

```java
@Getter
public class GeneralException extends RuntimeException {

    private final ErrorStatus errorStatus;
    private final Object data;

    public GeneralException(ErrorStatus errorStatus) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.data = null;
    }

    public GeneralException(ErrorStatus errorStatus, Object data) {
        super(errorStatus.getMessage());
        this.errorStatus = errorStatus;
        this.data = data;
    }

    public GeneralException(ErrorStatus errorStatus, Throwable cause) {
        super(errorStatus.getMessage(), cause);
        this.errorStatus = errorStatus;
        this.data = null;
    }

    public GeneralException(ErrorStatus errorStatus, Object data, Throwable cause) {
        super(errorStatus.getMessage(), cause);
        this.errorStatus = errorStatus;
        this.data = data;
    }
}


```

- GeneralException 클래스는 애플리케이션에서 발생할 수 있는 예외를 처리하기 위해 정의된 **사용자 정의 예외 클래스**입니다. 이 클래스는 **런타임 예외 (RuntimeException)**를 상속받아, 런타임에서 발생하는 오류를 다룹니다. 클래스 구성은 다음과 같습니다:

**필드**
```java
private final ErrorStatus errorStatus;
private final Object data;
```

* `ErrorStatus errorStatus`: 발생한 오류의 상태를 나타내는 **ErrorStatus** 객체입니다.
* `Object data`: 예외 발생 시 추가적인 정보를 전달할 수 있는 데이터입니다.


**생성자**
```java
public GeneralException(ErrorStatus errorStatus) {
    super(errorStatus.getMessage());
    this.errorStatus = errorStatus;
    this.data = null;
}

public GeneralException(ErrorStatus errorStatus, Object data) {
    super(errorStatus.getMessage());
    this.errorStatus = errorStatus;
    this.data = data;
}

public GeneralException(ErrorStatus errorStatus, Throwable cause) {
    super(errorStatus.getMessage(), cause);
    this.errorStatus = errorStatus;
    this.data = null;
}

public GeneralException(ErrorStatus errorStatus, Object data, Throwable cause) {
    super(errorStatus.getMessage(), cause);
    this.errorStatus = errorStatus;
    this.data = data;
}


```

* **첫 번째 생성자**: ErrorStatus만 받아서 예외를 생성하며, 추가 데이터는 null로 설정합니다.
* **두 번째 생성자**: ErrorStatus와 함께 추가 데이터를 받아 예외를 생성합니다.
* **세 번째 생성자**: ErrorStatus와 **발생 원인 (cause)**을 받아 예외를 생성합니다. 예외 발생 원인을 포함하여 추적이 가능하도록 합니다.
* **네 번째 생성자**: ErrorStatus, 에러 데이터 (data), 그리고 발생 원인 (cause)를 모두 받아 예외를 생성합니다.


### 사용 예시

**단순한 오류 상태만 사용하여 예외 생성**
```java
throw new GeneralException(ErrorStatus.BAD_REQUEST);
```

**추가 데이터와 함께 예외 생성**
```java
throw new GeneralException(ErrorStatus.MEMBER_MAJOR_NOT_FOUND, this);
```
- 비즈니스적 에러가 발생 시 해당 방식을 권장합니다.

**발생 원인 (****cause****)과 함께 예외 생성**
```java
try {
    ...
} catch (Exception e) {
    throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR, e);
}

```
- **발생 원인**을 포함하여 GeneralException을 던져 예외 추적이 가능합니다.
- 체크 예외를 언체크 예외로 전환할 때, **반드시 예외를 포함해서 던지도록 합니다.**

**발생 원인 (cause), 데이터와 함께 예외 생성**
```java
try {
    ...
} catch (Exception e) {
    throw new GeneralException(ErrorStatus.INTERNAL_SERVER_ERROR, this, e);
}

```

⠀

## 📜 GlobalExceptionHandler
```java
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(GeneralException.class)
    public ResponseEntity<ApiResponse<Object>> handleGeneralException(GeneralException ex) {
        log.error("Error Code: {}, Message: {}, Data: {}",
            ex.getErrorStatus().getCode(),
            ex.getErrorStatus().getMessage(),
            ex.getData() != null ? ex.getData() : "No additional data",
            ex
        );

        return ResponseEntity
            .status(ex.getErrorStatus().getHttpStatus())
            .body(ApiResponse.onFailure(
                ex.getErrorStatus(),
                ex.getData()
            ));
    }
}

```

- **로깅**
    * Error Code: 예외의 상태 코드 (ErrorStatus에 정의된 코드).
    * Message: 예외의 메시지.
    * Data: 추가적인 오류 데이터, 없을 경우 "No additional data"로 표시.
    * ex: **스택 트레이스**가 함께 기록되어 예외 발생 경로와 원인까지 확인할 수 있습니다.

- **응답생성**
    - 예외의 상태 코드를 사용하여 HTTP 응답 상태를 설정합니다. 저희 프로젝트 컨벤션에 맞춰 `ErrorStatus`를 400으로 생성하면, 항상 상태코드는 400으로 클라이언트에게 전달됩니다.
    - `ApiResponse.onFailure()`: 오류 응답을 생성하는 정적 메서드를 사용하여, 실패 상태의 응답 객체를 생성합니다.

- 추후 메소드 추가 가능성
    - GeneralException 이외에도, Spring에서 정의한 예외를 잡기 위해, 해당 GlobalExceptionHandler 내에서 메소드를 추가하여 처리하도록 확장할 수 있습니다.

---