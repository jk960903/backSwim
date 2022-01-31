package com.example.backswim.pool.params;

import com.example.backswim.pool.params.checkInterface;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SearchQueryParameter implements checkInterface {

    private String inputQuery;

    private String searchQuery;

    private Map<String, String> map;
    @Override
    public boolean checkStatus() {
        if(inputQuery == null || inputQuery.equals("")) return false;
        return true;
    }

    /**
     * input 값을 통해 검색할 Query값을 만듬
     */
    public void makeQuery(){
        Map<String,String> map = initMap();

        StringBuilder sb = new StringBuilder();
        //sb.append(".*");
        for(int i = 0 ; i < inputQuery.length(); i++){
            char temp = inputQuery.charAt(i);
            if(map.containsKey(Character.toString(temp))){
                sb.append(map.get(Character.toString(temp)));
            }else{
                sb.append("["+temp+"]");
            }
        }
        //sb.append("*.");
        this.searchQuery = sb.toString();
    }

    private Map<String, String> initMap(){
        map = new HashMap<>();
        map.put("ㄱ","[가-깋]");
        map.put("ㄴ","[나-닣]");
        map.put("ㄷ","[다-딯]");
        map.put("ㄹ","[라-맇]");
        map.put("ㅁ","[마-밓]");
        map.put("ㅂ","[바-빟]");
        map.put("ㅅ","[사-싷]");
        map.put("ㅇ","[아-잏]");
        map.put("ㅈ","[자-짛]");
        map.put("ㅊ","[차-칳]");
        map.put("ㅋ","[카-킿]");
        map.put("ㅌ","[타-팋]");
        map.put("ㅍ","[파-핗]");
        map.put("ㅎ","[하-힣]");
        map.put("ㄲ","[까-낗]");
        map.put("ㄸ","[따-띻]");
        map.put("ㅃ","[빠-삫]");
        map.put("ㅆ","[싸-앃]");
        map.put("ㅉ","[짜-찧]");


        return map;
    }
}
