package com.acumen.article.repository.search;

import com.acumen.article.domain.BillingAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the BillingAddress entity.
 */
public interface BillingAddressSearchRepository extends ElasticsearchRepository<BillingAddress, Long> {
}
