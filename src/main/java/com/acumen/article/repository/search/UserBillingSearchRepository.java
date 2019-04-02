package com.acumen.article.repository.search;

import com.acumen.article.domain.UserBilling;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserBilling entity.
 */
public interface UserBillingSearchRepository extends ElasticsearchRepository<UserBilling, Long> {
}
