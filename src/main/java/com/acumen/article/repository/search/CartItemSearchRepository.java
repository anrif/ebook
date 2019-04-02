package com.acumen.article.repository.search;

import com.acumen.article.domain.CartItem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the CartItem entity.
 */
public interface CartItemSearchRepository extends ElasticsearchRepository<CartItem, Long> {
}
