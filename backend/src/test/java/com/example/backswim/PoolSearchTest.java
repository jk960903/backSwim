package com.example.backswim;

import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.pool.params.SearchAddressParam;


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

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;


import java.util.stream.Stream;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PoolSearchTest {


    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    static Stream<Arguments> AddressNonParam(){
        return Stream.of(
                Arguments.arguments(new SearchAddressParam("","","","")),
                Arguments.arguments(new SearchAddressParam("","","","")),
                Arguments.arguments(new SearchAddressParam("","","","")),
                Arguments.arguments(new SearchAddressParam("","","",""))


        );
    }

    static Stream<Arguments> AddressFirstParam(){
        return Stream.of(
                Arguments.arguments(new SearchAddressParam("서울","","","")),
                Arguments.arguments(new SearchAddressParam("인천","","","")),
                Arguments.arguments(new SearchAddressParam("경기","","","")),
                Arguments.arguments(new SearchAddressParam("부산","","",""))

        );
    }

    static Stream<Arguments> AddressSecondParam(){
        return Stream.of(
                Arguments.arguments(new SearchAddressParam("서울","마포구","","")),
                Arguments.arguments(new SearchAddressParam("인천","계양구","","")),
                Arguments.arguments(new SearchAddressParam("경기","부천시","","")),
                Arguments.arguments(new SearchAddressParam("부산","해운대구","",""))

        );
    }

    static Stream<Arguments> AddressNullParam(){
        return Stream.of(
                Arguments.arguments(new SearchAddressParam(null,null,null,null)),
                Arguments.arguments(new SearchAddressParam(null,null,null,null)),
                Arguments.arguments(new SearchAddressParam(null,null,null,null)),
                Arguments.arguments(new SearchAddressParam(null,null,null,null))
        );
    }


    /**
     * 파라미터가 아무것도 들어오지 않을때 (null 처리가 아닌 공백처리 즉 사용자가 아무런 지역을 입력하지 않음)
     * 전체검색
     */
    @DisplayName("파라미터 들어오지 않아 Address Non Param (전체 검색")
    @ParameterizedTest(name="/api/search/searchaddress")
    @MethodSource({"AddressNonParam"})
    public void PoolSearchAddressNonParam(SearchAddressParam param) throws Exception{

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress", param.getSecondAddress());
        map.add("thirdAddress",param.getThirdAddress());
        map.add("fourthAddress",param.getFourthAddress());

        mockMvc.perform(get("/api/search/searchaddress").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()));
    }

    /**
     * 사용자가 시 , 도 ,특별시 1단계 주소만 입력 테스트
     * @throws Exception
     */
    @DisplayName("FirstAddress 만 존재")
    @ParameterizedTest(name="/api/search/searchaddress")
    @MethodSource("AddressFirstParam")
    public void PoolSearchAddressInFirstAddress(SearchAddressParam param) throws Exception{

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress", param.getSecondAddress());
        map.add("thirdAddress",param.getThirdAddress());
        map.add("fourthAddress",param.getFourthAddress());

        mockMvc.perform(get("/api/search/searchaddress").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()));
    }

    /**
     * 사용자가 도 , 시 , 군  2단계 지역 정보 까지 입력 테스트
     * @throws Exception
     */
    @DisplayName("FirstAddress 만 존재")
    @ParameterizedTest(name="/api/search/searchaddress")
    @MethodSource("AddressSecondParam")
    public void PoolSearchAddressInSecondAddress(SearchAddressParam param) throws Exception{

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress",param.getSecondAddress());
        map.add("thirdAddress","");
        map.add("fourthAddress","");


        mockMvc.perform(get("/api/search/searchaddress").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()));
    }
    /*
    /**
     * 구 , 동 ,읍 , 면 단위의 3단계 지역 정보 까지 입력  테스트
     * @throws Exception

    @Test
    public void PoolSearchAddressInThirdAddress() throws Exception{
        SearchAddressParam param = new SearchAddressParam("서울","강남구","논현동","");


        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress",param.getSecondAddress());
        map.add("thirdAddress",param.getThirdAddress());
        map.add("fourthAddress","");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("\"statusCode\":200");
                            response.getContentAsString().contains("\"pool\": [");
                        }
                )
                .andDo(print());

    }

    /**
     * 동 , 리 등의 최 하위 지역 정보 까지 입력 테스트
     * @throws Exception

    @Test
    public void PoolSearchAddressInFourthAddress() throws Exception{
        SearchAddressParam param = new SearchAddressParam("경기","수원시","장안구","이목동");


        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress",param.getSecondAddress());
        map.add("thirdAddress",param.getThirdAddress());
        map.add("fourthAddress",param.getFourthAddress());

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("\"statusCode\":200");
                            response.getContentAsString().contains("\"pool\": [");
                        }
                )
                .andDo(print());

    }
    */
    // 논의 중인 현재 third fourth는 사용 미정


    /**
     * 파라미터가 잘못되어 API 반환 오류
     * Response 400 Parameter Error
     * @throws Exception
     */
    @DisplayName("FirstAddress 만 존재")
    @ParameterizedTest(name="/api/search/searchaddress")
    @MethodSource("AddressNullParam")
    public void PoolSearchAddressNullParameter(SearchAddressParam param) throws Exception{
        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(get("/api/search/searchaddress").params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()));
    }

    /**
     * 수영장 검색 API 초성이 문장 or 단어 검색
     * runTime 7ms
     * @throws Exception
     */
    @DisplayName("수영장 단어 검색 성공")
    @ParameterizedTest(name="/api/search/searchquery")
    @ValueSource(strings = {"강남","인천","수련관","수영장"})
    public void PoolSearchQuerySuccessNoneChoSung(String param) throws Exception{
        String url = "/api/search/searchquery";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("inputQuery",param);


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.OK.name())));
    }

    /**
     * 수영장 검색 API 초성 검색
     * runTime 57ms
     * @throws Exception
     */
    @DisplayName("수영장 초성 검색 성공")
    @ParameterizedTest(name="/api/search/searchquery")
    @ValueSource(strings = {"ㄱㄴ","ㄷㅁ","ㄱㅁ","ㅅㅇㅈ"})
    public void PoolSearchQuerySuccessChoSung(String param) throws Exception{
        String url = "/api/search/searchquery";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("inputQuery",param);


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.OK.name())));
    }

    /**
     * 수영장 검색 API 글자와 초성이 섞였을 때의 검색
     * runTime 5ms
     * @throws Exception
     */
    @DisplayName("수영장 초성+단어 검색 성공")
    @ParameterizedTest(name="/api/search/searchquery")
    @ValueSource(strings = {"강ㄴ","수ㅇ장","수ㄹ관","ㅅ터"})
    public void PoolSearchQuerySuccessMix(String param) throws Exception{
        String url = "/api/search/searchquery";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("inputQuery",param);


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.OK.name())));
    }

    /**
     * 수영장 검색 엔진 query에 아무것도 넣지 않음 즉 공백을 넣음
     * runTime : 370ms
     * @throws Exception
     */
    @DisplayName("수영장 공백 검색 실패 허용하지 않음")
    @ParameterizedTest(name="/api/search/searchquery")
    @ValueSource(strings = {"","","",""})
    public void PoolSearchQueryNone(String param) throws Exception{
        String url = "/api/search/searchquery";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("inputQuery",param);


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.BAD_REQUEST.name())));
    }

    /**
     * inputQuery 자체가 null 일경우 UnitTest 공백검색이아닌 정상적인 페이지에서의 요청이 아님
     * runTime : 5ms
     * @throws Exception
     */
    @DisplayName("수영장 공백 Null Param 여러번 테스트를 위해 공백을 줬지만 실제 param null")
    @ParameterizedTest(name="/api/search/searchquery")
    @ValueSource(strings = {"","","",""})
    public void PoolSearchQueryNull(String param) throws Exception{
        String url = "/api/search/searchquery";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.BAD_REQUEST.name())));
    }

}
