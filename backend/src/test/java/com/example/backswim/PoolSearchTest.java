package com.example.backswim;

import com.example.backswim.pool.params.SearchAddressParam;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
@NoArgsConstructor
@AutoConfigureMockMvc
public class PoolSearchTest {

    @Autowired
    private MockMvc mockMvc;

    private Logger log= LoggerFactory.getLogger(this.getClass());
    /**
     * 파라미터가 아무것도 들어오지 않을때 (null 처리가 아닌 공백처리 즉 사용자가 아무런 지역을 입력하지 않음)
     * 전체검색
     */
    @Test
    public void PoolSearchAddressNonParam() throws Exception{
        SearchAddressParam param = new SearchAddressParam("","","","");


        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress","");
        map.add("secondAddress","");
        map.add("thirdAddress","");
        map.add("fourthAddress","");
        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(status().isOk()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("\"statusCode\":200");
                        }
                )
                .andDo(print());
    }

    /**
     * 사용자가 시 , 도 ,특별시 1단계 주소만 입력 테스트
     * @throws Exception
     */
    @Test
    public void PoolSearchAddressInFirstAddress() throws Exception{
        SearchAddressParam param = new SearchAddressParam("서울","","","");


        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress","");
        map.add("thirdAddress","");
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
     * 사용자가 도 , 시 , 군  2단계 지역 정보 까지 입력 테스트
     * @throws Exception
     */
    @Test
    public void PoolSearchAddressInSecondAddress() throws Exception{
        SearchAddressParam param = new SearchAddressParam("서울","강남구","","");


        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("firstAddress",param.getFirstAddress());
        map.add("secondAddress",param.getSecondAddress());
        map.add("thirdAddress","");
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
     * 구 , 동 ,읍 , 면 단위의 3단계 지역 정보 까지 입력  테스트
     * @throws Exception
     */
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
     */
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

    /**
     * 파라미터가 잘못되어 API 반환 오류
     * Response 400 Data null
     * @throws Exception
     */
    @Test
    public void PoolSearchAddressNullParameter() throws Exception{
        String url = "http://localhost:8080/api/search/searchaddress";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();


        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("\"statusCode\":400");
                            response.getContentAsString().contains("\"data\": null");
                        }
                )
                .andDo(print());
    }


}
