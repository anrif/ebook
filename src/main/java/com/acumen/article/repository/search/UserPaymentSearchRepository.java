package com.acumen.article.repository.search;

import com.acumen.article.domain.UserPayment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserPayment entity.
 */
public interface UserPaymentSearchRepository extends ElasticsearchRepository<UserPayment, Long> {
}
