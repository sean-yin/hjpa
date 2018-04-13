package com.yinx.hjpa.querychannel.query;

import com.yinx.hjpa.entity.BaseQuery;
import com.yinx.hjpa.entity.EntityRepository;
import com.yinx.hjpa.entity.NamedQuery;
import com.yinx.hjpa.querychannel.ChannelQuery;
import com.yinx.hjpa.utils.Page;

import java.util.List;

/**
 * 通道查询的SQL实现
 * 
 * @author seany
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ChannelNamedQuery extends ChannelQuery<ChannelNamedQuery> {

	private NamedQuery query;

	public ChannelNamedQuery(EntityRepository repository, String queryName) {
		super(repository);
		query = new NamedQuery(repository, queryName);
		setQuery(query);
	}

	@Override
	public <T> List<T> list() {
		return query.list();
	}

	@Override
	public <T> Page<T> pagedList() {
		return new Page<T>(query.getFirstResult(), queryResultCount(), query.getMaxResults(),
				(List<T>) query.list());
	}

	@Override
	public <T> T singleResult() {
		return (T) query.singleResult();
	}

	@Override
	protected String getQueryString() {
		return repository.getQueryStringOfNamedQuery(query.getQueryName());
	}

	@Override
	protected BaseQuery createBaseQuery(String queryString) {
		return repository.createJpqlQuery(queryString);
	}

}
