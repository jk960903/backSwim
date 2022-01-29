package com.example.backswim.pool.model;

import com.example.backswim.pool.params.checkInterface;

public class SearchQueryParameter implements checkInterface {

    private String inputQuery;

    private String searchQuery;

    @Override
    public boolean checkStatus() {
        if(inputQuery.equals("") || inputQuery == null) return false;
        return true;
    }

    /**
     * input 값을 통해 검색할 Query값을 만듬
     */
    public void makeQuery(){
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        int first = 0;
        int second = 0;
        String pattern = "^[ㄱ-ㅎ가-힣]*$";

        for(int i = 0 ; i < inputQuery.length(); i++){
            if(inputQuery.charAt(i) =='ㄱ'){

            }
        }
    }
}
