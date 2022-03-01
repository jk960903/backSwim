package com.example.backswim;

import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.component.JwtComponent;
import com.example.backswim.member.params.JoinMemberParam;
import com.example.backswim.member.params.login.LoginRequestParam;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 이페이지 그대로 실행하면 돌아가지 않음
 * 비밀번호는 개인정보이기 때문에 git에 올리기 위해 비밀번호부분은 가렸습니다.
 */
@SpringBootTest
@AutoConfigureMockMvc
@RequiredArgsConstructor
public class UserLoginTest extends TestPackage{

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    static Stream<Arguments> LoginSuccessCase(){
        return Stream.of(
                Arguments.arguments(new LoginRequestParam("benzen903@gmail.com","*****"))
        );
    }
    static Stream<Arguments> LoginFailNotEmailAuth(){
        return Stream.of(
                Arguments.arguments(new LoginRequestParam("jk960903@gmail.com","*****")),
                Arguments.arguments(new LoginRequestParam("jk960903@naver.com","*****"))
        );
    }

    static Stream<Arguments> LoginFailWrongPassword(){
        return Stream.of(
                Arguments.arguments(new LoginRequestParam("benzen903@gmail.com","*****")),
                Arguments.arguments(new LoginRequestParam("benzen903@gmail.com","*****"))
        );
    }

    static Stream<Arguments> LoginFailNullParam(){
        return Stream.of(
                Arguments.arguments(new LoginRequestParam())
        );
    }




    @DisplayName("로그인 성공 케이스")
    @ParameterizedTest(name="/api/login/login")
    @MethodSource("LoginSuccessCase")
    public void loingSuccessTest(LoginRequestParam param) throws Exception{
        mockMvc.perform(post("/api/login/login").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));
    }

    @DisplayName("로그인 실패 이메일 인증 X")
    @ParameterizedTest(name="/api/login/login")
    @MethodSource("LoginFailNotEmailAuth")
    public void loingFailNotEmailAuth(LoginRequestParam param) throws Exception{
        mockMvc.perform(post("/api/login/login").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(401)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.AUTH_FIRST.name()));
    }

    @DisplayName("로그인 실패 비밀번호 오류")
    @ParameterizedTest(name="/api/login/login")
    @MethodSource("LoginFailWrongPassword")
    public void loingFailWrongPassword(LoginRequestParam param) throws Exception{
        mockMvc.perform(post("/api/login/login").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(401)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.WRONG_PASSWORD.name()));
    }

    @DisplayName("로그인 실패 파라미터 null")
    @ParameterizedTest(name="/api/login/login")
    @MethodSource("LoginFailNullParam")
    public void loingSuccessFail(LoginRequestParam param) throws Exception{
        mockMvc.perform(post("/api/login/login").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.BAD_REQUEST.name()));
    }
}
