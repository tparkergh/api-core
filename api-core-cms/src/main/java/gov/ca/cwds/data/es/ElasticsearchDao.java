package gov.ca.cwds.data.es;

import static com.google.common.base.Preconditions.checkArgument;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.action.ActionRequest;
import org.elasticsearch.action.admin.indices.alias.IndicesAliasesRequestBuilder;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequest;
import org.elasticsearch.action.admin.indices.create.CreateIndexRequestBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.Requests;
import org.elasticsearch.cluster.metadata.MetaData;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.indices.InvalidIndexNameException;
import org.elasticsearch.search.SearchHit;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Strings;
import com.google.inject.Inject;

import gov.ca.cwds.common.ApiFileAssistant;
import gov.ca.cwds.rest.ElasticsearchConfiguration;

/**
 * A DAO for Elasticsearch.
 *
 * <p>
 * OPTION: In order to avoid minimize connections to Elasticsearch, this DAO class should either be
 * final, so that other classes cannot instantiate a client or else the ES client should be injected
 * by the framework.
 * </p>
 *
 * <p>
 * OPTION: allow child DAO classes to connect to a configured index of choice and read specified
 * document type(s).
 * </p>
 *
 * @author CWDS API Team
 */
public class ElasticsearchDao implements Closeable {

  private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ElasticsearchDao.class);

  /**
   * Standard "people" index name.
   */
  protected static final String DEFAULT_PERSON_IDX_NM = "people";
  /**
   * Standard "person" document name.
   */
  protected static final String DEFAULT_PERSON_DOC_TYPE = "person";
  private static final int DEFAULT_MAX_RESULTS = 100;
  private static final String DEFAULT_PERSON_MAPPING =
      "/elasticsearch/mapping/map_person_5x_snake.json";
  private static final int TIMEOUT_MILLIS = 1500;
  /**
   * Client is thread safe.
   */
  private Client client;

  private ElasticsearchConfiguration config;

  /**
   * Constructor.
   *
   * @param client The ElasticSearch client
   * @param config ES configuration, includes default index alias and document type
   */
  @Inject
  public ElasticsearchDao(Client client, ElasticsearchConfiguration config) {
    this.client = client;
    this.config = config;
  }

  /**
   * Get the default alias, either from the configuration or the default constant.
   *
   * @return default alias
   */
  public String getDefaultAlias() {
    return this.config != null && StringUtils.isNotBlank(config.getElasticsearchAlias())
        ? config.getElasticsearchAlias()
        : DEFAULT_PERSON_IDX_NM;
  }

  /**
   * Get the default document type, either from the configuration or the default constant.
   *
   * @return default document type
   */
  public String getDefaultDocType() {
    return this.config != null && StringUtils.isNotBlank(config.getElasticsearchDocType())
        ? config.getElasticsearchDocType()
        : DEFAULT_PERSON_DOC_TYPE;
  }

  /**
   * Check whether Elasticsearch cluster already contains the given index or alias.
   *
   * @param indexOrAlias index name or alias
   * @return whether the index or alias exists
   */
  public boolean doesIndexExist(final String indexOrAlias) {
    final MetaData clusterMeta = getMetaData();
    final boolean answer = clusterMeta.hasIndex(indexOrAlias) || clusterMeta.hasAlias(indexOrAlias);

    if (answer) {
      LOGGER.warn("ES INDEX {} DOES NOT EXIST!", indexOrAlias);
    }
    return answer;
  }

  private MetaData getMetaData() {
    return getClient().admin().cluster().state(Requests.clusterStateRequest()).actionGet()
        .getState().getMetaData();
  }

  /**
   * Create ES index based on supplied parameters.
   *
   * @param index Index name
   * @param type Index document type
   * @param settingsJsonFile Setting file
   * @param mappingJsonFile Mapping file
   */
  private void createIndex(final String index, final String type, final String settingsJsonFile,
      final String mappingJsonFile) throws IOException {
    LOGGER.warn("CREATING ES INDEX [{}] for type [{}] with settings [{}] and mappings [{}]...",
        index, type, settingsJsonFile, mappingJsonFile);

    final String settingsSource = readFile(settingsJsonFile);
    final String mappingSource = readFile(mappingJsonFile);
    final CreateIndexRequestBuilder builder = getClient().admin().indices().prepareCreate(index);

    builder.setSettings(settingsSource, XContentType.JSON);
    builder.addMapping(type, mappingSource, XContentType.JSON);
    getClient().admin().indices().create(builder.request()).actionGet();
  }

  /**
   * Create ES index based on supplied parameters if it does not already exists.
   *
   * <p>
   * This method intentionally synchronizes against race conditions by multiple, simultaneous
   * attempts to create the same index.
   * </p>
   *
   * @param index Index name
   * @param type Index document type
   * @param settingsJsonFile Setting file
   * @param mappingJsonFile Mapping file
   * @throws IOException on disconnect
   */
  public synchronized void createIndexIfNeeded(final String index, final String type,
      final String settingsJsonFile, final String mappingJsonFile) throws IOException {
    if (!doesIndexExist(index)) {
      createIndex(index, type, settingsJsonFile, mappingJsonFile);

      // Let Elasticsearch catch its breath.
      try {
        Thread.sleep(TIMEOUT_MILLIS); // NOSONAR
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.warn("Interrupted!");
      }
    } else {
      LOGGER.warn("Delete index INDEX {} already exists!", index);
    }
  }

  public synchronized void deleteIndex(final String index) {
    if (doesIndexExist(index) && getClient().admin().indices().prepareDelete(index)
        .get(TimeValue.timeValueMillis(TIMEOUT_MILLIS)).isAcknowledged()) {
      LOGGER.warn("\n\n\t>>>>>> DELETE INDEX {}! <<<<<<\n\n", index);
    }
  }

  /**
   * Creates or swaps index alias
   *
   * @param alias Alias name
   * @param index Index name
   * @return true if successful
   */
  public synchronized boolean createOrSwapAlias(final String alias, final String index) {
    final MetaData clusterMeta = getMetaData();
    String oldIndex = StringUtils.EMPTY;

    if (clusterMeta.hasIndex(alias)) {
      LOGGER.warn("CAN'T CREATE ALIAS {}! Index with the same name already exist. ", alias);
      return false;
    } else if (!clusterMeta.hasIndex(index)) {
      LOGGER.warn("CAN'T CREATE ALIAS {}! Index with the name {} doesn't  exist. ", alias, index);
      return false;
    } else if (clusterMeta.hasAlias(alias)) {
      // Only one index assumed to be associated with alias.
      oldIndex =
          clusterMeta.getAliasAndIndexLookup().get(alias).getIndices().get(0).getIndex().getName();
      LOGGER.info("Swapping Alias {} from Index {} to Index {}.", alias, oldIndex, index);
    } else {
      LOGGER.info("Creating Alias {} for Index {}.", alias, index);
    }

    return createOrSwapAlias(alias, index, oldIndex);
  }

  /**
   *
   * @param alias Alias name
   * @param index New Index name
   * @param oldIndex Current Index Name
   * @return true if successful
   */
  private boolean createOrSwapAlias(final String alias, final String index, final String oldIndex) {
    IndicesAliasesRequestBuilder preparedAliases = getClient().admin().indices().prepareAliases();
    TimeValue timeout = TimeValue.timeValueMillis(TIMEOUT_MILLIS);
    if (StringUtils.isBlank(oldIndex)) {
      return preparedAliases.addAlias(index, alias).get(timeout).isAcknowledged();
    } else {
      return preparedAliases.removeAlias(oldIndex, alias).addAlias(index, alias).get(timeout)
          .isAcknowledged();
    }
  }

  /**
   * Create an index and apply a mapping before blasting documents into it.
   *
   * @param index index name or alias
   * @param numShards number of shards
   * @param numReplicas number of replicas
   * @deprecated call {@link #createIndexIfNeeded(String)} instead
   */
  @Deprecated
  private void createIndex(final String index, int numShards, int numReplicas) throws IOException {
    LOGGER.warn("CREATE ES INDEX {} with {} shards and {} replicas", index, numShards, numReplicas);

    try {
      final Settings indexSettings = Settings.builder().put("number_of_shards", numShards)
          .put("number_of_replicas", numReplicas).build();

      CreateIndexRequest indexRequest = new CreateIndexRequest(index, indexSettings);
      getClient().admin().indices().create(indexRequest).actionGet();

      ByteArrayOutputStream out = new ByteArrayOutputStream();
      IOUtils.copy(this.getClass().getResourceAsStream(DEFAULT_PERSON_MAPPING), out);
      out.flush();
      final String mapping = out.toString();

      getClient().admin().indices().preparePutMapping(index).setType(getDefaultDocType())
          .setSource(mapping).get();
    } catch (InvalidIndexNameException e) { // NOSONAR
      LOGGER.warn("INDEX OR ALIAS ALREADY EXISTS! index/alias name={}, msg: {}", index,
          e.getDetailedMessage());
      // It's ok. Do not rethrow. This means that the "index" name is really an alias to
      // an existing index.
    }
  }

  /**
   * Create an index, if needed, before blasting documents into it.
   *
   * <p>
   * Defaults to 5 shards and 1 replica.
   * </p>
   *
   * <p>
   * This method intentionally synchronizes against race conditions by multiple, simultaneous
   * attempts to create the same index.
   * </p>
   *
   * @param index index name or alias
   * @param optShards optional number of shards. Defaults to 5.
   * @param optReplicas optional number of replicas. Defaults to 1.
   * @throws IOException on disconnect, hang, etc.
   * @deprecated call {@link #createIndexIfNeeded(String)} instead
   */
  @Deprecated
  private synchronized void createIndexIfNeeded(final String index,
      final Optional<Integer> optShards, final Optional<Integer> optReplicas) throws IOException {

    final int shards = optShards.orElse(5);
    final int replicas = optReplicas.orElse(1);

    if (!doesIndexExist(index)) {
      LOGGER.warn("ES INDEX {} DOES NOT EXIST!", index);
      LOGGER.warn("Create index {} with {} shards and {} replicas", index, shards, replicas);
      createIndex(index, shards, replicas);

      // Let Elasticsearch catch its breath.
      try {
        Thread.sleep(2000); // NOSONAR
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        LOGGER.warn("Interrupted!");
      }
      // Thread.currentThread().wait(2000L); // oops, thread monitor error. thanks SonarCube.
    } else {
      LOGGER.warn("INDEX {} already exists!", index);
    }
  }

  /**
   * Create an index, if missing.
   *
   * @param index index name or alias
   * @throws IOException on disconnect, hang, etc.
   * @see #createIndexIfNeeded(String, Optional, Optional)
   * @deprecated call {@link #createIndexIfNeeded(String)} instead
   */
  @Deprecated
  public synchronized void createIndexIfNeeded(final String index) throws IOException {
    createIndexIfNeeded(index, Optional.<Integer>empty(), Optional.<Integer>empty());
  }

  /**
   * Prepare an index request for bulk operations.
   *
   * <p>
   * Upsert: update or insert.
   * </p>
   *
   * @param mapper Jackson ObjectMapper
   * @param id ES document id
   * @param obj document object
   * @param alias index alias
   * @param docType document type
   * @param upsert update if document exists, else insert new
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public ActionRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj,
      final String alias, final String docType, boolean upsert) throws JsonProcessingException {
    final String json = mapper.writeValueAsString(obj);
    final IndexRequest idxReq =
        new IndexRequest(alias, docType, id).source(json, XContentType.JSON);
    return upsert
        ? new UpdateRequest(alias, docType, id).doc(json, XContentType.JSON).upsert(idxReq)
        : idxReq;
  }

  /**
   * Prepare an index request for bulk operations, using the default index name and person document
   * type.
   *
   * @param mapper Jackson ObjectMapper configured for bulk operations
   * @param id ES document id
   * @param obj document object
   * @param upsert update if document exists, else insert new
   * @return prepared IndexRequest
   * @throws JsonProcessingException if unable to serialize JSON
   */
  public ActionRequest bulkAdd(final ObjectMapper mapper, final String id, final Object obj,
      boolean upsert) throws JsonProcessingException {
    return bulkAdd(mapper, id, obj, getDefaultAlias(), getDefaultDocType(), upsert);
  }

  /**
   * Prepare an update request for bulk operations as an "upsert".
   *
   * <p>
   * If the document exists, then only update it with the update JSON. Otherwise, create a new
   * document with the insert JSON.
   * </p>
   *
   * @param id ES document id
   * @param alias index alias
   * @param docType document type
   * @param insertJson JSON to create a new document, if document does not exist
   * @param updateJson JSON to update existing document
   * @return prepared IndexRequest
   */
  public ActionRequest bulkUpsert(final String id, final String alias, final String docType,
      final String insertJson, final String updateJson) {
    return new UpdateRequest(alias, docType, id).doc(updateJson, XContentType.JSON)
        .upsert(new IndexRequest(alias, docType, id).source(insertJson, XContentType.JSON));
  }

  /**
   * Convenience overload for {@link #bulkUpsert(String, String, String, String, String)}.
   *
   * @param id ES document id
   * @param insertJson JSON to create a new document, if document does not exist
   * @param updateJson JSON to update existing document
   * @return prepared IndexRequest
   */
  public ActionRequest bulkUpsert(final String id, final String insertJson,
      final String updateJson) {
    return bulkUpsert(id, getDefaultAlias(), getDefaultDocType(), insertJson, updateJson);
  }

  /**
   * The Intake Auto-complete for Person takes a single search term, used to query Elasticsearch
   * Person documents on specific fields.
   *
   * <p>
   * For example, search strings consisting of only digits could be phone numbers, social security
   * numbers, or street address numbers. Search strings consisting of only letters could be last
   * name, first name, city, state, language, and so forth.
   * </p>
   *
   * <p>
   * This method calls Elasticsearch's <a href=
   * "https://www.elastic.co/guide/en/elasticsearch/guide/current/_best_fields.html#dis-max-query" >
   * "dis max"</a> query feature.
   * </p>
   *
   * @param searchTerm ES search String
   * @param alias index alias
   * @param docType document type
   * @return array of ElasticSearchPerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   * @deprecated obsolete API, scheduled for removal
   */
  @Deprecated
  public ElasticSearchPerson[] searchPerson(final String searchTerm, final String alias,
      final String docType) throws ApiElasticSearchException {
    checkArgument(!Strings.isNullOrEmpty(searchTerm), "searchTerm cannot be Null or empty");

    final BoolQueryBuilder queryBuilder = buildBoolQueryFromSearchTerms(searchTerm);
    if (!queryBuilder.hasClauses()) {
      return new ElasticSearchPerson[0];
    }

    final SearchRequestBuilder builder = client.prepareSearch(alias).setTypes(docType)
        .setQuery(queryBuilder).setFrom(0).setSize(DEFAULT_MAX_RESULTS).setExplain(true);

    LOGGER.warn("ES QUERY: {}", builder);
    final SearchHit[] hits = builder.execute().actionGet().getHits().getHits();

    final ElasticSearchPerson[] ret = new ElasticSearchPerson[hits.length];
    int counter = -1;
    for (SearchHit hit : hits) {
      ret[++counter] = ElasticSearchPerson.makeESPerson(hit);
    }

    return ret;
  }

  /**
   * @param searchTerm ES search String
   * @return array of ElasticSearchPerson
   * @throws ApiElasticSearchException unable to connect, disconnect, bad hair day, etc.
   * @deprecated obsolete API, scheduled for removal
   */
  @Deprecated
  public ElasticSearchPerson[] searchPerson(final String searchTerm)
      throws ApiElasticSearchException {
    return searchPerson(searchTerm, getDefaultAlias(), getDefaultDocType());
  }

  /**
   * Search given index by pass-through query.
   *
   * @param index index to search
   * @param query user-provided query
   * @param protocol presumably "http://" or https://"
   * @param port usually 9200
   * @param docType "person" or otherwise
   * @return JSON ES results
   */
  public String searchIndexByQuery(final String index, final String query, final String protocol,
      final int port, final String docType) {
    LOGGER.info(" index: {}", index);
    LOGGER.info(" query: {}", query);
    checkArgument(!Strings.isNullOrEmpty(query), "query cannot be Null or empty");
    checkArgument(!Strings.isNullOrEmpty(index), "index name cannot be Null or empty");

    final StringBuilder buf = new StringBuilder();
    buf.append(protocol.trim()).append(config.getElasticsearchHost().trim()).append(':')
        .append(port).append('/').append(index).append('/').append(docType.trim())
        .append("/_search");
    final String targetURL = buf.toString();
    LOGGER.info("ES SEARCH URL: {}", targetURL);
    return executionResult(targetURL, query);
  }

  /**
   * Convenience overload. Search given index by pass-through query.
   *
   * <p>
   * Parameter Defaults
   * </p>
   *
   * <table summary="Parameter Defaults">
   * <tr>
   * <th align="justify">Param</th>
   * <th align="justify">Default</th>
   * </tr>
   * <tr>
   * <td align="justify">Protocol</td>
   * <td align="justify">https</td>
   * </tr>
   * <tr>
   * <td>port</td>
   * <td>9200</td>
   * </tr>
   * <tr>
   * <td>document type</td>
   * <td>{@link #getDefaultDocType()}</td>
   * </tr>
   * </table>
   *
   * @param index index to search
   * @param query user-provided query
   * @return JSON ES results
   * @see #searchIndexByQuery(String, String, String, int, String)
   */
  public String searchIndexByQuery(final String index, final String query) {
    return searchIndexByQuery(index, query, "http://", 9200, this.getDefaultDocType());
  }

  /**
   * Builds an Elasticsearch compound query by combining multiple <b>should</b> clauses into a
   * Boolean Query.
   *
   * @param searchTerm the user entered values to search for separated by space
   * @return the Elasticsearch compound query
   */
  public BoolQueryBuilder buildBoolQueryFromSearchTerms(String searchTerm) {
    final String s = searchTerm.trim().toLowerCase();
    final String[] searchTerms = s.split("\\s+");
    BoolQueryBuilder queryBuilder = QueryBuilders.boolQuery();
    for (String term : searchTerms) {
      term = term.trim();
      if (StringUtils.isNotBlank(term)) {
        queryBuilder = ElasticSearchQuery.getQueryFromTerm(term).buildQuery(queryBuilder, term);
      }
    }
    return queryBuilder;
  }

  /**
   * Stop the ES client, if started.
   */
  public void stop() {
    if (client != null) {
      this.client.close();
    }
  }

  @Override
  public void close() throws IOException {
    try {
      stop();
    } catch (Exception e) {
      final String msg = "Error closing ElasticSearch DAO: " + e.getMessage();
      LOGGER.error(msg, e);
      throw new IOException(msg, e);
    }
  }

  /**
   * Exposed for testing. Don't abuse this.
   *
   * @return the client
   */
  public Client getClient() {
    return client;
  }

  /**
   * Expose underlying Elasticsearch configuration.
   *
   * @return ES configuration
   */
  public ElasticsearchConfiguration getConfig() {
    return config;
  }

  /**
   * Set Elasticsearch configuration. Does not automatically reconnect to host and port without
   * calling {@link #stop}, but alias and document type changes take effect immediately.
   *
   * @param config ES configuration
   */
  public void setConfig(ElasticsearchConfiguration config) {
    this.config = config;
  }

  /**
   * Consume an external REST web service, specifying URL, request headers and JSON payload.
   *
   * <p>
   * Note that this implementation calls the Elasticsearch HTTP transport, not the Java transport.
   * </p>
   *
   * @param targetURL the target URL
   * @param payload the payload specified by user
   * @return the JSON payload returned by the external web service
   */
  protected String executionResult(String targetURL, String payload) {
    String line;
    final StringBuilder jsonBuf = new StringBuilder();
    HttpURLConnection connection = null;

    try {
      URL url = new URL(targetURL);
      connection = (HttpURLConnection) url.openConnection();
      connection.setDoInput(true);
      connection.setDoOutput(true);
      connection.setRequestMethod("GET");
      connection.setRequestProperty("Content-Type", "application/json");

      // Send payload.
      if (StringUtils.isNotEmpty(payload)) {
        try (OutputStreamWriter writer =
            new OutputStreamWriter(connection.getOutputStream(), "UTF8")) {
          writer.write(payload.trim());
        } finally {
          // auto-close.
        }
      }

      // Fetch response.
      try (BufferedReader reader =
          new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
        while ((line = reader.readLine()) != null) {
          jsonBuf.append(line);
        }
      } finally {
        // auto-close.
      }

    } catch (Exception e) {
      // SUGGESTION: log the context or payload.
      final String msg = "ElasticSearch ERROR: " + e.getMessage();
      LOGGER.error(msg, e);
      throw new ApiElasticSearchException(msg, e);
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
    return jsonBuf.toString();
  }

  protected String readFile(String sourceFile) throws IOException {
    String ret = null;
    try {
      ret = new ApiFileAssistant().readFile(sourceFile);
    } catch (Exception e) {
      LOGGER.warn("NO RESOURCE FOUND FOR '{}'. Try as regular file.", sourceFile, e);
      ret = FileUtils.readFileToString(new File(sourceFile), Charset.defaultCharset());
    }

    return ret;
  }

}
