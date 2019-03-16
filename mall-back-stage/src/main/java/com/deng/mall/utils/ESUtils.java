package com.deng.mall.utils;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpHost;
import org.elasticsearch.action.DocWriteResponse;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexResponse;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.DisMaxQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ESUtils {

	private static Logger logger = LoggerFactory.getLogger(ESUtils.class);

	public static RestHighLevelClient getClient(String ip, int port) throws UnknownHostException {
		RestClientBuilder builder = RestClient.builder(new HttpHost(ip, port, "http"));
		builder.setMaxRetryTimeoutMillis(10000);
		RestHighLevelClient client = new RestHighLevelClient(builder);
		logger.info("success to link");
		return client;
	}

	public static void createIndex(RestHighLevelClient client, String indexName, String typeName) throws IOException {
		CreateIndexRequest request = new CreateIndexRequest(indexName);
		request.settings(Settings.builder().put("index.number_of_shards", 1).put("index.number_of_replicas", 1))
				.mapping(typeName, "content", "type=text,analyzer=ik_max_word")
				// ???????
				.timeout(TimeValue.timeValueMinutes(2))
				// ??????????????
				.masterNodeTimeout(TimeValue.timeValueMinutes(1));
		CreateIndexResponse indexResponse = client.indices().create(request);
		boolean isAck = indexResponse.isAcknowledged();
		if (isAck) {
			logger.info("index create success");
		} else {
			logger.error("index create faild ");
		}
	}

	public static void addData(RestHighLevelClient client, String beanJsonStr, String indexName, String typeName,
			String indexId) throws IOException {
		IndexRequest request = new IndexRequest(indexName, typeName, indexId)
				.source(beanJsonStr, XContentType.JSON);

		IndexResponse indexResponse = client.index(request);
		String index = indexResponse.getIndex();
		String type = indexResponse.getType();
		String id = indexResponse.getId();
		long version = indexResponse.getVersion();
		if (indexResponse.getResult() == DocWriteResponse.Result.CREATED) {
			logger.info("createIndex:version={},type={},id={},index={}", version, type, id, index);
		} else if (indexResponse.getResult() == DocWriteResponse.Result.UPDATED) {
			logger.info("updateIndex:version={},type={},id={},index={}", version, type, id, index);
		}
	}

	public static void batchAddData(RestHighLevelClient client, Map<Integer, String> beanJsonMap, String indexName,
			String typeName, String indexId) {
		List<Integer> exceptionId = new ArrayList<Integer>();
		String jsonStr = "";
		for (Integer jKey : beanJsonMap.keySet()) {
			jsonStr = beanJsonMap.get(jKey);
			try {
				addData(client, jsonStr, indexName, typeName, indexId);
			} catch (IOException e) {
				exceptionId.add(jKey);
			}
		}
		if (!exceptionId.isEmpty()) {
			logger.error("occur error ids:{}", exceptionId.toString());
		}
	}

	public static List<Map<String, Object>> queryByCondition(RestHighLevelClient client, Map<String, String> condition,
			String indexName, String... typeName) throws IOException {
		List<Map<String, Object>> results = new ArrayList<Map<String, Object>>();

		SearchRequest searchRequest = new SearchRequest(indexName).types(typeName);
		SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
		DisMaxQueryBuilder queryBuilder = QueryBuilders.disMaxQuery();

		for (String fieldName : condition.keySet()) {
			String text=condition.get(fieldName);
			queryBuilder.add(QueryBuilders.matchQuery(fieldName, text).analyzer("ik_max_word")
						);
			
		}

		searchSourceBuilder.query(queryBuilder);

		searchRequest.source(searchSourceBuilder);

		SearchResponse searchResponse = client.search(searchRequest);
		SearchHit[] searchHits = searchResponse.getHits().getHits();

		for (SearchHit searchHit : searchHits) {
			results.add(searchHit.getSourceAsMap());
		}

		return results;
	}

	public static void deleteIndex(RestHighLevelClient client,String indexName) throws IOException {
		DeleteIndexRequest request=new DeleteIndexRequest(indexName);
		client.indices().delete(request);
	}
	
}
