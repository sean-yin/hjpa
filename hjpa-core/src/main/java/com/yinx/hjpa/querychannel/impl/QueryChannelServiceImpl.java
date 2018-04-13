package com.yinx.hjpa.querychannel.impl;

import java.util.List;

import com.yinx.hjpa.entity.EntityRepository;
import com.yinx.hjpa.ioc.InstanceFactory;
import com.yinx.hjpa.querychannel.ChannelQuery;
import com.yinx.hjpa.querychannel.QueryChannelService;
import com.yinx.hjpa.querychannel.query.ChannelJpqlQuery;
import com.yinx.hjpa.querychannel.query.ChannelNamedQuery;
import com.yinx.hjpa.querychannel.query.ChannelSqlQuery;
import com.yinx.hjpa.utils.Page;
import org.springframework.stereotype.Service;


/**
 * Created by seany on 2018/3/16.
 */
@Service("queryChannel")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class QueryChannelServiceImpl implements QueryChannelService {

    private static final long serialVersionUID = -1045471527037313221L;
    // @Autowired
    private EntityRepository repository;

    private EntityRepository getEntityRepository() {
        if (null == repository) {
            repository = InstanceFactory.getInstance(EntityRepository.class);
        }
        return repository;
    }

    @Override
    public ChannelJpqlQuery createJpqlQuery(String jpql) {
        return new ChannelJpqlQuery(getEntityRepository(), jpql);
    }

    @Override
    public ChannelNamedQuery createNamedQuery(String queryName) {
        return new ChannelNamedQuery(getEntityRepository(), queryName);
    }

    @Override
    public ChannelSqlQuery createSqlQuery(String sql) {
        return new ChannelSqlQuery(getEntityRepository(), sql);
    }

    @Override
    public <T> List<T> list(ChannelQuery query) {
        return query.list();
    }

    @Override
    public <T> Page<T> pagedList(ChannelQuery query) {
        return query.pagedList();
    }

    @Override
    public <T> T getSingleResult(ChannelQuery query) {
        return (T) query.singleResult();
    }

    @Override
    public long getResultCount(ChannelQuery query) {
        return query.queryResultCount();
    }
}
