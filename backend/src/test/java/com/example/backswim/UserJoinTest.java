package com.example.backswim;

import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.member.params.ChangePasswordParam;
import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;
import com.example.backswim.member.params.ResetPasswordParam;
import com.example.backswim.pool.params.SearchAddressParam;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import java.util.UUID;
import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserJoinTest extends TestPackage{

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }


    static Stream<Arguments> CheckDuplicateID(){
        return Stream.of(
                Arguments.arguments(new CheckDuplicateID("jk960903@naver.com")),
                Arguments.arguments(new CheckDuplicateID("benzen903@gmail.com")),
                Arguments.arguments(new CheckDuplicateID("benzen903@kakao.com")),
                Arguments.arguments(new CheckDuplicateID("jk960903@gmail.com"))

        );
    }

    static Stream<Arguments> CheckDuplicateFailID(){
        return Stream.of(
                Arguments.arguments(new CheckDuplicateID(null)),
                Arguments.arguments(new CheckDuplicateID("jk960903")),
                Arguments.arguments(new CheckDuplicateID("123455555")),
                Arguments.arguments(new CheckDuplicateID("zewsqqq"))

        );
    }

    static Stream<Arguments> AddMemberSuccess(){
        return Stream.of(
                Arguments.arguments(new JoinMemberParam("benzen903@gmail.com","!@#asdf!@#")),
                Arguments.arguments(new JoinMemberParam("jk960903@naver.com","!@#asdf!@#")),
                Arguments.arguments(new JoinMemberParam("jk960903@gmail.com","!@#asdf!@#")),
                Arguments.arguments(new JoinMemberParam("benzen903@kakao.com","!@#asdf!@#"))
        );
    }

    static Stream<Arguments> AddMemberFail(){
        return Stream.of(
                Arguments.arguments(new JoinMemberParam("benzen903","!@#asdf!@#")),
                Arguments.arguments(new JoinMemberParam("","!@#asdf!@#")),
                Arguments.arguments(new JoinMemberParam("jk960903@gmail.com","")),
                Arguments.arguments(new JoinMemberParam())
        );
    }

    static Stream<Arguments> resetPasswordSuccess(){
        return Stream.of(
                Arguments.arguments(new ResetPasswordParam("benzen903@gmail")),
                Arguments.arguments(new ResetPasswordParam("whow1101@naver.com")),
                Arguments.arguments(new ResetPasswordParam("jk960903@naver.com"))
        );
    }

    static Stream<Arguments> resetPasswordFailNotFoundUser(){
        return Stream.of(
                Arguments.arguments(new ResetPasswordParam("benzen9031@gmail.com")),
                Arguments.arguments(new ResetPasswordParam("whow11011@naver.com")),
                Arguments.arguments(new ResetPasswordParam("jk9609031@naver.com"))
        );
    }
    static Stream<Arguments> resetPasswordFailParameterNull(){
        return Stream.of(
                Arguments.arguments(new ResetPasswordParam()),
                Arguments.arguments(new ResetPasswordParam()),
                Arguments.arguments(new ResetPasswordParam())
        );
    }

    static Stream<Arguments> changePasswordSuccess(){
        return Stream.of(
                Arguments.arguments(new ChangePasswordParam("e6927d1e-c9e8-4875-8c17-07c4bd76dd0f","backswim123")),
                Arguments.arguments(new ChangePasswordParam("3111d996-7105-44a4-8b00-d8a6402884ba","backswim123")),
                Arguments.arguments(new ChangePasswordParam("a68b00a0-4701-4693-ba06-e8518aed0b47","backswim123"))
        );
    }

    static Stream<Arguments> changePasswordFailNotValidateUUID(){
        return Stream.of(
                Arguments.arguments(new ChangePasswordParam(UUID.randomUUID().toString(),"backswim123")),
                Arguments.arguments(new ChangePasswordParam(UUID.randomUUID().toString(),"backswim123")),
                Arguments.arguments(new ChangePasswordParam(UUID.randomUUID().toString(),"backswim123"))
        );
    }

    static Stream<Arguments> changePasswordExpiredDate(){
        return Stream.of(
                Arguments.arguments(new ChangePasswordParam("e6927d1e-c9e8-4875-8c17-07c4bd76dd0f","backswim123")),
                Arguments.arguments(new ChangePasswordParam("3111d996-7105-44a4-8b00-d8a6402884ba","backswim123")),
                Arguments.arguments(new ChangePasswordParam("a68b00a0-4701-4693-ba06-e8518aed0b47","backswim123"))
        );
    }
    static Stream<Arguments> changePasswordNullParam(){
        return Stream.of(
                Arguments.arguments(new ChangePasswordParam(null,"backswim123")),
                Arguments.arguments(new ChangePasswordParam("3111d996-7105-44a4-8b00-d8a6402884ba",null)),
                Arguments.arguments(new ChangePasswordParam())
        );
    }
    //15ms
    @DisplayName("이메일 중복체크 API 성공 테스트")
    @ParameterizedTest(name="/api/joinmember/duplicateid")
    @MethodSource("CheckDuplicateID")
    public void checkDuplicateSuccessTest(CheckDuplicateID param) throws Exception{

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());
        mockMvc.perform(post("/api/joinmember/duplicateid").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)));
    }

    //15ms
    @DisplayName("이메일 중복체크 API 실패 테스트")
    @ParameterizedTest(name="/api/joinmember/duplicateid")
    @MethodSource("CheckDuplicateFailID")
    public void checkDuplicateFailTest(CheckDuplicateID param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());

        mockMvc.perform(post("/api/joinmember/duplicateid").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()));
    }
    //2~3sec
    @DisplayName("회원가입 API 성공 TEST")
    @ParameterizedTest(name="/api/joinmember/addmember")
    @MethodSource("AddMemberSuccess")
    public void addMemberSuccessTest(JoinMemberParam param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());
        map.add("password",param.getPassword());


        mockMvc.perform(post("/api/joinmember/addmember").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)));
    }
    //12ms
    @DisplayName("회원가입 API 실패 Fail Parameter")
    @ParameterizedTest(name="/api/joinmember/addmember")
    @MethodSource("AddMemberFail")
    public void addMemberFailTest(JoinMemberParam param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());
        map.add("password",param.getPassword());


        mockMvc.perform(post("/api/joinmember/addmember").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()));
    }

    //12ms
    @DisplayName("EmailAuthSuccess Test")
    @ParameterizedTest(name="/api/joinmember/email-auth")
    @ValueSource(strings = {"b1e05373-d279-4516-adf1-aaaa5f49f0a7","4cc09bbb-fe1b-4060-a963-7cea330717eb","451c6981-95fa-4bec-a526-7c9dc213b498","afeb9e76-0840-4674-92a0-dcd7ab2d4b35"})
    public void EmailAuthSuccessTest(String param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("uuid",param);

        mockMvc.perform(get("/api/joinmember/email-auth").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)))
                .andExpect(jsonPath("$.message",is("OK")));
    }
    //8ms
    @DisplayName("EmailAuthSuccess Test")
    @ParameterizedTest(name="/api/joinmember/email-auth")
    @ValueSource(strings = {"b1e05373-d279-4516-adf1-aaaa5f49f0a7","4cc09bbb-fe1b-4060-a963-7cea330717eb","451c6981-95fa-4bec-a526-7c9dc213b498","afeb9e76-0840-4674-92a0-dcd7ab2d4b35"})
    public void EmailAuthFailTest(String param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("uuid",param);

        mockMvc.perform(get("/api/joinmember/email-auth").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(false)))
                .andExpect(jsonPath("$.message",is("OK")));
    }

    @DisplayName("비밀번호 초기화 이메일 전송 Success")
    @ParameterizedTest(name="/api/joinmember/resetpassword")
    @MethodSource("resetPasswordSuccess")
    public void resetPasswordSuccess(ResetPasswordParam param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());

        mockMvc.perform(get("/api/joinmember/resetpassword").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)))
                .andExpect(jsonPath("$.message",is("OK")));
    }


    @DisplayName("비밀번호 초기화 이메일 전송 Fail User Not Found")
    @ParameterizedTest(name="/api/joinmember/resetpassword")
    @MethodSource("resetPasswordFailNotFoundUser")
    public void resetPasswordFailUserNotFound(ResetPasswordParam param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());

        mockMvc.perform(get("/api/joinmember/resetpassword").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(false)))
                .andExpect(jsonPath("$.message",is("OK")));
    }


    @DisplayName("비밀번호 초기화 이메일 전송 Fail PARAMTER NULL")
    @ParameterizedTest(name="/api/joinmember/resetpassword")
    @MethodSource("resetPasswordFailParameterNull")
    public void resetPasswordFailNullParam(ResetPasswordParam param) throws Exception{
        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(get("/api/joinmember/resetpassword").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message",is("BAD_REQUEST")));
    }
    //98ms
    @DisplayName("비밀번호 변경 성공 TEST")
    @ParameterizedTest(name="/api/joinmember/changepassword")
    @MethodSource("changePasswordSuccess")
    public void changePasswordSuccess(ChangePasswordParam param) throws Exception{
        mockMvc.perform(post("/api/joinmember/changepassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(true)))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));
    }

    //runtime 15ms
    @DisplayName("비밀번호 변경 실패 TEST 유효하지 않은 인증키")
    @ParameterizedTest(name="/api/joinmember/changepassword")
    @MethodSource("changePasswordFailNotValidateUUID")
    public void changePasswordFailNotValidateUUID(ChangePasswordParam param) throws Exception{
        mockMvc.perform(post("/api/joinmember/changepassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",is(false)))
                .andExpect(jsonPath("$.message").value(StatusEnum.OK.name()));
    }


    //runtime 9ms
    @DisplayName("비밀번호 변경 실패 TEST Null Param")
    @ParameterizedTest(name="/api/joinmember/changepassword")
    @MethodSource("changePasswordNullParam")
    public void changePasswordNullParam(ChangePasswordParam param) throws Exception{
        mockMvc.perform(post("/api/joinmember/changepassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.BAD_REQUEST.name()));
    }



    //runtime 22ms
    @DisplayName("비밀번호 변경 실패 변경 기한 초과 ")
    @ParameterizedTest(name="/api/joinmember/changepassword")
    @MethodSource("changePasswordExpiredDate")
    public void changePasswordExpiredDate(ChangePasswordParam param) throws Exception{
        mockMvc.perform(post("/api/joinmember/changepassword").content(toJson(param)).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message").value(StatusEnum.TIME_OUT.name()));
    }

}
