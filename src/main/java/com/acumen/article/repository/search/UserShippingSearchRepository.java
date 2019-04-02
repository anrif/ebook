package com.acumen.article.repository.search;

import com.acumen.article.domain.UserShipping;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the UserShipping entity.
 */
public interface UserShippingSearchRepository extends ElasticsearchRepository<UserShipping, Long> {
}
