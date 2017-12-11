package com.solr.controller;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.solr.client.solrj.SolrClient;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrResponse;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.impl.LBHttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.cloud.SolrZkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xuzz on 2017/12/7.
 * 访问solr
 */
@RestController
@RequestMapping("/solr/data")
@Component
@EnableScheduling
public class SolrApiController {

    private static final String URL = "http://192.168.0.120:8983/solr/mycore001";
    private static volatile BufferedReader bufferedReader;

    /**
     * solr请求
     * @return
     * @throws IOException
     * @throws SolrServerException
     */
    @PostMapping("/get")
    public JSONObject getSolrData() throws IOException, SolrServerException {
        JSONObject jsonObject = new JSONObject();
        //创建solr实例
        Map<String,Object> params = new HashMap<String,Object>();
        params.put("q","*:*");
        SolrClient solrClient = new LBHttpSolrClient(URL);
        SolrQuery solrQuery = new SolrQuery("*:*");
        solrQuery.setStart(0);
        solrQuery.setRows(20);
        solrQuery.setSort("resultTime", SolrQuery.ORDER.desc);
        solrQuery.setFilterQueries("tableType:10410061000000001","idNo:320302600204281");
        QueryResponse queryResponse = solrClient.query(solrQuery);
        System.err.println("----->");
        System.out.println("---->全体数据："+JSONObject.toJSONString(queryResponse.getResponse().get("numFound"),true));
        SolrDocumentList solrDocumentList = queryResponse.getResults();
        //获取分页分页数据
        System.err.println("----->"+solrDocumentList.getNumFound());
        //获取数据
        System.err.println("---->数据：》》》》》"+JSONObject.toJSONString(solrDocumentList,true));
        return null;
    }

    /**
     * solr定时增量
     * @throws IOException
     */
    @Scheduled(cron = "0/30 * * * * ?")
    public void deltaImport() throws IOException {
        System.out.println("--------------");
        HttpGet httpGet = new HttpGet("http://192.168.0.120:8983/solr/mycore001/dataimport?command=delta-import&clean=false&commit=true");
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpResponse httpResponse = closeableHttpClient.execute(httpGet);
        if (httpResponse.getStatusLine().getStatusCode()==200){
            HttpEntity httpEntity = httpResponse.getEntity();
            StringBuffer result = new StringBuffer();
            if (httpEntity.getContent()!=null) {
                bufferedReader = new BufferedReader(new InputStreamReader(httpEntity.getContent()));
                String res;
                while ((res = bufferedReader.readLine()) != null) {
                    result.append(res);
                }
                bufferedReader.close();
            }
            //转JSON
            JSONObject res = JSONObject.parseObject(result.toString());
            System.out.println("结果信息："+res.getJSONObject("statusMessages").getString(""));
        }

    }

}
