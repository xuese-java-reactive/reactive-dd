
package mofa.wangzhe.reactive.service.impl;

import mofa.wangzhe.reactive.model.OrgModel;
import mofa.wangzhe.reactive.service.OrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.CriteriaDefinition;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 *
 *
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
    public Mono<OrgModel> remove(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), OrgModel.class)
                .flatMap(f -> template.delete(f))
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Mono<OrgModel> update(OrgModel model) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(model.getUuid())), OrgModel.class)
                .flatMap(f -> {
                    //if (StringUtils.hasText(model.getPassword())) {
                    //    f.setPassword(model.getPassword());
                    //}
                    return template.update(f);
                })
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }

    @Override
    public Flux<OrgModel> page(int pageSize, int pageNum, String search) {

        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("uuid").like("%"+search+"%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        query = query.with(PageRequest.of(pageNum, pageSize, Sort.by(Sort.Direction.ASC, "uuid")));

        return template.select(query, OrgModel.class);
    }

    @Override
    public Mono<Long> count(String search) {
        Query query;
        if (StringUtils.hasText(search)) {
            query = Query.query(Criteria.where("uuid").like("%"+search+"%"));
        } else {
            query = Query.query(CriteriaDefinition.empty());
        }
        return template.select(query, OrgModel.class)
                .count();
    }

    @Override
    public Mono<OrgModel> one(String uuid) {
        return template.selectOne(Query.query(Criteria.where("uuid").is(uuid)), OrgModel.class)
                .switchIfEmpty(Mono.error(new Exception("参数为空")));
    }
}
