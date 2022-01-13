package com.example.backswim;
import com.example.backswim.pool.params.GetPoolMapParam;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
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
public class PoolTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void GetPoolMapForLocateSuccessTest() throws Exception{
        String url = "http:// localhost:8080/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("latitude","37.544419");
        map.add("longitude","126.333");
        map.add("mapLevel","4");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(print());
    }

    @Test
    public void GetPoolMapForLocateFailTest() throws Exception{
        String url = "http:// localhost:8080/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("latitude","37.544419");
        map.add("longitude","126.333");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }

    @Test
    public void GetPoolMapForLocateFailParamErrorTest() throws Exception{
        String url = "http:// localhost:8080/api/pool/getpoolmapforlocate";

        MultiValueMap<String ,String> map = new LinkedMultiValueMap<>();

        map.add("latitude","37.544419");
        map.add("longitude","126.333");
        map.add("mapLevel","4a");

        mockMvc.perform(MockMvcRequestBuilders.get(url).params(map).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest()).andExpect(result ->{
                            MockHttpServletResponse response = result.getResponse();
                            response.getContentAsString().contains("WRONG TYPE");
                        }
                        )
                .andDo(print());
    }

}
