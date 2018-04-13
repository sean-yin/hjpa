package com.yinx.hjpa.querychannel.query;

import com.yinx.hjpa.entity.BaseQuery;
import com.yinx.hjpa.entity.EntityRepository;
import com.yinx.hjpa.entity.JpqlQuery;
import com.yinx.hjpa.querychannel.ChannelQuery;
import com.yinx.hjpa.utils.Page;

import java.util.List;


/**
 * 通道查询的JPQL实现
 *
 * @author seany
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ChannelJpqlQuery extends ChannelQuery<ChannelJpqlQuery> {

    private final JpqlQuery query;

    public ChannelJpqlQuery(EntityRepository repository, String jpql) {
        super(repository);
        query = new JpqlQuery(repository, jpql);
        setQuery(query);
    }

    @Override
    public <T> List<T> list() {
        return query.list();
    }

    @Override
    public <T> Page<T> pagedList() {
        return new Page<T>(query.getFirstResult(), queryResultCount(), query.getMaxResults(), (List<T>) query.list());
    }

    @Override
    public <T> T singleResult() {
        return (T) query.singleResult();
    }

    @Override
    protected String getQueryString() {
        return query.getJpql();
    }

    @Override
    protected BaseQuery createBaseQuery(String queryString) {
        return repository.createJpqlQuery(queryString);
    }
}
