package com.acumen.article.repository.search;

import com.acumen.article.domain.ShippingAddress;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data Elasticsearch repository for the ShippingAddress entity.
 */
public interface ShippingAddressSearchRepository extends ElasticsearchRepository<ShippingAddress, Long> {
}
