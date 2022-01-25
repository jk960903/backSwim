package com.example.backswim;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
@NoArgsConstructor
@AutoConfigureMockMvc
public class PoolDetailTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * GetPoolDetailAPI No Parameter Unit Test
     * 파라미터가 없어 API 사용 불가
     * @throws Exception
     */
    @Test
    public void GetPoolDetailNoParameterFail() throws Exception{
        String url = "http:// localhost:8080/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.equals(null);
                        }
                )
                .andDo(print());

    }

    /**
     * 수영장 상세 조회 성공 테스트
     * parameter는 테스트 데이터를 넣었으며 정상적인 성공
     * expect) status : 200 OK and responseMessage : PoolDetailEntity
     * @throws Exception
     */
    @Test
    public void GetPoolDetailSuccess() throws Exception{
        String url = "http:// localhost:8080/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id","7865067");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("\"id\": 7865067");
                        }
                )
                .andDo(print());

    }


    /**
     * 파라미터 타입이 잘못되어 Fail Test
     * @throws Exception
     */
    @Test
    public void GetPoolDetailWrongParameterFail() throws Exception{
        String url = "http:// localhost:8080/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id","ㅁㅁㅁㅁ");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.equals("WRONG TYPE");
                        }
                )
                .andDo(print());


    }

    /**
     * 없는 id로 검색하여 Test 실패
     * @throws Exception
     */
    @Test
    public void GetPoolDetailNotFoundParameterFail() throws Exception{
        String url = "http:// localhost:8080/api/pooldetail/getpooldetail";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("id","1234");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.equals(null);
                        }
                )
                .andDo(print());
    }
}
