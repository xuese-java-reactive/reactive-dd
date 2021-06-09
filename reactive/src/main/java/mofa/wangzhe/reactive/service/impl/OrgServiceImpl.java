package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.OrgModel;
import mofa.wangzhe.reactive.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * @author LD
 */

@Service
@Transactional(rollbackFor = Exception.class)
public class OrgServiceImpl implements OrgService {

    @Autowired
    private R2dbcEntityTemplate template;

    @Override
    public Mono<OrgModel> save(OrgModel model) {
        model.setUuid(UUID.randomUUID().toString());
        return template.insert(model)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<OrgModel> remove(OrgModel model) {
        return template.select(OrgModel.class)
                .matching(Query.query(Criteria.where("pid").is(model.getUuid())))
                .count()
                .flatMap(f -> {
                    long i = 0;
                    if (f > i) {
                        return Mono.error(new Exception("请先删除下级"));
                    } else {
                        return template.delete(model);
                    }
                });
    }

    @Override
    public Mono<OrgModel> update(OrgModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), OrgModel.class)
                .flatMap(f -> {
                    if (StringUtils.hasText(model.getName())) {
                        f.setName(model.getName());
                    }
                    if (StringUtils.hasText(model.getPid())) {
                        f.setPid(model.getPid());
                    }
                    return template.update(f)
                            .switchIfEmpty(Mono.error(new Exception("参数为空")));
                });
    }

    @Override
    public Flux<OrgModel> findAll(String pid) {
        return template.select(OrgModel.class)
                .matching(Query.empty().sort(Sort.by(Sort.Order.asc("pid"))))
                .all();
    }
}
