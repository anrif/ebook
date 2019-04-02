package com.acumen.article.repository.search;

import com.acumen.article.domain.Ordered;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the Ordered entity.
 */
public interface OrderedSearchRepository extends ElasticsearchRepository<Ordered, Long> {
}
