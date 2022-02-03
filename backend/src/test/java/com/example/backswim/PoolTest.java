package com.example.backswim;
import com.example.backswim.common.api.enums.StatusEnum;
import com.example.backswim.pool.params.GetPoolMapParam;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

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
public class PoolTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp(WebApplicationContext ctx){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(ctx)
                .addFilters(new CharacterEncodingFilter("UTF-8",true))
                .alwaysDo(print())
                .build();
    }

    static Stream<Arguments> locateSuccess(){
        return Stream.of(
                Arguments.arguments(new GetPoolMapParam(37.544419,126.333,4)),
                Arguments.arguments(new GetPoolMapParam(37.544419,126.353,5)),
                Arguments.arguments(new GetPoolMapParam(37.644419,126.313,4)),
                Arguments.arguments(new GetPoolMapParam(38.544419,126.337,4))



        );
    }

    static Stream<Arguments> locateFail(){
        return Stream.of(
                Arguments.arguments(new GetPoolMapParam(37.544419,126.333,-5)),
                Arguments.arguments(new GetPoolMapParam(37.544419,126.353,0)),
                Arguments.arguments(new GetPoolMapParam(37.644419,126.313,15)),
                Arguments.arguments(new GetPoolMapParam(38.544419,126.337,20))



        );
    }



    @DisplayName("수영장 지도 검색 API ")
    @ParameterizedTest(name="/api/pool/getpoolmapforlocate")
    @MethodSource("locateSuccess")
    public void GetPoolMapForLocateSuccessTest(GetPoolMapParam param) throws Exception{
        String url = "/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("latitude",Double.toString(param.getLatitude()));
        map.add("longitude",Double.toString(param.getLongitude()));
        map.add("mapLevel",Integer.toString(param.getMapLevel()));

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(200)))
                .andExpect(jsonPath("$.data",notNullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.OK.name())));
    }

    @DisplayName("수영장 지도 검색 API 실패")
    @ParameterizedTest(name="/api/pool/getpoolmapforlocate")
    @MethodSource("locateFail")
    public void GetPoolMapForLocateFailTest(GetPoolMapParam param) throws Exception{
        String url = "/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("latitude",Double.toString(param.getLatitude()));
        map.add("longitude",Double.toString(param.getLongitude()));
        map.add("mapLevel",Integer.toString(param.getMapLevel()));

        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.BAD_REQUEST.name())));
    }

    @DisplayName("수영장 지도 검색 API Null")
    @ParameterizedTest(name="/api/pool/getpoolmapforlocate")
    @NullSource
    public void GetPoolMapForLocateFailParamErrorTest(GetPoolMapParam param) throws Exception{
        String url = "/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.statusCode",is(400)))
                .andExpect(jsonPath("$.data",nullValue()))
                .andExpect(jsonPath("$.message",is(StatusEnum.BAD_REQUEST.name())));
    }

}
