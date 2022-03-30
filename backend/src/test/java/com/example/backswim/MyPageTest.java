package com.example.backswim;

import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.member.params.ChangePasswordParam;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.mypage.CheckPasswordParam;
import com.example.backswim.member.params.mypage.UpdateUserPassword;
import com.example.backswim.member.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import com.example.backswim.TestPackage;

import javax.servlet.http.Cookie;
import javax.transaction.Transactional;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class MyPageTest extends TestPackage{

    private MockMvc mockMvc;

    @Value("${security.jwt.temp.token}")
    private String token;

    @Value("${security.jwt.temp.expired_token}")
    private String expiredToken;

    @Autowired
    UserService userService;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    static Stream<Arguments> checkPasswordSuccess() {
        return Stream.of(
                Arguments.arguments(new CheckPasswordParam("backswim123"))
        );
    }
    static Stream<Arguments> changePasswordNullParam() {
        return Stream.of(
                Arguments.arguments(new CheckPasswordParam())
        );
    }

    static Stream<Arguments> changePasswordWrongPassword() {
        return Stream.of(
                Arguments.arguments(new CheckPasswordParam("aslkdjgklwjkq"))
        );
    }
    static Stream<Arguments> updateuserpasswordsuccess(){
        return Stream.of(
                Arguments.arguments(new UpdateUserPassword("backswim123","qwertqqq")),
                Arguments.arguments(new UpdateUserPassword("backswim123","zxcvaaaaa"))
        );
    }
    static Stream<Arguments> updateuserpasswordnull(){
        return Stream.of(
                Arguments.arguments(new UpdateUserPassword("null","null")),
                Arguments.arguments(new UpdateUserPassword("null","null"))
        );
    }
    @DisplayName("마이페이지 비밀번호 체크 성공")
    @ParameterizedTest(name="/api/mypage/checkpassword")
    @MethodSource("checkPasswordSuccess")
    public void checkPasswordSuccess(CheckPasswordParam param) throws Exception{
        mockMvc.perform(post("/api/mypage/checkpassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON).cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));
    }

    @DisplayName("마이페이지 비밀번호 체크 실패 Null Param")
    @ParameterizedTest(name="/api/mypage/checkpassword")
    @MethodSource("changePasswordNullParam")
    public void checkPasswordNullParam(CheckPasswordParam param) throws Exception{
        mockMvc.perform(post("/api/mypage/checkpassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON).cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.BAD_REQUEST.name()));
    }

    @DisplayName("마이페이지 비밀번호 체크 비밀번호 오류")
    @ParameterizedTest(name="/api/mypage/checkpassword")
    @MethodSource("changePasswordWrongPassword")
    public void checkPasswordWrongPassword(CheckPasswordParam param) throws Exception{
        mockMvc.perform(post("/api/mypage/checkpassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON).cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(401)))
                .andExpect(jsonPath("$.data",is(false)))
                .andExpect(jsonPath("$.message").value(StatusEnum.WRONG_PASSWORD.name()));
    }


    @DisplayName("마이페이지 정보 GET 성공")
    @Test
    public void getMyPageTest() throws Exception{
        mockMvc.perform(get("/api/mypage/getmypage").cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));
    }

    @DisplayName("마이페이지 정보 토큰 null")
    @Test
    public void getMyPageTokenNullTest() throws Exception{
        mockMvc.perform(get("/api/mypage/getmypage"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(401)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.LOGIN_FIRST.name()));
    }

    @DisplayName("마이페이지 정보 토큰 만료")
    @Test
    public void getMyPageTokenExpiredTest() throws Exception{

        mockMvc.perform(get("/api/mypage/getmypage").cookie(new Cookie("JWTTOKEN",expiredToken)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(401)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.EXPIRED_TOKEN.name()));
    }

    @DisplayName("마이페이지 비밀번호 수정 성공")
    @ParameterizedTest(name="/api/mypage/updateuserpassword")
    @MethodSource("updateuserpasswordsuccess")
    @Transactional
    public void changePassword(UpdateUserPassword param) throws Exception{
        mockMvc.perform(post("/api/mypage/updateuserpassword").cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));

    }

    @DisplayName("마이페이지 비밀번호 수정 널 파람")
    @ParameterizedTest(name="/api/mypage/updateuserpassword")
    @MethodSource("updateuserpasswordnull")
    @Transactional
    public void changePasswordnullTest(UpdateUserPassword param) throws Exception{
        mockMvc.perform(post("/api/mypage/updateuserpassword").content(toJson(param)).cookie(new Cookie("JWTTOKEN",token)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.BAD_REQUEST.name()));

    }
}
