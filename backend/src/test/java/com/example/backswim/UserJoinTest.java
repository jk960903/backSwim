package com.example.backswim;

import com.example.backswim.member.params.CheckDuplicateID;
import com.example.backswim.member.params.JoinMemberParam;
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

import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class UserJoinTest {

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

    //15ms
    @DisplayName("이메일 중복체크 API 성공 테스트")
    @ParameterizedTest(name="/api/joinmember/duplicateid")
    @MethodSource("CheckDuplicateID")
    public void checkDuplicateSuccessTest(CheckDuplicateID param) throws Exception{

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("userEmail",param.getUserEmail());
        mockMvc.perform(post("/api/joinmember/duplicateid").params(map).contentType(MediaType.APPLICATION_JSON))
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
}
