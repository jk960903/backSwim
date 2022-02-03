package com.example.backswim;


import com.example.backswim.common.api.enums.StatusEnum;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;

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

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PoolDetailTest {


    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    /**
     * GetPoolDetailAPI No Parameter Unit Test
     * 파라미터가 없어 API 사용 불가
     * @throws Exception
     */
    @DisplayName("파라미터가 없어 실패")
    @ParameterizedTest(name="/api/pooldetail/getpooldetail")
    @ValueSource(ints={0,0,0,0})
    public void GetPoolDetailNoParameterFail(int num) throws Exception{
        String url = "/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data", nullValue()));

    }

    /**
     * 수영장 상세 조회 성공 테스트
     * parameter는 테스트 데이터를 넣었으며 정상적인 성공
     * expect) status : 200 OK and responseMessage : PoolDetailEntity
     * @throws Exception
     */
    @DisplayName("수영장 상세 조회 성공 ")
    @ParameterizedTest(name="/api/pooldetail/getpooldetail")
    @ValueSource(ints={7865067,8003272,8016583,8022405})
    public void GetPoolDetailSuccess(int param) throws Exception{
        String url = "/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id",Integer.toString(param));

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data", notNullValue()));

    }


    /**
     * 파라미터 타입이 잘못되어 Fail Test
     * @throws Exception
     */
    @DisplayName("수영장 상세 조회 파라미터 타입 오류 ")
    @ParameterizedTest(name="/api/pooldetail/getpooldetail")
    @ValueSource(strings={"7865067a","8003272a","8016583a","8022405a"})
    public void GetPoolDetailWrongParameterFail(String param) throws Exception{
        String url = "/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id",param);

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data", nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.PARAMETER_TYPE_ERROR.name())));


    }

    /**
     * 없는 id로 검색하여 Test 실패
     * @throws Exception
     */
    @DisplayName("수영장 상세 조회 not found 오류 ")
    @ParameterizedTest(name="/api/pooldetail/getpooldetail")
    @ValueSource(strings={"1","2","3","4"})
    public void GetPoolDetailNotFoundParameterFail(String param) throws Exception{
        String url = "/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id",param);

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data", nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.NOT_FOUND.name())));
    }
}
